package com.example.arouter_compiler.utils;

/**
 * @author Jack_Ou  created on 2020/12/16.
 */
public class ProcessorConfig {

    // @ARouter注解 的 包名 + 类名
    public static final String AROUTER_PACKAGE =  "com.example.arouter_annotations.ARouter";

    // 接收参数的TAG标记
    // 目的是接收 每个module名称
    public static final String OPTIONS = "moduleName";
    // 目的是接收 包名（APT 存放的包名）
    public static final String APT_PACKAGE = "packageNameForAPT";
}
