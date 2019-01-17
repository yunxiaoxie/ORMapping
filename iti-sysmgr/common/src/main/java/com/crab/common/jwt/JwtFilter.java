package com.crab.common.jwt;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.crab.common.Constant;
import com.crab.common.vo.Audience;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;


//@Component
public class JwtFilter extends GenericFilterBean {
	@Autowired
	private Audience audienceEntity;

	private boolean isContains(List<String> urls, String url) {
		List<String> list = urls.stream().filter(u -> url.endsWith(u) || url.contains(u))
				.collect(Collectors.toList());
		return list!=null&&!list.isEmpty();
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String url = httpRequest.getRequestURL().toString();
		//not filter urls
		List<String> urls = Arrays.asList("course/", "/login", "/logout", "v2/api-docs", "swagger-ui", "swagger-resources");
		//only for '/login'
		// 复杂ajax跨域请求前会先发出OPTIONS请求
		if (StringUtils.isNotEmpty(url) && isContains(urls, url)
				|| "OPTIONS".equals(httpRequest.getMethod())) {
			chain.doFilter(request, response);
		} else {
			String jwt = httpRequest.getHeader("Authorization");
			HttpServletResponse httpResponse = (HttpServletResponse) response;
			httpResponse.setCharacterEncoding("UTF-8");
			httpResponse.setContentType("application/json; charset=utf-8");
			if (StringUtils.isNoneBlank(jwt)) {
				String bearer = jwt.split(" ")[0];
				if (Constant.Bearer.equals(bearer)) {
			        String username = JwtHelper.parseJWT(jwt.split(" ")[1], audienceEntity.getBase64Secret()).getSubject();
			        String subjectName = (String) SecurityUtils.getSubject().getPrincipal();
					if (username != null && username.equals(subjectName)) {
						chain.doFilter(request, response);
					} else {
						httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
						//Don't use getWriter() to write json.
			            httpResponse.getOutputStream().print("{\"result\":\"You need a login.\"}");
					}
				}
			} else {
				//chain.doFilter(request, response);
				httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				//Don't use getWriter() to write json.
				httpResponse.getOutputStream().print("{\"result\":\"You need a 'Authorization' of http header.\"}");
			}
		}
	}

	@Override
	public void destroy() {
	}
}
