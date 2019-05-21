package com.alon.impl.seckill;

import com.alon.common.vo.GoodsVo;
import com.alon.impl.redis.key.OrderKey;
import com.alon.impl.redis.util.RedisUtil;
import com.alon.mapper.dao.seckill.OrderMapper;
import com.alon.model.seckill.LtUser;
import com.alon.model.seckill.OrderInfo;
import com.alon.model.seckill.SeckillOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @ClassName OrderService
 * @Description TODO
 * @Author 一股清风
 * @Date 2019/5/17 16:03
 * @Version 1.0
 **/
@Service
@Slf4j
@Transactional(rollbackFor = RuntimeException.class)
public class OrderService {
    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private RedisUtil redisUtil;

    public SeckillOrder getOrderByUserIdGoodsId(long userId, long goodsId) {
        return redisUtil.get(OrderKey.getSeckillOrderByUidGid, "" + userId + "_" + goodsId, SeckillOrder.class);
    }

    public OrderInfo getOrderById(long orderId) {
        return orderMapper.getOrderById(orderId);
    }

    public OrderInfo createOrder(LtUser user, GoodsVo goods) {
        OrderInfo orderInfo = null;
        SeckillOrder seckillOrder = null;
        try {
            orderInfo = new OrderInfo();
            orderInfo.setCreateDate(new Date());
            orderInfo.setDeliveryAddrId(0L);
            orderInfo.setGoodsCount(1);
            orderInfo.setGoodsId(goods.goodId);
            orderInfo.setGoodsName(goods.goodsName);
            orderInfo.setGoodsPrice(goods.goodsPrice);
            orderInfo.setOrderChannel(1);
            orderInfo.setStatus(0);
            orderInfo.setUserId(user.getId());
            orderMapper.insert(orderInfo);

            seckillOrder = new SeckillOrder();
            seckillOrder.setGoodsId(goods.goodId);
            seckillOrder.setOrderId(orderInfo.getId());
            seckillOrder.setUserId(user.getId());
            orderMapper.insertSeckillOrder(seckillOrder);
            redisUtil.set(OrderKey.getSeckillOrderByUidGid, "" + user.getId() + "_" + goods.goodId, seckillOrder);
        } catch (RuntimeException e) {
            e.printStackTrace();
            log.error("保存订单失败：" + e.getMessage());
        }
        return orderInfo;
    }
}
