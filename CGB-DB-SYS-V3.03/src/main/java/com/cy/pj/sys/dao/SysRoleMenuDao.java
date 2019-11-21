package com.cy.pj.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SysRoleMenuDao {
	int deleteObjectsByMenuId(Integer roleId);
	int insertObjects(
			@Param("roleId")Integer roleId,
			@Param("menuIds")Integer[] menuIds);
	int deleteObjectsByRoleId(Integer id);
	int insertObject(Integer id, Integer[] menuIds);
	List<Integer> findMenuIdsByRoleIds(
			@Param("roleIds")Integer[] roleIds);

}
