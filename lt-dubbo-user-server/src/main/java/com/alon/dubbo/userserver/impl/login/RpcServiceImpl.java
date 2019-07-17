package com.alon.dubbo.userserver.impl.login;

import com.alon.common.dto.sys.LoginDto;
import com.alon.common.result.ResultData;
import com.alon.service.rpc.RpcService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;

/**
 * @ClassName RpcUserServiceImpl
 * @Description dubbo服务提供者测试方法
 * @Author zoujiulong
 * @Date 2019/6/13 10:23
 * @Version 1.0
 **/
@Service(version = "1.0.0")
@Slf4j
public class RpcServiceImpl implements RpcService {
    @Override
    public ResultData login(LoginDto loginDto) {
        log.info("=================进入rpc-server的login方法===================");
        return ResultData.success("123_test");
    }
}
