package cn.sea.shiro.cache;

import cn.sea.utils.ApplicationContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.Collection;
import java.util.Set;

/**
 * 自定义redis缓存的实现
 * @param <K>
 * @param <V>
 */

@Slf4j
public class RedisCache<K,V> implements Cache<K,V> {

    // 认证或授权缓存的名字
    private String cacheName;

    public RedisCache() {
    }

    public RedisCache(String cacheName) {
        this.cacheName = cacheName;
    }

    // 根据key获取数据
    @Override
    public V get(K k) throws CacheException {

        log.info("RedisCache[ get value = " + k);
        //return (V) getRedisTemplate().opsForValue().get(k.toString());
        return (V) getRedisTemplate().opsForHash().get(cacheName, k.toString());
    }

    // 存放键值对数据
    @Override
    public V put(K k, V v) throws CacheException {

        log.info("RedisCache[ put key = " + k);
        log.info("RedisCache[ put value = " + v);
        //getRedisTemplate().opsForValue().set(k.toString(), v);
        getRedisTemplate().opsForHash().put(cacheName, k.toString(), v);
        return null;
    }

    // 删除cacheName（hash类型）中指定filed的数据
    @Override
    public V remove(K k) throws CacheException {

        log.info("============ remove ============");
        return (V) getRedisTemplate().opsForHash().delete(this.cacheName, k.toString());
    }

    // 清空指定key的hash缓存
    @Override
    public void clear() throws CacheException {

        log.info("-------------- clear -----------------");
        getRedisTemplate().delete(this.cacheName);
    }

    // 计算指定key（hash类型）中数据的长度（即filed的长度）
    @Override
    public int size() {
        return getRedisTemplate().opsForHash().size(this.cacheName).intValue();
    }

    // 获取所有key
    @Override
    public Set<K> keys() {
        return getRedisTemplate().opsForHash().keys(this.cacheName);
    }

    // 获取所有value
    @Override
    public Collection<V> values() {
        return getRedisTemplate().opsForHash().values(this.cacheName);
    }

    private RedisTemplate getRedisTemplate() {

        RedisTemplate redisTemplate = (RedisTemplate) ApplicationContextUtils.getBean("redisTemplate");
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        return redisTemplate;
    }
}
