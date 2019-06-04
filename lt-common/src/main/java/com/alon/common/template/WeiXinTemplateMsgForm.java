package com.alon.common.template;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;


/**
 * @ClassName MsgPushTemplate
 * @Description 微信模板消息接口VO
 * @Author 一股清风
 * @Date 2019/5/30 10:36
 * @Version 1.0
 **/
public class WeiXinTemplateMsgForm {
	/**openId(微信用户id)【必填项】*/
	private String openId;
	/**公众号的模板id【必填项】*/
	private String templateId;
	/**跳转链接(用于服务通知和办事结果页)【进入结果页必填】*/
	private String url;
	/**模板对应json数据，其中color字段只对服务通知有效【必填】*/
	private Map<String, Map<String, KeyWord>> data = new HashMap<String, Map<String, KeyWord>>();
	
	private KeyWord first;
	private KeyWord keyword1;
	private KeyWord keyword2;
	private KeyWord keyword3;
	private KeyWord keyword4;
	private KeyWord keyword5;
	private KeyWord keyword6;
	
	/**备注*/
	private KeyWord remark;
	
	public static class KeyWord {
		/**内容*/
		public String value;
		/**字体颜色*/
		public String color = "#173177";
		
		public KeyWord() {
			super();
		}
		
		public KeyWord(String value) {
			super();
			this.value = value;
		}

		public KeyWord(String value, String color) {
			super();
			this.value = value;
			this.color = color;
		}
	}
	
	/**
	  * 方法表述: 为城市服务请求设置公共参数
	  * @Author 一股清风
	  * @Date 10:46 2019/5/31
	  * @param       reqParamMap
	 * @param       pageVO
	  * @return java.util.Map<java.lang.String,java.lang.Object>
	*/
	private static Map<String, Object> addPublicParam(Map<String, Object> reqParamMap, WeiXinTemplateMsgForm pageVO) {
		if (null != reqParamMap && null != pageVO) {
			/*** 公共参数部分 begin ***/
			reqParamMap.put("touser", pageVO.openId);
			reqParamMap.put("template_id", pageVO.templateId);
			reqParamMap.put("url", pageVO.url);
			/*** 公共参数部分 end ***/
			
		}
		return reqParamMap;
	}
	
	/**
	  * 方法表述: 获取城市服务请求参数
	  * @Author 一股清风
	  * @Date 10:47 2019/5/31
	  * @param       pageVO
	  * @return java.lang.String
	*/
	public static String getRequestParam(WeiXinTemplateMsgForm pageVO) {
		//城市服务请求参数
		Map<String, Object> mapRequestParam = new HashMap<String, Object>();
		
		//为城市服务请求设置公共参数
		mapRequestParam = addPublicParam(mapRequestParam, pageVO);
		
		Map<String, Object> mapData = new HashMap<String, Object>();
		
		if (pageVO.first != null) {
			KeyWord first = new KeyWord(pageVO.first.value, pageVO.first.color);
			
			mapData.put("first", first);
		}
		
		if (pageVO.keyword1 != null) {
			KeyWord keyWord1 = new KeyWord(pageVO.keyword1.value, pageVO.keyword1.color);
			
			mapData.put("keyword1", keyWord1);
		}
		
		if (pageVO.keyword2 != null) {
			KeyWord keyword2 = new KeyWord(pageVO.keyword2.value, pageVO.keyword2.color);
			
			mapData.put("keyword2", keyword2);
		}
		
		if (pageVO.keyword3 != null) {
			KeyWord keyword3 = new KeyWord(pageVO.keyword3.value, pageVO.keyword3.color);
			
			mapData.put("keyword3", keyword3);
		}
		
		if (pageVO.keyword4 != null) {
			KeyWord keyword4 = new KeyWord(pageVO.keyword4.value, pageVO.keyword4.color);
			
			mapData.put("keyword4", keyword4);
		}
		
		if (pageVO.keyword5 != null) {
			KeyWord keyword5 = new KeyWord(pageVO.keyword5.value, pageVO.keyword5.color);
			
			mapData.put("keyword5", keyword5);
		}
		
		if (pageVO.keyword6 != null) {
			KeyWord keyword6 = new KeyWord(pageVO.keyword6.value, pageVO.keyword6.color);
			
			mapData.put("keyword6", keyword6);
		}
		
		mapRequestParam.put("data", mapData);
		
		if (pageVO.remark != null) {
			KeyWord remark = new KeyWord(pageVO.remark.value, pageVO.remark.color);
			
			mapData.put("remark", remark);
		}
		
		return JSON.toJSONString(mapRequestParam);
	}
	
	public String getOpenId() {
		return openId;
	}
	
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	
	public String getTemplateId() {
		return templateId;
	}
	
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public Map<String, Map<String, KeyWord>> getData() {
		return data;
	}
	
	public void setData(Map<String, Map<String, KeyWord>> data) {
		this.data = data;
	}
	
	public KeyWord getFirst() {
		return first;
	}
	
	public void setFirst(KeyWord first) {
		this.first = first;
	}
	
	public KeyWord getKeyword1() {
		return keyword1;
	}
	
	public void setKeyword1(KeyWord keyword1) {
		this.keyword1 = keyword1;
	}
	
	public KeyWord getKeyword2() {
		return keyword2;
	}
	
	public void setKeyword2(KeyWord keyword2) {
		this.keyword2 = keyword2;
	}
	
	public KeyWord getKeyword3() {
		return keyword3;
	}
	
	public void setKeyword3(KeyWord keyword3) {
		this.keyword3 = keyword3;
	}
	
	public KeyWord getKeyword4() {
		return keyword4;
	}
	
	public void setKeyword4(KeyWord keyword4) {
		this.keyword4 = keyword4;
	}
	
	public KeyWord getKeyword5() {
		return keyword5;
	}
	
	public void setKeyword5(KeyWord keyword5) {
		this.keyword5 = keyword5;
	}
	
	public KeyWord getKeyword6() {
		return keyword6;
	}
	
	public void setKeyword6(KeyWord keyword6) {
		this.keyword6 = keyword6;
	}
	
	public KeyWord getRemark() {
		return remark;
	}
	
	public void setRemark(KeyWord remark) {
		this.remark = remark;
	}
	
}