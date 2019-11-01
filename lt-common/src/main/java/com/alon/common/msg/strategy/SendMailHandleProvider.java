package com.alon.common.msg.strategy;

import com.alon.common.msg.strategy.handle.DefaultHandler;
import org.springframework.stereotype.Service;

/**
 * @ClassName SendMailHandleProvider
 * @Description 服务实现提供者
 * @Author zoujiulong
 * @Date 2019/10/29 14:06
 * @Version 1.0
 **/
@Service
public class SendMailHandleProvider extends OptionalServiceProvider<SendEmailService, DefaultHandler> {
}
