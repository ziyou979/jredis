package org.zy.datatype;

import lombok.ToString;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * <p>
 * Redis List底层的数据，基于Java的LinkedList实现
 * </p>
 *
 * @author ZPY
 * @date 2023/6/12 15:00
 */
@ToString
public class RedisList extends RedisData {

    private final LinkedList<String> list;

    public RedisList(LinkedList<String> list) {
        this(list, -1L);
    }

    public RedisList(LinkedList<String> list, long timeout) {
        this.list = new LinkedList<>();
        this.timeout = timeout;
    }

    /* LPUSH <key> <element> [<element> ...] */
    public long lPush(String... elements) {
        for (String element : elements) {
            list.addFirst(element);
        }
        return elements.length;
    }

    /* RPUSH <key> <element> [<element> ...] */
    public long rPush(String... elements) {
        for (String element : elements) {
            list.addLast(element);
        }
        return elements.length;
    }

    /* LRANGE <key> <start> <stop> */
    public List<String> lRange(long start, long stop) {
        return list.subList((int) start, (int) stop);
    }

    /* LPOP <key> */
    public String lPop() {
        return list.pollFirst();
    }

    /* LPOP <key> count */
    public List<String> lPop(long count) {
        List<String> pop = new ArrayList<>((int) count);
        for (int i = 0; i < count; i++) {
            pop.add(list.pollFirst());
        }
        return pop;
    }

    /* RPOP <key> */
    public String rPop() {
        return list.pollLast();
    }

    /* RPOP <key> count */
    public List<String> rPop(long count) {
        List<String> pop = new ArrayList<>((int) count);
        for (int i = 0; i < count; i++) {
            pop.add(list.pollLast());
        }
        return pop;
    }

}
