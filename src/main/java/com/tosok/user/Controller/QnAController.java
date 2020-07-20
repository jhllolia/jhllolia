package com.tosok.user.Controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tosok.user.Until.PageMaker;
import com.tosok.user.Until.SearchCriteria;
import com.tosok.user.Until.SendSms;
import com.tosok.user.VO.MemberVO;
import com.tosok.user.VO.ProductVO;
import com.tosok.user.VO.QnaVO;
import com.tosok.user.service.MemberService;
import com.tosok.user.service.ReserveService;

@Controller
public class QnAController {
	protected Log log = LogFactory.getLog(QnAController.class);
	// 사업자 : 403 - 15 - 94623

	public final static String HOST_PHONE = "010-2969-6918";

	@Autowired
	private MemberService memberService;

	@Autowired
	private ReserveService reserveService;

    @Autowired
    private SendSms sendSms;

    /* ========== Load Product ========== */
	@RequestMapping(value = "/productLoad/{num}")
	@ResponseBody
	public Map<String, Object> load_product_num(HttpSession session, ProductVO vo, @PathVariable String num) {
		vo.setPRODUCT_SEQ(Integer.parseInt(num));

		Map<String, Object> map = reserveService.selectQnaProductInfo(vo);
		map.put("member_Phone", (String) session.getAttribute("member_Phone"));

		return map;
	}

	@RequestMapping(value = "/qna/questionRes")
	@ResponseBody
	public int question_user(HttpSession session, HttpServletRequest request, QnaVO vo, MemberVO mem) {
		String sProduct = request.getParameter("check");
		String sOption = request.getParameter("select");
		String sOptionTxt = request.getParameter("txt");
		String title = request.getParameter("tit");
		String content = request.getParameter("content");

		if(!sProduct.equals("PRODUCT_QA") && !sProduct.equals("SHIPPING_QA") && !sProduct.equals("UNTIL_QA")) {
			return 0;
		} else if(sProduct.equals("") || title.equals("") || content.equals("")) {
			return 0;
		} else {

			vo.setMEMBER_SEQ((int) session.getAttribute("member_Seq"));
			vo.setMEMBER_ID((String) session.getAttribute("member_Id"));
			vo.setMEMBER_QNA_PHONE((String) session.getAttribute("member_Phone"));
			vo.setMEMBER_QNA_CAT(sProduct);
			vo.setMEMBER_QNA_TITLE(title);
			vo.setMEMBER_QNA_CONTENT(content);

			if(sProduct.equals("PRODUCT_QA")) {
				vo.setPRODUCT_SEQ(Integer.parseInt(sOption));
				vo.setPRODUCT_NAME(sOptionTxt);
			} else {
				vo.setPRODUCT_SEQ(0);
				vo.setPRODUCT_NAME("X");
			}

			int result = memberService.insertQnAProduct(vo);

			if(result > 0) {
				/* =================== 행동 문자 ================ */
    			mem.setMember_Phone(HOST_PHONE);
    			mem.setMember_Content("어양토속식품 : " + sProduct + " => " + title + "");
    			sendSms.send(mem);

				return result;
			} else {
				return 0;
			}
		}
	}

	@RequestMapping(value = "/qnaState/{num}/{state}")
	@ResponseBody
	public int qna_stateChange(@PathVariable int num, @PathVariable String state, QnaVO vo) {
		vo.setINTSEQ(num);

		if(state.equals("Y")) {
			vo.setMEMBER_QNA_STATE("N");
		} else {
			vo.setMEMBER_QNA_STATE("Y");
		}

		return memberService.updateAdminQnAState(vo);
	}

    /* ==========  ========== */
	@RequestMapping(value = "/info/customer")
	public ModelAndView qna_customer(ProductVO vo) {
		ModelAndView mav = new ModelAndView();

		mav.addObject("list", reserveService.selectTableList(vo));
		return mav;
	}

    /* ==========  ========== */
	@RequestMapping(value = "/linkQna")
	public ModelAndView product_link(@ModelAttribute("scri") SearchCriteria scri, HttpServletRequest request, HttpSession session, QnaVO vo) {
		ModelAndView mav = new ModelAndView();
		PageMaker pageMaker = new PageMaker();

		int seq = (int) request.getSession().getAttribute("member_Seq");
		String sessionId = (String) request.getSession().getAttribute("member_Id");
		String requestId = (String) request.getParameter("id");

		if(sessionId.equals("") || !sessionId.equals(requestId)) {
			mav.setViewName("redirect:/member/login");
			mav.addObject("msg", "failure");
		} else {
			vo.setMEMBER_SEQ(seq);

			scri.setSeq(seq);
			pageMaker.setCri(scri);
			pageMaker.setTotalCount(memberService.selectQnACount(vo));

			mav.addObject("msg", "success");
			mav.addObject("list", memberService.selectQnAList(scri));
			mav.addObject("pageMaker", pageMaker);
	    	mav.setViewName("redirect:/info/info_qna");
		}

		return mav;
	}

