package com.alon.web.controller.rpc;

import com.alon.common.dto.sys.LoginDto;
import com.alon.common.result.ResultData;
import com.alon.service.rpc.RpcService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName RpcLoginController
 * @Description TODO
 * @Author zoujiulong
 * @Date 2019/6/13 10:34
 * @Version 1.0
 **/
@RestController
@RequestMapping("/rpc")
public class RpcLoginController {

    @Reference(version = "1.0.0")
    private RpcService userService;

    @PostMapping("/rpc_login")
    public ResultData login(LoginDto dto) {
        ResultData resultData = userService.login(dto);
        return ResultData.success(resultData.getData());
    }
}
