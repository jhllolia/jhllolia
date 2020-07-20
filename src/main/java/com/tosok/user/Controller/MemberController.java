package com.tosok.user.Controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.tosok.user.OtherAPI.KakaoAPI;
import com.tosok.user.OtherAPI.NaverAPI;
import com.tosok.user.Until.PageMaker;
import com.tosok.user.Until.SearchCriteria;
import com.tosok.user.Until.SendSms;
import com.tosok.user.VO.MemberVO;
import com.tosok.user.VO.PayVO;
import com.tosok.user.VO.ReviewVO;
import com.tosok.user.service.MemberService;
import com.tosok.user.service.ReserveService;

@Controller
public class MemberController {
	protected Log log = LogFactory.getLog(MemberController.class);
 
	public final static String NAVER_CLIENT_ID = "yLTyoDNUKdCTTMskKa_T";		// Naver Client ID
	public final static String NAVER_CLIENT_Secret = "p6vCoid1b5";				// Naver Client Secret

    @Autowired
    private MemberService memberService;

    @Autowired
    private ReserveService reserveService;
    
    @Autowired
    private SendSms sendSms;					//
    
    @Autowired
    private KakaoAPI kakao;						// 카카오

    @Autowired
    private NaverAPI naver;						// 네이버

    /* ========== 회원 로그인 페이지 ========== */
	@RequestMapping("/member/login")
	public String login(HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession();
		String url = request.getScheme() + "://" + request.getServerName() +":" + request.getServerPort();

		/* ==== 이전 경로 저장 ==== */
		if(request.getRequestURI() != null && request.getHeader("referer") != null) {
			if(!(request.getRequestURI().equals("/member/login") && request.getHeader("referer").equals("" + url + "/member/login"))) {
				if(request.getHeader("referer").contains("member")) {
					session.setAttribute("prevURL", "" + url + "/info/info_payment");
				} else if(request.getHeader("referer").contains("null")) {
					session.setAttribute("prevURL", "" + url + "/info/info_payment");
				} else {
					session.setAttribute("prevURL", request.getHeader("referer"));
				}
			}
		} else {
			session.setAttribute("prevURL", "" + url + "/info/info_payment");
		}

		return "/member/login";
	}

    /* ========== 회원 로그인 유효성 검사 ========== */
	@RequestMapping("/member/loginCheck")
	public ModelAndView loginCheck(MemberVO vo, HttpSession session, HttpServletRequest request, Model model) {
		ModelAndView mav = new ModelAndView();
		String login_status = "";
		String member = vo.getMember_Id();			// 입력 E-mail

	    /* ========== 회원 유효성 검사  ========== */
		int exist = memberService.idCheck(member);

		if(exist != 0) {
		    /* ========== 인증 유효성 검사  ========== */
			int auth = memberService.loginAuth(vo);

			if(auth == 0) {
			    /* ========== False ========== */
				login_status = "auth_fail";

				mav.setViewName("redirect:../member/login");
				mav.addObject("code", "Auth");
			} else {

				boolean result = memberService.loginCheck(vo, session);

				if(result == true) {
					/* ========== Y ========== */
					login_status = "login_success";

					mav.setViewName("redirect:" + (String) session.getAttribute("prevURL") + "");
				} else {
					/* ========== N ========== */
					login_status = "login_fail";

					mav.setViewName("redirect:../member/login");
					mav.addObject("code", "failure");
				}
			}
		} else {

			login_status = "none_User";
			mav.setViewName("redirect:../member/login");
			mav.addObject("code", "noneUser");
		}

		session.setAttribute("login_status", login_status);

	    /*

		로그인 유효성 검사 / 회원가입 방식 조회 
		MemberVO way = memberService.otherSignupWay(vo);

		if(way.getMember_Way().equals("Y")) {

		} else {
			mav.setViewName("redirect:../member/login");
			mav.addObject("code", "otherWay");
		}

		*/

		return mav;
	}


    /* ========== 회원 등록 페이지 ========== */
	@RequestMapping("/member/signup")
	public String signup() {
		return "/member/signup";
	}

    /* ========== 회원 비밀번호 변경 페이지  ========== */
	@RequestMapping("/member/find_pwd")
	public String find_pwd() {
		return "/member/find_pwd";
	}

