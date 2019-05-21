package com.alon.impl.redis.util;

import com.alibaba.fastjson.JSON;
import com.alon.impl.redis.MyRedisTemplate;
import com.alon.impl.redis.key.KeyPrefix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName RedisUtil
 * @Description TODO
 * @Author 一股清风
 * @Date 2019/5/20 10:17
 * @Version 1.0
 **/
@Lazy
@Component
public class RedisUtil {

    @Autowired
    private MyRedisTemplate myRedisTemplate;

    public void setRedisTemplate(MyRedisTemplate myRedisTemplate) {
        this.myRedisTemplate = myRedisTemplate;
    }

    /**
      * 方法表述: 指定缓存失效时间
      * @Author 一股清风
      * @Date 10:56 2019/5/20
      * @param       key
     * @param       time
      * @return boolean
    */
    public boolean expire(String key,long time){
        try {
            if(time>0){
                myRedisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
      * 方法表述: 根据key 获取过期时间
      * @Author 一股清风
      * @Date 10:57 2019/5/20
      * @param       key
      * @return long
    */
    public long getExpire(String key){
        return myRedisTemplate.getExpire(key,TimeUnit.SECONDS);
    }

    /**
      * 方法表述: 判断key是否存在
      * @Author 一股清风
      * @Date 10:57 2019/5/20
      * @param       key
      * @return boolean
    */
    public boolean hasKey(String key){
        try {
            return myRedisTemplate.hasKey(key);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
      * 方法表述: 删除缓存
      * @Author 一股清风
      * @Date 10:58 2019/5/20
      * @param       key  可以传一个值 或多个
      * @return void
    */
    @SuppressWarnings("unchecked")
    public void del(String ... key){
        if(key!=null&&key.length>0){
            if(key.length==1){
                myRedisTemplate.delete(key[0]);
            }else{
                myRedisTemplate.delete(CollectionUtils.arrayToList(key));
            }
        }
    }

    //============================String=============================
    /**
      * 方法表述: 普通缓存获取
      * @Author 一股清风
      * @Date 10:58 2019/5/20
      * @param       key
     * @param       indexdb
      * @return java.lang.Object
    */
    public Object get(String key, int indexdb){
        myRedisTemplate.indexdb.set(indexdb);
        return key==null?null:myRedisTemplate.opsForValue().get(key);
    }

    /**
      * 方法表述: 普通缓存放入
      * @Author 一股清风
      * @Date 10:59 2019/5/20
      * @param       key
     * @param       value
     * @param       indexdb
      * @return boolean true成功 false失败
    */
    public boolean set(String key,Object value,int indexdb,Long time) {
        try {
            myRedisTemplate.indexdb.set(indexdb);
            if(time>0){
                myRedisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            }else{
                myRedisTemplate.opsForValue().set(key, value);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
      * 方法表述: 普通缓存放入并设置时间
      * @Author 一股清风
      * @Date 11:00 2019/5/20
      * @param       key
     * @param       value
     * @param       time 时间(秒) time要大于0 如果time小于等于0 将设置无限期
      * @return boolean true成功 false 失败
    */
    public boolean set(String key,Object value,long time){
        try {
            if(time>0){
                myRedisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            }else{
                myRedisTemplate.opsForValue().set(key, value);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
      * 方法表述: 递增
      * @Author 一股清风
      * @Date 11:00 2019/5/20
      * @param       key
     * @param       delta 要增加几(大于0)
      * @return long
    */
    public long incr(String key, long delta){
        if(delta<0){
            throw new RuntimeException("递增因子必须大于0");
        }
        return myRedisTemplate.opsForValue().increment(key, delta);
    }

    /**
      * 方法表述: 递减
      * @Author 一股清风
      * @Date 11:00 2019/5/20
      * @param       key
     * @param       delta 要减少几(小于0)
      * @return long
    */
    public long decr(String key, long delta){
        if(delta<0){
            throw new RuntimeException("递减因子必须大于0");
        }
        return myRedisTemplate.opsForValue().increment(key, -delta);
    }

    //================================Map=================================
    /**
      * 方法表述: HashGet
      * @Author 一股清风
      * @Date 11:01 2019/5/20
      * @param       key 不能为null
     * @param       item 不能为null
      * @return java.lang.Object
    */
    public Object hget(String key,String item){
        return myRedisTemplate.opsForHash().get(key, item);
    }

    /**
      * 方法表述: 获取hashKey对应的所有键值
      * @Author 一股清风
      * @Date 11:01 2019/5/20
      * @param       key
      * @return java.util.Map<java.lang.Object,java.lang.Object> 对应的多个键值
    */
    public Map<Object,Object> hmget(String key){
        return myRedisTemplate.opsForHash().entries(key);
    }

    /**
      * 方法表述: HashSet
      * @Author 一股清风
      * @Date 11:02 2019/5/20
      * @param       key
     * @param       map 对应多个键值
      * @return boolean true 成功 false 失败
    */
    public boolean hmset(String key, Map<String,Object> map){
        try {
            myRedisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
      * 方法表述: HashSet 并设置时间
      * @Author 一股清风
      * @Date 11:02 2019/5/20
      * @param       key
     * @param       map 对应多个键值
     * @param       time 时间(秒)
      * @return boolean true成功 false失败
    */
    public boolean hmset(String key, Map<String,Object> map, long time){
        try {
            myRedisTemplate.opsForHash().putAll(key, map);
            if(time>0){
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
      * 方法表述: 向一张hash表中放入数据,如果不存在将创建
      * @Author 一股清风
      * @Date 11:03 2019/5/20
      * @param       key 键
     * @param       item 项
     * @param       value 值
      * @return boolean true 成功 false失败
    */
    public boolean hset(String key,String item,Object value) {
        try {
            myRedisTemplate.opsForHash().put(key, item, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     * @param key 键
     * @param item 项
     * @param value 值
     * @param time 时间(秒)  注意:如果已存在的hash表有时间,这里将会替换原有的时间
     * @return true 成功 false失败
     */
    public boolean hset(String key,String item,Object value,long time) {
        try {
            myRedisTemplate.opsForHash().put(key, item, value);
            if(time>0){
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
      * 方法表述: 删除hash表中的值
      * @Author 一股清风
      * @Date 11:04 2019/5/20
      * @param       key 不能为null
     * @param       item 可以使多个 不能为null
      * @return void
    */
    public void hdel(String key, Object... item){
        myRedisTemplate.opsForHash().delete(key,item);
    }

    /**
      * 方法表述: 判断hash表中是否有该项的值
      * @Author 一股清风
      * @Date 11:04 2019/5/20
      * @param       key 不能为null
     * @param       item 不能为null
      * @return boolean
    */
    public boolean hHasKey(String key, String item){
        return myRedisTemplate.opsForHash().hasKey(key, item);
    }

    /**
      * 方法表述: hash递增 如果不存在,就会创建一个 并把新增后的值返回
      * @Author 一股清风
      * @Date 11:05 2019/5/20
      * @param       key
     * @param       item 项
     * @param       by 要增加几(大于0
      * @return double
    */
    public double hincr(String key, String item,double by){
        return myRedisTemplate.opsForHash().increment(key, item, by);
    }

    /**
      * 方法表述: hash递减
      * @Author 一股清风
      * @Date 11:05 2019/5/20
      * @param       key
     * @param       item
     * @param       by 要减少记(小于0)
      * @return double
    */
    public double hdecr(String key, String item,double by){
        return myRedisTemplate.opsForHash().increment(key, item,-by);
    }

    //============================set=============================
    /**
      * 方法表述: 根据key获取Set中的所有值
      * @Author 一股清风
      * @Date 11:06 2019/5/20
      * @param       key
      * @return java.util.Set<java.lang.Object>
    */
    public Set<Object> sGet(String key){
        try {
            return myRedisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
      * 方法表述: 根据value从一个set中查询,是否存在
      * @Author 一股清风
      * @Date 11:06 2019/5/20
      * @param       key
     * @param       value
      * @return boolean
    */
    public boolean sHasKey(String key,Object value){
        try {
            return myRedisTemplate.opsForSet().isMember(key, value);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
      * 方法表述: 将数据放入set缓存
      * @Author 一股清风
      * @Date 11:07 2019/5/20
      * @param       key
     * @param       values 值 可以是多个
      * @return long 成功个数
    */
    public long sSet(String key, Object...values) {
        try {
            return myRedisTemplate.opsForSet().add(key, values);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
      * 方法表述: 将set数据放入缓存
      * @Author 一股清风
      * @Date 11:07 2019/5/20
      * @param       key
     * @param       time 时间(秒)
     * @param       values 值 可以是多个
      * @return long 成功个数
    */
    public long sSetAndTime(String key,long time,Object...values) {
        try {
            Long count = myRedisTemplate.opsForSet().add(key, values);
            if(time>0) expire(key, time);
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
      * 方法表述: 获取set缓存的长度
      * @Author 一股清风
      * @Date 11:07 2019/5/20
      * @param       key
      * @return long
    */
    public long sGetSetSize(String key){
        try {
            return myRedisTemplate.opsForSet().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
      * 方法表述: 移除值为value的
      * @Author 一股清风
      * @Date 11:08 2019/5/20
      * @param       key
     * @param       values 值 可以是多个
      * @return long 移除的个数
    */
    public long setRemove(String key, Object ...values) {
        try {
            Long count = myRedisTemplate.opsForSet().remove(key, values);
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    //===============================list=================================

    /**
      * 方法表述: 获取list缓存的内容
      * @Author 一股清风
      * @Date 11:08 2019/5/20
      * @param       key
     * @param       start 开始
     * @param       end 结束  0 到 -1代表所有值
      * @return java.util.List<java.lang.Object>
    */
    public List<Object> lGet(String key, long start, long end){
        try {
            return myRedisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
      * 方法表述: 获取list缓存的长度
      * @Author 一股清风
      * @Date 11:08 2019/5/20
      * @param       key
      * @return long
    */
    public long lGetListSize(String key){
        try {
            return myRedisTemplate.opsForList().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
      * 方法表述: 通过索引 获取list中的值
      * @Author 一股清风
      * @Date 11:09 2019/5/20
      * @param       key
     * @param       index 索引  index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
      * @return java.lang.Object
    */
    public Object lGetIndex(String key,long index){
        try {
            return myRedisTemplate.opsForList().index(key, index);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
      * 方法表述:  将list放入缓存
      * @Author 一股清风
      * @Date 11:10 2019/5/20
      * @param       key
     * @param       value
      * @return boolean
    */
    public boolean lSet(String key, Object value) {
        try {
            myRedisTemplate.opsForList().rightPush(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
      * 方法表述: 将list放入缓存
      * @Author 一股清风
      * @Date 11:11 2019/5/20
      * @param       key
     * @param       value
     * @param       time 时间(秒)
      * @return boolean
    */
    public boolean lSet(String key, Object value, long time) {
        try {
            myRedisTemplate.opsForList().rightPush(key, value);
            if (time > 0) expire(key, time);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
      * 方法表述: 将list放入缓存
      * @Author 一股清风
      * @Date 11:11 2019/5/20
      * @param       key
     * @param       value
      * @return boolean
    */
    public boolean lSet(String key, List<Object> value) {
        try {
            myRedisTemplate.opsForList().rightPushAll(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
      * 方法表述: 将list放入缓存
      * @Author 一股清风
      * @Date 11:12 2019/5/20
      * @param       key
     * @param       value
     * @param       time 时间（秒）
      * @return boolean
    */
    public boolean lSet(String key, List<Object> value, long time) {
        try {
            myRedisTemplate.opsForList().rightPushAll(key, value);
            if (time > 0) expire(key, time);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
      * 方法表述: 根据索引修改list中的某条数据
      * @Author 一股清风
      * @Date 11:12 2019/5/20
      * @param       key
     * @param       index
     * @param       value
      * @return boolean
    */
    public boolean lUpdateIndex(String key, long index,Object value) {
        try {
            myRedisTemplate.opsForList().set(key, index, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
      * 方法表述: 移除N个值为value
      * @Author 一股清风
      * @Date 11:13 2019/5/20
      * @param       key
     * @param       count 移除多少个
     * @param       value
      * @return long 移除的个数
    */
    public long lRemove(String key,long count,Object value) {
        try {
            Long remove = myRedisTemplate.opsForList().remove(key, count, value);
            return remove;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
      * 方法表述: 根据key对象是否存在缓存中
      * @Author 一股清风
      * @Date 14:31 2019/5/20
      * @param       prefix
 * @param       key
 * @param       clazz
      * @return T
    */
    public <T> T get(KeyPrefix prefix, String key, Class<T> clazz) {
            //对key增加前缀，即可用于分类，也避免key重复
            String realKey = prefix.getPrefix() + key;
            String str = (String) get(realKey,RedisConstants.datebase2);
            T t = stringToBean(str, clazz);
            return t;
    }

    /**
      * 方法表述: TODO
      * @Author 一股清风
      * @Date 12:28 2019/5/20
      * @param       prefix
     * @param       key
     * @param       value
      * @return java.lang.Boolean
    */
    public <T> Boolean set(KeyPrefix prefix, String key, T value) {
        String str = beanToString(value);
        if (str == null || str.length() <= 0) {
            return false;
        }
        String realKey = prefix.getPrefix() + key;
        int seconds = prefix.expireSeconds();//获取过期时间
        set(realKey,str,RedisConstants.datebase2,Long.valueOf(seconds));
        return true;
    }

    /**
      * 方法表述: 判断key是否存在
      * @Author 一股清风
      * @Date 12:28 2019/5/20
      * @param       prefix
     * @param       key
      * @return boolean
    */
    public <T> boolean exists(KeyPrefix prefix, String key) {
        //生成真正的key
        String realKey = prefix.getPrefix() + key;
        return hasKey(realKey);
    }

    public static <T> T stringToBean(String str, Class<T> clazz) {
        if (str == null || str.length() <= 0 || clazz == null) {
            return null;
        }
        if (clazz == int.class || clazz == Integer.class) {
            return (T) Integer.valueOf(str);
        } else if (clazz == long.class || clazz == Long.class) {
            return (T) Long.valueOf(str);
        } else if (clazz == String.class) {
            return (T) str;
        } else {
            return JSON.toJavaObject(JSON.parseObject(str), clazz);
        }
    }

    public static <T> String beanToString(T value) {
        if (value == null) {
            return null;
        }
        Class<?> clazz = value.getClass();
        if (clazz == int.class || clazz == Integer.class) {
            return String.valueOf(value);
        } else if (clazz == long.class || clazz == Long.class) {
            return String.valueOf(value);
        } else if (clazz == String.class) {
            return (String) value;
        } else {
            return JSON.toJSONString(value);
        }

    }

    public static void main(String[] args) {
		/*JedisPool jedisPool = new JedisPool(null,"localhost",6379,100,"123456");
		Jedis jedis = jedisPool.getResource();
		//r.get("", RedisConstants.datebase4);
		jedis.select(RedisConstants.datebase4);
		Set<String> str =  jedis.keys("*");
		for (String string : str) {
			System.out.println(string);
		}*/
    }
}
