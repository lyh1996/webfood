package com.yc.biz;

public interface UserRankBiz {
	
	/**
	 * 
	 * @param total：用户的总金额来计算积分
	 * @param userid：用户的信息
	 */
	public void updateScore(double total,int userid);
}
