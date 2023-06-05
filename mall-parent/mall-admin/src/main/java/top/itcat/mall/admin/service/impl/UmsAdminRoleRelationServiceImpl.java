package top.itcat.mall.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.itcat.mall.admin.service.UmsAdminRoleRelationService;
import top.itcat.mall.entity.UmsAdminRoleRelation;
import top.itcat.mall.mapper.UmsAdminRoleRelationMapper;

import java.util.List;

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

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean updateAdminAndRoles(Long adminId, List<Long> roleIds) {
        UpdateWrapper<UmsAdminRoleRelation> wrapper = new UpdateWrapper<>();
        wrapper.eq("admin_id", adminId);
        baseMapper.delete(wrapper);
        int count = 0;
        for (Long roleId : roleIds) {
            UmsAdminRoleRelation relation = new UmsAdminRoleRelation();
            relation.setAdminId(adminId);
            relation.setRoleId(roleId);
            count += baseMapper.insert(relation);
        }
        return count == roleIds.size();
    }
}
