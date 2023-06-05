package top.itcat.mall.security.component;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Iterator;

/**
 * @className: DynamicAccessDecisionManager <br/>
 * @description: 动态权限决策管理器，用于判断用户是否有访问权限 <br/>
 * @author: CatKitty 33641 <br/>
 * @date: 2023/06/05 <br/>
 * @version: 1.0.0 <br/>
 */
public class DynamicAccessDecisionManager implements AccessDecisionManager {
    @Override
    public void decide(Authentication authentication,
                       Object object,
                       Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        // 当接口未配置权限时，直接放行
        if (CollUtil.isEmpty(configAttributes)) {
            return;
        }

        // 将访问所需资源与用户所有资源进行比对
        for (ConfigAttribute attribute : configAttributes) {
            String needAuth = attribute.getAttribute();
            for (GrantedAuthority authority : authentication.getAuthorities()) {
                // 权限存在，放行
                if (StrUtil.equals(needAuth.trim(), authority.getAuthority())) {
                    return;
                }
            }
        }

        // 无权限匹配，抛出权限异常
        throw new AccessDeniedException("抱歉， 您没有访问权限!");
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
