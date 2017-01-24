package com.crab;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.crab.shiro.SimpleApplication;
import com.crab.shiro.domain.Acl;
import com.crab.shiro.domain.Module;
import com.crab.shiro.service.IAcl;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringBootTest(classes = SimpleApplication.class)
public class AclTest {

	@Autowired
	private IAcl service;

	@Before
	public void setup() {

	}

	@Test
	public void searchAclRecord() {
		List<Acl> acls = service.searchAclRecord("ROLE", 1);
		System.out.println(acls);
	}
	
	@Test
	public void searchModules() {
		List<Module> modules = service.searchModules(1, null);
		System.out.println(modules);
	}
	
	@Test
	public void hasPermission() {
		Boolean bool = service.hasPermission(1, 1, 1);
		assertTrue(bool);
	}
}
