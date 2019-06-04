package com.alon.common.template;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName MsgPushTemplate
 * @Description TODO
 * @Author 一股清风
 * @Date 2019/5/30 10:36
 * @Version 1.0
 **/
@Slf4j
public class MsgPushTemplate {
    /**
     * 解析模板
     *
     * @param cfgId
     * @param templateValue
     * @throws IOException
     * @throws TemplateException
     */
    public static String processTemplate( String cfgId, Map<String, Object> templateValue){
        String configValue = "{\n" +
                "\"templateId\":\"PeQDVFS4Icmg0iLsZTcqxQkIlmT2jT0fj_CtrM9RMbE\",\n" +
                "\"url\":\"http://xunlong.dev.swiftpass.cn/mall?trans=orderDetail&orderNo=${orderNo!}\",\n" +
                "\"first\":{\"value\":\"支付成功通知\",\"color\":\"#173177\"},\n" +
                "\"keyword1\":{\"value\":\"${orderNo!}\",\"color\":\"#173177\"},\n" +
                "\"keyword2\":{\"value\":\"${products!}\",\"color\":\"#173177\"},\n" +
                "\"keyword3\":{\"value\":\"${payFee!}元\",\"color\":\"#173177\"},\n" +
                "\"keyword4\":{\"value\":\"${statusName!}\",\"color\":\"#173177\"},\n" +
                "\"keyword5\":{\"value\":\"${createTime!}\",\"color\":\"#173177\"},\n" +
                "\"remark\":{\"value\":\"客服电话：4008831872\",\"color\":\"#173177\"}\n" +
                "}";
        String rest = null;
        if (StringUtils.isEmpty(configValue)) {
            log.error(String.format("渲染模板错误,平台配置项[%s],没有配置值", cfgId));
        } else {
            try {
                StringWriter stringWriter = new StringWriter();
                Template template = new Template(cfgId, configValue, configuration());
                template.process(templateValue, stringWriter);
                rest = stringWriter.toString();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TemplateException e) {
                e.printStackTrace();
            }
        }
        return rest;
    }

    /**
     * 配置 freemarker configuration
     *
     * @return
     */
    private static Configuration configuration() {
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_27);
        StringTemplateLoader templateLoader = new StringTemplateLoader();
        configuration.setTemplateLoader(templateLoader);
        configuration.setDefaultEncoding("UTF-8");
        return configuration;
    }

    public static void main(String[] args) throws Exception {
        Map<String, Object> templateValue = new HashMap<String, Object>(6);
        templateValue.put("title","test123456");
        templateValue.put("orderNo","123456");
        templateValue.put("toUser","Alon");
        templateValue.put("payTime","2019-05-30");
        templateValue.put("remark","666");
        processTemplate("SEND",templateValue);
    }
}
