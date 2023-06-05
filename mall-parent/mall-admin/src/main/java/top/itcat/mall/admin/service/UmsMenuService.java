package top.itcat.mall.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
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
}
