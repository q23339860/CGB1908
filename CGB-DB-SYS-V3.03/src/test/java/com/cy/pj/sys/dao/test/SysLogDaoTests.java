package com.cy.pj.sys.dao.test;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.cy.pj.sys.dao.SysLogDao;

@SpringBootTest
public class SysLogDaoTests {
	@Autowired
	private SysLogDao sysLogDao;
	@Test
	public void SysLogDaoTests() {
		int rows=sysLogDao.getRowCount("admin");
		System.out.println(rows);
	}
}
