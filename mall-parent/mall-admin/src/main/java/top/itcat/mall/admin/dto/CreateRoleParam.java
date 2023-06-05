package top.itcat.mall.admin.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 * @className: CreateRoleParam <br/>
 * @description: 创建角色参数类 <br/>
 * @author: CatKitty 33641 <br/>
 * @date: 2023/06/05 <br/>
 * @version: 1.0.0 <br/>
 */
@Data
public class CreateRoleParam {

    @NotBlank
    private String name;

    private String description;

    @Min(0)
    @Max(1)
    private Integer status;

    private Integer sort;

}
