package com.tosok.user.Interceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.tosok.user.DAO.ReserveDAO;
import com.tosok.user.VO.ProductVO;

@Component("ProductInterceptor")
public class ProductInterceptor extends HandlerInterceptorAdapter {

	@Resource(name = "ReserveDAO")
	private ReserveDAO reserveDao;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handle) throws Exception {
		ProductVO vo = new ProductVO();
		String path = request.getServletPath();
		String[] idx = path.split("/");

		if(idx[2] == "" || idx[2] == null) {
			response.sendRedirect(request.getContextPath() + "/bbs/product");
			return false;  
		} else {

			vo.setPRODUCT_SEQ(Integer.parseInt(idx[2]));
			vo.setPRODUCT_STATE("Y");
			
			int result = reserveDao.selectProductValid(vo);
			
			if(result > 0) {
				return true;
			} else {
				response.sendRedirect(request.getContextPath() + "/bbs/product");  
				return false;  
			}
		}
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		super.postHandle(request, response, handler, modelAndView);
	}
}
