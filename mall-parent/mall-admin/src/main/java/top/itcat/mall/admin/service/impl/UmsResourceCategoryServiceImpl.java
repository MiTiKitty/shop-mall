package top.itcat.mall.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.itcat.mall.admin.service.UmsResourceCategoryService;
import top.itcat.mall.entity.UmsResourceCategory;
import top.itcat.mall.mapper.UmsResourceCategoryMapper;

/**
 * @className: UmsResourceCategoryServiceImpl <br/>
 * @description: 资源分了i服务实现类 <br/>
 * @author: CatKitty 33641 <br/>
 * @date: 2023/06/06 <br/>
 * @version: 1.0.0 <br/>
 */
@Slf4j
@Service
public class UmsResourceCategoryServiceImpl extends ServiceImpl<UmsResourceCategoryMapper, UmsResourceCategory> implements UmsResourceCategoryService {

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean removeCategoryById(Long id) {
        // 先删自己
        int delete = baseMapper.deleteById(id);
        return delete > 0;
    }
}
