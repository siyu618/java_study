package com.study.design_pattern.test;

/**
 * Created by tianyuzhi on 18/6/6.
 */


 class Prototype implements Cloneable {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }

}

 class NewPrototype implements Cloneable {

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private Prototype prototype;

    public Prototype getPrototype() {
        return prototype;
    }

    public void setPrototype(Prototype prototype) {
        this.prototype = prototype;
    }


    public Object clone(){
        NewPrototype ret = null;
        try {
            ret = (NewPrototype)super.clone();
            ret.prototype = (Prototype)this.prototype.clone();
            return ret;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }

}

public class TestMain {

    /**
     * @param args
     */
    public static void main(String[] args) {
        testDeepCopy();
    }

    private static void testDeepCopy(){
        Prototype pro = new Prototype();
        pro.setName("original object");
        NewPrototype newObj = new NewPrototype();
        newObj.setId("test1");
        newObj.setPrototype(pro);

        NewPrototype copyObj = (NewPrototype)newObj.clone();
        copyObj.setId("testCopy");
        copyObj.getPrototype().setName("changed object");

        System.out.println("original object id:" + newObj.getId());
        System.out.println("original object name:" + newObj.getPrototype().getName());

        System.out.println("cloned object id:" + copyObj.getId());
        System.out.println("cloned object name:" + copyObj.getPrototype().getName());

    }

}
