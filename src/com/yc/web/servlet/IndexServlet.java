package com.yc.web.servlet;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.yc.bean.Favorable;
import com.yc.bean.Resfood;
import com.yc.bean.Resuser;
import com.yc.biz.FavorableBiz;
import com.yc.biz.ResfoodBiz;
import com.yc.biz.impl.FavorableBizImpl;
import com.yc.biz.impl.ResfoodBizImpl;
import com.yc.utils.YcConstant;
import com.yc.web.model.JsonModel;

public class IndexServlet extends BasicServlet  {
	private static final long serialVersionUID = 1L;
	private ResfoodBiz resfoodBiz=new ResfoodBizImpl();
	private FavorableBiz favorableBiz=new FavorableBizImpl();
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
	 
		 
		JsonModel jModel=new JsonModel();
		try{
			//查找优惠条件
			List<Favorable> list1=favorableBiz.findAll();
			 
			//code:1 obj存储数据
			//code :0 msg:错误的信息存储数据
			jModel.setCode4(1);
			jModel.setObj4(list1);
			//查找所有的商品
		List<Resfood> list=(List<Resfood>) resfoodBiz.findAll();
		 
		//code:1 obj存储数据
		//code :0 msg:错误的信息存储数据
		jModel.setCode(1);
		jModel.setObj(list);
		//用户登入
		 HttpSession session =request.getSession();
		 if(session.getAttribute(YcConstant.LOGIN_USER)!=null){
			 Resuser resuser=(Resuser) session.getAttribute(YcConstant.LOGIN_USER);
			 jModel.setCode3(1);
			 resuser.setPwd(null);
			 jModel.setObj3(resuser);
		 }else{
			 jModel.setCode3(0);
		 }
		 //检测购物车
		 
			Map<Integer, Resfood> cart = null;
			if (session.getAttribute(YcConstant.CART_NAME) != null) {
				cart = (Map<Integer, Resfood>) session.getAttribute(YcConstant.CART_NAME);
			}
			 
			if (cart == null) {
				jModel.setCode2(0);
			} else {
				jModel.setCode2(1);
				jModel.setObj2(cart);
			}
		 
		}catch(Exception e){
			e.printStackTrace();
			jModel.setCode(0);
			jModel.setErrorMsg(e.getMessage());
			
		}
		super.outjson(jModel, response);
		
	}
	 
}
