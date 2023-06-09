package top.itcat.mall.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.itcat.mall.admin.dto.PmsProductCategoryNode;
import top.itcat.mall.admin.dto.PmsProductCategoryParam;
import top.itcat.mall.common.api.CommonPage;
import top.itcat.mall.entity.PmsProductCategory;

import java.util.List;

/**
 * @className: PmsProductCategoryService <br/>
 * @description: 商品分类服务接口 <br/>
 * @author: CatKitty 33641 <br/>
 * @date: 2023/06/08 <br/>
 * @version: 1.0.0 <br/>
 */
public interface PmsProductCategoryService extends IService<PmsProductCategory> {

    /**
     * 修改商品分类显示状态
     *
     * @param ids
     *         id集合
     * @param showStatus
     *         显示状态
     * @return 成功与否
     */
    Boolean updateShowStatus(List<Long> ids, Integer showStatus);

    /**
     * 根据id删除商品分类
     *
     * @param id
     *         id
     * @return 成功与否
     */
    boolean removeCategoryById(Long id);

    /**
     * 分页查询商品分类
     *
     * @param parentId
     *         分类父id
     * @param pageSize
     *         每页查询数量
     * @param pageNum
     *         当前页
     * @return 分页结果
     */
    CommonPage<PmsProductCategory> listByPage(Long parentId, Integer pageSize, Integer pageNum);

    /**
     * 以树状形式查询所有商品分类
     *
     * @return 分类树
     */
    List<PmsProductCategoryNode> getTree();

    /**
     * 删除缓存
     */
    void delCache();

    /**
     * 添加商品分类
     *
     * @param param
     *         参数对象
     * @return 成功与否
     */
    boolean create(PmsProductCategoryParam param);

    /**
     * 修改商品分类
     *
     * @param param
     *         参数对象
     * @param id id
     * @return 成功与否
     */
    boolean updateCategoryById(Long id, PmsProductCategoryParam param);
}
