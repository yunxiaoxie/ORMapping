package com.crab.shiro.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.crab.shiro.domain.Acl;

@Mapper
public interface AclMapper {
	
    int deleteByPrimaryKey(Integer id);

    int insert(Acl record);

    int insertSelective(Acl record);

    Acl selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Acl record);

    int updateByPrimaryKey(Acl record);
    
    @Select(value="select * from p_acl acl where acl.principal_type = #{0} and acl.principal_sn = #{1} and acl.resource_sn = #{2}")
    @ResultMap("com.crab.shiro.mapper.AclMapper.BaseResultMap")
    Acl getAcl(String principalType, Integer principalSn, Integer resourceSn);
    
    @Select(value="select * from p_acl a where a.principal_type=#{0} and a.principal_sn=#{1}  order by a.id")
    @ResultMap("com.crab.shiro.mapper.AclMapper.BaseResultMap")
    List<Acl> getAcls(String principalType, Integer principalSn);
    
    /**
     * 根据用户查找直接授予用户的授权列表（注意：如果直接授予用户的授权是继承的话，则不应该包含在这个列表中），返回的列表元素是：ACL实例
     * @param principalType
     * @param principalSn
     * @return
     */
    @Select(value="select * from p_acl a where a.acl_ext_state=0 and a.principal_type=#{0} and a.principal_sn=#{1}  order by a.id")
    @ResultMap("com.crab.shiro.mapper.AclMapper.BaseResultMap")
    List<Acl> getAclsNotExtends(String principalType, Integer principalSn);
    
    /**
     * 查找授予用户的继承授权列表
     * @param principalType
     * @param principalSn
     * @return
     */
    @Select(value="select * from p_acl a where a.acl_ext_state=-1 and a.principal_type=#{0} and a.principal_sn=#{1}  order by a.id")
    @ResultMap("com.crab.shiro.mapper.AclMapper.BaseResultMap")
    List<Acl> getAclsExtends(String principalType, Integer principalSn);
    
    @Select(value="select * from p_acl acl where acl.principal_type = #{0} and acl.principal_sn = #{1} and acl.resource_sn = #{2}")
    @ResultMap("com.crab.shiro.mapper.AclMapper.BaseResultMap")
    Acl searchModulePermission(String principalType, int principalSn, int resourceSn);
}