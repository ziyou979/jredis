package org.zy.redis;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RedisClientInfo {

    private String clientName;

    private int db;

    /**
     * 客户端是否通过了身份验证
     */
    boolean authenticated;
}
