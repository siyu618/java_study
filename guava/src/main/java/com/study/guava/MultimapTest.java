package com.study.guava;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by tianyuzhi on 16/5/15.
 */
public class MultimapTest {

    public static void main(String[] args) {
        MultimapTest test = new MultimapTest();
        Multimap<String, String> multiMap = test.genMultimap();

        List<String> lowerList = (List<String>) multiMap.get("lower");
        System.out.println("Initial lower case list");
        System.out.println(lowerList.toString());

        lowerList.add("f");
        System.out.println("Modified lower case list");
        System.out.println(lowerList.toString());

        List<String> upperList = (List<String>)multiMap.get("upper");
        System.out.println("Initial upper case list");
        System.out.println(upperList.toString());

        upperList.remove("D");
        System.out.println("Modified upper case list");
        System.out.println(upperList.toString());

        Map<String, Collection<String>> map = multiMap.asMap();
        System.out.println("Multimap as a map");
        for (Map.Entry<String, Collection<String>> entry : map.entrySet()){
            String key = entry.getKey();
            Collection<String> val = map.get(key);
            System.out.println(key + ":" + val);
        }

        System.out.println("Keys of Multimap");
        Set<String> keys = multiMap.keySet();
        for (String key : keys) {
            System.out.println(key);
        }
        System.out.println("values of multimap");
        Collection<Collection<String>> values = map.values();
        System.out.println(values);
     }


    private Multimap<String, String> genMultimap() {
        Multimap<String, String> multimap = ArrayListMultimap.create();
        multimap.put("lower", "a");
        multimap.put("lower", "b");
        multimap.put("lower", "c");
        multimap.put("lower", "d");
        multimap.put("lower", "e");

        multimap.put("upper", "A");
        multimap.put("upper", "B");
        multimap.put("upper", "C");
        multimap.put("upper", "D");
        return multimap;
    }
}
