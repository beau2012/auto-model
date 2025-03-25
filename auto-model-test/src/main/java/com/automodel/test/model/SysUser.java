package com.automodel.test.model;

import com.automodel.annotation.AutoModel;
import com.automodel.annotation.AutoModelId;

/**
 * @author rodger
 * @date 2025-03-20
 */
@AutoModel(lombok = true)
public class SysUser {

    @AutoModelId
    private Long id;

    private String username;
}
