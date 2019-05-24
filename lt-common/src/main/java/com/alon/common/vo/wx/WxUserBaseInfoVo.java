package com.alon.common.vo.wx;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName WxUserBaseInfoVo
 * @Description TODO
 * @Author 一股清风
 * @Date 2019/5/21 16:24
 * @Version 1.0
 **/
@Data
public class WxUserBaseInfoVo implements Serializable {
    /**
     * 网页授权access_token ：此access_token与基础支持的access_token不同
     */
    public String accessToken;
    /**
     * 过期时间
     */
    public Integer expiresIn;
    /**
     * 用户的标识，对当前公众号唯一
     */
    public String openid;
    /**
     * 用户刷新access_token
     */
    public String refreshToken;
    /**
     * 用户授权的作用域，使用逗号（,）分隔
     */
    public String scope;

    public Integer errcode;
    public String errmsg;
    /**
     * 用户所在国家
     */
    public String country;
    /**
     * 用户所在省份
     */
    public String province;
    /**
     * 	用户所在城市
     */
    public String city;
    /**
     * 用户的昵称
     */
    public String nickName;
    /**
     * 用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
     */
    public Integer sex;
    /**
     * 用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），
     * 用户没有头像时该项为空。若用户更换头像，原有头像URL将失效。
     */
    public String headImgUrl;
    /**
     * 用户的语言，简体中文为zh_CN
     */
    public String language;
    public String privilege;
    /**
     *二维码扫码场景（开发者自定义）
     */
    public Integer qrScene;
    /**
     * 用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息。
     */
    public Integer subscribe;
    /**
     * 用户被打上的标签ID列表
     */
    public String tagIdList;
    /**
     * 用户所在的分组ID（兼容旧的用户分组接口）
     */
    public Integer groupId;
    /**
     * 公众号运营者对粉丝的备注，公众号运营者可在微信公众平台用户管理界面对粉丝添加备注
     */
    public String remark;
    /**
     * 用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间
     */
    public Date subscribeTime;
    /**
     * 返回用户关注的渠道来源，ADD_SCENE_SEARCH 公众号搜索，
     * ADD_SCENE_ACCOUNT_MIGRATION 公众号迁移，ADD_SCENE_PROFILE_CARD 名片分享，
     * ADD_SCENE_QR_CODE 扫描二维码，ADD_SCENEPROFILE LINK 图文页内名称点击，
     * ADD_SCENE_PROFILE_ITEM 图文页右上角菜单，ADD_SCENE_PAID 支付后关注，ADD_SCENE_OTHERS 其他
     */
    public String subscribeScene;
    /**
     * 二维码扫码场景描述（开发者自定义）
     */
    public String qrSceneStr;
    /**
     * 只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。
     */
    public String unionId;
}
