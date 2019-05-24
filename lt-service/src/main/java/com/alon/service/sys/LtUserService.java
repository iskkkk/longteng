package com.alon.service.sys;

import com.alon.common.dto.sys.LoginDto;

/**
 * @ClassName LtUserService
 * @Description TODO
 * @Author 一股清风
 * @Date 2019/5/24 11:05
 * @Version 1.0
 **/
public interface LtUserService {
    /**
      * 方法表述: 注册功能
      * @Author 一股清风
      * @Date 11:05 2019/5/24
      * @param       dto
      * @return boolean
    */
    boolean registerData(LoginDto dto);
}
