package com.tosok.user.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.mail.HtmlEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.tosok.user.DAO.MemberDAO;
import com.tosok.user.Until.SearchCriteria;
import com.tosok.user.Upload.ReviewFileInsert;
import com.tosok.user.VO.CartVO;
import com.tosok.user.VO.MemberVO;
import com.tosok.user.VO.PayVO;
import com.tosok.user.VO.ProductVO;
import com.tosok.user.VO.QnaVO;
import com.tosok.user.VO.ReviewVO;
import com.tosok.user.mail.TempKey;
import com.tosok.user.service.MemberService;

@Service("MemberService")
public class MemberServiceImpl implements MemberService {

	public final static String HOST_SMTP = "smtp.naver.com";			// Client ID
	public final static String HOST_SMTP_NAME = "어양토속식품";				// Client ID
	public final static String HOST_EMAIL = "jhllolia@naver.com";		// Client ID
	public final static String HOST_ADMIN_ID = "jhllolia";				// Client ID
	public final static String HOST_ADMIN_Secret = "$Asd87568956";		// Client Secret

	@Resource(name = "MemberDAO")
	private MemberDAO memberDao;

	@Autowired(required=true) 
	private HttpServletRequest request;

    @Resource
    private ReviewFileInsert fileInsert;

	@Override
	public boolean loginCheck(MemberVO vo, HttpSession session) {
		/* ========== 로그인 체크 ========== */
		boolean result = memberDao.loginCheck(vo);

		if (result) {
			MemberVO vo2 = viewMember(vo);
			session.setAttribute("member_Seq", vo2.getMember_Seq());
			session.setAttribute("member_Id", vo2.getMember_Id());
			session.setAttribute("member_Way", vo2.getMember_Way());
			session.setAttribute("member_Profile", vo2.getMember_Profile());
			session.setAttribute("member_Name", vo2.getMember_Name());
			session.setAttribute("member_Phone", vo2.getMember_Phone());
		}

		return result;
	}

	@Override
	public int insertReviewUpload(ReviewVO vo, HttpServletRequest request) throws Exception {
		JsonArray jsonData = new JsonArray();
		List<Map<String, Object>> list = fileInsert.parseInsertFileInfo(vo, request);

		for (int i = 0; i < list.size(); i++) {
			JsonObject obj = new JsonObject();

			obj.addProperty("ORDER_NUM", (String) list.get(i).get("ORDER_NUM"));
			obj.addProperty("FILE_TITLE", (String) list.get(i).get("FILE_TITLE"));
			obj.addProperty("FILE_NAME", (String) list.get(i).get("FILE_NAME"));
			obj.addProperty("FILE_SIZE", (long) list.get(i).get("FILE_SIZE"));

			jsonData.add(obj);
		}

		vo.setVIEW_ARRAY(jsonData.toString());
		return memberDao.insertReviewUpload(vo);
	}

	@Override
	public int updateReviewUpload(ReviewVO vo, HttpServletRequest request) throws Exception {
		JsonArray jsonData = new JsonArray();
		List<Map<String, Object>> list = fileInsert.parseInsertFileInfo(vo, request);

		for (int i = 0; i < list.size(); i++) {
			JsonObject obj = new JsonObject();

			obj.addProperty("ORDER_NUM", (String) list.get(i).get("ORDER_NUM"));
			obj.addProperty("FILE_TITLE", (String) list.get(i).get("FILE_TITLE"));
			obj.addProperty("FILE_NAME", (String) list.get(i).get("FILE_NAME"));
			obj.addProperty("FILE_SIZE", (long) list.get(i).get("FILE_SIZE"));

			jsonData.add(obj);
		}
		
		System.out.println(jsonData.toString());

		vo.setVIEW_ARRAY(jsonData.toString());
		return memberDao.updateReviewUpload(vo);
	}

	@Override
	public int loginAuth(MemberVO vo) {
		return memberDao.loginAuth(vo);
	}

	@Override
	public MemberVO viewMember(MemberVO vo) {
		return memberDao.viewMember(vo);
	}

	@Override
	public void logout(HttpSession session) {
		session.invalidate();
	}

	@Override
	public int idCheck(String member_chk) {
		return memberDao.idCheck(member_chk);
	}

	@Override
	public int pwdCheck(MemberVO vo) {
		return memberDao.pwdCheck(vo);
	}

	@Override
	public int signup(MemberVO vo) {
		/* ========== 회원가입 인증 메일 전송 ========== */
		send_Email(vo, "signup_auth");
		return memberDao.signup(vo);
	}

