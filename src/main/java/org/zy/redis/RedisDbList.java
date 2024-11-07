package org.zy.redis;

import io.netty.channel.Channel;
import org.zy.util.PropertiesUtil;

import java.util.ArrayList;
import java.util.Objects;

public class RedisDbList extends ArrayList<RedisDb> {

    public RedisDbList(int initialCapacity) {
        super(initialCapacity);
        // 初始化数据库列表，懒加载模式，使用时才真正实例化
        for (int i = 0; i < PropertiesUtil.getDatabases(); i++) {
            add(null);
        }
    }

    @Override
    public RedisDb get(int index) {
        RedisDb redisDb = super.get(index);
        // 懒加载模式，第一次使用才实例化
        if (Objects.isNull(redisDb)) {
            redisDb = new RedisDb();
            super.set(index, redisDb);
        }
        return redisDb;
    }

    public RedisDb get(Channel channel) {
        if (Objects.isNull(channel)) {
            return get(0);
        }
        return get(RedisServer.getClientDb(channel));
    }
}
