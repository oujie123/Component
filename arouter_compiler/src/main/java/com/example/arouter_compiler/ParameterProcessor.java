package com.example.arouter_compiler;

import com.example.arouter_annotations.Parameter;
import com.example.arouter_compiler.utils.ProcessorConfig;
import com.example.arouter_compiler.utils.ProcessorUtils;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

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
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

/**
 * @author Jack_Ou  created on 2020/12/22.
 */
@AutoService(Processor.class)
@SupportedAnnotationTypes({ProcessorConfig.PARAMETER_PACKAGE})
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class ParameterProcessor extends AbstractProcessor {

    // 操作Element的工具类（类，函数，属性，其实就是Element）
    private Elements elementTool;

    // type（类信息）的工具类，包含用于操作TypeMirror的工具方法
    // 看类是implementations还是extends
    private Types typeTool;

    // Message用于打印日志相关信息
    private Messager messager;

    // 文件生成器，类，资源等。  就是最终要生成的文件是需要Filer来完成的
    private Filer filer;

    // key是MAinActivity， value是MainActivity中所有注解的参数
    private Map<TypeElement, List<Element>> mParameterMap = new HashMap<>();

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);

        elementTool = processingEnvironment.getElementUtils();
        messager = processingEnvironment.getMessager();
        filer = processingEnvironment.getFiler();
        typeTool = processingEnvironment.getTypeUtils();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        if (!ProcessorUtils.isEmpty(set)) {
            // 遍历所有被@Parameter 注解的元素
            Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(Parameter.class);
            if (!ProcessorUtils.isEmpty(elements)) {
                // 遍历所有被注解的参数
                for (Element element:elements) {
                    // 获取该字段节点的上一个节点，即类名
                    TypeElement enclosingElement = (TypeElement) element.getEnclosingElement();

                    // 把遍历的内容缓存在map中
                    if (mParameterMap.containsKey(enclosingElement)) {
                        mParameterMap.get(enclosingElement).add(element);
                    } else {
                        List<Element> paramList = new ArrayList<>();
                        paramList.add(element);
                        mParameterMap.put(enclosingElement,paramList);
                    }
                }

                if (ProcessorUtils.isEmpty(mParameterMap)) return true;

                TypeElement activityType = elementTool.getTypeElement(ProcessorConfig.ACTIVITY_PACKAGE);
                TypeElement parameterType = elementTool.getTypeElement(ProcessorConfig.AROUTER_API_PARAMETER_GET);

                // 生成方法
                // Object targetParameter  方法参数
                ParameterSpec targetParameter = ParameterSpec.builder(TypeName.OBJECT,ProcessorConfig.PARAMETER_NAME).build();

                // 循环遍历 缓存mParameterMap获得MAinActivity中的参数进行赋值
                for (Map.Entry<TypeElement,List<Element>> entry : mParameterMap.entrySet()){
                    TypeElement typeElement = entry.getKey();
                    if (!typeTool.isSubtype(typeElement.asType(),activityType.asType())) {
                        throw new RuntimeException("@Parameter注解目前仅限用于Activity类之上");
                    }

                    // t.name = t.getIntent().getStringExtra("name");
                    // t.age = t.getIntent().getIntExtra("age",0);
                    // 动态调用getXXExtra()
                    // 是Activity
                    // 获取类名 == Personal_MainActivity
                    ClassName className = ClassName.get(typeElement);

                    // 方法生成成功
                    ParameterFactory factory = new ParameterFactory.Builder(targetParameter)
                            .setMessager(messager)
                            .setClassName(className)
                            .build();

                    factory.addFirstStatement();
                    for (Element element : entry.getValue()) {
                        factory.buildStatement(element);
                    }

                    String finalClassName = typeElement.getSimpleName() + ProcessorConfig.PARAMETER_FILE_NAME;

                    messager.printMessage(Diagnostic.Kind.NOTE, "APT生成获取参数类文件名：" + className.packageName() + "." + finalClassName);

                    try {
                        JavaFile.builder(className.packageName(), TypeSpec.classBuilder(finalClassName)
                                .addModifiers(Modifier.PUBLIC)
                                .addSuperinterface(ClassName.get(parameterType))
                                .addMethod(factory.build())
                                .build())
                                .build()
                                .writeTo(filer);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return false; // 返回false不会重新调用这个方法检测
    }
}
