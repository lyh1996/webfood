package com.yc.bean;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

public class Resorder implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3848803853409661148L;
	
	private Integer roid ;
	private Integer userid;
	private String address ;
	private String tel;
	private String ordertime;
	private String deliverytime;
	private String ps;
	private Integer status;
	
	public String getDeliveryTimeLong() {
		DateFormat df=new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
		Date date=null;
		try{
	
		 date=df.parse( deliverytime );
		}catch(ParseException e){
			e.printStackTrace();
		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		df=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return df.format(date);
	}
	
	
	public Resorder() {
		super();
	}
	public Resorder(Integer roid, Integer userid, String address, String tel, String ordertime, String deliverytime,
			String ps, Integer status) {
		super();
		this.roid = roid;
		this.userid = userid;
		this.address = address;
		this.tel = tel;
		this.ordertime = ordertime;
		this.deliverytime = deliverytime;
		this.ps = ps;
		this.status = status;
	}
	@Override
	public String toString() {
		return "Resorder [roid=" + roid + ", userid=" + userid + ", address=" + address + ", tel=" + tel
				+ ", ordertime=" + ordertime + ", deliverytime=" + deliverytime + ", ps=" + ps + ", status=" + status
				+ "]";
	}
	public Integer getRoid() {
		return roid;
	}
	public void setRoid(Integer roid) {
		this.roid = roid;
	}
	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getOrdertime() {
		return ordertime;
	}
	public void setOrdertime(String ordertime) {
		this.ordertime = ordertime;
	}
	public String getDeliverytime() {
		return deliverytime;
	}
	public void setDeliverytime(String deliverytime) {
		this.deliverytime = deliverytime;
	}
	public String getPs() {
		return ps;
	}
	public void setPs(String ps) {
		this.ps = ps;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}
