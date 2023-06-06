package top.itcat.mall.admin.service.impl;


import cn.hutool.core.util.StrUtil;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import top.itcat.mall.admin.bo.AdminUserDetails;
import top.itcat.mall.admin.dto.UmsAdminRegisterParam;
import top.itcat.mall.admin.dto.UpdateAdminPasswordParam;
import top.itcat.mall.admin.service.*;
import top.itcat.mall.admin.vo.AdminInfoVO;
import top.itcat.mall.admin.vo.UmsAdminRegisterSuccessVO;
import top.itcat.mall.common.api.CommonPage;
import top.itcat.mall.common.constant.RedisConstant;
import top.itcat.mall.common.exception.Asserts;
import top.itcat.mall.common.service.RedisService;
import top.itcat.mall.common.util.RequestUtil;
import top.itcat.mall.entity.*;
import top.itcat.mall.mapper.UmsAdminMapper;
import top.itcat.mall.security.util.JwtTokenUtil;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 后台用户表 服务实现类
 * </p>
 *
 * @author CatKitty 33641
 * @since 2023-06-04
 */
@Slf4j
@Service
public class UmsAdminServiceImpl extends ServiceImpl<UmsAdminMapper, UmsAdmin> implements UmsAdminService {

    @Autowired
    private UmsResourceService resourceService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UmsRoleService roleService;

    @Autowired
    private UmsMenuService menuService;

    @Autowired
    private UmsAdminRoleRelationService adminRoleRelationService;

    @Autowired
    private RedisService redisService;

    @Override
    public UmsAdmin getUmsAdminByUsername(String username) {
        // 先从缓存中查找
        String key = RedisConstant.QUERY_ADMIN_INFO_KEY;
        String adminJson = redisService.vGet(key);
        if (StrUtil.isNotBlank(adminJson)) {
            return JSONUtil.toBean(adminJson, UmsAdmin.class);
        }
        // 从数据库中查询
        QueryWrapper<UmsAdmin> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        UmsAdmin admin = baseMapper.selectOne(wrapper);
        if (admin != null) {
            // 将用户数据入缓存
            adminJson = JSONUtil.toJsonStr(admin);
            redisService.vSet(key, adminJson, RedisConstant.QUERY_ADMIN_INFO_TIME, RedisConstant.QUERY_ADMIN_INFO_TIME_UNIT);
            return admin;
        }
        return null;
    }

    @Override
    public List<UmsResource> getResourceListByAdminId(Long adminId) {
        return resourceService.listAllByAdminId(adminId);
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        UmsAdmin admin = getUmsAdminByUsername(username);
        if (admin != null) {
            List<UmsResource> resources = getResourceListByAdminId(admin.getId());
            return new AdminUserDetails(admin, resources);
        }
        return null;
    }

    @Override
    public UmsAdminRegisterSuccessVO register(UmsAdminRegisterParam param) {
        UmsAdmin admin = new UmsAdmin();
        BeanUtils.copyProperties(param, admin);
        LocalDateTime now = LocalDateTime.now();
        admin.setCreateTime(now);
        admin.setStatus(1);
        // 需要查询是否有同名的用户
        QueryWrapper<UmsAdmin> wrapper = new QueryWrapper<>();
        wrapper.eq("username", param.getUsername());
        Integer count = baseMapper.selectCount(wrapper);
        if (count > 0) {
            // 或抛出异常，进行捕获
            return null;
        }
        String encodePassword = passwordEncoder.encode(param.getPassword());
        admin.setPassword(encodePassword);
        save(admin);
        UmsAdminRegisterSuccessVO vo = new UmsAdminRegisterSuccessVO();
        BeanUtils.copyProperties(admin, vo);
        return vo;
    }

