package top.itcat.mall.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.itcat.mall.entity.PmsProductCategoryAttributeRelation;

import java.util.List;

/**
 * @className: PmsProductCategoryAttributeRelationService <br/>
 * @description: 商品属性-分类联系服务接口 <br/>
 * @author: CatKitty 33641 <br/>
 * @date: 2023/06/08 <br/>
 * @version: 1.0.0 <br/>
 */
public interface PmsProductCategoryAttributeRelationService extends IService<PmsProductCategoryAttributeRelation> {

    /**
     * 更新关系
     *
     * @param categoryId
     *         分类id
     * @param productAttributeIdList
     *         属性id集合
     * @return 成功与否
     */
    boolean updateRelation(Long categoryId, List<Long> productAttributeIdList);

    /**
     * 根据分类id删除联系
     *
     * @param categoryId
     *         分类id
     * @return 成功与否
     */
    boolean deleteByCategoryId(Long categoryId);
}
