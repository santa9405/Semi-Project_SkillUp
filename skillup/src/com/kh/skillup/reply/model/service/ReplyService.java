package com.kh.skillup.reply.model.service;

import static com.kh.skillup.common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.List;

import com.kh.skillup.reply.model.dao.ReplyDAO;
import com.kh.skillup.reply.model.vo.Reply;


public class ReplyService {
	private ReplyDAO dao = new ReplyDAO();

	// 크로스 사이트 스크립트 방지 메소드
	private String replaceParameter(String param) {
		String result = param;
		if(param != null) {
			result = result.replaceAll("&", "&amp;");
			result = result.replaceAll("<", "&lt;");
			result = result.replaceAll(">", "&gt;");
			result = result.replaceAll("\"", "&quot;");
		}
		return result;
	}
	
	/** 댓글 목록 조회  Service
	 * @param parentBoardNo
	 * @return rList
	 * @throws Exception
	 */
	public List<Reply> selectList(int parentBoardNo) throws Exception {
		Connection conn = getConnetion();
		List<Reply> rList = dao.selectList(conn, parentBoardNo);
		close(conn);
		return rList;
	}

	/** 댓글삽입 Service
	 * @param reply
	 * @return result
	 * @throws Exception
	 */
	public int insertReply(Reply reply) throws Exception {
		Connection conn = getConnetion();
		
		// 크로스 사이트 스크립팅 방지 처리
		String replyContent = reply.getReplyContent();
		replyContent = replaceParameter(replyContent);
		
		// 개행문자 변환 처리
		// ajax통신시 textarea의 개행문자가 \n 하나넘어온다  → <br>
		replyContent = replyContent.replaceAll("\n", "<br>");
		reply.setReplyContent(replyContent);
		int result = dao.insertReply(conn, reply);
		
		// 트랜잭션 처리
		if(result > 0) {
			commit(conn);
		}
		else {
			rollback(conn);
		}
		return result;
	}
	


	/** 댓글 수정 Service
	 * @param reply
	 * @return result
	 * @throws Exception
	 */
	public int updateReply(Reply reply) throws Exception {
		Connection conn = getConnetion();
		String replyContent = reply.getReplyContent();
		replyContent = replaceParameter(replyContent);
		//System.out.println(replyContent);
		replyContent = replyContent.replaceAll("\n", "<br>");
		reply.setReplyContent(replyContent);
		int result = dao.updateReply(conn, reply);
		if(result > 0) {
			commit(conn);
		}
		else {
			rollback(conn);
		}
		close(conn);
		return result;
	}

	/** 댓글 상태 변경 Service
	 * @param replyNo
	 * @return result
	 * @throws Exception
	 */
	public int updateReplyStatus(int replyNo) throws Exception {
		Connection conn = getConnetion();
				
		int result = dao.updateReplyStatus(conn, replyNo);
		if(result > 0) {
			commit(conn);
		}
		else {
			rollback(conn);
		}
		close(conn);
		return result;
	}
}
