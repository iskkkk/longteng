package com.alon.mapper.dao.seckill;

import com.alon.common.vo.GoodsVo;
import com.alon.model.seckill.SeckillGoods;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @ClassName GoodsMapper
 * @Description TODO
 * @Author 一股清风
 * @Date 2019/5/17 15:58
 * @Version 1.0
 **/
@Mapper
public interface GoodsMapper {

    @Select("select g.*, sg.stock_count, sg.start_date, sg.end_date, sg.seckill_price, sg.version from lt_goods_seckill sg left join lt_goods g on sg.goods_id = g.id")
    public List<GoodsVo> listGoodsVo();

    @Select("select g.*, sg.stock_count, sg.start_date, sg.end_date, sg.seckill_price, sg.version  from lt_goods_seckill sg left join lt_goods g  on sg.goods_id = g.id where g.id = #{goodsId}")
    public GoodsVo getGoodsVoByGoodsId(@Param("goodsId") long goodsId);

    //stock_count > 0 和 版本号实现乐观锁 防止超卖
    @Update("update lt_goods_seckill set stock_count = stock_count - 1, version= version + 1 where goods_id = #{goodsId} and stock_count > 0 and version = #{version}")
    public int reduceStockByVersion(SeckillGoods seckillGoods);

    // 获取最新版本号
    @Select("select version from lt_goods_seckill  where goods_id = #{goodsId}")
    public int getVersionByGoodsId(@Param("goodsId") long goodsId);
}
