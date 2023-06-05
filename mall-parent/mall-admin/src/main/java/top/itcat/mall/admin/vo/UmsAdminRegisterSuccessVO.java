package top.itcat.mall.admin.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @className: UmsAdminRegisterSuccessVO <br/>
 * @description: 注册用户成功视图类 <br/>
 * @author: CatKitty 33641 <br/>
 * @date: 2023/06/05 <br/>
 * @version: 1.0.0 <br/>
 */
@Data
public class UmsAdminRegisterSuccessVO {

    private Long id;

    private String username;

    private String email;

    private String nickName;

    private String note;

    private String icon;

    private Integer status;

    private LocalDateTime createTime;

    private LocalDateTime loginTime;

}
