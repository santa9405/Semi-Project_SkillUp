package com.kh.skillup.pay.model.dao;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import static com.kh.skillup.common.JDBCTemplate.*;
import com.kh.skillup.course.model.dao.CourseDAO;
import com.kh.skillup.lesson.model.vo.Lesson;
import com.kh.skillup.pay.model.vo.Pay;

public class PayDAO {
	private Statement stmt = null;
	private PreparedStatement pstmt = null;
	private ResultSet rset = null;

	private Properties prop = null;

	public PayDAO() {
		String fileName = CourseDAO.class.getResource("/com/kh/skillup/sql/pay/pay-query.xml").getPath();

		try {
			prop = new Properties();
			prop.loadFromXML(new FileInputStream(fileName));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<Pay> selectList(Connection conn) throws Exception {

		List<Pay> list = null;
		String query = prop.getProperty("slelctList");

		try {
			stmt = conn.createStatement();
			rset = stmt.executeQuery(query);

			list = new ArrayList<Pay>();
			// SELECT PAYMENT_NO,PAYMENT_DT,LESSON_NO,CTGR_CD,MEMBER_NO,PAYMENT_CK
			while (rset.next()) {
				Pay pay = new Pay();
				pay.setPaymentNo(rset.getInt("PAYMENT_NO"));
				pay.setPaymentDt(rset.getDate("PAYMENT_DT"));
				pay.setLessonNo(rset.getInt("LESSON_NO"));
				pay.setCtgrCd(rset.getInt("CTGR_CD"));
				pay.setMemberNo(rset.getInt("MEMBER_NO"));
				pay.setPaymentCk(rset.getString("PAYMENT_CK"));

				list.add(pay);
			}

		} finally {
			close(rset);
			close(stmt);
		}
		return list;
	}

	public List<Lesson> slelctClassList(Connection conn) throws Exception {
		List<Lesson> list = null;
		String query = prop.getProperty("slelctClassList");

		try {
			stmt = conn.createStatement();
			rset = stmt.executeQuery(query);

			list = new ArrayList<Lesson>();
			// PAYMENT_NO,LESSON_TITLE,PAYMENT_DT,MEMBER_NO
			while (rset.next()) {
				Lesson lesson = new Lesson();
				lesson.setLessonNo(rset.getInt("LESSON_NO"));
				lesson.setLessonTitle(rset.getString("LESSON_TITLE"));
				lesson.setLessonContent(rset.getString("LESSON_CONTENT"));
				lesson.setPlace(rset.getString("PLACE"));
				lesson.setPrice(rset.getInt("PRICE"));
				lesson.setMaxNum(rset.getInt("MAX_NUM"));
				lesson.setLessonDate(rset.getDate("LESSON_DT"));
				lesson.setLessonCnt(rset.getInt("LESSON_CNT"));
				lesson.setLessonTime(rset.getInt("LESSON_TIME"));
				lesson.setLessonType(rset.getString("LESSON_TYPE").charAt(0));
				lesson.setMemberName(rset.getString("MEMBER_NAME"));

				list.add(lesson);
			}

		} finally {
			close(rset);
			close(stmt);
		}
		return list;
	}

	public Lesson selectOrder(Connection conn, int orderNo) throws Exception {

		Lesson lesson = null;
		String query = prop.getProperty("selectOrder");

		try {
			pstmt = conn.prepareStatement(query);

			pstmt.setInt(1, orderNo);

			rset = pstmt.executeQuery();

			if (rset.next()) {
				lesson = new Lesson();
				lesson.setLessonTitle(rset.getString("LESSON_TITLE"));
				lesson.setLessonContent(rset.getString("LESSON_CONTENT"));
				lesson.setPrice(rset.getInt("PRICE"));
				lesson.setMemberName(rset.getString("MEMBER_NAME"));
			}

		} finally {
			close(rset);
			close(pstmt);
		}
		return lesson;
	}

	public Pay CheckOrder(Connection conn, int checkOrder) throws Exception {
		Pay check = null;
		String query = prop.getProperty("CheckOrder");

		try {
			pstmt = conn.prepareStatement(query);

			pstmt.setInt(1, checkOrder);

			rset = pstmt.executeQuery();

			if (rset.next()) {
				check = new Pay();

				check.setPaymentNo(rset.getInt("PAYMENT_NO"));
				check.setPaymentDt(rset.getDate("PAYMENT_DT"));
				check.setLessonNo(rset.getInt("LESSON_NO"));
				check.setCtgrCd(rset.getInt("CTGR_CD"));
				check.setMemberNo(rset.getInt("MEMBER_NO"));
				check.setPaymentCk(rset.getString("PAYMENT_CK"));
				
			}

		} finally {
			close(rset);
			close(pstmt);
		}
		return check;
	}

	public int updatePayFl(Connection conn, int payNo) throws Exception {

		int result = 0;

		String query = prop.getProperty("updatepayfl");

		try {
			pstmt = conn.prepareStatement(query);

			pstmt.setInt(1, payNo);
			
			result=pstmt.executeUpdate();
		} finally {
			
			close(pstmt);
		}
		
		return result;
	}

}
