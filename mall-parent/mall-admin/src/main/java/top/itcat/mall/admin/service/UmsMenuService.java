package top.itcat.mall.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.itcat.mall.admin.vo.UmsMenuNode;
import top.itcat.mall.common.api.CommonPage;
import top.itcat.mall.entity.UmsMenu;

import java.util.List;

/**
 * @className: UmsMenuService <br/>
 * @description: 菜单服务接口 <br/>
 * @author: CatKitty 33641 <br/>
 * @date: 2023/06/05 <br/>
 * @version: 1.0.0 <br/>
 */
public interface UmsMenuService extends IService<UmsMenu> {

    /**
     * 根据用户id获取菜单列表
     *
     * @param adminId
     *         用户id
     * @return 菜单列表
     */
    List<UmsMenu> listByAdminId(Long adminId);

    /**
     * 根据角色id获取菜单列表
     *
     * @param roleId
     *         角色id
     * @return 菜单列表
     */
    List<UmsMenu> listByRoleId(Long roleId);

    /**
     * 根据菜单id删除菜单等信息，物理删除
     *
     * @param id
     *         菜单id
     * @return 成功与否
     */
    boolean removeMenuById(Long id);

    /**
     * 分页获取菜单列表
     *
     * @param parentId
     *         父id
     * @param pageNum
     *         当前页
     * @param pageSize
     *         每页查询数量
     * @return 菜单列表
     */
    CommonPage<UmsMenu> listByPage(Long parentId, Integer pageNum, Integer pageSize);

    /**
     * 获取树形结构的菜单
     *
     * @return 树形结构的菜单
     */
    List<UmsMenuNode> tree();

    /**
     * 删除缓存
     */
    void delCache();

}
