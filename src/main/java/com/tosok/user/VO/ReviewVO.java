package com.tosok.user.VO;

import java.util.Date;

import com.google.gson.JsonArray;

public class ReviewVO {

	private int VIEW_SEQ; // 후기 번호
	private String ORDER_NUM; // 테이블 번호
	private String VIEW_STATE; // 후기 상태
	private String VIEW_POINT;
	private String VIEW_TITLE; // 후기 제목
	private String VIEW_CONTENT; // 후기 내용
	private String VIEW_UPLOAD; // 후기 내용
	private String REVIEW_ID;
	private String REVIEW_NAME;

	private int AVG_COUNT;
	private int REV_COUNT;

	private String PRODUCT_NUM;
	private String PRODUCT_NAME;
	private String PRODUCT_OPTION;
	private String PRODUCT_QTY;

	private String VIEW_ARRAY; // 후기 상태 목록

	private String FILE_STATE; // 후기 파일 상태
	private String FILE_TITLE; // 후기 파일 순서
	private String FILE_NAME; // 후기 파일 이름

	private Date VIEW_DATE; // 등록, 수정 일

	public int getAVG_COUNT() {
		return AVG_COUNT;
	}

	public void setAVG_COUNT(int aVG_COUNT) {
		AVG_COUNT = aVG_COUNT;
	}

	public int getREV_COUNT() {
		return REV_COUNT;
	}

	public void setREV_COUNT(int rEV_COUNT) {
		REV_COUNT = rEV_COUNT;
	}

	public String getPRODUCT_NUM() {
		return PRODUCT_NUM;
	}

	public void setPRODUCT_NUM(String pRODUCT_NUM) {
		PRODUCT_NUM = pRODUCT_NUM;
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

	public String getVIEW_UPLOAD() {
		return VIEW_UPLOAD;
	}

	public void setVIEW_UPLOAD(String vIEW_UPLOAD) {
		VIEW_UPLOAD = vIEW_UPLOAD;
	}

	public String getREVIEW_ID() {
		return REVIEW_ID;
	}

	public void setREVIEW_ID(String rEVIEW_ID) {
		REVIEW_ID = rEVIEW_ID;
	}

	public String getREVIEW_NAME() {
		return REVIEW_NAME;
	}

	public void setREVIEW_NAME(String rEVIEW_NAME) {
		REVIEW_NAME = rEVIEW_NAME;
	}

	public int getVIEW_SEQ() {
		return VIEW_SEQ;
	}

	public void setVIEW_SEQ(int vIEW_SEQ) {
		VIEW_SEQ = vIEW_SEQ;
	}

	public String getORDER_NUM() {
		return ORDER_NUM;
	}

	public void setORDER_NUM(String oRDER_NUM) {
		ORDER_NUM = oRDER_NUM;
	}

	public String getVIEW_STATE() {
		return VIEW_STATE;
	}

	public void setVIEW_STATE(String vIEW_STATE) {
		VIEW_STATE = vIEW_STATE;
	}

	public String getVIEW_POINT() {
		return VIEW_POINT;
	}

	public void setVIEW_POINT(String vIEW_POINT) {
		VIEW_POINT = vIEW_POINT;
	}

	public String getVIEW_TITLE() {
		return VIEW_TITLE;
	}

	public void setVIEW_TITLE(String vIEW_TITLE) {
		VIEW_TITLE = vIEW_TITLE;
	}

	public String getVIEW_CONTENT() {
		return VIEW_CONTENT;
	}

	public void setVIEW_CONTENT(String vIEW_CONTENT) {
		VIEW_CONTENT = vIEW_CONTENT;
	}

	public String getVIEW_ARRAY() {
		return VIEW_ARRAY;
	}

	public void setVIEW_ARRAY(String vIEW_ARRAY) {
		VIEW_ARRAY = vIEW_ARRAY;
	}

	public String getFILE_STATE() {
		return FILE_STATE;
	}

	public void setFILE_STATE(String fILE_STATE) {
		FILE_STATE = fILE_STATE;
	}

	public String getFILE_TITLE() {
		return FILE_TITLE;
	}

	public void setFILE_TITLE(String fILE_TITLE) {
		FILE_TITLE = fILE_TITLE;
	}

	public String getFILE_NAME() {
		return FILE_NAME;
	}

	public void setFILE_NAME(String fILE_NAME) {
		FILE_NAME = fILE_NAME;
	}

	public Date getVIEW_DATE() {
		return VIEW_DATE;
	}

	public void setVIEW_DATE(Date vIEW_DATE) {
		VIEW_DATE = vIEW_DATE;
	}

}
