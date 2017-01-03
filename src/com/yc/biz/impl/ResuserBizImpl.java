package com.yc.biz.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import com.yc.bean.Resuser;
import com.yc.biz.ResuserBiz;
import com.yc.dao.DBHelper;
import com.yc.utils.Encrypt;
import com.yc.utils.YcConstant;
import com.yc.utils.redisutil.fn1.userlogin.UserRedis;

import redis.clients.jedis.Jedis;

public class ResuserBizImpl implements ResuserBiz{
	private Jedis jedis=new Jedis(YcConstant.REDIS_URL);
	private DBHelper db=new DBHelper();
	@Override
	
	public Resuser login(Resuser resuser) throws NumberFormatException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
	 resuser.setPwd(Encrypt.md5(resuser.getPwd()));
	// System.out.println(Encrypt.md5("a"));
	 String sql="select * from resuser where username=? and pwd=?";
	 List<Object> params=new ArrayList<Object>();
	 params.add(resuser.getUsername());
	 params.add(resuser.getPwd());
	  
	 List<Resuser> list=db.find(sql, Resuser.class,params);
	 resuser=list.size()>0?list.get(0):null;
	 UserRedis.activeUsers(jedis,list.get(0).getUserid());
		return resuser;
	}

}
