package com.study.easyclient.hashfunction;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Collection;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * http://www.tom-e-white.com/2007/11/consistent-hashing.html
 * @author jixu
 */
public class ConsistentHash<T> {
    private final int numOfReplicas;
    private final MessageDigest messageDigest;
    private final SortedMap<BigInteger, T> circle = new TreeMap<>();

    public ConsistentHash(int numOfReplicas, Collection<T> nodes) {
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            this.numOfReplicas = numOfReplicas;
            for (T node : nodes) {
                add(node);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public void add(T node) {
        for (int i = 0; i < numOfReplicas; ++i) {
            BigInteger hash = new BigInteger(messageDigest.digest((node.toString() + i).getBytes()));
            circle.put(hash, node);
        }
    }

    public void remove(T node) {
        for (int i = 0; i < numOfReplicas; ++i) {
            BigInteger hash = new BigInteger(messageDigest.digest((node.toString() + i).getBytes()));
            circle.remove(hash);
        }
    }

    public void clear() {
        circle.clear();
    }

    public T get(String key) {
        if (circle.isEmpty()) {
            return null;
        }

        BigInteger hash = new BigInteger(messageDigest.digest(key.getBytes()));
        if (!circle.containsKey(hash)) {
            SortedMap<BigInteger, T> tailMap = circle.tailMap(hash);
            hash = tailMap.isEmpty() ? circle.firstKey() : tailMap.firstKey();
        }
        return circle.get(hash);
    }
}
