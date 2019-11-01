package com.alon.common.msg.strategy.handle;

import com.alon.common.msg.EmailParam;
import com.alon.common.msg.strategy.SendEmailService;
import com.alon.common.result.ResultData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Properties;

/**
 * @ClassName AbstractHandler
 * @Description TODO
 * @Author zoujiulong
 * @Date 2019/10/29 13:57
 * @Version 1.0
 **/
public abstract class AbstractHandler<T extends ResultData> implements SendEmailService {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    public static Properties getProp() {
        //确定连接位置
        Properties props = new Properties();
        //获取163邮箱smtp服务器的地址，
        props.setProperty("mail.host","smtp.163.com");
        //是否进行权限验证。
        props.setProperty("mail.smtp.auth", "true");
        return props;
    }

    public static Authenticator authenticator() {
        //确定权限（账号和密码）
        Authenticator authenticator = new Authenticator() {
            @Override
            public PasswordAuthentication getPasswordAuthentication() {
                //填写自己的163邮箱的登录帐号和授权密码，授权密码的获取，在后面会进行讲解。
                return new PasswordAuthentication("Chow_V587@163.com","java666");
            }
        };
        return authenticator;
    }

    @Override
    public void sendEmail(EmailParam emailParam) {
        Properties props = getProp();
        Authenticator authenticator = authenticator();
        /**
         * 获得连接
         * props：包含配置信息的对象，Properties类型
         *         配置邮箱服务器地址、配置是否进行权限验证(帐号密码验证)等
         *
         * authenticator：确定权限(帐号和密码)
         */
        Session session = Session.getDefaultInstance(props,authenticator);

        sendMessage(emailParam,session);
    }

    protected abstract T sendMessage(EmailParam emailParam,Session session);

}
