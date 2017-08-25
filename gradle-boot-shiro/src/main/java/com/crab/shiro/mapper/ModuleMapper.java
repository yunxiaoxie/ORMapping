package com.crab.shiro.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.crab.shiro.domain.Module;
import com.crab.shiro.domain.ModuleInfo;

@Mapper
public interface ModuleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Module record);

    int insertSelective(Module record);

    Module selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Module record);

    int updateByPrimaryKey(Module record);
    
    /**
	 * 获取该人的权限模块
	 * @param userId
	 * @return
	 */
	@Select(value="select e.* from p_user_role b left join p_role c on b.role_id=c.id left join t_role_module d on c.id=d.role_id left join t_module e on d.module_id=e.id where b.user_id=#{userId}")
	@ResultMap("com.crab.shiro.mapper.ModuleMapper.BaseResultMap")
	List<ModuleInfo> findModuleByUserId(int userId);
	
	/**
	 * 得到用户拥有的权限模块
	 * @param userId
	 * @return
	 */
	//@Select(value="select * from p_module m where m.pid is null and m.id in (${ids}) order by m.order_no")
	List<Module> findModules(List<Integer> ids);
	
	/**
	 * 得到用户拥有的权限模块
	 * @param userId
	 * @return
	 */
	//@Select(value="select * from p_module m where m.pid=#{1} and m.id in (${0}) order by m.order_no")
	List<Module> findModulesWithPid(List<Integer> ids, Integer pid);
	
	/**
	 * 根据模块sn得到模块id
	 * @param sn
	 * @return
	 */
	@Select(value="select m.id from p_module m where m.sn = #{0}")
	Integer getModuleByResourceSn(String sn);

}