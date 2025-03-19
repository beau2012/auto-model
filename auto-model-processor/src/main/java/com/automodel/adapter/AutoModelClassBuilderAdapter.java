package com.automodel.adapter;

import com.squareup.javapoet.TypeSpec;

/**
 * @author rodger
 * @date 2025-03-19
 */
public interface AutoModelClassBuilderAdapter {

    /**
     * 处理
     *
     * @param builder
     */
    void handle(TypeSpec.Builder builder);
}
