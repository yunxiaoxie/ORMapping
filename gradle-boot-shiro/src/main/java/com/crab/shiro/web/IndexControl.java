package com.crab.shiro.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crab.shiro.domain.Module;
import com.crab.shiro.domain.User;
import com.crab.shiro.exception.SystemException;
import com.crab.shiro.service.IAcl;
import com.crab.shiro.service.IModule;
import com.crab.shiro.service.IUser;
import com.crab.shiro.utils.Constant;
import com.crab.shiro.utils.LoggerUtil;
import com.crab.shiro.vo.Acls;

@RestController
@RequestMapping("/")
public class IndexControl extends BaseControl {

	@Autowired
	private IAcl aclService;
	@Autowired
	private IModule moduleService;
	@Autowired
	private IUser userService;

	@PostMapping("updatePermission")
	public ModelMap updatePermission(Integer moduleId, String permission, Boolean yesno) throws Exception {
		try {
			Subject subject = SecurityUtils.getSubject();
			User user = userService.findUserByAccount(subject.getPrincipal().toString());
			aclService.updatePermission(moduleId, user.getId(), permission, yesno);
			return retResult(Constant.HTTP_200, Constant.SUCCESS_MSG, true);
		} catch (Exception e) {
			LoggerUtil.error("method updatePermission " + e.getMessage(), e);
			return retResult(Constant.FAIL_MSG, e.getMessage(), null);
		}
	}
	
	@GetMapping("getModuleByUser")
	public ModelMap getModuleByUser() throws Exception {
		try {
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
			return retResult(Constant.HTTP_200, Constant.SUCCESS_MSG, acls);
		} catch (Exception e) {
			LoggerUtil.error("method updatePermission " + e.getMessage(), e);
			return retResult(Constant.FAIL_MSG, e.getMessage(), null);
		}
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

	@RequestMapping("execption")
	public void execption(String id) throws SystemException {
		if (StringUtils.isEmpty(id)) {
			throw new SystemException("发生系统错误");
		}
	}

}
