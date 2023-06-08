package top.itcat.mall.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.itcat.mall.common.api.CommonPage;
import top.itcat.mall.entity.PmsBrand;

import java.util.List;

/**
 * @className: PmsBrandService <br/>
 * @description: 商品品牌服务接口 <br/>
 * @author: CatKitty 33641 <br/>
 * @date: 2023/06/08 <br/>
 * @version: 1.0.0 <br/>
 */
public interface PmsBrandService extends IService<PmsBrand> {

    /**
     * 获取所有品牌列表
     *
     * @return 品牌列表
     */
    List<PmsBrand> listAll();

    /**
     * 根据id删除品牌
     *
     * @param id
     *         id
     * @return 成功与否
     */
    boolean removeBrandById(Long id);

    /**
     * 批量删除品牌
     *
     * @param ids
     *         id集合
     * @return 成功与否
     */
    boolean removeBrandByIds(List<Long> ids);

    /**
     * 删除缓存
     */
    void delCache();

    /**
     * 根据品牌名称分页获取品牌列表
     *
     * @param keyword
     *         关键词：品牌名称
     * @param showStatus
     *         显示状态
     * @param pageNum
     *         当前页
     * @param pageSize
     *         每页查询数量
     * @return 分页结果
     */
    CommonPage<PmsBrand> listByPage(String keyword, Integer showStatus, Integer pageNum, Integer pageSize);

    /**
     * 批量修改品牌显示状态
     *
     * @param showStatus
     *         显示状态
     * @param ids
     *         id集合
     * @return 成功与否
     */
    boolean updateShowStatus(List<Long> ids, Integer showStatus);

    /**
     * 批量更新厂家制造商状态
     *
     * @param factoryStatus
     *         厂家制造商状态
     * @param ids
     *         id集合
     * @return 统一返回结果
     */
    boolean updateFactoryStatus(List<Long> ids, Integer factoryStatus);
}
