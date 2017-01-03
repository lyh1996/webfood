package com.yc.biz.impl;

import java.util.Date;
 
import java.util.Set;

import redis.clients.jedis.Jedis;

public class HistroyBizImpl {
	public static void saveHistroyInfo( Jedis jedis,String key, String content){
		 jedis.zadd(key, new Date().getTime(),content);
		 jedis.expire(key, 60*60*24*30);
		// jedis.zrem(key, members)
	 
	}
	public static Set<String> getTopNHistroy(  Jedis jedis, String key, int topn){
		Long count=jedis.zcard(key);
		if(count<=topn){
			return jedis.zrevrange(key, 0, count);
		}else{
			return jedis.zrevrange(key, 0, topn-1);
		}
	}
	 
}
