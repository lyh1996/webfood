package com.yc.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yc.bean.Resfood;
import com.yc.biz.ResfoodBiz;
import com.yc.biz.impl.ResfoodBizImpl;
import com.yc.web.model.JsonModel;

import jmapps.util.JMAppsCfg;

public class ResfoodServlet extends BasicServlet{

	
	private static final long serialVersionUID = 1L;
	
	private ResfoodBiz resfoodBiz=new ResfoodBizImpl();
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			 {
		try {
		if("".equals(op)){
			request.getRequestDispatcher("index.jsp").forward(request, response);
		}else if("findAll".equals(op)){
				findAll(request,response);
		} 
			} catch (Exception e) {
				e.printStackTrace();
				try {
					throw e;
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	
	 
	public void findAll(HttpServletRequest request, HttpServletResponse response) throws Exception {
		JsonModel jModel=new JsonModel();
		try{
		List<Resfood> list=(List<Resfood>) resfoodBiz.findAll();
		 
		//code:1 obj存储数据
		//code :0 msg:错误的信息存储数据
		jModel.setCode(1);
		jModel.setObj(list);
		 
		}catch(Exception e){
			e.printStackTrace();
			jModel.setCode(0);
			jModel.setErrorMsg(e.getMessage());
			
		}
		super.outjson(jModel, response);
	}

}
