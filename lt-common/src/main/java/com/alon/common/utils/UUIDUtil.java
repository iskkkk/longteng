package com.alon.common.utils;

import java.util.UUID;

/**
 * @ClassName UUIDUtil
 * @Description 唯一id生成类
 * @Author 一股清风
 * @Date 2019/5/20 14:52
 * @Version 1.0
 **/
public class UUIDUtil {

    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}