	@Override
	public int userUpdate(MemberVO vo) {
		/* ========== 회원정보 수정시 이메일 전송 ========== */
		send_Email(vo, "userUpdate");

		return memberDao.userUpdate(vo);
	}

	@Override
	public MemberVO memberInfo(MemberVO vo) {
		return memberDao.memberInfo(vo);
	}

	@Override
	public int getProfileUpload(MemberVO vo) {
		return memberDao.getProfileUpload(vo);
	}

	@Override
	public MemberVO info_update(MemberVO vo) {
		return memberDao.info_update(vo);
	}

	@Override
	public int pwdUpdate(MemberVO vo) {
		return memberDao.pwdUpdate(vo);
	}

	@Override
	public void pwdSearch(HttpServletResponse response, MemberVO vo) throws Exception {
		/* ========== 임시 비밀번호 발급 ========== */
		response.setContentType("text/html;charset=utf-8");

		String pw = "";
		for (int i = 0; i < 12; i++) {
			pw += (char) ((Math.random() * 26) + 97);
		}

		String member = vo.getMember_Id();		// 회원 이메일
		String id = find_Id(member);			// 이메일 존재 여부

		vo.setMember_Id(id);
		vo.setMember_Pw(pw);
		memberDao.update_pw(vo);

		send_Email(vo, "pwdSearch");
	}

	private String find_Id(String member_chk) {
		return memberDao.find_Id(member_chk);
	}

	@Override
	public int update_authstatus(MemberVO vo) {
		return memberDao.update_authstatus(vo);
	}

