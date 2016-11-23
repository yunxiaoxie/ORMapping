package com.crab.mybatis.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.crab.mybatis.service.SysDataDicService;

/**
 * Base controller.
 * 
 * @author YunxiaoXie
 *
 */
@Controller
public class BaseController {

	@Autowired
	private SysDataDicService service;

	/**
	 * 查询用户
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("dataDic/{code}")
	public String findUser(@PathVariable int code) {
//		if (1004 == code) {
//			return "[{\"id\":1, \"name\":\"看书\", \"model\":\"book\"},{\"id\":2, \"name\":\"跑步\", \"model\":\"run\"},{\"id\":3, \"name\":\"打球\", \"model\":\"ball\"}]";
//		}
		if (1005 == code) {
			return "[{\"id\":1, \"comet\":\"语文\", \"dic_value\":\"yw\"},{\"id\":2, \"comet\":\"数学\", \"dic_value\":\"sx\"},{\"id\":3, \"comet\":\"英语\", \"dic_value\":\"english\"}]";
		}
		if (1006 == code) {
			return "[{\"id\": 1,\"pId\": 0,\"name\": \"普通的父节点\",\"title\": \"我很普通，随便点我吧\",\"open\": true},"
					+ "{\"id\": 11,\"pId\": 1,\"name\": \"叶子节点 - 1\",\"title\": \"我很普通，随便点我吧\",\"ui-sref\":\"user\"},"
					+ "{\"id\": 2,\"pId\": 0,\"name\": \"NB的父节点\",\"title\": \"点我可以，但是不能点我的子节点，有本事点一个你试试看？\",\"open\": true},"
					+ "{\"id\": 21,\"pId\": 2,\"name\": \"叶子节点2 - 1\",\"title\": \"你哪个单位的？敢随便点我？小心点儿..\",\"click\": false,\"ui-sref\":\"user\"},"
					+ "{\"id\": 3,\"pId\": 0,\"name\": \"郁闷的父节点\",\"title\": \"别点我，我好害怕...我的子节点随便点吧...\",\"open\": true,\"click\": false},"
					+ "{\"id\": 31,\"pId\": 3,\"name\": \"叶子节点3 - 1\",\"title\": \"唉，随便点我吧\",\"ui-sref\":\"user\"}]";
		}
		return JSON.toJSONString(service.findByCode(code));
	}
}
