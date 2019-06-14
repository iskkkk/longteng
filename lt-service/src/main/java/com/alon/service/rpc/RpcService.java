package com.alon.service.rpc;

import com.alon.common.dto.sys.LoginDto;
import com.alon.common.result.ResultData;

public interface RpcService {
    /**
      * 方法表述: 用于rpc远程调用的登陆接口
      * @Author zoujiulong
      * @Date 10:22 2019/6/13
      * @param       loginDto
      * @return com.alon.common.result.ResultData
    */
    ResultData login(LoginDto loginDto);
}
