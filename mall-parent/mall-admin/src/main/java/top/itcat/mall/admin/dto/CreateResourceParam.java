package top.itcat.mall.admin.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 * @className: CreateResourceParam <br/>
 * @description: TODO 描述当前类 <br/>
 * @author: CatKitty 33641 <br/>
 * @date: 2023/06/06 <br/>
 * @version: 1.0.0 <br/>
 */
@Data
public class CreateResourceParam {

    @NotBlank
    private String name;

    @NotBlank
    private String url;

    private String description;

    @Min(0)
    private Long categoryId;

}
