package com.alon.mapper.dao.seckill;

import com.alon.common.dto.sys.LoginDto;
import com.alon.model.seckill.LtUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @ClassName LtUserMapper
 * @Description 用户信息mappper
 * @Author 一股清风
 * @Date 2019/5/20 14:29
 * @Version 1.0
 **/
@Mapper
public interface LtUserMapper {

    @Select("select * from lt_user where id = #{id}")
    LtUser getById(@Param("id")long id);

    @Update("update lt_user set password = #{password} where id = #{id}")
    void update(LtUser toBeUpdate);

    LtUser getByUserName(String userName);

    void insert(LoginDto dto);


}
