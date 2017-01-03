package com.yc.test;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.yc.bean.Resfood;
import com.yc.dao.DBHelper;

public class Test1 {
	public static void main(String[] args) throws NumberFormatException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		DBHelper dbHelper=new DBHelper();
		List<Resfood> list=dbHelper.find("select * from resfood", Resfood.class);
		for(Resfood map:list){
			System.out.println(map);
		}
	}
}
