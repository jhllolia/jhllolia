package com.tosok.user.VO;

import java.util.Date;

public class PayVO {

	private int PAY_SEQ; // 결제 번호
	private String ORDER_NUM;
	private String PAID_STATUS; // 결제 상태

	private String LIST_STATE; // 결제 검색 상태
	private String LIST_TERM; // 결제 검색 카테고리
	private String LIST_KEYWORD; // 결제 검색 텍스트

	private String PAID_BEFORE_DATE;
	private String PAID_AFTER_DATE;

	private String PAID_USER_DATE; // 사용자 테이블 이용 일자
	private String PAID_USER_TIME; // 사용자 테이블 이용 시간
	private String PAID_METHOD; // 사용자 결제 방식
	private String PAID_PG_PROVIDER; // 결제 PG사
	private String PAID_RECEIPT; // 결제 영수증
	private String PAID_PG_TID; //

	private String IMP_UID; // 아임포트 결제 고유번호
	private String MERCHANT_UID; // 아임포트 결제 고유번호

	private int VIEW_SEQ; // 후기 번호

	private String TAB_NUM; // 결제 될 테이블 번호
	private String TAB_NAME; // 결제 될 테이블 이름
	private String TAB_CONTENT; // 결제 될 테이블 설명
	private String TAB_PAID; // 결제 될 테이블 이용 금액
	private String TAB_START; // 결제 될 테이블 시작 일
	private String TAB_END; // 결제 될 테이블 종료 일

	private String PRODUCT_NUM;
	private String PRODUCT_PROFILE; // 결제 될 테이블 프로필
	private String PRODUCT_NAME;
	private String PRODUCT_OPTION;
	private String PRODUCT_QTY;
	private String PRODUCT_PRICE;
	private String PRODUCT_SHIPPING_NUM;
	private String PRODUCT_SHIPPING_COURIER;
	private String PRODUCT_SHIPPING_COST;

	private String REVIEW_EXIST;

	private String BUYER_SEQ; // 구매자 번호
	private String BUYER_PAID_STATUS; // 구매자 결제 상태
	private String BUYER_NAME; // 구매자 이름

	private String BUYER_ADDR;
	private String BUYER_POSTCODE;

	private String BUYER_EMAIL; // 구매자 이메일
	private String BUYER_TEL; // 구매자 전화번호
	private String BUYER_MEMO; // 구매자 메모

	private String BUYER_RETURN_STATE;
	private String BUYER_RETURN_CONTENT;

	private Date PAID_DATE; // 결제 일

	private String BUYER_PAID_TOTAL;

	public String getREVIEW_EXIST() {
		return REVIEW_EXIST;
	}

	public void setREVIEW_EXIST(String rEVIEW_EXIST) {
		REVIEW_EXIST = rEVIEW_EXIST;
	}

	public String getBUYER_RETURN_STATE() {
		return BUYER_RETURN_STATE;
	}

	public void setBUYER_RETURN_STATE(String bUYER_RETURN_STATE) {
		BUYER_RETURN_STATE = bUYER_RETURN_STATE;
	}

	public String getBUYER_RETURN_CONTENT() {
		return BUYER_RETURN_CONTENT;
	}

	public void setBUYER_RETURN_CONTENT(String bUYER_RETURN_CONTENT) {
		BUYER_RETURN_CONTENT = bUYER_RETURN_CONTENT;
	}

	public String getPAID_BEFORE_DATE() {
		return PAID_BEFORE_DATE;
	}

	public void setPAID_BEFORE_DATE(String pAID_BEFORE_DATE) {
		PAID_BEFORE_DATE = pAID_BEFORE_DATE;
	}

	public String getPAID_AFTER_DATE() {
		return PAID_AFTER_DATE;
	}

	public void setPAID_AFTER_DATE(String pAID_AFTER_DATE) {
		PAID_AFTER_DATE = pAID_AFTER_DATE;
	}

	public String getPRODUCT_SHIPPING_COURIER() {
		return PRODUCT_SHIPPING_COURIER;
	}

	public void setPRODUCT_SHIPPING_COURIER(String pRODUCT_SHIPPING_COURIER) {
		PRODUCT_SHIPPING_COURIER = pRODUCT_SHIPPING_COURIER;
	}

