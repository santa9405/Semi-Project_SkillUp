package com.kh.skillup.message.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.skillup.board.model.vo.PageInfo;
import com.kh.skillup.member.model.vo.Member;
import com.kh.skillup.message.model.service.MessageService;
import com.kh.skillup.message.model.vo.SendMessage;
import com.kh.skillup.message.model.vo.Message;
import com.kh.skillup.message.model.vo.ReceiveMessage;

@WebServlet("/message/*")
public class MessageController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public MessageController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = null;
		String swalIcon = null;
		String swalTitle = null;
		String contextPath = request.getContextPath();
		try {
			MessageService service = new MessageService();
			String page = request.getParameter("page");
			// int loginMember = ((Member)request.getSession().getAttribute("loginMember")).getMemberNo(); -------------------
			int loginMember = ((Member)request.getSession().getAttribute("loginMember")).getMemberNo();
			
			if(request.getRequestURI().equals(contextPath + "/message/rmessageList")) {
				PageInfo pInfo = service.getPageInfo(page, loginMember, 2);
				List<Message> list = service.rmessageList(pInfo, loginMember);
				request.setAttribute("pInfo", pInfo);
				request.setAttribute("list", list);
				path = "/WEB-INF/views/message/messageListR.jsp";
				request.getRequestDispatcher(path).forward(request, response);
			}
			
			else if(request.getRequestURI().equals(contextPath + "/message/smessageList")) {
				PageInfo pInfo = service.getPageInfo(page, loginMember, 1);
				List<Message> list = service.smessageList(pInfo, loginMember);
				request.setAttribute("pInfo", pInfo);
				request.setAttribute("list", list);
				path = "/WEB-INF/views/message/messageListS.jsp";
				request.getRequestDispatcher(path).forward(request, response);
			}
			
			else if(request.getRequestURI().equals(contextPath + "/message/messageView")) {
				int messageNo = Integer.parseInt(request.getParameter("messageNo"));
				int result = service.readStatus(messageNo);
				Message message = new Message();
				message = service.selectMessage(messageNo);
				request.setAttribute("message", message);
				path = "/WEB-INF/views/message/messageView.jsp";
				request.getRequestDispatcher(path).forward(request, response);
			}
			
			else if(request.getRequestURI().equals(contextPath + "/message/messageSend")) {
				String email = request.getParameter("email");
				String memberName = request.getParameter("memberName");
				String memberNo = request.getParameter("memberNo");
				if(email==null || memberName==null) {
					Member memberInfo = new Member();
					memberInfo = service.selectInfo(memberNo);
					email = memberInfo.getMemberEmail();
					memberName = memberInfo.getMemberName();
				}
				request.setAttribute("email", email);
				request.setAttribute("memberName", memberName);
				path = "/WEB-INF/views/message/messageSend.jsp";
				request.getRequestDispatcher(path).forward(request, response);
			}
			
			else if(request.getRequestURI().equals(contextPath + "/message/insert")) {
				
				String content = request.getParameter("content");
				String email = request.getParameter("email");
				// 이메일로 받는사람 번호조회
				int receiveMember = service.selectMember(email);
				// 내용 받는사람 보내는사람
				Message message = new Message();
				message.setReceiveMemberNo(receiveMember);
				message.setSendMemberNo(loginMember);
				message.setContent(content);
				
				// insert
				int result = service.insertMessage(message);
				path = "rmessageList";
				response.sendRedirect(path);
			}
			
			else if(request.getRequestURI().equals(contextPath + "/message/deleteR")) {
				String checkedMsgNo = request.getParameter("checkedMsgNo");
				String[] deleteMsg = checkedMsgNo.split("-");
				int result = service.deleteRMessage(deleteMsg);
				path = "rmessageList";
				request.getRequestDispatcher(path).forward(request, response);
			}
			
			else if(request.getRequestURI().equals(contextPath + "/message/deleteS")) {
				String checkedMsgNo = request.getParameter("checkedMsgNo");
				String[] deleteMsg = checkedMsgNo.split("-");
				int result = service.deleteSMessage(deleteMsg);
				path = "smessageList";
				request.getRequestDispatcher(path).forward(request, response);
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
