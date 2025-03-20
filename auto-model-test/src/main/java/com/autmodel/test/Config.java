package com.autmodel.test;

import com.automodel.callback.AutoModelClassBuilder;
import com.squareup.javapoet.TypeSpec;

/**
 * @Author: yb
 * @Date:   2025-03-20 9:12
 */
public class Config implements AutoModelClassBuilder {

    @Override
    public void handle(TypeSpec.Builder builder) {
        System.err.println("test111");
    }
}
