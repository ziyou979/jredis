package org.zy.command;

import lombok.Getter;
import org.zy.command.impl.*;
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
    INFO("info", "获取Redis信息", Info::new),
    PING("ping", "PING", Ping::new),
    SCAN("scan", "DB扫描", Scan::new),

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