	public int getPAY_SEQ() {
		return PAY_SEQ;
	}

	public void setPAY_SEQ(int pAY_SEQ) {
		PAY_SEQ = pAY_SEQ;
	}

	public String getORDER_NUM() {
		return ORDER_NUM;
	}

	public void setORDER_NUM(String oRDER_NUM) {
		ORDER_NUM = oRDER_NUM;
	}

	public String getPAID_STATUS() {
		return PAID_STATUS;
	}

	public void setPAID_STATUS(String pAID_STATUS) {
		PAID_STATUS = pAID_STATUS;
	}

	public String getLIST_STATE() {
		return LIST_STATE;
	}

	public void setLIST_STATE(String lIST_STATE) {
		LIST_STATE = lIST_STATE;
	}

	public String getLIST_TERM() {
		return LIST_TERM;
	}

	public void setLIST_TERM(String lIST_TERM) {
		LIST_TERM = lIST_TERM;
	}

	public String getLIST_KEYWORD() {
		return LIST_KEYWORD;
	}

	public void setLIST_KEYWORD(String lIST_KEYWORD) {
		LIST_KEYWORD = lIST_KEYWORD;
	}

	public String getPAID_USER_DATE() {
		return PAID_USER_DATE;
	}

	public void setPAID_USER_DATE(String pAID_USER_DATE) {
		PAID_USER_DATE = pAID_USER_DATE;
	}

	public String getPAID_USER_TIME() {
		return PAID_USER_TIME;
	}

	public void setPAID_USER_TIME(String pAID_USER_TIME) {
		PAID_USER_TIME = pAID_USER_TIME;
	}

	public String getPAID_METHOD() {
		return PAID_METHOD;
	}

	public void setPAID_METHOD(String pAID_METHOD) {
		PAID_METHOD = pAID_METHOD;
	}

	public String getPAID_PG_PROVIDER() {
		return PAID_PG_PROVIDER;
	}

	public void setPAID_PG_PROVIDER(String pAID_PG_PROVIDER) {
		PAID_PG_PROVIDER = pAID_PG_PROVIDER;
	}

	public String getPAID_RECEIPT() {
		return PAID_RECEIPT;
	}

	public void setPAID_RECEIPT(String pAID_RECEIPT) {
		PAID_RECEIPT = pAID_RECEIPT;
	}

	public String getPAID_PG_TID() {
		return PAID_PG_TID;
	}

	public void setPAID_PG_TID(String pAID_PG_TID) {
		PAID_PG_TID = pAID_PG_TID;
	}

	public String getIMP_UID() {
		return IMP_UID;
	}

	public void setIMP_UID(String iMP_UID) {
		IMP_UID = iMP_UID;
	}

	public String getMERCHANT_UID() {
		return MERCHANT_UID;
	}

	public void setMERCHANT_UID(String mERCHANT_UID) {
		MERCHANT_UID = mERCHANT_UID;
	}

	public int getVIEW_SEQ() {
		return VIEW_SEQ;
	}

	public void setVIEW_SEQ(int vIEW_SEQ) {
		VIEW_SEQ = vIEW_SEQ;
	}

	public String getTAB_NUM() {
		return TAB_NUM;
	}

	public void setTAB_NUM(String tAB_NUM) {
		TAB_NUM = tAB_NUM;
	}

	public String getTAB_NAME() {
		return TAB_NAME;
	}

	public void setTAB_NAME(String tAB_NAME) {
		TAB_NAME = tAB_NAME;
	}

	public String getTAB_CONTENT() {
		return TAB_CONTENT;
	}

	public void setTAB_CONTENT(String tAB_CONTENT) {
		TAB_CONTENT = tAB_CONTENT;
	}

	public String getTAB_PAID() {
		return TAB_PAID;
	}

	public void setTAB_PAID(String tAB_PAID) {
		TAB_PAID = tAB_PAID;
	}

	public String getTAB_START() {
		return TAB_START;
	}

	public void setTAB_START(String tAB_START) {
		TAB_START = tAB_START;
	}

	public String getTAB_END() {
		return TAB_END;
	}

	public void setTAB_END(String tAB_END) {
		TAB_END = tAB_END;
	}

	public String getPRODUCT_NUM() {
		return PRODUCT_NUM;
	}

