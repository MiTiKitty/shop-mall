package top.itcat.mall.admin.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @className: UpdateRoleMenuParam <br/>
 * @description: 修改角色菜单关联参数类 <br/>
 * @author: CatKitty 33641 <br/>
 * @date: 2023/06/05 <br/>
 * @version: 1.0.0 <br/>
 */
@Data
public class UpdateRoleMenuParam {

    @NotNull
    private Long roleId;

    private List<Long> menuIds;

}
