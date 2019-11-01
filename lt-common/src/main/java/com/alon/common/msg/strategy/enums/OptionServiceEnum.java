package com.alon.common.msg.strategy.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * @ClassName OptionServiceEnum
 * @Description TODO
 * @Author zoujiulong
 * @Date 2019/10/29 14:00
 * @Version 1.0
 **/
public enum OptionServiceEnum {

    DEFAULT("0000000", "DEFAULT");

    private final String code;
    private final String module;

    private OptionServiceEnum(String code, String module) {
        this.module = module;
        this.code = code;
    }

    public String getModule() {
        return module;
    }

    public String getCode() {
        return code;
    }

    public static String getModuleByCode(String code) {
        if (StringUtils.isEmpty(code)) {
            return null;
        }

        for (OptionServiceEnum value: OptionServiceEnum.values()) {
            if (StringUtils.equals(code, value.getCode())) {
                return value.getModule();
            }
        }

        return null;
    }
}
