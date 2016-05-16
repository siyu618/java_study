package com.study.guava;

import java.util.Iterator;
import java.util.Set;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

/**
 * Created by tianyuzhi on 16/5/16.
 */
public class MultiSetTest {
    public static void main(String[] args)
    {
        Multiset<String>  multiset = HashMultiset.create();
        multiset.add("a");
        multiset.add("b");
        multiset.add("c");
        multiset.add("d");
        multiset.add("a");
        multiset.add("b");
        multiset.add("c");
        multiset.add("b");
        multiset.add("b");
        multiset.add("b");
        System.out.println("Occurrence of 'b':" + multiset.count("b"));

        System.out.println("Total size: " + multiset.size());

        Set<String> set = multiset.elementSet();
        System.out.println("Set [");
        for (String str : set) {
            System.out.println(str);
        }
        System.out.println("]");

        Iterator<String> iterator = multiset.iterator();
        System.out.println("MultiSet [");
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
        System.out.println("]");

        System.out.println("MultiSet [");
        for (Multiset.Entry<String> entry : multiset.entrySet()) {
            System.out.println("Element: " + entry.getElement() + ", occurrence(s) : " + entry.getCount());
        }
        System.out.println("]");
        multiset.remove("b", 2);
        System.out.println("Occurrence of b:" + multiset.count("b"));
    }
}
