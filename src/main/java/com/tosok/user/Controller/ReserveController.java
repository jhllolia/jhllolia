package com.tosok.user.Controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.tosok.user.IamPort.IamportClient;
import com.tosok.user.IamPortRequest.CancelData;
import com.tosok.user.IamPortResponse.IamportResponse;
import com.tosok.user.IamPortResponse.Payment;
import com.tosok.user.Until.PageMaker;
import com.tosok.user.Until.ProductSellList;
import com.tosok.user.Until.SearchCriteria;
import com.tosok.user.Until.SendSms;
import com.tosok.user.VO.CartVO;
import com.tosok.user.VO.MemberVO;
import com.tosok.user.VO.PayVO;
import com.tosok.user.VO.ProductVO;
import com.tosok.user.VO.ReviewVO;
import com.tosok.user.service.MemberService;
import com.tosok.user.service.ReserveService;

@Controller
public class ReserveController {
	protected Log log = LogFactory.getLog(ReserveController.class);

	public final static int	LIMIT_COST = 30000;
	public final static String HOST_PHONE = "010-2969-6918";
	public final static String DATA_KEY = "imp78569276";
	public final static String API_KEY = "0267334428942794";																				// 아임포트 API
	public final static String API_SECRET = "F0rRamfbzSXTgpnvikZA6W4QPExKUjrzkSrvfHGPAEIQ36lmrkXyh2o7HVRBfibzbC4qjLfTpYJt55Qw";				// 아임포트 API

	@Autowired
    private ReserveService reserveService;			// 테이블 예약 Service

    @Autowired
    private MemberService memberService;			// 사용자 Service
	
	@Autowired
	private ProductSellList productList;

    @Autowired
    private SendSms sendSms;						// 문자 메세지

    /* ========== ADMIN 회원 결제 취소 ========== */
	@RequestMapping("/admin/refund")
	@ResponseBody
	public Map<String, Object> admin_refund(HttpServletRequest request, CancelData cancel, MemberVO mem, PayVO vo) throws Exception {
		IamportClient client = new IamportClient(API_KEY, API_SECRET);
		Map<String, Object> reVal = new HashMap<String, Object>();

		String order = request.getParameter("order");				//
		String arr = request.getParameter("arr");					//

		if(order.equals("") || arr.equals("")) {
			reVal.put("message", "존재하지 않는 명령입니다");
			return reVal;
		} else {
			vo.setORDER_NUM(order);
			vo.setLIST_KEYWORD(arr);

		    /* ========== 해당 제품정보 불러오기 ========== */
			List<PayVO> list = reserveService.selectAdminRefundList(vo);

			for (int i = 0; i < list.size(); i++) {
				int total = reserveService.selectOrderPayCount(vo);						// 취소 되지 않는 상품 개수
		    	int count = reserveService.selectProductCount(vo);						// 취소된 상품 개수
				int price = Integer.parseInt(list.get(i).getPRODUCT_PRICE());			// 
				int qty = Integer.parseInt(list.get(i).getPRODUCT_QTY());				// 

				cancel.setPay_seq(list.get(i).getPAY_SEQ());							// 
				cancel.setImp_uid(list.get(i).getIMP_UID());							// 아임포트 고유번호
				cancel.setMerchant_uid(list.get(i).getMERCHANT_UID());					// 가맹점에서 전달한 거래 고유번호
				cancel.setReason("ADMIN CANCELLED");									// 

				if(total == 1) {
					cancel.setAmount(Integer.toString(price * qty + reserveService.selectCancelShippingCost(vo)));
				} else {
					cancel.setAmount(Integer.toString(price * qty));
				}
				
			    /* ========== 취소 Handler ========== */
				IamportResponse<Payment> cancelPay = client.cancelPayment(cancel);

				log.info("====================================");
				log.info("cancelPay : " + cancelPay.getCode());
				log.info("cancelPay : " + cancelPay.getMessage());

				if(cancelPay.getCode() == 0) {
					cancel.setStatus((String) cancelPay.getResponse().getStatus());
					cancel.setReceipt_url((String) cancelPay.getResponse().getReceiptUrl());

				    /* ========== 결제 취소 이후 결제 상태 변경 ========== */
					reserveService.updatePaymentStatus(cancel);

	    	    	/* =========== 행동 문자 ================ */
	    			mem.setMember_Phone(cancelPay.getResponse().getBuyerTel());
	    			mem.setMember_Content("" + cancelPay.getResponse().getBuyerName() + "님의  " + cancelPay.getResponse().getName() + "을 " + qty + " 개 취소 => " + price + " 원. 취소 금액은 결제된 PG사 정책에 따라 금액 환불이 진행됩니다.");
	    			sendSms.send(mem);

					reVal.put("message", list.size() + " 개의 상품이 성공적으로 결제 취소 되었습니다");
				} else {
					reVal.put("message", list.size() + " 개의 상품 결과 => " + cancelPay.getMessage());
				}
			}

			return reVal;
		}
	}

	/* ==========  ========== */
	@RequestMapping("/sort/listChange")
	public void listChange(ProductVO vo, HttpServletRequest request) {
		reserveService.listSortChange(vo, request);
	}