    /* ========== 비밀번호 찾기 페이지  ========== */
	@RequestMapping("/member/search_password")
	public String search_password() {
		return "/member/search_password";
	}

    /* ========== 회원 결제 정보 페이지 ========== */
	@RequestMapping("/info/info_payment")
	public ModelAndView info_payment(MemberVO vo, HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView();
		vo.setMember_Id((String) request.getSession().getAttribute("member_Id"));

		if(request.getSession().getAttribute("member_Id") == null) {
			mav.setViewName("member/login");
			mav.addObject("msg", "failure");
		} else {
			mav.setViewName("/info/info_payment");
			mav.addObject("msg", "success");
		}

		return mav;
	}

    /* ========== 사용자 리뷰데이터 ========== */
	@RequestMapping("/info/review_all")
	public ModelAndView review_member(@ModelAttribute("scri") SearchCriteria scri, HttpServletRequest request, ReviewVO vo) {
		ModelAndView mav = new ModelAndView();
		PageMaker pageMaker = new PageMaker();

		String id = (String) request.getSession().getAttribute("member_Id");
		vo.setREVIEW_ID(id);

		scri.setId(id);
		scri.setPerPageNum(10);

		int count = memberService.selectMemberReviewCount(vo);

		pageMaker.setCri(scri);
		pageMaker.setTotalCount(count);									// 테이블 번호

		mav.addObject("list", memberService.selectMemberReviewList(scri));
		mav.addObject("pageMaker", pageMaker);
		mav.setViewName("/info/review_all");
		return mav;
	}

	@RequestMapping("/info/rev_delete")
	@ResponseBody
	public int review_delete(HttpServletRequest request, ReviewVO vo, PayVO pay) {
		JsonParser parser = new JsonParser();
		HttpSession session = request.getSession();
		String rootPath = "";

		String id = (String) request.getSession().getAttribute("member_Id");
		String product = request.getParameter("order_product");
		String num = request.getParameter("order_num");
		String name = request.getParameter("order_name");
		String option = request.getParameter("order_option");
		String qty = request.getParameter("order_qty");
		
		if(num.equals("") || name.equals("") || option.equals("") || qty.equals("") || id.equals("")) {
			return 0;
		} else {

			qty = qty.replaceAll("[^0-9]","");

			pay.setORDER_NUM(num);
			pay.setBUYER_EMAIL(id);
			pay.setPRODUCT_NUM(product);
			pay.setPRODUCT_NAME(name);
			pay.setPRODUCT_OPTION(option);
			pay.setPRODUCT_QTY(qty);

			int cnt = reserveService.selectProductBuyValid(pay);

			if(cnt > 0) {
				vo.setORDER_NUM(num);
				vo.setREVIEW_ID(id);
				vo.setPRODUCT_NUM(product);
				vo.setPRODUCT_NAME(name);
				vo.setPRODUCT_OPTION(option);
				vo.setPRODUCT_QTY(qty);

				/* ========= 데이터 불러오기 =========== */
				ReviewVO data = memberService.getProductReviewData(vo);
		    	JsonArray arr = (JsonArray) parser.parse(data.getVIEW_UPLOAD());

		    	for (int i = 0; i < arr.size(); i++) {
		    		JsonObject obj = (JsonObject) arr.get(i);
		    		rootPath = session.getServletContext().getRealPath("/resources/upload/review/" + obj.get("FILE_NAME").getAsString().replaceAll("\"", ""));

		    		File has = new File(rootPath);

		    		if(has.exists()) {
		    			has.delete();
		    		}
				}

		    	int result = memberService.deleteProductReview(vo);
		    	
		    	if(result > 0) {
		    		return result;
		    	} else {
		    		return 0;
		    	}
			} else {
				return 0;
			}
		}
	}

