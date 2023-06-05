package top.itcat.mall.admin.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @className: DeleteRoleParam <br/>
 * @description: 删除角色参数类 <br/>
 * @author: CatKitty 33641 <br/>
 * @date: 2023/06/05 <br/>
 * @version: 1.0.0 <br/>
 */
@Data
public class DeleteRoleParam {

    @NotEmpty
    private List<Long> ids;

}
