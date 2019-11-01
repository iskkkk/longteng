package com.alon.common.msg;

/**
 * @ClassName EmailParam
 * @Description TODO
 * @Author zoujiulong
 * @Date 2019/10/29 11:16
 * @Version 1.0
 **/
public class EmailParam {

    public String toUser;
    public String nickName;
    public String content;
    public String subject;

    public String cc;
    public String bcc;

    public String bizNum;
    public String file;

    public EmailParam(String toUser, String nickName, String content, String subject, String cc, String bcc) {
        this.toUser = toUser;
        this.nickName = nickName;
        this.content = content;
        this.subject = subject;
        this.cc = cc;
        this.bcc = bcc;
    }
}
