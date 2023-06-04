package top.itcat.mall.security.annotation;

import java.lang.annotation.*;

/**
 * @className: CacheException <br/>
 * @description: 缓存异常注解 <br/>
 * @author: CatKitty 33641 <br/>
 * @date: 2023/06/04 <br/>
 * @version: 1.0.0 <br/>
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CacheException {
}
