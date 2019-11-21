package com.cy.pj.common.vo;

import java.util.List;

import com.cy.pj.sys.entity.SysLog;

import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
public class PageObject<T> {
	private List<T> records;
	private Integer rowCount;
	private Integer pageCount;
	private Integer pageCurrent;
	private Integer pageSize;
	public PageObject(List<T> records,Integer pageSize,Integer pageCurrent,Integer rowCount) {
		super();
		this.pageCurrent=pageCurrent;
		this.pageSize=pageSize;
		this.rowCount=rowCount;
		this.records=records;
	}
	public List<T> getRecords() {
		return records;
	}
	public void setRecords(List<T> records) {
		this.records = records;
	}
	public Integer getRowCount() {
		return rowCount;
	}
	public void setRowCount(Integer rowCount) {
		this.rowCount = rowCount;
	}
	public Integer getPageCount() {
		return pageCount;
	}
	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}
	public Integer getPageCurrent() {
		return pageCurrent;
	}
	public void setPageCurrent(Integer pageCurrent) {
		this.pageCurrent = pageCurrent;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
}
