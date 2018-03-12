package com.study.design_pattern.prototype.old2;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by tianyuzhi on 18/3/9.
 */
public class PrototypeManager {
    protected static ConcurrentHashMap<String, Prototype> map = new ConcurrentHashMap<>();

    private PrototypeManager() {}

    public synchronized static void setPrototype(String prototypeId, Prototype prototype) {
        map.put(prototypeId, prototype);
    }

    public synchronized static void removePrototype(String prototypeId) {
        map.remove(prototypeId);
    }

    public synchronized static Prototype getPrototype(String prototypeId) {
        Prototype prototype = map.get(prototypeId);
        if (prototype == null) {
            throw new RuntimeException(prototypeId + ": not found");
        }
        return prototype;
    }
}
