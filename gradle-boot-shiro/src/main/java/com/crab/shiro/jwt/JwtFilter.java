package com.crab.shiro.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import com.crab.shiro.vo.Audience;

@Component
public class JwtFilter extends GenericFilterBean {
	@Autowired
	private Audience audienceEntity;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String url = httpRequest.getRequestURL().toString();
		if (StringUtils.isNotEmpty(url) && !url.endsWith("/login")) {
			String jwt = httpRequest.getHeader("Authorization");
			if (jwt != null && jwt.length() > 7) {
				String HeadStr = jwt.substring(0, 6);
				if ("Bearer".equals(HeadStr)) {
					//auth = auth.substring(7, auth.length());
					jwt = jwt.substring(jwt.indexOf(" "));
			        String username = JwtHelper.parseJWT(jwt, audienceEntity.getBase64Secret()).getSubject();
			        String subjectName = (String) SecurityUtils.getSubject().getPrincipal();
					if (username != null && username.equals(subjectName)) {
						chain.doFilter(request, response);
						return;
					}
				}
			}
		} else {
			chain.doFilter(request, response);
		}

		HttpServletResponse httpResponse = (HttpServletResponse) response;
		httpResponse.setCharacterEncoding("UTF-8");
		httpResponse.setContentType("application/json; charset=utf-8");
		httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		httpResponse.getWriter().write("Need a request header Authorization for JWT.");
		return;
	}

	@Override
	public void destroy() {
	}
}
