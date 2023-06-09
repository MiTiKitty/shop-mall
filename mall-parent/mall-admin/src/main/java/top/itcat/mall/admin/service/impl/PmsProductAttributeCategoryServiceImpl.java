package top.itcat.mall.admin.service.impl;

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
import top.itcat.mall.admin.service.PmsProductAttributeCategoryService;
import top.itcat.mall.admin.service.PmsProductAttributeService;
import top.itcat.mall.admin.service.PmsProductCategoryAttributeRelationService;
import top.itcat.mall.admin.vo.PmsProductAttributeCategoryNode;
import top.itcat.mall.common.api.CommonPage;
import top.itcat.mall.common.constant.RedisConstant;
import top.itcat.mall.common.service.RedisService;
import top.itcat.mall.entity.PmsProductAttribute;
import top.itcat.mall.entity.PmsProductAttributeCategory;
import top.itcat.mall.mapper.PmsProductAttributeCategoryMapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @className: PmsProductAttributeCategoryServiceImpl <br/>
 * @description: 商品属性分类服务实现类 <br/>
 * @author: CatKitty 33641 <br/>
 * @date: 2023/06/08 <br/>
 * @version: 1.0.0 <br/>
 */
@Slf4j
@Service
public class PmsProductAttributeCategoryServiceImpl
        extends ServiceImpl<PmsProductAttributeCategoryMapper, PmsProductAttributeCategory>
        implements PmsProductAttributeCategoryService {

    @Autowired
    private RedisService redisService;

    @Autowired
    private PmsProductAttributeService attributeService;

    @Autowired
    private PmsProductCategoryAttributeRelationService relationService;

    @Override
    public CommonPage<PmsProductAttributeCategory> listByPage(Integer pageSize, Integer pageNum) {
        Page<PmsProductAttributeCategory> page = new Page<>(pageNum, pageSize);
        page(page);
        List<PmsProductAttributeCategory> vos = page.getRecords();
        if (pageNum * pageSize < page.getTotal()) {
            pageNum++;
        }
        return new CommonPage<>(pageNum, pageSize, (int) page.getPages(), page.getTotal(), vos);
    }

    @Override
    public List<PmsProductAttributeCategory> getByIds(Collection<Long> ids) {
        QueryWrapper<PmsProductAttributeCategory> wrapper = new QueryWrapper<>();
        wrapper.in("id", ids);
        return list(wrapper);
    }

    @Override
    public List<PmsProductAttributeCategoryNode> getListWithAttr() {
        // 有缓存查缓存
        String key = RedisConstant.QUERY_PRODUCT_ATTR_CATEGORY_KEY;
        String json = redisService.vGet(key);
        if (StrUtil.isNotBlank(json)) {
            return JSONUtil.toList(json, PmsProductAttributeCategoryNode.class);
        }

        // 查库入缓存
        List<PmsProductAttributeCategory> all = list();
        List<Long> ids = all.stream().map(PmsProductAttributeCategory::getId).collect(Collectors.toList());
        List<PmsProductAttribute> attributes = attributeService.getAttrInfoByCategories(ids);
        List<PmsProductAttributeCategoryNode> nodes = all.stream().map(it -> {
            PmsProductAttributeCategoryNode node = new PmsProductAttributeCategoryNode();
            BeanUtils.copyProperties(it, node);
            node.setProductAttributeList(attributes.stream().filter(it1 -> it1.getProductAttributeCategoryId()
                    .equals(it.getId()))
                    .collect(Collectors.toList()));
            return node;
        }).collect(Collectors.toList());
        redisService.vSet(key, JSONUtil.toJsonStr(nodes), RedisConstant.QUERY_PRODUCT_ATTR_CATEGORY_TIME, RedisConstant.QUERY_PRODUCT_ATTR_CATEGORY_TIME_UNIT);
        return nodes;
    }

    @Override
    public void delCache() {
        redisService.del(RedisConstant.QUERY_PRODUCT_ATTR_CATEGORY_KEY);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean removeCategoryById(Long id) {
        // 先删自己
        int delete = baseMapper.deleteById(id);
        if (delete == 0) {
            return false;
        }

        // 再删联系表
        relationService.deleteByCategoryId(id);

        // 删除缓存
        delCache();
        return true;
    }
}
