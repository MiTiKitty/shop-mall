package top.itcat.mall.admin.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @className: UpdateAdminRoleParam <br/>
 * @description: 更新用户角色参数类 <br/>
 * @author: CatKitty 33641 <br/>
 * @date: 2023/06/05 <br/>
 * @version: 1.0.0 <br/>
 */
@Data
public class UpdateAdminRoleParam {

    @NotNull
    private Long adminId;

    private List<Long> roleIds;

}
