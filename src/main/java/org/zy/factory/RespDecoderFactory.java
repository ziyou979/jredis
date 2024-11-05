package org.zy.factory;

import io.netty.buffer.ByteBuf;
import org.zy.resp.RespType;
import org.zy.resp.data.Error;
import org.zy.resp.data.Integer;
import org.zy.resp.data.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

/**
 * <p>
 * Resp编解码工厂，功能类似于resp_parser.c
 * </p>
 *
 * @author ZPY
 * @date 2023/6/11 11:15
 */
public class RespDecoderFactory {
    private static final Map<Byte, Supplier<Resp>> DECODERS = new ConcurrentHashMap<>();

    static {
        DECODERS.put(RespType.SIMPLE_STRING.getCode(), SimpleString::new);
        DECODERS.put(RespType.ERROR.getCode(), Error::new);
        DECODERS.put(RespType.INTEGER.getCode(), Integer::new);
        DECODERS.put(RespType.BULK_STRINGS.getCode(), BulkString::new);
        DECODERS.put(RespType.ARRAY.getCode(), Array::new);
    }

    public static Resp decode(ByteBuf buffer) {
        byte respType = buffer.readByte();
        return DECODERS.getOrDefault(respType, UnsupportedProtocolError::new).get().decode(buffer);
    }
}
