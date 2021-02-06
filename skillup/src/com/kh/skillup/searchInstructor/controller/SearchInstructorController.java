package com.kh.skillup.searchInstructor.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.skillup.instructor.model.vo.Instructor;
import com.kh.skillup.instructor.model.vo.PageInfo;
import com.kh.skillup.searchInstructor.model.service.SearchInstructorService;

@WebServlet("/search.do")
public class SearchInstructorController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public SearchInstructorController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String searchKey = request.getParameter("sk");
		String searchValue = request.getParameter("sv1");
		String cp = request.getParameter("cp");
		
		try {
			SearchInstructorService service = new SearchInstructorService();
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("searchKey", searchKey);
			map.put("searchValue", searchValue);
			map.put("cp", cp);
			
			PageInfo pInfo = service.getPageInfo(map);
			
			// 검색 목록 조회
			List<Instructor> iList = service.searchInstrList(map, pInfo);
			
			// 조회된 내용과 PageInfo 객체를 request객체에 담아서 요청 위임
	        String path = "/WEB-INF/views/instructor/instructorApprove.jsp";
			request.setAttribute("pInfo", pInfo);
			request.setAttribute("iList", iList);
			RequestDispatcher view = request.getRequestDispatcher(path);
			view = request.getRequestDispatcher(path);
			view.forward(request, response);
	         
			
		}catch(Exception e) {
			e.printStackTrace();
			String path = "/WEB-ING/views/common/errorPage.jsp";
			request.setAttribute("errorMsg", "검색 과정에서 오류 발생");
			RequestDispatcher view = request.getRequestDispatcher(path);
			view.forward(request, response);
		}
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
