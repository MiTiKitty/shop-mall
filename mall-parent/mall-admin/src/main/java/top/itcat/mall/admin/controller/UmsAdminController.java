package top.itcat.mall.admin.controller;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.itcat.mall.admin.dto.UmsAdminLoginParam;
import top.itcat.mall.admin.dto.UmsAdminRegisterParam;
import top.itcat.mall.admin.service.UmsAdminService;
import top.itcat.mall.admin.vo.UmsAdminRegisterSuccessVO;
import top.itcat.mall.common.api.CommonResult;
import top.itcat.mall.common.api.ResultCode;

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
     * @return 用户登录
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

}
