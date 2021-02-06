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
import com.kh.skillup.member.model.vo.Member;
import com.kh.skillup.searchlesson.model.service.SearchLessonService;

@WebServlet("/main")
public class IndexController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public IndexController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			SearchLessonService service = new SearchLessonService();
			
			Map<String, Object> map = new HashMap<String, Object>();
			PageInfo pInfo = service.getPageInfo(map);

			// 관심 분야
			if(request.getSession().getAttribute("loginMember") != null) {
				String interest = ((Member) request.getSession().getAttribute("loginMember")).getInterest();
				
				if(interest != null) {
					String[] interestArr = interest.split(",");
					int ran = (int) (Math.random() * interestArr.length);
					map.put("ctgrName", interestArr[ran]);
					
					List<Lesson> intList = service.searchLessonList(map, pInfo);
					
					if(intList != null) {
						List<Attachment> intfList = service.searchThumbnailList(map, pInfo);
						if(!intfList.isEmpty()) 
							request.setAttribute("intfList", intfList);
					}
					request.setAttribute("intList", intList);
					map.put("ctgrName", null);
				}
			}
			
			// 좋아요 높은 순
			map.put("order", "like");
			List<Lesson> likeList = service.searchLessonList(map, pInfo);
			
			if(likeList != null) {
				List<Attachment> likefList = service.searchThumbnailList(map, pInfo);
				if(!likefList.isEmpty()) 
					request.setAttribute("likefList", likefList);
			}
			request.setAttribute("likeList", likeList);
			
			// 인기순
			map.put("order", "rank");
			List<Lesson> rankList = service.searchLessonList(map, pInfo);
			
			if(rankList != null) {
				List<Attachment> rankfList = service.searchThumbnailList(map, pInfo);
				if(!rankfList.isEmpty()) 
					request.setAttribute("rankfList", rankfList);
			}
			request.setAttribute("rankList", rankList);
			
			// 
			map.put("order", "latest");
			List<Lesson> latestList = service.searchLessonList(map, pInfo);
			
			if(latestList != null) {
				List<Attachment> latestfList = service.searchThumbnailList(map, pInfo);
				if(!latestfList.isEmpty()) 
					request.setAttribute("latestFList", latestfList);
			}
			request.setAttribute("latestList", latestList);
			
			RequestDispatcher view = request.getRequestDispatcher("/index.jsp");
			view.forward(request, response);
			
		} catch (Exception e) {
			e.printStackTrace();
			
			String path = "/WEB-INF/views/common/errorPage.jsp";
			request.setAttribute("errorMsg", "index 수업 목록 과정에서 오류 발생");
			RequestDispatcher view = request.getRequestDispatcher(path);
			view.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
