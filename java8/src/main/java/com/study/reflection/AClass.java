package com.study.reflection;

/**
 * Created by tianyuzhi on 17/3/27.
 */
public class AClass {
    private String getPrivate() {
        return "privateMethodWithOutParams";
    }
    private String getPrivateWithParam(String str) {
        return "privateMethodWithParams:" + str;
    }
    public String getPublic() {
        return "publicMethod";
    }
    public String getPublicWithParam(int a) {
        return "publicMethodWithParam:" + a;
    }
}
