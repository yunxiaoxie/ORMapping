

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.crab.redis.SimpleApplication;
import com.crab.redis.domain.SysDataDic;
import com.crab.redis.service.ISysDataDicService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringApplicationConfiguration(classes = SimpleApplication.class)
public class SysUserTest {

	@Autowired
	private ISysDataDicService service;

	@Before
	public void setup() {

	}

	@Test
	public void find() {
		SysDataDic bean = service.findOne(2);
		assertEquals("tree", bean.getDic_key());
	}
	
	@Test
	public void findAll() {
		Map<String, String> list = service.findAll();
		for (Map.Entry<String, String> entry : list.entrySet()) {
			System.out.println(entry.getKey() + "==" + entry.getValue());
		}
	}

}
