package top.itcat.mall.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.itcat.mall.common.api.CommonPage;
import top.itcat.mall.entity.UmsMenu;
import top.itcat.mall.entity.UmsResource;
import top.itcat.mall.entity.UmsRole;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @className: UmsRoleService <br/>
 * @description: 角色服务接口 <br/>
 * @author: CatKitty 33641 <br/>
 * @date: 2023/06/05 <br/>
 * @version: 1.0.0 <br/>
 */
public interface UmsRoleService extends IService<UmsRole> {

    /**
     * 根据用户id获取角色列表
     *
     * @param adminId
     *         用户id
     * @return 角色列表
     */
    List<UmsRole> listByAdminId(Long adminId);

    /**
     * 根据角色id集合修改该角色所拥有的用户数量
     *
     * @param ids
     *         id集合
     * @param value
     *         操作值
     */
    void updateAdminCountByIds(Collection<Long> ids, int value);

    /**
     * 根据角色id集合删除角色相关信息
     *
     * @param ids
     *         id集合
     * @return 成功与否
     */
    Boolean removeRolesByIds(List<Long> ids);

    /**
     * 分页查询角色列表
     *
     * @param keyword
     *         关键词：角色名称
     * @param pageSize
     *         每页查询数量
     * @param pageNum
     *         当前查询页
     * @return 查询角色列表
     */
    CommonPage<UmsRole> listByPage(Integer pageNum, Integer pageSize, String keyword);

    /**
     * 根据角色id获取餐袋列表
     *
     * @param id
     *         角色id
     * @return 惨淡列表
     */
    List<UmsMenu> listMenuById(Long id);

    /**
     * 根据角色id获取资源列表
     *
     * @param id
     *         角色id
     * @return 资源列表
     */
    List<UmsResource> listResourceById(Long id);

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

}
