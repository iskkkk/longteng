package com.alon.impl.redis.config;

import com.alon.impl.redis.FastJson2JsonRedisSerializer;
import com.alon.impl.redis.MyRedisTemplate;
import com.alon.impl.redis.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;


/**
 * @ClassName RedisConfig
 * @Description 读取配置文件的redis信息
 * @Author 一股清风
 * @Date 2019/5/17 15:35
 * @Version 1.0
 **/
@Configuration
@PropertySource("classpath:application-redis.properties")
@Slf4j
public class RedisConfig {
    @Value("${redis.hostName}")
    private String hostName;

    @Value("${redis.password}")
    private String password;

    @Value("${redis.port}")
    private Integer port;

    @Value("${redis.maxIdle}")
    private Integer maxIdle;

    @Value("${redis.timeout}")
    private Integer timeout;

    @Value("${redis.maxTotal}")
    private Integer maxTotal;

    @Value("${redis.maxWaitMillis}")
    private Integer maxWaitMillis;

    @Value("${redis.minEvictableIdleTimeMillis}")
    private Integer minEvictableIdleTimeMillis;

    @Value("${redis.numTestsPerEvictionRun}")
    private Integer numTestsPerEvictionRun;

    @Value("${redis.timeBetweenEvictionRunsMillis}")
    private long timeBetweenEvictionRunsMillis;

    @Value("${redis.testOnBorrow}")
    private boolean testOnBorrow;

    @Value("${redis.testWhileIdle}")
    private boolean testWhileIdle;

    /**
      * 方法表述: Jedis配置
      * @Author 一股清风
      * @Date 10:15 2019/5/20
      * @param       
      * @return org.springframework.data.redis.connection.jedis.JedisConnectionFactory
    */
    @Bean
    public JedisConnectionFactory JedisConnectionFactory(){
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration ();
        redisStandaloneConfiguration.setHostName(hostName);
        redisStandaloneConfiguration.setPort(port);
        //由于我们使用了动态配置库,所以此处省略
        //redisStandaloneConfiguration.setDatabase(database);
        redisStandaloneConfiguration.setPassword(RedisPassword.of(password));
        JedisClientConfiguration.JedisClientConfigurationBuilder jedisClientConfiguration = JedisClientConfiguration.builder();
        jedisClientConfiguration.connectTimeout(Duration.ofMillis(timeout));
        JedisConnectionFactory factory = new JedisConnectionFactory(redisStandaloneConfiguration,
                jedisClientConfiguration.build());
        return factory;
    }

    /**
      * 方法表述: 实例化 RedisTemplate 对象
      * @Author 一股清风
      * @Date 10:41 2019/5/20
      * @param       redisConnectionFactory
      * @return org.springframework.data.redis.core.RedisTemplate
    */
    @Bean
    public MyRedisTemplate functionDomainRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        log.info("RedisTemplate实例化成功！");
        MyRedisTemplate redisTemplate = new MyRedisTemplate();
        initDomainRedisTemplate(redisTemplate, redisConnectionFactory);
        return redisTemplate;
    }

    /**
      * 方法表述: 引入自定义序列化
      * @Author 一股清风
      * @Date 10:41 2019/5/20
      * @param       
      * @return org.springframework.data.redis.serializer.RedisSerializer
    */
    @Bean
    public RedisSerializer fastJson2JsonRedisSerializer() {
        return new FastJson2JsonRedisSerializer<Object>(Object.class);
    }

    /**
      * 方法表述: 设置数据存入 redis 的序列化方式,并开启事务
      * @Author 一股清风
      * @Date 10:17 2019/5/20
      * @param       redisTemplate
     * @param       factory
      * @return void
    */
    private void initDomainRedisTemplate(MyRedisTemplate redisTemplate, RedisConnectionFactory factory) {
        //如果不配置Serializer，那么存储的时候缺省使用String，如果用User类型存储，那么会提示错误User can't cast to String！
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setValueSerializer(fastJson2JsonRedisSerializer());
        // 开启事务
        redisTemplate.setEnableTransactionSupport(true);
        redisTemplate.setConnectionFactory(factory);
    }
    
    /**
      * 方法表述: 注入封装RedisTemplate
      * @Author 一股清风
      * @Date 10:16 2019/5/20 
      * @param       myRedisTemplate
      * @return RedisUtil
    */
    @Bean(name = "redisUtil")
    public RedisUtil redisUtil(MyRedisTemplate myRedisTemplate) {
        log.info("RedisUtil注入成功！");
        RedisUtil redisUtil = new RedisUtil();
        redisUtil.setRedisTemplate(myRedisTemplate);
        return redisUtil;
    }
}
