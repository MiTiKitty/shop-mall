package top.itcat.mall.admin.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.itcat.mall.admin.service.UmsResourceService;
import top.itcat.mall.admin.service.UmsRoleResourcesRelationService;
import top.itcat.mall.common.api.CommonPage;
import top.itcat.mall.common.constant.RedisConstant;
import top.itcat.mall.common.service.RedisService;
import top.itcat.mall.entity.UmsResource;
import top.itcat.mall.mapper.UmsResourceMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 后台资源表 服务实现类
 * </p>
 *
 * @author CatKitty 33641
 * @since 2023-06-04
 */
@Service
public class UmsResourceServiceImpl extends ServiceImpl<UmsResourceMapper, UmsResource> implements UmsResourceService {

    @Autowired
    private RedisService redisService;

    @Lazy
    @Autowired
    private UmsRoleResourcesRelationService roleResourcesRelationService;

    @Override
    public List<UmsResource> listAll() {
        // 有缓存读缓存
        String key = RedisConstant.QUERY_RESOURCE_ALL_KEY;
        String json = redisService.vGet(key);
        if (StrUtil.isNotBlank(json)) {
            return JSONUtil.toList(json, UmsResource.class);
        }
        // 查库，入缓存
        List<UmsResource> resourceList = baseMapper.selectList(null);
        json = JSONUtil.toJsonStr(resourceList);
        redisService.vSet(key, json, RedisConstant.QUERY_RESOURCE_ALL_TIME, RedisConstant.QUERY_RESOURCE_ALL_TIME_UNIT);
        return resourceList;
    }

    @Override
    public List<UmsResource> listAllByAdminId(Long adminId) {
        return baseMapper.selectListByAdminId(adminId);
    }

    @Override
    public List<UmsResource> listAllByRoleId(Long roleId) {
        // 有缓存查缓存
        String key = RedisConstant.QUERY_RESOURCE_BY_ROLE_KEY + roleId;
        String json = redisService.vGet(key);
        if (StrUtil.isNotBlank(json)) {
            return JSONUtil.toList(json, UmsResource.class);
        }
        // 查库，入缓存
        List<UmsResource> resourceList = baseMapper.selectListByRoleId(roleId);
        json = JSONUtil.toJsonStr(resourceList);
        redisService.vSet(key, json, RedisConstant.QUERY_RESOURCE_BY_ROLE_TIME, RedisConstant.QUERY_RESOURCE_BY_ROLE_TIME_UNIT);
        return resourceList;
    }

    @Override
    public CommonPage<UmsResource> listByPage(Long categoryId,
                                              String nameKeyword,
                                              String urlKeyword,
                                              Integer pageSize,
                                              Integer pageNum) {
        QueryWrapper<UmsResource> wrapper = new QueryWrapper<>();
        if (categoryId != null) {
            wrapper.eq("category_id", categoryId);
        }
        if (StrUtil.isNotBlank(nameKeyword)) {
            wrapper.and(w -> w.like("name", nameKeyword));
        }
        if (StrUtil.isNotBlank(urlKeyword)) {
            wrapper.and(w -> w.like("url", urlKeyword));
        }
        Page<UmsResource> page = new Page<>(pageNum, pageSize);
        page(page, wrapper);
        List<UmsResource> vos = page.getRecords();
        if (pageNum * pageSize < page.getTotal()) {
            pageNum++;
        }
        return new CommonPage<>(pageNum, pageSize, (int) page.getPages(), page.getTotal(), vos);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean removeResourceById(Long id) {
        // 先删自己
        int delete = baseMapper.deleteById(id);
        if (delete == 0) {
            return false;
        }

        // 资源删除了，还需要将角色资源关系进行清理
        boolean result = roleResourcesRelationService.delByResourceId(id);
        delCache();
        return result;
    }

    @Override
    public void delCacheByRoleId(Long roleId) {
        redisService.del(RedisConstant.QUERY_RESOURCE_BY_ROLE_KEY + roleId);
    }

    @Override
    public void delCacheByRoleIds(List<Long> roleIds) {
        String[] keys = new String[roleIds.size()];
        int i = 0;
        for (Long roleId : roleIds) {
            keys[i++] = RedisConstant.QUERY_RESOURCE_BY_ROLE_KEY + roleId;
        }
        redisService.del(keys);
    }

    @Override
    public void delCache() {
        redisService.del(RedisConstant.QUERY_RESOURCE_ALL_KEY);
        redisService.del(RedisConstant.QUERY_RESOURCE_BY_ROLE_KEY + "*");
    }
}
