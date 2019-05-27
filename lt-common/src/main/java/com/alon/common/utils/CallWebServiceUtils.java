package com.alon.common.utils;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.commons.collections.map.HashedMap;

import java.util.Map;

/**
 * @ClassName CallWebServiceUtils
 * @Description call调用webservice接口用具类
 * @Author 一股清风
 * @Date 2019/5/27 13:56
 * @Version 1.0
 **/
public class CallWebServiceUtils {
    /**
      * 方法表述: call调用webservice接口
      * @Author 一股清风
      * @Date 13:57 2019/5/27
      * @param       url 接口地址
     * @param       nameSpace 命名空间
     * @param       methodName 方法名
     * @param       paramMap 参数Map
      * @return org.apache.axiom.om.OMElement
    */
    public static OMElement callWebServiceAxis2(String url, String nameSpace, String methodName, Map<String, Object> paramMap) throws Exception {
        OMElement result = null;
        try {
            ServiceClient serviceClient = new ServiceClient();
            //创建WebService的URL
            EndpointReference targetEPR = new EndpointReference(url);
            Options options = serviceClient.getOptions();
            options.setTo(targetEPR);
            //确定调用方法（ 命名空间地址 (namespace) 和 方法名称）
            options.setAction(nameSpace + methodName);
            OMFactory fac = OMAbstractFactory.getOMFactory();
            OMNamespace omNs = fac.createOMNamespace(nameSpace, "");
            OMElement method = fac.createOMElement(methodName, omNs);
            // 遍历传入方法的参数
            for (String key : paramMap.keySet()) {
                OMElement element = fac.createOMElement(key, omNs);
                Object obj = paramMap.get(key);
                if (obj != null) {
                    element.setText(paramMap.get(key).toString());
                }
                method.addChild(element);
            }
            method.build();
            //调用接口
            result = serviceClient.sendReceive(method);
            String resultTexe = result.getFirstElement().getText();
        } catch (AxisFault axisFault) {
            axisFault.printStackTrace();
        }
        return result;
    }

    /**
      * 方法表述: call调用webservice接口
      * @Author 一股清风
      * @Date 13:58 2019/5/27
      * @param       url 接口地址
     * @param       nameSpace 命名空间
     * @param       methodName 方法名
     * @param       paramMap 参数Map
      * @return java.lang.String
    */
    public static String callWebServiceAxis2Text(String url, String nameSpace, String methodName, Map<String, Object> paramMap) throws Exception {
        OMElement element = callWebServiceAxis2(url, nameSpace, methodName, paramMap);
        String resultTexe = "";
        if (element != null) {
            resultTexe = element.getFirstElement().getText();
        }
        return resultTexe;
    }

    public static void main(String[] args) {
        String url = "http://39.102.123.17:9000/PushUserService.asmx";
        String nameSpace = "http://groms.com/";
        String methodName = "CloseAccount";
        Map<String, Object> paramMap = new HashedMap();
        paramMap.put("id","123456");
        paramMap.put("userCode","test123");
        paramMap.put("unitId","orgId001");
        paramMap.put("unitName","斯蒂芬森公司");
        try {
            String result = callWebServiceAxis2Text(url,nameSpace,methodName,paramMap);
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
