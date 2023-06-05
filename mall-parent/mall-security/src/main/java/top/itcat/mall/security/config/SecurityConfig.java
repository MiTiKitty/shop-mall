package top.itcat.mall.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import top.itcat.mall.security.component.*;

import javax.annotation.Resource;

/**
 * @className: SecurityConfig <br/>
 * @description: security配置类 <br/>
 * @author: CatKitty 33641 <br/>
 * @date: 2023/06/04 <br/>
 * @version: 1.0.0 <br/>
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Resource
    private IgnoreUrlsConfig ignoreUrlsConfig;

    @Resource
    private RestfulAccessDeniedHandler restfulAccessDeniedHandler;

    @Resource
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Resource
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Autowired(required = false)
    private DynamicSecurityService dynamicSecurityService;

    @Autowired(required = false)
    private DynamicSecurityFilter dynamicSecurityFilter;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity security) throws Exception {
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry urlRegistry = security
                .authorizeRequests();
        // 允许访问不需要进行保护的资源,(白名单放行)
        for (String url : ignoreUrlsConfig.getUrls()) {
            urlRegistry.antMatchers(url).permitAll();
        }

        // 允许跨域请求的 OPTIONS 请求
        urlRegistry.antMatchers(HttpMethod.OPTIONS).permitAll();

        // 其余全部需要进行身份验证
        urlRegistry.and()
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                // 关闭跨站请求防护并且关闭session
                .and()
                .csrf()
                .disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                // 设置自定义权限处理类
                .and()
                .exceptionHandling()
                .accessDeniedHandler(restfulAccessDeniedHandler)
                .authenticationEntryPoint(restAuthenticationEntryPoint)
                // 设置自定义权限拦截jwt过滤器
                .and()
                .addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

        // 有动态权限配置时添加动态权限校验过滤器
        if (dynamicSecurityService != null) {
            urlRegistry.and().addFilterBefore(dynamicSecurityFilter, FilterSecurityInterceptor.class);
        }
        return security.build();
    }

}
