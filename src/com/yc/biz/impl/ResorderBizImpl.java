package com.yc.biz.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Map.Entry;

import javax.sound.midi.Patch;

import com.yc.bean.Resfood;
import com.yc.bean.Resorder;
import com.yc.biz.ResorderBiz;
import com.yc.dao.DBHelper;

public class ResorderBizImpl implements ResorderBiz{
	private DBHelper db=new DBHelper();

	public void submitOrder(Resorder order, Map<Integer, Resfood> cart) throws Exception {
		//三种语句
		Connection con=db.getConn();
		ResultSet rs=null;
		PreparedStatement pstmt=null;
		try{
		con.setAutoCommit(false);
		//1.插入resorder
		String sql="insert into resorder(userid,address,tel,ordertime,deliverytime,ps,status) values(?,?,?,now(),?,?,?)";
		pstmt=con.prepareStatement(sql);
		pstmt.setString(1,   order.getUserid()+"");
		pstmt.setString(2,   order.getAddress()+"");
		pstmt.setString(3,   order.getTel()+"");
		
		pstmt.setString(4,   order.getDeliveryTimeLong()+"");
		
		pstmt.setString(5,   order.getPs()+"");
		pstmt.setString(6,   order.getStatus()+"");
		pstmt.executeUpdate();
		//2.查resorder表中最新的roid 订单编号
		sql="select max(roid) from resorder";
		pstmt=con.prepareStatement(sql);
		rs=pstmt.executeQuery();
		String roid=null;
		if(rs.next()){
			roid=rs.getString(1);
		}else{
			throw new Exception("db server error....");
		}
		//3.插入resorderitem
		for(Map.Entry<Integer, Resfood> entry:cart.entrySet()){
			sql="insert into resorderitem(roid,fid,dealprice,num) values(?,?,?,?)";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, roid);
			pstmt.setString(2, entry.getKey()+"");
			pstmt.setString(3, entry.getValue().getRealprice().toString());
			pstmt.setString(4, entry.getValue().getNum().toString());
			pstmt.executeUpdate();
		}
		con.commit();
		}catch(Exception e){
			e.printStackTrace();
			try{
				con.rollback();
			}catch(SQLException e1){
				e1.printStackTrace();
				
			}
			throw e;
		}finally{
			try{
				con.setAutoCommit(true);
			}catch(SQLException e){
				e.printStackTrace();
			}
			if(rs!=null){
				try{
					rs.close();
				}catch(SQLException e){
					e.printStackTrace();
				}
			}
			if(pstmt!=null){
				try{
					pstmt.close();
				}catch(SQLException e){
					e.printStackTrace();
				}
			}
			if(con!=null){
				try{
					con.close();
				}catch(SQLException e){
					e.printStackTrace();
				}
			}
		}
	}

}
