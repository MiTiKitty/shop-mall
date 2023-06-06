package top.itcat.mall.admin.vo;

import lombok.Data;
import top.itcat.mall.entity.UmsMenu;

import java.util.List;

/**
 * @className: UmsMenuNode <br/>
 * @description: 菜单节点封装 <br/>
 * @author: CatKitty 33641 <br/>
 * @date: 2023/06/06 <br/>
 * @version: 1.0.0 <br/>
 */
@Data
public class UmsMenuNode extends UmsMenu {

    /**
     * 子菜单
     */
    private List<UmsMenuNode> children;

}
