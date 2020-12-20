package com.example.arouter_compiler;

import com.example.arouter_annotations.ARouter;
import com.example.arouter_annotations.bean.RouterBean;
import com.example.arouter_compiler.utils.ProcessorConfig;
import com.example.arouter_compiler.utils.ProcessorUtils;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.WildcardTypeName;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedOptions;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

@AutoService(Processor.class) //一个服务，其他地方可以调用
@SupportedAnnotationTypes({ProcessorConfig.AROUTER_PACKAGE})  // 支持那些注解
@SupportedSourceVersion(SourceVersion.RELEASE_7)  // 环境的版本
@SupportedOptions({ProcessorConfig.OPTIONS, ProcessorConfig.APT_PACKAGE})  // 接受Android工程传递过来的参数
public class ARouterProcessor extends AbstractProcessor {

    // 操作Element的工具类（类，函数，属性，其实就是Element）
    private Elements elementTool;

    // type（类信息）的工具类，包含用于操作TypeMirror的工具方法
    // 看类是implementations还是extends
    private Types typeTool;

    // Message用于打印日志相关信息
    private Messager messager;

    // 文件生成器，类，资源等。  就是最终要生成的文件是需要Filer来完成的
    private Filer filer;

    private String options;
    private String aptPackage;

    // path仓库 String 代表模块名  Map<"personal", List<RouterBean>>
    private Map<String, List<RouterBean>> mAllPathMap = new HashMap<>();

