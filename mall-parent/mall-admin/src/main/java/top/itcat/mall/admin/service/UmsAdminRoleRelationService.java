package top.itcat.mall.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.itcat.mall.entity.UmsAdminRoleRelation;

import java.util.List;

/**
 * @className: UmsAdminRoleRelationService <br/>
 * @description: 用户角色联系服务接口 <br/>
 * @author: CatKitty 33641 <br/>
 * @date: 2023/06/05 <br/>
 * @version: 1.0.0 <br/>
 */
public interface UmsAdminRoleRelationService extends IService<UmsAdminRoleRelation> {
    /**
     * 更新用户与角色的关系
     *
     * @param adminId
     *         用户id
     * @param roleIds
     *         角色id集合
     * @return 成功与否
     */
    Boolean updateAdminAndRoles(Long adminId, List<Long> roleIds);
}
