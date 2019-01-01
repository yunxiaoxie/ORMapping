package com.crab;

import com.crab.domain.Role;
import com.crab.service.IRole;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringBootTest(classes = Application.class)
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
