package com.tosok.user.VO;

import java.sql.Date;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import lombok.Data;

@Data
public class MemberVO {

	@NotEmpty
	private int member_Seq; // 회원 번호

	@NotBlank(message = "Not Empty")
	private String member_Id; // 회원 아이디 (이메일)
	private String member_Pw; // 회원 비밀번호
	private String member_Way; // 회원 가입 방식 ( ex. 카카오 / 네이버 )
	private String member_Profile; // 회원 프로필
	private String member_Name; // 회원 이름
	private String member_Subject; //
	private String member_Content; //
	private String member_Phone; // 회원 핸드폰 번호
	private String member_Zip;
	private String member_Addr1;
	private String member_Addr2;
	private String member_authkey; // 회원 인증 여부

	@NotEmpty
	private int member_authstatus; // 회원 인증 키

	private String member_Inquiry;

	private String member_Cart;

	private String LIST_STATE; // 사용자 결제 정보
	private String LIST_KEYWORD; // 사용자 검색 키워드

	private int MAIN_MEETING; // 전체 사용자 테이블
	private int MAIN_MEMBER; // 전체 사용자 인원
	private int MAIN_EVENT; // 전체 결제 인원

	private int member_Apply_Count; // 사용자 결제 횟수
	private int member_Payment_Count; // 사용자 총 결제 금액
	private int memeber_login_Count;

	private Date register_Date; // 등록 일
	private Date update_Date; // 수정 일

}
