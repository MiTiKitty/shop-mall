package top.itcat.mall.common.service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @className: RedisService <br/>
 * @description: redis服务接口 <br/>
 * @author: CatKitty 33641 <br/>
 * @date: 2023/06/04 <br/>
 * @version: 1.0.0 <br/>
 */
public interface RedisService {

    /**
     * 保存key-value
     *
     * @param key
     *         key
     * @param value
     *         value
     */
    void vSet(String key, String value);

    /**
     * 保存key-value，设置过期时间
     *
     * @param key
     *         key
     * @param value
     *         value
     * @param time
     *         过期时间
     * @param timeUnit
     *         时间单位
     */
    void vSet(String key, String value, long time, TimeUnit timeUnit);

    /**
     * 通过key拿到value
     *
     * @param key
     *         key
     * @return value
     */
    String vGet(String key);

    /**
     * 删除keys
     *
     * @param keys
     *         keys
     * @return 删除条数
     */
    Long del(String... keys);

    /**
     * 获取过期时间
     *
     * @param key
     *         key
     * @return 过期时间
     */
    Long getExpire(String key);

    /**
     * 判断key是否存在
     *
     * @param key
     *         key
     * @return key是否存在
     */
    Boolean hasKey(String key);

    /**
     * 按value值进行递增
     *
     * @param key
     *         key
     * @param value
     *         递增值
     * @return 递增完之后的值
     */
    Long incr(String key, Long value);

    /**
     * 按value值进行递减
     *
     * @param key
     *         key
     * @param value
     *         递增值
     * @return 递减完之后的值
     */
    Long decr(String key, Long value);

    /**
     * 往Hash结构中存入一个键值对
     *
     * @param hashKey
     *         hashKey
     * @param key
     *         key
     * @param value
     *         value
     */
    void hSet(String hashKey, String key, String value);

    /**
     * 往Hash结构中放入多个键值对
     *
     * @param hashKey
     *         hashKey
     * @param jsonMap
     *         多个键值对
     */
    void hSet(String hashKey, Map<String, String> jsonMap);

    /**
     * 获取hash结构中的某个key的值
     *
     * @param hashKey
     *         hashKey
     * @param key
     *         key
     * @return value
     */
    Object hGet(String hashKey, String key);

    /**
     * 获取整个Hash结构的元素
     *
     * @param hahKey
     *         hashKey
     * @return 所有元素对象
     */
    Map<Object, Object> hGet(String hahKey);

    /**
     * 删除Hash结构中的某些key
     *
     * @param hashKey
     *         hashKey
     * @param keys
     *         key
     */
    void hDel(String hashKey, String... keys);

    /**
     * 判断Hash结构中是否有该属性
     *
     * @param hashKey
     *         hashKey
     * @param key
     *         key
     * @return 是否存在
     */
    Boolean hHasKey(String hashKey, String key);

    /**
     * Hash结构中属性递增
     *
     * @param hashKey
     *         hashKey
     * @param key
     *         key
     * @param delta
     *         递增值
     * @return 递增至后的值
     */
    Long hIncr(String hashKey, String key, Long delta);

    /**
     * Hash结构中属性递减
     *
     * @param hashKey
     *         hashKey
     * @param key
     *         key
     * @param delta
     *         递减值
     * @return 递减至后的值
     */
    Long hDecr(String hashKey, String key, Long delta);

    /**
     * 获取Set结构
     *
     * @param key
     *         key
     * @return 所有set成员
     */
    Set<String> sMembers(String key);

    /**
     * 向Set结构中添加属性
     *
     * @param key
     *         key
     * @param values
     *         值列表
     * @return 成功添加长度
     */
    Long sAdd(String key, String... values);

    /**
     * 向Set结构中添加属性
     *
     * @param key
     *         key
     * @param time
     *         过期时间
     * @param timeUnit
     *         时间单位
     * @param values
     *         属性列表
     * @return
     */
    Long sAdd(String key, Long time, TimeUnit timeUnit, String... values);

    /**
     * 是否为Set中的属性
     *
     * @param key
     *         key
     * @param value
     *         value
     * @return 是否为Set中的属性
     */
    Boolean sIsMember(String key, String value);

    /**
     * 获取Set结构的长度
     *
     * @param key
     *         key
     * @return 长度
     */
    Long sSize(String key);

    /**
     * 删除Set结构中的属性
     *
     * @param key
     *         key
     * @param values
     *         key列表
     * @return 删除成功个数
     */
    Long sRemove(String key, String... values);

    /**
     * 获取List结构中的属性
     *
     * @param key
     *         key
     * @param start
     *         起始位置
     * @param end
     *         结束位置
     * @return 属性列表
     */
    List<String> lRange(String key, long start, long end);

    /**
     * 获取List结构的长度
     *
     * @param key
     *         key
     * @return 长度
     */
    Long lSize(String key);

    /**
     * 根据索引获取List中的属性
     *
     * @param key
     *         key
     * @param index
     *         索引下标
     * @return 值
     */
    Object lIndex(String key, long index);

    /**
     * 向List结构中添加属性
     *
     * @param key
     *         key
     * @param value
     *         value
     * @return 添加成功个数
     */
    Long lPush(String key, String value);

    /**
     * 向List结构中添加属性，并设置过期时间
     *
     * @param key
     *         key
     * @param value
     *         值
     * @param time
     *         过期时间
     * @param timeUnit
     *         时间单位
     * @return 成功添加个数
     */
    Long lPush(String key, String value, long time, TimeUnit timeUnit);

    /**
     * 向List结构中批量添加属性
     *
     * @param key
     *         key
     * @param values
     *         值列表
     * @return 成功添加数量
     */
    Long lPushAll(String key, String... values);

    /**
     * 向List结构中批量添加属性，并设置过期时间
     *
     * @param key
     *         key
     * @param time
     *         过期时间
     * @param timeUnit
     *         时间单位
     * @param values
     *         值列表
     * @return 成功添加数量
     */
    Long lPushAll(String key, Long time, TimeUnit timeUnit, String... values);

    /**
     * 从List结构中移除属性
     *
     * @param key
     *         key
     * @param count
     *         长度
     * @param value
     *         值
     * @return 移除长度
     */
    Long lRemove(String key, long count, String value);

    /**
     * 给key设置过期时间
     *
     * @param key
     *         key
     * @param time
     *         过期时间
     * @param timeUnit
     *         时间单位
     */
    void expire(String key, Long time, TimeUnit timeUnit);

}
