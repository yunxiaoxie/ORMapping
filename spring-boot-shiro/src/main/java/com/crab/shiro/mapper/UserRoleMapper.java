package com.crab.shiro.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.crab.shiro.domain.UserRole;

@Mapper
public interface UserRoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserRole record);

    int insertSelective(UserRole record);

    UserRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserRole record);

    int updateByPrimaryKey(UserRole record);
}