package com.gacrnd.gcs.arouter_api;

/**
 * @author Jack_Ou  created on 2020/12/22.
 */
public interface ParameterGet {

    /**
     * 目标对象.属性名 = getIntent().属性类型... 完成赋值操作
     *
     * 从targetParameter中拿到变量并且进行赋值
     *
     * @param targetParameter 目标对象：例如：MainActivity 中的那些属性
     */
    void getParameter(Object targetParameter);
}
