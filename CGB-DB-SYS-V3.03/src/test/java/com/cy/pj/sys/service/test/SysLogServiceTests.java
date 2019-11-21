package com.cy.pj.sys.service.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.cy.pj.common.vo.PageObject;
import com.cy.pj.sys.service.SysLogService;
import com.cy.pj.sys.entity.SysLog;
@SpringBootTest
public class SysLogServiceTests {
	@Autowired
	private SysLogService sysLogService;
	@Test
	public void TestFindPageObject() {
		PageObject<SysLog>pageObject=sysLogService.findPageObjects("admin", 3);
		System.out.println(pageObject.getPageCount());
		System.out.println(pageObject);
	}
}
