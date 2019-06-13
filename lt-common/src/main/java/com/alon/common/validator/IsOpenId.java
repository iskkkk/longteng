package com.alon.common.validator;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD,
        ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR,
        ElementType.PARAMETER})
/*ElementType.PACKAGE  注解作用于包
ElementType.TYPE 注解作用于类型（类，接口，注解，枚举）
ElementType.ANNOTATION_TYPE 注解作用于注解
ElementType.CONSTRUCTOR  注解作用于构造方法
ElementType.METHOD  注解作用于方法
ElementType.PARAMETER 注解作用于方法参数
ElementType.FIELD 注解作用于属性
ElementType.LOCAL_VARIABLE  注解作用于局部变量*/
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IsOpenId {
    String value() default "";
    boolean isCheck() default false;
}
