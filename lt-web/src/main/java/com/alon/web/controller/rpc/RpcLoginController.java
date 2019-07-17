package com.alon.web.controller.rpc;

import com.alon.common.dto.sys.LoginDto;
import com.alon.common.result.ResultData;
import com.alon.service.rpc.RpcService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName RpcLoginController
 * @Description TODO
 * @Author zoujiulong
 * @Date 2019/6/13 10:34
 * @Version 1.0
 **/
@RestController
@RequestMapping("/rpc")
@Slf4j
public class RpcLoginController {

    @Reference(version = "1.0.0")
    private RpcService userService;

    @PostMapping("/rpc_login")
    public ResultData login(LoginDto dto) {
        ResultData resultData = userService.login(dto);
        log.info("====dubbo消费方===服务提供方响应数据====================");
        return ResultData.success(resultData.getData());
    }


    /**
      * 方法表述: TODO
      * @Author zoujiulong
      * @Date 18:07 2019/6/18
      * @param       file
      * @return java.util.Map<java.lang.String,java.lang.Object>
    */
    @PostMapping("/upload")
    public Map<String, Object> doUpload(@RequestParam("file") MultipartFile file) throws Exception {
        if (file == null) {
            return new HashMap(0);
        }

        Map map = new HashMap(2);

        // 原文件名称
        String filename = file.getOriginalFilename();

        // 创建临时文件
        File tempFile = File.createTempFile("tem", null);
        file.transferTo(tempFile);
        tempFile.deleteOnExit();

        // 文件输入流
        FileInputStream inputStream = new FileInputStream(tempFile);

        byte[] buffer = new byte[(int)tempFile.length()];
        inputStream.read(buffer);
        inputStream.close();

        // 转换为base64编码格式
        String base64 = new sun.misc.BASE64Encoder().encode(buffer);

        // 上面方法中获得的base64编码中，包含有换行符，统一全部替换掉
        base64 = base64.replaceAll("[\\s*\t\n\r]", "");

        // 返回值
        map.put("filename", filename);
        map.put("base64", base64);
        log.info("");

        return map;
    }
}
