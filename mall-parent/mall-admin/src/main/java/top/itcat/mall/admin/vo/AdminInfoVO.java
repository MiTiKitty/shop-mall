package top.itcat.mall.admin.vo;

import lombok.Data;
import top.itcat.mall.entity.UmsMenu;

import java.util.List;

/**
 * @className: AdminInfoVO <br/>
 * @description: 用户详情信息 <br/>
 * @author: CatKitty 33641 <br/>
 * @date: 2023/06/05 <br/>
 * @version: 1.0.0 <br/>
 */
@Data
public class AdminInfoVO {

    private String username;

    public String icon;

    public List<String> roles;

    public List<UmsMenu> menus;

}