    /* ==========  ========== */
	@RequestMapping("/bbs/product")
	@ResponseBody
	public ModelAndView product_list(ProductVO vo, ReviewVO review) {
		ModelAndView mav = new ModelAndView();

		mav.addObject("list", productList.listProductParam(vo, review));
		mav.setViewName("/bbs/product");
		return mav;
	}

	/* =============== ================ */
	@RequestMapping("/review/product")
	@ResponseBody
	public List<Map<String, Object>> reviewProduct(HttpServletRequest request, ReviewVO vo) {
		Gson gson = new Gson();
		List<Map<String, Object>> data = null;
		JsonArray jsonData = new JsonArray();

		vo.setPRODUCT_NUM((String) request.getParameter("tab"));
		List<ReviewVO> list = memberService.selectProductReview(vo);

		for (int i = 0; i < list.size(); i++) {
			JsonObject obj = new JsonObject();

			SimpleDateFormat trans = new SimpleDateFormat("yyyy.MM.dd");
			Date date = list.get(i).getVIEW_DATE();

			obj.addProperty("REVIEW_ID", list.get(i).getREVIEW_ID().substring(0, 3).concat("*****"));
			obj.addProperty("REVIEW_NAME", list.get(i).getREVIEW_NAME().substring(0, 1).concat("**"));
			obj.addProperty("PRODUCT_NUM", list.get(i).getPRODUCT_NUM());
			obj.addProperty("PRODUCT_NAME", list.get(i).getPRODUCT_NAME());
			obj.addProperty("PRODUCT_OPTION", list.get(i).getPRODUCT_OPTION());
			obj.addProperty("PRODUCT_QTY", list.get(i).getPRODUCT_QTY());
			obj.addProperty("REVIEW_REPLY_YN", list.get(i).getREVIEW_REPLY_YN());
			obj.addProperty("REVIEW_REPLY", list.get(i).getREVIEW_REPLY());
			obj.addProperty("VIEW_SEQ", list.get(i).getVIEW_SEQ());
			obj.addProperty("ORDER_NUM", list.get(i).getORDER_NUM());
			obj.addProperty("VIEW_STATE", list.get(i).getVIEW_STATE());
			obj.addProperty("VIEW_POINT", list.get(i).getVIEW_POINT());
			obj.addProperty("VIEW_CONTENT", list.get(i).getVIEW_CONTENT());
			obj.addProperty("VIEW_DATE", trans.format(date));
			obj.addProperty("VIEW_UPLOAD", list.get(i).getVIEW_UPLOAD());

			jsonData.add(obj);
		}

		data = gson.fromJson(jsonData, new TypeToken<List<Map<String, Object>>>() {}.getType());

		return data;
	}

	/* =============== ================ */
	@RequestMapping("/info/review")
	@ResponseBody
	public ModelAndView LinkReviewPage(HttpServletRequest request, PayVO vo) {
		ModelAndView mav = new ModelAndView();
		String referer = request.getHeader("Referer");

		vo.setBUYER_EMAIL((String) request.getSession().getAttribute("member_Id"));
		vo.setORDER_NUM((String) request.getParameter("num"));
		vo.setREVIEW_EXIST("0");

		List<PayVO> list = reserveService.getProductInfo(vo);

		if(list.isEmpty()) {
			mav.setViewName("redirect:" + referer + "");
		} else {
			mav.addObject("ORDER", (String) request.getParameter("num"));
			mav.addObject("list", list);
			mav.setViewName("/info/review");
		}

		return mav;
	}

	/* =============== ================ */
    @RequestMapping("/order/changeProduct")
    @ResponseBody
    public List<PayVO> getStatusProductData(PayVO vo, HttpServletRequest request, HttpSession session) {
		vo.setBUYER_SEQ((String) session.getAttribute("member_Seq").toString());
		vo.setLIST_STATE(request.getParameter("arr"));

    	return reserveService.getStatusProductData(vo);
    }

	/* =============== 결제 취소 ================ */
    @RequestMapping("/payment/cancel")
    @ResponseBody
    public Map<String, Object> post_Cancel(HttpServletRequest request, CancelData cancel, MemberVO mem, PayVO vo) throws Exception {
		IamportClient client = new IamportClient(API_KEY, API_SECRET);
		Map<String, Object> reVal = new HashMap<String, Object>();
    	JsonParser parser = new JsonParser();

    	String str = request.getParameter("data");
    	String content = "" + request.getParameter("tit") + " - " + request.getParameter("txt") + "";
    	JsonArray arr = (JsonArray) parser.parse(str);

    	vo.setORDER_NUM(request.getParameter("num"));

    	for (int i = 0; i < arr.size(); i++) {
			JsonObject data = (JsonObject) arr.get(i);

			int total = reserveService.selectOrderPayCount(vo);							// 취소 되지 않는 상품 개수
	    	int count = reserveService.selectProductCount(vo);							// 취소된 상품 개수
			int price = data.get("product_PRICE").getAsInt();							// 
			int qty = data.get("product_QTY").getAsInt();								// 

			cancel.setPay_seq(data.get("pay_SEQ").getAsInt());							// 
			cancel.setImp_uid((String) data.get("imp_UID").getAsString());				// 아임포트 고유번호
			cancel.setMerchant_uid((String) data.get("merchant_UID").getAsString());	// 가맹점에서 전달한 거래 고유번호
			cancel.setReason((String) content);											// 

			/* =============================== */
			if(total == 1) {
				cancel.setAmount(Integer.toString(price * qty + reserveService.selectCancelShippingCost(vo)));
			} else {
				cancel.setAmount(Integer.toString(price * qty));
			}

			IamportResponse<Payment> cancelPay = client.cancelPayment(cancel);

			log.info("====================================");
			log.info("cancelPay : " + cancelPay.getCode());
			log.info("cancelPay : " + cancelPay.getMessage());

			if(cancelPay.getCode() == 0) {
				cancel.setStatus((String) cancelPay.getResponse().getStatus());
				cancel.setReceipt_url((String) cancelPay.getResponse().getReceiptUrl());

				reserveService.updatePaymentStatus(cancel);

    	    	/* =================== 행동 문자 ================ */
    			mem.setMember_Phone(HOST_PHONE);
    			mem.setMember_Content("" + cancelPay.getResponse().getBuyerName() + "님의  " + cancelPay.getResponse().getName() + "을 " + qty + " 개 취소 => " + price + " 원");
    			sendSms.send(mem);

				reVal.put("message", arr.size() + " 개의 상품이 성공적으로 결제 취소 되었습니다");
			} else {
				reVal.put("message", arr.size() + " 개의 상품 결과 => " + cancelPay.getMessage());
			}
    	}

		return reVal;
    }