    @Override
    public String login(String username, String password) {
        String token = null;
        try {
            UserDetails userDetails = loadUserByUsername(username);
            if (userDetails == null) {
                Asserts.fail("用户名或密码错误");
            }
            if (!passwordEncoder.matches(password, userDetails.getPassword())) {
                Asserts.fail("用户名或密码错误");
            }
            if (!userDetails.isEnabled()) {
                Asserts.fail("账号已被禁用");
            }
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails
                    .getUsername(), null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            token = jwtTokenUtil.generateToken(userDetails);
            // 修改登录时间，添加登录记录
            addLoginLog(userDetails.getUsername());
        } catch (AuthenticationException e) {
            log.warn("登录异常：{}", e);
        }
        return token;
    }

    @Override
    public String refreshToken(String token) {
        return jwtTokenUtil.refreshHeadToken(token);
    }

    @Override
    public AdminInfoVO getInfoByUsername(String username) {
        // TODO 应该上缓存
        UmsAdmin admin = getUmsAdminByUsername(username);
        List<UmsRole> umsRoles = roleService.listByAdminId(admin.getId());
        List<String> roles = umsRoles.stream().map(UmsRole::getName).collect(Collectors.toList());
        List<UmsMenu> menus = menuService.listByAdminId(admin.getId());
        AdminInfoVO vo = new AdminInfoVO();
        vo.setUsername(username);
        vo.setIcon(admin.getIcon());
        vo.setRoles(roles);
        vo.setMenus(menus);
        return vo;
    }

    @Override
    public Boolean updateRole(Long adminId, List<Long> roleIds) {
        // 先删再加
        return adminRoleRelationService.updateAdminAndRoles(adminId, roleIds);
    }

    @Override
    public CommonPage<UmsAdminRegisterSuccessVO> listByPage(Integer pageNum, Integer pageSize, String keyword) {
        QueryWrapper<UmsAdmin> wrapper = new QueryWrapper<>();
        if (StrUtil.isNotBlank(keyword)) {
            wrapper.like("username", keyword)
                    .or()
                    .like("nick_name", keyword);
        }
        Page<UmsAdmin> page = new Page<>(pageNum, pageSize);
        page(page, wrapper);
        List<UmsAdminRegisterSuccessVO> vos = page.getRecords().stream().map(it -> {
            UmsAdminRegisterSuccessVO vo = new UmsAdminRegisterSuccessVO();
            BeanUtils.copyProperties(it, vo);
            return vo;
        }).collect(Collectors.toList());
        if (pageNum * pageSize < page.getTotal()) {
            pageNum++;
        }
        return new CommonPage<>(pageNum, pageSize, (int) page.getPages(), page.getTotal(), vos);
    }

    @Override
    public Boolean removeAdminById(Long id) {
        // TODO 删除用户信息，包括所有附带的联系信息，缓存也需要进行删除，物理删除
        return null;
    }

    @Override
    public int updateAdminPassword(UpdateAdminPasswordParam param) {
        UmsAdmin admin = getUmsAdminByUsername(param.getUsername());
        if (admin == null) {
            return -1;
        }
        if (!passwordEncoder.matches(param.getOldPassword(), admin.getPassword())) {
            return -2;
        }
        UmsAdmin umsAdmin = new UmsAdmin();
        umsAdmin.setId(admin.getId());
        umsAdmin.setPassword(passwordEncoder.encode(param.getNewPassword()));
        if (updateById(umsAdmin)) {
            return 1;
        }
        return 0;
    }

    private void addLoginLog(String username) {
        UmsAdmin admin = getUmsAdminByUsername(username);
        if (admin == null) {
            return;
        }
        UmsAdminLoginLog loginLog = new UmsAdminLoginLog();
        loginLog.setAddress("");
        loginLog.setAdminId(admin.getId());
        loginLog.setCreateTime(LocalDateTime.now());
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String userAgentString = request.getHeader("User-Agent");
        UserAgent userAgent = UserAgentUtil.parse(userAgentString);
        String browser = userAgent.getBrowser().toString();
        String os = userAgent.getOs().toString();
        String userAgentStr = "Browser: " + browser + ", OS: " + os;
        loginLog.setUserAgent(userAgentStr);
        loginLog.setIp(RequestUtil.getRequestIp(request));
    }
}
