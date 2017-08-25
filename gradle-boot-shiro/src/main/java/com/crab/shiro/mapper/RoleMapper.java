package com.crab.shiro.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.crab.shiro.domain.Role;

@Mapper
public interface RoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);
    
    @Select(value="select ur.role_id from p_user_role ur left join p_role r on ur.role_id=r.id left join p_user u on u.id=ur.user_id where u.id=#{0} order by ur.order_no")
    List<Integer> getRoleIdsByUserId(Integer userId);
    
    @Select(value="select r.* from p_user_role ur left join p_role r on ur.role_id=r.id left join p_user u on u.id=ur.user_id where u.id=#{0} order by ur.order_no")
    @ResultMap("com.crab.shiro.mapper.RoleMapper.BaseResultMap")
    List<Role> getRolesByUserId(Integer userId);
    
    @Select(value="select ur.role_id from p_user_role ur left join p_role r on ur.role_id=r.id left join p_user u on u.id=ur.user_id where u.id=#{0} order by ur.order_no desc")
    List<Integer> getRoleIdsByUserIdDesc(Integer userId);
}