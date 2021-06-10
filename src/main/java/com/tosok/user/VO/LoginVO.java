package com.tosok.user.VO;

import java.util.Date;
import lombok.Data;

@Data
public class LoginVO {

	private String LOGIN_ID;
	private String LOGIN_IP;
	private String LOGIN_BROWSER;
	private String LOGIN_STATUS;
	private String PREV_URL;
	private Date LOGIN_DATE;


}
