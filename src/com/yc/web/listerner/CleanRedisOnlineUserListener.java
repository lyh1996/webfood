package com.yc.web.listerner;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import com.yc.utils.YcConstant;
import com.yc.utils.redisutil.fn1.userlogin.UserRedis;
import redis.clients.jedis.Jedis;
 
 
public class CleanRedisOnlineUserListener implements ServletContextListener {
	private Jedis jedis=new Jedis(YcConstant.REDIS_URL);
     private Timer timer;
     
    public void contextInitialized(ServletContextEvent arg0)  { 
          //启动定时器
    	//System.out.println("启动定时器");
    	//如果这个时间想要设成是可以更改的，则可以使用
    	//ServletContext application =new ServletContext() { 在这里获取application中的时间来设置时间};
    	  timer=new Timer();
    	Calendar calendar=Calendar.getInstance();
    	calendar.set(Calendar.HOUR, 2);
    	calendar.set(Calendar.MINUTE, 0);
    	
    	//
    	timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				UserRedis.keepNDaysRecord(jedis, YcConstant.KEEPNDAYSFORONLINEUSER);
				
			}
		}, 24*60*60*1000);
    }

	 
    public void contextDestroyed(ServletContextEvent arg0)  { 
    	//销毁定时器
    	//    	System.out.println("销毁定时器");
    	timer.cancel();
    }
	
}
