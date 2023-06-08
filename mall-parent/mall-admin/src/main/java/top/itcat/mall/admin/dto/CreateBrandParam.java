package top.itcat.mall.admin.dto;

import lombok.Data;
import top.itcat.mall.admin.validator.FlagValidator;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

/**
 * @className: CreateBrandParam <br/>
 * @description: 添加品牌参数类 <br/>
 * @author: CatKitty 33641 <br/>
 * @date: 2023/06/08 <br/>
 * @version: 1.0.0 <br/>
 */
@Data
public class CreateBrandParam {

    @NotBlank
    private String name;

    private String firstLetter;

    @Min(value = 0)
    private Integer sort;

    @FlagValidator(value = {"0","1"}, message = "厂家状态不正确")
    private Integer factoryStatus;

    @FlagValidator(value = {"0","1"}, message = "显示状态不正确")
    private Integer showStatus;

    @NotBlank
    private String logo;

    private String bigPic;

    private String brandStory;

}
