package com.alon.common.result;

import java.io.Serializable;

/**
 * @ClassName CodeMessage
 * @Description TODO
 * @Author 一股清风
 * @Date 2019/5/17 14:14
 * @Version 1.0
 **/
public class CodeMessage implements Serializable {
    private Integer code;
    private String codes;
    private String message;

    //通用的错误码
    public static CodeMessage SUCCESS = new CodeMessage(0, "success");
    public static CodeMessage SERVER_ERROR = new CodeMessage(500100, "服务端异常");
    public static CodeMessage BIND_ERROR = new CodeMessage(500101, "参数校验异常：%s");
    public static CodeMessage ACCESS_LIMIT_REACHED= new CodeMessage(500104, "访问高峰期，请稍等！");
    //登录模块 5002XX
    public static CodeMessage SESSION_ERROR = new CodeMessage(500210, "Session不存在或者已经失效");
    public static CodeMessage PASSWORD_EMPTY = new CodeMessage(500211, "登录密码不能为空");
    public static CodeMessage MOBILE_EMPTY = new CodeMessage(500212, "手机号不能为空");
    public static CodeMessage MOBILE_ERROR = new CodeMessage(500213, "手机号格式错误");
    public static CodeMessage MOBILE_NOT_EXIST = new CodeMessage(500214, "手机号不存在");
    public static CodeMessage PASSWORD_ERROR = new CodeMessage(500215, "密码错误");
    public static CodeMessage PRIMARY_ERROR = new CodeMessage(500216, "主键冲突");
    public static CodeMessage NONE_USER = new CodeMessage(500217, "账号不存在");
    public static CodeMessage ERROR_PASS = new CodeMessage(500218, "密码不正确");
    public static CodeMessage LOCKING = new CodeMessage(500219, "帐号已被锁定");
    public static CodeMessage AUTH_FAILED = new CodeMessage(500220, "认证失败");

    //订单模块 5004XX
    public static CodeMessage ORDER_NOT_EXIST = new CodeMessage(500400, "订单不存在");

    //秒杀模块 5005XX
    public static CodeMessage SECKILL_OVER = new CodeMessage(500500, "商品已经秒杀完毕");
    public static CodeMessage REPEATE_SECKILL = new CodeMessage(500501, "不能重复秒杀");

    //请求接口
    public static CodeMessage WX_TOKEN = new CodeMessage(400501, "请求token失败");

    /**
      * 方法表述: 返回带参数的错误码
      * @Author 一股清风
      * @Date 14:17 2019/5/17
      * @param       args
      * @return com.alon.common.result.CodeMessage
    */
    public CodeMessage fillArgs(Object... args) {
        Integer code = this.code;
        String message = String.format(this.message, args);
        return new CodeMessage(code, message);
    }

    public CodeMessage(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
    public CodeMessage(String codes, String message) {
        this.codes = codes;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCodes() {
        return codes;
    }

    public void setCodes(String codes) {
        this.codes = codes;
    }

    @Override
    public String toString() {
        return "CodeMessage{" +
                "code=" + code +
                ", codes='" + codes + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
