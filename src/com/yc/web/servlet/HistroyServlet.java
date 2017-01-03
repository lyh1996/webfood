package com.yc.web.servlet;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yc.biz.impl.HistroyBizImpl;
import com.yc.biz.impl.MonthCountImpl;
import com.yc.utils.YcConstant;
import com.yc.web.model.JsonModel;

import redis.clients.jedis.Jedis;

public class HistroyServlet extends BasicServlet {
	private static final long serialVersionUID = 1L;
	private Jedis jedis = new Jedis(YcConstant.REDIS_URL, YcConstant.REDIS_ID);
	private String histroydata = null;
	private String fid;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if ("monthCount".equals(op)) {
			// MonthCountImpl monthCountImpl=new MonthCountImpl();
			fid = request.getParameter("fid");
			monthCount(request, response);
		} else if ("histroy".equals(op)) {
			JsonModel jModel = new JsonModel();
			histroydata = request.getParameter("data");
			String userid = request.getParameter("userid");
			Set<String> list = null;
			try {
				if (histroydata == null) {
					list = HistroyBizImpl.getTopNHistroy(jedis, userid, 10);
				} else {
					HistroyBizImpl.saveHistroyInfo(jedis, userid, histroydata);
					list = HistroyBizImpl.getTopNHistroy(jedis, userid, 10);
				}
				// code:1 obj存储数据
				// code :0 msg:错误的信息存储数据
				jModel.setCode(1);
				jModel.setObj(list);

			} catch (Exception e) {
				e.printStackTrace();
				jModel.setCode(0);
				jModel.setErrorMsg(e.getMessage());

			}
			super.outjson(jModel, response);
		}

	}

	private void monthCount(HttpServletRequest request, HttpServletResponse response) throws IOException {
		JsonModel jModel = new JsonModel();
		MonthCountImpl monthCountImpl = new MonthCountImpl();
		List<String> list = monthCountImpl.getMonthCount(fid);
		jModel.setCode(1);
		jModel.setObj(list);
		super.outjson(jModel, response);
	}

}