    // group仓库   Map<"personal", "ARouter$$Path$$personal.class">
    private Map<String, String> mAllGroupMap = new HashMap<>();

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);

        elementTool = processingEnvironment.getElementUtils();
        messager = processingEnvironment.getMessager();
        filer = processingEnvironment.getFiler();
        typeTool = processingEnvironment.getTypeUtils();

        options = processingEnvironment.getOptions().get(ProcessorConfig.OPTIONS);
        aptPackage = processingEnvironment.getOptions().get(ProcessorConfig.APT_PACKAGE);
        // 如果想要注解处理器中抛出异常，可以使用Diagnostic.Kind.ERROR，但是这里只是打印调试信息
        // 只能用Diagnostic.Kind.NOTE
        messager.printMessage(Diagnostic.Kind.NOTE, "--------->" + options);
        messager.printMessage(Diagnostic.Kind.NOTE, "--------->" + aptPackage);
        if (options != null && aptPackage != null) {
            messager.printMessage(Diagnostic.Kind.NOTE, "APT 环境搭建完成....");
        } else {
            messager.printMessage(Diagnostic.Kind.NOTE, "APT 环境有问题，请检查 options 与 aptPackage 为null...");
        }
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        //messager.printMessage(Diagnostic.Kind.NOTE, "---------> running");   //有一个注解就会运行一次process
        if (set.isEmpty()) {
            messager.printMessage(Diagnostic.Kind.NOTE, "没有找到被@ARouter注解的地方呀");
            return false; // 如果返回false说明我的注解处理器不干活儿了，  返回true说明干完了
        }
        /**
         TODO 模块一
         package com.example.helloworld;

         public final class HelloWorld {

         public static void main(String[] args) {
         System.out.println("Hello, JavaPoet!");
         }
         }
         */
        // 获取被ARouter注解的“类节点信息”
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(ARouter.class);

        // Activity类型镜像
        TypeElement activityType = elementTool.getTypeElement(ProcessorConfig.ACTIVITY_PACKAGE);
        TypeMirror activityMirror = activityType.asType();  // activity描述信息

        for (Element element : elements) {
            //多个MainActivity注册     element就代表每一个Activity
            // 1.写方法 生成main方法   返回值也是对象
            /**
             MethodSpec mainMethod = MethodSpec.methodBuilder("main")
             .addModifiers(Modifier.PUBLIC,Modifier.STATIC)
             .returns(void.class)
             .addParameter(String[].class,"args")
             .addStatement("$T.out.println($S)",System.class,"Hello, JavaPoet!") // 增加方法内部的内容.   $T代表替换的类  $S代表字符串
             .build();

             // 2.写类
             TypeSpec jackClass = TypeSpec.classBuilder("HelloWorld")
             .addModifiers(Modifier.PUBLIC,Modifier.FINAL)
             .addMethod(mainMethod)
             .build();

             // 3.写包
             JavaFile file = JavaFile.builder("com.gacrnd.gcs.component",jackClass).build();

             // 生成文件
             try {
             file.writeTo(filer);
             } catch (IOException e) {
             e.printStackTrace();
             messager.printMessage(Diagnostic.Kind.NOTE, "生成失败");
             }**/

            /**
             TODO 模板：
             public class MainActivity3$$$$$$$$$ARouter {
             public static Class findTargetClass(String path) {
             return path.equals("/app/MainActivity3") ? MainActivity3.class : null;
             }
             }
             */

            /**
             // 拿到包名
             String packageName = elementTool.getPackageOf(element).getQualifiedName().toString();

             //拿到类名
             String className = element.getSimpleName().toString();
             messager.printMessage(Diagnostic.Kind.NOTE, "被@ARoute注解的类有：" + className);
             String finalClassName = className + "$$$$$$$$$ARouter";

             //拿到ARouter注解
             ARouter aRouter = element.getAnnotation(ARouter.class);

             // 1.写方法
             MethodSpec findTargetMethod = MethodSpec.methodBuilder("findTargetClass")
             .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
             .returns(Class.class)
             .addParameter(String.class, "path")
             // element就是MainActivity，但是需要转型，需要使用javapoet包装一下,强转成TypeElement
             .addStatement("return path.equals($S) ? $T.class : null", aRouter.path(), ClassName.get((TypeElement) element))
             .build();

             // 2.写类
             TypeSpec myClass = TypeSpec.classBuilder(finalClassName)
             .addModifiers(Modifier.PUBLIC)
             .addMethod(findTargetMethod)
             .build();

             // 3.写包名
             JavaFile packagef = JavaFile.builder(packageName,myClass).build();

             // 4.写文件
             try {
             packagef.writeTo(filer);
             } catch (IOException e) {
             e.printStackTrace();
             }*/

            // TODO 模仿ARouter
            // 校验用户传的参数是否符合规则
            //拿到ARouter注解
            ARouter aRouter = element.getAnnotation(ARouter.class);
            RouterBean routerBean = new RouterBean.Builder()
                    .addGroup(aRouter.group())
                    .addPath(aRouter.path())
                    .addElement(element)
                    .build();

            // 被ARouter注解的类必须继承Activity
            // 拿到element的类型，element就是MainActivity
            //TypeMirror可以监测element的类有没有实现什么类型,typeMirror代表MainActivity的具体详情，继承实现了谁
            TypeMirror typeMirror = element.asType();

            if (typeTool.isSubtype(typeMirror, activityMirror)) {
                routerBean.setTypeEnum(RouterBean.TypeEnum.ACTIVITY);  //证明是activity
            } else {
                // 不是activity就抛出异常
                throw new RuntimeException("@ARouter注解的类必须继承Activity。path -- >" + routerBean.getPath());
            }

            if (checkRouterPath(routerBean)) {
                messager.printMessage(Diagnostic.Kind.NOTE, "ARouterBean Check Success," + routerBean.toString());

                // 获取pathList
                List<RouterBean> beans = mAllPathMap.get(routerBean.getGroup());

                // 如果通过group没有找到详情，就新建一个
                if (ProcessorUtils.isEmpty(beans)) {
                    beans = new ArrayList<>();
                    beans.add(routerBean);
                    mAllPathMap.put(routerBean.getGroup(), beans);
                } else {
                    beans.add(routerBean);
                }
            } else {
                // 编译器发出异常，不让编译通过
                messager.printMessage(Diagnostic.Kind.ERROR, "@ARouter注解的类必须按规范使用，如：/app/MainActivity");
            }
        }

        // TODO mAllPathMap收集了所有被ARouter注解的类
        // 描述要实现的接口， ARouterPath和ARouterGroup
        TypeElement pathType = elementTool.getTypeElement(ProcessorConfig.AROUTER_API_PATH);
        TypeElement groupType = elementTool.getTypeElement(ProcessorConfig.AROUTER_API_GROUP);

        // what the fuck, 少导入依赖，害得我调了一晚上，MD
