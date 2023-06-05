package top.itcat.mall.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.itcat.mall.admin.service.UmsRoleMenuRelationService;
import top.itcat.mall.entity.UmsRoleMenuRelation;
import top.itcat.mall.mapper.UmsRoleMenuRelationMapper;

import java.util.List;

/**
 * @className: UmsRoleMenuRelationServiceImpl <br/>
 * @description: 角色菜单服务实现类 <br/>
 * @author: CatKitty 33641 <br/>
 * @date: 2023/06/05 <br/>
 * @version: 1.0.0 <br/>
 */
@Slf4j
@Service
public class UmsRoleMenuRelationServiceImpl extends ServiceImpl<UmsRoleMenuRelationMapper, UmsRoleMenuRelation> implements UmsRoleMenuRelationService {

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean allocMenus(Long roleId, List<Long> menuIds) {
        UpdateWrapper<UmsRoleMenuRelation> wrapper = new UpdateWrapper<>();
        wrapper.eq("role_id", roleId);
        baseMapper.delete(wrapper);
        int count = 0;
        for (Long menuId : menuIds) {
            UmsRoleMenuRelation relation = new UmsRoleMenuRelation();
            relation.setRoleId(roleId);
            relation.setMenuId(menuId);
            count += baseMapper.insert(relation);
        }
        return count == menuIds.size();
    }
}
