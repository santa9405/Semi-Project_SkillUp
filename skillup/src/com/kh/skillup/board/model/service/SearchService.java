package com.kh.skillup.board.model.service;

import static com.kh.skillup.common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import com.kh.skillup.board.model.vo.Board;
import com.kh.skillup.board.model.vo.PageInfo;
import com.kh.skillup.board.model.dao.SearchDAO;

public class SearchService {
	private SearchDAO dao = new SearchDAO();
	public PageInfo getPageInfo(Map<String, Object> map) throws Exception {
		Connection conn = getConnetion();
		map.put("currentPage", map.get("currentPage")==null ? 1:Integer.parseInt((String)map.get("currentPage")));
		String condition = createCondition(map);
		int listCount = dao.getListCount(conn, condition);
		close(conn);
		return new PageInfo((int)map.get("currentPage"), listCount);
	}

	// 검색 조건에 따른 SQL조건문을 조합하려는 메소드 호출
	private String createCondition(Map<String, Object> map) throws Exception {
		
		String condition = null;
		String searchKey = (String)map.get("searchKey");
		String searchValue = (String)map.get("searchValue");
		
		// 검색 조건에 따라 SQL 조합
		switch(searchKey) {
		case "title":
			condition = " BOARD_NO = " + (Integer)(map.get("boardNo")) + " AND BOARD_TITLE LIKE '%' || '" + searchValue + "' || '%' ";
			break;			// BOARD_TITLE LIKE '%49%'
		case "content":
			condition = " BOARD_NO = " + (Integer)(map.get("boardNo")) + " AND BOARD_CONTENT LIKE '%' || '" + searchValue + "' || '%' ";
			break;
		case "titcont":
			condition = " BOARD_NO = " + (Integer)(map.get("boardNo")) + " AND (BOARD_TITLE LIKE '%' || '" + searchValue + "' || '%' "
			+ "OR BOARD_CONTENT LIKE '%' || '" + searchValue + "' || '%') ";
			break;
		case "writer":
			condition = " BOARD_NO = " + (Integer)(map.get("boardNo")) + " AND MEMBER_NAME LIKE '%' || '" + searchValue + "' || '%' ";
			break;	
		}
		return condition;
	}
	
	public List<Board> searchBoardList(Map<String, Object> map, PageInfo pInfo) throws Exception {
		Connection conn = getConnetion();
		String condition = createCondition(map);
		List<Board> list = dao.searchBoardList(conn, pInfo, condition);
		close(conn);
		return list;
	}
	
}

