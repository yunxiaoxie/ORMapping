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
import com.crab.shiro.domain.Role;
import com.crab.shiro.service.IRole;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringBootTest(classes = SimpleApplication.class)
public class RoleTest {

	@Autowired
	private IRole service;

	@Before
	public void setup() {

	}

	@Test
	public void findOne() {
		Role role = service.findRole(1);
		assertEquals("admin", role.getRoleName());
	}

	@Test
	public void searchRolesOfUser() {
		List<Integer> roleIds = service.searchRoleIdsOfUser(1);
		System.out.println(roleIds);
		assertEquals(2, roleIds.size());
	}
}
