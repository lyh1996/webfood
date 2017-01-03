package com.yc.biz;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.yc.bean.Resfood;

public interface ResfoodBiz {
	public abstract List<Resfood> findAll() throws Exception;
	 
	
	public Resfood getResfoodByFid(Integer fid)
			throws NumberFormatException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException;
}
