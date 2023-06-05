package top.itcat.mall.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.itcat.mall.admin.service.UmsResourceService;
import top.itcat.mall.entity.UmsResource;
import top.itcat.mall.mapper.UmsResourceMapper;

import java.util.List;

/**
 * <p>
 * 后台资源表 服务实现类
 * </p>
 *
 * @author CatKitty 33641
 * @since 2023-06-04
 */
@Service
public class UmsResourceServiceImpl extends ServiceImpl<UmsResourceMapper, UmsResource> implements UmsResourceService {

    @Override
    public List<UmsResource> listAll() {
        return baseMapper.selectList(null);
    }

    @Override
    public List<UmsResource> listAllByAdminId(Long adminId) {
        return baseMapper.selectListByAdminId(adminId);
    }
}
