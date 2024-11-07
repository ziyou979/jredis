package org.zy.command;

import lombok.Getter;
import org.zy.command.impl.*;
import org.zy.command.impl.string.Get;
import org.zy.command.impl.string.Set;
import org.zy.exception.JredisException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * <p>
 *
 * </p>
 *
 * @author ZPY
 * @date 2023/6/13 15:42
 */
public enum CommandType {
    AUTH("auth", "用户鉴权", Auth::new),
    CLIENT("client", "客户端连接", Client::new),
    CONFIG("config", "配置", Config::new),
    GET("get", "获取字符串", Get::new),
    INFO("info", "获取Redis服务端基础信息", Info::new),
    PING("ping", "心跳检测", Ping::new),
    QUIT("quit", "退出", Quit::new),
    SCAN("scan", "DB扫描", Scan::new),
    SELECT("select", "切换DB", Select::new),
    SET("set", "设置字符串", Set::new),
    TTL("ttl", "获取剩余时间", Ttl::new),
    TYPE("type", "获取类型", Type::new),
    ;

    @Getter
    private final String code;

    private final Supplier<RedisCommand> command;

    private final static Map<String, CommandType> commandTypeMap = HashMap.newHashMap(values().length);

    static {
        for (CommandType commandType : values()) {
            commandTypeMap.put(commandType.code, commandType);
        }
    }


    CommandType(String code, String desc, Supplier<RedisCommand> command) {
        this.code = code;
        this.command = command;
    }

    public RedisCommand newInstance() {
        return command.get();
    }

    public static CommandType getByCode(String code) {
        CommandType commandType = commandTypeMap.get(code);
        if (Objects.isNull(commandType)) {
            throw JredisException.invalidCommand(code);
        }
        return commandType;
    }

}
