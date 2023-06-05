package top.itcat.mall.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.itcat.mall.admin.service.UmsRoleService;
import top.itcat.mall.entity.UmsRole;
import top.itcat.mall.mapper.UmsRoleMapper;

import java.util.List;

/**
 * @className: UmsRoleServiceImpl <br/>
 * @description: 角色服务实现类 <br/>
 * @author: CatKitty 33641 <br/>
 * @date: 2023/06/05 <br/>
 * @version: 1.0.0 <br/>
 */
@Slf4j
@Service
public class UmsRoleServiceImpl extends ServiceImpl<UmsRoleMapper, UmsRole> implements UmsRoleService {
    @Override
    public List<UmsRole> listByAdminId(Long adminId) {
        return baseMapper.selectListByAdminId(adminId);
    }
}
