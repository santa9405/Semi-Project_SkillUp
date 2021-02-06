package com.kh.skillup.pay.model.service;

import java.sql.Connection;
import java.util.List;

import com.kh.skillup.lesson.model.vo.Lesson;
import com.kh.skillup.pay.model.dao.PayDAO;
import com.kh.skillup.pay.model.vo.Pay;

import static com.kh.skillup.common.JDBCTemplate.*;

public class PayService {
	PayDAO dao = new PayDAO();

	public List<Pay> selectList() throws Exception {
		// TODO Auto-generated method stub
		Connection conn = getConnetion();

		List<Pay> list = dao.selectList(conn);

		close(conn);
		return list;
	}

	public List<Lesson> selectClassList() throws Exception {
		Connection conn = getConnetion();

		List<Lesson> list = dao.slelctClassList(conn);

		close(conn);
		return list;
	}

	public Lesson selectOrder(int orderNo) throws Exception {
		Connection conn = getConnetion();

		Lesson lesson = dao.selectOrder(conn, orderNo);

		close(conn);

		return lesson;
	}

	public Pay CheckOrder(int checkOrder) throws Exception {
		Connection conn = getConnetion();

		Pay check = dao.CheckOrder(conn, checkOrder);

		close(conn);
		return check;
	}

	public int updatePayFl(int payNo) throws Exception {

		Connection conn = getConnetion();
		
		int result = dao.updatePayFl(conn,payNo);
		
		if(result> 0) {
			commit(conn);
		}
		else {
			
			rollback(conn);
		}
		
		
		close(conn);

		return result;
	}

}
