package com.crab;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import static org.junit.Assert.assertEquals;
import com.crab.shiro.SimpleApplication;
import com.crab.shiro.domain.User;
import com.crab.shiro.service.IUser;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringBootTest(classes = SimpleApplication.class)
public class UserTest {

	@Autowired
	private IUser service;

	@Before
	public void setup() {

	}

	@Test
	public void findOne() {
		User user = service.findUser(1);
		assertEquals("admin", user.getAccount());
	}

	
}
