package com.yc.biz.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.yc.bean.Resfood;
import com.yc.biz.ResfoodBiz;
import com.yc.biz.ResorderBiz;
import com.yc.dao.DBHelper;
import com.yc.utils.YcConstant;
import com.yc.utils.redisutil.fn5.util.RedisUtil;

import redis.clients.jedis.Jedis;

public class ResfoodBizImpl implements ResfoodBiz {
	private DBHelper db = new DBHelper();

	private Jedis jedis = new Jedis(YcConstant.REDIS_URL, YcConstant.REDIS_ID);

	RedisUtil<Resfood> ru = new RedisUtil<Resfood>();

	public Resfood getResfoodByFid(Integer fid) throws NumberFormatException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Resfood resfood = null;
		List<Resfood> list = null;
		try {
			jedis.connect();
			if (jedis.isConnected() == true && jedis.keys(YcConstant.ALLFOOD + ":" + fid).size() > 0) {
				list = ru.getFormHash(jedis, YcConstant.ALLFOOD + ":" + fid, "fid", Resfood.class);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		if (list == null) {
			List<Object> params = new ArrayList<Object>();
			params.add(fid);
			list = db.find("select * from resfood where fid=?", Resfood.class, params);
		}

		if (list != null && list.size() > 0) {
			resfood = list.get(0);
		}
		return resfood;
	}

	@Override
	public List<Resfood> findAll() throws Exception {
		// 1.判断jedis中是否有数据，如果有，则用redis中的数据
		// 2、没有的话则从数据查一次
		List<Resfood> list = null;
		try {
			jedis.connect();
			if (jedis.isConnected() == true && jedis.keys(YcConstant.ALLFOOD + ":*").size() > 0) {
				RedisUtil<Resfood> ru = new RedisUtil<Resfood>();
				list = ru.getFormHash(jedis, YcConstant.ALLFOOD + ":*", "fid", Resfood.class);
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();

		}

		// 取出键
		/*
		 * Set<String> keys=jedis.keys(YcConstant.ALLFOOD+":*"); list=new
		 * ArrayList<Resfood>(); for(String key : keys){
		 * 
		 * Map<String, String> map=jedis.hgetAll(key); Resfood rf=new Resfood();
		 * for(Map.Entry<String, String> entry :map.entrySet()){ String
		 * k=entry.getKey(); String value=entry.getValue(); if("fid".equals(k)){
		 * rf.setFid(Integer.parseInt(value)); }else if("fname".equals(k)){
		 * rf.setFname(value); }else if("normprice".equals(k)){
		 * rf.setNormprice(Double.parseDouble(value)); } } list.add(rf); }
		 */
		 
		if (list == null) {
			 
			list = db.find("SELECT * FROM resfood", Resfood.class);

			// 存入的形式 ：allfood:1 fid:??? fname:??
			/*
			 * for(Resfood rf:list){
			 * jedis.hset(YcConstant.ALLFOOD+":"+rf.getFid(),
			 * "fid",rf.getFid()+"");
			 * jedis.hset(YcConstant.ALLFOOD+":"+rf.getFid(),
			 * "fname",rf.getFname()+"");
			 * jedis.hset(YcConstant.ALLFOOD+":"+rf.getFid(),
			 * "normprice",rf.getNormprice()+"");
			 * jedis.hset(YcConstant.ALLFOOD+":"+rf.getFid(),
			 * "realprice",rf.getRealprice()+"");
			 * jedis.hset(YcConstant.ALLFOOD+":"+rf.getFid(),
			 * "detail",rf.getDetail()+"");
			 * jedis.hset(YcConstant.ALLFOOD+":"+rf.getFid(),
			 * "fphoto",rf.getFphoto()+""); }
			 */

			RedisUtil<Resfood> ru = new RedisUtil<Resfood>();
			ru.saveToHash(jedis, YcConstant.ALLFOOD, "fid", list, Resfood.class);
		}
		return list;
	}

	public static void main(String[] args) throws Exception {
		ResfoodBizImpl rbl = new ResfoodBizImpl();
		
		  List<Resfood> list=rbl.findAll();
		  for(Resfood f:list){
			  	System.out.println(f); 
			  }
		 
		//Resfood rf = rbl.getResfoodByFid(1);
		//System.out.println(rf);
	}

}
