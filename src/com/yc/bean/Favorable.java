package com.yc.bean;

public class Favorable {
	private Integer f_id ;
	private String f_name ;
	private String man ;
	private String jian ;
	
	
	
	public Favorable() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Favorable(Integer f_id, String f_name, String man, String jian) {
		super();
		this.f_id = f_id;
		this.f_name = f_name;
		this.man = man;
		this.jian = jian;
	}
	@Override
	public String toString() {
		return "Favorable [f_id=" + f_id + ", f_name=" + f_name + ", man=" + man + ", jian=" + jian + "]";
	}
	public Integer getF_id() {
		return f_id;
	}
	public void setF_id(Integer f_id) {
		this.f_id = f_id;
	}
	public String getF_name() {
		return f_name;
	}
	public void setF_name(String f_name) {
		this.f_name = f_name;
	}
	public String getMan() {
		return man;
	}
	public void setMan(String man) {
		this.man = man;
	}
	public String getJian() {
		return jian;
	}
	public void setJian(String jian) {
		this.jian = jian;
	}
	
	

}