	/* =============== 회원 결제 리스트 ============== */
    @RequestMapping("/payment/list")
    @ResponseBody
    public Map<String, List<PayVO>> list(HttpSession session, HttpServletRequest request, ProductVO product, PayVO vo) throws Exception {
    	vo.setPAID_BEFORE_DATE(request.getParameter("before"));				// 출력할 시간 년도 - 월 - 일
    	vo.setPAID_AFTER_DATE(request.getParameter("after"));				// 
		vo.setLIST_STATE(request.getParameter("search"));					// 검색 할 카테고리
		vo.setLIST_KEYWORD(request.getParameter("keyword"));				// 검색 할 텍스트
		vo.setBUYER_PAID_STATUS(request.getParameter("status"));			// 결제 상태
		vo.setBUYER_EMAIL((String) session.getAttribute("member_Id"));		// 회원 아이디

		List<PayVO> list = memberService.memberPaymentList(vo);
		TreeMap<String, List<PayVO>> result = new TreeMap<>();

		/* =============== GROUP BY ============== */
		for(PayVO p : list) {
			if(!result.containsKey(p.getORDER_NUM())) {
				result.put(p.getORDER_NUM(), new ArrayList<>()); 
			}

			result.get(p.getORDER_NUM()).add(p);
		}

		return result.descendingMap();
    }

	/* =============== 결제 완료 페이지 ============== */
    @RequestMapping("/pay/orderComplete")
    public ModelAndView payComplete(HttpServletRequest request, PayVO vo, HttpSession session ) throws Exception {
    	ModelAndView mav = new ModelAndView();

    	String id = (String) session.getAttribute("member_Id");
    	String order = request.getParameter("order");
    	String imp = request.getParameter("imp");

    	if(id.equals("") || order.equals("") || imp.equals("")) {
    		mav.setViewName("/pay/orderMobile");
    	} else {

    		vo.setORDER_NUM(order);
    		vo.setBUYER_EMAIL(id);
    		vo.setIMP_UID(imp);

    		List<PayVO> list = reserveService.selectPayComplete(vo);

    		if(list.isEmpty()) {
        		mav.setViewName("/pay/orderMobile");
    		} else {
    			mav.addObject("order", list);
    			mav.setViewName("/pay/orderComplete");
    		}
    	}

    	return mav;
    }

