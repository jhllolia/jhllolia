package com.tosok.user.Interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class PayInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handle) throws Exception {
		String path = request.getServletPath();
		HttpSession session = request.getSession();

		@SuppressWarnings("unchecked")
    	String arr = (String) request.getParameter("arr");

		if(arr == "" || arr == null) {
			response.sendRedirect(request.getContextPath() + "/bbs/product");  
			return false;  
		} else {
        	session.setAttribute("pay_prev", path);
			return true;
		}
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		super.postHandle(request, response, handler, modelAndView);
	}

}
