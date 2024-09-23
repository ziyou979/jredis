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
    CommandType type();

    void setContent(Resp[] resp);

    void handle(ChannelHandlerContext ctx, RedisDb redisDb);
}
