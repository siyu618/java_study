package com.study.examples;



import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * 没有考虑实现：各种接口、扩容（load factor）....
 * 冲突解决： 链式哈希
 */
public class SimpleHashMap //implements Map, ...
{
    private static final int MIN_SIZE = 16;
    private int capacity;
    private int size;
    private InnerNode[] table;

    public SimpleHashMap(int capacity) {
        if (capacity < MIN_SIZE) {
            capacity = MIN_SIZE;
        }
        this.capacity = capacity;
        size = 0;
        table = new InnerNode[capacity];
    }

    public char[] get(Long key) {
        if (null == key) {
            return null;
        }
        InnerNode node = getNode(key);
        if (null == node) {
            return null;
        } else {
            return node.value.toCharArray();
        }
    }

    public boolean put(Long key, char[] value) {
        if (null == key || null == value) {
            return false;
        }
        InnerNode node = getNode(key);
        if (null == node) {
            addNode(key, String.valueOf(value));
        } else {
            node.value = String.valueOf(value);
        }
        return true;
    }

    public boolean containsKey(Long key) {
        InnerNode node = getNode(key);
        return null != node;
    }

    /**
     * make sure the key does exist in the table before calling add...
     *
     * @param key
     * @param value
     * @return
     */
    private boolean addNode(Long key, String value) {
        int hash = hash(key);
        InnerNode node = new InnerNode(key, value);
        if (null == table[hash]) {
            table[hash] = node;
        } else {
            InnerNode head = table[hash];
            node.next = head;
            head.pre = node;
            table[hash] = node;
            size++;
        }
        return true;

    }

    private InnerNode getNode(Long key) {
        if (null == key) {
            return null;
        }
        int hash = hash(key);
        if (null == table[hash]) {
            return null;
        } else {
            InnerNode cur = table[hash];
            while (cur != null) {
                if (cur.key == key) {
                    break;
                }
                cur = cur.next;
            }
            return cur;
        }
    }

    private int hash(Long key) {
        return (int)(Math.abs(0x9e370001L * key) % capacity);
       // return Math.abs(key.hashCode()) % capacity;
    }


    private static class InnerNode {
        private InnerNode pre = null;
        private InnerNode next = null;
        private long key;
        private String value;

        public InnerNode(long key, String value) {
            this.key = key;
            this.value = value;
            pre = null;
            next = null;
        }
    }

    private static char[] genCharArr(int size) {
        if (size < 0) {
            return null;
        }
        int n = size;
        char[] chars = new char[n];
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            chars[i] = (char) (random.nextInt(26) + 65);
        }
        return chars;
    }

    public static void main(String[] args) {
        int capacity = 50;
        int charArrLen = 16;
        SimpleHashMap simpleHashMap = new SimpleHashMap(capacity);
        List<Long> keys = Arrays.asList(0L, 1L, 2L, 3L, 3L, 6L, -6L, -3L);
        List<Long> queryKeys = Arrays.asList(0L, 1L, 3L, -1L, -2L);

        for (Long key : keys) {
            char[] value = genCharArr(charArrLen);
            System.out.println("insert key[" + key + ", value[" + String.valueOf(value) + "]");
            simpleHashMap.put(key, value);
        }

        System.out.println("====");
        for (Long key : queryKeys) {
            char[] queryResult = simpleHashMap.get(key);
            System.out.println("query key[" + key + ", value[" + (queryResult == null ? null : String.valueOf(queryResult)) + "]");
        }

    }
}
