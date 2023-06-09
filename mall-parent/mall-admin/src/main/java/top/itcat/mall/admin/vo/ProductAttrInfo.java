package top.itcat.mall.admin.vo;

import lombok.Data;

/**
 * @className: ProductAttrInfo <br/>
 * @description: 商品分类对应属性信息 <br/>
 * @author: CatKitty 33641 <br/>
 * @date: 2023/06/08 <br/>
 * @version: 1.0.0 <br/>
 */
@Data
public class ProductAttrInfo {

    /**
     * 商品属性ID
     */
    private Long attributeId;

    /**
     * 商品属性分类ID
     */
    private Long attributeCategoryId;

}
