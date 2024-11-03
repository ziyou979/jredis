package org.zy.command;

import io.netty.channel.ChannelHandlerContext;
import org.zy.redis.RedisDb;
import org.zy.resp.data.Resp;

/**
 * <p>
 *
 * </p>
 *
 * @author ZPY
 * @date 2023/6/13 15:38
 */
public interface RedisCommand {
    /**
     * 命令类型
     */
    CommandType type();

    /**
     * 写入resp内容
     */
    void setContent(Resp[] resp);

    void handle(ChannelHandlerContext ctx, RedisDb redisDb);

    /**
     * 是否需要记录到aof文件
     */
    default boolean aof() {
        return false;
    }
}
