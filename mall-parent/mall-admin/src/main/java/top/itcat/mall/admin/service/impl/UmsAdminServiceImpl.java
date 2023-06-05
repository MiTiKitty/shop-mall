package top.itcat.mall.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import top.itcat.mall.admin.bo.AdminUserDetails;
import top.itcat.mall.admin.dto.UmsAdminRegisterParam;
import top.itcat.mall.admin.service.UmsResourceService;
import top.itcat.mall.admin.vo.UmsAdminRegisterSuccessVO;
import top.itcat.mall.entity.UmsAdmin;
import top.itcat.mall.entity.UmsResource;
import top.itcat.mall.mapper.UmsAdminMapper;
import top.itcat.mall.admin.service.UmsAdminService;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

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

    @Override
    public UmsAdmin getUmsAdminByUsername(String username) {
        // TODO 先从缓存中查找

        // 从数据库中查询
        QueryWrapper<UmsAdmin> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        UmsAdmin admin = baseMapper.selectOne(wrapper);
        if (admin != null) {
            // TODO 将用户数据入缓存
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
        wrapper.eq("username", param.getNickname());
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
}