    /* ==========  ========== */
	@RequestMapping(value = "/info/info_qna")
	public ModelAndView product_qna(@ModelAttribute("scri") SearchCriteria scri, HttpSession session, QnaVO vo) {
		ModelAndView mav = new ModelAndView();
		PageMaker pageMaker = new PageMaker();
		int seq = (int) session.getAttribute("member_Seq");

		if(seq == 0) {
			mav.setViewName("member/login");
			mav.addObject("msg", "failure");
		} else {
			vo.setMEMBER_SEQ(seq);

			scri.setSeq(seq);
			pageMaker.setCri(scri);
			pageMaker.setTotalCount(memberService.selectQnACount(vo));

			mav.addObject("msg", "success");
			mav.addObject("list", memberService.selectQnAList(scri));
			mav.addObject("pageMaker", pageMaker);
	    	mav.setViewName("/info/info_qna");
		}

		return mav;
	}

	/* ==========  ========== */
	@RequestMapping(value = "/qna/sendAdmin")
	@ResponseBody
	public int sendAdminQnA(HttpServletRequest request, QnaVO vo, MemberVO mem) {
		String num = (String) request.getParameter("num");
		String content = (String) request.getParameter("content");

		if(num.equals("") || content.equals("")) {
			return 0;
		} else {
			String str = num.replaceAll("[^0-9]","");

			vo.setINTSEQ(Integer.parseInt(str));
			vo.setADMIN_QNA_CONTENT(content);

			int result = memberService.updateAdminQnAData(vo);

			/* ========== 문자메세지 send ========== */
			if(result > 0) {
    			/* =================== 행동 문자 ================ */
				String result_cat = vo.getMEMBER_QNA_CAT();

				if(result_cat.equals("PRODUCT_QA")) {
					result_cat = "상품문의";
				} else if(result_cat.equals("SHIPPING_QA")) {
					result_cat = "배송문의";
				} else {
					result_cat = "기타문의";
				}

    			mem.setMember_Phone(vo.getMEMBER_QNA_PHONE());
    			mem.setMember_Content("어양토속식품 => " + result_cat + " 에 답변이 달렸습니다. 웹사이트에서 확인해주세요.");
    			sendSms.send(mem);

    			return result;
			} else {
				return 0;
			}
		}
	}

	/* ==========  ========== */
	@RequestMapping(value = "/qna/userUpdate")
	@ResponseBody
	public int updateQnA(HttpServletRequest request, QnaVO vo, HttpSession session) {
		int seq = (int) session.getAttribute("member_Seq");
		String num = (String) request.getParameter("num");
		String content = (String) request.getParameter("content");

		if(num.equals("") || content.equals("")) {
			return 0;
		} else {
			String str = num.replaceAll("[^0-9]","");

			vo.setINTSEQ(Integer.parseInt(str));
			vo.setMEMBER_SEQ(seq);
			vo.setMEMBER_QNA_CONTENT(content);

			return memberService.updateUserQnAData(vo);
		}
	}

	/* ========== Load Product ========== */
	@RequestMapping(value = "/send/productQnA")
	@ResponseBody
	public int sendQnA(HttpServletRequest request, QnaVO vo, HttpSession session) {
		int seq = (int) session.getAttribute("member_Seq");
		String num = (String) request.getParameter("num");
		String name = (String) request.getParameter("name");
		String type = (String) request.getParameter("type");
		String title = (String) request.getParameter("txt");
		String content = (String) request.getParameter("content");

		String tel_01 = (String) request.getParameter("phone_01");
		String tel_02 = (String) request.getParameter("phone_02");
		String tel_03 = (String) request.getParameter("phone_03");

		if(!type.equals("PRODUCT_QA") && !type.equals("SHIPPING_QA") && !type.equals("UNTIL_QA")) {
			return 0;
		} else if(type.equals("") || name.equals("") || title.equals("") || content.equals("")) {
			return 0;
		} else if(!tel_01.matches(".*[0-9].*") || !tel_02.matches(".*[0-9].*") || !tel_03.matches(".*[0-9].*")) {
			return 0;
		} else {
			vo.setPRODUCT_SEQ(Integer.parseInt((String) num));
			vo.setPRODUCT_NAME(name);
			vo.setMEMBER_SEQ(seq);
			vo.setMEMBER_ID((String) session.getAttribute("member_Id"));
			vo.setMEMBER_QNA_CAT(type);
			vo.setMEMBER_QNA_TITLE(title);
			vo.setMEMBER_QNA_CONTENT(content);
			vo.setMEMBER_QNA_PHONE(tel_01 + "-" + tel_02 + "-" + tel_03);

			return memberService.insertQnAProduct(vo);
		}
	}

}