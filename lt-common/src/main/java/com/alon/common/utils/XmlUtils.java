package com.alon.common.utils;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.xml.sax.InputSource;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.*;

/**
 * 功能描述:XML的工具方法
 * @Author : zoujiulong
 * @Date : 2018/9/6   14:59
 */
public class XmlUtils {
    
    /**
      * 方法表述: request转字符串
      * @Author 一股清风
      * @Date 15:34 2019/5/13
      * @param       request
      * @return java.lang.String
    */
    public static String parseRequst(HttpServletRequest request){
        String body = "";
        BufferedReader br = null;
        try {
            ServletInputStream inputStream = request.getInputStream();
            br = new BufferedReader(new InputStreamReader(inputStream));
            while(true){
                String info = br.readLine();
                if(info == null){
                    break;
                }
                if(body == null || "".equals(body)){
                    body = info;
                }else{
                    body += info;
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
        	if(br != null){
        		try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
        	}
        }
        return body;
    }
    
	public static String parseXML(SortedMap<String, String> parameters) {
        StringBuffer sb = new StringBuffer();
        sb.append("<xml>");
        Set es = parameters.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry)it.next();
            String k = (String)entry.getKey();
            String v = (String)entry.getValue();
            if (null != v && !"".equals(v) && !"appkey".equals(k)) {
                sb.append("<" + k + ">" + parameters.get(k) + "</" + k + ">\n");
            }
        }
        sb.append("</xml>");
        return sb.toString();
    }
    /**
      * 方法表述: 从request中获得参数Map，并返回可读的Map
      * @Author 一股清风
      * @Date 15:35 2019/5/13
      * @param       request
      * @return java.util.SortedMap
    */
	public static SortedMap getParameterMap(HttpServletRequest request) {
        // 参数Map
        Map properties = request.getParameterMap();
        // 返回值Map
        SortedMap returnMap = new TreeMap();
        Iterator entries = properties.entrySet().iterator();
        Map.Entry entry;
        String name = "";
        String value = "";
        while (entries.hasNext()) {
            entry = (Map.Entry) entries.next();
            name = (String) entry.getKey();
            Object valueObj = entry.getValue();
            if(null == valueObj){
                value = "";
            }else if(valueObj instanceof String[]){
                String[] values = (String[])valueObj;
                for(int i=0;i<values.length;i++){
                    value = values[i] + ",";
                }
                value = value.substring(0, value.length()-1);
            }else{
                value = valueObj.toString();
            }
            returnMap.put(name, value.trim());
        }
        return returnMap;
    }
    
    /**
      * 方法表述: 转XMLmap
      * @Author 一股清风
      * @Date 15:35 2019/5/13
      * @param       xmlBytes
     * @param       charset
      * @return java.util.Map<java.lang.String,java.lang.String>
    */
    public static Map<String, String> toMap(byte[] xmlBytes,String charset) throws Exception{
        SAXReader reader = new SAXReader(false);
        InputSource source = new InputSource(new ByteArrayInputStream(xmlBytes));
        source.setEncoding(charset);
        Document doc = reader.read(source);
        Map<String, String> params = XmlUtils.toMap(doc.getRootElement());
        return params;
    }
    
    /**
      * 方法表述: 转MAP
      * @Author 一股清风
      * @Date 15:36 2019/5/13
      * @param       element
      * @return java.util.Map<java.lang.String,java.lang.String>
    */
	public static Map<String, String> toMap(Element element){
        Map<String, String> rest = new HashMap<String, String>();
        List<Element> els = element.elements();
        for(Element el : els){
            rest.put(el.getName().toLowerCase(), el.getTextTrim());
        }
        return rest;
    }
    /**
      * 方法表述: 读取xml信息
      * @Author 一股清风
      * @Date 15:36 2019/5/13
      * @param       params
     * @param       cdata
      * @return java.lang.String
    */
    public static String toXml(Map<String, String> params,boolean cdata){
        StringBuilder buf = new StringBuilder();
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);
        buf.append("<xml>");
        for(String key : keys){
            buf.append("<").append(key).append(">");
            if(cdata){
            	  buf.append("<![CDATA[");
            }
            buf.append(params.get(key));
            if(cdata){
            	 buf.append("]]>");
            }
            buf.append("</").append(key).append(">\n");
        }
        buf.append("</xml>");
        return buf.toString();
    }

    /**
      * 方法表述: 解析xml,返回第一级元素键值对。如果第一级元素有子节点，则此节点的值是子节点的xml数据。
      * @Author 一股清风
      * @Date 15:51 2019/5/13
      * @param       strxml
      * @return java.util.Map
    */
    public static Map doXMLParse(String strxml){
        if(null == strxml || "".equals(strxml)) {
            return null;
        }
        Map m = new HashMap();
        InputStream in = HttpClientUtils.String2Inputstream(strxml);
        SAXBuilder builder = new SAXBuilder();
        org.jdom2.Document doc = null;
        try {
            doc = builder.build(in);
            org.jdom2.Element root = doc.getRootElement();
            List list = root.getChildren();
            Iterator it = list.iterator();
            while(it.hasNext()) {
                org.jdom2.Element e = (org.jdom2.Element) it.next();
                String k = e.getName();
                String v = "";
                List children = e.getChildren();
                if(children.isEmpty()) {
                    v = e.getTextNormalize();
                } else {
                    v = XmlUtils.getChildrenText(children);
                }
                m.put(k, v);
            }
        } catch (JDOMException e) {
            e.printStackTrace();
            System.err.println("解析失败：" + e);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("读取失败：" + e);
        }finally {
            //关闭流
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("关闭流失败" + e);
            }
        }
        return m;
    }

    /**
      * 方法表述: 获取子结点的xml
      * @Author 一股清风
      * @Date 15:51 2019/5/13
      * @param       children
      * @return java.lang.String
    */
    public static String getChildrenText(List children) {
        StringBuffer sb = new StringBuffer();
        if(!children.isEmpty()) {
            Iterator it = children.iterator();
            while(it.hasNext()) {
                org.jdom2.Element e = (org.jdom2.Element) it.next();
                String name = e.getName();
                String value = e.getTextNormalize();
                List list = e.getChildren();
                sb.append("<" + name + ">");
                if(!list.isEmpty()) {
                    sb.append(XmlUtils.getChildrenText(list));
                }
                sb.append(value);
                sb.append("</" + name + ">");
            }
        }

        return sb.toString();
    }
}

