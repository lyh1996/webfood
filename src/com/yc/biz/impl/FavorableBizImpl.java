package com.yc.biz.impl;

import java.util.List;


import com.yc.bean.Favorable;
import com.yc.biz.FavorableBiz;
import com.yc.dao.DBHelper;

public class FavorableBizImpl implements FavorableBiz {
	private DBHelper db=new DBHelper();
	@Override
	public List<Favorable> findAll() throws Exception {
		 String sql="select * from favorable order by f_id desc";
		 List<Favorable> list=db.find(sql, Favorable.class);
		return list;
	}

}
