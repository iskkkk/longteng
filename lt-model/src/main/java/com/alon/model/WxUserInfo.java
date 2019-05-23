package com.alon.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName WxUserInfo
 * @Description TODO
 * @Author 一股清风
 * @Date 2019/5/21 18:21
 * @Version 1.0
 **/
@Data
public class WxUserInfo implements Serializable {
    private Long id;
    private String city;
    private String country;
    private String headImgUrl;
    private String language;
    private String nickName;
    private String openid;
    private String privilege;
    private String province;
    private String scope;
    private Integer sex;
    /**
     *二维码扫码场景（开发者自定义）
     */
    private Integer qrScene;
    /**
     * 用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息。
     */
    private Integer subscribe;
    /**
     * 用户被打上的标签ID列表
     */
    private String tagIdList;
    /**
     * 用户所在的分组ID（兼容旧的用户分组接口）
     */
    private Integer groupId;
    /**
     * 公众号运营者对粉丝的备注，公众号运营者可在微信公众平台用户管理界面对粉丝添加备注
     */
    private String remark;
    /**
     * 用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间
     */
    private Date subscribeTime;
    /**
     * 返回用户关注的渠道来源，ADD_SCENE_SEARCH 公众号搜索，
     * ADD_SCENE_ACCOUNT_MIGRATION 公众号迁移，ADD_SCENE_PROFILE_CARD 名片分享，
     * ADD_SCENE_QR_CODE 扫描二维码，ADD_SCENEPROFILE LINK 图文页内名称点击，
     * ADD_SCENE_PROFILE_ITEM 图文页右上角菜单，ADD_SCENE_PAID 支付后关注，ADD_SCENE_OTHERS 其他
     */
    private String subscribeScene;
    /**
     * 二维码扫码场景描述（开发者自定义）
     */
    private String qrSceneStr;
    /**
     * 只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。
     */
    private String unionId;
}
