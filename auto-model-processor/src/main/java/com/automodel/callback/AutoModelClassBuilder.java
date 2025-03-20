package com.automodel.callback;

import com.squareup.javapoet.TypeSpec;

/**
 * @author rodger
 * @date 2025-03-19
 */
public interface AutoModelClassBuilder {

    void handle(TypeSpec.Builder builder);
}
