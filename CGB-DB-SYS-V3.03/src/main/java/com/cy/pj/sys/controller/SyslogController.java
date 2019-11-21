package com.cy.pj.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cy.pj.common.vo.JsonResult;
import com.cy.pj.common.vo.PageObject;
import com.cy.pj.sys.entity.SysLog;
import com.cy.pj.sys.service.SysLogService;

//日志控制层对象
//核心业务
//1)请求数据处理（请求路径，请求方式，
@RestController
@RequestMapping(value = {"/log/"},produces = "application/json;text/html;charset=utf-8")
public class SyslogController {
	@Autowired
	private SysLogService sysLogService;

	@GetMapping("doFindPageObjects")
	public JsonResult doFindPageObjects(String username, Integer pageCurrent) {
		PageObject<SysLog> pageObject = sysLogService.findPageObjects(username, pageCurrent);
		return new JsonResult(pageObject);
	}// spring mvc 会借助jackson api将结果转换成json格式
	@RequestMapping("doDeleteObjects")
	@ResponseBody
	public JsonResult doDeleteObjects(Integer...ids) {
		sysLogService.deleteObjects(ids);
		return new JsonResult("delete ok");
	}
}
