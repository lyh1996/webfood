package com.yc.biz.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.yc.dao.DBHelper;

public class MonthCountImpl {
	private DBHelper db=new DBHelper();
	
	public  List<String> getMonthCount(String fid){
		List<String> list=null;
		List<Object> params = new ArrayList<Object>();
		String sql="select sum(num) from resorderitem where fid=?";
		params.add(fid);
		list =db.find(sql, params);
		return list  ;
		
	}

}
