package com.cy.pj.sys.dao;



import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.cy.pj.sys.entity.SysLog;

@Mapper
public interface SysLogDao {
	//基于条件统计用户行为日志
	int getRowCount(@Param("username") String username);
	
	List<SysLog> findPageObjects(
		@Param("username")String username,
		@Param("startIndex")Integer startIndex,
		@Param("pageSize")Integer pageSize);
	int deleteObjects(@Param("ids")Integer...ids);
	int insertObject(SysLog entity);
}