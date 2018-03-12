package com.study.design_pattern.proxy.user;

/**
 * Created by tianyuzhi on 18/3/1.
 */
public class UserDaoProxy {
    private IUserDao userDao;
    public UserDaoProxy(IUserDao userDao) {
        this.userDao = userDao;
    }
    public void save() {
        System.out.println("before save");
        userDao.save();
        System.out.println("after save");
    }

    public static void main(String[] args) {
        UserDao userDao = new UserDao();

        UserDaoProxy proxy = new UserDaoProxy(userDao);
        proxy.save();
    }
}
