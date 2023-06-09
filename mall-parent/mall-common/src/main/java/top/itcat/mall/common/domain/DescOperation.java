package top.itcat.mall.common.domain;

import java.lang.annotation.*;

/**
 * @className: DescOperation <br/>
 * @description: 描述操作注解 <br/>
 * @author: CatKitty 33641 <br/>
 * @date: 2023/06/04 <br/>
 * @version: 1.0.0 <br/>
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DescOperation {

    String value() default "";

}
