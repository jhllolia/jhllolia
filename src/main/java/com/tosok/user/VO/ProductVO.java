package com.tosok.user.VO;

import java.sql.Date;

import lombok.Data;

@Data
public class ProductVO {

	private int PRODUCT_SEQ;
	private String PRODUCT_STATE;
	private String PRODUCT_SALE_MODE;
	private String PRODUCT_INFO;
	private String PRODUCT_SUB_INFO;
	private String PRODUCT_SELL;
	private String PRODUCT_TITLE;
	private String PRODUCT_CONTENT;
	private String PRODUCT_SHIPPING;
	private String PRODUCT_ON_SALE;
	private String PRODUCT_UPLOAD;

	private String num;
	private String name;
	private String option;
	private String price;
	private String sale;
	private String sell;

	private String PRODUCT_BUYER_SELECT;
	private String PRODUCT_SHIPPING_TXT;
	private String PRODUCT_RETURN_TXT;

	private int FILE_SEQ;

	private String REVIEW_EXIST;

	private String PRODUCT_MIN_TIT;
	private String PRODUCT_MIN_IMG;
	private String PRODUCT_MAX_TIT;
	private String PRODUCT_MAX_IMG;

	private String PRODUCT_FILE_STATE;
	private String PRODUCT_FILE_NAME;
	private String PRODUCT_FILE_TITLE;
	private long PRODUCT_FILE_SIZE;
	private String ORD;

	private Date REGISTER_DATE;

}
