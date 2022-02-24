package com.spring.smpor.common.annotation;

import java.lang.annotation.*;

/**
 * @Description:
 * @Auther: fanlz
 * @Date: 2021/12/7 16:51
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD})
@Documented
public @interface ResponseResult {
}
