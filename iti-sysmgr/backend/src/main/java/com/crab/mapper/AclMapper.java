package com.crab.mapper;

import com.crab.common.GenericMapper;
import com.crab.domain.Acl;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface AclMapper extends GenericMapper<Acl, Integer> {
    @Select(value="select * from p_acl acl where acl.principal_type = #{arg0} and acl.principal_sn = #{arg1} and acl.resource_sn = #{arg2}")
    @ResultMap("com.crab.mapper.AclMapper.BaseResultMap")
    Acl getAcl(String principalType, Integer principalSn, Integer resourceSn);

    @Select(value="select * from p_acl a where a.principal_type=#{arg0} and a.principal_sn=#{arg1}  order by a.id")
    @ResultMap("com.crab.mapper.AclMapper.BaseResultMap")
    List<Acl> getAcls(String principalType, Integer principalSn);

    /**
     * 根据用户查找直接授予用户的授权列表（注意：如果直接授予用户的授权是继承的话，则不应该包含在这个列表中），返回的列表元素是：ACL实例
     * @param principalType
     * @param principalSn
     * @return
     */
    @Select(value="select * from p_acl a where a.acl_ext_state=0 and a.principal_type=#{arg0} and a.principal_sn=#{arg1}  order by a.id")
    @ResultMap("com.crab.mapper.AclMapper.BaseResultMap")
    List<Acl> getAclsNotExtends(String principalType, Integer principalSn);

    /**
     * 查找授予用户的继承授权列表
     * @param principalType
     * @param principalSn
     * @return
     */
    @Select(value="select * from p_acl a where a.acl_ext_state=-1 and a.principal_type=#{arg0} and a.principal_sn=#{arg1}  order by a.id")
    @ResultMap("com.crab.mapper.AclMapper.BaseResultMap")
    List<Acl> getAclsExtends(String principalType, Integer principalSn);

    @Select(value="select * from p_acl acl where acl.principal_type = #{arg0} and acl.principal_sn = #{arg1} and acl.resource_sn = #{arg2}")
    @ResultMap("com.crab.mapper.AclMapper.BaseResultMap")
    Acl searchModulePermission(String principalType, int principalSn, int resourceSn);
}