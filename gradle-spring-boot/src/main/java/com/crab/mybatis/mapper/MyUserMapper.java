package com.crab.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.mybatis.generator.paginator.Page;
import org.mybatis.generator.paginator2.PageRowBounds;
import org.mybatis.generator.paginator3.PageBounds;

import com.crab.mybatis.domain.MyUser;

@Mapper
public interface MyUserMapper {

	/**
	 * findOne
	 * 
	 * @param id
	 * @return
	 */
	@Select(value = "select * from my_user where id=#{0}")
	@Results(value = {
            @Result(property = "isAdult", column = "is_adult"),
            @Result(property = "createTime", column = "create_time")})
	MyUser findOne(int id);

	/**
	 * findAll
	 * Use query sql from mapper.xml, so column name can be transformed automatic. 
	 * If you want to use @Select, you need to use @Results to show the column with alias, not *.
	 * @return
	 */
	List<MyUser> findAll();

	/**
	 * findAll
	 * 
	 * @return
	 */
	@Select(value = "select * from my_user order by id desc")
	@Results(value = {
            @Result(property = "isAdult", column = "is_adult"),
            @Result(property = "createTime", column = "create_time")})
	List<MyUser> findAllPage(Page page);

	@Select(value = "select * from my_user order by id desc")
	@Results(value = {
            @Result(property = "isAdult", column = "is_adult"),
            @Result(property = "createTime", column = "create_time")})
	List<MyUser> findAllPage3(PageBounds page);

	@Select(value = "select * from my_user order by id desc")
	@Results(value = {
            @Result(property = "isAdult", column = "is_adult"),
            @Result(property = "createTime", column = "create_time")})
	List<MyUser> findAllPage2(PageRowBounds page);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table my_user
	 *
	 * @mbg.generated
	 */
	MyUser selectByPrimaryKey(Integer id);

	int insert(MyUser user);

	int deleteByPrimaryKey(Integer id);

	int updateByPrimaryKey(MyUser user);
	
	int insertSelective(MyUser record);
	
	int updateByPrimaryKeySelective(MyUser record);
}