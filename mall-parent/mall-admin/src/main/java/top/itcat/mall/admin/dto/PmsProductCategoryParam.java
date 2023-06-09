package top.itcat.mall.admin.dto;

import lombok.Data;
import top.itcat.mall.admin.validator.FlagValidator;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @className: PmsProductCategoryParam <br/>
 * @description: 添加更新产品分类的参数类 <br/>
 * @author: CatKitty 33641 <br/>
 * @date: 2023/06/08 <br/>
 * @version: 1.0.0 <br/>
 */
@Data
public class PmsProductCategoryParam {

    private Long parentId;

    @NotBlank
    private String name;

    private String productUnit;

    @FlagValidator(value = {"0","1"},message = "状态只能为0或1")
    private Integer navStatus;

    @FlagValidator(value = {"0","1"},message = "状态只能为0或1")
    private Integer showStatus;

    @Min(value = 0)
    private Integer sort;

    private String icon;

    private String keywords;

    private String description;

    private List<Long> productAttributeIdList;

}