	@RequestMapping("/info/rev_update")
	public ModelAndView review_update(HttpServletRequest request, ReviewVO vo, PayVO pay) {
		ModelAndView mav = new ModelAndView();
    	Gson gson = new Gson();

    	String referrer = request.getHeader("Referer");
		List<Map<String, Object>> file = null;

		String id = (String) request.getSession().getAttribute("member_Id");
		String seq = request.getParameter("order_product");
		String num = request.getParameter("order_num");
		String name = request.getParameter("order_name");
		String option = request.getParameter("order_option");
		String qty = request.getParameter("order_qty");

		if(num.equals("") || name.equals("") || option.equals("") || qty.equals("") || id.equals("")) {
			mav.setViewName("redirect:" + referrer + "");
		} else {

			qty = qty.replaceAll("[^0-9]","");

			pay.setORDER_NUM(num);
			pay.setBUYER_EMAIL(id);
			pay.setPRODUCT_NUM(seq);
			pay.setPRODUCT_NAME(name);
			pay.setPRODUCT_OPTION(option);
			pay.setPRODUCT_QTY(qty);

			int cnt = reserveService.selectProductBuyValid(pay);

			if(cnt > 0) {
				vo.setORDER_NUM(num);
				vo.setREVIEW_ID(id);
				vo.setPRODUCT_NUM(seq);
				vo.setPRODUCT_NAME(name);
				vo.setPRODUCT_OPTION(option);
				vo.setPRODUCT_QTY(qty);

				ReviewVO data = memberService.getProductReviewData(vo);
				file = gson.fromJson(data.getVIEW_UPLOAD(), new TypeToken<List<Map<String, Object>>>() {}.getType());

				mav.addObject("DATA", data);
				mav.addObject("FILE", file);
				mav.setViewName("/info/review_update");				
			} else {
				mav.setViewName("redirect:" + referrer + "");	
			}
		}

		return mav;
	}

    /* ========== 리뷰데이터 send ========== */
	@RequestMapping("/update/review")
	@ResponseBody
	public String updateReviewData(HttpServletRequest request, ReviewVO vo, PayVO pay) throws Exception {
		String id = (String) request.getSession().getAttribute("member_Id");
		String name = (String) request.getSession().getAttribute("member_Name");

		String seq = request.getParameter("seq");
		String productNm = request.getParameter("name");
		String option = request.getParameter("option");
		String qty = request.getParameter("qty");

		String order = request.getParameter("order");
		String star = request.getParameter("star");
		String content = request.getParameter("content");

		if(id.equals("") || name.equals("") || seq.equals("") || productNm.equals("") || option.equals("") || qty.equals("") || order.equals("") || star.equals("") || content.equals("")) {
			return "";
		} else if(!order.matches(".*[0-9].*") || !star.matches(".*[0-9].*")) {
			return "";
		} else {

			qty = qty.replaceAll("[^0-9]","");

			pay.setORDER_NUM(order);
			pay.setBUYER_EMAIL(id);
			pay.setPRODUCT_NUM(seq);
			pay.setPRODUCT_NAME(productNm);
			pay.setPRODUCT_OPTION(option);
			pay.setPRODUCT_QTY(qty);

			int cnt = reserveService.selectProductBuyValid(pay);

			if(cnt > 0) {
				vo.setORDER_NUM(order);
				vo.setPRODUCT_NUM(seq);
				vo.setPRODUCT_NAME(productNm);
				vo.setPRODUCT_OPTION(option);
				vo.setPRODUCT_QTY(qty);
				vo.setVIEW_POINT(star);
				vo.setVIEW_CONTENT(content);

				int result = memberService.updateReviewUpload(vo, request);

				if(result > 0) {
					return order;
				} else {
					return "";
				}
			} else {
				return "";
			}
		}
	}

    /* ==========  ========== */
	@RequestMapping("/delete/rev_img")
	@ResponseBody
	public int del_rev_img(HttpServletRequest request, HttpSession session, ReviewVO vo) throws ParseException {
		JsonParser parser = new JsonParser();

		String id = (String) request.getSession().getAttribute("member_Id");
		int index = Integer.parseInt(request.getParameter("index"));
		String order = request.getParameter("order");
		String num = request.getParameter("num");
		String name = request.getParameter("name");
		String option = request.getParameter("option");
		String qty = request.getParameter("qty");

		String file_name = request.getParameter("file_name");
		String file_size = request.getParameter("file_size");
		String path = session.getServletContext().getRealPath("/resources/upload/review/" + file_name + "");

		File f = new File(path);

		if(f.exists()) {

			vo.setORDER_NUM(order);
			vo.setREVIEW_ID(id);
			vo.setPRODUCT_NUM(num);
			vo.setPRODUCT_NAME(name);
			vo.setPRODUCT_OPTION(option);
			vo.setPRODUCT_QTY(qty);

			/* ========== REVIEW 데이터 불러오기 =========== */
			ReviewVO data = memberService.getProductReviewData(vo);
	    	JsonArray arr = (JsonArray) parser.parse(data.getVIEW_UPLOAD());
	    	arr.remove(index);

	    	String data_arr = arr.toString();
	    	vo.setVIEW_UPLOAD(data_arr);

			if(f.delete()) {
				/* ========== 해당 REVIEW UPLOAD 수정 =========== */
				memberService.deleteReviewImg(vo);

				return 1;
			} else {
				return 0;
			}
		} else {
			return 0;
		}
	}
	