	public void setPRODUCT_NUM(String pRODUCT_NUM) {
		PRODUCT_NUM = pRODUCT_NUM;
	}

	public String getPRODUCT_PROFILE() {
		return PRODUCT_PROFILE;
	}

	public void setPRODUCT_PROFILE(String pRODUCT_PROFILE) {
		PRODUCT_PROFILE = pRODUCT_PROFILE;
	}

	public String getPRODUCT_NAME() {
		return PRODUCT_NAME;
	}

	public void setPRODUCT_NAME(String pRODUCT_NAME) {
		PRODUCT_NAME = pRODUCT_NAME;
	}

	public String getPRODUCT_OPTION() {
		return PRODUCT_OPTION;
	}

	public void setPRODUCT_OPTION(String pRODUCT_OPTION) {
		PRODUCT_OPTION = pRODUCT_OPTION;
	}

	public String getPRODUCT_QTY() {
		return PRODUCT_QTY;
	}

	public void setPRODUCT_QTY(String pRODUCT_QTY) {
		PRODUCT_QTY = pRODUCT_QTY;
	}

	public String getPRODUCT_PRICE() {
		return PRODUCT_PRICE;
	}

	public void setPRODUCT_PRICE(String pRODUCT_PRICE) {
		PRODUCT_PRICE = pRODUCT_PRICE;
	}

	public String getPRODUCT_SHIPPING_NUM() {
		return PRODUCT_SHIPPING_NUM;
	}

	public void setPRODUCT_SHIPPING_NUM(String pRODUCT_SHIPPING_NUM) {
		PRODUCT_SHIPPING_NUM = pRODUCT_SHIPPING_NUM;
	}

	public String getPRODUCT_SHIPPING_COST() {
		return PRODUCT_SHIPPING_COST;
	}

	public void setPRODUCT_SHIPPING_COST(String pRODUCT_SHIPPING_COST) {
		PRODUCT_SHIPPING_COST = pRODUCT_SHIPPING_COST;
	}

	public String getBUYER_SEQ() {
		return BUYER_SEQ;
	}

	public void setBUYER_SEQ(String bUYER_SEQ) {
		BUYER_SEQ = bUYER_SEQ;
	}

	public String getBUYER_PAID_STATUS() {
		return BUYER_PAID_STATUS;
	}

	public void setBUYER_PAID_STATUS(String bUYER_PAID_STATUS) {
		BUYER_PAID_STATUS = bUYER_PAID_STATUS;
	}

	public String getBUYER_NAME() {
		return BUYER_NAME;
	}

	public void setBUYER_NAME(String bUYER_NAME) {
		BUYER_NAME = bUYER_NAME;
	}

	public String getBUYER_ADDR() {
		return BUYER_ADDR;
	}

	public void setBUYER_ADDR(String bUYER_ADDR) {
		BUYER_ADDR = bUYER_ADDR;
	}

	public String getBUYER_POSTCODE() {
		return BUYER_POSTCODE;
	}

	public void setBUYER_POSTCODE(String bUYER_POSTCODE) {
		BUYER_POSTCODE = bUYER_POSTCODE;
	}

	public String getBUYER_EMAIL() {
		return BUYER_EMAIL;
	}

	public void setBUYER_EMAIL(String bUYER_EMAIL) {
		BUYER_EMAIL = bUYER_EMAIL;
	}

	public String getBUYER_TEL() {
		return BUYER_TEL;
	}

	public void setBUYER_TEL(String bUYER_TEL) {
		BUYER_TEL = bUYER_TEL;
	}

	public String getBUYER_MEMO() {
		return BUYER_MEMO;
	}

	public void setBUYER_MEMO(String bUYER_MEMO) {
		BUYER_MEMO = bUYER_MEMO;
	}

	public Date getPAID_DATE() {
		return PAID_DATE;
	}

	public void setPAID_DATE(Date pAID_DATE) {
		PAID_DATE = pAID_DATE;
	}

	public String getBUYER_PAID_TOTAL() {
		return BUYER_PAID_TOTAL;
	}

	public void setBUYER_PAID_TOTAL(String bUYER_PAID_TOTAL) {
		BUYER_PAID_TOTAL = bUYER_PAID_TOTAL;
	}

}
