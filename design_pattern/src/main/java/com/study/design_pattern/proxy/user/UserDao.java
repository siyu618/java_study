package com.study.design_pattern.proxy.user;

/**
 * Created by tianyuzhi on 18/3/1.
 */
public class UserDao implements IUserDao {

    @Override
    public void save() {
        System.out.println("save the data");
    }
}