    /* ========== 리뷰데이터 send ========== */
	@RequestMapping("/send/review")
	@ResponseBody
	public String sendReviewData(HttpServletRequest request, ReviewVO vo, PayVO pay) throws Exception {
		String id = (String) request.getSession().getAttribute("member_Id");
		String name = (String) request.getSession().getAttribute("member_Name");

		String seq = request.getParameter("seq");
		String productNm = request.getParameter("name");
		String option = request.getParameter("option");
		String qty = request.getParameter("qty");

		String order = request.getParameter("order");
		String star = request.getParameter("star");
		String content = request.getParameter("content");

		if(id.equals("") || name.equals("") || seq.equals("") || productNm.equals("") || option.equals("") || qty.equals("") || order.equals("") || star.equals("") || content.equals("")) {
			return "";
		} else if(!order.matches(".*[0-9].*") || !star.matches(".*[0-9].*")) {
			return "";
		} else {

			qty = qty.replaceAll("[^0-9]","");

			pay.setORDER_NUM(order);
			pay.setBUYER_EMAIL(id);
			pay.setPRODUCT_NUM(seq);
			pay.setPRODUCT_NAME(productNm);
			pay.setPRODUCT_OPTION(option);
			pay.setPRODUCT_QTY(qty);

			int cnt = reserveService.selectProductBuyValid(pay);

			if(cnt > 0) {
				vo.setORDER_NUM(order);
				vo.setREVIEW_ID(id);
				vo.setREVIEW_NAME(name);
				vo.setPRODUCT_NUM(seq);
				vo.setPRODUCT_NAME(productNm);
				vo.setPRODUCT_OPTION(option);
				vo.setPRODUCT_QTY(qty);
				vo.setVIEW_POINT(star);
				vo.setVIEW_CONTENT(content);

				int result = memberService.insertReviewUpload(vo, request);

				if(result > 0) {
					return order;
				} else {
					return "";
				}
			} else {
				return "";
			}
		}
	}

    /* ========== 회원 총 결제 정보 ========== */
	@RequestMapping("/act/count")
	@ResponseBody
	public MemberVO actCount(MemberVO vo, HttpServletRequest requset) {
		vo.setMember_Id(requset.getParameter("id"));
		return memberService.memberInfoCnt(vo);
	}

    /* ========== 회원 대시보드 ========== */
	@RequestMapping("/info/member_Info")
	public ModelAndView info(MemberVO vo, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		vo.setMember_Id((String) request.getSession().getAttribute("member_Id"));

		if(request.getSession().getAttribute("member_Id") == null) {
			mav.addObject("msg", "failure");
			mav.setViewName("member/login");
		} else {
			mav.addObject("msg", "success");		
			mav.addObject("info", memberService.memberInfo(vo));
			mav.setViewName("/info/member_Info");
		}

		return mav;
	}

    /* ========== 사용자 정보 수정 페이지 ========== */
	@RequestMapping(value = "/info/info_update")
	public ModelAndView info_chk(MemberVO vo, HttpSession session) {
		ModelAndView mav = new ModelAndView();

		int seq = (int) session.getAttribute("member_Seq");
		if(seq == 0) seq = 0;

		if(seq == 0) {
			mav.setViewName("member/login");
			mav.addObject("msg", "fail");
		} else {
			vo.setMember_Seq(seq);

			mav.addObject("info", memberService.info_update(vo));
			mav.setViewName("/info/info_update");
		}

		return mav;
	}

