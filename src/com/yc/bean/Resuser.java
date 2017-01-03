package com.yc.bean;

import java.io.Serializable;

public class Resuser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1676684284807690359L;
	
	
	private Integer userid ;
	private String username ;
	private String pwd ;
	private String email ;
	
	private String valcode;
	
	
	public Resuser() {
		super();
	}
	public Resuser(Integer userid, String username, String pwd, String email) {
		super();
		this.userid = userid;
		this.username = username;
		this.pwd = pwd;
		this.email = email;
	}
	@Override
	public String toString() {
		return "Resuser [userid=" + userid + ", username=" + username + ", pwd=" + pwd + ", email=" + email + "]";
	}
	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getValcode() {
		return valcode;
	}
	public void setValcode(String valcode) {
		this.valcode = valcode;
	}
	
	

}
