package com.study.annotations.todo;

import java.lang.annotation.*;

/**
 * Created by tianyuzhi on 17/5/8.
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Todo {
    enum Priority{
        LOW, MIDDLE, HIGH
    }
    enum Status {
        STARTED, NOT_STARTED
    }
    String author() default "siyu";
    Priority priority() default Priority.LOW;
    Status status() default Status.NOT_STARTED;
}