    /* ========== 회원 비밀번호 변경 페이지 ========== */
	@RequestMapping(value = "/info/pwd_update")
	public ModelAndView info_pwd(MemberVO vo, HttpSession session) {
		ModelAndView mav = new ModelAndView();

		int seq = (int) session.getAttribute("member_Seq");
		if(seq == 0) seq = 0;

		if(seq == 0) {
			mav.setViewName("member/login");
			mav.addObject("msg", "fail");
		} else {
			vo.setMember_Seq(seq);

			mav.addObject("info", memberService.info_update(vo));
			mav.setViewName("/info/pwd_update");
		}
		
		return mav;
	}

    /* ========== 사용자 프로필 불러오기 ========== */
	@RequestMapping(value = "/person/{seq}", produces = "application/text;charset=utf8")
	@ResponseBody
	public String info_profile(MemberVO vo, @PathVariable int seq) {
		vo.setMember_Seq(seq);
		return memberService.selectPaymentProfile(vo);
	}

    /* ========== 회원 비밀번호 변경 ========== */
	@RequestMapping("/info/info_pwd_update")
	@ResponseBody
	public int info_pwd_update(@ModelAttribute MemberVO vo, HttpServletRequest request) {
		String regExp = "^(?=.*[0-9])(?=.*[a-z]).{8,20}$";
		String id = (String) request.getSession().getAttribute("member_Id");
		String now_pw = request.getParameter("now_Pw");
		String pwd1 = request.getParameter("insert_pwd1");
		String pwd2 = request.getParameter("insert_pwd2");

		if(!id.equals((String) request.getSession().getAttribute("member_Id"))) {
			return 0;
		} else if(id.equals("") || now_pw.equals("") || pwd1.equals("") || pwd2.equals("") || id.equals(null) || now_pw.equals(null) || pwd1.equals(null) || pwd2.equals(null)) {
			return 0;
		} else if(pwd1.matches(regExp) == false || pwd2.matches(regExp) == false) {
			return 0;
		} else {

			vo.setMember_Id(id);
			vo.setMember_Pw(now_pw);

			/* =========== 비밀번호 유효성 =========== */
			int pwCheck = memberService.pwdCheck(vo);

			if(pwCheck > 0) {
				vo.setMember_Pw(pwd1);

				return memberService.pwdUpdate(vo);
			} else {
				return 0;
			}
		}
	}

    /* ========== 네이버 로그인 (토큰) ========== */
	@RequestMapping("/naver/accessToken")
	@ResponseBody
	public String NaverAccessToken() {
		return naver.getAccessToken(NAVER_CLIENT_ID);
	}

    /* ========== 네이버 로그인 (Info) ========== */
	@RequestMapping("/member/naver_login")
	public ModelAndView NaverLogin( MemberVO vo, HttpSession session, HttpServletRequest request ) {
		ModelAndView mav = new ModelAndView();

		String code = naver.getAccessCode(NAVER_CLIENT_ID, NAVER_CLIENT_Secret, request);	// 코드 불러오기
		HashMap<String, Object> userInfo = naver.getUserInfo(code);							// 사용자 정보 불어오기

		String id = (String) userInfo.get("email");
		vo.setMember_Id(id);
		vo.setMember_authstatus(1);

	    /* ========== 사용자 이메일이 있다면 True / 없다면 False ( 회원가입 페이지로 이동 ) ========== */
		if (id != null) {
			int idCheck = memberService.idCheck(id);

			if(idCheck != 0) {
			    /* ========== True ========== */
		        MemberVO mem = memberService.memberInfo(vo);

				session.setAttribute("member_Seq", mem.getMember_Seq());
				session.setAttribute("member_Id", mem.getMember_Id());

				mav.addObject("info", mem);
				mav.setViewName("redirect:../info/info_payment");
			} else {
			    /* ========== False ========== */
				vo.setMember_Way("naver");
				vo.setMember_Pw(code);
				vo.setMember_Profile((String) userInfo.get("profile"));
				vo.setMember_Name((String) userInfo.get("nickname"));

				mav.addObject("info", vo);
				mav.setViewName("/member/other_signup");
			}
		} else {
			mav.setViewName("redirect:../member/login");
		}
		
		return mav;
	}

