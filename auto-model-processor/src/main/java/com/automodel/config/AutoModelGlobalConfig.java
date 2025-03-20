package com.automodel.config;

import com.automodel.callback.AutoModelClassBuilder;
import com.squareup.javapoet.TypeSpec;

/**
 * @Author: yb
 * @Date: 2025-03-20 9:55
 */
public class AutoModelGlobalConfig {

    private static AutoModelClassBuilder autoModelClassBuilder = builder -> {

    };

    public static AutoModelClassBuilder getAutoModelClassBuilder() {
        return autoModelClassBuilder;
    }

    public static void setAutoModelClassBuilder(AutoModelClassBuilder autoModelClassBuilder) {
        AutoModelGlobalConfig.autoModelClassBuilder = autoModelClassBuilder;
    }
}
