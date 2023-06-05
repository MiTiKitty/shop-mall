package top.itcat.mall.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.security.core.userdetails.UserDetails;
import top.itcat.mall.admin.dto.UmsAdminRegisterParam;
import top.itcat.mall.admin.vo.UmsAdminRegisterSuccessVO;
import top.itcat.mall.entity.UmsAdmin;
import top.itcat.mall.entity.UmsResource;

import java.util.List;

/**
 * <p>
 * 后台用户表 服务类
 * </p>
 *
 * @author CatKitty 33641
 * @since 2023-06-04
 */
public interface UmsAdminService extends IService<UmsAdmin> {

    /**
     * 根据用户名获取用户
     *
     * @param username
     *         用户名
     * @return 用户信息
     */
    UmsAdmin getUmsAdminByUsername(String username);

    /**
     * 根据用户id获取资源列表
     *
     * @param adminId
     *         用户id
     * @return 资源列表
     */
    List<UmsResource> getResourceListByAdminId(Long adminId);

    /**
     * 获取用户信息
     *
     * @param username
     *         用户名
     * @return 用户信息
     */
    UserDetails loadUserByUsername(String username);

    /**
     * 用户注册
     *
     * @param param
     *         注册参数
     * @return 注册成功返回对象
     */
    UmsAdminRegisterSuccessVO register(UmsAdminRegisterParam param);
}
