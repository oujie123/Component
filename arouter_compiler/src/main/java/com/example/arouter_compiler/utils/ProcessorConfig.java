package com.example.arouter_compiler.utils;

/**
 * @author Jack_Ou  created on 2020/12/16.
 */
public class ProcessorConfig {

    // @ARouter注解 的 包名 + 类名
    public static final String AROUTER_PACKAGE =  "com.example.arouter_annotations.ARouter";
    public static final String PARAMETER_PACKAGE =  "com.example.arouter_annotations.Parameter";

    // 原生Activity的全类名
    public static final String ACTIVITY_PACKAGE = "android.app.Activity";

    //ARouter api包名
    public static final String AROUTER_PACKAGE_NAME = "com.gacrnd.gcs.arouter_api";

    //ARouter api的ARouterPath高层标准
    public static final String AROUTER_API_PATH = AROUTER_PACKAGE_NAME + ".ARouterPath";

    //ARouter api的ARouterGroup高层标准
    public static final String AROUTER_API_GROUP = AROUTER_PACKAGE_NAME + ".ARouterGroup";

    //ARouter api的ParameterGet高层标准
    public static final String AROUTER_API_PARAMETER_GET = AROUTER_PACKAGE_NAME + ".ParameterGet";

    //ARouter api的Call高层标准
    public static final String AROUTER_API_CALL = AROUTER_PACKAGE_NAME + ".Call";

    //获取pathMap
    public static final String PATH_METHOD_NAME = "getPathMap";

    // 方法中放pathName变量
    public static final String PATH_VAR1 = "pathName";
    // 方法中放groupName变量
    public static final String GROUP_VAR1 = "groupMap";

    //Path类前缀
    public static final String PATH_FILE_NAME = "ARouter$$Path$$";

    //Group类前缀
    public static final String GROUP_FILE_NAME = "ARouter$$Group$$";

    //参数类后缀
    public static final String PARAMETER_FILE_NAME = "$$Parameter";

    //获取groupMap
    public static final String GROUP_METHOD_NAME = "getGroupMap";

    // 接收参数的TAG标记
    // 目的是接收 每个module名称
    public static final String OPTIONS = "moduleName";
    // 目的是接收 包名（APT 存放的包名）
    public static final String APT_PACKAGE = "packageNameForAPT";

    public static final String PARAMETER_NAME = "targetParameter";

    public static final String METHOD_GET_PARAMETER = "getParameter";
    public static final String STRING = "java.lang.String";
}
