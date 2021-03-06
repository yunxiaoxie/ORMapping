package com.crab.mapper;

import com.crab.common.GenericMapper;
import com.crab.domain.Module;
import com.crab.domain.ModuleInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ModuleMapper extends GenericMapper<Module, Integer> {
    /**
     * 获取该人的权限模块
     */
    @Select(value="select e.* from p_user_role b left join p_role c on b.role_id=c.id left join t_role_module d on c.id=d.role_id left join t_module e on d.module_id=e.id where b.user_id=#{userId}")
    @ResultMap("com.crab.mapper.ModuleMapper.BaseResultMap")
    List<ModuleInfo> findModuleByUserId(int userId);

    /**
     * 得到用户拥有的权限模块
     */
    //@Select(value="select * from p_module m where m.pid is null and m.id in (${ids}) order by m.order_no")
    @Select({
            "<script>",
            "select * from p_module m where m.pid is null and m.id in ",
            "<foreach item='item' index='index' collection='ids' open='(' separator=',' close=')'>",
            "#{item}",
            "</foreach>",
            "</script>"
    })
    List<Module> findModules(@Param("ids") List<Integer> ids);

    /**
     * 得到用户拥有的权限模块
     */
    //@Select(value="select * from p_module m where m.pid=#{1} and m.id in (${0}) order by m.order_no")
    List<Module> findModulesWithPid(List<Integer> ids, Integer pid);

    /**
     * 根据模块sn得到模块id
     */
    @Select(value="select m.id from p_module m where m.sn = #{arg0}")
    Integer getModuleByResourceSn(String sn);
}