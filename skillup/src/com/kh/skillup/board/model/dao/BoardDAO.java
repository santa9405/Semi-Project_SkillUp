package com.kh.skillup.board.model.dao;

import java.io.FileInputStream;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.kh.skillup.board.model.vo.Board;
import com.kh.skillup.board.model.vo.PageInfo;
import com.kh.skillup.reply.model.vo.Reply;

import static com.kh.skillup.common.JDBCTemplate.*;

public class BoardDAO {
	private Statement stmt = null;
	private PreparedStatement pstmt = null;
	private ResultSet rset = null;
	private Properties prop = null;
	
	public BoardDAO(){
		String fileName = BoardDAO.class.getResource("/com/kh/skillup/sql/board/board-query.xml").getPath();
		try {
			prop = new Properties();
			prop.loadFromXML(new FileInputStream(fileName));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int getListCount(Connection conn, int boardNo) throws Exception {
		int listCount = 0;
		String query = prop.getProperty("getListCount");
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, boardNo);
			rset = pstmt.executeQuery();
			if(rset.next()) {
				listCount = rset.getInt(1);
			}
		} finally {
			close(rset);
			close(stmt);
		}
		return listCount;
	}
	
	public List<Board> selectBoardList(Connection conn, PageInfo pInfo, int boardNo) throws Exception {
		String query = prop.getProperty("selectBoardList");
		List<Board> list = new ArrayList<>();
		try {
			int startRow = (pInfo.getCurrentPage()-1)*pInfo.getLimit()+1;
			int endRow = startRow + pInfo.getLimit()-1;
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, boardNo);
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
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
		} finally {
			close(rset);
			close(pstmt);
		}
		return list;
	}
	
	public int increaseReadCount(Connection conn, int documentNo) throws Exception {
		int result = 0;
		String query = prop.getProperty("increaseReadCount");
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, documentNo);
			result = pstmt.executeUpdate();
		} finally {
			close(pstmt);
		}
		return result;
	}
	
	public Board selectBoard(Connection conn, int documentNo, int boardNo) throws Exception {
		Board board = new Board();
		String query = prop.getProperty("selectBoard");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, boardNo);
			pstmt.setInt(2, documentNo);
			rset = pstmt.executeQuery();
			if(rset.next()) {
				board.setDocumentNo(rset.getInt("DOCUMENT_NO"));
				board.setBoardTitle(rset.getString("BOARD_TITLE"));
				board.setBoardContent(rset.getString("BOARD_CONTENT"));
				board.setMemberNo(rset.getInt("MEMBER_NO"));
				board.setBoardDt(rset.getTimestamp("BOARD_DT"));
				board.setReadCount(rset.getInt("READ_COUNT"));
				board.setCategoryName(rset.getString("CTGR_NM"));
				board.setRowNum(rset.getInt("RNUM"));
				board.setMemberName(rset.getString("MEMBER_NAME"));
			}
		} finally {
			close(rset);
			close(stmt);
		}
		return board;
	}

	public int writeBoard(Connection conn, Map<String, Object> map) throws Exception {
		int result = 0;
		String query = prop.getProperty("insertBoard");
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, (int)map.get("documentNo"));
			pstmt.setString(2, (String)map.get("title"));
			pstmt.setString(3, (String)map.get("content"));
			pstmt.setInt(4, (int)map.get("boardWriter"));
			pstmt.setString(5, (String)map.get("category"));
			pstmt.setInt(6, (int)map.get("boardNo"));
			result = pstmt.executeUpdate();
		} finally {
			close(pstmt);
		}
		return result;
	}

	public int selectDocumentNo(Connection conn) throws Exception {
		int documentNo = 0;
		String query = prop.getProperty("selectDocumentNo");
		try {
			stmt = conn.createStatement();
			rset = stmt.executeQuery(query);
			if(rset.next()) {
				documentNo = rset.getInt(1);
			}
		} finally {
			close(rset);
			close(stmt);
		}
		return documentNo;
	}

	public int deleteBoard(Connection conn, int documentNo) throws Exception {
		int result = 0;
		String query = prop.getProperty("deleteBoard");
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, documentNo);
			result = pstmt.executeUpdate();
		} finally {
			close(pstmt);
		}
		return result;
	}
	
	public int deleteReply(Connection conn, int documentNo) throws Exception {
		int result = 0;
		String query = prop.getProperty("deleteReply");
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, documentNo);
			result = pstmt.executeUpdate();
		} finally {
			close(pstmt);
		}
		return result;
	}

	public int updateBoard(Connection conn, Map<String, Object> map) throws Exception {
		int result = 0;
		String query = prop.getProperty("updateBoard");
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, (String)map.get("title"));
			pstmt.setString(2, (String)map.get("content"));
			pstmt.setString(3, (String)map.get("category"));
			pstmt.setInt(4, (int)map.get("documentNo"));
			result = pstmt.executeUpdate();
		} finally {
			close(pstmt);
		}
		return result;
	}

	public int nextDocument(Connection conn, int documentNo, int boardNo) throws Exception {
		int result = 0;
		String query = prop.getProperty("changeDocument");
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, boardNo);
			pstmt.setInt(2, documentNo);
			rset = pstmt.executeQuery();
			if(rset.next()) {
				result = rset.getInt(1);
			}
		} finally {
			close(rset);
			close(pstmt);
		}
		return result;
	}

	public int prevDocument(Connection conn, int documentNo, int boardNo) throws Exception {
		int result = 0;
		String query = prop.getProperty("changeDocument");
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, boardNo);
			pstmt.setInt(2, documentNo);
			rset = pstmt.executeQuery();
			if(rset.next()) {
				result = rset.getInt(2);
			}
		} finally {
			close(rset);
			close(pstmt);
		}
		return result;
	}

	public int reportBoard(Connection conn, Map<String,Object> map) throws Exception {
		int result = 0;
		String query = prop.getProperty("reportBoard");
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, (String)map.get("reportContent"));
			pstmt.setInt(2, (int)map.get("documentNo"));
			pstmt.setInt(3, (int)map.get("memberNo"));
			pstmt.setInt(4, (int)map.get("reportCategory"));
			result = pstmt.executeUpdate();
		} finally {
			close(pstmt);
		}
		return result;
	}

	public List<Board> myBoard(Connection conn, int memberNo) throws Exception {
		
		List<Board> list = new ArrayList<Board>();
		String query = prop.getProperty("myBoard");
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, memberNo);
			rset = pstmt.executeQuery();
			while(rset.next()) {
				Board myBoard = new Board();
				myBoard.setDocumentNo(rset.getInt("DOCUMENT_NO"));
				myBoard.setBoardTitle(rset.getString("BOARD_TITLE"));
				myBoard.setBoardDt(rset.getTimestamp("BOARD_DT"));
				myBoard.setReadCount(rset.getInt("READ_COUNT"));
				list.add(myBoard);
			}
		} finally {
			close(rset);
			close(pstmt);
		}
		return list;
	}

	public List<Reply> myReply(Connection conn, int memberNo) throws Exception {
		List<Reply> list = new ArrayList<Reply>();
		String query = prop.getProperty("myReply");
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, memberNo);
			rset = pstmt.executeQuery();
			while(rset.next()) {
				Reply myReply= new Reply();
				myReply.setReplyContent(rset.getString("COMMENT"));
				myReply.setReplyCreateDate(rset.getTimestamp("COMMENT_DT"));
				myReply.setParentBoardNo(rset.getInt("DOCUMENT_NO"));
				list.add(myReply);
			}
		} finally {
			close(rset);
			close(pstmt);
		}
		return list;
	}
}
