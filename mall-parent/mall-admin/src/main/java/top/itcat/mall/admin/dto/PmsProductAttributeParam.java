package top.itcat.mall.admin.dto;

import lombok.Data;
import top.itcat.mall.admin.validator.FlagValidator;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @className: PmsProductAttributeParam <br/>
 * @description: 商品属性参数类 <br/>
 * @author: CatKitty 33641 <br/>
 * @date: 2023/06/08 <br/>
 * @version: 1.0.0 <br/>
 */
@Data
public class PmsProductAttributeParam {

    @NotNull
    private Long productAttributeCategoryId;

    @NotBlank
    private String name;

    @FlagValidator({"0","1","2"})
    private Integer selectType;

    @FlagValidator({"0","1"})
    private Integer inputType;

    private String inputList;

    private Integer sort;

    @FlagValidator({"0","1"})
    private Integer filterType;

    @FlagValidator({"0","1","2"})
    private Integer searchType;

    @FlagValidator({"0","1"})
    private Integer relatedStatus;

    @FlagValidator({"0","1"})
    private Integer handAddStatus;

    @FlagValidator({"0","1"})
    private Integer type;

}
