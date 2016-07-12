package com.my.consistent_hash;

import com.google.common.base.Optional;
import com.google.common.hash.HashFunction;

import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by tianzhiyu on 16/7/13.
 */
public class ConsistentHash<T> {
    private HashFunction hashFunction;
    private int numberOfReplicas;
    private TreeMap<Integer, T> circle = new TreeMap<>();

    public ConsistentHash(HashFunction hashFunction, int numberOfReplicas, Collection<T> nodes) {
        this.hashFunction = hashFunction;
        this.numberOfReplicas = numberOfReplicas;
        nodes.forEach(this::add);
    }



    private void add(T node) {
        for (int i = 0; i < numberOfReplicas; i ++) {
            int hash = hashFunction.hashString(node.toString() + i, StandardCharsets.UTF_8).asInt();
            circle.put(hash, node);
        }
    }
    private void remove(T node) {
        for (int i = 0; i < numberOfReplicas; i ++) {
            int hash = hashFunction.hashString(node.toString() + i, StandardCharsets.UTF_8).asInt();
            circle.remove(hash);
        }
    }

    public Optional<T> getNode(Object key) {
        if (circle.isEmpty()) {
            return Optional.absent();
        }
        int hash = hashFunction.hashString(key.toString(), StandardCharsets.UTF_8).asInt();
        if (!circle.containsKey(hash)) {
            SortedMap tailMap = circle.tailMap(hash);
            hash = tailMap.isEmpty() ? circle.firstKey() : (Integer)tailMap.firstKey();
        }
        return Optional.of(circle.get(hash));

    }

}
