package com.yx.test;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.generator.paginator.Page;
import org.mybatis.generator.paginator2.PageRowBounds;
import org.mybatis.generator.paginator3.PageBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.crab.mybatis.Application;
import com.crab.mybatis.domain.MyUser;
import com.crab.mybatis.domain.UserInfo;
import com.crab.mybatis.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringApplicationConfiguration(classes = Application.class)
public class UserTest {

	@Autowired
	private UserService userService;

	@Autowired
	private SqlSession sqlSession;

	@Before
	public void setup() {

	}

	@Test
	public void findAll() {
		List<MyUser> list = userService.findAll();
		for (MyUser bean : list) {
			System.out.println(bean.getName());
		}
	}

	@Test
	public void findAllPage() {
		List<MyUser> list = userService.findAll(Page.newBuilder(1, 3, "/user/page"));
		for (MyUser bean : list) {
			System.out.println(bean.getName());
		}
	}
	
	@Test
	public void findAllPage3() {
		PageBounds pg = new PageBounds(0, 3);
		List<MyUser> list = userService.findAll(pg);
		for (MyUser bean : list) {
			System.out.println(bean.getName());
		}
	}
	
	@Test
	public void findAllPage2() {
		PageRowBounds pg = new PageRowBounds(0, 3);
		List<MyUser> list = userService.findAll(pg);
		for (MyUser bean : list) {
			System.out.println(bean.getName());
		}
	}

	@Test
	public void findOne() {
		System.out.println(userService.findOne(1).getName());
	}

	@Test
	public void testPage() throws Exception {
		PageRowBounds pageRowBounds = new PageRowBounds(0, 3);
		List<UserInfo> students = sqlSession.selectList("com.crab.mybatis.mapper.UserMapper.findAll", null,
				pageRowBounds);
		System.out.println(pageRowBounds.getTotalCount());
		students.forEach(System.out::println);
	}

	@Test
	public void testPage2() throws Exception {
		PageBounds pageRowBounds = new PageBounds(0, 3);
		List<UserInfo> students = sqlSession.selectList("com.crab.mybatis.mapper.UserMapper.findAll", null,
				pageRowBounds);
		System.out.println(pageRowBounds.getTotal());
		students.forEach(System.out::println);
	}
}
