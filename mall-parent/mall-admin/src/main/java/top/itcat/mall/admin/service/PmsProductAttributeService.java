package top.itcat.mall.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.itcat.mall.admin.vo.ProductAttrInfo;
import top.itcat.mall.common.api.CommonPage;
import top.itcat.mall.entity.PmsProductAttribute;

import java.util.List;

/**
 * @className: PmsProductAttributeService <br/>
 * @description: 商品属性服务接口 <br/>
 * @author: CatKitty 33641 <br/>
 * @date: 2023/06/08 <br/>
 * @version: 1.0.0 <br/>
 */
public interface PmsProductAttributeService extends IService<PmsProductAttribute> {

    /**
     * 添加商品属性
     *
     * @param pmsProductAttribute
     *         商品属性对象
     * @return 成功与否
     */
    boolean create(PmsProductAttribute pmsProductAttribute);

    /**
     * 根据id集合删除属性
     *
     * @param ids
     *         id集合
     * @return 成功与否
     */
    boolean removeAttributeByIds(List<Long> ids);

    /**
     * 更新商品属性
     *
     * @param pmsProductAttribute
     *         商品属性对象
     * @return 成功与否
     */
    boolean updateAttributeById(PmsProductAttribute pmsProductAttribute);

    /**
     * 根据分类查询属性列表或参数列表
     *
     * @param cid
     *         所属分类id
     * @param type
     *         0表示属性，1表示参数
     * @param pageSize
     *         每页查询数量
     * @param pageNum
     *         当前页
     * @return 分页结果
     */
    CommonPage<PmsProductAttribute> listByPage(Long cid, Integer type, Integer pageSize, Integer pageNum);

    /**
     * 根据商品分类的id获取商品属性及属性分类
     *
     * @param pid
     *         商品分类id
     * @return 结果集
     */
    List<ProductAttrInfo> getAttrInfo(Long pid);

    /**
     * 删除缓存
     */
    void delCache();

    /**
     * 根据分类id查询其所有属性列表
     * @param categories 分类id
     * @return 属性列表
     */
    List<PmsProductAttribute> getAttrInfoByCategories(List<Long> categories);
}
