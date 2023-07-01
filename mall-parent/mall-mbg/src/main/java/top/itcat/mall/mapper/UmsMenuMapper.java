package top.itcat.mall.mapper;

import top.itcat.mall.entity.UmsMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 后台菜单表 Mapper 接口
 * </p>
 *
 * @author CatKitty 33641
 * @since 2023-06-04
 */
public interface UmsMenuMapper extends BaseMapper<UmsMenu> {

    /**
     * 根据用户id获取菜单列表
     *
     * @param adminId
     *         用户id
     * @return 菜单列表
     */
    List<UmsMenu> selectListByAdminId(Long adminId);

    /**
     * 根据角色id获取菜单列表
     *
     * @param roleId
     *         角色id
     * @return 菜单列表
     */
    List<UmsMenu> selectListByRoleId(Long roleId);
}
