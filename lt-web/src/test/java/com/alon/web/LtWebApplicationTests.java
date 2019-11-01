package com.alon.web;

import com.alon.amqp.model.Employee;
import com.alon.amqp.producer.MessageProducer;
import com.alon.common.msg.EmailParam;
import com.alon.common.msg.strategy.engine.SendMailEngineProcess;
import com.alon.impl.redis.util.RedisUtil;
import com.alon.service.third.wx.WxTokenService;
import io.searchbox.client.JestClient;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class LtWebApplicationTests {

    @Autowired
    private JestClient jestClient;

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private MessageProducer messageProducer;
    @Autowired
    private WxTokenService tokenService;

    @Autowired
    private SendMailEngineProcess handle;

    @Test
    public void contextLoads() {
        //自定义一个dsl语句
        String query = "{\n" +
                "  \"query\": {\n" +
                "    \"match\": {\n" +
                "      \"tag\": {\n" +
                "        \"query\": \"java elasticsearch\",\n" +
                "        \"operator\": \"and\"\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "}";
        //定义查询对象
        Search search = new Search.Builder(query).addIndex("forum").addType("article").build();
        try {
            SearchResult result = jestClient.execute(search);
            if (StringUtils.isBlank(result.getErrorMessage())) {
                List<SearchResult.Hit<Map, Void>> hits = result.getHits(Map.class);
                for (SearchResult.Hit<Map, Void> hit : hits) {
                    Map map = hit.source;
                    log.info("数据：" + map);
                }
            } else {
                log.error("错误：" + result.getErrorMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("异常：" + e.getMessage());
        }
    }

    @Test
    public void testAmqp() {
        messageProducer.sendMessage(new Employee("3306","Alon",18));
    }

    @Test
    public void testDBU() {
//        String token = (String) tokenService.getAccessToken().getData();
//        WxFansList list = WxTokenUtil.getFansListUrl(token, "");
//        redisUtil.set("next_openid",list.nextOpenid,0L);
//        List<WxUserBaseInfoVo> baseInfoVos = new ArrayList<WxUserBaseInfoVo>();
//        list.data.forEach(i -> {
//            baseInfoVos.add(WxTokenUtil.getMoreInfo(token, i.toString()));
//        });
//        redisUtil.set("userInfo",baseInfoVos, RedisConstants.datebase2,0L);
    }

    @Test
    public void sendMessage() {
//        String token = (String) tokenService.getAccessToken().getData();
//        SendOrderPaySuccessMsg.sendMessage(token,"onhAr1eEQ8QUJroJ3fiIgixLPW24");
        String content = "test123212";
        String cc = "";
        String bcc = "";
        EmailParam emailParam = new EmailParam("1442882303@qq.com","123",content,"111111",cc,bcc);
        emailParam.bizNum = "1000001";
        emailParam.file = "D:\\工单附件\\工单导入模板.xlsx";
        handle.sendMail(emailParam);
    }

}
