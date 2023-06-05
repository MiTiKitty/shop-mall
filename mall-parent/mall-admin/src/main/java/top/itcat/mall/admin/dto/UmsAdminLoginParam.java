package top.itcat.mall.admin.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @className: UmsAdminLoginParam <br/>
 * @description: 用户登录dto <br/>
 * @author: CatKitty 33641 <br/>
 * @date: 2023/06/05 <br/>
 * @version: 1.0.0 <br/>
 */
@Data
public class UmsAdminLoginParam {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

}
