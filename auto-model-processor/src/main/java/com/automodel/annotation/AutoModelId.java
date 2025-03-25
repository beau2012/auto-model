package com.automodel.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author rodger
 * @date 2025-03-20
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.FIELD)
public @interface AutoModelId {
}
