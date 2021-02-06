package com.kh.skillup.board.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kh.skillup.board.model.service.BoardService;
import com.kh.skillup.board.model.vo.Board;
import com.kh.skillup.board.model.vo.PageInfo;
import com.kh.skillup.member.model.vo.Member;
import com.kh.skillup.reply.model.vo.Reply;

@WebServlet("/board/*")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public BoardController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String contextPath = request.getContextPath(); // /semi
		//		String uri = request.getRequestURI(); // /semi/board/something
		//		String command = uri.substring((contextPath + "/board").length()); // /something
		String path = null;
		String swalIcon = null;
		String swalTitle = null;
		String swalText = null;
		try {
			BoardService service = new BoardService();
			String page = request.getParameter("page");
			int boardNo = 0;
			if(request.getParameter("boardNo")!=null) {
				boardNo = Integer.parseInt(request.getParameter("boardNo"));
			}
			request.setAttribute("boardNo", boardNo);
			// 목록조회
			if(request.getRequestURI().equals(contextPath + "/board/list")) {
				PageInfo pInfo = service.getPageInfo(page, boardNo);
				List<Board> list = service.selectBoardList(pInfo, boardNo);
				// System.out.println(pInfo);
				request.setAttribute("pInfo", pInfo);
				request.setAttribute("list", list);
				path = "/WEB-INF/views/board/boardList.jsp";
				request.getRequestDispatcher(path).forward(request, response);
			}
			
			// 상세조회
			else if(request.getRequestURI().equals(contextPath + "/board/view")) {
				int documentNo = Integer.parseInt(request.getParameter("document"));
				PageInfo pInfo = service.getPageInfo(page, boardNo);
				List<Board> list = service.selectBoardList(pInfo, boardNo);
				// System.out.println(pInfo);
				request.setAttribute("pInfo", pInfo);
				request.setAttribute("list", list);
				Board board = service.selectBoard(documentNo, boardNo);
				request.setAttribute("page", page);
				request.setAttribute("board", board);
				path = "/WEB-INF/views/board/boardView.jsp";
				request.getRequestDispatcher(path).forward(request, response);
			}
			
			// 작성창
			else if(request.getRequestURI().equals(contextPath + "/board/write")) {				
				path = "/WEB-INF/views/board/boardWrite.jsp";
				request.getRequestDispatcher(path).forward(request, response);
			}
			
			// 작성(입력)
			else if(request.getRequestURI().equals(contextPath + "/board/insert")) {
				String category = request.getParameter("category");
				String title = request.getParameter("title");
				String content = request.getParameter("content");
				int boardWriter = ((Member)request.getSession().getAttribute("loginMember")).getMemberNo();
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("category", category);
				map.put("title", title);
				map.put("content", content);
				map.put("boardWriter", boardWriter);
				map.put("boardNo", boardNo);
				int result = service.writeBoard(map);
				if(result > 0) { // DB 삽입 성공시 result에 삽입한 글 번호가 저장
					swalIcon = "success";
					swalTitle = "게시글 등록 성공";
					path = "view?document=" + result + "&boardNo=" + boardNo;
				}
				else {
					swalIcon = "error";
					swalTitle = "게시글 등록 실패";
					path = "list";
				}
				request.getSession().setAttribute("swalIcon", swalIcon);
				request.getSession().setAttribute("swalTitle", swalTitle);
				response.sendRedirect(path);
			}
			
			// 삭제
			else if(request.getRequestURI().equals(contextPath + "/board/delete")) {
				int documentNo = Integer.parseInt(request.getParameter("document"));
				int result = service.deleteBoard(documentNo);
				if(result > 0) {
					swalIcon = "success";
					swalTitle = "게시글 삭제 성공";
					path = "list" + (page==null ? "" : "?page="+page) + "?boardNo=1";
				}
				else {
					swalIcon = "error";
					swalTitle = "게시글 삭제 실패";
				}
				request.getSession().setAttribute("swalIcon", swalIcon);
				request.getSession().setAttribute("swalTitle", swalTitle);
				response.sendRedirect(path);
			}
			
			// 수정창
			else if(request.getRequestURI().equals(contextPath + "/board/revise")) {
				int documentNo = Integer.parseInt(request.getParameter("document"));
				Board board = service.updateView(documentNo, boardNo);
				request.setAttribute("board", board);
				path = "/WEB-INF/views/board/boardUpdate.jsp";
				request.getRequestDispatcher(path).forward(request, response);
			}
			
			// 수정(입력)
			else if(request.getRequestURI().equals(contextPath + "/board/update")) {
				int documentNo = Integer.parseInt(request.getParameter("document"));
				String category = request.getParameter("category");
				String title = request.getParameter("title");
				String content = request.getParameter("content");
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("documentNo", documentNo);
				map.put("category", category);
				map.put("title", title);
				map.put("content", content);
				int result = service.updateBoard(map);
				if(result > 0) {
					swalIcon = "success";
					swalTitle = "게시글 수정 성공";
					path = "view?document=" + documentNo + "&page=" + page + "&boardNo=" + boardNo;
				}
				else {
					swalIcon = "error";
					swalTitle = "게시글 수정 실패";
				}
				request.getSession().setAttribute("swalIcon", swalIcon);
				request.getSession().setAttribute("swalTitle", swalTitle);
				response.sendRedirect(path);
			}
			
			else if(request.getRequestURI().equals(contextPath + "/board/prev")) {
				PageInfo pInfo = service.getPageInfo(page, boardNo);
				int documentNo = Integer.parseInt(request.getParameter("document"));
				int newDocumentNo = service.prevDocument(documentNo, boardNo);
				if(newDocumentNo == 0) {
					path = "view?document=" + documentNo + "&page=" + pInfo.getCurrentPage() + "&boardNo=" + boardNo;
				}
				else {
					path = "view?document=" + newDocumentNo + "&page=" + pInfo.getCurrentPage() + "&boardNo=" + boardNo;
				}
				response.sendRedirect(path);
			}
			
			else if(request.getRequestURI().equals(contextPath + "/board/next")) {
				PageInfo pInfo = service.getPageInfo(page, boardNo);
				int documentNo = Integer.parseInt(request.getParameter("document"));
				int newDocumentNo = service.nextDocument(documentNo, boardNo);
				if(newDocumentNo == 0) {
					path = "view?document=" + documentNo + "&page=" + pInfo.getCurrentPage() + "&boardNo=" + boardNo;
				}
				else {
					path = "view?document=" + newDocumentNo + "&page=" + pInfo.getCurrentPage() + "&boardNo=" + boardNo;
				}
				response.sendRedirect(path);
			}
			
			else if(request.getRequestURI().equals(contextPath + "/board/report")) {
				int documentNo = Integer.parseInt((String)request.getParameter("documentNo"));
				int memberNo = ((Member)request.getSession().getAttribute("loginMember")).getMemberNo();
				String reportContent = request.getParameter("reportContent");
				int reportCategory = Integer.parseInt((String)request.getParameter("reportCategory"));
				
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("documentNo", documentNo);
				map.put("memberNo", memberNo);
				map.put("reportContent", reportContent);
				map.put("reportCategory", reportCategory);
				
				int result = service.reportBoard(map);
				if(result > 0) {
					swalIcon = "success";
					swalTitle = "신고에 성공했습니다";
				}
				else {
					swalIcon = "error";
					swalTitle = "신고를 실패했습니다.";
				}
				request.getSession().setAttribute("swalIcon", swalIcon);
				request.getSession().setAttribute("swalTitle", swalTitle);
				response.sendRedirect(request.getHeader("referer"));
			}
			
			else if(request.getRequestURI().equals(contextPath + "/board/myboard")) {
				int memberNo = ((Member)request.getSession().getAttribute("loginMember")).getMemberNo();
				List<Board> list = service.myBoard(memberNo);
				request.setAttribute("list", list);
				path = "/WEB-INF/views/board/myBoard.jsp";
				request.getRequestDispatcher(path).forward(request, response);
			}
			
			else if(request.getRequestURI().equals(contextPath + "/board/myreply")) {
				int memberNo = ((Member)request.getSession().getAttribute("loginMember")).getMemberNo();
				List<Reply> list = service.myReply(memberNo);
				request.setAttribute("list", list);
				path = "/WEB-INF/views/board/myReply.jsp";
				request.getRequestDispatcher(path).forward(request, response);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

