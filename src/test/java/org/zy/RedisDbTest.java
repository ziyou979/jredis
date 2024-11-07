package org.zy;

import org.junit.jupiter.api.Test;
import org.zy.datatype.RedisString;
import org.zy.redis.RedisDb;

/**
 * <p>
 *
 * </p>
 *
 * @author ZPY
 * @date 2023/6/13 15:47
 */
public class RedisDbTest {
    @Test
    public void cacheTest() throws InterruptedException {
        RedisDb redisDb = new RedisDb();
        for (int i = 0; i < 100; i++) {
            redisDb.put("key" + i, new RedisString("value" + i, System.currentTimeMillis() + 3000));
        }
        System.out.println(redisDb.keys());
        System.out.println(redisDb.exists("key33"));
        System.out.println(redisDb.get("key50"));
        System.out.println(redisDb.del("key40", "key50"));
        redisDb.put("key1", new RedisString("永不过期"));
        Thread.sleep(3000L);
        System.out.println(redisDb.keys());
        redisDb.flushDb();
        System.out.println(redisDb.keys());
    }
}
