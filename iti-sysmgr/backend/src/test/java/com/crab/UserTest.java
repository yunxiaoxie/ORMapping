package com.crab;

import com.crab.domain.User;
import com.crab.service.IUser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringBootTest(classes = Application.class)
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
