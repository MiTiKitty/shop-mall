package top.itcat.mall.admin.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.itcat.mall.admin.service.*;
import top.itcat.mall.common.api.CommonPage;
import top.itcat.mall.entity.UmsMenu;
import top.itcat.mall.entity.UmsResource;
import top.itcat.mall.entity.UmsRole;
import top.itcat.mall.mapper.UmsRoleMapper;

import java.util.Collection;
import java.util.List;

/**
 * @className: UmsRoleServiceImpl <br/>
 * @description: 角色服务实现类 <br/>
 * @author: CatKitty 33641 <br/>
 * @date: 2023/06/05 <br/>
 * @version: 1.0.0 <br/>
 */
@Slf4j
@Service
public class UmsRoleServiceImpl extends ServiceImpl<UmsRoleMapper, UmsRole> implements UmsRoleService {

    @Autowired
    private UmsMenuService menuService;

    @Autowired
    private UmsResourceService resourceService;

    @Autowired
    private UmsRoleMenuRelationService roleMenuRelationService;

    @Autowired
    private UmsRoleResourcesRelationService roleResourcesRelationService;

    @Override
    public List<UmsRole> listByAdminId(Long adminId) {
        return baseMapper.selectListByAdminId(adminId);
    }

    @Override
    public void updateAdminCountByIds(Collection<Long> ids, int value) {
        if (CollUtil.isEmpty(ids) || Math.abs(value) > 1) {
            return;
        }
        baseMapper.updateAdminCountByIds(ids, value);
    }

    @Override
    public Boolean removeRolesByIds(List<Long> ids) {
        // TODO 删除与该些角色对应的关系记录
        return null;
    }

    @Override
    public CommonPage<UmsRole> listByPage(Integer pageNum, Integer pageSize, String keyword) {
        QueryWrapper<UmsRole> wrapper = new QueryWrapper<>();
        if (StrUtil.isNotBlank(keyword)) {
            wrapper.like("name", keyword);
        }
        Page<UmsRole> page = new Page<>(pageNum, pageSize);
        page(page, wrapper);
        List<UmsRole> vos = page.getRecords();
        if (pageNum * pageSize < page.getTotal()) {
            pageNum++;
        }
        return new CommonPage<>(pageNum, pageSize, (int) page.getPages(), page.getTotal(), vos);
    }

    @Override
    public List<UmsMenu> listMenuById(Long id) {
        return menuService.listByRoleId(id);
    }

    @Override
    public List<UmsResource> listResourceById(Long id) {
        return resourceService.listAllByRoleId(id);
    }

    @Override
    public Boolean allocMenus(Long roleId, List<Long> menuIds) {
        // 先删后加
        return roleMenuRelationService.allocMenus(roleId, menuIds);
    }

    @Override
    public Boolean allocResources(Long roleId, List<Long> resourceIds) {
        // 先删后加
        return roleResourcesRelationService.allocResources(roleId, resourceIds);
    }
}
