package top.itcat.mall.admin.bo;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import top.itcat.mall.entity.UmsAdmin;
import top.itcat.mall.entity.UmsResource;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @className: AdminUserDetails <br/>
 * @description: Security所需的用户信息封装类 <br/>
 * @author: CatKitty 33641 <br/>
 * @date: 2023/06/05 <br/>
 * @version: 1.0.0 <br/>
 */
public class AdminUserDetails implements UserDetails {

    /**
     * 后台用户
     */
    private UmsAdmin umsAdmin;

    /**
     * 拥有的资源列表
     */
    private List<UmsResource> resources;

    public AdminUserDetails(UmsAdmin umsAdmin, List<UmsResource> resources) {
        this.umsAdmin = umsAdmin;
        this.resources = resources;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return resources.stream()
                .map(role -> new SimpleGrantedAuthority(role.getId() + ":" + role.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return umsAdmin.getPassword();
    }

    @Override
    public String getUsername() {
        return umsAdmin.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return umsAdmin.getStatus().equals(1);
    }
}
