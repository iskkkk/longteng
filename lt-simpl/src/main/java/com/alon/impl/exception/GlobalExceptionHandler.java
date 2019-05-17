package com.alon.impl.exception;

import com.alon.common.result.CodeMessage;
import com.alon.common.result.ResultData;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @ClassName GlobalExceptionHandler
 * @Description 自定义全局异常拦截器
 * @Author 一股清风
 * @Date 2019/5/17 14:44
 * @Version 1.0
 **/
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)//拦截所有异常
    public ResultData<String> exceptionHandler(HttpServletRequest request, Exception e) {
        e.printStackTrace();
        if(e instanceof GlobalException) {
            GlobalException ex = (GlobalException)e;
            return ResultData.error(ex.getCodeMessage());
        }else if(e instanceof BindException) {
            BindException ex = (BindException)e;
            List<ObjectError> errors = ex.getAllErrors();//绑定错误返回很多错误，是一个错误列表，只需要第一个错误
            ObjectError error = errors.get(0);
            String msg = error.getDefaultMessage();
            return ResultData.error(CodeMessage.BIND_ERROR.fillArgs(msg));//给状态码填充参数
        }else {
            return ResultData.error(CodeMessage.SERVER_ERROR);
        }
    }

}
