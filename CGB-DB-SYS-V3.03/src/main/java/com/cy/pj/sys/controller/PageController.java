 package com.cy.pj.sys.controller;

import org.hibernate.validator.cfg.context.ReturnValueConstraintMappingContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class PageController {
	//返回首页页面
	@RequestMapping("doIndexUI")
	public String doIndexUI() {
		return "starter";
	}
	//返回分页页面
	@RequestMapping("doPageUI")
	public String dopageUI() {
		return "common/page";
	}
	//返回日志列表页面
	/*
	 * @RequestMapping("log/log_list") public String doLogUI() { return
	 * "sys/log_list"; }
	 */
	@RequestMapping("{module}/{moduleUI}")
	public String doModuleUI(@PathVariable String moduleUI) {
		return "sys/"+moduleUI;
	}
	@RequestMapping("doLoginUI")
	public String doLoginUI() {
		return "login";
	}
}
