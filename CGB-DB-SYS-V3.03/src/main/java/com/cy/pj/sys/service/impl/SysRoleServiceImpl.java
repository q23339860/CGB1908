package com.cy.pj.sys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.cy.pj.common.config.PageProperties;
import com.cy.pj.common.exception.ServiceException;
import com.cy.pj.common.vo.CheckBox;
import com.cy.pj.common.vo.PageObject;
import com.cy.pj.sys.dao.SysRoleDao;
import com.cy.pj.sys.dao.SysRoleMenuDao;
import com.cy.pj.sys.dao.SysUserRoleDao;
import com.cy.pj.sys.entity.SysLog;
import com.cy.pj.sys.entity.SysRole;
import com.cy.pj.sys.service.SysLogService;
import com.cy.pj.sys.service.SysRoleService;
import com.cy.pj.sys.vo.SysRoleMenuVo;
@Service
public class SysRoleServiceImpl implements SysRoleService{
	@Autowired
	private SysRoleDao sysRoleDao;
	@Autowired
	private PageProperties pageProperties;
	@Autowired
	private SysRoleMenuDao sysRoleMenuDao;
	@Autowired
	private SysUserRoleDao sysUserRoleDao;
	@Override
	public int deleteObject(Integer id) {
		if(id==null||id<1)
			throw new IllegalArgumentException("请先选择");
		sysRoleMenuDao.deleteObjectsByMenuId(id);
		sysUserRoleDao.deleteObjectsByRoleId(id);
		int rows =sysRoleDao.deleteObject(id);
		if(rows==0)
			throw new ServiceException("此菜单可能已经不存在");
		return rows;
	}
	@Override
	public PageObject<SysRole> findPageObjects(String name, Integer pageCurrent) {
		if(pageCurrent==null||pageCurrent<1)
			throw new IllegalArgumentException("当前页码值无效");
			int rowCount=sysRoleDao.getRowCount(name);
			if(rowCount==0)
				throw new ServiceException("没有找到对应记录");
			int pageSize=pageProperties.getPageSize();
			System.out.println(pageSize);
			int startIndex=(pageCurrent-1)*pageSize;
			List<SysRole> records=sysRoleDao.findPageObjects(name,startIndex,pageSize);
			PageObject<SysRole> pageObject = new PageObject<>();
			// 4.2)封装数据
			pageObject.setPageCurrent(pageCurrent);
			pageObject.setPageSize(pageSize);
			pageObject.setRowCount(rowCount);
			pageObject.setRecords(records);
			pageObject.setPageCount((rowCount - 1) / pageSize + 1);
		return pageObject;
	}
	@Override
	public int saveObject(SysRole entity, Integer[] menuIds) {
		if(entity==null)
			throw new IllegalArgumentException("保存对象不能为空");
		if(StringUtils.isEmpty(entity.getName()))
			throw new IllegalArgumentException("角色名不允许为空");
		if(menuIds==null||menuIds.length==0)
			throw new ServiceException("必须为角色分配权限");
		//2.保存角色自身信息
		int rows=sysRoleDao.insertObject(entity);
		//3.保存角色菜单关系数据
		sysRoleMenuDao.insertObjects(entity.getId(), menuIds);
		//4.返回业务结果
		return rows;
	}
	@Override
	public SysRoleMenuVo findObjectById(Integer id) {
    	//1.合法性验证
    	if(id==null||id<=0)
    	throw new ServiceException("id的值不合法");
    	//2.执行查询
    	SysRoleMenuVo result=sysRoleDao.findObjectById(id);
  	//3.验证结果并返回
    	if(result==null)
    	throw new ServiceException("此记录已经不存在");
    	return result;

	}
	@Override
	public int updateObject(SysRole entity, Integer[] menuIds) {
	    	//1.合法性验证
	    	if(entity==null)
	        throw new ServiceException("更新的对象不能为空");
	    	if(entity.getId()==null)
	    	throw new ServiceException("id的值不能为空");
	    	
	    	if(StringUtils.isEmpty(entity.getName()))
	    	throw new ServiceException("角色名不能为空");
	    	if(menuIds==null||menuIds.length==0)
	    	throw new ServiceException("必须为角色指定一个权限");
	    	
	    	//2.更新数据
	    	int rows=sysRoleDao.updateObject(entity);
	    	if(rows==0)
	        throw new ServiceException("对象可能已经不存在");
	    	sysRoleMenuDao.deleteObjectsByRoleId(entity.getId());
	    	sysRoleMenuDao.insertObjects(entity.getId(),menuIds);
	    	//3.返回结果
	    	return rows;
	  }
	@Override
    public List<CheckBox> findObjects() {
     	return sysRoleDao.findObjects();
    }


}
