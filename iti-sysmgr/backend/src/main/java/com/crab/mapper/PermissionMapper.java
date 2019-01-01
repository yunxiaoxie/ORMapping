package com.crab.mapper;

import com.crab.common.GenericMapper;
import com.crab.domain.Permission;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface PermissionMapper extends GenericMapper<Permission, Integer> {
    @Select(value="select * from p_permission order by id")
    @ResultMap("com.crab.mapper.PermissionMapper.BaseResultMap")
    List<Permission> getAll();

    @Select(value="select * from p_permission where name=#{arg0}")
    @ResultMap("com.crab.mapper.PermissionMapper.BaseResultMap")
    Permission findByName(String permission);
}