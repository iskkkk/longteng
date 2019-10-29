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
    public String content = "李四： <br/>" +
            "您好，您在本论坛注册用户，点击下面url进行激活<br/>" +
            "http://ww......<br/>" +
            "如果不能点击，请复制直接激活<br/>" +
            "如果不是本人，请删除邮件";
    public String subject;

    public String cc;
    public String bcc;

    public EmailParam(String toUser, String nickName, String content, String subject, String cc, String bcc) {
        this.toUser = toUser;
        this.nickName = nickName;
        this.content = content;
        this.subject = subject;
        this.cc = cc;
        this.bcc = bcc;
    }
}
