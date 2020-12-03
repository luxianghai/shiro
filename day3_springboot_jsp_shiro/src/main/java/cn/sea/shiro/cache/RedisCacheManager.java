package cn.sea.shiro.cache;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;

/**
 * 自定义shiro缓存管理器（与redis集成）
 *      需要实现 org.apache.shiro.cache.CacheManager 接口，并重新方法
 */

@Slf4j
public class RedisCacheManager implements CacheManager {

    /**
     * @param cacheName  认证或授权缓存的名字
     * @param <K>
     * @param <V>
     * @return
     * @throws CacheException
     */
    @Override
    public <K, V> Cache<K, V> getCache(String cacheName) throws CacheException {

        log.info("RedisCacheManager[ cacheName = " + cacheName);
        return new RedisCache<K,V>(cacheName);
    }
}
