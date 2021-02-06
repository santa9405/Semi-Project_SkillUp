package com.kh.skillup.pay.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.skillup.lesson.model.vo.Lesson;
import com.kh.skillup.pay.model.service.PayService;
import com.kh.skillup.pay.model.vo.Pay;

@WebServlet("/pay/*")
public class PayController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String uri = request.getRequestURI(); // 전체 요청 주소

		String contextPath = request.getContextPath();

		String command = uri.substring((contextPath + "/pay").length());

		String path = null; // forward 또는 redirect경로를 저장할 변수
		RequestDispatcher view = null; // 요청 위임 객체

		String swalIcon = null;
		String swalTitle = null;
		String swalText = null;

		String errorMsg = null; // 에러 메세지 전달용 변수

		try {
			PayService service = new PayService();
			if (command.equals("/payment.do")) {
				errorMsg = "결제 조회중 오류발생.";
				List<Pay> list = service.selectList();

				// 요청위임할 경로 지정
				path = "/WEB-INF/views/pay/payMentHistory.jsp";
				request.setAttribute("list", list);
				// 요청 위임 객체 생성 후 위임 진행
				view = request.getRequestDispatcher(path);
				view.forward(request, response);

			}

			else if (command.equals("/payclasshistory.do")) {
				errorMsg = "결제 목록조회중 오류.";
				List<Lesson> list = service.selectClassList();
				// 요청을 위임할 jsp 경로 지정
				path = "/WEB-INF/views/pay/payClassHistory.jsp";
				request.setAttribute("list", list);
				// 요청 위임 객체 생성 후 위임 진행
				view = request.getRequestDispatcher(path);
				view.forward(request, response);
				// System.out.println(list);
			}

			else if (command.equals("/myorder.do")) {
				errorMsg = "결제 주문하기 상세 조회중 오류.";

				int orderNo = Integer.parseInt(request.getParameter("no"));

				Lesson lesson = service.selectOrder(orderNo);

				if (lesson != null) {
					path = "/WEB-INF/views/pay/myOrder.jsp";
					request.setAttribute("order", lesson);

					view = request.getRequestDispatcher(path);
					view.forward(request, response);

				} else {
					HttpSession session = request.getSession();
					session.setAttribute("swalIon", "error");
					session.setAttribute("swalTitle", "상세결제 주문 조회중 오류 발생");
					response.sendRedirect(request.getHeader("referer"));
				}

			}

			else if (command.equals("/paycheck.do")) {
				errorMsg = "공지사항 목록 조회 중 오류 발생.";
				int CheckOrder = Integer.parseInt(request.getParameter("no"));

				Pay check = service.CheckOrder(CheckOrder);
				
				if (check != null) {
					path = "/WEB-INF/views/pay/payCheck.jsp";

					request.setAttribute("ck", check);

					view = request.getRequestDispatcher(path);
					view.forward(request, response);
					
				} else {
					HttpSession session = request.getSession();
					session.setAttribute("swalIon", "error");
					session.setAttribute("swalTitle", "결제 확인 오류 발생");
					response.sendRedirect(request.getHeader("referer"));
				}

			}

			else if (command.equals("/delete.do")) {
				errorMsg = "공지사항 목록 조회 중 오류 발생.";
				int payNo = Integer.parseInt(request.getParameter("no"));
				System.out.println(payNo);

				int result = service.updatePayFl(payNo);
				System.out.println(result);
				if (result > 0) {
					swalIcon = "success";
					swalTitle = "결제 삭제 되었습니다.";
					path = "payment.do";
				} else {
					
					path = request.getHeader("referer");
				}
				request.getSession().setAttribute("swalIcon", swalIcon);
				request.getSession().setAttribute("swalTitle", swalTitle);
				response.sendRedirect(path);
			}

		} catch (Exception e) {
			e.printStackTrace();
			path = "/WEB-INF/views/common/errorPage.jsp";
			request.setAttribute("errorMsg", errorMsg);
			view = request.getRequestDispatcher(path);
			view.forward(request, response);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
