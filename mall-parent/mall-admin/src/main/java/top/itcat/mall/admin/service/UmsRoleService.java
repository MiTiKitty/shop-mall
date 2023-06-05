package top.itcat.mall.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.itcat.mall.entity.UmsRole;

import java.util.List;

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

}
