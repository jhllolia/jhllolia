package com.tosok.user.VO;

import java.sql.Date;

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

	public int getFILE_SEQ() {
		return FILE_SEQ;
	}

	public void setFILE_SEQ(int fILE_SEQ) {
		FILE_SEQ = fILE_SEQ;
	}

	public String getORD() {
		return ORD;
	}

	public void setORD(String oRD) {
		ORD = oRD;
	}

	public String getPRODUCT_SALE_MODE() {
		return PRODUCT_SALE_MODE;
	}

	public void setPRODUCT_SALE_MODE(String pRODUCT_SALE_MODE) {
		PRODUCT_SALE_MODE = pRODUCT_SALE_MODE;
	}

	public String getPRODUCT_MIN_TIT() {
		return PRODUCT_MIN_TIT;
	}

	public void setPRODUCT_MIN_TIT(String pRODUCT_MIN_TIT) {
		PRODUCT_MIN_TIT = pRODUCT_MIN_TIT;
	}

	public String getPRODUCT_MAX_TIT() {
		return PRODUCT_MAX_TIT;
	}

	public void setPRODUCT_MAX_TIT(String pRODUCT_MAX_TIT) {
		PRODUCT_MAX_TIT = pRODUCT_MAX_TIT;
	}

	public String getPRODUCT_MIN_IMG() {
		return PRODUCT_MIN_IMG;
	}

	public void setPRODUCT_MIN_IMG(String pRODUCT_MIN_IMG) {
		PRODUCT_MIN_IMG = pRODUCT_MIN_IMG;
	}

	public String getPRODUCT_MAX_IMG() {
		return PRODUCT_MAX_IMG;
	}

	public void setPRODUCT_MAX_IMG(String pRODUCT_MAX_IMG) {
		PRODUCT_MAX_IMG = pRODUCT_MAX_IMG;
	}

	public String getREVIEW_EXIST() {
		return REVIEW_EXIST;
	}

	public void setREVIEW_EXIST(String rEVIEW_EXIST) {
		REVIEW_EXIST = rEVIEW_EXIST;
	}

	public int getPRODUCT_SEQ() {
		return PRODUCT_SEQ;
	}

	public void setPRODUCT_SEQ(int pRODUCT_SEQ) {
		PRODUCT_SEQ = pRODUCT_SEQ;
	}

	public String getPRODUCT_STATE() {
		return PRODUCT_STATE;
	}

	public void setPRODUCT_STATE(String pRODUCT_STATE) {
		PRODUCT_STATE = pRODUCT_STATE;
	}

	public String getPRODUCT_INFO() {
		return PRODUCT_INFO;
	}

	public void setPRODUCT_INFO(String pRODUCT_INFO) {
		PRODUCT_INFO = pRODUCT_INFO;
	}

	public String getPRODUCT_SUB_INFO() {
		return PRODUCT_SUB_INFO;
	}

	public void setPRODUCT_SUB_INFO(String pRODUCT_SUB_INFO) {
		PRODUCT_SUB_INFO = pRODUCT_SUB_INFO;
	}

	public String getPRODUCT_SELL() {
		return PRODUCT_SELL;
	}

	public void setPRODUCT_SELL(String pRODUCT_SELL) {
		PRODUCT_SELL = pRODUCT_SELL;
	}

	public String getPRODUCT_TITLE() {
		return PRODUCT_TITLE;
	}

	public void setPRODUCT_TITLE(String pRODUCT_TITLE) {
		PRODUCT_TITLE = pRODUCT_TITLE;
	}

	public String getPRODUCT_CONTENT() {
		return PRODUCT_CONTENT;
	}

	public void setPRODUCT_CONTENT(String pRODUCT_CONTENT) {
		PRODUCT_CONTENT = pRODUCT_CONTENT;
	}

	public String getPRODUCT_SHIPPING() {
		return PRODUCT_SHIPPING;
	}

	public void setPRODUCT_SHIPPING(String pRODUCT_SHIPPING) {
		PRODUCT_SHIPPING = pRODUCT_SHIPPING;
	}

	public String getPRODUCT_ON_SALE() {
		return PRODUCT_ON_SALE;
	}

	public void setPRODUCT_ON_SALE(String pRODUCT_ON_SALE) {
		PRODUCT_ON_SALE = pRODUCT_ON_SALE;
	}

	public String getPRODUCT_UPLOAD() {
		return PRODUCT_UPLOAD;
	}

	public void setPRODUCT_UPLOAD(String pRODUCT_UPLOAD) {
		PRODUCT_UPLOAD = pRODUCT_UPLOAD;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getSale() {
		return sale;
	}

	public void setSale(String sale) {
		this.sale = sale;
	}

	public String getSell() {
		return sell;
	}

	public void setSell(String sell) {
		this.sell = sell;
	}

	public String getPRODUCT_BUYER_SELECT() {
		return PRODUCT_BUYER_SELECT;
	}

	public void setPRODUCT_BUYER_SELECT(String pRODUCT_BUYER_SELECT) {
		PRODUCT_BUYER_SELECT = pRODUCT_BUYER_SELECT;
	}

	public String getPRODUCT_SHIPPING_TXT() {
		return PRODUCT_SHIPPING_TXT;
	}

	public void setPRODUCT_SHIPPING_TXT(String pRODUCT_SHIPPING_TXT) {
		PRODUCT_SHIPPING_TXT = pRODUCT_SHIPPING_TXT;
	}

	public String getPRODUCT_RETURN_TXT() {
		return PRODUCT_RETURN_TXT;
	}

	public void setPRODUCT_RETURN_TXT(String pRODUCT_RETURN_TXT) {
		PRODUCT_RETURN_TXT = pRODUCT_RETURN_TXT;
	}

	public String getPRODUCT_FILE_STATE() {
		return PRODUCT_FILE_STATE;
	}

	public void setPRODUCT_FILE_STATE(String pRODUCT_FILE_STATE) {
		PRODUCT_FILE_STATE = pRODUCT_FILE_STATE;
	}

	public String getPRODUCT_FILE_NAME() {
		return PRODUCT_FILE_NAME;
	}

	public void setPRODUCT_FILE_NAME(String pRODUCT_FILE_NAME) {
		PRODUCT_FILE_NAME = pRODUCT_FILE_NAME;
	}

	public String getPRODUCT_FILE_TITLE() {
		return PRODUCT_FILE_TITLE;
	}

	public void setPRODUCT_FILE_TITLE(String pRODUCT_FILE_TITLE) {
		PRODUCT_FILE_TITLE = pRODUCT_FILE_TITLE;
	}

	public long getPRODUCT_FILE_SIZE() {
		return PRODUCT_FILE_SIZE;
	}

	public void setPRODUCT_FILE_SIZE(long pRODUCT_FILE_SIZE) {
		PRODUCT_FILE_SIZE = pRODUCT_FILE_SIZE;
	}

	public Date getREGISTER_DATE() {
		return REGISTER_DATE;
	}

	public void setREGISTER_DATE(Date rEGISTER_DATE) {
		REGISTER_DATE = rEGISTER_DATE;
	}

}
