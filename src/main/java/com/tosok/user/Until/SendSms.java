package com.tosok.user.Until;

import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Component;

import com.tosok.user.VO.MemberVO;

import net.nurigo.java_sdk.api.Message;

@Component("SendSms")
public class SendSms {
	protected Log log = LogFactory.getLog(SendSms.class);

    /* ========== 각 상황별 메세지 전송 ========== */
	public final static String HOST = "0638419582";									// HOST 번호 ( ****** )
	public final static String API_API = "NCS9UXNP07JTQKNW";					// API_API
	public final static String API_SECRET = "3YO8OFCV9OSZ9E1TMJEZKDT1YAZZRBNV";	// API_SECRET

	public void send(MemberVO vo) {
		HashMap<String, String> params = new HashMap<String, String>();	
		Message coolsms = new Message(API_API, API_SECRET);
		String to = vo.getMember_Phone().replace("-", "");

	    params.put("to", to);
	    params.put("from", HOST);
	    params.put("type", "SMS");
	    params.put("text", vo.getMember_Content());

	    try {

	    	JSONObject obj = (JSONObject) coolsms.send(params);
	    	log.info("SendSms : " + obj);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
}
