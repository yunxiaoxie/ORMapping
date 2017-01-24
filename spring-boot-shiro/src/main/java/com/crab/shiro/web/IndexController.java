package com.crab.shiro.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

	/**
	 * Go Index
	 * @return
	 */
	@RequestMapping(value={"", "/", "index"})
	public String index() {
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
