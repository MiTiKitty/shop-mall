package top.itcat.mall.admin.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @className: UpdateAdminPasswordParam <br/>
 * @description: 修改用户密码参数类 <br/>
 * @author: CatKitty 33641 <br/>
 * @date: 2023/06/05 <br/>
 * @version: 1.0.0 <br/>
 */
@Data
public class UpdateAdminPasswordParam {

    @NotBlank
    private String username;

    @NotBlank
    private String oldPassword;

    @NotBlank
    private String newPassword;

}
