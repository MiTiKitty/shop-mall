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
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.itcat.mall.admin.service.PmsProductAttributeCategoryService;
import top.itcat.mall.admin.service.PmsProductAttributeService;
import top.itcat.mall.admin.vo.ProductAttrInfo;
import top.itcat.mall.common.api.CommonPage;
import top.itcat.mall.common.constant.RedisConstant;
import top.itcat.mall.common.service.RedisService;
import top.itcat.mall.entity.PmsProductAttribute;
import top.itcat.mall.entity.PmsProductAttributeCategory;
import top.itcat.mall.mapper.PmsProductAttributeMapper;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @className: PmsProductAttributeServiceImpl <br/>
 * @description: 商品属性服务实现类 <br/>
 * @author: CatKitty 33641 <br/>
 * @date: 2023/06/08 <br/>
 * @version: 1.0.0 <br/>
 */
@Slf4j
@Service
public class PmsProductAttributeServiceImpl
        extends ServiceImpl<PmsProductAttributeMapper, PmsProductAttribute>
        implements PmsProductAttributeService {

    @Lazy
    @Autowired
    private PmsProductAttributeCategoryService productAttributeCategoryService;

    @Autowired
    private RedisService redisService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean create(PmsProductAttribute pmsProductAttribute) {
        // 入库
        int insert = baseMapper.insert(pmsProductAttribute);
        if (insert == 0) {
            return false;
        }

        // 更新属性分类相应字段的个数
        PmsProductAttributeCategory category = productAttributeCategoryService.getById(pmsProductAttribute.getProductAttributeCategoryId());
        PmsProductAttributeCategory updateCategory = new PmsProductAttributeCategory();
        if (category != null) {
            updateCategory.setId(category.getId());
            if (pmsProductAttribute.getType().equals(0)) {
                updateCategory.setAttributeCount(category.getAttributeCount() + 1);
                productAttributeCategoryService.updateById(updateCategory);
            } else if (pmsProductAttribute.getType().equals(1)) {
                updateCategory.setParamCount(category.getParamCount() + 1);
                productAttributeCategoryService.updateById(updateCategory);
            }
        }

        // 删除属性分类的缓存
        productAttributeCategoryService.delCache();
        return true;
    }

    @Override
    public boolean removeAttributeByIds(List<Long> ids) {
        // 获取所有待删除的属性列表
        List<PmsProductAttribute> all = listByIds(ids);
        Set<Long> categoryIds = all.stream()
                .map(PmsProductAttribute::getProductAttributeCategoryId)
                .collect(Collectors.toSet());
        List<PmsProductAttributeCategory> categories = productAttributeCategoryService.getByIds(categoryIds);
        Map<Long, PmsProductAttributeCategory> map = new HashMap<>();
        for (PmsProductAttributeCategory category : categories) {
            map.put(category.getId(), category);
        }

        // 更新属性分类相应字段的个数
        List<PmsProductAttributeCategory> updateCategories = new ArrayList<>();
        for (PmsProductAttribute attribute : all) {
            PmsProductAttributeCategory updateCategory = new PmsProductAttributeCategory();
            PmsProductAttributeCategory category = map.get(attribute.getProductAttributeCategoryId());
            if (category != null) {
                boolean flag = false;
                updateCategory.setId(attribute.getProductAttributeCategoryId());
                if (attribute.getType().equals(0)) {
                    if (category.getAttributeCount() > 0) {
                        flag = true;
                        updateCategory.setAttributeCount(category.getAttributeCount() - 1);
                    }
                } else if (attribute.getType().equals(1)) {
                    if (category.getParamCount() > 0) {
                        flag = true;
                        updateCategory.setParamCount(category.getParamCount() - 1);
                    }
                }
                if (flag) {
                    updateCategories.add(updateCategory);
                }
            }
        }
        if (updateCategories.size() > 0) {
            productAttributeCategoryService.updateBatchById(updateCategories);
        }
        int count = baseMapper.deleteBatchIds(ids);

        // 删除属性分类的缓存
        productAttributeCategoryService.delCache();
        delCache();
        return count > 0;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateAttributeById(PmsProductAttribute pmsProductAttribute) {
        PmsProductAttribute oldValue = getById(pmsProductAttribute.getId());
        int count = baseMapper.updateById(pmsProductAttribute);
        if (count == 0) {
            return false;
        }

        // 如果旧属性的分类id不等于新属性的分类id，则应该减掉原有的分类的字段数量，并且给新的分类加上字段数量
        if (!oldValue.getProductAttributeCategoryId().equals(pmsProductAttribute.getProductAttributeCategoryId())) {
            PmsProductAttributeCategory category = productAttributeCategoryService.getById(oldValue.getProductAttributeCategoryId());
            PmsProductAttributeCategory newCategory = productAttributeCategoryService.getById(pmsProductAttribute.getProductAttributeCategoryId());
            PmsProductAttributeCategory updateCategory = new PmsProductAttributeCategory();
            PmsProductAttributeCategory updateCategory2 = new PmsProductAttributeCategory();
            if (category != null) {
                updateCategory.setId(category.getId());
                if (pmsProductAttribute.getType().equals(0)) {
                    if (category.getAttributeCount() > 0) {
                        updateCategory.setAttributeCount(category.getAttributeCount() - 1);
                    }
                } else if (pmsProductAttribute.getType().equals(1)) {
                    if (category.getParamCount() > 0) {
                        updateCategory.setParamCount(category.getParamCount() - 1);
                    }
                }
                productAttributeCategoryService.updateById(updateCategory);
            }
            if (newCategory != null) {
                updateCategory2.setId(newCategory.getId());
                if (pmsProductAttribute.getType().equals(0)) {
                    if (newCategory.getAttributeCount() > 0) {
                        updateCategory2.setAttributeCount(newCategory.getAttributeCount() + 1);
                    }
                } else if (pmsProductAttribute.getType().equals(1)) {
                    if (newCategory.getParamCount() > 0) {
                        updateCategory2.setParamCount(newCategory.getParamCount() + 1);
                    }
                }
                productAttributeCategoryService.updateById(updateCategory2);
            }
            delCache();
            return true;
        }

        // 如果两者不同，则更新属性分类字段值
        if (!oldValue.getType().equals(pmsProductAttribute.getType())) {
            PmsProductAttributeCategory category = productAttributeCategoryService.getById(pmsProductAttribute.getProductAttributeCategoryId());
            PmsProductAttributeCategory updateCategory = new PmsProductAttributeCategory();
            if (category != null) {
                updateCategory.setId(category.getId());
                if (pmsProductAttribute.getType().equals(0)) {
                    if (category.getAttributeCount() > 0) {
                        updateCategory.setParamCount(category.getParamCount() - 1);
                    }
                    updateCategory.setAttributeCount(category.getAttributeCount() + 1);
                } else if (pmsProductAttribute.getType().equals(1)) {
                    if (category.getParamCount() > 0) {
                        updateCategory.setAttributeCount(category.getAttributeCount() - 1);
                    }
                    updateCategory.setParamCount(category.getParamCount() + 1);
                }
                productAttributeCategoryService.updateById(updateCategory);
            }
        }

        // 删除属性分类的缓存
        productAttributeCategoryService.delCache();
        delCache();
        return true;
    }

    @Override
    public CommonPage<PmsProductAttribute> listByPage(Long cid, Integer type, Integer pageSize, Integer pageNum) {
        QueryWrapper<PmsProductAttribute> wrapper = new QueryWrapper<>();
        if (cid != null) {
            wrapper.eq("product_attribute_category_id", cid);
        }
        if (type != null) {
            wrapper.and(w -> w.eq("type", type));
        }
        Page<PmsProductAttribute> page = new Page<>(pageNum, pageSize);
        page(page, wrapper);
        List<PmsProductAttribute> vos = page.getRecords();
        if (pageNum * pageSize < page.getTotal()) {
            pageNum++;
        }
        return new CommonPage<>(pageNum, pageSize, (int) page.getPages(), page.getTotal(), vos);
    }

    @Override
    public List<ProductAttrInfo> getAttrInfo(Long pid) {
        // 有缓存查缓存
        String key = RedisConstant.QUERY_PRODUCT_ATTR_INFO_BY_PID_KEY + pid;
        String json = redisService.vGet(key);
        if (StrUtil.isNotEmpty(json)) {
            return JSONUtil.toList(json, ProductAttrInfo.class);
        }

        // 查库入缓存
        QueryWrapper<PmsProductAttribute> wrapper = new QueryWrapper<>();
        wrapper.eq("product_attribute_category_id", pid);
        List<PmsProductAttribute> vos = list(wrapper);
        if (CollUtil.isEmpty(vos)) {
            return Collections.emptyList();
        }
        List<ProductAttrInfo> infos = new ArrayList<>();
        for (PmsProductAttribute vo : vos) {
            ProductAttrInfo info = new ProductAttrInfo();
            info.setAttributeCategoryId(pid);
            info.setAttributeId(vo.getId());
            infos.add(info);
        }
        redisService.vSet(key, JSONUtil.toJsonStr(infos), getExpireTime(), RedisConstant.QUERY_PRODUCT_ATTR_INFO_BY_PID_TIME_UNIT);
        return infos;
    }

    @Override
    public void delCache() {
        redisService.del(RedisConstant.QUERY_PRODUCT_ATTR_INFO_BY_PID_KEY + "*");
    }

    @Override
    public List<PmsProductAttribute> getAttrInfoByCategories(List<Long> categories) {
        QueryWrapper<PmsProductAttribute> wrapper = new QueryWrapper<>();
        wrapper.in("product_attribute_category_id", categories);
        return list(wrapper);
    }

    /**
     * 随机生成存活时间，单位:秒
     *
     * @return 存活时间
     */
    private long getExpireTime() {
        return new Random().nextInt(1000) + 5L;
    }
}
