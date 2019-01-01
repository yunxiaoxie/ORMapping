package com.crab.mapper;

import com.crab.common.GenericMapper;
import com.crab.domain.User;
import org.apache.ibatis.annotations.Select;

public interface UserMapper extends GenericMapper<User, Integer> {

    @Select("select * from p_user where account=#{account}")
    User findByAccount(String account);

    @Select("select * from p_user where account=#{arg0} and password=#{arg1}")
    User findUser(String account, String pwd);
}