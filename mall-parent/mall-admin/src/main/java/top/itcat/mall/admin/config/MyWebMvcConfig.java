package top.itcat.mall.admin.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import top.itcat.mall.security.component.RateLimitInterceptor;

/**
 * @className: MyWebMvcConfig <br/>
 * @description: webMvc配置类 <br/>
 * @author: CatKitty 33641 <br/>
 * @date: 2023/05/27 <br/>
 * @version: 1.0.0 <br/>
 */
@Configuration
public class MyWebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private RateLimitInterceptor rateLimitInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(rateLimitInterceptor).addPathPatterns("/**");
    }
}
