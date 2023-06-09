package top.itcat.mall.admin.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.itcat.mall.admin.dto.PmsProductCategoryNode;
import top.itcat.mall.admin.dto.PmsProductCategoryParam;
import top.itcat.mall.admin.service.PmsProductCategoryAttributeRelationService;
import top.itcat.mall.admin.service.PmsProductCategoryService;
import top.itcat.mall.common.api.CommonPage;
import top.itcat.mall.common.constant.RedisConstant;
import top.itcat.mall.common.service.RedisService;
import top.itcat.mall.entity.PmsProductCategory;
import top.itcat.mall.entity.PmsProductCategoryAttributeRelation;
import top.itcat.mall.mapper.PmsProductCategoryMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @className: PmsProductCategoryServiceImpl <br/>
 * @description: 商品分类服务实现类 <br/>
 * @author: CatKitty 33641 <br/>
 * @date: 2023/06/08 <br/>
 * @version: 1.0.0 <br/>
 */
@Slf4j
@Service
public class PmsProductCategoryServiceImpl extends ServiceImpl<PmsProductCategoryMapper, PmsProductCategory> implements PmsProductCategoryService {

    @Autowired
    private RedisService redisService;

    @Autowired
    private PmsProductCategoryAttributeRelationService productCategoryAttributeRelationService;

    @Override
    public Boolean updateShowStatus(List<Long> ids, Integer showStatus) {
        List<PmsProductCategory> categories = new ArrayList<>(ids.size());
        for (Long id : ids) {
            PmsProductCategory productCategory = new PmsProductCategory();
            productCategory.setId(id);
            productCategory.setShowStatus(showStatus);
            categories.add(productCategory);
        }
        return updateBatchById(categories);
    }

    @Override
    public boolean removeCategoryById(Long id) {
        // TODO 删除逻辑
        return false;
    }

    @Override
    public CommonPage<PmsProductCategory> listByPage(Long parentId, Integer pageSize, Integer pageNum) {
        QueryWrapper<PmsProductCategory> queryWrapper = new QueryWrapper<>();
        if (parentId != null) {
            queryWrapper.eq("parent_id", parentId);
        }
        queryWrapper.orderByDesc("sort");
        Page<PmsProductCategory> page = new Page<>(pageNum, pageSize);
        page(page, queryWrapper);
        List<PmsProductCategory> vos = page.getRecords();
        if (pageNum * pageSize < page.getTotal()) {
            pageNum++;
        }
        return new CommonPage<>(pageNum, pageSize, (int) page.getPages(), page.getTotal(), vos);
    }

    @Override
    public List<PmsProductCategoryNode> getTree() {
        // 有缓存读缓存
        String key = RedisConstant.QUERY_PRODUCT_CATEGORY_TREE_KEY;
        String json = redisService.vGet(key);
        if (StrUtil.isNotBlank(json)) {
            return JSONUtil.toList(json, PmsProductCategoryNode.class);
        }

        // 查库入缓存
        List<PmsProductCategory> all = list();
        List<PmsProductCategoryNode> nodeList = all.stream().map(it -> {
            PmsProductCategoryNode node = new PmsProductCategoryNode();
            BeanUtils.copyProperties(it, node);
            node.setChildren(new ArrayList<>());
            return node;
        }).collect(Collectors.toList());
        Map<Long, PmsProductCategoryNode> map = new HashMap<>();
        for (PmsProductCategoryNode node : nodeList) {
            map.put(node.getId(), node);
        }

        List<PmsProductCategoryNode> tree = new ArrayList<>();
        for (PmsProductCategoryNode node : nodeList) {
            PmsProductCategoryNode parent = map.get(node.getParentId());
            if (parent != null) {
                parent.getChildren().add(node);
            } else {
                tree.add(node);
            }
        }
        redisService.vSet(key, JSONUtil.toJsonStr(tree), RedisConstant.QUERY_PRODUCT_CATEGORY_TREE_TIME, RedisConstant.QUERY_PRODUCT_CATEGORY_TREE_TIME_UNIT);
        return tree;
    }

    @Override
    public void delCache() {
        redisService.del(RedisConstant.QUERY_PRODUCT_CATEGORY_TREE_KEY);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean create(PmsProductCategoryParam param) {
        PmsProductCategory pmsProductCategory = new PmsProductCategory();
        BeanUtils.copyProperties(param, pmsProductCategory);
        pmsProductCategory.setProductCount(0);
        setCategoryLevel(pmsProductCategory);
        int insert = baseMapper.insert(pmsProductCategory);
        if (insert == 0) {
            return false;
        }
        if (CollUtil.isEmpty(param.getProductAttributeIdList())) {
            return true;
        }
        List<Long> productAttributeIdList = param.getProductAttributeIdList();
        List<PmsProductCategoryAttributeRelation> list = new ArrayList<>();
        for (Long id : productAttributeIdList) {
            PmsProductCategoryAttributeRelation relation = new PmsProductCategoryAttributeRelation();
            relation.setProductCategoryId(pmsProductCategory.getId());
            relation.setProductAttributeId(id);
            list.add(relation);
        }
        return productCategoryAttributeRelationService.saveBatch(list);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateCategoryById(Long id, PmsProductCategoryParam param) {
        PmsProductCategory pmsProductCategory = new PmsProductCategory();
        BeanUtils.copyProperties(param, pmsProductCategory);
        pmsProductCategory.setId(id);
        if (param.getParentId() != null) {
            setCategoryLevel(pmsProductCategory);
        }
        int update = baseMapper.updateById(pmsProductCategory);
        if (update == 0) {
            return false;
        }
        if (CollUtil.isEmpty(param.getProductAttributeIdList())) {
            return true;
        }
        return productCategoryAttributeRelationService.updateRelation(id, param.getProductAttributeIdList());
    }

    /**
     * 根据分类的parentId设置分类的level
     */
    private void setCategoryLevel(PmsProductCategory productCategory) {
        //没有父分类时为一级分类
        if (productCategory.getParentId() == 0) {
            productCategory.setLevel(0);
        } else {
            //有父分类时选择根据父分类level设置
            PmsProductCategory parentCategory = getById(productCategory.getParentId());
            if (parentCategory != null) {
                productCategory.setLevel(parentCategory.getLevel() + 1);
            } else {
                productCategory.setLevel(0);
            }
        }
    }
}
