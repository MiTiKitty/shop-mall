package top.itcat.mall.admin.controller;

import cn.hutool.core.util.StrUtil;
import jdk.nashorn.internal.objects.annotations.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.itcat.mall.admin.dto.UmsAdminLoginParam;
import top.itcat.mall.admin.dto.UmsAdminRegisterParam;
import top.itcat.mall.admin.service.UmsAdminService;
import top.itcat.mall.admin.vo.AdminInfoVO;
import top.itcat.mall.admin.vo.UmsAdminRegisterSuccessVO;
import top.itcat.mall.common.api.CommonResult;
import top.itcat.mall.common.api.ResultCode;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.HashMap;
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

}
