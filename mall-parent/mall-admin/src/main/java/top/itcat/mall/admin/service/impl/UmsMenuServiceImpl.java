package top.itcat.mall.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.itcat.mall.admin.service.UmsMenuService;
import top.itcat.mall.entity.UmsMenu;
import top.itcat.mall.mapper.UmsMenuMapper;

import java.util.List;

/**
 * @className: UmsMenuServiceImpl <br/>
 * @description: 菜单服务实现类 <br/>
 * @author: CatKitty 33641 <br/>
 * @date: 2023/06/05 <br/>
 * @version: 1.0.0 <br/>
 */
@Slf4j
@Service
public class UmsMenuServiceImpl extends ServiceImpl<UmsMenuMapper, UmsMenu> implements UmsMenuService {
    @Override
    public List<UmsMenu> listByAdminId(Long adminId) {
        return baseMapper.selectListByAdminId(adminId);
    }
}
