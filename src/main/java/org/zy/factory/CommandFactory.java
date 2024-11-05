package org.zy.factory;

import lombok.extern.slf4j.Slf4j;
import org.zy.command.CommandType;
import org.zy.command.RedisCommand;
import org.zy.exception.JredisException;
import org.zy.resp.data.Array;
import org.zy.resp.data.BulkString;
import org.zy.resp.data.Resp;
import org.zy.resp.data.SimpleString;
import org.zy.util.ArrayUtil;
import org.zy.util.ObjectUtil;
import org.zy.util.TraceUtil;

import java.util.Locale;
import java.util.Objects;

@Slf4j
public class CommandFactory {

    public static RedisCommand valueOf(Array array) {
        checkResp(array);
        Resp[] content = array.getContent();
        BulkString bulkString = ObjectUtil.cast(ArrayUtil.getFirst(content));
        if (Objects.isNull(bulkString)) {
            log.error("请求命令结构非法：{}，traceId：{}", array,TraceUtil.getTraceId());
            throw JredisException.invalidCommand(array.toString());
        }

        // 不区分命令大小写
        String commandCode = bulkString.getContent().toLowerCase(Locale.ROOT);
        RedisCommand redisCommand = CommandType.getByCode(commandCode).newInstance();
        redisCommand.setContent(content);
        return redisCommand;
    }

    public static RedisCommand valueOf(SimpleString string) {
        checkResp(string);
        String commandCode = string.getContent().toLowerCase(Locale.ROOT);
        return CommandType.getByCode(commandCode).newInstance();
    }


    private static void checkResp(Resp resp) {
        if (Objects.isNull(resp)) {
            log.error("请求命令为空，traceId：{}", TraceUtil.getTraceId());
            throw JredisException.invalidCommand(null);
        }
    }
}
