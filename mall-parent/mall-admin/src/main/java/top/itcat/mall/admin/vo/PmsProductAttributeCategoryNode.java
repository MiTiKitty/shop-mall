package top.itcat.mall.admin.vo;

import lombok.Getter;
import lombok.Setter;
import top.itcat.mall.entity.PmsProductAttribute;
import top.itcat.mall.entity.PmsProductAttributeCategory;

import java.util.List;

/**
 * @className: PmsProductAttributeCategoryNode <br/>
 * @description: 带有属性的商品属性分类 <br/>
 * @author: CatKitty 33641 <br/>
 * @date: 2023/06/08 <br/>
 * @version: 1.0.0 <br/>
 */
public class PmsProductAttributeCategoryNode extends PmsProductAttributeCategory {

    @Setter
    @Getter
    private List<PmsProductAttribute> productAttributeList;

}
