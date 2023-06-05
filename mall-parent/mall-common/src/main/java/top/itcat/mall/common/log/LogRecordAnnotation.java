package top.itcat.mall.common.log;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @className: LogRecordAnnotation <br/>
 * @description:  日志描述注解 <br/>
 * @author: CatKitty 33641 <br/>
 * @date: 2023/06/05 <br/>
 * @version: 1.0.0 <br/>
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogRecordAnnotation {

    /**
     * 操作
     */
    String action() default "记录";

    /**
     * 描述
     */
    String description() default "执行了某些操作";

}
