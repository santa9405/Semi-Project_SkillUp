package com.kh.skillup.board.controller;

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

import com.kh.skillup.board.model.vo.Board;
import com.kh.skillup.board.model.vo.PageInfo;
import com.kh.skillup.board.model.service.SearchService;

@WebServlet("/search")
public class SearchController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public SearchController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String searchKey = request.getParameter("sk");
		String searchValue = request.getParameter("sv");
		String page = request.getParameter("page");
		int boardNo = Integer.parseInt(request.getParameter("boardNo"));
		request.setAttribute("boardNo", boardNo);
		try {
			SearchService service = new SearchService();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("searchKey", searchKey);
			map.put("searchValue", searchValue);
			map.put("currentPage", page);
			map.put("boardNo", boardNo);
			
			// 페이징 처리를 위한 데이터를 계산하고 저장하는 객체 PageInfo얻어오기
			PageInfo pInfo = service.getPageInfo(map);
			
			// 검색 게시글 목록 조회
			List<Board> list = service.searchBoardList(map, pInfo);
			
			String path = "/WEB-INF/views/board/boardList.jsp";
			request.setAttribute("list", list);
			request.setAttribute("pInfo", pInfo);
			RequestDispatcher view = request.getRequestDispatcher(path);
			view.forward(request, response);
		} catch(Exception e) {
			e.printStackTrace();
			String path = "/WEB-INF/views/common/errorPage.jsp";
			request.setAttribute("errorMsg", "검색과정에서오류");
			RequestDispatcher view = request.getRequestDispatcher(path);
			view.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
