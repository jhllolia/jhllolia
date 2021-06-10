package com.tosok.user.VO;

import java.util.Date;

import lombok.Data;

@Data
public class QnaVO {

	private int INTSEQ;
	private int MEMBER_SEQ;
	private int INTSEQ_RE_REF;
	private int PRODUCT_SEQ;

	private String PRODUCT_PROFILE;
	private String PRODUCT_NAME;

	private String MEMBER_ID;
	private String MEMBER_QNA_STATE;
	private String MEMBER_QNA_RESULT;
	private String MEMBER_QNA_CAT;
	private String MEMBER_QNA_TITLE;
	private String MEMBER_QNA_CONTENT;
	private String MEMBER_QNA_PHONE;

	private String ADMIN_QNA_TITLE;
	private String ADMIN_QNA_CONTENT;

	private Date MEMBER_QNA_DATE;
	private Date ADMIN_QNA_DATE;

}
