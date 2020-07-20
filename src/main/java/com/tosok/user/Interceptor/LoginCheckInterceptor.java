package com.tosok.user.Interceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.tosok.user.DAO.MemberDAO;
import com.tosok.user.VO.LoginVO;

@Component("LoginCheckInterceptor")
public class LoginCheckInterceptor extends HandlerInterceptorAdapter {
	protected Log log = LogFactory.getLog(LoginCheckInterceptor.class);

	@Resource(name = "MemberDAO")
	private MemberDAO memberDao;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handle) throws Exception {
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		HttpServletRequest req = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		LoginVO vo = new LoginVO();

        String id = (String) request.getSession().getAttribute("member_Id");
        String status = (String) request.getSession().getAttribute("login_status");
        String prev_url = (String) request.getSession().getAttribute("prevURL");

        try {

        	String ip = req.getHeader("X-Forwarded-For");

        	if(ip == null) {
        		ip = req.getRemoteAddr();
        	}
        	
        	/*

        	log.info("login_success : " + id);
        	log.info("ip : " + ip);
        	log.info("browser : " + req.getHeader("User-Agent"));
        	log.info("status : " + status);
        	log.info("prev_url : " + prev_url);

        	*/

        	vo.setLOGIN_ID(id);
        	vo.setLOGIN_IP(ip);
        	vo.setLOGIN_STATUS(status);
        	vo.setLOGIN_BROWSER(req.getHeader("User-Agent"));
        	vo.setPREV_URL(prev_url);

        	memberDao.insertLoginData(vo);
		} catch (Exception e) {
			log.error("login_fail : " + e + "");
		}
		
		super.postHandle(request, response, handler, modelAndView);
	}
}
