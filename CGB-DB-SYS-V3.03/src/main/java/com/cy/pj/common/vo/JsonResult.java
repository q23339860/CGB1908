package com.cy.pj.common.vo;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
public class JsonResult implements Serializable{
	private static final long serialVersionUID=856924038217431339L;
	//状态码（服务器响应到客户端的状态码
	private int state=1;
	//表示状态码对象的状态信息
	private String message="ok";
	//表示响应到客户端的具体数据
	private Object data;
	public JsonResult() {}
	public JsonResult(String message) {
		this.message=message;
	}
	public JsonResult(Object data) {
		this.data=data;
	}
	public JsonResult(Throwable t) {
		this.state=0;
		this.message=t.getMessage();
	}

}


