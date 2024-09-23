package org.zy;

import io.netty.bootstrap.ServerBootstrap;
import lombok.extern.slf4j.Slf4j;
import org.zy.redis.RedisDb;

/**
 * <p>
 * Redis服务入口
 * </p>
 *
 * @author ZPY
 * @date 2023/6/8 21:04
 */
@Slf4j
public class RedisApplication {

    private final RedisDb redisDb = new RedisDb();

    private final ServerBootstrap serverBootstrap = new ServerBootstrap();


    public static void main(String[] args) {
        System.out.println("Hello world!");
    }
}
