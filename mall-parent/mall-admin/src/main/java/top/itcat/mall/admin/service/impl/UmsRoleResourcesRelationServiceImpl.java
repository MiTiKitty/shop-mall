package top.itcat.mall.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.itcat.mall.admin.service.UmsResourceService;
import top.itcat.mall.admin.service.UmsRoleResourcesRelationService;
import top.itcat.mall.entity.UmsRoleResourceRelation;
import top.itcat.mall.mapper.UmsRoleResourceRelationMapper;

import java.util.List;

/**
 * @className: UmsRoleResourcesRelationServiceImpl <br/>
 * @description: 角色资源关系服务实现类 <br/>
 * @author: CatKitty 33641 <br/>
 * @date: 2023/06/05 <br/>
 * @version: 1.0.0 <br/>
 */
@Slf4j
@Service
public class UmsRoleResourcesRelationServiceImpl extends ServiceImpl<UmsRoleResourceRelationMapper, UmsRoleResourceRelation> implements UmsRoleResourcesRelationService {

    @Autowired
    private UmsResourceService resourceService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean allocResources(Long roleId, List<Long> resourceIds) {
        UpdateWrapper<UmsRoleResourceRelation> wrapper = new UpdateWrapper<>();
        wrapper.eq("role_id", roleId);
        baseMapper.delete(wrapper);
        int count = 0;
        for (Long resourceId : resourceIds) {
            UmsRoleResourceRelation relation = new UmsRoleResourceRelation();
            relation.setRoleId(roleId);
            relation.setResourceId(resourceId);
            count += baseMapper.insert(relation);
        }
        resourceService.delCacheByRoleId(roleId);
        return count == resourceIds.size();
    }
}
