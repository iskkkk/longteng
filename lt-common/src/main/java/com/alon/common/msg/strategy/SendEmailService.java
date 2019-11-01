package com.alon.common.msg.strategy;

import com.alon.common.msg.EmailParam;

/**
 * @ClassName SendEmailService
 * @Description 发送短信统一接口
 * @Author zoujiulong
 * @Date 2019/10/29 11:42
 * @Version 1.0
 **/
public interface SendEmailService extends OptionServiceSelector {
    void sendEmail(EmailParam emailParam);
}
