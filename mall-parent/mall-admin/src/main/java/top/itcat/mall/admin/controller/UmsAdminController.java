package top.itcat.mall.admin.controller;

import cn.hutool.core.util.StrUtil;
import jdk.nashorn.internal.objects.annotations.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.itcat.mall.admin.dto.*;
import top.itcat.mall.admin.service.UmsAdminService;
import top.itcat.mall.admin.service.UmsRoleService;
import top.itcat.mall.admin.vo.AdminInfoVO;
import top.itcat.mall.admin.vo.UmsAdminRegisterSuccessVO;
import top.itcat.mall.common.api.CommonPage;
import top.itcat.mall.common.api.CommonResult;
import top.itcat.mall.common.api.ResultCode;
import top.itcat.mall.entity.UmsAdmin;
import top.itcat.mall.entity.UmsRole;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @className: UmsAdminController <br/>
 * @description: 后台用户管理 <br/>
 * @author: CatKitty 33641 <br/>
 * @date: 2023/06/05 <br/>
 * @version: 1.0.0 <br/>
 */
@Slf4j
@RestController
@RequestMapping("/admin")
public class UmsAdminController {

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;

    @Autowired
    private UmsAdminService adminService;

    @Autowired
    private UmsRoleService roleService;

    /**
     * 用户注册
     *
     * @param param
     *         用户注册参数
     * @return 统一返回结果
     */
    @PostMapping("register")
    public CommonResult register(@RequestBody @Validated UmsAdminRegisterParam param) {
        if (StrUtil.isBlank(param.getNickName())) {
            param.setNickName(param.getUsername());
        }
        UmsAdminRegisterSuccessVO admin = adminService.register(param);
        if (admin == null) {
            return CommonResult.fail("注册失败");
        }
        return CommonResult.success(admin);
    }

    /**
     * 用户登录
     *
     * @return token
     */
    @PostMapping("login")
    public CommonResult login(@RequestBody @Validated UmsAdminLoginParam param) {
        String token = adminService.login(param.getUsername(), param.getPassword());
        if (token == null) {
            return CommonResult.fail("用户名或密码错误");
        }
        Map<String, String> map = new HashMap<>();
        map.put("token", token);
        map.put("tokenHead", tokenHead);
        return CommonResult.success(map);
    }

    /**
     * 刷新token
     *
     * @param request
     *         请求对象
     * @return 新的token
     */
    @GetMapping("refreshToken")
    public CommonResult refreshToken(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);
        String refreshToken = adminService.refreshToken(token);
        if (refreshToken == null) {
            return CommonResult.fail("token 已过期");
        }
        Map<String, String> map = new HashMap<>();
        map.put("token", refreshToken);
        map.put("tokenHead", tokenHead);
        return CommonResult.success(map);
    }

    /**
     * 获取当前登录用户的信息
     *
     * @param principal
     *         当前登录用户
     * @return 登录者信息
     */
    @GetMapping("info")
    public CommonResult info(Principal principal) {
        if (principal == null) {
            return CommonResult.fail(ResultCode.UNAUTHORIZED);
        }
        // 用户信息需要用户信息，权限列表，角色列表，
        AdminInfoVO result = adminService.getInfoByUsername(principal.getName());
        return CommonResult.success(result);
    }

    /**
     * 用户登出
     *
     * @return
     */
    @PostMapping("logout")
    public CommonResult logout() {
        return CommonResult.success(null);
    }

    /**
     * 分页查询用户列表
     *
     * @param keyword
     *         搜索关键词：用户名或者用户昵称
     * @param pageSize
     *         每页查询数量
     * @param pageNum
     *         当前查询页
     * @return 查询用户列表
     */
    @GetMapping("list")
    public CommonResult listByPage(@RequestParam(value = "keyword", required = false) String keyword,
                                   @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                   @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        CommonPage<UmsAdminRegisterSuccessVO> adminList = adminService.listByPage(pageNum, pageSize, keyword);
        return CommonResult.success(adminList);
    }

    /**
     * 根据用户id获取指定用户信息
     *
     * @param id
     *         用户id
     * @return 用户信息
     */
    @GetMapping("{id}")
    public CommonResult getInfoById(@PathVariable("id") Long id) {
        UmsAdmin admin = adminService.getById(id);
        if (admin == null) {
            return CommonResult.fail("用户不存在");
        }
        UmsAdminRegisterSuccessVO vo = new UmsAdminRegisterSuccessVO();
        BeanUtils.copyProperties(admin, vo);
        return CommonResult.success(vo);
    }

    /**
     * 根据用户id更新用户基本信息
     *
     * @param id
     *         用户id
     * @param dto
     *         用户基本信息
     * @return 统一返回结果
     */
    @PutMapping("{id}")
    public CommonResult updateById(@PathVariable("id") Long id, @RequestBody @Validated UmsAdminUpdateDTO dto) {
        UmsAdmin admin = new UmsAdmin();
        admin.setId(id);
        BeanUtils.copyProperties(dto, admin);
        boolean update = adminService.updateById(admin);
        if (update) {
            return CommonResult.success("修改成功");
        }
        return CommonResult.fail("修改失败");
    }

    /**
     * 根据用户id删除用户信息，包括该用户的所有联系信息
     *
     * @param id
     *         用户id
     * @return 统一返回结果
     */
    @DeleteMapping("{id}")
    public CommonResult deleteById(@PathVariable("id") Long id) {
        Boolean result = adminService.removeAdminById(id);
        if (result) {
            return CommonResult.success("删除成功");
        }
        return CommonResult.fail("删除失败");
    }

    /**
     * 修改用户密码
     *
     * @param param
     *         修改密码参数对象
     * @return 统一返回结果
     */
    @PutMapping("/updatePassword")
    public CommonResult updateAdminPassword(@RequestBody @Validated UpdateAdminPasswordParam param) {
        int status = adminService.updateAdminPassword(param);
        if (status > 0) {
            return CommonResult.success(status);
        } else if (status == -1) {
            return CommonResult.fail("找不到该用户");
        } else if (status == -2) {
            return CommonResult.fail("旧密码错误");
        } else {
            return CommonResult.fail("修改失败");
        }
    }

    /**
     * 修改用户账号状态
     *
     * @param id
     *         用户id
     * @param status
     *         用户状态值
     * @return 统一返回结果
     */
    @PutMapping("/updateStatus/{id}")
    public CommonResult updateAdminStatus(@PathVariable("id") Long id,
                                          @RequestParam("status") @Min(0) @Max(1) Integer status) {
        UmsAdmin admin = new UmsAdmin();
        admin.setId(id);
        admin.setStatus(status);
        boolean update = adminService.updateById(admin);
        if (update) {
            return CommonResult.success("修改成功");
        }
        return CommonResult.fail("修改失败");
    }

    /**
     * 修改用户角色列表
     *
     * @param param
     *         修改参数对象
     * @return 统一返回结果
     */
    @PutMapping("updateRoles")
    public CommonResult updateRole(@RequestBody @Validated UpdateAdminRoleParam param) {
        Boolean updateRole = adminService.updateRole(param.getAdminId(), param.getRoleIds());
        if (updateRole) {
            return CommonResult.success("修改成功");
        }
        return CommonResult.fail("修改失败");
    }

    /**
     * 获取指定用户id的角色列表
     *
     * @param id
     *         用户id
     * @return 角色列表
     */
    @GetMapping("/roles/{id}")
    public CommonResult getRoleListByAdminId(@PathVariable("id") Long id) {
        List<UmsRole> roles = roleService.listByAdminId(id);
        return CommonResult.success(roles);
    }

}
