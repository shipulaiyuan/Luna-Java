package com.virtual.luna.common.base.utils;

import cn.hutool.core.bean.BeanUtil;

public class BeanUtils {

    public static <T> T toBean(Object source, Class<T> targetClass) {
        return BeanUtil.toBean(source, targetClass);
    }

}