//            messager.printMessage(Diagnostic.Kind.NOTE,  pathType +"---------> running");

        // TODO 正式开始生成文件：生成path文件
        try {
            // TODO Step 1 根据每个组的RouterBean生成getPathMap
            createPathFile(pathType);
        } catch (IOException e) {
            e.printStackTrace();
            messager.printMessage(Diagnostic.Kind.NOTE, "文件生成失败");
        }

        try {
            // TODO Step 2 组合每一个group，生成groupfile
            createGroupFile(groupType, pathType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 生成路由组group，如：ARouter$$Group$$app
     *
     * @param groupType 生成的group需要实现的接口
     * @param pathType  放入value的路由路径，通过这个getPathMap可以拿到每个模块的所有具体Activity类
     */
    private void createGroupFile(TypeElement groupType, TypeElement pathType) throws IOException {
        // group仓库和path缓存 判断是否有需要生成的类文件
        if (ProcessorUtils.isEmpty(mAllGroupMap) || ProcessorUtils.isEmpty(mAllPathMap)) return;

        //返回值Map<String, Class<? extends ARouterPath>>
        TypeName methodReturn = ParameterizedTypeName.get(
                ClassName.get(Map.class), // Map
                ClassName.get(String.class), //Map<String,
                ParameterizedTypeName.get(ClassName.get(Class.class),   // Class<? extends ARouterPath>
                        // 通配符获得子类WildcardTypeName
                        WildcardTypeName.subtypeOf(ClassName.get(pathType))       // ? extends ARouterPath
                )
        );

        // 写方法   public Map<String, Class<? extends ARouterPath>> getGroupMap() {
        MethodSpec.Builder builder = MethodSpec.methodBuilder(ProcessorConfig.GROUP_METHOD_NAME)
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .returns(methodReturn);

        // 给方法加内容  Map<String, Class<? extends ARouterPath>> groupMap = new HashMap<>();
        // <? extends $T> 这个需要用api来补全
        builder.addStatement("$T<$T, $T> $N = new $T<>()",
                ClassName.get(Map.class),
                ClassName.get(String.class),
                ParameterizedTypeName.get(ClassName.get(Class.class),   // Class<? extends ARouterPath>
                        // 通配符获得子类WildcardTypeName
                        WildcardTypeName.subtypeOf(ClassName.get(pathType))),      // ? extends ARouterPath
                ProcessorConfig.GROUP_VAR1,
                ClassName.get(HashMap.class)
        );

        //  groupMap.put("personal", ARouter$$Path$$personal.class);
        //	groupMap.put("order", ARouter$$Path$$order.class);
        for (Map.Entry<String, String> entry : mAllGroupMap.entrySet()) {
            builder.addStatement("$N.put($S, $T.class)",
                    ProcessorConfig.GROUP_VAR1,
                    entry.getKey(),
                    // TODO 把String变成class   ClassName.get(aptPackage,entry.getValue()) 通过包名加类名帮助找到类
                    ClassName.get(aptPackage, entry.getValue())
            );
        }

        // return groupMap;
        builder.addStatement("return $N",ProcessorConfig.GROUP_VAR1);

        // 拼接类  ARouter$$Group$$ + register     options是每个Module传入的
        String finalClassName = ProcessorConfig.GROUP_FILE_NAME + options;
        messager.printMessage(Diagnostic.Kind.NOTE, "APT生成路由Group类文件名：" + aptPackage + "." + finalClassName);

        TypeSpec.Builder classBuilder = TypeSpec.classBuilder(finalClassName)
                .addModifiers(Modifier.PUBLIC)
                .addMethod(builder.build())
                .addSuperinterface(ClassName.get(groupType));

        JavaFile file = JavaFile.builder(aptPackage, classBuilder.build()).build();

        file.writeTo(filer);
    }

    private void createPathFile(TypeElement pathType) throws IOException {
        if (ProcessorUtils.isEmpty(mAllPathMap)) {
            return;
        }
        // 1.生成方法   任何类类型都需要用ClassName包装：ClassName.get(Map.class)
        // JavaPoet高级用法  生成 Map<String, RouterBean>
        TypeName methodReturn = ParameterizedTypeName.get(
                ClassName.get(Map.class), // Map
                ClassName.get(String.class),  //Map<String,
                ClassName.get(RouterBean.class) // Map<String,RouterBean>
        );

        for (Map.Entry<String, List<RouterBean>> entry : mAllPathMap.entrySet()) {
            //遍历每个module中所有的routerbean
            // 1.生成方法    使用MethodSpec.Builder才能生成带注解的方法
            MethodSpec.Builder builder = MethodSpec.methodBuilder(ProcessorConfig.PATH_METHOD_NAME)
                    .addAnnotation(Override.class)
                    .addModifiers(Modifier.PUBLIC)
                    .returns(methodReturn);

            // 生成方法内部的内容  Map<String, RouterBean> pathMap = new HashMap<>();   需要导包的就必须要替换占位
            // TODO pathMap是变量，有引用的，不能用字符串代替，使用 $N 代表变量
            builder.addStatement("$T<$T, $T> $N = new $T<>()",
                    ClassName.get(Map.class),         //Map
                    ClassName.get(String.class),      //Map<String,
                    ClassName.get(RouterBean.class),  //Map<String, RouterBean>
                    ProcessorConfig.PATH_VAR1,        //Map<String, RouterBean> pathMap
                    ClassName.get(HashMap.class)      //Map<String, RouterBean> pathMap = new HashMap<>();
            );

            // 必须要循环，因为有多个
            // pathMap.put("/register/register_Main2Activity", RouterBean.create(RouterBean.TypeEnum.ACTIVITY,
            // register_Main2Activity.class,"/register/MainActivity","register");
            // pathMap.put("/register/register_MainActivity", RouterBean.create(RouterBean.TypeEnum.ACTIVITY));
            List<RouterBean> pathList = entry.getValue();
            for (RouterBean routerBean : pathList) {
                //TODO RouterBean.TypeEnum.ACTIVITY   枚举使用 $L 表示    $L = TypeEnum.ACTIVITY
                builder.addStatement("$N.put($S,$T.create($T.$L, $T.class, $S, $S))",
                        ProcessorConfig.PATH_VAR1,
                        routerBean.getPath(),
                        ClassName.get(RouterBean.class),
                        ClassName.get(RouterBean.TypeEnum.class), //RouteBean.TypeEnum.
                        routerBean.getTypeEnum(),  // 枚举的值ACTIVITY
                        ClassName.get((TypeElement) routerBean.getElement()),  //element代表MainActivity，TypeElement是格式化过后的MainActivity
                        routerBean.getPath(),
                        routerBean.getGroup()
                );
            }
            // 返回值
            builder.addStatement("return $N", ProcessorConfig.PATH_VAR1);

            // TODO 注意：不能像以前一样，1.方法，2.类  3.包， 因为这里面有implements ，所以 方法和类要合为一体生成才行，这是特殊情况
            // public class ARouter$$Path$$register implements ARouterPath    实现的，一个register Module会有很多ARouterBean，所以需要在for循环内部写类

            // 生成类
            String finalClassName = ProcessorConfig.PATH_FILE_NAME + entry.getKey();
            messager.printMessage(Diagnostic.Kind.NOTE, "APT生成路由Path类文件名：" + aptPackage + "." + finalClassName);

            TypeSpec finalClass = TypeSpec.classBuilder(finalClassName)
                    .addSuperinterface(ClassName.get(pathType))   // 增加implements
                    .addModifiers(Modifier.PUBLIC)
                    .addMethod(builder.build())
                    .build();

            // 一气呵成生成文件
            JavaFile file = JavaFile.builder(aptPackage, finalClass).build();

            file.writeTo(filer);

            //加入group
            mAllGroupMap.put(entry.getKey(), finalClassName);
        }
    }

    // 保证ARouterBean没问题
    private final boolean checkRouterPath(RouterBean bean) {
        String group = bean.getGroup();
        String path = bean.getPath();

        if (ProcessorUtils.isEmpty(path) || !path.startsWith("/")) {
            messager.printMessage(Diagnostic.Kind.NOTE, "@ARouter注解中的path参数，必须要以/开头");
            return false;
        }

        if (path.endsWith("/")) {
            messager.printMessage(Diagnostic.Kind.NOTE, "@ARouter注解中的path参数，必须不能以/结尾");
            return false;
        }

        // 取第一个字段为组名
        String finalGroup = path.substring(1, path.indexOf("/", 1));

        // options:为module名
        if (!ProcessorUtils.isEmpty(group) && !group.equals(options)) {
            messager.printMessage(Diagnostic.Kind.NOTE, "@ARouter注解中的group参数必须是Module名");
            return false;
        } else {
            bean.setGroup(finalGroup);
        }
        return true;
    }
}
