package com.yx.test;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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

	@Before
	public void setup() {
		
	}
	
	@Test
	public void findAll() {
		List<UserInfo> list = userService.findAll();
		System.out.println(list);
		System.out.println(userService.find(1));
	}
}
