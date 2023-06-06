package top.itcat.mall.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.itcat.mall.entity.UmsResourceCategory;

/**
 * @className: UmsResourceCategoryService <br/>
 * @description: 资源分类服务接口 <br/>
 * @author: CatKitty 33641 <br/>
 * @date: 2023/06/06 <br/>
 * @version: 1.0.0 <br/>
 */
public interface UmsResourceCategoryService extends IService<UmsResourceCategory> {
    /**
     * 根据id删除分类
     *
     * @param id
     *         分类id
     * @return 成功与否
     */
    Boolean removeCategoryById(Long id);
}
