package com.crab;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.crab.shiro.SimpleApplication;
import com.crab.shiro.domain.Permission;
import com.crab.shiro.service.IPermission;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringBootTest(classes = SimpleApplication.class)
public class PermissionTest {

	@Autowired
	private IPermission service;

	@Before
	public void setup() {

	}

	@Test
	public void findOne() {
		Permission permission = service.find(1);
		assertEquals("query", permission.getName());
	}

	@Test
	public void findAll() {
		List<Permission> permissions = service.findAll();
		assertEquals("query", permissions.get(0).getName());
		assertEquals(4, permissions.size());
	}
}
