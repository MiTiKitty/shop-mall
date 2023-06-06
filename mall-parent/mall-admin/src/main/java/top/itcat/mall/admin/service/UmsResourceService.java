package top.itcat.mall.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.itcat.mall.common.api.CommonPage;
import top.itcat.mall.entity.UmsResource;

import java.util.List;

/**
 * <p>
 * 后台资源表 服务类
 * </p>
 *
 * @author CatKitty 33641
 * @since 2023-06-04
 */
public interface UmsResourceService extends IService<UmsResource> {

    /**
     * 获取所有资源
     *
     * @return 资源列表
     */
    List<UmsResource> listAll();

    /**
     * 根据用户id获取所有资源列表
     *
     * @param adminId
     *         用户id
     * @return 资源列表
     */
    List<UmsResource> listAllByAdminId(Long adminId);

    /**
     * 根据角色id获取资源列表
     *
     * @param roleId
     *         角色id
     * @return 资源列表
     */
    List<UmsResource> listAllByRoleId(Long roleId);

    /**
     * 分页查询资源列表
     *
     * @param categoryId
     *         分类id
     * @param nameKeyword
     *         关键词：资源名称
     * @param urlKeyword
     *         关键词：url关键词
     * @param pageSize
     *         每页查询数量
     * @param pageNum
     *         当前页
     * @return 分页查询结果
     */
    CommonPage<UmsResource> listByPage(Long categoryId,
                                       String nameKeyword,
                                       String urlKeyword,
                                       Integer pageSize,
                                       Integer pageNum);

    /**
     * 根据资源id删除资源及相关信息
     *
     * @param id
     *         资源id
     * @return 成功与否
     */
    Boolean removeResourceById(Long id);
}
