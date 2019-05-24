package com.alon.mapper.dao;

import com.alon.model.LtRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LtRoleMapper {
    List<LtRole> findByUserName(Long userId);
}
