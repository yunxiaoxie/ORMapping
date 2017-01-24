package com.crab;

import org.apache.commons.collections.Factory;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.AbstractFactory;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.crab.shiro.SimpleApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringBootTest(classes = SimpleApplication.class)
public class Role2Test extends AbstractShiroTest {

	@Before
	public void setup() {
	}

	@SuppressWarnings("unchecked")
	@BeforeClass
	public static void beforeClass() {
		// 0. Build and set the SecurityManager used to build Subject instances
		// used in your tests
		// This typically only needs to be done once per class if your shiro.ini
		// doesn't change,
		// otherwise, you'll need to do this logic in each test that is
		// different
		Factory factory = (Factory) new IniSecurityManagerFactory("classpath:test.shiro.ini");
		setSecurityManager(((AbstractFactory<SecurityManager>) factory).getInstance());
	}

	@Test
	public void testSimple() {
		// 1. Build the Subject instance for the test to run:
		Subject subjectUnderTest = new Subject.Builder(getSecurityManager()).buildSubject();

		// 2. Bind the subject to the current thread:
		setSubject(subjectUnderTest);

		// perform test logic here. Any call to
		// SecurityUtils.getSubject() directly (or nested in the
		// call stack) will work properly.
		Assert.assertTrue(subjectUnderTest.hasRole("role1"));
	}

	@AfterClass
	public void tearDownSubject() {
		// 3. Unbind the subject from the current thread:
		clearSubject();
	}
}
