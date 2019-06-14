package com.alon.dubbo.userserver.impl.login;

import com.alon.common.dto.sys.LoginDto;
import com.alon.common.result.ResultData;
import com.alon.service.rpc.RpcService;
import org.apache.dubbo.config.annotation.Service;

/**
 * @ClassName RpcUserServiceImpl
 * @Description TODO
 * @Author zoujiulong
 * @Date 2019/6/13 10:23
 * @Version 1.0
 **/
@Service(version = "1.0.0")
public class RpcServiceImpl implements RpcService {
    @Override
    public ResultData login(LoginDto loginDto) {
        return ResultData.success("123_test");
    }
}
