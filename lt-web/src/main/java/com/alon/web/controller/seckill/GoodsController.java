package com.alon.web.controller.seckill;

import com.alon.common.result.ResultData;
import com.alon.common.vo.GoodsVo;
import com.alon.impl.redis.key.GoodsKey;
import com.alon.impl.redis.util.RedisUtil;
import com.alon.impl.seckill.GoodsService;
import com.alon.impl.seckill.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName GoodsController
 * @Description TODO
 * @Author 一股清风
 * @Date 2019/5/20 16:13
 * @Version 1.0
 **/
@RestController
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private GoodsService goodsService;

    @PostMapping("/list")
    public ResultData list() {

        //取缓存
        String html = redisUtil.get(GoodsKey.getGoodsList, "", String.class);
        if (StringUtils.isNotBlank(html)) {
            return ResultData.success(html);
        }
        List<GoodsVo> list = goodsService.listGoodsVo();
        String goodsList = RedisUtil.beanToString(list);
        redisUtil.set(GoodsKey.getGoodsList,"",list);
        return ResultData.success(goodsList);
    }
}
