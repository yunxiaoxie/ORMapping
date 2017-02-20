package com.crab.shiro.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.crab.shiro.domain.Module;
import com.crab.shiro.domain.User;
import com.crab.shiro.exception.SystemException;
import com.crab.shiro.service.IAcl;
import com.crab.shiro.service.IModule;
import com.crab.shiro.service.IUser;
import com.crab.shiro.vo.Acls;

@Controller
public class IndexController {

	@Autowired
	private IAcl aclService;
	@Autowired
	private IModule moduleService;
	@Autowired
	private IUser userService;
	
	@ResponseBody
	@RequestMapping(value="updatePermission", method=RequestMethod.POST)
	public String updatePermission(Integer moduleId, String permission, Boolean yesno) {
		Map<String, Boolean> result = new HashMap<>();
		try {
			Subject subject = SecurityUtils.getSubject();
			User user = userService.findUserByAccount(subject.getPrincipal().toString());
			aclService.updatePermission(moduleId, user.getId(), permission, yesno);
			result.put("success", true);
		} catch(Exception e) {
			result.put("success", false);
		}
		return JSON.toJSONString(result);
	}
	
	@ResponseBody
	@RequestMapping(value="getModuleByUser", method=RequestMethod.GET)
	public String getModuleByUser() {
		Subject subject = SecurityUtils.getSubject();
		List<Acls> acls = new ArrayList<>();
		User user = userService.findUserByAccount(subject.getPrincipal().toString());
		List<Module> modules = aclService.searchModules(subject.getPrincipal().toString(), null);
		for (Module m : modules) {
			Acls acl = new Acls();
			acl.setModuleId(m.getId());
			acl.setModuleName(m.getName());
			acl.setModuleUrl(m.getUrl());
			if (m.getPid() != null) {
				// set parent module.
				Module module = moduleService.findModule(m.getPid());
				acl.setModuleExtend(module.getName());
			}
			// get module power
			Map<String, Boolean> powerMap = aclService.searchModulePermission(user.getId(), m.getId());
			acl.setPowers(getPower(powerMap));
			acls.add(acl);
		}
		return JSON.toJSONString(acls);
	}
	
	private List<Acls.Power> getPower(Map<String, Boolean> powerMap) {
		List<Acls.Power> list = new ArrayList<>();
		for (Map.Entry<String, Boolean> entry : powerMap.entrySet()) {
			Acls.Power power = new Acls().new Power();
			power.setPowerName(entry.getKey());
			power.setPowerBool(entry.getValue());
			list.add(power);
		}
		return list;
	}
	
	@RequestMapping(value="/execption")
    public void execption(String id) throws SystemException {
		if (StringUtils.isEmpty(id)) {
			throw new SystemException("发生系统错误");
		}
    }
	
	/**
	 * Go Index
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value={"", "/", "index"})
	public String index(String id) throws Exception {
		return "index.jsp";
	}
	
	@RequestMapping("unauthor")
	public String unauthor() {
		return "unauthor.jsp";
	}
	
	@RequestMapping("/reports")
	public String reports() {
		return "reports.jsp";
	}
	
	@RequestMapping("/acl")
	public String acl() {
		return "acl.jsp";
	}
}
