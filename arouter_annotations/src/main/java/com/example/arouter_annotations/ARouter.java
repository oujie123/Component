package com.example.arouter_annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: Jack Ou
 * @CreateDate: 2020/12/14 22:07
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/12/14 22:07
 * @UpdateRemark: 更新说明
 */
@Target(ElementType.TYPE) //作用于类上
@Retention(RetentionPolicy.CLASS) //编译期
public @interface ARouter {

    String path();
    String group() default "";
}
