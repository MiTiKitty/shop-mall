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
    public static final String QUERY_ADMIN_KEY = "itcat:mall:admin:account:";

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
    public static final String QUERY_ROLE_ALL_KEY = "itcat:mall:role:all";

    /**
     * 查询所有角色存活时间
     */
    public static final long QUERY_ROLE_ALL_TIME = 3L;

    /**
     * 查询所有角色存活时间单位
     */
    public static final TimeUnit QUERY_ROLE_ALL_TIME_UNIT = TimeUnit.DAYS;

    /**
     * 查询角色菜单key前缀
     */
    public static final String QUERY_MENU_BY_ROLE_ID_KEY = "itcat:mall:menu:role:";

    /**
     * 查询角色菜单存活时间
     */
    public static final long QUERY_MENU_BY_ROLE_ID_TIME = 3L;

    /**
     * 查询角色菜单存活时间单位
     */
    public static final TimeUnit QUERY_MENU_BY_ROLE_ID_TIME_UNIT = TimeUnit.DAYS;

    /**
     * 查询所有商品品牌的key
     */
    public static final String QUERY_BRAND_ALL_KEY = "itcat:mall:brand:all";

    /**
     * 查询所有商品品牌存活时间
     */
    public static final long QUERY_BRAND_ALL_TIME = 5L;

    /**
     * 查询所有商品品牌存活时间单位
     */
    public static final TimeUnit QUERY_BRAND_ALL_TIME_UNIT = TimeUnit.DAYS;

    /**
     * 查询商品分类树key
     */
    public static final String QUERY_PRODUCT_CATEGORY_TREE_KEY = "itcat:mall:product:category:tree";

    /**
     * 查询商品分类树存活时间
     */
    public static final Long QUERY_PRODUCT_CATEGORY_TREE_TIME = 7L;

    /**
     * 查询商品分类树存活时间单位
     */
    public static final TimeUnit QUERY_PRODUCT_CATEGORY_TREE_TIME_UNIT = TimeUnit.DAYS;

    /**
     * 根据商品属性分类id查询商品属性key
     */
    public static final String QUERY_PRODUCT_ATTR_INFO_BY_PID_KEY = "itcat:mall:product:attributes:";

    /**
     * 根据商品属性分类id查询商品属性存活时间单位
     */
    public static final TimeUnit QUERY_PRODUCT_ATTR_INFO_BY_PID_TIME_UNIT = TimeUnit.SECONDS;

    /**
     * 查询所有分类及其属性列表key
     */
    public static final String QUERY_PRODUCT_ATTR_CATEGORY_KEY = "itcat:mall:attribute:category:all";

    /**
     * 查询所有分类及其属性列表存活时间
     */
    public static final Long QUERY_PRODUCT_ATTR_CATEGORY_TIME = 1L;

    /**
     * 查询所有分类及其属性列表存活时间单位
     */
    public static final TimeUnit QUERY_PRODUCT_ATTR_CATEGORY_TIME_UNIT = TimeUnit.DAYS;
}
