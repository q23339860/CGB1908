package com.cy.pj.sys.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.cy.pj.common.exception.ServiceException;
import com.cy.pj.common.vo.Node;
import com.cy.pj.sys.dao.SysMenuDao;
import com.cy.pj.sys.dao.SysRoleMenuDao;
import com.cy.pj.sys.entity.SysMenu;
import com.cy.pj.sys.service.SysMenuService;

@Service
public class SysMenuServiceImpl implements SysMenuService {
	@Autowired
	private SysMenuDao sysMenuDao;
	@Autowired
	private SysRoleMenuDao sysRoleMenuDao;
	@Cacheable(value="menuCache")
	@Override
	public List<Map<String, Object>> findObjects() {
		System.out.println("==MenuService==");
		List<Map<String, Object>> list = sysMenuDao.findObjects();
		if (list == null || list.size() == 0)
			throw new ServiceException("没有对应的菜单信息");
		return list;
	}

	@Override
	public int deleteObject(Integer id) {
		if (id == null || id <= 0)
			throw new IllegalArgumentException("请先选择");
		int count = sysMenuDao.getChildCount(id);
		if (count > 0)
			throw new ServiceException("请先删除子菜单");
		sysRoleMenuDao.deleteObjectsByMenuId(id);
		int rows = sysMenuDao.deleteObject(id);
		if (rows == 0)
			throw new ServiceException("此菜单可能已经不存在");
		return rows;
	}

	@Override
	public List<Node> findZtreeMenuNodes() {

		return sysMenuDao.findZtreeMenuNodes();
	}

	@Override
	public int saveObject(SysMenu entity) {
		// 1.合法验证
		if (entity == null)
			throw new ServiceException("保存对象不能为空");
		if (StringUtils.isEmpty(entity.getName()))
			throw new ServiceException("菜单名不能为空");
		int rows;
		// 2.保存数据
		try {
			System.err.println("entity:"+entity);
			rows = sysMenuDao.insertObject(entity);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("保存失败");
		}
		// 3.返回数据

		return rows;
	}

	@Override
	public int updateObject(SysMenu entity) {
		// 1.合法验证
		if (entity == null)
			throw new ServiceException("保存对象不能为空");
		if (StringUtils.isEmpty(entity.getName()))
			throw new ServiceException("菜单名不能为空");

		// 2.更新数据
		int rows = sysMenuDao.updateObject(entity);
		if (rows == 0)
			throw new ServiceException("记录可能已经不存在");
		// 3.返回数据
		return rows;
	}

}
