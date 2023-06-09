package top.itcat.mall.admin.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import top.itcat.mall.entity.PmsProductCategory;

import java.util.List;

/**
 * @className: PmsProductCategoryNode <br/>
 * @description: 商品分类节点 <br/>
 * @author: CatKitty 33641 <br/>
 * @date: 2023/06/08 <br/>
 * @version: 1.0.0 <br/>
 */
public class PmsProductCategoryNode extends PmsProductCategory {

    @Setter
    @Getter
    private List<PmsProductCategory> children;

}
