package com.alon.common.vo.wx;

import java.util.List;

/**
 * @ClassName WxFansList
 * @Description 用户关注列表
 * @Author 一股清风
 * @Date 2019/5/27 16:41
 * @Version 1.0
 **/
public class WxFansList {
    /**
     * 关注该公众账号的总用户数
     */
    public Integer total;
    /**
     * 拉取的OPENID个数，最大值为10000
     */
    public Integer count;
    /**
     * 列表数据，OPENID的列表
     */
    public List data;
    /**
     * 拉取列表的最后一个用户的OPENID
     */
    public String nextOpenid;
    /**
     * 错误编码
     */
    public Integer errcode;
    /**
     * 错误信息
     */
    public String errmsg;

}
