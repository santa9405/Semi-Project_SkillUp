package com.kh.skillup.searchlesson.model.service;

import static com.kh.skillup.common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kh.skillup.lesson.model.vo.Attachment;
import com.kh.skillup.lesson.model.vo.Lesson;
import com.kh.skillup.lesson.model.vo.PageInfo;
import com.kh.skillup.searchlesson.model.dao.SearchLessonDAO;

public class SearchLessonService {
	private SearchLessonDAO dao = new SearchLessonDAO();

	public PageInfo getPageInfo(Map<String, Object> map) throws Exception {
		Connection conn = getConnetion();
		
		map.put("currentPage", (map.get("currentPage") == null) ? 1 : Integer.parseInt((String)map.get("currentPage")));
		
		Map<String, Object> cMap = createCondition(map);
		
		int listCount = dao.getListCount(conn, (String)cMap.get("condition"));
		
		close(conn);
		
		return new PageInfo((int) map.get("currentPage"), listCount);
	}

	private Map<String, Object> createCondition(Map<String, Object> map) {
		
		String condition = "";
		
		String sido = (String) map.get("sido");
		String gugun = (String) map.get("gugun");

		int price1 = map.get("price1") == null ? 0 : (int) map.get("price1");
		int price2 = map.get("price1") == null ? 0: (int) map.get("price2");
		
		String searchValue = (String) map.get("searchValue");
		
		String onoff = (String) map.get("onoff");
		String cnt = (String) map.get("cnt");
		String num = (String) map.get("num");
		
		String ctgrName = (String) map.get("ctgrName");
		String order = (String) map.get("order");
		
		condition = sido == null || sido.equals("") ?  "" : "AND PLACE LIKE '%' || '" + sido + " " + gugun + "' || '%' ";
		
		if(price1 != 0 && price2 != 0) {
			if(price1 == price2) condition += "AND PRICE = "+ price1 + " ";
			else if(price1 < price2) condition += "AND PRICE BETWEEN "+ price1 +" AND " + price2 + " ";
			else condition += "AND PRICE BETWEEN "+ price2 +" AND " + price1 + " ";
		}
		
		condition +=  searchValue == null || searchValue.equals("") ? "" : "AND (LESSON_TITLE LIKE '%' || '"+ searchValue +"' ||'%' "
				+ "OR LESSON_CONTENT LIKE '%' || '" + searchValue + "' || '%' OR MEMBER_NAME LIKE '%' || '" + searchValue + "' || '%') ";
		
		if(onoff != null) {
			if(onoff.equals("on")) condition += "AND PLACE IS NULL ";
			else if(onoff.equals("off")) condition += "AND PLACE IS NOT NULL ";
		}
		
		if(cnt != null) {
			if(cnt.equals("oneday")) condition += "AND LESSON_CNT = 1 ";
			else if(cnt.equals("multiple")) condition += "AND LESSON_CNT > 1 ";
		}
		
		if(num != null) {
			if(num.equals("one")) condition += "AND MAX_NUM = 1 ";
			else if(num.equals("group")) condition += "AND MAX_NUM > 1 ";
		}

		if(ctgrName != null && ctgrName != "")
			condition += " AND CTGR_NM = '" + ctgrName + "' ";
		
		String orderby = "";
		if(order != null && order.equals("like")) orderby = "ORDER BY LIKES_CNT DESC";
		else if(order != null && order.equals("rank")) orderby = "ORDER BY  READ_CNT DESC";
		else if(order != null && order.equals("latest")) orderby = "ORDER BY LESSON_NO DESC";
		else orderby = "ORDER BY LESSON_NO DESC";
		
		Map<String, Object> cMap = new HashMap<String, Object>();
		cMap.put("condition", condition);
		cMap.put("orderby", orderby);
		
		return cMap;
	}

	public List<Lesson> searchLessonList(Map<String, Object> map, PageInfo pInfo) throws Exception {
		Connection conn = getConnetion();
		
		Map<String, Object> cMap = createCondition(map);
		List<Lesson> lessonList = dao.searchLessonList(conn, pInfo, cMap);
		
		close(conn);
		return lessonList;
	}

	public List<Attachment> searchThumbnailList(Map<String, Object> map, PageInfo pInfo) throws Exception {
		Connection conn = getConnetion();
		
		Map<String, Object> cMap = createCondition(map);
		List<Attachment> fList = dao.searchThumbnailList(conn, pInfo, cMap);
		
		close(conn);
		return fList;
	}
}