package com.automodel.annotation;

import com.automodel.enums.ModelTypeEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author rodger
 * @date 2025-03-04
 */
@Retention(RetentionPolicy.SOURCE) // 仅保留在源码阶段
@Target(ElementType.TYPE)          // 作用于类/接口
public @interface AutoModel {
    ModelTypeEnum[] value() default {
            ModelTypeEnum.ADD,
            ModelTypeEnum.UPDATE,
            ModelTypeEnum.DELETE
    };

}
