package com.alon.impl.seckill;

import com.alon.common.vo.GoodsVo;
import com.alon.impl.redis.key.SeckillKey;
import com.alon.impl.redis.util.RedisUtil;
import com.alon.model.seckill.LtUser;
import com.alon.model.seckill.OrderInfo;
import com.alon.model.seckill.SeckillOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName SeckillService
 * @Description TODO
 * @Author 一股清风
 * @Date 2019/5/17 16:06
 * @Version 1.0
 **/
@Service
public class SeckillService {
    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private RedisUtil redisUtil;

    @Transactional
    public OrderInfo seckill(LtUser user, GoodsVo goods){
        //减库存
        boolean success = goodsService.reduceStock(goods);
        if (success){
            //下订单 写入秒杀订单
            return orderService.createOrder(user, goods);
        }else {
            setGoodsOver(goods.goodId);
            return null;
        }
    }

    public long getSeckillResult(long userId, long goodsId){
        SeckillOrder order = orderService.getOrderByUserIdGoodsId(userId, goodsId);
        if (order != null){
            return order.getOrderId();
        }else{
            boolean isOver = getGoodsOver(goodsId);
            if(isOver) {
                return -1;
            }else {
                return 0;
            }
        }
    }

    private void setGoodsOver(Long goodsId) {
        redisUtil.set(SeckillKey.isGoodsOver, ""+goodsId, true);
    }

    private boolean getGoodsOver(long goodsId) {
        return redisUtil.exists(SeckillKey.isGoodsOver, ""+goodsId);
    }


}
