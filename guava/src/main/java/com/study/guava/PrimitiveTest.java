package com.study.guava;

import com.google.common.primitives.Bytes;

import java.util.List;

/**
 * Created by tianyuzhi on 16/5/16.
 */
public class PrimitiveTest {
    public static void main(String[] args) {
        PrimitiveTest test = new PrimitiveTest();
        test.testBytes();
    }
    public void testBytes() {
        byte[] byteArray = {1,2,3,4,5,5,7,9,9};
        // convert array of primitives to array of objects
        List<Byte> objectArray = Bytes.asList(byteArray);

        // convert ayyray of objcets to array of primitive
        byteArray = Bytes.toArray(objectArray);
        System.out.print("[");
        for (int i = 0; i < byteArray.length ; i ++) {
            System.out.print(byteArray[i] + " ");
        }
        System.out.print("]");

        byte data = 5;
        // check if in list
        System.out.println("5 is in list:" + Bytes.contains(byteArray, data));
        // return the index
        System.out.println("index fof 5 is : " + Bytes.indexOf(byteArray, data));
        // return the last index maximum
        System.out.println("Last index of 5:" + Bytes.lastIndexOf(byteArray, data));

    }
}
