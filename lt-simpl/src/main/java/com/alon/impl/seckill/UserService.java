package com.alon.impl.seckill;

import com.alibaba.druid.util.StringUtils;
import com.alon.common.result.CodeMessage;
import com.alon.common.utils.MD5Util;
import com.alon.common.utils.UUIDUtil;
import com.alon.common.vo.LoginVo;
import com.alon.impl.exception.GlobalException;
import com.alon.impl.redis.key.UserKey;
import com.alon.impl.redis.util.RedisUtil;
import com.alon.mapper.dao.seckill.LtUserMapper;
import com.alon.model.seckill.LtUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName UserService
 * @Description TODO
 * @Author 一股清风
 * @Date 2019/5/20 14:26
 * @Version 1.0
 **/
@Service
public class UserService {

    @Autowired
    private LtUserMapper userMapper;

    @Autowired
    private RedisUtil redisUtil;

    public static final String COOKIE_NAME_TOKEN = "token";

    /**
      * 方法表述: 根据id查询用户
      * @Author 一股清风
      * @Date 14:32 2019/5/20
      * @param       id
      * @return com.alon.model.seckill.LtUser
    */
    public LtUser getById(long id) {
        //对象缓存
        LtUser user = redisUtil.get(UserKey.getById, "" + id, LtUser.class);
        if (user != null) {
            return user;
        }
        //取数据库
        user = userMapper.getById(id);
        //再存入缓存
        if (user != null) {
            redisUtil.set(UserKey.getById, "" + id, user);
        }
        return user;
    }

    /**
      * 方法表述: 典型缓存同步场景：更新密码
      * @Author 一股清风
      * @Date 14:54 2019/5/20
      * @param       token
     * @param       id
     * @param       formPass
      * @return boolean
    */
    public boolean updatePassword(String token, long id, String formPass) {
        //取user
        LtUser user = getById(id);
        if(user == null) {
            throw new GlobalException(CodeMessage.MOBILE_NOT_EXIST);
        }
        //更新数据库
        LtUser toBeUpdate = new LtUser();
        toBeUpdate.setId(id);
        toBeUpdate.setPassword(MD5Util.formPassToDBPass(formPass, user.getSalt()));
        userMapper.update(toBeUpdate);
        //更新缓存：先删除再插入
        redisUtil.del(UserKey.getById.getPrefix().concat(""+id));
        user.setPassword(toBeUpdate.getPassword());
        redisUtil.set(UserKey.token, token, user);
        return true;
    }

    public String login(HttpServletResponse response, LoginVo loginVo) {
        if (loginVo == null) {
            throw new GlobalException(CodeMessage.SERVER_ERROR);
        }
        String mobile = loginVo.getMobile();
        String formPass = loginVo.getPassword();
        //判断手机号是否存在
        LtUser user = getById(Long.parseLong(mobile));
        if (user == null) {
            throw new GlobalException(CodeMessage.MOBILE_NOT_EXIST);
        }
        //验证密码
        String dbPass = user.getPassword();
        String saltDB = user.getSalt();
        String calcPass = MD5Util.formPassToDBPass(formPass, saltDB);
        if (!calcPass.equals(dbPass)) {
            throw new GlobalException(CodeMessage.PASSWORD_ERROR);
        }
        //生成唯一id作为token
        String token = UUIDUtil.uuid();
        addCookie(response, token, user);
        return token;
    }

    /**
      * 方法表述: 将token做为key，用户信息做为value 存入redis模拟session 同时将token存入cookie，保存登录状态
      * @Author 一股清风
      * @Date 14:53 2019/5/20
      * @param       response
     * @param       token
     * @param       user
      * @return void
    */
    public void addCookie(HttpServletResponse response, String token, LtUser user) {
        redisUtil.set(UserKey.token, token, user);
        Cookie cookie = new Cookie(COOKIE_NAME_TOKEN, token);
        cookie.setMaxAge(UserKey.token.expireSeconds());
        cookie.setPath("/");//设置为网站根目录
        response.addCookie(cookie);
    }

    /**
      * 方法表述: 根据token获取用户信息
      * @Author 一股清风
      * @Date 14:53 2019/5/20
      * @param       response
     * @param       token
      * @return com.alon.model.seckill.LtUser
    */
    public LtUser getByToken(HttpServletResponse response, String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        LtUser user = redisUtil.get(UserKey.token, token, LtUser.class);
        //延长有效期，有效期等于最后一次操作+有效期
        if (user != null) {
            addCookie(response, token, user);
        }
        return user;
    }
}
