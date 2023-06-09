package top.itcat.mall.admin.dto;

import lombok.Data;
import top.itcat.mall.admin.validator.FlagValidator;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @className: UpdateProductCategoryShowStatusParam <br/>
 * @description: 修改商品分类显示状态参数类 <br/>
 * @author: CatKitty 33641 <br/>
 * @date: 2023/06/08 <br/>
 * @version: 1.0.0 <br/>
 */
@Data
public class UpdateProductCategoryShowStatusParam {

    @NotEmpty
    private List<Long> ids;

    @FlagValidator(value = {"0","1"},message = "状态只能为0或1")
    private Integer showStatus;

}
