package org.zy.command;

import org.zy.exception.JredisException;
import org.zy.resp.data.BulkString;
import org.zy.resp.data.Resp;
import org.zy.util.ObjectUtil;

import java.util.Objects;

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
        // 越界异常交给后续编码器处理
        BulkString bulkString = ObjectUtil.cast(resp[index]);
        if (Objects.isNull(bulkString)) {
            throw JredisException.invalidArgument(type().getCode());
        }
        return bulkString.getContent();
    }
}