	@Override
	public void send_Email(MemberVO vo, String div) {
		/* ========== 비밀번호 찾기 / 회원가입 / 사용자 정보 수정 ========== */
		String member = vo.getMember_Id();
		String member_Id = find_Id(member);

		/* ========== 보내는 사람 EMail, 제목, 내용 ========== */
		String charSet = "utf-8";					// UTF - 8
		String local = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
		String href = "";
		String subject = "";						// 메일 제목
		String msg = "";							// 메일 내용

		try {

			if (div.equals("pwdSearch")) {
				/* ========== 비밀번호 찾기 ========== */
				href = local + "/member/login?find_target=Y";
				subject = "[" + HOST_SMTP_NAME + "] 임시 비밀번호 입니다.";

				msg += "<div align='center' style='display: table; width: 100%; min-height: calc(100vh - 94px); padding: 40px 0px 0px 0px; box-sizing: border-box; background-color: #f6f7f8; vertical-align: middle;'>";
				msg += "<div style='width: 600px; margin: 40px auto; padding: 60px 20px 50px 20px; border: 1px solid #dadada; background-color: #fff;'>";
				msg += "<h2 style='font-size: 25px; font-weight: 400; text-align: center;'>" + member_Id + "님의 임시 비밀번호 입니다.</h2>";
				msg += "<p style='max-width: 450px; font-size: 17px; font-weight: 400; text-align: center;'>임시비밀번호를 발급 받은 후에 <span style='font-weight: 550;'>내정보 -> 비밀번호 변경</span> 으로 이동해서 변경해주세요.</p>";
				msg += "<div style='margin: 30px 20px 0px 20px; font-size: 20px; border-bottom: 2px solid #eee; padding: 0px 0px 15px 0px;'>임시 비밀번호 : "
						+ vo.getMember_Pw() + "</div>";
				msg += "<a href=" + href + " style='display:inline-block; padding: 10px 30px 10px 30px; text-decoration: none; font-size: 20px; font-weight: 600;text-align: center; box-sizing:border-box; border-radius: 2px; background-color:#f0ae44; border-color: #f0ae44; color: #fff; margin: 10px 0px 20px 0px;'>";
				msg += "로그인 페이지로 이동";
				msg += "</a>";
				msg += "</div>";
				msg += "</div>";
			} else if (div.equals("signup_auth")) {
				/* ========== 회원가입 인증 ========== */

				String authkey = new TempKey().getKey(50, false);
				vo.setMember_authkey(authkey);

				href = local + "/member/joinConfirm?member_Id=" + vo.getMember_Id() + "&authkey=" + authkey;
				subject = "[" + HOST_SMTP_NAME + "] 회원가입 이메일 인증";
				msg += "<div style='display: table; width: 100%; min-height: calc(100vh - 94px); padding: 40px 0px 0px 0px; box-sizing: border-box; background-color: #f6f7f8; vertical-align: middle;'>";
				msg += "<div style='width: 500px; margin: 40px auto; padding: 30px 20px 30px 20px; border: 1px solid #dadada; background-color: #fff;'>";
				msg += "<h2 style='font-size: 20px; font-weight: 600; margin: 0px 0px 10px 0px'>이메일을 인증해주세요.</h2>";
				msg += "<p style='font-size: 14px; font-weight: 400; margin: 0px 0px 3px 0px;'>안녕하세요, " + vo.getMember_Id() + " 님</p>";
				msg += "<p style='font-size: 14px; font-weight: 400; margin: 0px;'>회원가입을 완료하시려면 아래 링크를 클릭해주세요.</p>";
				msg += "<a href=" + href + " style='display:inline-block; padding: 10px 20px 10px 20px; text-decoration: none; font-size: 18px; font-weight: 800; text-align: center; box-sizing:border-box; border: 1px solid #303030; color: #303030; background-color: #f0ae44; margin: 15px 0px 10px 0px;'>";
				msg += "이메일을 인증해주세요.";
				msg += "</a>";
				msg += "<p style='font-size: 14px; font-weight: 400; padding: 0px 0px 10px 0px; margin: 5px 0px 10px 0px; border-bottom: 1px solid #dadada;'>항상 회원을 위해 최선을 다하겠습니다.</p>";
				msg += "<div style=''>";
				msg += "<a href=" + local + "/terms/termsUse style='font-size: 12px; font-weight: 600; color: #787777; margin: 0px 10px 0px 0px'>이용약관</a>";
				msg += "<a href=" + local + "/terms/termsPrivacy style='font-size: 12px; font-weight: 600; color: #787777; margin: 0px 10px 0px 0px'>개인정보처리방침</a>";
				msg += "<a href=" + local + "/terms/counsel style='font-size: 12px; font-weight: 600; color: #787777;'>E-mail 고객센터</a>";
				msg += "</div>";
				msg += "</div>";
				msg += "</div>";
			} else if (div.equals("userUpdate")) {
				/* ========== 사용자 정보수정 ========== */
				vo.setMember_Id(member_Id);

				subject = "[" + HOST_SMTP_NAME + "] 회원정보 수정 확인메일";
				msg += "<div align='center' style='display: table; width: 100%; min-height: calc(100vh - 94px); padding: 40px 0px 0px 0px; box-sizing: border-box; background-color: #f6f7f8; vertical-align: middle;'>";
				msg += "<div style='width: 800px; margin: 40px auto;padding: 0px 20px 25px 20px;border: 1px solid #dadada;background-color: #fff;'>";
				msg += "<h4 style='width: 100%; padding-top: 10px; border-top: 2px solid #a58045; font-size: 20px; font-weight: 400; text-align: center; margin-top: 30px; margin-bottom: 11px;'>"
						+ vo.getMember_Name() + " 님의 수정된 회원정보</h4>";
				msg += "<table style='width: 100%;border-top: 1px solid #4d4d4f;border-bottom: 1px solid #4d4d4f;margin-bottom: 10px;'>";
				msg += "<tbody>";
				msg += "<tr style='border-bottom: 1px solid #e2e2e2;color: black;'>";
				msg += "<td style='padding: 10px 5px 10px 5px; text-align: center;background-color: #f9f9f9;color: #555;font-weight: 600;'>이메일</td>";
				msg += "<td style='padding: 10px 5px 10px 5px; padding-left: 20px;'>" + vo.getMember_Id() + "</td>";
				msg += "</tr>";
				msg += "<tr style='border-bottom: 1px solid #e2e2e2;color: black;'>";
				msg += "<td style='padding: 10px 5px 10px 5px; text-align: center;background-color: #f9f9f9;color: #555;font-weight: 600;'>닉네임</td>";
				msg += "<td style='padding: 10px 5px 10px 5px; padding-left: 20px;'>" + vo.getMember_Name() + "</td>";
				msg += "</tr>";
				msg += "<tr style='border-bottom: 1px solid #e2e2e2;color: black;'>";
				msg += "<td style='padding: 10px 5px 10px 5px; text-align: center;background-color: #f9f9f9;color: #555;font-weight: 600;'>주소</td>";
				msg += "<td style='padding: 10px 5px 10px 5px; padding-left: 20px;'>" + "(" + vo.getMember_Zip() + ") " + vo.getMember_Addr1() + "" + vo.getMember_Addr2() + "</td>";
				msg += "</tr>";
				msg += "<tr style='border-bottom: 1px solid #e2e2e2;color: black;'>";
				msg += "<td style='padding: 10px 5px 10px 5px; text-align: center;background-color: #f9f9f9;color: #555;font-weight: 600;'>휴대폰 번호</td>";
				msg += "<td style='padding: 10px 5px 10px 5px; padding-left: 20px;'>" + vo.getMember_Phone() + "</td>";
				msg += "</tr>";
				msg += "</tbody>";
				msg += "</table>";
				msg += "</div>";
				msg += "</div>";

			} else if (div.equals("other_signup_auth")) {
				/* ========== 사용자 정보수정 ========== */
				String authkey = new TempKey().getKey(50, false);
				vo.setMember_authkey(authkey);

				href = local + "/member/joinConfirm?member_Id=" + vo.getMember_Id() + "&authkey=" + authkey;
				subject = "[" + HOST_SMTP_NAME + "] 인증메일 재전송";
				msg += "<div align='center' style='display: table; width: 100%; min-height: calc(100vh - 94px); padding: 40px 0px 0px 0px; box-sizing: border-box; background-color: #f6f7f8; vertical-align: middle;'>";
				msg += "<div style=' width: 600px; margin: 40px auto; padding: 60px 20px 50px 20px; border: 1px solid #dadada; background-color: #fff;'>";
				msg += "<h2 style=' max-width: 400px; font-size: 20px; font-weight: 400; text-align: center; margin: 0px auto;'>아래 버튼을 클릭하시면 " + vo.getMember_Id() + " 님의 이메일 인증이 완료됩니다.</h2>";
				msg += "<div style='margin: 0px; border-bottom: 2px solid #eee; padding: 0px 0px 15px 0px;'></div>";
				msg += "<a href=" + href + " style='display:inline-block; padding: 10px 30px 10px 30px; text-decoration: none; font-size: 20px; font-weight: 600; text-align: center; box-sizing:border-box; border-radius: 2px; background-color: #503837; border-color: #503837; color: #fff; margin: 15px 0px 20px 0px;'>";
				msg += "로그인 페이지로 이동";
				msg += "</a>";
				msg += "</div>";
				msg += "</div>";
			}

			/* ========== 출력 된 결과 메일 전송 ========== */
			HtmlEmail email = new HtmlEmail();
			email.setDebug(true);
			email.setCharset(charSet);
			email.setSSL(true);
			email.setHostName(HOST_SMTP);
			email.setSmtpPort(587);
			email.setAuthentication(HOST_ADMIN_ID, HOST_ADMIN_Secret);
			email.setTLS(true);
			email.addTo(member, charSet);
			email.setFrom(HOST_EMAIL, HOST_SMTP_NAME, charSet);
			email.setSubject(subject);
			email.setHtmlMsg(msg);
			email.send();
		} catch (Exception e) {
			System.out.println("메일발송 실패 : " + e);
		}
	}

