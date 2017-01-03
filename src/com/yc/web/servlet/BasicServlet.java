package com.yc.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

public abstract class BasicServlet <T> extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5656614265959966425L;
	protected String op;
	protected String num;
	
	protected void outjson (Object obj,HttpServletResponse response) throws IOException{
		//以json的格式返回给客户端
		Gson gson=new Gson();
		String jsonString=gson.toJson(obj);
		//以流的方式写出客户端
		//取流response.getWriter();
		//设定回传函数的数据类型
		response.setContentType("text/json;charset=utf-8");
		PrintWriter out=response.getWriter();
		out.println(jsonString);
		out.close();
	}

	@Override
	protected void service(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
		arg0.setCharacterEncoding("utf-8");
		op=arg0.getParameter("op");
		num=arg0.getParameter("num"); 
		super.service(arg0, arg1);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * 受保护的方法:子类可以继承这个方法，作用是解析request，取出中间所有的非空的参数，创建一个将参数注入到这个bean中的
	 * request中的数组类型的参数不管
	 */

	 
		protected T parseRequest(HttpServletRequest request,Class<T> c){
			//1.从request 中取值
			Map<String ,String[]> map=request.getParameterMap();
			Set<String> methodNames=getMethodName(map.keySet());//setPname,setPrice setIns
			Method[] ms=c.getMethods();
			T t=null;
			try {
				t= c.newInstance(); //创建反射类的实例化对象 Product
				//循环所有的方法，查到与 method Names中相同的方法
				for( Method method:ms){
					for(String mn:methodNames){
						if(method.getName().equals(mn)){
							//激活这个method      invoke
							String keyname=mn.substring(3,4).toLowerCase()+mn.substring(4);
							//TODO : 类型 问题
							//System.out.println(method.getParameterTypes()[0]);
							//Class      method.getParameterTypes()[0]
							String typeName=method.getParameterTypes()[0].getName();
							String[] value=map.get(keyname);
							if("java.lang.Integer".equals(typeName) || "int".equals(typeName)){
								method.invoke(t, Integer.parseInt(value[0]));
							}else if("java.lang.Double".equals(typeName) || "double".equals(typeName)){
								method.invoke(t, Double.parseDouble(value[0]));
							}else if("java.lang.Long".equals(typeName) || "long".equals(typeName)){
								method.invoke(t, Long.parseLong(value[0]));
							}else {
								method.invoke(t,value[0]);
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

		private Set<String> getMethodName(Set<String> keys){
			Set<String> result=new HashSet<String>();
			for(String key:keys){
				String newName=key.substring(0,1).toUpperCase()+key.substring(1);
				result.add("set"+newName);
			}
			return result;
		}

}
