package com.tosok.user.VO;

import lombok.Data;

@Data
public class CartVO {

	private String CART_SEQ;
	private int CART_ORDER;
	private String CART_STATE;
	private String CART_MEMBER;
	private String CART_PRODUCT_NUM;
	private String CART_PRODUCT_NAME;
	private String CART_PRODUCT_OPTION;
	private String CART_PRODUCT_PRICE;
	private String CART_PRODUCT_SALE;
	private String CART_PRODUCT_SELL;
	private String CART_PRODUCT_QTY;
	private String CART_DATE;

	private String CART_ARRAY;				// 지울 것

	private String PRODUCT_FILE_STATE;
	private String PRODUCT_FILE_TITLE;
	private String PRODUCT_FILE_NAME;

}
