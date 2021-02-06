package com.kh.skillup.course.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.skillup.common.MyFileRenamePolicy;
import com.kh.skillup.course.model.service.CourseService;
import com.kh.skillup.course.model.vo.Attachment;
import com.kh.skillup.course.model.vo.Course;
import com.kh.skillup.course.model.vo.PageInfo;
import com.kh.skillup.member.model.vo.Member;
import com.kh.skillup.pay.model.vo.Pay;
import com.oreilly.servlet.MultipartRequest;

@WebServlet("/course/*")
public class CourseController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String uri = request.getRequestURI(); // 전체 요청 주소

		String contextPath = request.getContextPath();

		String command = uri.substring((contextPath + "/course").length());

		String path = null; // forward 또는 redirect경로를 저장할 변수
		RequestDispatcher view = null; // 요청 위임 객체

		String swalIcon = null;
		String swalTitle = null;
		String swalText = null;

		String errorMsg = null; // 에러 메세지 전달용 변수

		try {
			CourseService service = new CourseService();
			String cp = request.getParameter("cp");

			if (command.equals("/classlist.do")) {
				errorMsg = "강사 목록 조회 중 오류 발생.";

				// 페이징 처리

				PageInfo pInfo = service.getPageInfo(cp);
				
				//				System.out.println(pInfo);

				//List<Course> bList = service.selectLessonList(pInfo);

				//for(Course c : bList) {
				//	System.out.println(c);
				//}
				//if (bList != null) {
					// 썸네일 이미지 목록 조회 서비스 호출
				//	List<Attachment> fList = service.selectThumbnailList(pInfo);

				//	if (!fList.isEmpty()) {
				//		request.setAttribute("fList", fList);
				//	}
				//}
				// 요청을 위임할 jsp 경로 지정

				//request.setAttribute("bList", bList);
				//request.setAttribute("pInfo", pInfo);

				path = "/WEB-INF/views/course/instructorList.jsp";
				view = request.getRequestDispatcher(path);
				view.forward(request, response);
			}
			
			else if (command.equals("/studentlist.do")) {
				errorMsg = "수강목록 조회중 오류발생.";
				List<Course> list = service.selectStudentList();

				// 요청을 위임할 jsp 경로 지정
				path = "/WEB-INF/views/course/studentCourseList.jsp";
				request.setAttribute("list", list);
				// 요청 위임 객체 생성 후 위임 진행
				view = request.getRequestDispatcher(path);
				view.forward(request, response);
			} 

			

			else if (command.equals("/instructorclass.do")) {
				errorMsg = "수업 상세 조회중 오류 발생.";

				int orderNo2 = Integer.parseInt(request.getParameter("no"));
				
				Course cou = service.selectMyClass(orderNo2);
				

				if (cou != null) {
					path = "/WEB-INF/views/course/instructorClass.jsp";
					request.setAttribute("course", cou);

					view = request.getRequestDispatcher(path);
					view.forward(request, response);

				} else {
					HttpSession session = request.getSession();
					session.setAttribute("swalIon", "error");
					session.setAttribute("swalTitle", "수업 상세 조회중 오류 발생");
					response.sendRedirect(request.getHeader("referer"));
				}

				
			}
			
			
			else if (command.equals("/studentbeforecourselist.do")) {
				errorMsg = "공지사항 목록 조회 중 오류 발생.";

				// 요청을 위임할 jsp 경로 지정
				path = "/WEB-INF/views/course/studentBeforeCourseList.jsp";

				// 요청 위임 객체 생성 후 위임 진행
				view = request.getRequestDispatcher(path);
				view.forward(request, response);
			}

		



		} catch (Exception e) {
			e.printStackTrace();
			path = "/WEB-INF/views/common/errorPage.jsp";
			request.setAttribute("errorMsg", errorMsg);
			view = request.getRequestDispatcher(path);
			view.forward(request, response);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
