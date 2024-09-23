package org.zy;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;
import org.junit.jupiter.api.Test;
import org.zy.resp.RespDecoderFactory;
import org.zy.resp.data.Resp;

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
}
