package com.kh.skillup.course.model.service;

import static com.kh.skillup.common.JDBCTemplate.*;

import java.io.File;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

import com.kh.skillup.course.model.dao.CourseDAO;
import com.kh.skillup.course.model.vo.Attachment;
import com.kh.skillup.course.model.vo.Course;
import com.kh.skillup.course.model.vo.PageInfo;
import com.kh.skillup.lesson.model.exception.FileInsertFailedException;
import com.kh.skillup.lesson.model.vo.Lesson;
import com.kh.skillup.pay.model.vo.Pay;

public class CourseService {

	private CourseDAO dao = new CourseDAO();

	
	/**
	 * 페이징처리
	 * @param cp
	 * @return
	 * @throws Exception
	 */
	public PageInfo getPageInfo(String cp) throws Exception{
		
		Connection conn = getConnetion();
		
		int currentPage = cp == null ? 1 : Integer.parseInt(cp);
		
		int listCount = dao.getListCount(conn);
		close(conn);
		
		
		return new PageInfo(currentPage,listCount);
	}
	
	



	public List<Course> selectStudentList() throws Exception {
		Connection conn = getConnetion();

		List<Course> list = dao.selectStudentList(conn);

		close(conn);
		return list;
	}





	public Course selectMyClass(int orderNo2) throws Exception {
	Connection conn = getConnetion();
		
	Course course = dao.selectMyClass(conn,orderNo2);
	System.out.println(orderNo2);
	
	
		close(conn);
		
		return course;
	}

}
