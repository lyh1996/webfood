package com.yc.biz;

import java.util.List;

import com.yc.bean.Favorable;

public interface FavorableBiz {
	public abstract List<Favorable> findAll() throws Exception;
}
