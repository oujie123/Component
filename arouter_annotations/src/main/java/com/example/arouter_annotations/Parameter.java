package com.example.arouter_annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Jack_Ou  created on 2020/12/22.
 */
@Target(ElementType.FIELD) // 该注解作用在类之上 字段上有作用
@Retention(RetentionPolicy.CLASS) // 要在编译时进行一些预处理操作，注解会在class文件中存在
public @interface Parameter {

    // 不填写name的注解值表示该属性名就是key，填写了就用注解值作为key
    // 从getIntent()方法中获取传递参数值
    String name() default "";
}
