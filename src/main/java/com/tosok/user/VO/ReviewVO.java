package com.tosok.user.VO;

import java.util.Date;

import com.google.gson.JsonArray;

import lombok.Data;

@Data
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
	private String REVIEW_REPLY;
	private String REVIEW_REPLY_YN;

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

}
