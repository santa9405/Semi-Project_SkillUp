package com.kh.skillup.likes.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.kh.skillup.likes.model.service.LikesService;

@WebServlet("/likes/*")
public class LikesController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public LikesController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String uri = request.getRequestURI();
		String contextPath = request.getContextPath();
		String command = uri.substring((contextPath + "/likes").length());
		
		try {
			LikesService service = new LikesService();
			
			if(command.equals("/insert.do")) {
				
				int lessonNo = Integer.parseInt(request.getParameter("lessonNo"));
				int memberNo = Integer.parseInt(request.getParameter("memberNo"));
				
				int result = service.insertLikes(lessonNo, memberNo);
				response.getWriter().print(result);
			} 
			else if(command.equals("/delete.do")) {
				int lessonNo = Integer.parseInt(request.getParameter("lessonNo"));
				int memberNo = Integer.parseInt(request.getParameter("memberNo"));
				
				int result = service.deleteLikes(lessonNo, memberNo);
				response.getWriter().print(result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
