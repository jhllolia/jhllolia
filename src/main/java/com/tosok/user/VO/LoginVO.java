package com.tosok.user.VO;

import java.util.Date;

public class LoginVO {

	private String LOGIN_ID;
	private String LOGIN_IP;
	private String LOGIN_BROWSER;
	private String LOGIN_STATUS;
	private String PREV_URL;
	private Date LOGIN_DATE;

	public String getLOGIN_ID() {
		return LOGIN_ID;
	}

	public void setLOGIN_ID(String lOGIN_ID) {
		LOGIN_ID = lOGIN_ID;
	}

	public String getLOGIN_IP() {
		return LOGIN_IP;
	}

	public void setLOGIN_IP(String lOGIN_IP) {
		LOGIN_IP = lOGIN_IP;
	}

	public String getLOGIN_BROWSER() {
		return LOGIN_BROWSER;
	}

	public void setLOGIN_BROWSER(String lOGIN_BROWSER) {
		LOGIN_BROWSER = lOGIN_BROWSER;
	}

	public String getLOGIN_STATUS() {
		return LOGIN_STATUS;
	}

	public void setLOGIN_STATUS(String lOGIN_STATUS) {
		LOGIN_STATUS = lOGIN_STATUS;
	}

	public String getPREV_URL() {
		return PREV_URL;
	}

	public void setPREV_URL(String pREV_URL) {
		PREV_URL = pREV_URL;
	}

	public Date getLOGIN_DATE() {
		return LOGIN_DATE;
	}

	public void setLOGIN_DATE(Date lOGIN_DATE) {
		LOGIN_DATE = lOGIN_DATE;
	}

}
