package com.alon.amqp.seckill;

import com.alon.amqp.config.MQConfig;
import com.alon.common.vo.GoodsVo;
import com.alon.impl.redis.util.RedisUtil;
import com.alon.impl.seckill.GoodsService;
import com.alon.impl.seckill.OrderService;
import com.alon.impl.seckill.SeckillService;
import com.alon.model.seckill.LtUser;
import com.alon.model.seckill.SeckillOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName MQReceiver
 * @Description TODO
 * @Author 一股清风
 * @Date 2019/5/17 15:52
 * @Version 1.0
 **/
@Service
@Slf4j
public class MQReceiver {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private SeckillService seckillService;

    @RabbitListener(queues= MQConfig.QUEUE)
    public void receive(String message){
        log.info("receive message:"+message);
        SeckillMessage m = redisUtil.stringToBean(message, SeckillMessage.class);
        LtUser user = m.getUser();
        long goodsId = m.getGoodsId();

        GoodsVo goodsVo = goodsService.getGoodsVoByGoodsId(goodsId);
        int stock = goodsVo.stockCount;
        if(stock <= 0){
            return;
        }

        //判断重复秒杀
        SeckillOrder order = orderService.getOrderByUserIdGoodsId(user.getId(), goodsId);
        if(order != null) {
            return;
        }

        //减库存 下订单 写入秒杀订单
        seckillService.seckill(user, goodsVo);
    }

    @RabbitListener(queues = MQConfig.TOPIC_QUEUE1)
    public void receiveTopic1(String message) {
        log.info(" topic  queue1 message:" + message);
    }

    @RabbitListener(queues = MQConfig.TOPIC_QUEUE2)
    public void receiveTopic2(String message) {
        log.info(" topic  queue2 message:" + message);
    }

}
