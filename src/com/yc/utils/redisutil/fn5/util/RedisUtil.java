package com.yc.utils.redisutil.fn5.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.sun.jndi.url.corbaname.corbanameURLContextFactory;
import com.yc.bean.Resfood;

import redis.clients.jedis.Jedis;

public class RedisUtil<T> {
	public void saveToHash(Jedis jedis, String keyPrefix, String id, List<T> list, Class<T> c)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		 
		String getIDMethodName = "get" + id.substring(0, 1).toUpperCase() + id.substring(1);
		// 取出所有的get方法
		Set<Method> methodGet = getMethod(c);
		for (T rf : list) {
			// 取出所有的id
			String itemid = keyPrefix + ":";// allfood:1 allfood:2
			for (Method m : methodGet) {
				if (m.getName().equals(getIDMethodName)) {
					itemid = itemid + m.invoke(rf).toString();
					break;
				}
			}
			for (Method m : methodGet) {
				// m name -->getFid() getFname -> fid fname
				String fieldName = m.getName().substring(3, 4).toLowerCase() + m.getName().substring(4);
				// jedis.hset ( ,"fid","fname");
				Object value=m.invoke(rf);
				if(value!=null){
				jedis.hset(itemid, fieldName, value.toString());
				}
			}
		}
	}

	public List<T> getFormHash(Jedis jedis, String keyPreFix, String id, Class<T> c) throws NumberFormatException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {
		// String getIDMethodName = "set" + id.substring(0, 1).toUpperCase() +
		// id.substring(1);
		// 取出所有的set方法
		List<T> list = new ArrayList<T>();
		Set<Method> methodGet = getSetMethod(c);
		Set<String> keys = jedis.keys(keyPreFix);// 查出所有的键
		Iterator<String> its = keys.iterator();
		T t = null;
		while (its.hasNext()) {
			String key = its.next();// allfood:1 allfood:2
			Map<String, String> map = jedis.hgetAll(key);
			t = parseMaptoT(map, c);
			list.add(t);
		}
		return list;
	}

	protected T parseMaptoT(Map<String, String> map, Class<T> c) {
		Set<Method> setmethod = getSetMethod(c);
		T t = null;
		try {
			t = c.newInstance();// 创建反射类的实例化对象
			for (Method method : setmethod) {
				Set<String> keys = map.keySet();
				for (String key : keys) {
					String methodName = "set" + key.substring(0, 1).toUpperCase() + key.substring(1);
					if (method.getName().equals(methodName)) {
						// javabean规范 ：set方法中只有一个参数 get方法中不能有参数 要有无参构造函数
						String typename = method.getParameterTypes()[0].getName();
						String value = map.get(key);
						if (value != null && !"".equals(value)) {
							if ("java.lang.Integer".equals(typename) || "int".equals(typename)) {
								method.invoke(t, Integer.parseInt(value));
							} else if ("java.lang.Double".equals(typename) || "double".equals(typename)) {
								method.invoke(t, Double.parseDouble(value));
							} else if ("java.lang.Float".equals(typename) || "float".equals(typename)) {
								method.invoke(t, Float.parseFloat(value));
							} else if ("java.lang.Long".equals(typename) || "long".equals(typename)) {
								method.invoke(t, Long.parseLong(value));
							} else {
								method.invoke(t, value);

							}

						}
						break;
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return t;
	}

	private Set<Method> getSetMethod(Class c) {
		Method[] ms = c.getMethods();
		Set<Method> result = new HashSet<Method>();
		for (Method m : ms) {
			if (m.getName().startsWith("set")) {
				result.add(m);
			}
		}
		return result;
	}

	private Set<Method> getMethod(Class<T> c) {
		Method[] ms = c.getMethods();
		Set<Method> result = new HashSet<Method>();
		for (Method m : ms) {
			if (m.getName().startsWith("get")) {
				result.add(m);
			}
		}
		return result;
	}

}
