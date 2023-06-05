package top.itcat.mall.security.component;

import org.springframework.security.access.ConfigAttribute;

import java.util.Map;

/**
 * @className: DynamicSecurityService <br/>
 * @description: 动态权限相关业务接口 <br/>
 * @author: CatKitty 33641 <br/>
 * @date: 2023/06/05 <br/>
 * @version: 1.0.0 <br/>
 */
public interface DynamicSecurityService {

    /**
     * 加载资源ANT通配符和资源对应MAP
     *
     * @return 资源
     */
    Map<String, ConfigAttribute> loadDataSource();

}
