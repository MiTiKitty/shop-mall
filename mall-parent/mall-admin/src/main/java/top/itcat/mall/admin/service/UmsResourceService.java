package top.itcat.mall.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
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

}
