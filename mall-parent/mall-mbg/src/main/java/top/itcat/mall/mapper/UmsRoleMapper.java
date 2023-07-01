package top.itcat.mall.mapper;

import org.apache.ibatis.annotations.Param;
import top.itcat.mall.entity.UmsRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 后台用户角色表 Mapper 接口
 * </p>
 *
 * @author CatKitty 33641
 * @since 2023-06-04
 */
public interface UmsRoleMapper extends BaseMapper<UmsRole> {

    /**
     * 根据用户id获取角色列表
     *
     * @param adminId
     *         用户id
     * @return 角色列表
     */
    List<UmsRole> selectListByAdminId(Long adminId);

    /**
     * 根据角色id集合修改该角色所拥有的用户数量
     *
     * @param ids
     *         id集合
     * @param value
     *         操作值
     * @return 行数
     */
    int updateAdminCountByIds(@Param("ids") Collection<Long> ids, @Param("value") int value);
}
