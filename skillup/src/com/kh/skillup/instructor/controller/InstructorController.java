package com.kh.skillup.instructor.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.skillup.common.MyFileRenamePolicy;
import com.kh.skillup.instructor.model.service.InstructorService;
import com.kh.skillup.instructor.model.vo.Attachment;
import com.kh.skillup.instructor.model.vo.Instructor;
import com.kh.skillup.instructor.model.vo.PageInfo;
import com.kh.skillup.lesson.model.vo.Lesson;
import com.kh.skillup.member.model.vo.Member;
import com.oreilly.servlet.MultipartRequest;

@WebServlet("/instructor/*")
public class InstructorController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uri = request.getRequestURI();
		String contextPath = request.getContextPath();
		String command = uri.substring((contextPath+"/instructor").length());
		
		String path = null;
		RequestDispatcher view = null;
		
		String swalIcon = null;
		String swalTitle = null;
		String swalText = null;
		
		String errorMsg = null;
		
		try {
			InstructorService service = new InstructorService();
			
			String cp = request.getParameter("cp");
			
			// 전문가 등록 화면 전환 Controller--------------------------
			if(command.equals("/enrollmentForm.do")) {
				
				errorMsg = "전문가 등록 화면 전환 과정에서 오류 발생";
				
				Member loginMember = (Member)request.getSession().getAttribute("loginMember");
				int instrNo = loginMember.getMemberNo();
				
				// 저장 여부 확인 후 저장 정보 불러오기
				int result = service.checkInstr(instrNo);
				
				if(result == 1) {
					Instructor instr = service.updateView(instrNo);
					
					if(instr != null) {
						List<Attachment> fList = service.selectInstrFiles(instrNo);
						
						if(!fList.isEmpty()) {
							request.setAttribute("fList", fList);
					}
				}
				//System.out.println(instr);
					
				request.setAttribute("instr", instr);
				path = "/WEB-INF/views/instructor/instructorEnrollment.jsp";
				view = request.getRequestDispatcher(path);
				view.forward(request, response);
					
				}else {
					//request.setAttribute("instr", instr);
					path = "/WEB-INF/views/instructor/instructorEnrollment.jsp";
					view = request.getRequestDispatcher(path);
					view.forward(request, response);
				}
				
//				path = "/WEB-INF/views/instructor/instructorEnrollment.jsp";
//				view = request.getRequestDispatcher(path);
//				view.forward(request, response);
			}
			
			// 전문가 등록 저장 Controller(+ 파일 업로드)---------------------
			else if(command.equals("/saveEnrollment.do")) {
				errorMsg = "전문가 등록 저장 중 오류 발생.";
				
				int maxSize = 20 * 1024 * 1024;
				
				
				String root = request.getSession().getServletContext().getRealPath("/"); // 실제주소 WebContent가 반환됨
				String filePath = root + "resources/files/";
				// 비즈니스 로직 처리 후 결과 반환 받기
				
				MultipartRequest multiRequest 
				= new MultipartRequest(request, filePath, maxSize, "UTF-8", new MyFileRenamePolicy());
				
				List<Attachment> fList = new ArrayList<Attachment>();
				
				Enumeration<String> files = multiRequest.getFileNames();
				
				while(files.hasMoreElements()) {
					String name = files.nextElement();
					
					if( multiRequest.getFilesystemName(name) != null) {
						
						// Attachment 객체에 파일 정보 저장
						Attachment temp = new Attachment();
						
						temp.setFileName(multiRequest.getFilesystemName(name));
						temp.setFilePath(filePath);
						
						// name 속성에 따라 fileLevel 지정
						int fileLevel = 0;
						switch(name) {
						case "file0" : fileLevel = 0; break;
						case "file1" : fileLevel = 1; break;
						}
						
						temp.setFileLevel(fileLevel);
						
						// fList에 추가
						fList.add(temp);
						System.out.println(fList);
					}
				} // end wile
				
				Member loginMember = (Member)request.getSession().getAttribute("loginMember");
				int memberNo = loginMember.getMemberNo();
				String memberName = loginMember.getMemberName();
				
				int categoryCode = Integer.parseInt(multiRequest.getParameter("categoryCode"));
				String introduceContent = multiRequest.getParameter("introduceContent");
				String careerContent = multiRequest.getParameter("careerContent");
				
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("fList", fList);
				map.put("memberNo", memberNo);
				map.put("memberName", memberName);
				map.put("categoryCode", categoryCode);
				map.put("introduceContent", introduceContent);
				map.put("careerContent", careerContent);
				
				int instrNo = service.insertSaveInstructor(map);
				
				if(instrNo > 0) {
					request.getSession().setAttribute("swalIcon", "success");
					request.getSession().setAttribute("swalTitle", "저장을 완료하였습니다.");
					response.sendRedirect(request.getHeader("referer"));
				}else {
					swalIcon = "error";
					request.getSession().setAttribute("swalIcon", "error");
					request.getSession().setAttribute("swalTitle", "저장을 실패하였습니다.");
					response.sendRedirect("enrollmentForm.do");
				}
			}
			// 전문가 등록 Controller(+ 파일 업로드)---------------------
			else if(command.equals("/enrollment.do")) {
				errorMsg = "전문가 등록 중 오류 발생.";
				
				int maxSize = 20 * 1024 * 1024;
				
				String root = request.getSession().getServletContext().getRealPath("/"); // 실제주소 WebContent가 반환됨
				String filePath = root + "resources/files/";
				// 비즈니스 로직 처리 후 결과 반환 받기
				
				MultipartRequest multiRequest 
				= new MultipartRequest(request, filePath, maxSize, "UTF-8", new MyFileRenamePolicy());
				
				List<Attachment> fList = new ArrayList<Attachment>();
				
				Enumeration<String> files = multiRequest.getFileNames();
				
				while(files.hasMoreElements()) {
					String name = files.nextElement();
					
					if( multiRequest.getFilesystemName(name) != null) {
						
						// Attachment 객체에 파일 정보 저장
						Attachment temp = new Attachment();
						
						temp.setFileName(multiRequest.getFilesystemName(name));
						temp.setFilePath(filePath);
						
						// name 속성에 따라 fileLevel 지정
						int fileLevel = 0;
						switch(name) {
						case "file0" : fileLevel = 0; break;
						case "file1" : fileLevel = 1; break;
						}
						
						temp.setFileLevel(fileLevel);
						
						// fList에 추가
						fList.add(temp);
						//System.out.println(fList);
					}
				} // end wile
			
				Member loginMember = (Member)request.getSession().getAttribute("loginMember");
				int memberNo = loginMember.getMemberNo();
				String memberName = loginMember.getMemberName();
				String memberEmail = loginMember.getMemberEmail();
				
				int categoryCode = Integer.parseInt(multiRequest.getParameter("categoryCode"));
				String introduceContent = multiRequest.getParameter("introduceContent");
				String careerContent = multiRequest.getParameter("careerContent");
				
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("fList", fList);
				map.put("memberNo", memberNo);
				map.put("memberName", memberName);
				map.put("memberEmail", memberEmail);
				map.put("categoryCode", categoryCode);
				map.put("introduceContent", introduceContent);
				map.put("careerContent", careerContent);
				
				int checkInstr = service.checkInstr(memberNo);
				
				if(checkInstr > 0) {
					int result = service.updateEnrollment(map);
					request.getSession().setAttribute("swalIcon", "success");
					request.getSession().setAttribute("swalTitle", "전문가 등록 신청을 완료하였습니다. 관리자의 승인을 기다려주세요. ʕ￫ᴥ￩ʔ ");
					response.sendRedirect(request.getContextPath());
				}else {
					int instrNo = service.insertInstructor(map);
					
					HttpSession session = request.getSession();
					
					if(instrNo > 0) {
						request.getSession().setAttribute("swalIcon", "success");
						request.getSession().setAttribute("swalTitle", "전문가 등록 신청을 완료하였습니다. 관리자의 승인을 기다려주세요. ʕ￫ᴥ￩ʔ ");
						response.sendRedirect(request.getContextPath());
					}else {
						swalIcon = "error";
						request.getSession().setAttribute("swalIcon", "error");
						request.getSession().setAttribute("swalTitle", "전문가 등록 신청이 실패하였습니다.");
						response.sendRedirect("enrollmentForm.do");
					}
				}
			}
			
			// 전문가 신청 목록 조회 Controller -------------------------
			else if(command.equals("/approveList.do")){
				errorMsg = "전문가 신청 목록 조회 중 오류 발생";
				
				PageInfo pInfo = service.getPageInfo(cp);
				
				System.out.println(pInfo);
				
				List<Instructor> iList = service.selectInstrList(pInfo);
				
//				for(Instructor list : iList) {
//					System.out.println(list);
//				}
				
				path = "/WEB-INF/views/instructor/instructorApprove.jsp";
				request.setAttribute("pInfo", pInfo);
				request.setAttribute("iList", iList);
				view = request.getRequestDispatcher(path);
				view.forward(request, response);
			}
			
			// 전문가 신청 목록 상세 조회 Controller -------------------------
			else if(command.equals("/approveView.do")){
				errorMsg = "전문가 신청 목록 상세 조회 중 오류 발생";
				
				int instrNo = Integer.parseInt(request.getParameter("no"));
				
				Instructor instr = service.selectInstr(instrNo);
				
				if(instr != null) {
					
					List<Attachment> fList = service.selectInstrFiles(instrNo);
					
					if(!fList.isEmpty()) {
						request.setAttribute("fList", fList);
					}
					
					for(Attachment list : fList) {
						System.out.println(list);
					}
					
					path = "/WEB-INF/views/instructor/instructorApproveView.jsp";
					request.setAttribute("instr", instr);
					view = request.getRequestDispatcher(path);
					view.forward(request, response);
					
				}else {
					request.getSession().setAttribute("swalIcon", "error");
					request.getSession().setAttribute("swalTitle", "게시글 상세 조회 실패");
					response.sendRedirect("approveList.do?cp=1");
				}
				
			}
			
			// 전문가 승인 Controller ------------------------------------
			else if(command.equals("/approve.do")) {
				errorMsg = "전문가 승인 과정에서 오류 발생";
				
				int instrNo = Integer.parseInt(request.getParameter("no"));
				
				int result = service.updateMemberGrade(instrNo);
				
				if(result > 0) {
					result = 0;
					result = service.updatePermitFl(instrNo);
					
					if(result > 0) {
						request.getSession().setAttribute("swalIcon", "success");
	            		request.getSession().setAttribute("swalTitle", "전문가 승인 성공");
	            		response.sendRedirect(request.getHeader("referer"));
						
					}else {
						request.getSession().setAttribute("swalIcon", "error");
						request.getSession().setAttribute("swalTitle", "전문가 승인 실패");
						response.sendRedirect("approveList.do?cp=1");
					}
				}
				
			}
			
			// 전문가 마이페이지 화면 전환 Controller -------------------------
			else if(command.equals("/myPage.do")){
				errorMsg = "전문가 마이페이지 화면 전환 과정에서 오류 발생";
				
				Member loginMember = (Member)request.getSession().getAttribute("loginMember");
				int instrNo = loginMember.getMemberNo();
				
				Instructor instr = service.updateView(instrNo);
				
				if(instr != null) {
					List<Attachment> fList = service.selectInstrFiles(instrNo);
					
					if(!fList.isEmpty()) {
						request.setAttribute("fList", fList);
					}
					
					request.setAttribute("instr", instr);
					path = "/WEB-INF/views/instructor/instructorMyPage.jsp";
					view = request.getRequestDispatcher(path);
					view.forward(request, response);
					
				}else {
					request.getSession().setAttribute("swalIcon", "error");
					request.getSession().setAttribute("swalTitle", "전문가 마이페이지 화면 전환 실패");
					response.sendRedirect(request.getHeader("referer"));
				}
				
			}
			
			// 전문가 마이페이지 수정 Controller -------------------------
			else if(command.equals("/updateMyPage.do")){
				errorMsg = "전문가 마이페이지 수정 중 오류 발생";
				
				int maxSize = 20 * 1024 * 1024;
				String root = request.getSession().getServletContext().getRealPath("/"); // 실제주소 WebContent가 반환됨
				String filePath = root + "resources/files/";
				// 비즈니스 로직 처리 후 결과 반환 받기
				
				MultipartRequest multiRequest 
				= new MultipartRequest(request, filePath, maxSize, "UTF-8", new MyFileRenamePolicy());
				
				Member loginMember = (Member)request.getSession().getAttribute("loginMember");
				int memberNo = loginMember.getMemberNo();
				String memberName = loginMember.getMemberName();
				String memberEmail = loginMember.getMemberEmail();
				
				int categoryCode = Integer.parseInt(multiRequest.getParameter("categoryCode"));
				String introduceContent = multiRequest.getParameter("introduceContent");
				String careerContent = multiRequest.getParameter("careerContent");
				
				List<Attachment> fList = new ArrayList<Attachment>();
				Enumeration<String> files = multiRequest.getFileNames();
				
				while(files.hasMoreElements()) {
					String name = files.nextElement();
					
					if( multiRequest.getFilesystemName(name) != null) {
						
						// Attachment 객체에 파일 정보 저장
						Attachment temp = new Attachment();
						
						 // 변경된 파일 이름 temp에 저장
	                     temp.setFileName(multiRequest.getFilesystemName(name));
	                     
	                     // 지정한 파일 경로 tmep에 저장
	                     temp.setFilePath(filePath);
	                     
	                     // 해당 게시글 번호 temp에 저장
	                     temp.setParentInstrNo(memberNo);
						
						// fileLevel 지정
						int fileLevel = 0;
						
						temp.setFileLevel(fileLevel);
						
						// fList에 추가
						fList.add(temp);
					}
				} // end wile
				
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("fList", fList);
				map.put("memberNo", memberNo);
				map.put("memberName", memberName);
				map.put("memberEmail", memberEmail);
				map.put("categoryCode", categoryCode);
				map.put("introduceContent", introduceContent);
				map.put("careerContent", careerContent);
				
				int instrNo = service.updateInstructor(map);
				
				HttpSession session = request.getSession();
				
				path = "myPage.do";
				
				if(instrNo > 0) {
					session.setAttribute("instrNo", instrNo);
					swalIcon = "success";
					swalTitle = "전문가 마이페이지 수정을 완료하였습니다. ʕ￫ᴥ￩ʔ ";
				}else {
					swalIcon = "error";
					swalTitle = "전문가 마이페이지 수정을 실패하였습니다.";
				}
				
				request.getSession().setAttribute("swalIcon", swalIcon);
				request.getSession().setAttribute("swalTitle", swalTitle);
				response.sendRedirect(path);
			}
			
			// 전문가 정보 조회 Controller -------------------------
			else if(command.equals("/reperence.do")){
				errorMsg = "전문가 정보 조회 중 오류 발생";
				int instrNo = Integer.parseInt(request.getParameter("no"));
				
				//int instrNo = 1;
				
				Instructor instr = service.selectInstr(instrNo);
				
				if(instr != null) {
					
					List<Attachment> fList = service.selectInstrFiles(instrNo);
					
					//if(!fList.isEmpty()) {
						request.setAttribute("fList", fList);
					
						PageInfo pInfo = service.getLessonPageInfo(cp, instrNo);
						
						//System.out.println(pInfo);
						
						List<Lesson> lessonList = service.selectLessonList(pInfo, instrNo);

						request.setAttribute("lessonList", lessonList);
						
						//System.out.println(lessonList);
						
						if(lessonList != null) {
							List<Attachment> lList = service.selectThumbnailList(pInfo, instrNo);
						
						if(!lList.isEmpty())
							request.setAttribute("lList", lList);
						}
					//}
					
//					for(Attachment list : fList) {
//						System.out.println(list);
//					}
					
					path = "/WEB-INF/views/instructor/instructorReperence.jsp";
					request.setAttribute("instr", instr);
					request.setAttribute("pInfo", pInfo);
					view = request.getRequestDispatcher(path);
					view.forward(request, response);
					
				}else {
					request.getSession().setAttribute("swalIcon", "error");
					request.getSession().setAttribute("swalTitle", "전문가 정보 조회 실패");
					response.sendRedirect(request.getHeader("referer"));
				}
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			path = "/WEB-INF/views/common/errorPage.jsp";
			request.setAttribute("errorMsg", errorMsg);
			view = request.getRequestDispatcher(path);
			view.forward(request, response);
		}	
			
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
