package top.itcat.mall.admin.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.itcat.mall.admin.service.UmsResourceService;
import top.itcat.mall.common.api.CommonPage;
import top.itcat.mall.entity.UmsResource;
import top.itcat.mall.mapper.UmsResourceMapper;

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

    @Override
    public List<UmsResource> listAll() {
        return baseMapper.selectList(null);
    }

    @Override
    public List<UmsResource> listAllByAdminId(Long adminId) {
        return baseMapper.selectListByAdminId(adminId);
    }

    @Override
    public List<UmsResource> listAllByRoleId(Long roleId) {
        return baseMapper.selectListByRoleId(roleId);
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

    @Override
    public Boolean removeResourceById(Long id) {
        // TODO 删除逻辑
        return null;
    }
}
