package com.cy.pj.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysUserRoleDao {
	
	int deleteObjectsByRoleId(Integer roleId);

	int insertObjects(Integer userId, Integer[] roleIds);
	
	List<Integer> findRoleIdsByUserId(Integer id);
	int deleteObjectsByUserId(Integer userId);
}