    /* ========== 카카오 로그인 (API) ========== */
	@RequestMapping("/kakao/login")
	public ModelAndView KakaoLogin(@RequestParam("code") String code, MemberVO vo, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		String access_Token = kakao.getAccessToken(code);	// 인증 토큰

		if(access_Token != "") {
			HashMap<String, Object> userInfo = kakao.getUserInfo(access_Token);
			int responeCode = (int) userInfo.get("code");

			if(responeCode == 200) {
				String id = (String) userInfo.get("email");
				vo.setMember_Id(id);
				vo.setMember_authstatus(1);

				/* ========== 사용자 이메일이 있다면 True / 없다면 False ( 회원가입 페이지로 이동 ) ========== */
				if (id != null) {
					int idCheck = memberService.idCheck(id);

					if(idCheck != 0) {
						/* ========== 인증 여부 ========== */
						int authCheck = memberService.loginAuth(vo);

						if(authCheck == 0) {
							memberService.send_Email(vo, "other_signup_auth");

							mav.setViewName("redirect:../member/login");
							mav.addObject("code", "reAuth");
						} else {
						    /* ========== True ========== */
					        MemberVO mem = memberService.memberInfo(vo);

							session.setAttribute("member_Seq", mem.getMember_Seq());
							session.setAttribute("member_Id", mem.getMember_Id());

							mav.setViewName("redirect:../info/info_payment");
							mav.addObject("info", mem);
						}
					} else {
					    /* ========== False ========== */
						vo.setMember_Way("kakao");
						vo.setMember_Pw(access_Token);
						vo.setMember_Profile((String) userInfo.get("profile"));
						vo.setMember_Name((String) userInfo.get("nickname"));

						mav.addObject("info", vo);
						mav.setViewName("/member/other_signup");
					}
			    }
			} else {
				mav.setViewName("redirect:../member/login");
			}
		} else {
			mav.setViewName("redirect:../member/login");
		}

		return mav;
	}

    /* ========== 네이버 / 카카오 회원가입 ========== */
	@RequestMapping(value = "/other/signup", method=RequestMethod.POST)
	@ResponseBody
	public int member_signup(@ModelAttribute MemberVO vo, HttpServletRequest request, HttpSession session) {
		vo.setMember_Way(request.getParameter("way"));			// 등록 방식
		vo.setMember_Profile(request.getParameter("profile"));	// 프로필
		vo.setMember_Id(request.getParameter("id"));			// ID
		vo.setMember_Name(request.getParameter("name"));		// 이름
		vo.setMember_Phone(request.getParameter("phone"));		// 번호
		vo.setMember_Zip(request.getParameter("zip"));			// 
		vo.setMember_Addr1(request.getParameter("addr1"));		// 
		vo.setMember_Addr2(request.getParameter("addr2"));		// 

		int result = memberService.otherSignup(vo);

		if(result > 0) {
			session.setAttribute("member_Seq", vo.getMember_Seq());
			session.setAttribute("member_Id", request.getParameter("id"));
		}

		return result;
	}

    /* ========== 회원 아이디 유무 체크 ========== */
	@RequestMapping("/member/WayCheck")
	@ResponseBody
	public MemberVO WayCheck(@RequestBody String member_chk, MemberVO vo) {
		vo.setMember_Id(member_chk);
		return memberService.otherSignupWay(vo);
	}

    /* ========== 회원 아이디 유무 체크 ========== */
	@RequestMapping("/member/idCheck")
	@ResponseBody
	public Map<Object, Object> idCheck(@RequestBody String member_chk) {
		Map<Object, Object> map = new HashMap<Object, Object>();	
		map.put("cnt", memberService.idCheck(member_chk));			// 개수 체크
		return map;
	}

    /* ========== 회원 비밀번호 결과 체크 ========== */
	@RequestMapping("/member/pwdCheck")
	@ResponseBody
	public Map<Object, Object> pwdCheck(@ModelAttribute MemberVO vo, HttpServletRequest request) {
		Map<Object, Object> map = new HashMap<Object, Object>();

		vo.setMember_Id((String) request.getSession().getAttribute("member_Id"));
		vo.setMember_Pw((String) request.getParameter("pwd"));

		map.put("cnt", memberService.pwdCheck(vo));
		return map;
	}

