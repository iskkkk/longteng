package com.alon.impl.exception;

import com.alon.common.result.CodeMessage;

/**
 * @ClassName GlobalException
 * @Description TODO
 * @Author 一股清风
 * @Date 2019/5/17 14:41
 * @Version 1.0
 **/
public class GlobalException extends RuntimeException {

    private static final long servialVersionUID = 1L;

    private CodeMessage codeMessage;

    public GlobalException(CodeMessage codeMessage) {
        super(codeMessage.toString());
        this.codeMessage = codeMessage;
    }

    public CodeMessage getCodeMessage() {
        return codeMessage;
    }
}
