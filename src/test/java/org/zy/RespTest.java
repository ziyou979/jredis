package org.zy;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;
import org.junit.jupiter.api.Test;
import org.zy.factory.RespDecoderFactory;
import org.zy.redis.RedisServer;
import org.zy.resp.data.BulkString;
import org.zy.resp.data.Resp;

import java.util.Map;
import java.util.stream.Collectors;

import static org.zy.constant.CommonConstant.COLON;
import static org.zy.resp.RespType.CRLF;

/**
 * <p>
 *
 * </p>
 *
 * @author ZPY
 * @date 2023/6/10 14:35
 */
public class RespTest {
    @Test
    public void readLineTest() {
        ByteBuf in = Unpooled.copiedBuffer(":5\r\n", CharsetUtil.UTF_8);
        Resp resp = new Resp() {
            @Override
            public void encode(ByteBuf buffer) {
            }

            @Override
            public Resp decode(ByteBuf buffer) {
                return null;
            }
        };
        System.out.println(resp.readLine(in));
    }

    @Test
    public void RespCodecTest() {
        ByteBuf in = Unpooled.copiedBuffer("*5\r\n+OK\r\n-ERR unknown command 'foobar'\r\n:3\r\n???\r\n$6\r\nfoobar\r\n",
                CharsetUtil.UTF_8);
        Resp resp = RespDecoderFactory.decode(in);
        System.out.println(resp);
    }

    @Test
    public void RespEncodeTest() {
        Map<String, String> infos = Map.of(
                "redis_version", "0.0.1.java",
                "redis_git_sha1", "00000000",
                "redis_git_dirty", "0",
                "redis_build_id", "zy-java-redis",
                "redis_mode", "standalone",
                "os", RedisServer.getOsName(),
                "process_id", RedisServer.getPid(),
                "used_memory", RedisServer.getUsedHeapMemory()
        );
        String infoString = infos.entrySet().stream()
                .map(entry -> entry.getKey() + COLON + entry.getValue() + CRLF)
                .collect(Collectors.joining());
        ByteBuf buffer = Unpooled.buffer();
        new BulkString(infoString).encode(buffer);
        System.out.println(buffer.toString(CharsetUtil.UTF_8));
    }
}
