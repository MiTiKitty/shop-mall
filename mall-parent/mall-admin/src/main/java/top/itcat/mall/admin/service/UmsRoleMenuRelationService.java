package top.itcat.mall.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.itcat.mall.entity.UmsRoleMenuRelation;

import java.util.List;

/**
 * @className: UmsRoleMenuRelationService <br/>
 * @description: 角色菜单关系服务接口 <br/>
 * @author: CatKitty 33641 <br/>
 * @date: 2023/06/05 <br/>
 * @version: 1.0.0 <br/>
 */
public interface UmsRoleMenuRelationService extends IService<UmsRoleMenuRelation> {

    /**
     * 给角色分配菜单
     *
     * @param roleId
     *         角色id
     * @param menuIds
     *         菜单id集合
     * @return 成功与否
     */
    Boolean allocMenus(Long roleId, List<Long> menuIds);
}