	/* =============== 모바일 결제 ============== */
    @RequestMapping("/pay/orderMobile")
    public String post_order_mobile(HttpServletRequest request, CartVO cart, MemberVO mem, PayVO vo, RedirectAttributes rd, HttpSession session) throws Exception {
    	String imp_uId = (String) request.getParameter("imp_uid");
    	String merchant_Id = (String) request.getParameter("merchant_uid");

    	SimpleDateFormat order = new SimpleDateFormat("yyyyMMddHHmmss");
    	Date time = new Date();

    	if(imp_uId == null || imp_uId.equals("")) {
    		return "/pay/orderMobile";
    	} else {
    		/* =============== 결제 Imp_Uid 여부 ============== */
        	IamportClient client = new IamportClient(API_KEY, API_SECRET);
    		IamportResponse<Payment> payCheck = client.paymentByImpUid(imp_uId);

    		int code = payCheck.getCode();															// 코드
    		String status = (String) payCheck.getResponse().getStatus();							// 결제 상태

			if(code == 0) {
    			if(status.equals("paid")) {
    	    		int result = 0;

    	    		String contain_url = (String) request.getSession().getAttribute("pay_prev");
    	    		JsonObject product = (JsonObject) session.getAttribute("product");
    	    		JsonArray arr = (JsonArray) product.get("arr");

    	    		log.info("contain_url : " + contain_url);

            		/* =============== 결제 진행 ============== */
    	    		log.info("code : " + code);
    	    		log.info("rsp : " + product);

    	    		for (int i = 0; i < arr.size(); i++) {
    	    			JsonObject data = (JsonObject) arr.get(i);

    	    	    	vo.setORDER_NUM(order.format(time));

    	    	    	/* ========== 아임포트 고유 키 ========== */
    	    	    	vo.setIMP_UID((String) imp_uId);
    	    			vo.setMERCHANT_UID((String) merchant_Id);

    	    	    	/* ======================================== */
    	    			vo.setPAID_METHOD((String) payCheck.getResponse().getPayMethod());
    	    			vo.setPAID_STATUS((String) status);
    	    			vo.setPAID_PG_PROVIDER((String) payCheck.getResponse().getPgProvider());
    	    			vo.setPAID_PG_TID((String) payCheck.getResponse().getPgTid());
    	    			vo.setPAID_RECEIPT((String) payCheck.getResponse().getReceiptUrl());

    	    	    	/* ======================================== */
    	    			vo.setPRODUCT_NUM((String) data.get("seq").getAsString());
    	    			vo.setPRODUCT_PROFILE((String) data.get("profile").getAsString());
    	    			vo.setPRODUCT_NAME((String) data.get("name").getAsString());
    	    			vo.setPRODUCT_OPTION((String) data.get("option").getAsString());
    	    			vo.setPRODUCT_QTY((String) data.get("qty").getAsString());
    	    			vo.setPRODUCT_PRICE((String) data.get("price").getAsString());
    	    			vo.setPRODUCT_SHIPPING_COST((String) product.get("cost").getAsString());

    	    	    	/* ======================================== */
    	    			vo.setBUYER_SEQ((String) session.getAttribute("member_Seq").toString());
    	    			vo.setBUYER_PAID_STATUS((String) payCheck.getResponse().getStatus());
    	    			vo.setBUYER_NAME((String) payCheck.getResponse().getBuyerName());
    	    			vo.setBUYER_EMAIL((String) payCheck.getResponse().getBuyerEmail());
    	    			vo.setBUYER_TEL((String) payCheck.getResponse().getBuyerTel());
    	    			vo.setBUYER_POSTCODE((String) payCheck.getResponse().getBuyerPostcode());
    	    			vo.setBUYER_ADDR((String) payCheck.getResponse().getBuyerAddr());
    	    			vo.setBUYER_PAID_TOTAL((String) product.get("total").getAsString());
    					vo.setBUYER_MEMO("");

    	    	    	result = reserveService.insertTablePaid(vo);
    	    	    	log.info("insertTablePaid : " + result);

    	    			if(contain_url.contains("cart")) {
    	    				cart.setCART_STATE("N");
    	    				cart.setCART_MEMBER((String) payCheck.getResponse().getBuyerEmail());
    	    				cart.setCART_PRODUCT_NAME((String) data.get("name").getAsString());
    	    				cart.setCART_PRODUCT_OPTION((String) data.get("option").getAsString());

    	    				int cart_result = memberService.updateCartUnitState(cart);
	            			log.info("contain_cart : " + cart_result);
    	    			}

    	    	    	/* =================== 행동 문자 ================ */
    	    			mem.setMember_Phone(HOST_PHONE);
    	    			mem.setMember_Content("" + (String) data.get("name").getAsString() + " 을(를) " + (String) data.get("qty").getAsString() + " 개 주문");
    	    			sendSms.send(mem);
    				}

    	    		if(result == 1) {
    	    			rd.addAttribute("order", vo.getORDER_NUM());	// redirect
    	    			rd.addAttribute("imp", vo.getIMP_UID());		// redirect
    	    			return "redirect:/pay/orderComplete";			// redirect
    	    		} else {
    	    			return "redirect:/pay/orderMobile";				// redirect
    	    		}
    			} else {
            		/* =============== 결제 상태시 Redirect ( 취소 , 실패 ) ============== */
    	    		return "/pay/orderMobile";
    			}
    		} else if (code == -1) {
        		/* =============== 결제 진행 취소 ============== */
        		return "/pay/orderMobile";
    		}

    		return "/pay/orderMobile";
    	}
    }

