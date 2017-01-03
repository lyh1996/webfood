package com.yc.web.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.yc.bean.Resfood;
import com.yc.bean.Resorder;
import com.yc.bean.Resuser;
import com.yc.biz.ResfoodBiz;
import com.yc.biz.ResorderBiz;
import com.yc.biz.UserRankBiz;
import com.yc.biz.impl.ResfoodBizImpl;
import com.yc.biz.impl.ResorderBizImpl;
import com.yc.biz.impl.UserRankBizImpl;
import com.yc.utils.YcConstant;
import com.yc.web.model.JsonModel;

public class ResorderServlet extends BasicServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if ("order".equals(op)) {
			orderOP(request, response);
		} else if ("delall".equals(op)) {
			delall(request, response);
		} else if ("delorder".equals(op)) {
			delorder(request, response);
		} else if ("findcartop".equals(op)) {
			findcartop(request, response);

		} else if ("comfirmorder".equals(op)) {
			comfirmorderOP(request, response);
		}
	}
	/**
	 * 确认收货地址
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	private void comfirmorderOP(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session =request.getSession();
		JsonModel jModel=new JsonModel();
		if(session.getAttribute(YcConstant.LOGIN_USER)==null){
			jModel.setCode(0);
			jModel.setErrorMsg("you havenot logined in ....");
			super.outjson(jModel, response);
			return ;
		} 
		Map<Integer, Resfood> cart=null;
		if (session.getAttribute(YcConstant.CART_NAME) == null) {
			jModel.setCode(0);
			jModel.setErrorMsg("you havenot order any goods ... ....");
			super.outjson(jModel, response);
			return ; 
		}
		cart = (Map<Integer, Resfood>) session.getAttribute(YcConstant.CART_NAME);
		Resuser resuser=(Resuser) session.getAttribute(YcConstant.LOGIN_USER);
		Resorder resorder =(Resorder) super.parseRequest(request, Resorder.class);
		resorder.setStatus(0);
		resorder.setUserid(resuser.getUserid());
		ResorderBiz rb= new ResorderBizImpl();
		try {
			rb.submitOrder(resorder, cart);
			//下单成功，计算总的价格
			double total=0;
			for(Map.Entry<Integer, Resfood> entry:cart.entrySet()){
				total+=entry.getValue().getRealprice()*entry.getValue().getNum();
			}
			UserRankBiz urBiz=new UserRankBizImpl();
			urBiz.updateScore(total, resuser.getUserid());
			
			session.removeAttribute(YcConstant.CART_NAME);
			jModel.setCode(1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		super.outjson(jModel, response);
	}

	public  void findcartop(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		Map<Integer, Resfood> cart = null;
		if (session.getAttribute(YcConstant.CART_NAME) != null) {
			cart = (Map<Integer, Resfood>) session.getAttribute(YcConstant.CART_NAME);
		}
		JsonModel jModel = new JsonModel();
		if (cart == null) {
			jModel.setCode(0);
		} else {
			jModel.setCode(1);
			jModel.setObj(cart);
		}
		super.outjson(jModel, response);
	}

	private void delorder(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		Resfood resfood = (Resfood) super.parseRequest(request, Resfood.class);// 取出fid
		HttpSession session = request.getSession();
		Map<Integer, Resfood> cart = new HashMap<Integer, Resfood>();
		if (session.getAttribute(YcConstant.CART_NAME) != null) {
			cart = (Map<Integer, Resfood>) session.getAttribute(YcConstant.CART_NAME);
		}
		if (cart.containsKey(resfood.getFid())) {
			cart.remove(resfood.getFid());
		}
		session.setAttribute(YcConstant.CART_NAME, cart);
		JsonModel jModel = new JsonModel();
		jModel.setCode(1);
		jModel.setObj(cart);
		super.outjson(jModel, response);

	}

	private void delall(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		session.removeAttribute(YcConstant.CART_NAME);
		JsonModel jModel = new JsonModel();
		jModel.setCode(1);

		super.outjson(jModel, response);
	}

	private void orderOP(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Resfood resfood = (Resfood) super.parseRequest(request, Resfood.class);// 取出fid
		// num在resfood中增加一个属性，由上面的parseRequest来解决参数的问题
		// 2.使用原生request.getparameter("num");
		HttpSession session = request.getSession();
		// 判断session中map是否已经存在
		Map<Integer, Resfood> cart = new HashMap<Integer, Resfood>();
		if (session.getAttribute(YcConstant.CART_NAME) != null) {
			cart = (Map<Integer, Resfood>) session.getAttribute(YcConstant.CART_NAME);
		}
		JsonModel jModel = new JsonModel();

		try {
			// 不存在，说明这是一个用户的第一次下单
			// 创建一个map，作为购物侧放到session中
			// 如果存在的话说明用户已经买过东西了。从session中取出map
			// 处理数量
			if (cart.containsKey(resfood.getFid())) {
				//
				// 如果存在则数量增加
				Resfood resfood2 = cart.get(resfood.getFid());
				if (resfood.getNum() + resfood2.getNum() <= 0) {
					cart.remove(resfood.getFid());
				} else {
					resfood2.setNum(resfood.getNum() + resfood2.getNum());// 更新数量
					cart.put(resfood.getFid(), resfood2);
				}

			} else {
				// 如果不存在的话，则数量为1
				ResfoodBiz rb = new ResfoodBizImpl();
				Resfood resfood2 = rb.getResfoodByFid(resfood.getFid());
				// 数量
				resfood2.setNum(resfood.getNum());
				cart.put(resfood.getFid(), resfood2);
			}
			session.setAttribute(YcConstant.CART_NAME, cart);
			 
			jModel.setCode(1);
			jModel.setObj(cart);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			jModel.setCode(0);
			jModel.setErrorMsg(e.toString());
		}
		super.outjson(jModel, response);
	}
}
