package com.alon.mapper.dao;

import com.alon.model.LtPermission;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LtPermissionMapper {
    List<LtPermission> findByUserName(Long userId);
}
