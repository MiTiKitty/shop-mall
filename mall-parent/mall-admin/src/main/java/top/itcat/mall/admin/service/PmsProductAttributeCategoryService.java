package top.itcat.mall.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.itcat.mall.admin.vo.PmsProductAttributeCategoryNode;
import top.itcat.mall.common.api.CommonPage;
import top.itcat.mall.entity.PmsProductAttributeCategory;

import java.util.Collection;
import java.util.List;

/**
 * @className: PmsProductAttributeCategoryService <br/>
 * @description: 商品属性分类服务接口 <br/>
 * @author: CatKitty 33641 <br/>
 * @date: 2023/06/08 <br/>
 * @version: 1.0.0 <br/>
 */
public interface PmsProductAttributeCategoryService extends IService<PmsProductAttributeCategory> {

    /**
     * 分页获取商品属性分类列表
     *
     * @param pageSize
     *         每页查询数量
     * @param pageNum
     *         当前页
     * @return 分页结果
     */
    CommonPage<PmsProductAttributeCategory> listByPage(Integer pageSize, Integer pageNum);

    /**
     * 根据id集合获取所有属性分类
     *
     * @param ids
     *         id集合
     * @return 属性分类集合
     */
    List<PmsProductAttributeCategory> getByIds(Collection<Long> ids);

    /**
     * 获取所有商品属性分类及其下属性
     *
     * @return 分类列表
     */
    List<PmsProductAttributeCategoryNode> getListWithAttr();

    /**
     * 删除缓存
     */
    void delCache();

    /**
     * 根据id删除属性分类
     *
     * @param id
     *         id
     * @return 成功与否
     */
    boolean removeCategoryById(Long id);
}
