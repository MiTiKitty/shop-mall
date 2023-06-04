package top.itcat.mall.common.service.impl;

import cn.hutool.core.collection.CollUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import top.itcat.mall.common.service.RedisService;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @className: RedisServiceImpl <br/>
 * @description: redis服务实现类 <br/>
 * @author: CatKitty 33641 <br/>
 * @date: 2023/06/04 <br/>
 * @version: 1.0.0 <br/>
 */
public class RedisServiceImpl implements RedisService {

    @Autowired
    private StringRedisTemplate template;

    @Override
    public void vSet(String key, String value) {
        template.opsForValue().set(key, value);
    }

    @Override
    public void vSet(String key, String value, long time, TimeUnit timeUnit) {
        template.opsForValue().set(key, value, time, timeUnit);
    }

    @Override
    public String vGet(String key) {
        return template.opsForValue().get(key);
    }

    @Override
    public Long del(String... keys) {
        return template.delete(CollUtil.toList(keys));
    }

    @Override
    public Long getExpire(String key) {
        return template.getExpire(key);
    }

    @Override
    public Boolean hasKey(String key) {
        return template.hasKey(key);
    }

    @Override
    public Long incr(String key, Long value) {
        return template.opsForValue().increment(key, value);
    }

    @Override
    public Long decr(String key, Long value) {
        return template.opsForValue().decrement(key, value);
    }

    @Override
    public void hSet(String hashKey, String key, String value) {
        template.opsForHash().put(hashKey, key, value);
    }

    @Override
    public void hSet(String hashKey, Map<String, String> jsonMap) {
        template.opsForHash().putAll(hashKey, jsonMap);
    }

    @Override
    public Object hGet(String hashKey, String key) {
        return template.opsForHash().get(hashKey, key);
    }

    @Override
    public Map<Object, Object> hGet(String hahKey) {
        return template.opsForHash().entries(hahKey);
    }

    @Override
    public void hDel(String hashKey, String... keys) {
        template.opsForHash().delete(hashKey, keys);
    }

    @Override
    public Boolean hHasKey(String hashKey, String key) {
        return template.opsForHash().hasKey(hashKey, key);
    }

    @Override
    public Long hIncr(String hashKey, String key, Long delta) {
        return template.opsForHash().increment(hashKey, key, delta);
    }

    @Override
    public Long hDecr(String hashKey, String key, Long delta) {
        return template.opsForHash().increment(hashKey, key, -delta);
    }

    @Override
    public Set<String> sMembers(String key) {
        return template.opsForSet().members(key);
    }

    @Override
    public Long sAdd(String key, String... values) {
        return template.opsForSet().add(key, values);
    }

    @Override
    public Long sAdd(String key, Long time, TimeUnit timeUnit, String... values) {
        Long size = template.opsForSet().add(key, values);
        template.expire(key, time, timeUnit);
        return size;
    }

    @Override
    public Boolean sIsMember(String key, String value) {
        return template.opsForSet().isMember(key, value);
    }

    @Override
    public Long sSize(String key) {
        return template.opsForSet().size(key);
    }

    @Override
    public Long sRemove(String key, String... values) {
        return template.opsForSet().remove(key, values);
    }

    @Override
    public List<String> lRange(String key, long start, long end) {
        return template.opsForList().range(key, start, end);
    }

    @Override
    public Long lSize(String key) {
        return template.opsForList().size(key);
    }

    @Override
    public Object lIndex(String key, long index) {
        return template.opsForList().index(key, index);
    }

    @Override
    public Long lPush(String key, String value) {
        return template.opsForList().leftPush(key, value);
    }

    @Override
    public Long lPush(String key, String value, long time, TimeUnit timeUnit) {
        Long count = template.opsForList().leftPush(key, value);
        template.expire(key, time, timeUnit);
        return count;
    }

    @Override
    public Long lPushAll(String key, String... values) {
        return template.opsForList().leftPushAll(key, values);
    }

    @Override
    public Long lPushAll(String key, Long time, TimeUnit timeUnit, String... values) {
        Long count = template.opsForList().leftPushAll(key, values);
        template.expire(key, time, timeUnit);
        return count;
    }

    @Override
    public Long lRemove(String key, long count, String value) {
        return template.opsForList().remove(key, count, value);
    }

    @Override
    public void expire(String key, Long time, TimeUnit timeUnit) {
        template.expire(key, time, timeUnit);
    }
}
