package com.kh.skillup.searchInstructor.model.dao;

import static com.kh.skillup.common.JDBCTemplate.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.kh.skillup.instructor.model.vo.Instructor;
import com.kh.skillup.instructor.model.vo.PageInfo;

public class SearchInstructorDAO {
	
	private Statement stmt = null;
	private PreparedStatement pstmt = null;
	private ResultSet rset = null;

	/** 조건을 만족하는 전문가 수 조회 DAO
	 * @param conn
	 * @param condition
	 * @return listCount
	 * @throws Exception
	 */
	public int getListCount(Connection conn, String condition) throws Exception {
		int listCount = 0;
		
		String query = "SELECT COUNT(*) FROM ENROLLMENT WHERE " + condition;
		
		try {
			stmt = conn.createStatement();
			rset = stmt.executeQuery(query);
			if(rset.next()) {
				listCount = rset.getInt(1);
			}
			
		}finally {
			close(rset);
			close(stmt);
		}
		
		return listCount;
	}

	/** 검색 목록 조회 DAO
	 * @param conn
	 * @param pInfo
	 * @param condition
	 * @return iList
	 * @throws Exception
	 */
	public List<Instructor> searchInstrList(Connection conn, PageInfo pInfo, String condition) throws Exception {
		List<Instructor> iList = null;
		
		String query =
				"SELECT * FROM" + 
				"		(SELECT ROWNUM RNUM, V.*" + 
				"		 FROM" + 
				"		    (SELECT * FROM ENROLLMENT " +
				"			WHERE " + condition +
				"		    ORDER BY INSTR DESC) V)" + 
				"		WHERE RNUM BETWEEN ? AND ?";
		
		try {
			int startRow = (pInfo.getCurrentPage() - 1) * pInfo.getLimit() + 1;
			int endRow = startRow + pInfo.getLimit() - 1;
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			
			rset = pstmt.executeQuery();
			
			iList = new ArrayList<Instructor>();
			
			while (rset.next()) {
				Instructor instr = new Instructor(rset.getInt("INSTR"), rset.getInt("CTGR_CD"), rset.getNString("INSTR_NAME"), rset.getNString("PERMIT_FL"), rset.getInt("ENROLLMENT_NO"));
				
				iList.add(instr);
			}
			
		}finally {
			close(rset);
			close(pstmt);
		}
		
		return iList;
	}

}
