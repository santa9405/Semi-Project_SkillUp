package com.kh.skillup.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.kh.skillup.member.model.vo.Member;

@WebFilter(urlPatterns= {"/instructor/myPage.do", "/instructor/updateMyPage.do",
		"/lesson/myView.do", "/lesson/insertForm.do", "/lesson/updateForm.do", "/lesson/update.do", "/lesson/myList.do", "/lesson/delete.do"})
public class InstructorFilter implements Filter {

    public InstructorFilter() {}
	public void destroy() {}
	public void init(FilterConfig fConfig) throws ServletException {}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		
		HttpSession session = req.getSession();
		Member loginMember = (Member) session.getAttribute("loginMember");
		
		if(loginMember == null || !loginMember.getMemberGrade().equals("I"))
			res.sendRedirect(req.getContextPath());
		else
			chain.doFilter(request, response);
	}
}
