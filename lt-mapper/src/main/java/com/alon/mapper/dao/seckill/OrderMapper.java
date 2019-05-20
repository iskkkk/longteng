package com.alon.mapper.dao.seckill;

import com.alon.model.seckill.OrderInfo;
import com.alon.model.seckill.SeckillOrder;
import org.apache.ibatis.annotations.*;

/**
 * @ClassName OrderMapper
 * @Description TODO
 * @Author 一股清风
 * @Date 2019/5/17 16:04
 * @Version 1.0
 **/
@Mapper
public interface OrderMapper {

    @Select("select * from lt_order where user_id = #{userId} and goods_id = #{goodsId}")
    public SeckillOrder getOrderByUserIdGoodsId(@Param("userId") long userId, @Param("goodsId") long goodsId);


    /**
     * 通过@SelectKey使insert成功后返回主键id，也就是订单id
     * @param orderInfo
     * @return
     */
    @Insert("insert into lt_order_info(user_id, goods_id, goods_name, goods_count, goods_price, order_channel, status, create_date)values("
            + "#{userId}, #{goodsId}, #{goodsName}, #{goodsCount}, #{goodsPrice}, #{orderChannel},#{status},#{createDate} )")
    @SelectKey(keyColumn = "id", keyProperty = "id", resultType = long.class, before = false, statement = "select last_insert_id()")
    public long insert(OrderInfo orderInfo);


    @Insert("insert into lt_order (user_id, goods_id, order_id)values(#{userId}, #{goodsId}, #{orderId})")
    public int insertSeckillOrder(SeckillOrder order);

    @Select("select * from lt_order_info where id = #{orderId}")
    public OrderInfo getOrderById(@Param("orderId")long orderId);
}
