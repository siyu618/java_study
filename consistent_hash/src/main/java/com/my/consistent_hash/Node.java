package com.my.consistent_hash;

/**
 * Created by tianyuzhi on 15/9/30.
 */
public class Node {
    private String name;

    public Node(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int hashCode() {
        return name.hashCode();

    }

    public boolean equals(Object anOther) {
        if (anOther == this) {
            return true;
        }
        else {
            if (anOther instanceof  Node) {
                Node an = (Node) anOther;
                return this.name.equals(an.name);
            }
            return false;
        }
    }

    public String toString() {
        return name;
    }
}
