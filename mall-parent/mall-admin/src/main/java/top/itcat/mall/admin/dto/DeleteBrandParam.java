package top.itcat.mall.admin.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @className: DeleteBrandParam <br/>
 * @description: 删除品牌参数类 <br/>
 * @author: CatKitty 33641 <br/>
 * @date: 2023/06/08 <br/>
 * @version: 1.0.0 <br/>
 */
@Data
public class DeleteBrandParam {

    @NotEmpty
    private List<Long> ids;

}
