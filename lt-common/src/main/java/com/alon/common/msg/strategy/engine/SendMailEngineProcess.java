package com.alon.common.msg.strategy.engine;

import com.alon.common.msg.EmailParam;
import com.alon.common.msg.strategy.SendEmailService;
import com.alon.common.msg.strategy.SendMailHandleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @ClassName SendMailEngineProcess
 * @Description TODO
 * @Author zoujiulong
 * @Date 2019/10/29 14:19
 * @Version 1.0
 **/
@Component
public class SendMailEngineProcess {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SendMailHandleProvider handleProvider;

    /**
      * 方法表述: 外部统一调用该方法传入对应实现类的编码
      * @Author zoujiulong
      * @Date 14:22 2019/10/29
      * @param       emailParam
      * @return void
    */
    public void sendMail(EmailParam emailParam) {
        SendEmailService service = handleProvider.getService(emailParam.bizNum);
        service.sendEmail(emailParam);
    }
}
