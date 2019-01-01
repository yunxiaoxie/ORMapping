package com.crab.mapper;

import com.crab.common.GenericMapper;
import com.crab.domain.Role;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RoleMapper extends GenericMapper<Role, Integer> {

    @Select(value="select ur.role_id from p_user_role ur left join p_role r on ur.role_id=r.id left join p_user u on u.id=ur.user_id where u.id=#{arg0} order by ur.order_no")
    List<Integer> getRoleIdsByUserId(Integer userId);

    @Select(value="select r.* from p_user_role ur left join p_role r on ur.role_id=r.id left join p_user u on u.id=ur.user_id where u.id=#{arg0} order by ur.order_no")
    @ResultMap("com.crab.mapper.RoleMapper.BaseResultMap")
    List<Role> getRolesByUserId(Integer userId);

    @Select(value="select ur.role_id from p_user_role ur left join p_role r on ur.role_id=r.id left join p_user u on u.id=ur.user_id where u.id=#{arg0} order by ur.order_no desc")
    List<Integer> getRoleIdsByUserIdDesc(Integer userId);
}