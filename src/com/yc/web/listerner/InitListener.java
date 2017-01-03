package com.yc.web.listerner;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.yc.bean.Resfood;
import com.yc.biz.ResfoodBiz;
import com.yc.biz.impl.ResfoodBizImpl;
 
public class InitListener implements ServletContextListener {

    
    public InitListener() {
        // TODO Auto-generated constructor stub
    }
    
    /*
     * 监听器application的创建
     */
	 
    public void contextInitialized(ServletContextEvent event)  { 
  
    	ResfoodBiz rBiz=new ResfoodBizImpl(); 
    	try {
			List<Resfood>list=rBiz.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
      	//利用application存储数据的缺点：
    	//1.内存太小   无法  2.当服务器关闭的时候数据就没了
//    	ServletContext application=event.getServletContext();
//    	application.setAttribute("list", list);
//    	jvm的虚拟内存
    }

	 
    public void contextDestroyed(ServletContextEvent arg0)  { 
         // TODO Auto-generated method stub
    }
	
}
