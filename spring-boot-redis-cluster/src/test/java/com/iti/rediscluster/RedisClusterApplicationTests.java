package com.iti.rediscluster;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.iti.rediscluster.util.SerializeUtil;
import com.iti.rediscluster.vo.Student;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.JedisCluster;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisClusterApplicationTests {

	@Autowired
	private JedisCluster jedisCluster;

	@Test
	public void set_get() {
		jedisCluster.set("testkey", "testvalue");
		Assert.assertEquals("testvalue", jedisCluster.get("testkey"));
		jedisCluster.del("testkey");
	}

	@Test
	public void hset_hget() {
		jedisCluster.hset("webside", "google", "www.google.com");
		Assert.assertEquals("www.google.com", jedisCluster.hget("webside", "google"));
		jedisCluster.del("webside");
	}

	@Test
	public void hmset_hmget() {
		Map<String, String> map = new HashMap<>();
		map.put("google", "www.google.com");
		map.put("baidu", "www.baidu.com");
		jedisCluster.hmset("webside", map);
		Assert.assertEquals("www.google.com", jedisCluster.hget("webside", "google"));
		Assert.assertEquals(2, jedisCluster.hmget("webside", "google", "baidu").size());
		jedisCluster.del("webside");
	}

	@Test
	public void hset_json_obj() {
		// 非格式化
		String s = JSON.toJSONString(getStudent(),false);
		jedisCluster.hset("jsonObject", "student", s);
		//Student stu = JSON.parseObject(jedisCluster.hget("jsonObject", "student"), Student.class);
		Student stu = SerializeUtil.deserialize(jedisCluster.hget("jsonObject", "student"), Student.class);
		Assert.assertEquals("张三", stu.getName());
		jedisCluster.del("jsonObject");
	}

	@Test
	public void hset_json_obj_byte() {
		jedisCluster.hset("jsonObject".getBytes(), "student".getBytes(), SerializeUtil.serizlize(getStudent()));
		Student stu = (Student) SerializeUtil.deserialize(jedisCluster.hget("jsonObject".getBytes(), "student".getBytes()));
		Assert.assertEquals("张三", stu.getName());
		jedisCluster.del("jsonObject");
	}

	@Test
	public void hset_json_list() {
		// 非格式化
		String s = JSON.toJSONString(getStudentList());
		System.out.println(s);
		jedisCluster.hset("jsonObjectList", "student", s);
		//List<Student> stus = JSON.parseObject(jedisCluster.hget("jsonObjectList", "student"), new TypeReference<List<Student>>(){});
		List<Student> stus = SerializeUtil.deserialize(jedisCluster.hget("jsonObjectList", "student"), new TypeReference<List<Student>>(){});
		Assert.assertEquals(2, stus.size());
		Assert.assertEquals("张三", stus.get(0).getName());
		//jedisCluster.del("jsonObjectList");
	}

	@Test
	public void hset_json_map() {
		Map<String, String> map = new HashMap<>();
		map.put("k1", "v1");
		map.put("k2", "v2");
		map.put("k3", "v3");
		List<Map<String, String>> list = new ArrayList<>();
		list.add(map);
		// 非格式化
		String s = JSON.toJSONString(list,false);
		System.out.println(s);
		jedisCluster.hset("jsonObjectMap", "student", s);
		List<Map<String, String>> stus = JSON.parseObject(jedisCluster.hget("jsonObjectMap", "student"), new TypeReference<List<Map<String, String>>>(){});
		Assert.assertEquals(1, stus.size());
		Assert.assertEquals("v1", stus.get(0).get("k1"));
		jedisCluster.del("jsonObjectMap");
	}

	private Student getStudent() {
		Student student = new Student();
		student.setName("张三");
		student.setAge(22);
		student.setSex("男");
		student.setAddress("武汉");
		return student;
	}

	private List<Student> getStudentList() {
		List<Student> list = new ArrayList<>();
		Student student = new Student();
		student.setName("张三");
		student.setAge(22);
		student.setSex("男");
		student.setAddress("武汉");
		list.add(student);
		Student student2 = new Student();
		student2.setName("李四");
		student2.setAge(23);
		student2.setSex("男");
		student2.setAddress("北京");
		list.add(student2);
		return list;
	}

}