	/* =============== 결제 진행 ============== */
    @RequestMapping("/payment/payInsert")
    @ResponseBody
    public Map<String, Object> pay_insert( @RequestParam Map<String, Object> paramMap, MemberVO mem, PayVO vo, HttpSession session ) throws Exception {
    	SimpleDateFormat order = new SimpleDateFormat("yyyyMMddHHmmss");
    	Map<String, Object> retVal = new HashMap<String, Object>();

    	Date time = new Date();
    	String id = (String) session.getAttribute("member_Id");

    	if(id.equals("")) {
    		return retVal;
    	} else {
    		int result = 0;

    		JsonParser parser = new JsonParser();
    		JsonObject product = (JsonObject) session.getAttribute("product");
    		JsonArray arr = (JsonArray) product.get("arr");

    		for (int i = 0; i < arr.size(); i++) {
    			JsonObject data = (JsonObject) arr.get(i);
    	    	JsonObject obj = (JsonObject) parser.parse((String) paramMap.get("rsp"));

    	    	vo.setORDER_NUM(order.format(time));

    	    	/* ========== 아임포트 고유 키 ========== */
    	    	vo.setIMP_UID((String) obj.get("imp_uid").getAsString());
    			vo.setMERCHANT_UID((String) obj.get("merchant_uid").getAsString());

    	    	/* ======================================== */
    			vo.setPAID_METHOD((String) obj.get("pay_method").getAsString());
    			vo.setPAID_STATUS((String) obj.get("status").getAsString());
    			vo.setPAID_PG_PROVIDER((String) obj.get("pg_provider").getAsString());
    			vo.setPAID_PG_TID((String) obj.get("pg_tid").getAsString());
    			vo.setPAID_RECEIPT((String) obj.get("receipt_url").getAsString());

    	    	/* ======================================== */
    			vo.setPRODUCT_NUM((String) data.get("seq").getAsString());
    			vo.setPRODUCT_PROFILE((String) data.get("profile").getAsString());
    			vo.setPRODUCT_NAME((String) data.get("name").getAsString());
    			vo.setPRODUCT_OPTION((String) data.get("option").getAsString());
    			vo.setPRODUCT_QTY((String) data.get("qty").getAsString());
    			vo.setPRODUCT_PRICE((String) data.get("price").getAsString());
    			vo.setPRODUCT_SHIPPING_COST((String) product.get("cost").getAsString());

    	    	/* ======================================== */
    			vo.setBUYER_SEQ((String) session.getAttribute("member_Seq").toString());
    			vo.setBUYER_PAID_STATUS((String) obj.get("success").getAsString());
    			vo.setBUYER_NAME((String) obj.get("buyer_name").getAsString());
    			vo.setBUYER_EMAIL((String) obj.get("buyer_email").getAsString());
    			vo.setBUYER_TEL((String) obj.get("buyer_tel").getAsString());
    			vo.setBUYER_POSTCODE((String) obj.get("buyer_postcode").getAsString());
    			vo.setBUYER_ADDR((String) obj.get("buyer_addr").getAsString());
    			vo.setBUYER_PAID_TOTAL((String) product.get("total").getAsString());
				vo.setBUYER_MEMO((String) paramMap.get("memo"));

    	    	result = reserveService.insertTablePaid(vo);
    	    	log.info("insertTablePaid : " + result);

    	    	/* =================== 제품 문자 ================ */
    			mem.setMember_Phone(HOST_PHONE);
    			mem.setMember_Content("" + (String) data.get("name").getAsString() + " 을(를) " + (String) data.get("qty").getAsString() + " 개 주문");
    			sendSms.send(mem);
			}

    		if(result == 1) {
    			retVal.put("order", vo.getORDER_NUM());
    			retVal.put("imp", vo.getIMP_UID());
    		} else {
				log.info("insertTablePaid : " + "Fail");
    		}
    	}

		return retVal;
    }

    /* =============== ================ */
    @RequestMapping("/info/returnProduct")
    public ModelAndView orderReturn(HttpServletRequest request, PayVO vo) {
    	ModelAndView mav = new ModelAndView();

    	String id = (String) request.getSession().getAttribute("member_Id");
    	String arr = request.getParameter("arr");

    	if(id.equals("") || arr.equals("")) {
    		mav.setViewName("/info/info_payment");
    	} else {
        	vo.setBUYER_EMAIL((String) request.getSession().getAttribute("member_Id"));
        	vo.setLIST_KEYWORD(request.getParameter("arr"));

        	List<PayVO> list = memberService.selectProductReturnSEQ(vo);

        	if(list.isEmpty()) {
        		mav.setViewName("/info/info_payment");
        	} else {
        		mav.addObject("list", list);
        		mav.addObject("keyword", request.getParameter("arr"));
        		mav.setViewName("/info/info_return");
        	}
    	}

    	return mav;
    }

    /* =============== ================ */
	@RequestMapping("/productReturn/apply")
	@ResponseBody
	public int returnApply(HttpServletRequest request, PayVO vo) {
		vo.setBUYER_EMAIL((String) request.getSession().getAttribute("member_Id"));
		vo.setLIST_KEYWORD(request.getParameter("productNum"));

    	List<PayVO> list = memberService.selectProductReturnSEQ(vo);

    	if(list.isEmpty()) {
    		return 0;
    	} else {

    		String radioChk = request.getParameter("radioCheck");
    		String boxChk = request.getParameter("boxCheck");
    		String whyTxt = request.getParameter("whyText");

    		if(radioChk.equals("") || boxChk.equals("") || whyTxt.equals("")) {
    			return 0;
    		} else if(!radioChk.equals("shipping_return") && !radioChk.equals("shipping_exchange")) {
    			return 0;
    		} else if(!boxChk.equals("A") && !boxChk.equals("B") && !boxChk.equals("C") && !boxChk.equals("D") && !boxChk.equals("E") && !boxChk.equals("F") && !boxChk.equals("G") && !boxChk.equals("H") && !boxChk.equals("I")) {
    			return 0;
    		} else {

    			vo.setPAID_STATUS(radioChk);
    			vo.setBUYER_RETURN_STATE(boxChk);
    			vo.setBUYER_RETURN_CONTENT(whyTxt);

    			return reserveService.applyReturnProduct(vo);
    		}
    	}
	}

