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

    /**
     * 查询用户信息key前缀
     */
    public static final String QUERY_ADMIN_INFO_KEY = "itcat:mall:admin:info:";

    /**
     * 查询用户信息存活时间
     */
    public static final Long QUERY_ADMIN_INFO_TIME = 1L;

    /**
     * 查询用户信息存活时间单位
     */
    public static final TimeUnit QUERY_ADMIN_INFO_TIME_UNIT = TimeUnit.DAYS;

    /**
     * 查询所有资源key
     */
    public static final String QUERY_RESOURCE_ALL_KEY = "itcat:mall:resource:all";

    /**
     * 查询所有资源存活时间
     */
    public static final Long QUERY_RESOURCE_ALL_TIME = 3L;

    /**
     * 查询所有资源存活时间单位
     */
    public static final TimeUnit QUERY_RESOURCE_ALL_TIME_UNIT = TimeUnit.DAYS;

    /**
     * 查询角色所有资源key前缀
     */
    public static final String QUERY_RESOURCE_BY_ROLE_KEY = "itcat:mall:resource:role:";

    /**
     * 查询所有资源存活时间
     */
    public static final Long QUERY_RESOURCE_BY_ROLE_TIME = 3L;

    /**
     * 查询所有资源存活时间单位
     */
    public static final TimeUnit QUERY_RESOURCE_BY_ROLE_TIME_UNIT = TimeUnit.DAYS;

    /**
     * 查询所有角色key
     */
    public static final String QUERY_ROLE_ALL_KEY = "itcat:mall:qole:all";

    /**
     * 查询所有角色存活时间
     */
    public static final long QUERY_ROLE_ALL_TIME = 3L;

    /**
     * 查询所有角色存活时间单位
     */
    public static final TimeUnit QUERY_ROLE_ALL_TIME_UNIT = TimeUnit.DAYS;
}
