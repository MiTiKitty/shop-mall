package top.itcat.mall.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
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
}
