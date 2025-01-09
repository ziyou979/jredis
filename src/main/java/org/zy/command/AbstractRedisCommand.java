package org.zy.command;

import org.zy.exception.JredisException;
import org.zy.resp.data.BulkString;
import org.zy.resp.data.Resp;
import org.zy.util.ObjectUtil;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

public abstract class AbstractRedisCommand implements RedisCommand {

    protected Resp[] resp;

    @Override
    public void setContent(Resp[] resp) {
        if (Objects.isNull(resp)) {
            throw JredisException.invalidCommand(null);
        }
        this.resp = resp;
    }

    protected String getCommandInfo(int index) {
        return getBulkString(resp[index]);
    }

    /**
     * 获取命令参数，默认从索引0开始
     * @param start 起始索引，从 0 开始
     * @return 命令参数数组
     */
    protected String[] getCommandInfos(int start) {
        return Arrays.stream(resp).skip(start).map(this::getBulkString).toArray(String[]::new);
    }

    protected String[] getCommandInfos(int start, int end) {
        return Stream.of(resp).skip(start).limit(end - start).map(this::getBulkString).toArray(String[]::new);
    }

    protected final String getBulkString(Resp resp) {
        // 越界异常交给后续编码器处理
        BulkString bulkString = ObjectUtil.cast(resp);
        if (Objects.isNull(bulkString)) {
            throw JredisException.invalidArgument(type().getCode());
        }
        return bulkString.getContent();
    }
}