	/* =============== ================ */
    @RequestMapping("/info/orderStatus")
    public ModelAndView orderStatus(MemberVO mem, PayVO vo, HttpServletRequest request, HttpSession session, Model model) {
    	ModelAndView mav = new ModelAndView();

		String member_info = (String) session.getAttribute("member_Id");	// 로그인 된 세션
		if(member_info == null) member_info = null;

		vo.setBUYER_EMAIL(member_info);
		vo.setORDER_NUM((String) request.getParameter("key"));

		List<PayVO> list = reserveService.getProductInfo(vo);

		/*

		JsonArray jsonData = new JsonArray();
    	JsonObject json = new JsonObject();

		for (int i = 0; i < list.size(); i++) {
			JsonObject obj = new JsonObject();

			obj.addProperty("PAID_STATUS", list.get(i).getPAID_STATUS());

			jsonData.add(obj);
		}

    	json.addProperty("PRODUCT_SHIPPING_NUM", list.get(0).getPRODUCT_SHIPPING_NUM());
    	json.addProperty("PRODUCT_SHIPPING_COURIER", list.get(0).getPRODUCT_SHIPPING_COURIER());
    	json.addProperty("PRODUCT_SHIPPING_COST", list.get(0).getPRODUCT_SHIPPING_COST());
    	json.add("ARR", jsonData);

    	*/

		/* ============================================== */
		int result = reserveService.selectMemberValid(vo);

		if(member_info == null) {
			mav.setViewName("member/login");
			mav.addObject("msg", "failure");
		} else {
			if(result == 0) {
				mav.addObject("msg", "fail");
		    	mav.setViewName("/info/info_payment");
			} else {
				mav.addObject("msg", "success");
		    	mav.addObject("order", list);
		    	mav.setViewName("/info/info_order");
			}
		}

    	return mav;
    }

	/* =============== 결제 상세정보  ============== */
    @RequestMapping("/pay/orderDetail")
    public String post_order(HttpServletRequest request, MemberVO vo, ProductVO product, HttpSession session, Model model) throws JsonProcessingException {
    	String referer = request.getHeader("Referer");
    	JsonParser parser = new JsonParser();
    	JsonObject json = new JsonObject();

    	Gson gson = new Gson();
    	List<Map<String, Object>> list = null;

    	String seq = request.getParameter("seq");				// 
    	String user = (String) request.getParameter("arr");		// 배열

    	if (user == null || user.equals("")) {
    		return "redirect:" + referer;
    	} else {
        	/* =============== 해당 제품 정보 불러오기  ============== */
        	product.setPRODUCT_SEQ(Integer.parseInt((String) seq));
        	ProductVO result = reserveService.selectTableInfo(product);

        	/* ============================================== */
        	String str = result.getPRODUCT_SUB_INFO();			// DB 데이터 불러오기 

        	JsonArray jsonData = new JsonArray();
        	JsonArray arr = (JsonArray) parser.parse(str);		// DB 데이터
        	JsonArray data = (JsonArray) parser.parse(user);	// 사용자 체크 리스트

        	int total = 0;												// 총액
        	int cost = Integer.parseInt(result.getPRODUCT_SHIPPING());	// 해당 제품 배송비

        	for (int i = 0; i < arr.size(); i++) {
        		for (int j = 0; j < data.size(); j++) {
        			JsonObject obj = new JsonObject();
        			JsonObject saveData = (JsonObject) arr.get(i);
        			JsonObject userData = (JsonObject) data.get(j);

        			String saveNum = saveData.get("num").getAsString().replaceAll("[^0-9]", "");
        			String userNum = userData.get("num").getAsString().replaceAll("[^0-9]", "");

        			if(!saveNum.matches(".*[0-9].*") || !userNum.matches(".*[0-9].*")) {
        				break;
        			} else {

            			if(saveNum.equals(userNum)) {
            				/* ============================= */
            				int qty = Integer.parseInt(userData.get("qty").getAsString());
            				int price = Integer.parseInt(saveData.get("price").getAsString());

            				obj.addProperty("num", saveData.get("num").getAsString());
            				obj.addProperty("name", saveData.get("name").getAsString());
            				obj.addProperty("option", saveData.get("option").getAsString());
            				obj.addProperty("price", saveData.get("price").getAsString());
            				obj.addProperty("sale", saveData.get("sale").getAsString());
            				obj.addProperty("sell", saveData.get("sell").getAsString());

            				obj.addProperty("seq", userData.get("seq").getAsString());
            				obj.addProperty("order", userData.get("order").getAsString());
            				obj.addProperty("profile", result.getPRODUCT_FILE_NAME());
            				obj.addProperty("qty", userData.get("qty").getAsString());

            				/* ============================= */
            				total = (total + (price * qty));

            				jsonData.add(obj);
            			}
        			}
        		}
        	}

        	if(total >= LIMIT_COST) {
        		cost = 0;
        	}

        	/* ========= 데이터 출력 ============ */
        	json.addProperty("total", total);
        	json.addProperty("cost", cost);
        	json.add("arr", jsonData);

    		list = gson.fromJson(jsonData, new TypeToken<List<Map<String, Object>>>() {}.getType());

        	/* ========= 세션 유효성 검사 ============ */
        	JsonObject beforeSession = (JsonObject) session.getAttribute("product");

        	if(beforeSession != null) {
        		session.removeAttribute("product");
        	}

        	session.setAttribute("product", json);

        	/* ========= 데이터 출력 ============ */
        	String member = (String) session.getAttribute("member_Id");			// 
        	vo.setMember_Id(member);											// 예약 사용자 정보 가져오기

        	model.addAttribute("PRODUCT", json);
        	model.addAttribute("ARR", list);
        	model.addAttribute("LIMIT", LIMIT_COST);
        	model.addAttribute("COST", cost);
        	model.addAttribute("user", memberService.memberInfo(vo));

        	return "/pay/orderDetail";
    	}
    }

