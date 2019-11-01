package com.alon.common.msg.strategy.handle;

import com.alon.common.msg.EmailParam;
import com.alon.common.msg.strategy.enums.DataServiceEnum;
import com.alon.common.result.CodeMessage;
import com.alon.common.result.ResultData;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

/**
 * @ClassName DefaultHandler
 * @Description TODO
 * @Author zoujiulong
 * @Date 2019/10/29 13:57
 * @Version 1.0
 **/
@Service
public class DefaultHandler extends AbstractHandler {

    @Override
    public String getBizNum() {
        return DataServiceEnum.DEFAULT.getCode();
    }

    @Override
    public void sendEmail(EmailParam emailParam) {
        super.sendEmail(emailParam);
    }

    @Override
    protected ResultData sendMessage(EmailParam emailParam, Session session) {
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
            return ResultData.success("发送成功");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("===========-----发送失败-------===========" + e.getMessage());
            return ResultData.error(CodeMessage.SERVER_ERROR);
        }
    }
}
