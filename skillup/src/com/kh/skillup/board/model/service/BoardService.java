package com.kh.skillup.board.model.service;

import java.sql.Connection;

import java.util.List;
import java.util.Map;

import com.kh.skillup.board.model.dao.BoardDAO;
import com.kh.skillup.board.model.vo.Board;
import com.kh.skillup.board.model.vo.PageInfo;
import com.kh.skillup.reply.model.vo.Reply;

import static com.kh.skillup.common.JDBCTemplate.*;

public class BoardService {

	BoardDAO dao = new BoardDAO();
	
	private String replaceParameter(String param) {

		String result = param;
		if(result != null) {
			result = result.replaceAll("&", "&amp;");
			result = result.replaceAll("<", "&lt;");
			result = result.replaceAll(">", "&gt;");
			result = result	.replaceAll("\"", "&quot;");
		}
		return result;
	}
	
	public PageInfo getPageInfo(String page, int boardNo) throws Exception {
		Connection conn = getConnetion();
		int currentPage = (page == null? 1: Integer.parseInt(page));
		int listCount = dao.getListCount(conn, boardNo);
		close(conn);
		return new PageInfo(currentPage, listCount);
	}
	
	/** 게시판 목록조회 Service
	 * @param pInfo
	 * @param boardNo 
	 * @return list
	 * @throws Exception
	 */
	public List<Board> selectBoardList(PageInfo pInfo, int boardNo) throws Exception {
		Connection conn = getConnetion();
		List<Board> list = dao.selectBoardList(conn, pInfo, boardNo);
		close(conn);
		return list;
	}

	public Board selectBoard(int documentNo, int boardNo) throws Exception {
		Connection conn = getConnetion();
		int result = dao.increaseReadCount(conn, documentNo);
		if(result > 0) {
			commit(conn);
		}
		else { 
			close(conn);
		}
		Board board = dao.selectBoard(conn, documentNo, boardNo);
		close(conn);
		return board;
	}

	public int writeBoard(Map<String, Object> map) throws Exception {
		Connection conn = getConnetion();
		int documentNo = dao.selectDocumentNo(conn);
		String title = (String)map.get("title");
		String content = (String)map.get("content");
		title = replaceParameter(title);
		content = replaceParameter(content);
		content = content.replaceAll("\r\n", "<br>");
		map.put("documentNo", documentNo);
		map.put("title", title);
		map.put("content", content);
		int result = dao.writeBoard(conn, map);
		if(result > 0) {
			commit(conn);
			result = documentNo;
		}
		if(result < 0) {
			rollback(conn);
		}
		close(conn);
		return result;
	}

	public int deleteBoard(int documentNo) throws Exception {
		Connection conn = getConnetion();
		
		dao.deleteReply(conn, documentNo);
		int result = dao.deleteBoard(conn, documentNo);
		if(result > 0) {
			commit(conn);
		}
		if(result < 0) {
			rollback(conn);
		}
		close(conn);
		return result;
	}

	public int updateBoard(Map<String, Object> map) throws Exception {
		Connection conn = getConnetion();
		String title = (String)map.get("title");
		String content = (String)map.get("content");
		title = replaceParameter(title);
		content = replaceParameter(content);
		content = content.replaceAll("\r\n", "<br>");
		map.put("title", title);
		map.put("content", content);
		int result = dao.updateBoard(conn, map);
		if(result > 0) {
			commit(conn);
		}
		else {
			rollback(conn);
		}
		close(conn);
		return result;
	}

	public Board updateView(int documentNo, int boardNo) throws Exception {
		Connection conn = getConnetion();
		Board board = dao.selectBoard(conn, documentNo, boardNo);
		board.setBoardContent(board.getBoardContent().replace("<br>", "\r\n"));
		close(conn);
		return board;
	}

	public int nextDocument(int documentNo, int boardNo) throws Exception {
		Connection conn = getConnetion();
		int result = dao.nextDocument(conn, documentNo, boardNo);
		close(conn);
		return result;
	}

	public int prevDocument(int documentNo, int boardNo) throws Exception {
		Connection conn = getConnetion();
		int result = dao.prevDocument(conn, documentNo, boardNo);
		close(conn);
		return result;
	}

	public int reportBoard(Map<String,Object> map) throws Exception {
		Connection conn = getConnetion();
		int result = dao.reportBoard(conn, map);
		if(result > 0) {
			commit(conn);
		}
		else {
			rollback(conn);
		}
		close(conn);
		return result;
	}

	public List<Board> myBoard(int memberNo) throws Exception {
		Connection conn = getConnetion();
		List<Board> list = dao.myBoard(conn, memberNo);
		close(conn);
		return list;
	}

	public List<Reply> myReply(int memberNo) throws Exception {
		Connection conn = getConnetion();
		List<Reply> list = dao.myReply(conn, memberNo);
		close(conn);
		return list;
	}

}
