package com.yx.test;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.crab.mybatis.Application;
import com.crab.mybatis.domain.SysDataDic;
import com.crab.mybatis.service.SysDataDicService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringBootTest(classes = Application.class)
public class SysDataDicTest {

	@Autowired
	private SysDataDicService service;

	@Before
	public void setup() {

	}

	@Test
	public void find() {
		List<SysDataDic> list = service.findByCode(1001);
		assertEquals(2, list.size());
	}

}
