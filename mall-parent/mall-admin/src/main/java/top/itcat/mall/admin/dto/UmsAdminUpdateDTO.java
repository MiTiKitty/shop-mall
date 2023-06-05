package top.itcat.mall.admin.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * @className: UmsAdminUpdateDTO <br/>
 * @description: 修改用户信息dto <br/>
 * @author: CatKitty 33641 <br/>
 * @date: 2023/06/05 <br/>
 * @version: 1.0.0 <br/>
 */
@Data
public class UmsAdminUpdateDTO {

    private String icon;

    @Email
    private String email;

    @NotBlank
    private String nickName;

    private String note;

}