	/* =============== 결제 전 검증 ============== */
    @RequestMapping("/final/price")
    @ResponseBody
    public Map<String, Object> finalPrice(HttpServletRequest request, HttpSession session) {
    	Map<String, Object> retVal = new HashMap<String, Object>();

    	JsonObject content = (JsonObject) session.getAttribute("product");

    	String pg = request.getParameter("selectPg");
    	String method = request.getParameter("selectMethod");
    	String checkNm = request.getParameter("checkNm");
    	String name = request.getParameter("user_name");
    	String email = request.getParameter("user_email");
    	String phone = request.getParameter("user_phone");
    	String zip = request.getParameter("zip");
    	String addr1 = request.getParameter("addr1");
    	String addr2 = request.getParameter("addr2");

    	if(!pg.equals("html5_inicis") && !pg.equals("kakaopay")) {
    		return retVal;
    	} else if(!method.equals("card") && !method.equals("trans")) {
    		return retVal;
    	} else if(zip.equals("") || addr1.equals("") || addr2.equals("")) {
    		return retVal;
    	} else {

        	retVal.put("key", DATA_KEY);
        	retVal.put("pg", pg);
        	retVal.put("method", method);
        	retVal.put("checkNm", checkNm);
        	retVal.put("price", content.get("total").getAsInt() + content.get("cost").getAsInt());
        	retVal.put("email", email);
        	retVal.put("name", name);
        	retVal.put("phone", phone);
        	retVal.put("zip", zip);
        	retVal.put("addr1", addr1);
        	retVal.put("addr2", addr2);

        	return retVal;
    	}
    }

	/* =============== 테이블 리스트 별 번호  ============== */
    @RequestMapping("/tab/{seq}")
    public ModelAndView seat_table( @PathVariable Integer seq, ProductVO vo, ReviewVO review, HttpSession session ) {
    	ModelAndView mav = new ModelAndView();
    	JsonParser parser = new JsonParser();

    	Gson gson = new Gson();
    	List<Map<String, Object>> list = null;

    	vo.setPRODUCT_SEQ(seq);										// 제품정보
    	review.setPRODUCT_NUM(Integer.toString(seq));				// 리뷰 

    	ProductVO seat = reserveService.selectTableInfo(vo);

		/* =============== 리스트로 변환 ============== */
    	JsonArray arr = (JsonArray) parser.parse(seat.getPRODUCT_SUB_INFO());
    	list = gson.fromJson(arr, new TypeToken<List<Map<String, Object>>>() {}.getType());

		/* =============== 리뷰 평균 / 카운트 ============== */
		ReviewVO count = reserveService.selectProductReviewCount(review);

		/* =============== ============== */
		session.removeAttribute("product");

		mav.addObject("REVIEW_AVG", count.getAVG_COUNT());
		mav.addObject("REVIEW_COUNT", count.getREV_COUNT());
		mav.addObject("myPushList", list);
    	mav.addObject("seat", seat);								// 테이블 정보 출력
		mav.addObject("file", reserveService.selectTableFile(vo));	// 테이블 슬라이드 이미지, 로고
    	mav.setViewName("/product/product_Table");
    	return mav;
    }

	/* =============== 테이블 이용 인원 체크  ============== */
    @RequestMapping("/time/person")
    @ResponseBody
    public int person_count( HttpServletRequest request, PayVO vo ) {
    	vo.setTAB_NUM(request.getParameter("seq"));					// 테이블 번호
    	vo.setPAID_USER_DATE(request.getParameter("date"));			// 선택 일
    	vo.setPAID_USER_TIME(request.getParameter("time"));			// 선택 시간
		return reserveService.selectPersonCount(vo);
    }

	/* =============== 로그인 체크  ============== */
    @RequestMapping("/check/login")
	@ResponseBody
    public String get_user( HttpSession session ) throws Exception {
		return (String) session.getAttribute("member_Id");
    }

	/* =============== ADMIN 테이블 리스트  ============== */
	@RequestMapping("/product/table")
	public ModelAndView seat(@ModelAttribute("scri") SearchCriteria scri) {
		ModelAndView mav = new ModelAndView();
		PageMaker pageMaker = new PageMaker();

		int count = reserveService.selectTableCount(scri);

		scri.setPerPageNum(12);											// 출력 테이블 번호
		pageMaker.setCri(scri);
		pageMaker.setTotalCount(count);									// 테이블 번호

		mav.addObject("list", reserveService.listTableOrder(scri));		// 등록된 테이블 출력
		mav.addObject("pageMaker", pageMaker);							// 페이징
		mav.setViewName("/product/table");
		return mav;
	}

	/* =============== ADMIN 테이블 삭제  ============== */
	@Secured("ROLE_ADMIN")
	@RequestMapping("/seat/delete/{state}/{seq}")
	@ResponseBody
	public String tb_del( @PathVariable String state, @PathVariable int seq, ProductVO vo, HttpServletRequest request ) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		String str = "";

		vo.setPRODUCT_SEQ(seq);
		vo.setPRODUCT_STATE(state);

		int result = reserveService.deleteSeatTable(vo, request);
		if( result > 0 ) {
			str = mapper.writeValueAsString(true);		// SUCCESS
		}