	@Override
	public void send_Opinion(MemberVO vo) {
		/* ========== 사용자 테이블 신청 ========== */
		String charSet = "utf-8";

		/* ========== 보내는 사람 EMail, 제목, 내용 ========== */
		String msg = "";

		try {

			/* ========== 사용자 테이블 신청 ========== */
			String member = vo.getMember_Id();
			String subject = "[" + HOST_SMTP_NAME + "] E-mail 문의  → " + vo.getMember_Inquiry() + "";

			msg += "<div>";
			msg += "	<p>문의 메일 → " + member + "</p><br />";
			msg += "	<p>문의 제목 → " + vo.getMember_Subject() + "</p><br /><br />";
			msg += "	<p>" + vo.getMember_Content() + "</p>";
			msg += "</div>";

			HtmlEmail email = new HtmlEmail();
			email.setDebug(true);
			email.setCharset(charSet);
			email.setSSL(true);
			email.setHostName(HOST_SMTP);
			email.setSmtpPort(587);
			email.setAuthentication(HOST_ADMIN_ID, HOST_ADMIN_Secret);
			email.setTLS(true);
			email.addTo(HOST_EMAIL, charSet);
			email.setFrom(HOST_EMAIL, HOST_SMTP_NAME, charSet);
			email.setSubject(subject);
			email.setHtmlMsg(msg);
			email.send();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<PayVO> memberPaymentList(PayVO vo) {
		return memberDao.memberPaymentList(vo);
	}

	@Override
	public List<PayVO> adminSelectPayment(PayVO vo) {
		return memberDao.adminSelectPayment(vo);
	}

	@Override
	public String selectPaymentProfile(MemberVO vo) {
		return memberDao.selectPaymentProfile(vo);
	}

	@Override
	public List<MemberVO> adminSelectPerson(MemberVO vo) {
		return memberDao.adminSelectPerson(vo);
	}

	@Override
	public MemberVO memberInfoCnt(MemberVO vo) {
		return memberDao.memberInfoCnt(vo);
	}

	@Override
	public int otherSignup(MemberVO vo) {
		return memberDao.otherSignup(vo);
	}

	@Override
	public MemberVO otherSignupWay(MemberVO vo) {
		return memberDao.otherSignupWay(vo);
	}

	@Override
	public int addShippingCourier(PayVO vo) {
		return memberDao.addShippingCourier(vo);
	}

	@Override
	public int insertQnAProduct(QnaVO vo) {
		return memberDao.insertQnAProduct(vo);
	}

	@Override
	public List<QnaVO> selectQnAList(SearchCriteria scri) {
		return memberDao.selectQnAList(scri);
	}

	@Override
	public int selectQnACount(QnaVO vo) {
		return memberDao.selectQnACount(vo);
	}

	@Override
	public int updateUserQnAData(QnaVO vo) {
		return memberDao.updateUserQnAData(vo);
	}

	@Override
	public List<QnaVO> selectAdminQnAList(SearchCriteria scri) {
		return memberDao.selectAdminQnAList(scri);
	}

	@Override
	public int updateAdminQnAData(QnaVO vo) {
		return memberDao.updateAdminQnAData(vo);
	}

	@Override
	public int updateAdminQnAState(QnaVO vo) {
		return memberDao.updateAdminQnAState(vo);
	}

	@Override
	public List<PayVO> selectProductReturnSEQ(PayVO vo) {
		return memberDao.selectProductReturnSEQ(vo);
	}

	@Override
	public int selectAdminQnACount(QnaVO vo) {
		return memberDao.selectAdminQnACount(vo);
	}

	@Override
	public List<ReviewVO> selectProductReview(ReviewVO vo) {
		return memberDao.selectProductReview(vo);
	}

	@Override
	public List<ReviewVO> selectMemberReviewList(SearchCriteria scri) {
		return memberDao.selectMemberReviewList(scri);
	}

	@Override
	public int selectMemberReviewCount(ReviewVO vo) {
		return memberDao.selectMemberReviewCount(vo);
	}

	@Override
	public ReviewVO getProductReviewData(ReviewVO vo) {
		return memberDao.getProductReviewData(vo);
	}

	@Override
	public int deleteProductReview(ReviewVO vo) {
		return memberDao.deleteProductReview(vo);
	}

	@Override
	public int insertCartProduct(CartVO vo) {
		return memberDao.insertCartProduct(vo);
	}

	@Override
	public int selectCartCount(CartVO vo) {
		return memberDao.selectCartCount(vo);
	}

	@Override
	public ProductVO getCartProductUnit(ProductVO vo) {
		return memberDao.getCartProductUnit(vo);
	}

	@Override
	public List<CartVO> selectCartMemberList(CartVO vo) {
		return memberDao.selectCartMemberList(vo);
	}

	@Override
	public int selectCartProductVaild(CartVO vo) {
		return memberDao.selectCartProductVaild(vo);
	}

	@Override
	public int updateCartProductState(CartVO vo) {
		return memberDao.updateCartProductState(vo);
	}

	@Override
	public int haveMemberCartProduct(CartVO vo) {
		return memberDao.haveMemberCartProduct(vo);
	}

	@Override
	public int updateCartUnitState(CartVO vo) {
		return memberDao.updateCartUnitState(vo);
	}

	@Override
	public int updateCartProductQty(CartVO vo) {
		return memberDao.updateCartProductQty(vo);
	}

	@Override
	public List<PayVO> selectRequestPayment(PayVO vo) {
		return memberDao.selectRequestPayment(vo);
	}

	@Override
	public int deleteReviewImg(ReviewVO vo) {
		return memberDao.deleteReviewImg(vo);
	}

	@Override
	public List<ReviewVO> selectReview(ReviewVO vo) {
		return memberDao.selectReview(vo);
	}

	@Override
	public int updateAdminReview(ReviewVO vo) {
		return memberDao.updateAdminReview(vo);
	}

	@Override
	public int deleteAdminReview(ReviewVO vo) {
		return memberDao.deleteAdminReview(vo);
	}

}
