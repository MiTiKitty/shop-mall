package top.itcat.mall.admin.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @className: UpdateRoleResourceParam <br/>
 * @description: 修改角色资源关系参数类 <br/>
 * @author: CatKitty 33641 <br/>
 * @date: 2023/06/05 <br/>
 * @version: 1.0.0 <br/>
 */
@Data
public class UpdateRoleResourceParam {

    @NotNull
    private Long roleId;

    private List<Long> resourceIds;

}
