package com.kh.skillup.board.model.dao;

import static com.kh.skillup.common.JDBCTemplate.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.kh.skillup.board.model.vo.Board;
import com.kh.skillup.board.model.vo.PageInfo;

public class SearchDAO {
	private Statement stmt = null;
	private PreparedStatement pstmt = null;
	private ResultSet rset = null;

	public int getListCount(Connection conn, String condition) throws Exception {
		int listCount = 0;
		String query = "SELECT COUNT(*) FROM V_BOARD WHERE BOARD_FL = 'N' AND " + condition;
		
		try {
			stmt = conn.createStatement();
			rset = stmt.executeQuery(query);
			if(rset.next()) {
				listCount = rset.getInt(1);
			} 
		} finally {
			close(rset);
			close(stmt);
		}
		return listCount;
	}

	public List<Board> searchBoardList(Connection conn, PageInfo pInfo, String condition) throws Exception {
		List<Board> list = new ArrayList<Board>();
		String query = 
				"SELECT * FROM" + 
				"    (SELECT ROWNUM RNUM , V.*" + 
				"    FROM" + 
				"        (SELECT * FROM V_BOARD " + 
				"        WHERE " + condition + 
				"        AND BOARD_FL = 'N' ORDER BY DOCUMENT_NO DESC) V )" + 
				"WHERE RNUM BETWEEN ? AND ?";
		
		try {
			// SQL 구문 조건절에 대입할 변수 생성
			int startRow = (pInfo.getCurrentPage()-1)*pInfo.getLimit()+1;
			int endRow = startRow + pInfo.getLimit()-1;
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			rset = pstmt.executeQuery();
			while(rset.next()) {
				Board board = new Board();
				board.setDocumentNo(rset.getInt("DOCUMENT_NO"));
				board.setBoardTitle(rset.getString("BOARD_TITLE"));
				board.setBoardContent(rset.getString("BOARD_CONTENT"));
				board.setMemberNo(rset.getInt("MEMBER_NO"));
				board.setMemberName(rset.getString("MEMBER_NAME"));
				board.setBoardDt(rset.getTimestamp("BOARD_DT"));
				board.setReadCount(rset.getInt("READ_COUNT"));
				board.setCategoryName(rset.getString("CTGR_NM"));
				board.setRowNum(pInfo.getListCount() - rset.getInt("RNUM") + 1);
				list.add(board);
			}
			System.out.println(list);
		} finally {
			close(rset);
			close(pstmt);
		}
		return list;
	}
}
