package com.example.arouter_compiler.utils;

/**
 * @author Jack_Ou  created on 2020/12/16.
 */
public class ProcessorConfig {

    // @ARouter注解 的 包名 + 类名
    public static final String AROUTER_PACKAGE =  "com.example.arouter_annotations.ARouter";

    // 原生Activity的全类名
    public static final String ACTIVITY_PACKAGE = "android.app.Activity";

    //ARouter api包名
    public static final String AROUTER_PACKAGE_NAME = "com.gacrnd.gcs.arouter_api";

    //ARouter api的ARouterPath高层标准
    public static final String AROUTER_API_PATH = AROUTER_PACKAGE_NAME + ".ARouterPath";

    //ARouter api的ARouterGroup高层标准
    public static final String AROUTER_API_GROUP = AROUTER_PACKAGE_NAME + ".ARouterGroup";

    //获取pathMap
    public static final String PATH_METHOD_NAME = "getPathMap";

    // 方法中放pathName变量
    public static final String PATH_VAR1 = "pathName";

    //Path类前缀
    public static final String PATH_FILE_NAME = "ARouter$$Path$$";

    //Group类前缀
    public static final String GROUP_FILE_NAME = "ARouter$$GROUP$$";

    //获取groupMap
    public static final String GROUP_METHOD_NAME = "getGroupMap";

    // 接收参数的TAG标记
    // 目的是接收 每个module名称
    public static final String OPTIONS = "moduleName";
    // 目的是接收 包名（APT 存放的包名）
    public static final String APT_PACKAGE = "packageNameForAPT";
}
