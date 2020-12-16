package com.example.arouter_annotations.bean;

import javax.lang.model.element.Element;

/**
 * 各个分组中的Activity对象，例如app分组中的MainActivity
 *
 * @author Jack_Ou  created on 2020/12/16.
 */
public class RouterBean {

    public enum TypeEnum {
        ACTIVITY
    }

    // 枚举类型：Activity
    private TypeEnum typeEnum;

    // 类节点,可以拿到很多的信息
    private Element element;

    // 被注解的 Class对象 例如： MainActivity.class  Main2Activity.class  Main3Activity.class
    private Class<?> myClass;

    // 路由地址  例如：/app/MainActivity
    private String path;

    // 路由组  例如：app  order  personal
    private String group;

    private RouterBean(TypeEnum typeEnum, /*Element element,*/ Class<?> myClass, String path, String group) {
        this.typeEnum = typeEnum;
        // this.element = element;
        this.myClass = myClass;
        this.path = path;
        this.group = group;
    }

    // 工厂模式创建bean
    public static RouterBean create(TypeEnum typeEnum, Class<?> myClass, String path, String group) {
        return new RouterBean(typeEnum, myClass, path, group);
    }

    private RouterBean(Builder builder) {
        this.element = builder.element;
        this.typeEnum = builder.type;
        this.myClass = builder.clazz;
        this.path = builder.path;
        this.group = builder.group;
    }

    public static class Builder {
        // 枚举类型：Activity
        private TypeEnum type;
        // 类节点
        private Element element;
        // 注解使用的类对象
        private Class<?> clazz;
        // 路由地址
        private String path;
        // 路由组
        private String group;

        public Builder addType(TypeEnum type) {
            this.type = type;
            return this;
        }

        public Builder addElement(Element element) {
            this.element = element;
            return this;
        }

        public Builder addClass(Class<?> clazz) {
            this.clazz = clazz;
            return this;
        }

        public Builder addPath(String path) {
            this.path = path;
            return this;
        }

        public Builder addGroup(String group) {
            this.group = group;
            return this;
        }

        // 最后的build或者create，往往是做参数的校验或者初始化赋值工作
        public RouterBean build() {
            if (path == null || path.length() == 0) {
                throw new IllegalArgumentException("path必填项为空，如：/app/MainActivity");
            }
            return new RouterBean(this);
        }
    }

    public TypeEnum getTypeEnum() {
        return typeEnum;
    }

    public void setTypeEnum(TypeEnum typeEnum) {
        this.typeEnum = typeEnum;
    }

    public Element getElement() {
        return element;
    }

    public void setElement(Element element) {
        this.element = element;
    }

    public Class<?> getMyClass() {
        return myClass;
    }

    public void setMyClass(Class<?> myClass) {
        this.myClass = myClass;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
