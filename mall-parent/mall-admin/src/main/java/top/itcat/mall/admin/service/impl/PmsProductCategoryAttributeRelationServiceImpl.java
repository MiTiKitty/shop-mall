package top.itcat.mall.admin.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.itcat.mall.admin.service.PmsProductCategoryAttributeRelationService;
import top.itcat.mall.entity.PmsProductCategoryAttributeRelation;
import top.itcat.mall.mapper.PmsProductCategoryAttributeRelationMapper;

import java.util.List;

/**
 * @className: PmsProductCategoryAttributeRelationServiceImpl <br/>
 * @description: 商品属性-分类联系服务实现类 <br/>
 * @author: CatKitty 33641 <br/>
 * @date: 2023/06/08 <br/>
 * @version: 1.0.0 <br/>
 */
@Slf4j
@Service
public class PmsProductCategoryAttributeRelationServiceImpl
        extends ServiceImpl<PmsProductCategoryAttributeRelationMapper, PmsProductCategoryAttributeRelation>
        implements PmsProductCategoryAttributeRelationService {

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateRelation(Long categoryId, List<Long> productAttributeIdList) {
        // 先删再加
        int delete = baseMapper.delete(new LambdaQueryWrapper<PmsProductCategoryAttributeRelation>()
                .eq(PmsProductCategoryAttributeRelation::getProductCategoryId, categoryId));
        if (delete == 0) {
            return false;
        }
        if (CollUtil.isEmpty(productAttributeIdList)) {
            return true;
        }
        int count = 0;
        for (Long id : productAttributeIdList) {
            PmsProductCategoryAttributeRelation relation = new PmsProductCategoryAttributeRelation();
            relation.setProductCategoryId(categoryId);
            relation.setProductAttributeId(id);
            count += baseMapper.insert(relation);
        }
        return count == productAttributeIdList.size();
    }

    @Override
    public boolean deleteByCategoryId(Long categoryId) {
        return baseMapper.delete(new LambdaQueryWrapper<PmsProductCategoryAttributeRelation>()
              .eq(PmsProductCategoryAttributeRelation::getProductCategoryId, categoryId)) > 0;
    }
}
