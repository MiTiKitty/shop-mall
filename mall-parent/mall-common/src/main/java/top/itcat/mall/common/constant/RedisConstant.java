package top.itcat.mall.common.constant;

import java.util.concurrent.TimeUnit;

/**
 * @className: RedisConstant <br/>
 * @description: redis常量类 <br/>
 * @author: CatKitty 33641 <br/>
 * @date: 2023/06/06 <br/>
 * @version: 1.0.0 <br/>
 */
public class RedisConstant {

    /**
     * 查询菜单树键
     */
    public static final String QUERY_MENU_TREE_KEY = "itcat:mall:menu:tree";

    /**
     * 查询菜单树键存活时间
     */
    public static final Long QUERY_MENU_TREE_TIME = 7L;

    /**
     * 查询菜单树键存活时间单位
     */
    public static final TimeUnit QUERY_MENU_TREE_TIME_UNIT = TimeUnit.DAYS;

}
