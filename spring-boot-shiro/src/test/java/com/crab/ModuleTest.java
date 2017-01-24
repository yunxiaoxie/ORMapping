package com.crab;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.crab.shiro.SimpleApplication;
import com.crab.shiro.domain.Module;
import com.crab.shiro.service.IModule;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringBootTest(classes = SimpleApplication.class)
public class ModuleTest {

	@Autowired
	private IModule service;

	@Before
	public void setup() {

	}

	@Test
	public void findOne() {
		Module module = service.findModule(1);
		assertEquals("Overview", module.getName());
	}

	
}
