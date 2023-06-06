package top.itcat.mall.admin.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 * @className: CreateMenuParam <br/>
 * @description: 添加菜单参数类 <br/>
 * @author: CatKitty 33641 <br/>
 * @date: 2023/06/06 <br/>
 * @version: 1.0.0 <br/>
 */
@Data
public class CreateMenuParam {

    private Long parentId;

    @NotBlank
    private String title;

    private Integer level;

    private Integer sort;

    @NotBlank
    private String name;

    private String icon;

    private Integer hidden;

}
