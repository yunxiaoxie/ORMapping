package com.yx.test;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.generator.paginator.Page;
import org.mybatis.generator.paginator2.PageRowBounds;
import org.mybatis.generator.paginator3.PagingBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.crab.mybatis.SimpleApplication;
import com.crab.mybatis.domain.UserInfo;
import com.crab.mybatis.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringApplicationConfiguration(classes = SimpleApplication.class)
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
		List<UserInfo> list = userService.findAll(Page.newBuilder(2, 3, "/user/page"));
		for (UserInfo bean : list) {
			System.out.println(bean.getName());
		}
	}

	@Test
	public void findOne() {
		System.out.println(userService.find(1));
	}

	@Test
	public void testPage() throws Exception {
		PageRowBounds pageRowBounds = new PageRowBounds(0, 3);
		List<UserInfo> students = sqlSession.selectList("com.crab.mybatis.mapper.UserMapper.findAll", null, pageRowBounds);
		System.out.println(pageRowBounds.getTotalCount());
		students.forEach(System.out::println);
	}
	
	@Test
	public void testPage2() throws Exception {
		PagingBounds pageRowBounds = new PagingBounds(0, 3);
		List<UserInfo> students = sqlSession.selectList("com.crab.mybatis.mapper.UserMapper.findAll", null, pageRowBounds);
		System.out.println(pageRowBounds.getTotal());
		students.forEach(System.out::println);
	}
}
