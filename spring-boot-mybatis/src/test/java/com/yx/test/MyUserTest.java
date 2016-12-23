package com.yx.test;

import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.crab.mybatis.Application;
import com.crab.mybatis.domain.MyUser;
import com.crab.mybatis.service.MyUserService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringBootTest(classes = Application.class)
public class MyUserTest {

	@Autowired
	private MyUserService service;

	@Before
	public void setup() {

	}

	@Test
	public void testQuery() {
		List<MyUser> list = service.findAll();
		assertTrue(list.size() > 0);
	}
	
	@Test
	public void testQueryOne() {
		MyUser bean = service.findOne(2);
		assertTrue(bean.getId() == 2);
	}

	@Test
	public void testInsert() {
		MyUser user = new MyUser();
		// user.setId(7);
		user.setName("Test");
		user.setSex("M");
		user.setAge(20);
		user.setAddr("光谷");
		user.setCreateTime(new Date());
		int result = service.insert(user);
		assertTrue(result == 1);
	}
	
	@Test
	public void testInsertSelective() {
		MyUser user = new MyUser();
		user.setName("Test2");
		user.setSex("M");
		user.setAge(20);
		user.setAddr("光谷");
		user.setCreateTime(new Date());
		int result = service.insertSelective(user);
		assertTrue(result == 1);
	}

	@Test
	public void testUpdate() {
		MyUser user = new MyUser();
		user.setId(7);
		user.setName("Test0");
		user.setSex("M");
		user.setAge(20);
		user.setAddr("光谷");
		user.setCreateTime(new Date());
		int result = service.update(user);
		assertTrue(result == 1);
	}
	
	@Test
	public void testUpdateSelective() {
		MyUser user = new MyUser();
		user.setId(10);
		user.setAge(22);
		int result = service.updateBySelective(user);
		assertTrue(result == 1);
	}

	@Test
	public void testDel() {
		int result = service.deleteById(7);
		assertTrue(result == 1);
	}
}
