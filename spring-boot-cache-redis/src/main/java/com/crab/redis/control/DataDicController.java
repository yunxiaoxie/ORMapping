package com.crab.redis.control;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.crab.redis.service.ISysDataDicService;
import com.redis.RedisUtil;

@Controller
public class DataDicController {
	
	private static final Logger LOG = LoggerFactory.getLogger(DataDicController.class);
	@Autowired
	private ISysDataDicService service;

	@ResponseBody
	@RequestMapping("findAllDataDic")
	public void findAllDataDic() {
		Map<String, String> map = service.findAll();
		// write to REDIS.
		LOG.info("***start write data to redis.");
		for (Map.Entry<String, String> entry : map.entrySet()) {
			LOG.info("add " + entry.getKey());
			RedisUtil.set(entry.getKey(), entry.getValue());
		}
		LOG.info("***write data to redis end.");
	}

	@ResponseBody
	@RequestMapping("findDataDic/{id}")
	public String findDataDicById(@PathVariable int id) {
		return JSON.toJSONString(service.findOne(id));
	}
}
