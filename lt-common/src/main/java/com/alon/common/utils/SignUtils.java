package com.alon.common.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.xml.sax.InputSource;

import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

/**
 * 功能描述:SignUtils Function: 签名用的工具箱
 * @Author : zoujiulong
 * @Date : 2018/9/6   14:37
 */
public class SignUtils {

	/**
	  * 方法表述: 验证返回参数
	  * @Author 一股清风
	  * @Date 15:29 2019/5/13
	  * @param       params
	 * @param       key
	  * @return boolean
	*/
	public static boolean checkParam(Map<String, String> params, String key) {
		boolean result = false;
		String charset = params.get("charset");
		if(StringUtils.isBlank(charset)){
			charset = "UTF-8";
		}
		if (params.containsKey("sign")) {
			String sign = params.get("sign");
			params.remove("sign");
			StringBuilder buf = new StringBuilder((params.size() + 1) * 10);
			SignUtils.buildPayParams(buf, params, false);
			String preStr = buf.toString();
			String signRecieve = sign(preStr, "&key=" + key, charset);
			result = sign.equalsIgnoreCase(signRecieve);
		}
		return result;
	}

	/**
	 * 功能描述:过滤参数
	 * @param:
	 * @return:
	 * @auther: zoujiulong
	 * @date: 2018/9/6   14:39
	 */
	public static Map<String, String> paraFilter(Map<String, String> sArray) {
		Map<String, String> result = null;
		if (sArray == null || sArray.size() <= 0) {
			return result;
		}
		result = new HashMap<String, String>(sArray.size());
		for (String key : sArray.keySet()) {
			String value = sArray.get(key);
			if (StringUtils.isBlank(value) || key.equalsIgnoreCase("sign")) {
				continue;
			}
			result.put(key, value);
		}
		return result;
	}

	/**
	 * 功能描述:将map转成String
	 * @param:
	 * @return:
	 * @auther: zoujiulong
	 * @date: 2018/9/6   14:39
	 */
	public static String payParamsToString(Map<String, String> payParams) {
		return payParamsToString(payParams, false);
	}

	public static String payParamsToString(Map<String, String> payParams, boolean encoding) {
		return payParamsToString(new StringBuilder(), payParams, encoding);
	}

	/**
	 * @author
	 * @param payParams
	 * @return
	 */
	public static String payParamsToString(StringBuilder sb, Map<String, String> payParams, boolean encoding) {
		buildPayParams(sb, payParams, encoding);
		return sb.toString();
	}

	/**
	 * @author
	 * @param payParams
	 * @return
	 */
	public static void buildPayParams(StringBuilder sb, Map<String, String> payParams, boolean encoding) {
		List<String> keys = new ArrayList<String>(payParams.keySet());
		Collections.sort(keys);
		for (String key : keys) {
			sb.append(key).append("=");
			if (encoding) {
				sb.append(urlEncode(payParams.get(key)));
			} else {
				sb.append(payParams.get(key));
			}
			sb.append("&");
		}
		sb.setLength(sb.length() - 1);
	}

	public static String urlEncode(String str) {
		try {
			return URLEncoder.encode(str, "UTF-8");
		} catch (Throwable e) {
			return str;
		}
	}

	public static Element readerXml(String body, String encode) throws DocumentException {
		SAXReader reader = new SAXReader(false);
		InputSource source = new InputSource(new StringReader(body));
		source.setEncoding(encode);
		Document doc = reader.read(source);
		Element element = doc.getRootElement();
		return element;
	}

	/**
	 * 功能描述:签名字符串
	 * @param: text  需要签名的字符串 key 密钥 input_charset 编码格式
	 * @return:
	 * @auther: zoujiulong
	 * @date: 2018/9/6   14:46
	 */
	public static String sign(String text, String key, String input_charset) {
		text = text + key;
		return DigestUtils.md5Hex(getContentBytes(text, input_charset));
	}

	/**
	 * 功能描述:
	 * @param:
	 * @return:
	 * @auther: zoujiulong
	 * @date: 2018/9/6   14:47
	 */
	private static byte[] getContentBytes(String content, String charset) {
		if (charset == null || "".equals(charset)) {
			return content.getBytes();
		}
		try {
			return content.getBytes(charset);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:" + charset);
		}
	}

    public static String sign(String signKey, Map<String, String> params) {
        try {
        	String charset = params.get("charset");
    		if(StringUtils.isBlank(charset)){
    			charset = "UTF-8";
    		}
            params = SignUtils.paraFilter(params); // 过滤掉一些不需要签名的字段
            StringBuilder buf = new StringBuilder((params.size() + 1) * 10);
            SignUtils.buildPayParams(buf, params, false);
            buf.append("&key=").append(signKey);
            String preStr = buf.toString();
            String sign = DigestUtils.md5Hex(preStr.getBytes(charset)).toUpperCase();
            return sign;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

	/**
	 * 功能描述:是否签名正确,规则是:按参数名称a-z排序,遇到空值的参数不参加签名。
	 * @param:
	 * @return:
	 * @auther: zoujiulong
	 * @date: 2018/9/6   15:46
	 */
	public static boolean isTenpaySign(String characterEncoding, Map<Object, Object> packageParams, String API_KEY) {
		StringBuffer sb = new StringBuffer();
		Set es = packageParams.entrySet();
		Iterator it = es.iterator();
		while(it.hasNext()) {
			Map.Entry entry = (Map.Entry)it.next();
			String k = (String)entry.getKey();
			String v = (String)entry.getValue();
			if(!"sign".equals(k) && null != v && !"".equals(v)) {
				sb.append(k + "=" + v + "&");
			}
		}

		sb.append("key=" + API_KEY);

		//算出摘要
		String mysign = MD5Util.MD5Encode(sb.toString(), characterEncoding).toLowerCase();
		String tenpaySign = ((String)packageParams.get("sign")).toLowerCase();

		//System.out.println(tenpaySign + "    " + mysign);
		return tenpaySign.equals(mysign);
	}

}
