package top.itcat.mall.security.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @className: SpringUtil <br/>
 * @description: spring工具类 <br/>
 * @author: CatKitty 33641 <br/>
 * @date: 2023/06/04 <br/>
 * @version: 1.0.0 <br/>
 */
@Component
public class SpringUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @Override
    public void setApplicationContext(org.springframework.context.ApplicationContext applicationContext) throws BeansException {
        if (SpringUtil.applicationContext == null) {
            synchronized (Objects.requireNonNull(SpringUtil.applicationContext)) {
                if (SpringUtil.applicationContext == null) {
                    SpringUtil.applicationContext = applicationContext;
                }
            }
        }
    }

    /**
     * 通过名称获取bean
     *
     * @param name
     *         名称
     * @return bean对象
     */
    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }

    /**
     * 通过类型获取bean对象
     *
     * @param clazz
     *         类型
     * @param <T>
     *         泛型
     * @return bean对象
     */
    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

    /**
     * 根据名称和类型获取bean对象
     *
     * @param name
     *         名称
     * @param clazz
     *         类型
     * @param <T>
     *         泛型
     * @return bean对象
     */
    public static <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }
}
