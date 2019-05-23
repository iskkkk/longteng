package com.alon.mapper.dao;

import com.alon.model.WxUserInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @ClassName WxUserInfoMapper
 * @Description TODO
 * @Author 一股清风
 * @Date 2019/5/21 18:24
 * @Version 1.0
 **/
@Mapper
public interface WxUserInfoMapper {
    void insert(WxUserInfo info);

    List<WxUserInfo> getInfoForOpenId(WxUserInfo info);
}
