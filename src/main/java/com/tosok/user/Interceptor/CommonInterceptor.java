package com.tosok.user.Interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component("CommonInterceptor")
public class CommonInterceptor extends HandlerInterceptorAdapter {

	@SuppressWarnings("unchecked")
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handle) throws Exception {
		response.setCharacterEncoding("UTF-8");

		String reqUrl = request.getRequestURL().toString();

		// 2021.05.04 추가
		if(reqUrl.startsWith("http://") && reqUrl.indexOf("localhost") < 0) {
			reqUrl = reqUrl.replaceAll("http://", "https://");
/*
			try {
				response.sendRedirect(reqUrl);
			} catch (Exception e) {
				e.printStackTrace();
			}
*/
		}

		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		super.postHandle(request, response, handler, modelAndView);
	}
}
