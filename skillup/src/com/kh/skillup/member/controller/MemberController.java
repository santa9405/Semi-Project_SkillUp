package com.kh.skillup.member.controller;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.skillup.member.model.service.MemberService;
import com.kh.skillup.member.model.vo.Member;

@WebServlet("/member/*")
public class MemberController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public MemberController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String uri = request.getRequestURI();
		String contextPath = request.getContextPath();
		String command = uri.substring((contextPath + "/member").length());
		
		String path = null;
		RequestDispatcher view = null;
		
		String swalIcon = null;
		String swalTitle = null;
		String swalText = null;
		
		String errorMsg = null;
		
		try {
			MemberService service = new MemberService();
			
			if(command.equals("/emailDupCheck.do")) { // 회원가입 -------------------------
				String email = request.getParameter("email");
				
				int result = service.emailDupCheck(email);
				response.getWriter().print(result);
			}
			else if(command.equals("/signUpForm.do")) { 
				path = "/WEB-INF/views/member/signUp.jsp";
				view = request.getRequestDispatcher(path);
				view.forward(request, response);
			} 
			else if(command.equals("/signUp.do")) {
				String memberName = request.getParameter("name");
				String memberEmail = request.getParameter("email");
				String password = request.getParameter("pwd");
				
				String phone1 = request.getParameter("phone1");
				String phone2 = request.getParameter("phone2");
				String phone3 = request.getParameter("phone3");
				String phone = phone1 + "-" + phone2 + "-" + phone3;

				String[] interestArr = request.getParameterValues("interest");
				String interest = null;
				if(interestArr != null) 
					interest = String.join(",", interestArr);
				
				Member member = new Member(memberEmail, password, memberName, phone, interest);
				
				int result = service.signUp(member);
				
				if(result > 0) {
					swalIcon = "success";
					swalTitle = "회원가입 성공";
				} else {
					swalIcon = "error";
					swalTitle = "회원가입 실패";
				}
				
				HttpSession session = request.getSession();
				session.setAttribute("swalIcon", swalIcon);
				session.setAttribute("swalTitle", swalTitle);
				session.setAttribute("swalText", swalText);
				
				response.sendRedirect(request.getContextPath());
			}
			else if(command.equals("/login.do")) { // 로그인 -------------------------
				String memberEmail = request.getParameter("inputEmail");
				String password = request.getParameter("inputPwd");
				String save = request.getParameter("save"); 
				
				Member member = new Member();
				member.setMemberEmail(memberEmail);
				member.setPassword(password);
				
				Member loginMember = service.login(member);
				
				response.setContentType("text/html; charset=UTF-8");

				HttpSession session = request.getSession();
				
				if(loginMember != null) { 
					session.setMaxInactiveInterval(60 * 30); 
					session.setAttribute("loginMember", loginMember); 
					
					Cookie cookie = new Cookie("saveId", memberEmail);
					
					if(save != null) 
						cookie.setMaxAge(60 * 60 * 24 * 7);
					 else 
						cookie.setMaxAge(0);
					
					cookie.setPath(request.getContextPath());
					response.addCookie(cookie);
					
				} else {
					session.setAttribute("swalIcon", "error");
					session.setAttribute("swalTitle", "로그인 실패");
					session.setAttribute("swalText", "아이디 또는 비밀번호를 확인해주세요");
				}
				
				response.sendRedirect(request.getHeader("referer"));
			}
			else if(command.equals("/logout.do")) {
				request.getSession().invalidate();
				response.sendRedirect(request.getHeader("referer"));
			}
			else if(command.equals("/findPwdForm.do")) {
				path = "/WEB-INF/views/member/findPwd.jsp";
				view = request.getRequestDispatcher(path);
				view.forward(request, response);
			} 
			else if(command.equals("/findPwd.do")) {
				String email = request.getParameter("email");
				String pwd = request.getParameter("pwd");
				
				Member member = new Member();
				member.setMemberEmail(email);				
				member.setPassword(pwd);				
				
				int result = service.findPwd(member);
				
				if(result > 0) { 
					swalIcon = "success";
					swalTitle = "비밀번호가 변경되었습니다";
				} else if (result == 0){ 
					swalIcon = "error";
					swalTitle = "비밀번호 변경에 실패했습니다";
				} else { 
					swalIcon = "warning";
					swalTitle = "현재 비밀번호가 일치하지 않습니다";
				}
					
				request.getSession().setAttribute("swalIcon", swalIcon);
				request.getSession().setAttribute("swalTitle", swalTitle);
				
				response.sendRedirect(request.getContextPath());
			} 
			else if(command.equals("/myPage.do")) { // 마이페이지 ---------------------------
				path = "/WEB-INF/views/member/myPage.jsp";
				view = request.getRequestDispatcher(path);
				view.forward(request, response);
			} 
			else if(command.equals("/updateMember.do")) {
				String phone1 = request.getParameter("phone1");
				String phone2 = request.getParameter("phone2");
				String phone3 = request.getParameter("phone3");
				String phone = phone1 + "-" + phone2 + "-" + phone3;

				String[] interestArr = request.getParameterValues("interest");
				String interest = null;
				if(interestArr != null)
					interest = String.join(",", interestArr);
				
				HttpSession session = request.getSession();
				Member loginMember = (Member) session.getAttribute("loginMember");
				
				Member member = new Member();
				member.setMemberNo(loginMember.getMemberNo());
				member.setPhone(phone);
				member.setInterest(interest);

				int result = service.updateMember(member);
				
				if(result > 0) {
					member.setMemberEmail(loginMember.getMemberEmail());
					member.setMemberName(loginMember.getMemberName());
					member.setMemberGrade(loginMember.getMemberGrade());

					session.setAttribute("loginMember", member);
					
					swalIcon = "success";
					swalTitle = "회원 정보 수정 성공";
					swalText = "회원 정보가 수정되었습니다";
				} else {
					swalIcon = "error";
					swalTitle = "회원 정보 수정 실패";
					swalText = "고객센터로 문의 바랍니다";
				}
					
				session.setAttribute("swalIcon", swalIcon);
				session.setAttribute("swalTitle", swalTitle);
				session.setAttribute("swalText", swalText);
				
				response.sendRedirect(request.getHeader("referer"));
			}
			else if(command.equals("/changePwdForm.do")) {
				path = "/WEB-INF/views/member/changePwd.jsp";
				view = request.getRequestDispatcher(path);
				view.forward(request, response);
			} 
			else if(command.equals("/updatePwd.do")) { 
				String currentPwd = request.getParameter("currentPwd");
				String newPwd = request.getParameter("newPwd");
				
				HttpSession session = request.getSession();
				Member loginMember = (Member)session.getAttribute("loginMember");
				loginMember.setPassword(currentPwd);				
				
				int result = service.updatePwd(loginMember, newPwd);
				
				if(result > 0) { 
					swalIcon = "success";
					swalTitle = "비밀번호가 변경되었습니다";
				} else if (result == 0){ 
					swalIcon = "error";
					swalTitle = "비밀번호 변경에 실패했습니다";
				} else { 
					swalIcon = "warning";
					swalTitle = "현재 비밀번호가 일치하지 않습니다";
				}
					
				session.setAttribute("swalIcon", swalIcon);
				session.setAttribute("swalTitle", swalTitle);
				
				response.sendRedirect("myPage.do");
			}
			else if(command.equals("/secession.do")) {
				path = "/WEB-INF/views/member/secession.jsp";
				view = request.getRequestDispatcher(path);
				view.forward(request, response);
			}
			else if(command.equals("/updateStatus.do")) {
				String currentPwd = request.getParameter("currentPwd");
				
				HttpSession session = request.getSession();
				Member loginMember = (Member) session.getAttribute("loginMember");
				loginMember.setPassword(currentPwd);
				
				int result = service.updateStatus(loginMember);
				
				String url = null;
				
				if(result > 0) {
					swalIcon = "success";
					swalTitle = "탈퇴되었습니다";
					
					session.invalidate();
					session = request.getSession();
					url = request.getContextPath();
					
				} else if (result == 0){ 
					swalIcon = "error";
					swalTitle = "탈퇴 실패했습니다";
					url = "secession.do";
				} else {
					swalIcon = "warning";
					swalTitle = "비밀번호가 일치하지 않습니다";
					url = "secession.do";
				}
				
				session.setAttribute("swalIcon", swalIcon);
				session.setAttribute("swalTitle", swalTitle);
				response.sendRedirect(url);
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
