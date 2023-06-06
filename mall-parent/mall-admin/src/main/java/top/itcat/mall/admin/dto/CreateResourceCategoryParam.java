package top.itcat.mall.admin.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @className: CreateResourceCategoryParam <br/>
 * @description: 添加资源分类参数类 <br/>
 * @author: CatKitty 33641 <br/>
 * @date: 2023/06/06 <br/>
 * @version: 1.0.0 <br/>
 */
@Data
public class CreateResourceCategoryParam {

    @NotBlank
    private String name;

    private Integer sort;

}
