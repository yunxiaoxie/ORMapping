package com.crab.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.crab.domain.Module;
import com.crab.service.IAcl;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


//@Component("urlPermissionsFilter")
public class URLPermissionsFilter extends PermissionsAuthorizationFilter{
	@Autowired
	private IAcl aclService;

	@Override
	public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws IOException {
		String curUrl = getRequestUrl(request);
		// remove get parameter
		if (StringUtils.isNotEmpty(curUrl) && curUrl.indexOf("?") != -1) {
			curUrl = curUrl.substring(0, curUrl.indexOf("?"));
		}
		Subject subject = SecurityUtils.getSubject();
		if(subject.getPrincipal() == null 
				|| StringUtils.endsWithAny(curUrl, ".js",".css",".html")
				|| StringUtils.endsWithAny(curUrl, ".jpg",".png",".gif", ".jpeg")
				|| StringUtils.startsWith(curUrl, "/fonts")
				|| Arrays.asList("/execption", "/unauthor", "/getModuleByUser", "/updatePermission", "/login", "/authorize", "/accessToken", "/user/info")
				.contains(curUrl)	) {
			return true;
		}
		List<Module> modules = aclService.searchModules(subject.getPrincipal().toString(), null);
		List<String> urls = new ArrayList<>();
		for (Module m : modules) {
			if (StringUtils.isNotEmpty(m.getUrl())) {
				urls.add(m.getUrl());
			}
		}
		return urls.contains(curUrl);
	}
	
	/**
	 * 获取当前URL+Parameter
	 * @param request	拦截请求request
	 * @return			返回完整URL
	 */
	private String getRequestUrl(ServletRequest request) {
		HttpServletRequest req = (HttpServletRequest)request;
		String queryString = req.getQueryString();

		queryString = StringUtils.isBlank(queryString)?"": "?"+queryString;
		return req.getRequestURI()+queryString;
	}
}
