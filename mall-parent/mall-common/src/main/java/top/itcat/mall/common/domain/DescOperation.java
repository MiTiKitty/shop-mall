package top.itcat.mall.common.domain;

/**
 * @className: DescOperation <br/>
 * @description: 描述操作注解 <br/>
 * @author: CatKitty 33641 <br/>
 * @date: 2023/06/04 <br/>
 * @version: 1.0.0 <br/>
 */
public @interface DescOperation {

    String value() default "";

}
