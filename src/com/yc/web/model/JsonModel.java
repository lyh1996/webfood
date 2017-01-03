package com.yc.web.model;

import java.io.Serializable;

public class JsonModel implements Serializable {
 
	private static final long serialVersionUID = 1L;
	
	private Integer code;
	private Integer code2;
	private Integer code3;
	private Integer code4;
	private String errorMsg;
	private Object obj;
	private Object obj2;
	private Object obj3;
	private Object obj4;
	
	 
	
	public JsonModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public Object getObj() {
		return obj;
	}
	public void setObj(Object obj) {
		this.obj = obj;
	}
	public Integer getCode2() {
		return code2;
	}
	public void setCode2(Integer code2) {
		this.code2 = code2;
	}
	public Integer getCode3() {
		return code3;
	}
	public void setCode3(Integer code3) {
		this.code3 = code3;
	}
	public Object getObj2() {
		return obj2;
	}
	public void setObj2(Object obj2) {
		this.obj2 = obj2;
	}
	public Object getObj3() {
		return obj3;
	}
	public void setObj3(Object obj3) {
		this.obj3 = obj3;
	}
	public Integer getCode4() {
		return code4;
	}
	public void setCode4(Integer code4) {
		this.code4 = code4;
	}
	public Object getObj4() {
		return obj4;
	}
	public void setObj4(Object obj4) {
		this.obj4 = obj4;
	}
 	 
}
