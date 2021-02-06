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

@WebFilter(urlPatterns= {"/instructor/approveView.do", "/instructor/approve.do", "/instructor/approveList.do"})
public class AdminFilter implements Filter {

    public AdminFilter() {}
	public void destroy() {}
	public void init(FilterConfig fConfig) throws ServletException {}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		
		HttpSession session = req.getSession();
		Member loginMember = (Member) session.getAttribute("loginMember");
		
		if(loginMember == null || !loginMember.getMemberGrade().equals("A"))
			res.sendRedirect(req.getContextPath());
		else
			chain.doFilter(request, response);
	}
}
