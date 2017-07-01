package com.study.annotations.req;

import java.lang.annotation.*;

/**
 * Created by tianyuzhi on 17/5/8.
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@interface ReqType {
    enum ReqTypeEnum {
        GET, POST, DELETE, PUT
    }
    ReqTypeEnum reqType() default ReqTypeEnum.POST;
}
