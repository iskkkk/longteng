package com.alon.impl.sys;

import com.alon.common.dto.sys.LoginDto;
import com.alon.common.utils.ShiroUtils;
import com.alon.common.utils.UUIDUtil;
import com.alon.mapper.dao.seckill.LtUserMapper;
import com.alon.service.sys.LtUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName LtUserServiceImpl
 * @Description 用户实现类
 * @Author 一股清风
 * @Date 2019/5/24 11:06
 * @Version 1.0
 **/
@Service
@Slf4j
@Transactional(rollbackFor = RuntimeException.class)
public class LtUserServiceImpl implements LtUserService {
    @Autowired
    private LtUserMapper userMapper;

    @Override
    public boolean registerData(LoginDto dto) {
        // 生成uuid
        dto.unionId = UUIDUtil.uuid();
        dto.salt = ShiroUtils.getSalt();
        dto.password = ShiroUtils.md5(dto.password,dto.salt);
        try {
            userMapper.insert(dto);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
