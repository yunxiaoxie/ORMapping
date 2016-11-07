package wx.redis;

import org.junit.Test;

import com.redis.RedisUtil;

public class RedisTest {
	@Test
	public void redisTest() {
		RedisUtil.setObject("jzyName", "金震宇");
		if (RedisUtil.exists("jzyName")) {
			String a = (String) RedisUtil.getObject("jzyName");
			System.out.println(a);
		}
	}

	@Test
	public void redisTest2() {
	}

}
