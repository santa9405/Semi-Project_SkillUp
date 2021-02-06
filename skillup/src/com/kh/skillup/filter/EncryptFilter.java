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
import com.kh.skillup.wrapper.EncryptWrapper;

//@WebFilter(urlPatterns = {"/member/login.do", "/member/signUp.do", "/member/updatePwd.do", "/member/updateStatus.do"})
public class EncryptFilter implements Filter {

    public EncryptFilter() {}
    public void init(FilterConfig fConfig) throws ServletException {}
	public void destroy() {}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		EncryptWrapper encWrapper = new EncryptWrapper(req);
		
		chain.doFilter(encWrapper, response);
	}
}