package com.kh.skillup.course.model.dao;

import static com.kh.skillup.common.JDBCTemplate.*;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.kh.skillup.course.model.vo.Attachment;
import com.kh.skillup.course.model.vo.Course;
import com.kh.skillup.course.model.vo.PageInfo;
import com.kh.skillup.lesson.model.vo.Lesson;
import com.kh.skillup.pay.model.vo.Pay;
public class CourseDAO {

	
	private Statement stmt = null;
	private PreparedStatement pstmt = null;
	private ResultSet rset = null;
	
	private Properties prop = null;
	
	public CourseDAO(){
		String fileName = CourseDAO.class.getResource("/com/kh/skillup/sql/course/course.xml").getPath();
		
		try {
			prop = new Properties();
			prop.loadFromXML(new FileInputStream(fileName)); 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 전체 수업 목록 반환
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	public int getListCount(Connection conn) throws Exception{
		int listCount = 0;
		
		String query = prop.getProperty("getListCount");
		
		
		try {
			stmt = conn.createStatement();
			rset = stmt.executeQuery(query);
		
		if(rset.next()) {
			listCount = rset.getInt(1);
		}
		
		} finally {
			close(rset);

			close(stmt);
		}
		
		return listCount;
	}




	public List<Course> selectStudentList(Connection conn) throws Exception{
		List<Course> list = null;
		String query = prop.getProperty("selectStudentList");

		try {
			stmt = conn.createStatement();
			rset = stmt.executeQuery(query);

			list = new ArrayList<Course>();
			//SELECT LESSON_NO,LESSON_TITLE,LESSON_CONTENT,PLACE,PRICE,MAX_NUM,LESSON_DT
			//,LESSON_CNT, LESSON_TIME,LESSON_TYPE,MEMBER_NO,PAYMENT_CK
		
			while (rset.next()) {
				Course couse = new Course();
				couse.setLessonNo(rset.getInt("LESSON_NO"));
				couse.setLessonTitle(rset.getString("LESSON_TITLE"));
				couse.setLessonContent(rset.getString("LESSON_CONTENT"));
				couse.setPlace(rset.getString("PLACE"));
				couse.setPrice(rset.getInt("PRICE"));
				couse.setMaxNum(rset.getInt("MAX_NUM"));
				couse.setLessonDate(rset.getDate("LESSON_DT"));
				couse.setLessonCnt(rset.getInt("LESSON_CNT"));
				couse.setLessonTime(rset.getInt("LESSON_TIME"));
				couse.setLessonType(rset.getString("LESSON_TYPE").charAt(0));
				couse.setMemberNo(rset.getInt("MEMBER_NO"));
				couse.setPaymentCk(rset.getString("PAYMENT_CK"));
			
				
				list.add(couse);
			}

		} finally {
			close(rset);
			close(stmt);
		}
		return list;
	}


	public Course selectMyClass(Connection conn, int orderNo2) throws Exception {
		
		Course couse = null;
		String query = prop.getProperty("selectMyClass");
		
		try {
			pstmt= conn.prepareStatement(query);
			
			pstmt.setInt(1, orderNo2);
			
			rset= pstmt.executeQuery();
			
			
			if(rset.next()) {
				 couse = new Course();
			
				couse.setLessonTitle(rset.getString("LESSON_TITLE"));
				couse.setLessonContent(rset.getString("LESSON_CONTENT"));
				couse.setPlace(rset.getString("PLACE"));
				couse.setPrice(rset.getInt("PRICE"));
				couse.setMaxNum(rset.getInt("MAX_NUM"));
				couse.setLessonDate(rset.getDate("LESSON_DT"));
				couse.setLessonCnt(rset.getInt("LESSON_CNT"));
				couse.setLessonTime(rset.getInt("LESSON_TIME"));
				couse.setLessonType(rset.getString("LESSON_TYPE").charAt(0));
				couse.setMemberNo(rset.getInt("MEMBER_NO"));
				couse.setPaymentCk(rset.getString("PAYMENT_CK"));
			
			}
			
		} finally {
			close(rset);
			close(pstmt);
		}
		return couse;
	}
	}

	
	
	
	

