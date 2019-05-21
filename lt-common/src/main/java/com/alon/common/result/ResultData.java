package com.alon.common.result;

/**
 * @ClassName ResultData
 * @Description 返回结构封装集
 * @Author 一股清风
 * @Date 2019/5/17 14:33
 * @Version 1.0
 **/
public class ResultData<T> {
    private Integer code;
    private String msg;
    private T data;

    public ResultData(T data) {
        this.code = CodeMessage.SUCCESS.getCode();
        this.msg = CodeMessage.SUCCESS.getMessage();
        this.data = data;
    }

    public ResultData(CodeMessage codeMsg) {
        if (null != codeMsg) {
            this.code = codeMsg.getCode();
            this.msg = codeMsg.getMessage();
        }
    }

    public ResultData(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
      * 方法表述: 成功时候的调用
      * @Author 一股清风
      * @Date 14:35 2019/5/17
      * @param       data
      * @return com.alon.common.result.ResultData<T>
    */
    public static  <T> ResultData<T> success(T data){
        return new ResultData<T>(data);
    }

    /**
      * 方法表述: 失败时候的调用
      * @Author 一股清风
      * @Date 14:38 2019/5/17
      * @param       codeMsg
      * @return com.alon.common.result.ResultData<T>
    */
    public static  <T> ResultData<T> error(CodeMessage codeMsg){
        return new ResultData<T>(codeMsg);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
