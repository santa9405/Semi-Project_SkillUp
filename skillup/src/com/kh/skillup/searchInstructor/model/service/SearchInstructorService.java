package com.kh.skillup.searchInstructor.model.service;

import static com.kh.skillup.common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import com.kh.skillup.instructor.model.vo.Instructor;
import com.kh.skillup.instructor.model.vo.PageInfo;
import com.kh.skillup.searchInstructor.model.dao.SearchInstructorDAO;

public class SearchInstructorService {
	SearchInstructorDAO dao = new SearchInstructorDAO();

	/** 겁색 내용이 포함된 페이징 처리 정보 생성 Service
	 * @param map
	 * @return map
	 * @throws Exception
	 */
	public PageInfo getPageInfo(Map<String, Object> map) throws Exception {
		Connection conn = getConnetion();
		
		map.put("currentPage", (map.get("currentPage") == null) ? 1 
				: Integer.parseInt((String)map.get("currentPage")));
		
		String condition = createCondition(map);
		
		// 조건을 만족하는 전문가 수 조회
		int listCount = dao.getListCount(conn, condition);
		
		close(conn);
		
		return new PageInfo((int)map.get("currentPage"), listCount);
	}

	/** 검색 조건에 따라 SQL에 사용될 조건문을 조합하는 메소드
	 * @param map
	 * @return condition
	 */
	private String createCondition(Map<String, Object> map) {
		String condition = null;
		
		String searchKey = (String)map.get("searchKey");
		String searchValue = (String)map.get("searchValue");
		
		switch(searchKey) {
		case "instr" :
								// " INSTR LIKE '%' || '49' || '%' "
			condition = " INSTR LIKE '%' || '" + searchValue + "' || '%' ";
			break;
			
		case "instrName" :
			condition = " INSTR_NAME LIKE '%' || '" + searchValue + "' || '%' ";
			break;
			
		case "permitFl" :
			condition = " PERMIT_FL LIKE '%' || '" + searchValue + "' || '%' ";
			break;
		}
		
		return condition;
	}

	/** 검색 목록 조회 Service
	 * @param map
	 * @param pInfo
	 * @return iList
	 * @throws Exception
	 */
	public List<Instructor> searchInstrList(Map<String, Object> map, PageInfo pInfo) throws Exception {
		Connection conn = getConnetion();
		
		String condition = createCondition(map);
		
		List<Instructor> iList = dao.searchInstrList(conn, pInfo, condition);
		
		close(conn);
		
		return iList;
	}

}
