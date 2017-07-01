package com.study.annotations.todo;


import java.lang.reflect.Method;

/**
 * Created by tianyuzhi on 17/5/8.
 */
public class TodoExample {

    @Todo(author = "lisi", priority = Todo.Priority.MIDDLE, status = Todo.Status.STARTED)
    public void method() {

    }

    public static void main(String[] args) {
        System.setProperty("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        Class clz = TodoExample.class;
        for (Method method : clz.getMethods()) {
            Todo todo = method.getAnnotation(Todo.class);
            if (null != todo) {
                System.out.println("method name: " + method.getName());
                System.out.println("Author:" + todo.author());
                System.out.println("Priority:" + todo.priority());
                System.out.println("Status:" + todo.status());
            }
        }

        System.out.println();
    }
}
