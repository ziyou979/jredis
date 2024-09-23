package org.zy.redis;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Expiry;
import org.checkerframework.checker.index.qual.NonNegative;
import org.zy.datatype.RedisData;

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *
 * </p>
 *
 * @author ZPY
 * @date 2023/6/13 10:20
 */
public class RedisDb {
    /**
     * 利用Caffeine作为底层哈希表，提高性能
     */
    private final Cache<String, RedisData> dist = Caffeine.newBuilder()
            .expireAfter(new Expiry<String , RedisData>() {
                @Override
                public long expireAfterCreate(String key, RedisData redisData, long currentTime) {
                    long timeout = redisData.getTimeout();
                    if (timeout == -1L) {
                        // 永不过期则过期时间设为long的最大值，即292年
                        return Long.MAX_VALUE;
                    }
                    return TimeUnit.MILLISECONDS.toNanos(timeout - System.currentTimeMillis());
                }

                @Override
                public long expireAfterUpdate(String key, RedisData redisData, long currentTime, @NonNegative long currentDuration) {
                    long timeout = redisData.getTimeout();
                    if (timeout == -1L) {
                        return Long.MAX_VALUE; // 永不过期则过期时间设为long的最大值，即292年
                    }
                    return TimeUnit.MILLISECONDS.toNanos(timeout - System.currentTimeMillis());
                }

                @Override
                public long expireAfterRead(String key, RedisData redisData, long currentTime, @NonNegative long currentDuration) {
                    return currentDuration;
                }
            })
            .maximumSize(Integer.MAX_VALUE) // 单个数据库最多可存放2^32个key
            .build();

    public Set<String> keys() {
        return dist.asMap().keySet();
    }

    public long exits(String... key) {
        if (Objects.isNull(key) || key.length == 0) {
            return 0L;
        }
        if (key.length == 1) {
            return Objects.isNull(dist.getIfPresent(key[0])) ? 0L : 1L;
        }
        return dist.getAllPresent(Arrays.asList(key)).size();
    }

    public void put(String key, RedisData value) {
        dist.put(key, value);
    }

    public RedisData get(String key) {
        return dist.getIfPresent(key);
    }

    public long del(String... key) {
        long count = exits(key);
        if (count != 0L) {
            dist.invalidateAll(Arrays.asList(key));
        }
        return count;
    }

    public void flushDb() {
        dist.invalidateAll();
    }


}
