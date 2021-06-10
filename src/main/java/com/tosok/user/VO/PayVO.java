package com.tosok.user.VO;

import java.util.Date;

import lombok.Data;

@Data
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

}
