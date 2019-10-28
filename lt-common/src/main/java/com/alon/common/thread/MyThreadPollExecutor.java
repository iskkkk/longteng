package com.alon.common.thread;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.*;

/**
 * @ClassName MyThreadPollExecutor
 * @Description 线程池
 * @Author zoujiulong
 * @Date 2019/10/28 14:36
 * @Version 1.0
 **/
public class MyThreadPollExecutor {

    private static ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("workOrder-pool-%d").build();
    private static ExecutorService executorService  = new ThreadPoolExecutor(5, 200,0L, TimeUnit.MILLISECONDS,new LinkedBlockingQueue<Runnable>(1024),
            namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());

    static {
        /**
         * 获得当前设备的CPU个数
         */
        int cpuNum = Runtime.getRuntime().availableProcessors();
        System.out.println("设备的CPU个数为：%s" + cpuNum);
    }

    public static String doSomething(Long id) {
        try {
            Thread.sleep(1000);
            System.out.println("132165" + id);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "执行结束：%s" + id;
    }

    public static void test() {
        String s = null;
        try {
            // execute()
            //用于提交不需要返回值的任务,所以无法判断任务是否被线程池执行成功
//            executorService.execute(() -> doSomething(1L));
            //submit()
            //用于提交需要返回值的任务.线程池会返回一个future类型对象,通过此对象可以判断任务是否执行成功
            Future<String> submit = executorService.submit(() -> doSomething(2L));
            s = submit.get(2000,TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        executorService.shutdown();
        System.out.println("0000000000000" + s);
    }
    
    public static void main(String[] args) {
//        test();
        try {
            RuntimeMXBean runtimeBean = ManagementFactory.getRuntimeMXBean();
            String jvmName = runtimeBean.getName();
            System.out.println("JVM Name = " + jvmName);
            long pid = Long.valueOf(jvmName.split("@")[0]);
            System.out.println("JVM PID  = " + pid);
            ThreadMXBean bean = ManagementFactory.getThreadMXBean();
            int n = 30000;
            for (int i = 0; i < n; i++) {
                ThreadPoolExecutor executor = new ThreadPoolExecutor(10,20,1000,TimeUnit.SECONDS,new LinkedBlockingDeque<>());
                for(int j=0;j<10;j++){
                    executor.execute(()->{
                        System.out.println("当前线程总数为："+bean.getThreadCount());
                    });
                }
                executor.shutdown();
            }
            Thread.sleep(10000);
            System.out.println("线程总数为 = " + bean.getThreadCount());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
