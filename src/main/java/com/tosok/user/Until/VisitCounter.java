package com.tosok.user.Until;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.tosok.user.DAO.VisitCountDAO;
import com.tosok.user.VO.VisitCountVO;

public class VisitCounter implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent arg0) {

        HttpSession session = arg0.getSession();
        WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(session.getServletContext());
        HttpServletRequest req = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();

        try {

        	VisitCountDAO dao = (VisitCountDAO) wac.getBean("visitCountDAO");
        	VisitCountVO vo = new VisitCountVO();
        	String ip = "";

        	if(ip == null) {
        		ip = req.getRemoteAddr();
        	} else {
        		ip = req.getHeader("X-Forwarded-For");
        	}

            vo.setVisit_ip(ip);
            vo.setVisit_agent(req.getHeader("User-Agent"));	// 브라우저 정보
            vo.setVisit_refer(req.getHeader("referer"));	// 접속 전 사이트 정보

        	dao.insertVisitor(vo);

        	if(arg0.getSession().isNew()) {
        		execute(dao, session);
        	}
        } catch (Exception e) {
			e.printStackTrace();
		}
    }

    private void execute(VisitCountDAO dao, HttpSession session) {

        int todayCount = 0;
        int totalCount = 0;
    	
    	try {
    		totalCount = dao.getVisitTotalCount();
    		todayCount = dao.getVisitTodayCount();
		} catch (Exception e) {
			e.printStackTrace();
		}

		session.setAttribute("totalCount", totalCount);
		session.setAttribute("todayCount", todayCount); 
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent arg0) {

    }

}
