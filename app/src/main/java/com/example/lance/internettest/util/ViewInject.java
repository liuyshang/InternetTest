package com.example.lance.internettest.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * author: admin
 * time: 2016/3/9 17:26
 * e-mail: lance.cao@anarry.com
 */
@Target(ElementType.FIELD)//表示用在字段上
@Retention(RetentionPolicy.RUNTIME)//表示在生命周期运行时
public @interface ViewInject {
    public int value();
}
