package com.alon.common.msg;

import org.apache.commons.lang3.StringUtils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.util.Properties;

/**
 * @ClassName SendEmail
 * @Description TODO
 * @Author zoujiulong
 * @Date 2019/10/25 13:50
 * @Version 1.0
 **/
public class SendEmail {

    public static void main(String[] args) {
        String content = "test";
        String cc = "";
        String bcc = "";
        EmailParam emailParam = new EmailParam("1442882303@qq.com","123",content,"123",cc,bcc);
        System.out.println(sendEmail(emailParam));

    }


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

    public static Boolean sendEmail(EmailParam emailParam) {
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

        //创建消息
        Message message = new MimeMessage(session);
        // 发件人        xxx@163.com
        try {
            String nickName = MimeUtility.encodeText(emailParam.nickName);
            String from = MimeUtility.encodeText("Chow_V587@163.com");
            message.setFrom(new InternetAddress(nickName+" <"+from+">"));
            /**
             * 2.2 收件人
             *         第一个参数：
             *             RecipientType.TO    代表收件人
             *             RecipientType.CC    抄送
             *             RecipientType.BCC    暗送
             *         比如A要给B发邮件，但是A觉得有必要给要让C也看看其内容，就在给B发邮件时，
             *         将邮件内容抄送给C，那么C也能看到其内容了，但是B也能知道A给C抄送过该封邮件
             *         而如果是暗送(密送)给C的话，那么B就不知道A给C发送过该封邮件。
             *     第二个参数
             *         收件人的地址，或者是一个Address[]，用来装抄送或者暗送人的名单。或者用来群发。可以是相同邮箱服务器的，也可以是不同的
             *         这里我们发送给我们的qq邮箱
             */
            message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(emailParam.toUser));
            if (StringUtils.isNotBlank(emailParam.cc)) {
                message.setRecipient(MimeMessage.RecipientType.CC,new InternetAddress(emailParam.cc));
            }
            if (StringUtils.isNotBlank(emailParam.bcc)) {
                message.setRecipient(MimeMessage.RecipientType.BCC,new InternetAddress(emailParam.bcc));
            }
            // 主题（标题）
            message.setSubject(emailParam.subject);
            //设置编码，防止发送的内容中文乱码。
            message.setContent(emailParam.content, "text/html;charset=UTF-8");
            //发送消息
            Transport.send(message);
            System.out.println("===========-----发送成功-------===========");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("===========-----发送失败-------===========" + e.getMessage());
            return false;
        }
    }
}
