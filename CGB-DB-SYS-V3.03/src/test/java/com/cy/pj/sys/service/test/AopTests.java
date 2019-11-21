package com.cy.pj.sys.service.test;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.cy.Application;
import com.cy.pj.common.vo.PageObject;
import com.cy.pj.sys.service.SysUserService;
import com.cy.pj.sys.vo.SysUserDeptVo;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AopTests {
	@Autowired
	private ApplicationContext ctx;

	@Test
	public void testSysUserService() {
		SysUserService userService = ctx.getBean("sysUserServiceImpl", SysUserService.class);
		System.out.println(userService.getClass());
		PageObject<SysUserDeptVo> po=userService.findPageObjects("admin", 1);
		System.out.println("rowCount:"+po.getRowCount());
	}
}
