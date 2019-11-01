package com.alon.common.msg.strategy.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * @ClassName DataServiceEnum
 * @Description TODO
 * @Author zoujiulong
 * @Date 2019/10/29 14:14
 * @Version 1.0
 **/
public enum DataServiceEnum {

    /**
     * 默认的
     */
    DEFAULT("0000000", "DEFAULT"),
    /**
     * html邮件发送
     */
    HTML_EMAIL("1000001", "HTML_EMAIL");

    private final String code;
    private final String module;

    private DataServiceEnum(String code, String module) {
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
