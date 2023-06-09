package top.itcat.mall.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.itcat.mall.entity.UmsRoleResourceRelation;

import java.util.List;

/**
 * @className: UmsRoleResourcesRelationService <br/>
 * @description: 角色资源关系服务接口 <br/>
 * @author: CatKitty 33641 <br/>
 * @date: 2023/06/05 <br/>
 * @version: 1.0.0 <br/>
 */
public interface UmsRoleResourcesRelationService extends IService<UmsRoleResourceRelation> {

    /**
     * 给角色分配资源
     *
     * @param roleId
     *         角色id
     * @param resourceIds
     *         资源id集合
     * @return 成功与否
     */
    Boolean allocResources(Long roleId, List<Long> resourceIds);

    /**
     * 根据资源id删除角色资源联系
     *
     * @param resourceId
     *         资源id
     * @return 成功与否
     */
    boolean delByResourceId(Long resourceId);
}
