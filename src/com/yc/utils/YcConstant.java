package com.yc.utils;

/*
 * 专门用来用于定义系统中所使用的字符串常量
 */
public class YcConstant {

	//redis中用来村所有的菜的键，值的类型：String;  list; hash ;sort set ;Set ;为了实现排序效果用sort set
	public static final String ALLFOOD = "allfood";
	//redis连接地址
	public static final String REDIS_URL = "192.168.146.129";
	//redis连接端口
	public static final int REDIS_ID = 6379;
	//购物车在session中的键名
	public static final String  CART_NAME = "CART";
 
	//登入用户在session中的键名
	public static final String LOGIN_USER = "login";
	
	//redis中保存在线用户记录的天数
		public static final int KEEPNDAYSFORONLINEUSER = 7;
}
