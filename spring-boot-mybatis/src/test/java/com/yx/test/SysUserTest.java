package com.yx.test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.crab.mybatis.Application;
import com.crab.mybatis.domain.SysUser;
import com.crab.mybatis.service.SysUserService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringApplicationConfiguration(classes = Application.class)
public class SysUserTest {

	@Autowired
	private SysUserService service;

	@Before
	public void setup() {

	}

	@Test
	public void find() {
		SysUser bean = service.find(1);
		assertEquals("test", bean.getUser());
	}

}