    /* ========== 회원가입 이메일 인증 ========== */
	@RequestMapping(value = "/member/joinConfirm", method=RequestMethod.GET)
	public ModelAndView emailConfirm(@ModelAttribute MemberVO vo, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();

		vo.setMember_Id(request.getParameter("member_Id"));		// 회원 이메일
		vo.setMember_authstatus(1);								// False : 0, True : 1

	    /* ========== 인증 여부 ========== */
		int result = memberService.update_authstatus(vo);
		if(result == 1) {
		    /* ========== True ========== */
			mav.addObject("code", "SUCCESS");
			mav.setViewName("member/login");
		} else {
		    /* ========== False ========== */
			mav.addObject("code", "FAIL");
			mav.setViewName("member/signup");
		}

		return mav;
	}
	
    /* ========== 사용자 프로필 불러오기 ========== */
	@RequestMapping(value = "/menuInfo/{id:.+}", method = RequestMethod.POST)
	@ResponseBody
	public MemberVO menu_profile(MemberVO vo, @PathVariable("id") String id) {
		vo.setMember_Id(id);
		return memberService.memberInfo(vo);
	}

    /* ========== 로그인 비밀번호 찾기 ========== */
	@RequestMapping("/member/pwdSearch")
	public void pwdSearch(@ModelAttribute MemberVO vo, HttpServletResponse response) throws Exception {
		memberService.pwdSearch(response, vo);
	}

    /* ========== 회원가입 ========== */
	@RequestMapping(value = "/member/member_signup", method=RequestMethod.POST)
	@ResponseBody
	public int member_signup(@ModelAttribute MemberVO vo, @RequestParam Map<String, Object> paramMap) {
		String regex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
		String pwExp = "^(?=.*[0-9])(?=.*[a-z]).{8,20}$";

		String id = (String) paramMap.get("id");
		String pw_01 = (String) paramMap.get("pw_01");
		String pw_02 = (String) paramMap.get("pw_02");
		String name = (String) paramMap.get("name");
		String zip = (String) paramMap.get("zip");
		String addr1 = (String) paramMap.get("addr1");
		String addr2 = (String) paramMap.get("addr2");

		String phone = "";
		String phone_first = (String) paramMap.get("m_first");
		String phone_second = (String) paramMap.get("m_second");
		String phone_third = (String) paramMap.get("m_third");

		if(id.equals("") || pw_01.equals("") || pw_02.equals("") || name.equals("") || phone_first.equals("") || phone_second.equals("") || phone_third.equals("") || zip.equals("") || addr1.equals("") || addr2.equals("")) {
			return 0;
		} else if(!pw_01.equals(pw_02)) {
			return 0;
		} else if(name.length() > 20) {
			return 0;
		} else if(!zip.matches(".*[0-9].*") || !phone_first.matches(".*[0-9].*") || !phone_second.matches(".*[0-9].*") || !phone_third.matches(".*[0-9].*")) {
			return 0;
		} else if(id.matches(regex) == false) {
			return 0;
		} else if(pw_01.matches(pwExp) == false || pw_02.matches(pwExp) == false) {
			return 0;
		} else {

			int idCheck = memberService.idCheck(id);

			if(idCheck != 0) {
				return 0;
			} else {

				phone = phone_first + "-" + phone_second + "-" + phone_third;

				vo.setMember_Id(id);		// 이메일
				vo.setMember_Pw(pw_01);		// 비밀번호
				vo.setMember_Name(name);	// 이름
				vo.setMember_Phone(phone);	// 번호
				vo.setMember_Zip(zip);		// 우편번호
				vo.setMember_Addr1(addr1);	// 주소1
				vo.setMember_Addr2(addr2);	// 주소2
				vo.setMember_Content("" + name + " 님 회원가입 해주셔서 감사합니다. 이메일 인증 후 로그인 가능합니다.");

    	    	/* =================== 행동 문자 ================ */
    			sendSms.send(vo);

				return memberService.signup(vo);
			}
		}
	}

