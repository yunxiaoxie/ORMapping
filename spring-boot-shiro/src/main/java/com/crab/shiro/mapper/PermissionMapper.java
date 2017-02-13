package com.crab.shiro.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.crab.shiro.domain.Permission;

@Mapper
public interface PermissionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Permission record);

    int insertSelective(Permission record);

    Permission selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Permission record);

    int updateByPrimaryKey(Permission record);
    
    @Select(value="select * from p_permission order by id")
    @ResultMap("com.crab.shiro.mapper.PermissionMapper.BaseResultMap")
    List<Permission> getAll();
    
    @Select(value="select * from p_permission where name=#{0}")
    @ResultMap("com.crab.shiro.mapper.PermissionMapper.BaseResultMap")
    Permission findByName(String permission);
}