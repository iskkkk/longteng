
package com.alon.shiro.config;

/**
 * 功能描述:Redis所有Keys
 * @param:
 * @return:
 * @auther: zoujiulong
 * @date: 2018/8/28   15:19
 */
public class RedisKeys {

    public static String getSysConfigKey(String key){
        return "sys:config:" + key;
    }

    public static String getShiroSessionKey(String key){
        return "sessionid:" + key;
    }
}
