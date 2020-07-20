package com.tosok.user.VO;

import java.sql.Date;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

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

	public int getMemeber_login_Count() {
		return memeber_login_Count;
	}

	public void setMemeber_login_Count(int memeber_login_Count) {
		this.memeber_login_Count = memeber_login_Count;
	}

	public String getMember_Subject() {
		return member_Subject;
	}

	public void setMember_Subject(String member_Subject) {
		this.member_Subject = member_Subject;
	}

	public String getMember_Inquiry() {
		return member_Inquiry;
	}

	public void setMember_Inquiry(String member_Inquiry) {
		this.member_Inquiry = member_Inquiry;
	}

	public int getMember_Seq() {
		return member_Seq;
	}

	public void setMember_Seq(int member_Seq) {
		this.member_Seq = member_Seq;
	}

	public String getMember_Id() {
		return member_Id;
	}

	public void setMember_Id(String member_Id) {
		this.member_Id = member_Id;
	}

	public String getMember_Pw() {
		return member_Pw;
	}

	public void setMember_Pw(String member_Pw) {
		this.member_Pw = member_Pw;
	}

	public String getMember_Way() {
		return member_Way;
	}

	public void setMember_Way(String member_Way) {
		this.member_Way = member_Way;
	}

	public String getMember_Profile() {
		return member_Profile;
	}

	public void setMember_Profile(String member_Profile) {
		this.member_Profile = member_Profile;
	}

	public String getMember_Name() {
		return member_Name;
	}

	public void setMember_Name(String member_Name) {
		this.member_Name = member_Name;
	}

	public String getMember_Content() {
		return member_Content;
	}

	public void setMember_Content(String member_Content) {
		this.member_Content = member_Content;
	}

	public String getMember_Phone() {
		return member_Phone;
	}

	public void setMember_Phone(String member_Phone) {
		this.member_Phone = member_Phone;
	}

	public String getMember_Zip() {
		return member_Zip;
	}

	public void setMember_Zip(String member_Zip) {
		this.member_Zip = member_Zip;
	}

	public String getMember_Addr1() {
		return member_Addr1;
	}

	public void setMember_Addr1(String member_Addr1) {
		this.member_Addr1 = member_Addr1;
	}

	public String getMember_Addr2() {
		return member_Addr2;
	}

	public void setMember_Addr2(String member_Addr2) {
		this.member_Addr2 = member_Addr2;
	}

	public String getMember_authkey() {
		return member_authkey;
	}

	public void setMember_authkey(String member_authkey) {
		this.member_authkey = member_authkey;
	}

	public int getMember_authstatus() {
		return member_authstatus;
	}

	public void setMember_authstatus(int member_authstatus) {
		this.member_authstatus = member_authstatus;
	}

	public String getMember_Cart() {
		return member_Cart;
	}

	public void setMember_Cart(String member_Cart) {
		this.member_Cart = member_Cart;
	}

	public String getLIST_STATE() {
		return LIST_STATE;
	}

	public void setLIST_STATE(String lIST_STATE) {
		LIST_STATE = lIST_STATE;
	}

	public String getLIST_KEYWORD() {
		return LIST_KEYWORD;
	}

	public void setLIST_KEYWORD(String lIST_KEYWORD) {
		LIST_KEYWORD = lIST_KEYWORD;
	}

	public int getMAIN_MEETING() {
		return MAIN_MEETING;
	}

	public void setMAIN_MEETING(int mAIN_MEETING) {
		MAIN_MEETING = mAIN_MEETING;
	}

	public int getMAIN_MEMBER() {
		return MAIN_MEMBER;
	}

	public void setMAIN_MEMBER(int mAIN_MEMBER) {
		MAIN_MEMBER = mAIN_MEMBER;
	}

	public int getMAIN_EVENT() {
		return MAIN_EVENT;
	}

	public void setMAIN_EVENT(int mAIN_EVENT) {
		MAIN_EVENT = mAIN_EVENT;
	}

	public int getMember_Apply_Count() {
		return member_Apply_Count;
	}

	public void setMember_Apply_Count(int member_Apply_Count) {
		this.member_Apply_Count = member_Apply_Count;
	}

	public int getMember_Payment_Count() {
		return member_Payment_Count;
	}

	public void setMember_Payment_Count(int member_Payment_Count) {
		this.member_Payment_Count = member_Payment_Count;
	}

	public Date getRegister_Date() {
		return register_Date;
	}

	public void setRegister_Date(Date register_Date) {
		this.register_Date = register_Date;
	}

	public Date getUpdate_Date() {
		return update_Date;
	}

	public void setUpdate_Date(Date update_Date) {
		this.update_Date = update_Date;
	}

}
