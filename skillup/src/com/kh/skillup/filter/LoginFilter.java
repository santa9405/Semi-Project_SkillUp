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

@WebFilter(urlPatterns = {"/member/myPage.do", "/member/changePwd.do", "/member/changePwdForm.do", "/member/secession.do", 
						"/member/updateMember.do", "/member/updatePwd.do", "/member/updateStatus.do", "/lesson/likesView.do",
						"/instructor/reperence.do", "/instructor/enrollmentForm.do", "/instructor/saveEnrollment.do", "/instructor/enrollment.do"})
public class LoginFilter implements Filter {

    public LoginFilter() {}
	public void destroy() {}
	public void init(FilterConfig fConfig) throws ServletException {}
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		
		HttpSession session = req.getSession();
		Member loginMember = (Member) session.getAttribute("loginMember");
		
		if(loginMember == null) 
			res.sendRedirect(req.getContextPath());
		else 
			chain.doFilter(request, response); 
	}
}
