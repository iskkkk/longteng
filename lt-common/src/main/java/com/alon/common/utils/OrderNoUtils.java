package com.alon.common.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicLong;

public class OrderNoUtils {
    private final AtomicLong SEQ = new AtomicLong(0);
    private final int MAX_ID = 10000;
    private final NumberFormat df = new DecimalFormat("0000");

    /**
     *
     * 功能描述: 信德订单号
     *
     * @param:
     * @return:
     * @auther: zoujiulong
     * @date: 2018/8/3   17:06
     */
    public static synchronized String getOrderNo() {
        return new SimpleDateFormat("MMddHHmmss").format(new Date()) + NumberUtil.getRandomString(4);

    }

    /**
     *
     * 功能描述: 支付宝支付交易号
     *
     * @param:  buzType  payType
     * @return:
     * @auther: zoujiulong
     * @date: 2018/8/3   17:06
     */

    public static synchronized String getTradeNo(String buzType, String payType) {
        String dateStr = new SimpleDateFormat("yyMMddHHmmss").format(new Date());
        return buzType + payType + dateStr + NumberUtil.getRandomString(4);
    }

    /**
     *
     * 功能描述: 景点订单号生成17位
     *
     * @param:  * @param null
     * @return:
     * @auther: zoujiulong
     * @date: 2018/8/3   17:07
     */
    public String getScenicOrderNo() {
        SimpleDateFormat sdf = new SimpleDateFormat("MMddHHmmss");
        String orderNo = sdf.format(new Date());
        long seq = (long) (Math.abs(SEQ.getAndIncrement()) % MAX_ID);
        return orderNo + NumberUtil.getRandomString(3) + df.format(seq);
    }

    /**
     *
     * 功能描述: 旅行社购票订单号18位
     *
     * @param:  * @param null
     * @return:
     * @auther: zoujiulong
     * @date: 2018/8/3   17:07
     */
    public String getTravelNo() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
        String orderNo = sdf.format(new Date());
        long seq = (long) (Math.abs(SEQ.getAndIncrement()) % MAX_ID);
        return orderNo + NumberUtil.getRandomString(2) + df.format(seq);
    }

    /**
     *
     * 功能描述: 生成客户协议号
     *
     * @param:  memberId,payType
     * @return:
     * @auther: zoujiulong
     * @date: 2018/8/3   17:07
     */
    public static synchronized String getProtocolNo(String memberId, String payType) {
        String dateStr = new SimpleDateFormat("yyMMddHHmmss").format(new Date());
        return memberId + payType + dateStr + NumberUtil.getRandomString(6);
    }

    /**
     *
     * 功能描述: 生成流水号
     *
     * @param:  payType
     * @return:
     * @auther: zoujiulong
     * @date: 2018/8/3   17:08
     */
    public static synchronized String getSerialNo(String payType) {
        String dateStr = new SimpleDateFormat("yyMMddHHmmss").format(new Date());
        return payType + dateStr + NumberUtil.getRandomString(6);
    }

    /**
     *
     * 功能描述: 生成船票号()
     *
     * @param:  * @param null
     * @return:
     * @auther: zoujiulong
     * @date: 2018/8/3   17:08
     */
    public static String getTicketCode() {
        Date d = new Date();
        String str1 = String.valueOf(d.getTime()).substring(5);
        String generateRandom = NumberUtil.getRandomString(6);
        String ticketCode = str1 + generateRandom;
        return ticketCode;
    }

    /**
      * 方法表述: 当前时间戳
      * @Author 一股清风
      * @Date 15:26 2019/5/13
      * @param
      * @return java.lang.String
    */
    public static String GetTimestamp() {
        return Long.toString(new Date().getTime() / 1000);
    }
    public static void main(String[] args) {
        int thread_num = 200;
        int client_num = 500;
        ExecutorService exec = Executors.newCachedThreadPool();
        final Semaphore semp = new Semaphore(thread_num);
        for (int index = 0; index < client_num; index++) {
            final int NO = index;
            Runnable run = new Runnable() {
                public void run() {
                    try {
                        semp.acquire();
                        System.out.println("Thread:" + NO + "==" + OrderNoUtils.getOrderNo());
                        semp.release();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            exec.execute(run);
        }
        exec.shutdown();
    }
}
