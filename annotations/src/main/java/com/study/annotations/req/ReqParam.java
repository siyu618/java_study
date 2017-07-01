package com.study.annotations.req;

import java.lang.annotation.*;

/**
 * Created by tianyuzhi on 17/5/8.
 */
@Documented
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface ReqParam {
    String value() default "";
}
