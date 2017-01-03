package com.yc.web.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ibm.media.codec.video.h263.YCbCrToRGB;
import com.yc.bean.Resfood;
import com.yc.bean.Resuser;
import com.yc.biz.ResuserBiz;
import com.yc.biz.impl.ResuserBizImpl;
import com.yc.utils.YcConstant;
import com.yc.web.model.JsonModel;

public class ResuserServlet extends BasicServlet {
	private static final long serialVersionUID = 1L;
	private ResuserBiz rb = new ResuserBizImpl();

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			if ("".equals(op)) {
				request.getRequestDispatcher("index.jsp").forward(request, response);
			} else if ("login".equals(op)) {
				loginop(request, response);
			}else if("logout".equals(op)){
				logoutOp(request,response);
			}
			else if("islogin".equals(op)){
				isloginOp(request,response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				throw e;
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}

	 
	public  void isloginOp(HttpServletRequest request, HttpServletResponse response) throws IOException {
		 HttpSession session =request.getSession();
		 JsonModel jModel=new JsonModel();
		 if(session.getAttribute(YcConstant.LOGIN_USER)!=null){
			 Resuser resuser=(Resuser) session.getAttribute(YcConstant.LOGIN_USER);
			 jModel.setCode(1);
			 resuser.setPwd(null);
			 jModel.setObj(resuser);
		 }else{
			 jModel.setCode(0);
		 }
		 super.outjson(jModel, response);
		
	}


	private void logoutOp(HttpServletRequest request, HttpServletResponse response) throws IOException {
		//退出时，将session中的登入信息删除
		
		HttpSession session =request.getSession();
		session .removeAttribute(YcConstant.LOGIN_USER);
		//购物车不用删除，因为session是随着会话的结束而结束的
		JsonModel jModel=new JsonModel();
		jModel.setCode(1);
		super.outjson(jModel, response);
	}


	@SuppressWarnings("null")
	private void loginop(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Resuser resuser = (Resuser) super.parseRequest(request, Resuser.class);// 取出fid
		JsonModel jModel = new JsonModel();

		if (resuser.getUsername() == null || "".equals(resuser.getUsername())) {
			jModel.setCode(0);
			jModel.setErrorMsg("username should not be empty");
			super.outjson(jModel, response);
			return;
		}
		if (resuser.getPwd() == null || "".equals(resuser.getPwd())) {
			jModel.setCode(0);
			jModel.setErrorMsg("password should not be empty");
			super.outjson(jModel, response);
			return;
		}
		HttpSession session = request.getSession();
		String validateCode = (String) session.getAttribute("validateCode");
		if (!validateCode.toUpperCase().equals(resuser.getValcode().toUpperCase())) {
			jModel.setCode(0);
			jModel.setErrorMsg("valide code is not right");
			super.outjson(jModel, response);
			return;
		}
		try {
			resuser = rb.login(resuser);
		// System.out.println(resuser);
			if (resuser != null) {
				 session.setAttribute(YcConstant.LOGIN_USER, resuser);
				jModel.setCode(1);
				resuser.setPwd(null);
				jModel.setObj(resuser);
				super.outjson(jModel, response);
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		 
		jModel.setCode(0);
		jModel.setErrorMsg("error user");
		super.outjson(jModel, response);
	}

}
