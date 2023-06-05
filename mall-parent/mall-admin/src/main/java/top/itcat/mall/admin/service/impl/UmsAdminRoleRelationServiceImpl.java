package top.itcat.mall.admin.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.itcat.mall.admin.service.UmsAdminRoleRelationService;
import top.itcat.mall.admin.service.UmsRoleService;
import top.itcat.mall.entity.UmsAdminRoleRelation;
import top.itcat.mall.mapper.UmsAdminRoleRelationMapper;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @className: UmsAdminRoleRelationServiceImpl <br/>
 * @description: 用户角色联系服务实现类 <br/>
 * @author: CatKitty 33641 <br/>
 * @date: 2023/06/05 <br/>
 * @version: 1.0.0 <br/>
 */
@Slf4j
@Service
public class UmsAdminRoleRelationServiceImpl extends ServiceImpl<UmsAdminRoleRelationMapper, UmsAdminRoleRelation> implements UmsAdminRoleRelationService {

    @Autowired
    private UmsRoleService roleService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean updateAdminAndRoles(Long adminId, List<Long> roleIds) {
        UpdateWrapper<UmsAdminRoleRelation> wrapper = new UpdateWrapper<>();
        wrapper.eq("admin_id", adminId);
        List<UmsAdminRoleRelation> relations = baseMapper.selectList(wrapper);
        if (CollUtil.isNotEmpty(relations)) {
            Set<Long> descRoleIds = relations.stream().map(UmsAdminRoleRelation::getRoleId).collect(Collectors.toSet());
            roleService.updateAdminCountByIds(descRoleIds, -1);
        }
        baseMapper.delete(wrapper);
        int count = 0;
        Set<Long> roleIdSet = new HashSet<>(roleIds);
        for (Long roleId : roleIdSet) {
            UmsAdminRoleRelation relation = new UmsAdminRoleRelation();
            relation.setAdminId(adminId);
            relation.setRoleId(roleId);
            count += baseMapper.insert(relation);
        }
        if (CollUtil.isNotEmpty(roleIdSet)) {
            roleService.updateAdminCountByIds(roleIdSet, 1);
        }
        return count == roleIds.size();
    }
}
