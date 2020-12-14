package com.example.arouter_compiler;

import com.example.arouter_annotations.ARouter;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
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
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

@AutoService(Processor.class) //一个服务，其他地方可以调用
@SupportedAnnotationTypes({"com.example.arouter_annotations.ARouter"})  // 支持那些注解
@SupportedSourceVersion(SourceVersion.RELEASE_7)  // 环境的版本
@SupportedOptions("jack")  // 接受Android工程传递过来的参数
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

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);

        elementTool = processingEnvironment.getElementUtils();
        messager = processingEnvironment.getMessager();
        filer = processingEnvironment.getFiler();

        String value = processingEnvironment.getOptions().get("jack");
        // 如果想要注解处理器中抛出异常，可以使用Diagnostic.Kind.ERROR，但是这里只是打印调试信息
        // 只能用Diagnostic.Kind.NOTE
        messager.printMessage(Diagnostic.Kind.NOTE, "--------->" + value);
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        messager.printMessage(Diagnostic.Kind.NOTE, "---------> running");
        if (set.isEmpty()){
            return false; // 如果返回false说明我的注解处理器不干活儿了，  返回true说明干完了
        }
        /**
             模块一
             package com.example.helloworld;

             public final class HelloWorld {

                public static void main(String[] args) {
                    System.out.println("Hello, JavaPoet!");
                }
             }
         */
        // 获取被ARouter注解的“类节点信息”
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(ARouter.class);
        for (Element element : elements) {
            //多个MainActivity注册
            // 1.写方法 生成main方法   返回值也是对象
            MethodSpec mainMethod = MethodSpec.methodBuilder("main")
                    .addModifiers(Modifier.PUBLIC,Modifier.STATIC)
                    .returns(void.class)
                    .addParameter(System[].class,"args")
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
            }
        }
        return true;
    }
}
