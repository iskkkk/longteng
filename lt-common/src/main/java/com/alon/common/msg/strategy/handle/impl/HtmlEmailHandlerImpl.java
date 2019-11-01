package com.alon.common.msg.strategy.handle.impl;

import com.alon.common.msg.EmailParam;
import com.alon.common.msg.strategy.enums.DataServiceEnum;
import com.alon.common.msg.strategy.handle.AbstractHandler;
import com.alon.common.result.CodeMessage;
import com.alon.common.result.ResultData;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.UnsupportedEncodingException;

/**
 * @ClassName HtmlEmailHandlerImpl
 * @Description TODO
 * @Author zoujiulong
 * @Date 2019/10/31 16:37
 * @Version 1.0
 **/
@Service
public class HtmlEmailHandlerImpl extends AbstractHandler {
    @Override
    public String getBizNum() {
        return DataServiceEnum.HTML_EMAIL.getCode();
    }

    @Override
    public void sendEmail(EmailParam emailParam) {
        super.sendEmail(emailParam);
    }

    @Override
    protected ResultData sendMessage(EmailParam emailParam, Session session) {
        try{
            // 创建默认的 MimeMessage 对象。
            MimeMessage message = new MimeMessage(session);
            String nickName = MimeUtility.encodeText(emailParam.nickName);
            String from = MimeUtility.encodeText("Chow_V587@163.com");
            message.setFrom(new InternetAddress(nickName+" <"+from+">"));

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

            // 创建消息部分
            BodyPart messageBodyPart = new MimeBodyPart();

            // 消息
            messageBodyPart.setText("This is message body");

            // 创建多重消息
            Multipart multipart = new MimeMultipart();

            // 设置文本消息部分
            multipart.addBodyPart(messageBodyPart);

            // 附件部分
            messageBodyPart = new MimeBodyPart();
            DataSource source = new FileDataSource(emailParam.file);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(emailParam.file);
            multipart.addBodyPart(messageBodyPart);

            // 发送完整消息
            message.setContent(multipart );

            //   发送消息
            Transport.send(message);
            System.out.println("Sent message successfully....");
            return ResultData.success("发送成功");
        }catch (MessagingException mex) {
            mex.printStackTrace();
            return ResultData.error(CodeMessage.SERVER_ERROR);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return ResultData.error(CodeMessage.SERVER_ERROR);
        }
    }
}
