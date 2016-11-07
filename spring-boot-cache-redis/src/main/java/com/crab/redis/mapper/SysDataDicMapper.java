package com.crab.redis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.crab.redis.domain.SysDataDic;

@Mapper
public interface SysDataDicMapper {

	/**
	 * findOne
	 * 
	 * @param id
	 * @return
	 */
	@Select(value = "select * from sys_data_dic where id=#{id}")
	SysDataDic findOne(int id);

	/**
	 * find simply key-value data.
	 * 
	 * @return
	 */
	@Select(value = "select * from sys_data_dic where pid is null and dic_value is not null")
	List<SysDataDic> findKV();

	/**
	 * find complex key-value data, that have child construction.
	 * 
	 * @return
	 */
	@Select(value = "select * from sys_data_dic where pid is null and dic_value is null")
	List<SysDataDic> findTreeRoot();

	/**
	 * find complex key-value data, that have child construction.
	 * 
	 * @return
	 */
	@Select(value = "select * from sys_data_dic where pid=#{pid}")
	List<SysDataDic> findTreeChild(int pid);
}
