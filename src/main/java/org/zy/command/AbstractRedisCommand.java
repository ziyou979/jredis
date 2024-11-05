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

    public String getCommandInfo(int index) {
        index = Math.min(index, resp.length);
        BulkString bulkString = ObjectUtil.cast(resp[index]);
        if (Objects.isNull(bulkString)) {
            throw JredisException.invalidArgument(type().getCode());
        }
        return bulkString.getContent();
    }
}