    /* ========== 회원정보 수정 ========== */
	@RequestMapping(value = "/info/member_Update", method=RequestMethod.POST)
	@ResponseBody
	public int member_Update(@ModelAttribute MemberVO vo, HttpServletRequest request, @RequestParam Map<String, Object> paramMap) {
		String id = (String) request.getSession().getAttribute("member_Id");
		String name = (String) paramMap.get("name");
		String zip = (String) paramMap.get("zip");
		String addr1 = (String) paramMap.get("addr1");
		String addr2 = (String) paramMap.get("addr2");

		String phone = "";
		String phone_first = (String) paramMap.get("m_first");
		String phone_second = (String) paramMap.get("m_second");
		String phone_third = (String) paramMap.get("m_third");

		if(!id.equals((String) request.getSession().getAttribute("member_Id"))) {
			return 0;
		} else if(id.equals(null) || name.equals(null) || zip.equals(null) || addr1.equals(null) || addr2.equals(null) || phone_first.equals(null) || phone_second.equals(null) || phone_third.equals(null) || id.equals("") || name.equals("") || zip.equals("") || addr1.equals("") || addr2.equals("") || phone_first.equals("") || phone_second.equals("") || phone_third.equals("")) {
			return 0;
		} else if(name.length() > 20) {
			return 0;
		} else if(!zip.matches(".*[0-9].*") || !phone_first.matches(".*[0-9].*") || !phone_second.matches(".*[0-9].*") || !phone_third.matches(".*[0-9].*")) {
			return 0;
		} else {

			phone = phone_first + "-" + phone_second + "-" + phone_third;

			vo.setMember_Id(id);
			vo.setMember_Name(name);
			vo.setMember_Zip(zip);
			vo.setMember_Addr1(addr1);
			vo.setMember_Addr2(addr2);
			vo.setMember_Phone(phone);

			return memberService.userUpdate(vo);
		}
	}

    /* ========== 로그아웃 ( 세션종료 ) ========== */
	@RequestMapping("/member/logout")
	public String logout(HttpServletRequest request, HttpSession session) {
		String referer = request.getHeader("Referer");
		memberService.logout(session);

		return "redirect:" + referer + "";
	}

    /* ========== 회원 프로필 등록 ========== */
	@RequestMapping("/info/upload")
	@ResponseBody
	public Map<String, Object> upload( @RequestParam("f_profile") MultipartFile file, MemberVO vo, HttpServletRequest request ) {
		Map<String, Object> map = new HashMap<String, Object>();									// result

		String filename = "";
		String saveName = "";

		HttpSession session = request.getSession();													// session
		String id = (String) session.getAttribute("member_Id");										// now User
		String rootPath = session.getServletContext().getRealPath("/resources/upload/profile/");	// root

        try {

        	UUID uuid = UUID.randomUUID();
        	filename = uuid.toString() + "_" + file.getOriginalFilename();

            long fileSize = file.getSize();
        	int idx = filename.lastIndexOf(".");

        	String extension = filename.substring( idx + 1 );
        	String regEx = "(jpg|jpeg|JPEG|JPG|png|PNG|gif|GIF|BMP|bmp)";

    		if(extension.matches(regEx)) {
            	if(fileSize > 0) {
                	/* ======== 존재 여부 체크 이후 파입업로드 ======== */
                	File hasFolder = new File(rootPath + id + "/");
                	if (!hasFolder.exists()) {
                		hasFolder.mkdir();
                	}

                	/* ======== 이전 파일 삭제 ======== */
                	try {
                		File[] fileList = hasFolder.listFiles(); 	// Get FileList

                		for (int j = 0; j < fileList.length; j++) {
                			fileList[j].delete();					// File Delete
                		}
					} catch (Exception e) {
						e.printStackTrace();
					}

                	/* ======== 폴더 존재 여부 ======== */
            		File rootFile = new File(rootPath + id + "/" + filename);

            		vo.setMember_Id(id);
            		vo.setMember_Profile(filename);

                	/* ======== DB 저장 / File Save ======== */
    				log.debug("=========================================");
    				log.debug("name : " + file.getName());
    				log.debug("filename : " + file.getOriginalFilename());
    				log.debug("rootFile : " + rootFile);
    				log.debug("size : " + file.getSize());

                	memberService.getProfileUpload(vo);
                	file.transferTo(rootFile);

                	/* ========= 세션 유효성 검사 ============ */
                	session.removeAttribute("member_Profile");
                	session.setAttribute("member_Profile", filename);

    				map.put("code", "OK");
            	} else {
        			map.put("code", "SIZE");
        			log.info("File Size Error");			// 파일 사이즈 오류
            	}
    		} else {
    			map.put("code", "FORMAT");
    			log.info("File Format Error");				// 파일 업로드 형식 오류
    		}
        } catch(IOException e) {
        	e.printStackTrace();
        }

		return map;
	}
}