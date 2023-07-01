package top.itcat.mall.mapper;

import top.itcat.mall.entity.UmsResource;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 后台资源表 Mapper 接口
 * </p>
 *
 * @author CatKitty 33641
 * @since 2023-06-04
 */
public interface UmsResourceMapper extends BaseMapper<UmsResource> {

    /**
     * 根据用户id查找资源列表
     *
     * @param adminId
     *         用户id
     * @return 资源列表
     */
    List<UmsResource> selectListByAdminId(Long adminId);

    /**
     * 根据角色id获取资源列表
     *
     * @param roleId
     *         角色id
     * @return 资源列表
     */
    List<UmsResource> selectListByRoleId(Long roleId);
}
