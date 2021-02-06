package com.kh.skillup.searchlesson.controller;

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

import com.kh.skillup.lesson.model.vo.Attachment;
import com.kh.skillup.lesson.model.vo.Lesson;
import com.kh.skillup.lesson.model.vo.PageInfo;
import com.kh.skillup.searchlesson.model.service.SearchLessonService;

@WebServlet("/searchLesson.do")
public class SearchLessonController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public SearchLessonController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String sido = request.getParameter("sido1");
		String gugun = request.getParameter("gugun1");

		int price1 = request.getParameter("price1") == null || request.getParameter("price1") == "" ? 0 : Integer.parseInt(request.getParameter("price1"));
		int price2 = request.getParameter("price2") == null || request.getParameter("price2") == "" ? 0 : Integer.parseInt(request.getParameter("price2"));
		
		String searchValue = request.getParameter("sv");
		
		String onoff = request.getParameter("onoff");
		String cnt = request.getParameter("cnt");
		String num = request.getParameter("num");
		
		String ctgrName = request.getParameter("ctgrName");
		String order = request.getParameter("order");
		String cp = request.getParameter("cp");
		
		try {
			SearchLessonService service = new SearchLessonService();
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("sido",sido);
			map.put("gugun",gugun);
			map.put("price1",price1);
			map.put("price2",price2);
			map.put("searchValue", searchValue);
			map.put("onoff", onoff);
			map.put("cnt", cnt);
			map.put("num", num);
			map.put("order", order);
			map.put("currentPage", cp);
			map.put("ctgrName", ctgrName);
			
			PageInfo pInfo = service.getPageInfo(map);
			
			List<Lesson> lessonList = service.searchLessonList(map, pInfo);
			
			if(lessonList != null) {
				List<Attachment> fList = service.searchThumbnailList(map, pInfo);
				
				if(!fList.isEmpty()) 
					request.setAttribute("fList", fList);
			}
			
			String path = "/WEB-INF/views/lesson/lessonList.jsp";
			
			request.setAttribute("lessonList", lessonList);
			request.setAttribute("pInfo", pInfo);
			
			RequestDispatcher view = request.getRequestDispatcher(path);
			view.forward(request, response);
			
		} catch (Exception e) {
			e.printStackTrace();
			
			String path = "/WEB-INF/views/common/errorPage.jsp";
			request.setAttribute("errorMsg", "검색 과정에서 오류 발생");
			RequestDispatcher view = request.getRequestDispatcher(path);
			view.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
