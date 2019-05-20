package com.alon.springExchange.exchange;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName News
 * @Description TODO
 * @Author 一股清风
 * @Date 2019/5/15 17:22
 * @Version 1.0
 **/
public class News implements Serializable {
    /**
     * 来源
     */
    private String source;
    private String title;
    private Date createTime;
    private String content;

    public News(String source, String title, Date createTime, String content) {
        this.source = source;
        this.title = title;
        this.createTime = createTime;
        this.content = content;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
