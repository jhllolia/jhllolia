package com.tosok.user.Interceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.tosok.user.DAO.ReserveDAO;
import com.tosok.user.DAO.VisitCountDAO;
import com.tosok.user.VO.ProductVO;
import com.tosok.user.VO.VisitCountVO;

@Component("ProductInterceptor")
public class ProductInterceptor extends HandlerInterceptorAdapter {

	@Resource(name = "ReserveDAO")
	private ReserveDAO reserveDao;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handle) throws Exception {
		HttpSession session = request.getSession();
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
				/* ============= 2020.12.16 추가 ============= */
				WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(session.getServletContext());
		        VisitCountDAO dao = (VisitCountDAO) wac.getBean("visitCountDAO");
	        	VisitCountVO param = new VisitCountVO();

	        	param.setVisit_ip((String) request.getSession().getAttribute("member_Id"));
	        	param.setVisit_agent(request.getHeader("User-Agent"));	// 브라우저 정보
	        	param.setVisit_refer(request.getHeader("referer"));		// 접속 전 사이트 정보
	        	param.setVisit_product(idx[2]);

				dao.insertVisitor(param);
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
