package top.itcat.mall.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.security.core.userdetails.UserDetails;
import top.itcat.mall.entity.UmsAdmin;

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
     * 获取用户信息
     *
     * @param username
     *         用户名
     * @return 用户信息
     */
    UserDetails loadUserByUsername(String username);

}