    	return str;
	}

	/* =============== ADMIN 수정 될 테이블  ============== */
	@RequestMapping("/modify/{seq}")
	public ModelAndView tb_modify( ProductVO vo, @PathVariable int seq ) {
		ModelAndView mav = new ModelAndView();
		vo.setPRODUCT_SEQ(seq);

		mav.addObject("seat", reserveService.selectTableInfo(vo));	// 테이블 출력
		mav.addObject("file", reserveService.selectTableFile(vo));	// 테이블 슬라이드, 로고
		mav.setViewName("/product/product_Modify");
		return mav;
	}

	/* =============== ADMIN 테이블 등록 페이지  ============== */
	@RequestMapping("/product/product_Insert")
	public ModelAndView product_Insert() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/product/product_Insert");
		return mav;
	}

	/* =============== ADMIN 테이블 등록 데이터  ============== */
	@RequestMapping("/product/insert")
	@ResponseBody
	public String insert(ProductVO vo, HttpServletRequest request) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		String str = "";

		vo.setPRODUCT_SALE_MODE(request.getParameter("mode"));
		vo.setPRODUCT_INFO(request.getParameter("info"));
		vo.setPRODUCT_SUB_INFO(request.getParameter("product_Arr"));
		vo.setPRODUCT_TITLE(request.getParameter("title"));
		vo.setPRODUCT_CONTENT(request.getParameter("content"));
		vo.setPRODUCT_SHIPPING_TXT(request.getParameter("shipping_txt"));
		vo.setPRODUCT_RETURN_TXT(request.getParameter("return_txt"));

		try {

			int insertChk = reserveService.insertSeatTable(vo);

			/* =============== 테이블 등록  ============== */
			if(insertChk > 0) {
				/* =============== 테이블 이미지 파일업로드 ============== */
				int result = reserveService.insertFileUpload(vo, request);

				if(result > 0) {
					str = mapper.writeValueAsString(true);		// SUCCESS
				} else {
					str = mapper.writeValueAsString(false);		// FAIL
				}
			}
		} catch (Exception e) {
			log.error(e);										// Error
		}

    	return str;
	}

	/* =============== ADMIN 테이블 수정 데이터  ============== */
	@RequestMapping("/seat/update/{seq}")
	@ResponseBody
	public String update( @PathVariable int seq, ProductVO vo, HttpServletRequest request) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		String str = "";

		vo.setPRODUCT_SEQ(seq);
		vo.setPRODUCT_SALE_MODE(request.getParameter("mode"));
		vo.setPRODUCT_INFO(request.getParameter("info"));
		vo.setPRODUCT_SUB_INFO(request.getParameter("product_Arr"));
		vo.setPRODUCT_TITLE(request.getParameter("title"));
		vo.setPRODUCT_CONTENT(request.getParameter("content"));
		vo.setPRODUCT_SHIPPING_TXT(request.getParameter("shipping_txt"));
		vo.setPRODUCT_RETURN_TXT(request.getParameter("return_txt"));

		try {
			/* =============== 테이블 수정  ============== */
			int updateChk = reserveService.updateSeatTable(vo);

			if(updateChk > 0) {
				/* =============== 테이블 이미지 수정  ============== */
				int result = reserveService.updateFileUpload(vo, request);

				if(result > 0) {
					str = mapper.writeValueAsString(true);		// SUCCESS
				} else {
					str = mapper.writeValueAsString(false);		// FAIL
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

    	return str;
	}

	/* =============== 테이블 삭제 (파일 삭제) ============== */
	@RequestMapping("/fileDel/{seq}")
	@ResponseBody
	public String fileDel(@PathVariable int seq, HttpServletRequest request, HttpSession session, ProductVO vo) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		String str = "";

		vo.setPRODUCT_FILE_STATE("D");		// 테이블 상태 변경
		vo.setPRODUCT_SEQ(seq);
		vo.setPRODUCT_FILE_TITLE((String) request.getParameter("name"));		// 삭제 될 파일 이름

		int del = reserveService.deleteSlideImgFile(vo);

		/* ================== 파일존재여부 (삭제) ================ */
		if(del > 0) {
    		File has = new File((String) session.getServletContext().getRealPath("/resources/upload/table/" + seq + "/" + (String) request.getParameter("realFileName")));

    		if(has.exists()) {
    			has.delete();
    		}

			str = mapper.writeValueAsString(true);		// SUCCESS
		} else {
			str = mapper.writeValueAsString(false);		// FALSE
		}

		return str; 
	}
    
	/* =============== 판매 제품 상태 변경 ============== */
    @RequestMapping("/product/sell")
    @ResponseBody
    public int productState( HttpServletRequest request, ProductVO vo ) {
    	vo.setPRODUCT_SEQ(Integer.parseInt(request.getParameter("seq")));
    	vo.setPRODUCT_SUB_INFO(request.getParameter("arr"));

    	return reserveService.updateProductChange(vo);
    }

	/* =============== 판매 제품 배송비 설정 ============== */
    @RequestMapping("/shipping/cost")
    @ResponseBody
    public int shippingCost( HttpServletRequest request, ProductVO vo ) {
    	vo.setPRODUCT_SEQ(Integer.parseInt(request.getParameter("seq")));
    	vo.setPRODUCT_SHIPPING((String) request.getParameter("input"));

    	return reserveService.updateShippingCost(vo);
    }

}