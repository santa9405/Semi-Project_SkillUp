package com.kh.skillup.lesson.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.Session;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.skillup.instructor.model.service.InstructorService;
import com.kh.skillup.instructor.model.vo.Instructor;
import com.kh.skillup.lesson.model.service.LessonService;
import com.kh.skillup.lesson.model.vo.Attachment;
import com.kh.skillup.lesson.model.vo.Lesson;
import com.kh.skillup.lesson.model.vo.PageInfo;
import com.kh.skillup.likes.model.service.LikesService;
import com.google.gson.Gson;
import com.kh.skillup.common.MyFileRenamePolicy;
import com.kh.skillup.member.model.vo.Member;
import com.oreilly.servlet.MultipartRequest;

@WebServlet("/lesson/*")
public class LessonController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public LessonController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String uri = request.getRequestURI();
		String contextPath = request.getContextPath();
		String command = uri.substring((contextPath + "/lesson").length());
		
		String path = null;
		RequestDispatcher view = null;
		
		String swalIcon = null;
		String swalTitle = null;
		String swalText = null;
		
		String errorMsg = null;
		
		try {
			LessonService service = new LessonService();
			LikesService likesService = new LikesService();
			InstructorService instructorService = new InstructorService();
			
			String cp = request.getParameter("cp");
			
			if(command.equals("/list.do")) {
				errorMsg = "수업 목록 조회 과정에서 오류 발생";
				
				PageInfo pInfo = service.getPageInfo(cp);
				List<Lesson> lessonList = service.selectLessonList(pInfo);

				if(lessonList != null) {
					List<Attachment> fList = service.selectThumbnailList(pInfo);
					
					if(!fList.isEmpty())
						request.setAttribute("fList", fList);
				}
				
				request.setAttribute("lessonList", lessonList);
				request.setAttribute("pInfo", pInfo);
				
				path = "/WEB-INF/views/lesson/lessonList.jsp";
				view = request.getRequestDispatcher(path);
				view.forward(request, response);
			} 
			else if(command.equals("/myList.do")) { // 강사 수업 목록 --------------------------------------------------------------
				errorMsg = "수업 목록 조회 과정에서 오류 발생";
				
				int memberNo = ((Member) request.getSession().getAttribute("loginMember")).getMemberNo();
				
				PageInfo pInfo = service.getMyPageInfo(cp, memberNo);
				List<Lesson> lessonList = service.selectMyLessonList(pInfo, memberNo);

				if(lessonList != null) {
					List<Attachment> fList = service.selectMyThumbnailList(pInfo, memberNo);
					
					if(!fList.isEmpty())
						request.setAttribute("fList", fList);
				}
				
				HttpSession session = request.getSession();
				session.setAttribute("instrMode", "on");
				
				request.setAttribute("lessonList", lessonList);
				request.setAttribute("pInfo", pInfo);
				
				path = "/WEB-INF/views/lesson/myLessonList.jsp";
				view = request.getRequestDispatcher(path);
				view.forward(request, response);
			}
			else if(command.equals("/memberMode.do")) { // 회원 모드 --------------------------------------------------------------
				HttpSession session = request.getSession();
				session.removeAttribute("instrMode");
				
				response.sendRedirect(contextPath + "/");
			}
			else if(command.equals("/view.do")) { 
				errorMsg = "수업 상세 조회 과정에서 오류 발생";
				
				int lessonNo = Integer.parseInt(request.getParameter("no"));
				Lesson lesson = service.selectLesson(lessonNo);
				
				if(request.getSession().getAttribute("loginMember") != null) {
					int memberNo = ((Member) request.getSession().getAttribute("loginMember")).getMemberNo();
					int likes = likesService.selectLikes(lessonNo, memberNo);
					request.setAttribute("likes", likes);
				}
				
				if(lesson != null) {
					List<Attachment> fList = service.selectLessonFiles(lessonNo);
					
					if(!fList.isEmpty()) 
						request.setAttribute("fList", fList);
					
					Instructor instructor = instructorService.updateView(lesson.getInstr());
					
					if(instructor != null) {
						List<com.kh.skillup.instructor.model.vo.Attachment> instrfList = instructorService.selectInstrFiles(lesson.getInstr());
						
						if(!instrfList.isEmpty()) 
							request.setAttribute("instrfList", instrfList);
						
						request.setAttribute("lesson", lesson);
						request.setAttribute("instructor", instructor);
						
						path = "/WEB-INF/views/lesson/lessonView.jsp";
						view = request.getRequestDispatcher(path);
						view.forward(request, response);
					}
				} else {
					request.getSession().setAttribute("swalIcon", "error");
					request.getSession().setAttribute("swalTitle", "수업 상세 조회 실패");
					response.sendRedirect("list.do?cp=1");
				}
			}
			else if(command.equals("/myView.do")) { 
				errorMsg = "수업 상세 조회 과정에서 오류 발생";
				
				int lessonNo = Integer.parseInt(request.getParameter("no"));
				Lesson lesson = service.selectLesson(lessonNo);
				
				if(lesson != null) {
					List<Attachment> fList = service.selectLessonFiles(lessonNo);
					
					if(!fList.isEmpty()) 
						request.setAttribute("fList", fList);
					
					Instructor instructor = instructorService.updateView(lesson.getInstr());
					
					if(instructor != null) {
						List<com.kh.skillup.instructor.model.vo.Attachment> instrfList = instructorService.selectInstrFiles(lesson.getInstr());
						
						if(!instrfList.isEmpty()) 
							request.setAttribute("instrfList", instrfList);
						
						request.setAttribute("lesson", lesson);
						request.setAttribute("instructor", instructor);
						
						path = "/WEB-INF/views/lesson/myLessonView.jsp";
						view = request.getRequestDispatcher(path);
						view.forward(request, response);
					}
				} else {
					request.getSession().setAttribute("swalIcon", "error");
					request.getSession().setAttribute("swalTitle", "수업 상세 조회 실패");
					response.sendRedirect("list.do?cp=1");
				}
			}
			else if(command.equals("/insertForm.do")) { // 수업 등록 -------------------------
				path = "/WEB-INF/views/lesson/lessonInsert.jsp";
				view = request.getRequestDispatcher(path);
				view.forward(request, response);
			}
			else if(command.equals("/insert.do")) {
				errorMsg = "수업 등록 과정에서 오류 발생";
				
				// 이미지
				int maxSize = 20 * 1024 * 1024; 
				String filePath = request.getSession().getServletContext().getRealPath("/") + "resources/uploadImages/lesson/";
				MultipartRequest multiRequest = new MultipartRequest(request, filePath, maxSize, "UTF-8", new MyFileRenamePolicy());
				
				List<Attachment> fList = new ArrayList<Attachment>();
				Enumeration<String> files = multiRequest.getFileNames(); 
				while(files.hasMoreElements()) {
					String name = files.nextElement();
					
					if(multiRequest.getFilesystemName(name) != null) {
						Attachment tmp = new Attachment();
						tmp.setFileName(multiRequest.getFilesystemName(name));
						tmp.setFilePath(filePath);
						tmp.setFileLevel(Integer.parseInt(name.substring(name.length()-1)));
						
						fList.add(tmp);
					}
				}
				
				// 수업
				String lessonTitle = multiRequest.getParameter("lessonTitle");
				String lessonContent = multiRequest.getParameter("lessonContent");
				
				String sido1 = multiRequest.getParameter("sido1");
				String gugun1 = multiRequest.getParameter("gugun1");
				String place3 = multiRequest.getParameter("place3");
				
				char lessonType = multiRequest.getParameter("placeOp").charAt(0);
				
				String place = null;
				if(lessonType == 'F')
					place = sido1 + " " + gugun1 + " " + place3;
				
				int price = Integer.parseInt(multiRequest.getParameter("price"));

				int maxNum = multiRequest.getParameter("maxNum") == null ? 1 : Integer.parseInt(multiRequest.getParameter("maxNum"));
				
				Date lessonDate = Date.valueOf(multiRequest.getParameter("lessonDate"));
				int lessonCnt = Integer.parseInt(multiRequest.getParameter("lessonCnt"));
				int lessonTime = Integer.parseInt(multiRequest.getParameter("lessonTime"));
				
				int categoryCode = Integer.parseInt(multiRequest.getParameter("categoryCode"));
				int instr = ((Member) request.getSession().getAttribute("loginMember")).getMemberNo();
				
				Lesson lesson = new Lesson(lessonTitle, lessonContent, place, price, maxNum, lessonDate, lessonCnt, lessonTime, lessonType);
				
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("fList", fList);
				map.put("lesson", lesson);
				map.put("categoryCode", categoryCode);
				map.put("instr", instr);
				
				int result = service.insertLesson(map);
				
				if(result > 0) {
					swalIcon = "success";
					swalTitle = "수업 등록 성공";
					path = "myView.do?cp=1&no=" + result;
				} else {
					swalIcon = "error";
					swalTitle = "수업 등록 실패";
					path = "list.do";
				}
				request.getSession().setAttribute("swalIcon", swalIcon);
				request.getSession().setAttribute("swalTitle", swalTitle);
				response.sendRedirect(path);
			}
			else if(command.equals("/updateForm.do")) { // 수업 수정 ----------------------------------------
				errorMsg = "수업 수정 화면 전환 과정에서 오류 발생";
				
				int lessonNo = Integer.parseInt(request.getParameter("no"));
				Lesson lesson = service.updateView(lessonNo);
				
				if(lesson != null) {
					List<Attachment> fList = service.selectLessonFiles(lessonNo);
					
					if(!fList.isEmpty())
						request.setAttribute("fList", fList);
					
					request.setAttribute("lesson", lesson);
					path = "/WEB-INF/views/lesson/lessonUpdate.jsp";
					view = request.getRequestDispatcher(path);
					view.forward(request, response);
				} else {
					request.getSession().setAttribute("swalIcon", "error");
					request.getSession().setAttribute("swalTitle", "수업 수정 화면 전환 실패");
					response.sendRedirect(request.getHeader("referer"));
				}
			}
			else if(command.equals("/update.do")) {
				errorMsg = "수업 수정 과정에서 오류 발생";
				
				// 이미지
				int maxSize = 20 * 1024 * 1024; 
				String filePath = request.getSession().getServletContext().getRealPath("/") + "resources/uploadImages/lesson/";
				MultipartRequest multiRequest = new MultipartRequest(request, filePath, maxSize, "UTF-8", new MyFileRenamePolicy());
				
				int lessonNo = Integer.parseInt(multiRequest.getParameter("no"));
				String[] deletedImges = multiRequest.getParameterValues("deletedImges");
				
				List<Attachment> fList = new ArrayList<Attachment>();
				Enumeration<String> files = multiRequest.getFileNames(); 
				while(files.hasMoreElements()) {
					String name = files.nextElement();
					
					if(multiRequest.getFilesystemName(name) != null) {
						Attachment tmp = new Attachment();
						tmp.setFileName(multiRequest.getFilesystemName(name));
						tmp.setFilePath(filePath);
						tmp.setFileLevel(Integer.parseInt(name.substring(name.length()-1)));
						tmp.setParentLessonNo(lessonNo);
						fList.add(tmp);
					}
				}
				
				// 수업
				String lessonTitle = multiRequest.getParameter("lessonTitle");
				String lessonContent = multiRequest.getParameter("lessonContent");
				
				// 장소
				String sido1 = multiRequest.getParameter("sido1");
				String gugun1 = multiRequest.getParameter("gugun1");
				String place3 = multiRequest.getParameter("place3");
				
				char lessonType = multiRequest.getParameter("placeOp").charAt(0);
				
				String place = null;
				if(lessonType == 'F')
					place = sido1 + " " + gugun1 + " " + place3;
				
				int price = Integer.parseInt(multiRequest.getParameter("price"));

				int maxNum = multiRequest.getParameter("maxNum") == null ? 1 : Integer.parseInt(multiRequest.getParameter("maxNum"));
				
				Date lessonDate = Date.valueOf(multiRequest.getParameter("lessonDate"));
				int lessonCnt = Integer.parseInt(multiRequest.getParameter("lessonCnt"));
				int lessonTime = Integer.parseInt(multiRequest.getParameter("lessonTime"));
				
				int categoryCode = Integer.parseInt(multiRequest.getParameter("categoryCode"));
				
				Lesson lesson = new Lesson(lessonTitle, lessonContent, place, price, maxNum, lessonDate, lessonCnt, lessonTime, lessonType);
				lesson.setLessonNo(lessonNo);
				
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("deletedImges", deletedImges);
				map.put("fList", fList);
				map.put("lesson", lesson);
				map.put("categoryCode", categoryCode);
				
				int result = service.updateLesson(map);
				
				path = "myView.do?cp=" + cp + "&no=" + lessonNo;
				
				String sk = multiRequest.getParameter("sk");
				String sv = multiRequest.getParameter("sv");
				
				if(sk != null && sv != null)
					path += "&sk=" + sk + "&sv=" + sv;
				
				if(result > 0) {
					swalIcon = "success";
					swalTitle= "수업 수정 성공";
				} else {
					swalIcon = "error";
					swalTitle= "수업 수정 실패";
				}
				
				request.getSession().setAttribute("swalIcon", swalIcon);
				request.getSession().setAttribute("swalTitle", swalTitle);
				response.sendRedirect(path);
			}
			else if(command.equals("/delete.do")) { // 삭제 ----------------------------------------
				
				int lessonNo = Integer.parseInt(request.getParameter("no"));
				
				int check = service.selectSubjectCnt(lessonNo);
					
				if(check < 1) {
					int result = service.updateLessonFL(lessonNo);
					
					if(result > 0) {
						swalIcon = "success";
						swalTitle= "수업 삭제 성공";
						path = "myList.do";
					} else {
						swalIcon = "error";
						swalTitle= "수업 삭제 실패";
						path = request.getHeader("referer");
					}
				} else {
					swalIcon = "error";
					swalTitle= "수강 중인 회원이 있는 수업은 삭제가 불가능합니다.";
					path = request.getHeader("referer");
				}
				
				request.getSession().setAttribute("swalIcon", swalIcon);
				request.getSession().setAttribute("swalTitle", swalTitle);
				response.sendRedirect(path);
			}
			else if(command.equals("/updateStatus.do")) { 
				
				int lessonNo = Integer.parseInt(request.getParameter("no"));
				int result = service.updateStatus(lessonNo);
				
				if(result > 0) {
					swalIcon = "success";
					swalTitle= "신청 종료 성공";
				} else {
					swalIcon = "error";
					swalTitle= "신청 종료 실패";
				}
				
				request.getSession().setAttribute("swalIcon", swalIcon);
				request.getSession().setAttribute("swalTitle", swalTitle);
				response.sendRedirect(request.getHeader("referer"));
			}
			else if(command.equals("/likesView.do")) { // 좋아요한 수업 목록 --------------------------------------------------------------
				errorMsg = "수업 목록 조회 과정에서 오류 발생";
				
				int memberNo = ((Member) request.getSession().getAttribute("loginMember")).getMemberNo();
				
				PageInfo pInfo = service.getLikesPageInfo(cp, memberNo);
				pInfo.setLimit(4);

				List<Lesson> lessonList = service.selectLikesLesson(pInfo, memberNo);

				if(lessonList != null) {
					List<Attachment> fList = service.selectLikesThumbnailList(pInfo, memberNo);
					
					if(!fList.isEmpty())
						request.setAttribute("fList", fList);
				}
				
				request.setAttribute("lessonList", lessonList);
				request.setAttribute("pInfo", pInfo);
				
				path = "/WEB-INF/views/lesson/likesLessonList.jsp";
				view = request.getRequestDispatcher(path);
				view.forward(request, response);
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
