package com.alon.common.validator;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * @ClassName UserInfoMethodArgumentResolver
 * @Description 注解实现类
 * @Author zoujiulong
 * @Date 2019/6/12 14:39
 * @Version 1.0
 **/
@Component  //注入
@Aspect //切面
@Slf4j
public class UserInfoMethodArgumentResolver {
    /**
      * 方法表述: 定义切入点，只要方法体上有这个注解
      * @Author zoujiulong
      * @Date 15:00 2019/6/12
      * @param       isOpenId
      * @return void
    */
    @Pointcut("@annotation(isOpenId)")
    public void capabilityMonitor(IsOpenId isOpenId){
        log.info("==============1223+++++++++============");
    }

    /**
      * 方法表述: 环绕通知，在方法执行之前和之后都执行
     * capabilityMonitor(isOpenId)：这里的参数一定要和切入点（`@Pointcut("@annotation(isOpenId)")`）的参数相同
      * @Author zoujiulong
      * @Date 15:05 2019/6/12
      * @param       point
     * @param       isOpenId
      * @return void
    */
    @Around("capabilityMonitor(isOpenId)")
    public void execute(ProceedingJoinPoint point, IsOpenId isOpenId) throws Throwable{
        Long startTime=System.currentTimeMillis();  //开始时间
        Object[] args=point.getArgs(); //获取目标方法执行的参数数组
        Object returnValues=point.proceed(args);   //执行目标方法
        Long endTime=System.currentTimeMillis();  //结束时间
        log.info("程序执行的时间："+((endTime-startTime)/1000.0));   //输出程序执行的时间，秒位单位
    }

    @Before("capabilityMonitor(isOpenId)")
    public void before(IsOpenId isOpenId){
        System.out.println("2");
        log.info("===============before==============");
    }

    @After("capabilityMonitor(isOpenId)")
    public void after(IsOpenId isOpenId){
        System.out.println("5");
        log.info("===============after==============");
    }
}
