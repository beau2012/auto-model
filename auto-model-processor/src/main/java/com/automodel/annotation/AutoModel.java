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
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface AutoModel {
    ModelTypeEnum[] value() default {
            ModelTypeEnum.ADD,
            ModelTypeEnum.UPDATE,
            ModelTypeEnum.DELETE
    };

    boolean lombok() default false;
}
