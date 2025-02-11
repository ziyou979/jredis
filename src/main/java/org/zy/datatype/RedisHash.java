package org.zy.datatype;

import org.zy.util.ArrayUtil;

import java.util.*;

/**
 *
 * @author ZPY
 * @date 2024/9/23
 */
public class RedisHash extends RedisData {

    private final Map<String, String> map = new HashMap<>();

    /**
     * HSET key field value
     */
    public int hSet(String field, String value) {
        boolean exists = map.containsKey(field);
        boolean insert = Objects.isNull(map.put(field, value));
        return exists && insert ? 1 : 0;
    }

    /**
     * HDEL key field [field ...]
     */
    public int hDel(String... fields) {
        if (ArrayUtil.isEmpty(fields)) {
            return 0;
        }
        int count = 0;
        for (String field : fields) {
            String removedStr = map.remove(field);
            if (Objects.nonNull(removedStr)) {
                count++;
            }
        }
        return count;
    }

    /**
     * HEXISTS key field
     */
    public int hExists(String field) {
        return map.containsKey(field) ? 1 : 0;
    }

    public Collection<String> hgetall() {
        return map.values();
    }

    public String hGet(String field) {
        return map.get(field);
    }

    public long hLen() {
        return map.size();
    }

    public static void main(String[] args) {
        String[] del = new String[1000000];
        RedisHash hash = new RedisHash();
        hash.hSet("a", "1");
        System.out.println(hash.hDel("a", "b"));
    }

}
