package com.kh.skillup.review.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.kh.skillup.lesson.model.vo.Lesson;
import com.kh.skillup.member.model.vo.Member;
import com.kh.skillup.review.model.service.ReviewService;
import com.kh.skillup.review.model.vo.Review;
import java.sql.Timestamp;

@WebServlet("/review/*")
public class ReviewController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ReviewController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uri = request.getRequestURI();
      String contextPath = request.getContextPath();
      String command = uri.substring((contextPath+"/review").length());
      
      try {
         ReviewService service = new ReviewService();
         
         // 리뷰 목록 조회 Controller ---------------------------------
         if(command.equals("/selectList.do")) {
            int instrNo = Integer.parseInt(request.getParameter("instrNo"));
            
            List<Review> rList = service.selectList(instrNo);
            
            Gson gson = new GsonBuilder().setDateFormat("yyyy년 MM월 dd일 HH:mm").create();
            //gson.toJson(rList, response.getWriter()); // rList가 자동으로 gson 형태로 변환된다.
            
            gson.toJson(rList, response.getWriter()); // rList가 자동으로 gson 형태로 변환된다.
            
         }
         
         // 작성 가능 리뷰 확인 Controller ---------------------------------
         else if(command.equals("/confirm.do")) {
        	// 필요 값 : 전문가 번호, 학생 번호 
        	int instrNo = Integer.parseInt(request.getParameter("instrNo"));
        	Member loginMember = (Member)request.getSession().getAttribute("loginMember");
        	int memNo = loginMember.getMemberNo();
        	
       	    //System.out.println(instrNo);
       	    //System.out.println(memNo);
        	
        	int result = service.confirm(instrNo, memNo);
        	
        	response.getWriter().print(result);
         }
         
         // 리뷰 개수 count Controller ---------------------------------
         else if(command.equals("/selectReviewCount.do")) {
        	 int instrNo = Integer.parseInt(request.getParameter("instrNo"));
        	 
        	 //System.out.println(instrNo);
        	 
        	 int result = service.selectReviewCount(instrNo);
        	 
        	 response.getWriter().print(result);
         }
         
         // 수강 평균 만족도 계산 Controller ---------------------------------
         else if(command.equals("/selectReviewScore.do")) {
        	 int instrNo = Integer.parseInt(request.getParameter("instrNo"));
        	 
        	 //System.out.println(instrNo);
        	 
        	 int result = service.selectReviewScore(instrNo);
        	 
        	 System.out.println(result);
        	 
        	 response.getWriter().print(result);
         }
         
         // 수업명 조회 Controller ---------------------------------
         else if(command.equals("/selectLessonTitle.do")) {
        	 // 필요 값 : 전문가 번호, 학생 번호 
        	 int instrNo = Integer.parseInt(request.getParameter("instrNo"));
        	 
        	 Member loginMember = (Member)request.getSession().getAttribute("loginMember");
        	 int memNo = loginMember.getMemberNo();
        	 
//        	 System.out.println(instrNo);
//        	 System.out.println(memNo);
        	 
        	 List<Lesson> lTitle = service.selectLessonTitle(instrNo, memNo);
        	 
//        	 System.out.println(lTitle);
//        	 Gson gson = new Gson();
//        	 String result = gson.toJson(lTitle); // 리스트를 json으로 파싱 
        	 
        	 JSONObject jsonUser = null;
        	 
        	 for(int i=0; i<lTitle.size(); i++) {
	        	 jsonUser = new JSONObject();
	        	 jsonUser.put("subjectNo", lTitle.get(i).getLessonNo());
	        	 jsonUser.put("lessonTitle", lTitle.get(i).getLessonTitle());
        	 }
        	 
        	 //System.out.println(jsonUser.toJSONString());
        	 
        	 response.setCharacterEncoding("UTF-8");
        	 response.getWriter().print(jsonUser.toJSONString());
        	 //response.getWriter().write(result);	
         }
         
         // 리뷰 삽입 Controller -----------------------------------------------------------
         else if(command.equals("/insert.do")) {
        	 
        	// 작성자 번호(로그인 멤버), 수강 번호, 만족도, 후기
        	Member loginMember = (Member)request.getSession().getAttribute("loginMember");
        	int reviewWriter = loginMember.getMemberNo();
        	 
        	int instrNo = Integer.parseInt(request.getParameter("instr"));
        	int subjectNo = Integer.parseInt(request.getParameter("subjectNo"));
        	int score = Integer.parseInt(request.getParameter("score"));
        	String reviewContent = request.getParameter("reviewContent");
        	
			
			Review review = new Review();
			review.setReviewWriter(reviewWriter);
			review.setSubjectNo(subjectNo);
			review.setScore(score);
			review.setReviewContent(reviewContent);
			review.setInstructorNo(instrNo);
			
			// 리뷰 중복 확인
			int overlap = service.overlap(subjectNo);
			
			if(overlap == 0) {
				int result = service.insertReview(review);
				
				if(result > 0) {
					request.getSession().setAttribute("swalIcon", "success");
					request.getSession().setAttribute("swalTitle", "소중한 리뷰가 등록되었습니다. ʕ￫ᴥ￩ʔ ");
					response.sendRedirect(request.getHeader("referer"));
				}else {
					request.getSession().setAttribute("swalIcon", "error");
					request.getSession().setAttribute("swalTitle", "리뷰 등록이 실패했습니다.");
					response.sendRedirect(request.getHeader("referer"));
				}
			}else {
				request.getSession().setAttribute("swalIcon", "error");
				request.getSession().setAttribute("swalTitle", "이미 등록된 리뷰가 존재합니다.");
				response.sendRedirect(request.getHeader("referer"));
			}
         }
         
         
         // 리뷰 수정 Controller--------------------------------------------------------------
         else if(command.equals("/updateReview.do")) {
        	 
        	 // 파라미터(댓글 번호, 댓글 내용) 얻어오기
        	 int reviewNo = Integer.parseInt(request.getParameter("reviewNo"));
        	 String reviewContent = request.getParameter("reviewContent");
        	 
        	 Review review = new Review();
        	 review.setReviewNo(reviewNo);
        	 review.setReviewContent(reviewContent);
        	 
        	 int result = service.updateReview(review);
        	 
        	 response.getWriter().print(result);
         }
         
         
         // 리뷰 상태 변경 Controller-------------------------------------------------
         else if(command.equals("/deleteReview.do")) {
        	 
        	 int reviewNo = Integer.parseInt(request.getParameter("reviewNo"));
        	 
        	 System.out.println(reviewNo);
        	 
        	 int result = service.updateReviewStatus(reviewNo);
        	 
        	 response.getWriter().print(result);
        	 
         }
         
         // 리뷰 신고 Controller ----------------------------------------------------
         else if(command.equals("/report.do")) {
        	Member loginMember = (Member)request.getSession().getAttribute("loginMember");
         	int memberNo = loginMember.getMemberNo();
        	
         	int reviewNo = Integer.parseInt(request.getParameter("reviewNo"));
         	int ctgrCd = Integer.parseInt(request.getParameter("categoryCode2"));
         	String reportTitle = request.getParameter("reportTitle");
         	String reportContent = request.getParameter("reportContent");
         	
         	Map<String, Object> map = new HashMap<String, Object>();
         	map.put("memberNo", memberNo);
         	map.put("reviewNo", reviewNo);
         	map.put("ctgrCd", ctgrCd);
         	map.put("reportTitle", reportTitle);
         	map.put("reportContent", reportContent);
         	
         	System.out.println(memberNo);
         	System.out.println(reviewNo);
         	System.out.println(ctgrCd);
         	System.out.println(reportTitle);
         	System.out.println(reportContent);
         	
         	// 리뷰 신고 삽입
         	int result = service.insertReport(map);
         	
         	
         	if(result > 0) {
         		request.getSession().setAttribute("swalIcon", "success");
				request.getSession().setAttribute("swalTitle", "리뷰가 신고가 접수되었습니다.");
				response.sendRedirect(request.getHeader("referer"));
			}else {
				request.getSession().setAttribute("swalIcon", "error");
				request.getSession().setAttribute("swalTitle", "리뷰 신고를 실패했습니다.");
				response.sendRedirect(request.getHeader("referer"));
			}
         }
         
      } catch (Exception e) {
         e.printStackTrace();
      }
   }
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
