package com.alon.impl.seckill;

import com.alon.common.vo.GoodsVo;
import com.alon.mapper.dao.seckill.GoodsMapper;
import com.alon.model.seckill.SeckillGoods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName GoodsService
 * @Description TODO
 * @Author 一股清风
 * @Date 2019/5/17 15:56
 * @Version 1.0
 **/
@Service
public class GoodsService {

    //乐观锁冲突最大重试次数
    private static final int DEFAULT_MAX_RETRIES = 5;

    @Autowired
    private GoodsMapper goodsMapper;

    /**
      * 方法表述: 查询商品列表
      * @Author 一股清风
      * @Date 16:00 2019/5/17
      * @param
      * @return java.util.List<com.alon.common.vo.GoodsVo>
    */
    public List<GoodsVo> listGoodsVo() {
        return goodsMapper.listGoodsVo();
    }

    /**
      * 方法表述: 根据id查询指定商品
      * @Author 一股清风
      * @Date 16:00 2019/5/17
      * @param       goodsId
      * @return com.alon.common.vo.GoodsVo
    */
    public GoodsVo getGoodsVoByGoodsId(long goodsId) {
        return goodsMapper.getGoodsVoByGoodsId(goodsId);
    }

    /**
      * 方法表述: 减少库存，每次减一
      * @Author 一股清风
      * @Date 16:02 2019/5/17
      * @param       goods
      * @return boolean
    */
    public boolean reduceStock(GoodsVo goods) {
        int numAttempts = 0;
        int ret = 0;
        SeckillGoods sg = new SeckillGoods();
        sg.setGoodsId(goods.goodId);
        sg.setVersion(goods.version);
        do {
            numAttempts++;
            try {
                sg.setVersion(goodsMapper.getVersionByGoodsId(goods.goodId));
                ret = goodsMapper.reduceStockByVersion(sg);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (ret != 0)
                break;
        } while (numAttempts < DEFAULT_MAX_RETRIES);

        return ret > 0;
    }

}
