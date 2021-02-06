package com.kh.skillup.review.model.service;

import static com.kh.skillup.common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import com.kh.skillup.lesson.model.vo.Lesson;
import com.kh.skillup.review.model.dao.ReviewDAO;
import com.kh.skillup.review.model.vo.Review;

public class ReviewService {
	private ReviewDAO dao = new ReviewDAO();

	/** 리뷰 작성자명 조회 Service
	 * @param instrNo
	 * @return writerName
	 * @throws Exception
	 */
//	public List<Review> selectWriterName(int instrNo) throws Exception {
//		
//		Connection conn = getConnetion();
//
//		List<Review> writerName = dao.selectWriterName(conn, instrNo);
//		
//		close(conn);
//		
//		return writerName;
//	}

	/**
	 * 리뷰 목록 조회 Service
	 * 
	 * @param parentBoardNo
	 * @return rList
	 * @throws Exception
	 */
	public List<Review> selectList(int instrNo) throws Exception {
		Connection conn = getConnetion();

		List<Review> rList = dao.selectList(conn, instrNo);
		
		close(conn);
		
		return rList;
	}
	
	/** 작성 가능 리뷰 확인 Service
	 * @param instrNo
	 * @param memNo
	 * @return result
	 * @throws Exception
	 */
	public int confirm(int instrNo, int memNo) throws Exception {
		Connection conn = getConnetion();
		
		int result = dao.confirm(conn, instrNo, memNo);
		
		close(conn);
		
		return result;
	}
	
	/** 수업 번호, 수업명 조회 Service
	 * @param instrNo
	 * @param memNo
	 * @return lTitle
	 * @throws Exception
	 */
	public List<Lesson> selectLessonTitle(int instrNo, int memNo) throws Exception {
		Connection conn = getConnetion();
		
		List<Lesson> lTitle = dao.selectLessonTitle(conn, instrNo, memNo);

		close(conn);
		
		return lTitle;
	}
	

	/** 리뷰 중복 확인 Service
	 * @param subjectNo
	 * @return
	 * @throws Exception
	 */
	public int overlap(int subjectNo) throws Exception {
		
		Connection conn = getConnetion();
		
		int overlap = dao.overlap(conn, subjectNo);
		
		close(conn);
		
		return overlap;
	}


	
	/** 리뷰 삽입 Service
	 * @param review
	 * @return result
	 * @throws Exception
	 */
	public int insertReview(Review review) throws Exception {
		Connection conn = getConnetion();
		
		// 크로스 사이트 스크립팅 방지 처리
		String reviewContent = review.getReviewContent();
		reviewContent = replaceParameter(reviewContent);
		
		// 개행문자 변환 처리
		reviewContent = reviewContent.replace("\n", "<br>");
		review.setReviewContent(reviewContent);
		
		int result = dao.insertReview(conn, review);
		
		if(result > 0) commit(conn);
		else		   rollback(conn);
		
		return result;
	}

	// 크로스 사이트 스크립트 방지 메소드
	private String replaceParameter(String param) {
		String result = param;
		if (param != null) {
			result = result.replaceAll("&", "&amp;");
			result = result.replaceAll("<", "&lt;");
			result = result.replaceAll(">", "&gt;");
			result = result.replaceAll("\"", "&quot;");
		}

		return result;
	}

	/**
	 * 리뷰 수정 Service
	 * 
	 * @param review
	 * @return result
	 * @throws Exception
	 */
	public int updateReview(Review review) throws Exception {

		Connection conn = getConnetion();

		String reviewContent = review.getReviewContent();

		// 크로스 사이트 스크립팅 방지 처리
		reviewContent = replaceParameter(reviewContent);

		// 개행문자 변환 처리
		// ajax 통신 시 textarea의 개행문자가 \n 하나만 넘어옴.
		// \n -> <br>
		reviewContent = reviewContent.replace("\n", "<br>");

		review.setReviewContent(reviewContent);

		int result = dao.updateReview(conn, review);

		if (result > 0)
			commit(conn);
		else
			rollback(conn);

		close(conn);

		return result;
	}

	/**
	 * 리뷰 상태 변경 Service
	 * 
	 * @param replyNo
	 * @return result
	 * @throws Exception
	 */
	public int updateReviewStatus(int reviewNo) throws Exception {

		Connection conn = getConnetion();
		
		int result = dao.updateReviewStatus(conn, reviewNo);
		
		if(result > 0) commit(conn);
		else		   rollback(conn);
		
		close(conn);

		return result;
	}

	/** 리뷰 신고 삽입 Service
	 * @param map
	 * @return result
	 * @throws Exception
	 */
	public int insertReport(Map<String, Object> map) throws Exception {
		Connection conn = getConnetion();
		
		int result = dao.insertReport(conn, map);
		
		if(result > 0) commit(conn);
		else		   rollback(conn);
		
		close(conn);

		return result;
	}

	/** 리뷰 개수 count Service
	 * @param instrNo
	 * @return result
	 * @throws Exception
	 */
	public int selectReviewCount(int instrNo) throws Exception {
		Connection conn = getConnetion();
		
		int result = dao.selectReviewCount(conn, instrNo);
		
		close(conn);
		
		return result;
	}

	/** 수강 평균 만족도 계산 Service
	 * @param instrNo
	 * @return result
	 * @throws Exception
	 */
	public int selectReviewScore(int instrNo) throws Exception {
		Connection conn = getConnetion();
		
		int result = dao.selectReviewScore(conn, instrNo);
		
		close(conn);
		
		return result;
	}
}
