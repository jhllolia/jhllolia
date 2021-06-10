package com.tosok.user.VO;

import java.util.Date;

import lombok.Data;

@Data
public class VisitCountVO {
	private int visit_id;
	private String visit_ip;
	private Date visit_time;
	private String visit_refer;
	private String visit_agent;
	private String visit_product;

}
