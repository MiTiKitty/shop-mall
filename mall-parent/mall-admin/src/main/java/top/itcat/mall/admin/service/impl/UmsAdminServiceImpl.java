package top.itcat.mall.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import top.itcat.mall.entity.UmsAdmin;
import top.itcat.mall.mapper.UmsAdminMapper;
import top.itcat.mall.admin.service.UmsAdminService;

/**
 * <p>
 * 后台用户表 服务实现类
 * </p>
 *
 * @author CatKitty 33641
 * @since 2023-06-04
 */
@Service
public class UmsAdminServiceImpl extends ServiceImpl<UmsAdminMapper, UmsAdmin> implements UmsAdminService {

    @Override
    public UserDetails loadUserByUsername(String username) {

        return null;
    }
}
