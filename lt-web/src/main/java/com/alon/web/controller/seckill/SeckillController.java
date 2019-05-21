package com.alon.web.controller.seckill;

import com.alon.amqp.seckill.MQSender;
import com.alon.amqp.seckill.SeckillMessage;
import com.alon.common.result.CodeMessage;
import com.alon.common.result.ResultData;
import com.alon.common.vo.GoodsVo;
import com.alon.impl.redis.key.GoodsKey;
import com.alon.impl.redis.util.RedisUtil;
import com.alon.impl.seckill.GoodsService;
import com.alon.impl.seckill.OrderService;
import com.alon.impl.seckill.SeckillService;
import com.alon.model.seckill.LtUser;
import com.alon.model.seckill.SeckillOrder;
import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName SeckillController
 * @Description TODO
 * @Author 一股清风
 * @Date 2019/5/20 18:23
 * @Version 1.0
 **/
@RestController
@RequestMapping("seckill")
@Slf4j
public class SeckillController implements InitializingBean {
    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private MQSender sender;

    @Autowired
    private SeckillService seckillService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OrderService orderService;

    //基于令牌桶算法的限流实现类
    private RateLimiter rateLimiter = RateLimiter.create(10);

    //做标记，判断该商品是否被处理过了
    private HashMap<Long, Boolean> localOverMap = new HashMap<Long, Boolean>();

    @PostMapping("do_seckill")
    public ResultData<Integer> list(long goodsId, LtUser user) {

        if (!rateLimiter.tryAcquire(1000, TimeUnit.MILLISECONDS)) {
            return  ResultData.error(CodeMessage.ACCESS_LIMIT_REACHED);
        }

       /* if (user == null) {
            return Result.error(CodeMessage.SESSION_ERROR);
        }*/

        //内存标记，减少redis访问
        boolean over = localOverMap.get(goodsId);
        if (over) {
            return ResultData.error(CodeMessage.SECKILL_OVER);
        }
        //预减库存
        long stock = redisUtil.decr(GoodsKey.getGoodsStock.getPrefix().concat(String.valueOf(goodsId)),1);//10
        if (stock < 0) {
            afterPropertiesSet();
            long stock2 = redisUtil.decr(GoodsKey.getGoodsStock.getPrefix().concat(String.valueOf(goodsId)),1);//10
            if(stock2 < 0){
                localOverMap.put(goodsId, true);
                return ResultData.error(CodeMessage.SECKILL_OVER);
            }
        }
        //判断重复秒杀
        SeckillOrder order = orderService.getOrderByUserIdGoodsId(user.getId(), goodsId);
        if (order != null) {
            return ResultData.error(CodeMessage.REPEATE_SECKILL);
        }
        //入队
        SeckillMessage message = new SeckillMessage();
        message.setUser(user);
        message.setGoodsId(goodsId);
        sender.sendSeckillMessage(message);
        return ResultData.success(0);//排队中
    }

    @Override
    public void afterPropertiesSet(){
        try {
            List<GoodsVo> goodsVoList = goodsService.listGoodsVo();
            if (goodsVoList == null) {
                return;
            }
            for (GoodsVo goods : goodsVoList) {
                redisUtil.set(GoodsKey.getGoodsStock, "" + goods.id, goods.stockCount);
                //初始化商品都是没有处理过的
                localOverMap.put(goods.id, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.info("==========-------==========" + e.getMessage());
        }
    }
}
