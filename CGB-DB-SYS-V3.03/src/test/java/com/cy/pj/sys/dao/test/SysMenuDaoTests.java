package com.cy.pj.sys.dao.test;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.cy.pj.sys.dao.SysMenuDao;
@SpringBootTest
public class SysMenuDaoTests {
	@Autowired
	private SysMenuDao sysMeunDao;
	
	public void testFindObjects() {
		long t1=System.nanoTime();
		List<Map<String,Object>> list=
		sysMeunDao.findObjects();
		
	}
}
