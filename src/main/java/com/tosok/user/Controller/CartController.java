package com.tosok.user.Controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.tosok.user.VO.CartVO;
import com.tosok.user.VO.MemberVO;
import com.tosok.user.VO.PayVO;
import com.tosok.user.VO.ProductVO;
import com.tosok.user.service.MemberService;
import com.tosok.user.service.ReserveService;

@Controller
public class CartController {
	// 사업자 : 403 - 15 - 94623

	protected Log log = LogFactory.getLog(CartController.class);
	public final static int	LIMIT_COST = 30000;

	@Autowired
	private MemberService memberService;

	@Autowired
	private ReserveService reserveService;

	/* ===============  ============== */
	@RequestMapping("/info/cart")
	public ModelAndView myCart(HttpServletRequest request, CartVO vo, MemberVO dto, ProductVO product) {
		ModelAndView mav = new ModelAndView();

		List<Map<String, Object>> data = null;
    	Gson gson = new Gson();
		JsonParser parser = new JsonParser();
		JsonArray jsonData = new JsonArray();

		int seq = (int) request.getSession().getAttribute("member_Seq");
		String id = (String) request.getSession().getAttribute("member_Id");

		dto.setMember_Seq(seq);		// 회원정보
		vo.setCART_MEMBER(id);		// 장바구니

		List<CartVO> list = memberService.selectCartMemberList(vo);

		for (int i = 0; i < list.size(); i++) {
			JsonObject obj = new JsonObject();

			int num = list.get(i).getCART_ORDER();
			product.setPRODUCT_SEQ(num);

        	ProductVO getProduct = reserveService.selectTableInfo(product);
        	JsonArray arr = (JsonArray) parser.parse(getProduct.getPRODUCT_SUB_INFO());

        	obj.addProperty("CART_SEQ", list.get(i).getCART_SEQ());
        	obj.addProperty("CART_ORDER", list.get(i).getCART_ORDER());
        	obj.addProperty("CART_STATE", list.get(i).getCART_STATE());
        	obj.addProperty("CART_MEMBER", list.get(i).getCART_MEMBER());
        	obj.addProperty("PRODUCT_FILE_NAME", list.get(i).getPRODUCT_FILE_NAME());

        	int j = Integer.parseInt(list.get(i).getCART_PRODUCT_NUM().replaceAll("[^0-9]", ""));
    		JsonObject sub = (JsonObject) arr.get(j);

        	obj.addProperty("CART_PRODUCT_NUM", list.get(i).getCART_PRODUCT_NUM());
    		obj.addProperty("CART_PRODUCT_NAME", sub.get("name").getAsString());
    		obj.addProperty("CART_PRODUCT_OPTION", sub.get("option").getAsString());
    		obj.addProperty("CART_PRODUCT_PRICE", sub.get("price").getAsString());
    		obj.addProperty("CART_PRODUCT_QTY", list.get(i).getCART_PRODUCT_QTY());
    		obj.addProperty("CART_PRODUCT_SALE", sub.get("sale").getAsString());
    		obj.addProperty("CART_PRODUCT_SELL", sub.get("sell").getAsString());

			jsonData.add(obj);
		}

		data = gson.fromJson(jsonData, new TypeToken<List<Map<String, Object>>>() {}.getType());
		
		mav.addObject("info", memberService.info_update(dto));
		mav.addObject("list", data);
		mav.setViewName("/info/cart");
		return mav;
	}

	/* ===============  ============== */
	@RequestMapping("/count/cart")
	@ResponseBody
	public int count_cart(HttpServletRequest request, CartVO vo) {
		String id = (String) request.getSession().getAttribute("member_Id");

		if("null".equals(id)) {
			return 0;
		} else {
			vo.setCART_STATE("Y");
			vo.setCART_MEMBER(id);

			return memberService.selectCartCount(vo);
		}
	}

	/* ===============  ============== */
	@RequestMapping("/update/qty")
	@ResponseBody
	public int cart_qty_change(HttpServletRequest request, CartVO vo) {
		String id = (String) request.getSession().getAttribute("member_Id");
		String num = request.getParameter("num");
		String name = request.getParameter("name");
		String option = request.getParameter("option");
		String qty = request.getParameter("qty");

		if(id.equals("") || num.equals("") || name.equals("") || option.equals("") || qty.equals("")) {
			return 0;
		} else {

			vo.setCART_MEMBER(id);
			vo.setCART_PRODUCT_NUM(num);
			vo.setCART_PRODUCT_NAME(name);
			vo.setCART_PRODUCT_OPTION(option);
			vo.setCART_PRODUCT_QTY(qty);

			int count = memberService.haveMemberCartProduct(vo);

			if(count > 0) {
				return memberService.updateCartProductQty(vo);
			} else {
				return 0;
			}
		}
	}

	/* ===============  ============== */
	@RequestMapping("/unit/delete")
	@ResponseBody
	public int cart_array_delete(HttpServletRequest request, CartVO vo) {
		String id = (String) request.getSession().getAttribute("member_Id");
		String arr = request.getParameter("arr");

		if(id.equals("") || arr.isEmpty()) {
			return 0;
		} else {

			vo.setCART_SEQ(arr);
			vo.setCART_MEMBER(id);
			vo.setCART_STATE("N");

			int vaild = memberService.selectCartProductVaild(vo);

			if(vaild > 0) {
				return memberService.updateCartProductState(vo);
			} else {
				return 0;
			}
		}
	}

	/* =============== 결제 상세정보  ============== */
    @RequestMapping("/cart/orderDetail")
    public String post_order(HttpServletRequest request, ProductVO product, MemberVO vo, HttpSession session, Model model) {
    	String referer = request.getHeader("Referer");
    	JsonParser parser = new JsonParser();
    	JsonObject json = new JsonObject();

    	Gson gson = new Gson();
    	List<Map<String, Object>> list = null;

    	String id = (String) request.getSession().getAttribute("member_Id");
    	String cart = (String) request.getParameter("arr");

    	if(id == "" && cart.isEmpty()) {
    		return "redirect:" + referer + "";
    	} else {
        	JsonArray jsonData = new JsonArray();
        	JsonArray data = (JsonArray) parser.parse(cart);
        	
        	int total = 0;
        	int cost = 0;

        	for (int i = 0; i < data.size(); i++) {
    			JsonObject obj = new JsonObject();
    			JsonObject saveData = (JsonObject) data.get(i);

            	product.setPRODUCT_SEQ(Integer.parseInt((String) saveData.get("seq").getAsString()));
            	ProductVO result = reserveService.selectTableInfo(product);

            	int j = Integer.parseInt((String) saveData.get("num").getAsString().replaceAll("[^0-9]", ""));

            	String str = result.getPRODUCT_SUB_INFO();			// DB 데이터 불러오기 
            	JsonArray arr = (JsonArray) parser.parse(str);		// DB 데이터
    			JsonObject userData = (JsonObject) arr.get(j);

				int qty = Integer.parseInt(saveData.get("qty").getAsString());
				int price = Integer.parseInt(userData.get("price").getAsString());
				
				obj.addProperty("num", userData.get("num").getAsString());
				obj.addProperty("name", userData.get("name").getAsString());
				obj.addProperty("option", userData.get("option").getAsString());
				obj.addProperty("price", userData.get("price").getAsString());
				obj.addProperty("sale", userData.get("sale").getAsString());
				obj.addProperty("sell", userData.get("sell").getAsString());

				obj.addProperty("seq", saveData.get("seq").getAsString());
				obj.addProperty("order", saveData.get("order").getAsString());
				obj.addProperty("profile", result.getPRODUCT_FILE_NAME());
				obj.addProperty("qty", saveData.get("qty").getAsString());

				total = (total + (price * qty));
				cost = Integer.parseInt(result.getPRODUCT_SHIPPING());

				jsonData.add(obj);
			}

        	if(total >= LIMIT_COST) {
        		cost = 0;
        	}

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

	/* =============== 결제 완료 페이지 ============== */
    @RequestMapping("/cart/orderComplete")
    public ModelAndView payComplete(PayVO vo, CartVO cart, HttpServletRequest request) throws Exception {
    	ModelAndView mav = new ModelAndView();

    	String id = (String) request.getSession().getAttribute("member_Id");
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

        		/*	============= 장바구니 제품 조회 ============== */
        		for (int i = 0; i < list.size(); i++) {
        			cart.setCART_STATE("N");
        			cart.setCART_MEMBER((String) list.get(i).getBUYER_EMAIL());
        			cart.setCART_PRODUCT_NAME((String) list.get(i).getPRODUCT_NAME());
        			cart.setCART_PRODUCT_OPTION((String) list.get(i).getPRODUCT_OPTION());
        			
        			int result = memberService.updateCartUnitState(cart);
        			log.info("result : " + result);
        		}

        		mav.addObject("order", list);
        		mav.setViewName("/pay/orderComplete");
        	}
    	}

    	return mav;
    }

	@RequestMapping("/cart/product")
	@ResponseBody
	public int addCart(HttpServletRequest request, ProductVO dto, CartVO vo) {
    	JsonParser parser = new JsonParser();
		String id = (String) request.getSession().getAttribute("member_Id");
		int order = Integer.parseInt(request.getParameter("order"));

		if(id.equals("")) {
			return 0;
		} else {

			int j = 0;
			String product = (String) request.getParameter("arr");
			JsonArray data = (JsonArray) parser.parse(product);
			dto.setPRODUCT_SEQ(order);

			for (int i = 0; i < data.size(); i++) {
    			JsonObject result = (JsonObject) data.get(i);

    			String num = result.get("num").getAsString();
    			String str = num.replaceAll("[^0-9]", "");

    			ProductVO reData = memberService.getCartProductUnit(dto);							// 해당 제품 서브메뉴 출력
    			JsonArray getProduct = (JsonArray) parser.parse(reData.getPRODUCT_SUB_INFO());		// JSON 파싱
    			JsonObject clickUnit = (JsonObject) getProduct.get(Integer.parseInt(str));			// 

    			vo.setCART_ORDER(order);
				vo.setCART_MEMBER(id);
				vo.setCART_PRODUCT_NUM(clickUnit.get("num").getAsString());
				vo.setCART_PRODUCT_NAME(clickUnit.get("name").getAsString());
				vo.setCART_PRODUCT_OPTION(clickUnit.get("option").getAsString());
				vo.setCART_PRODUCT_PRICE(clickUnit.get("price").getAsString());
				vo.setCART_PRODUCT_QTY(result.get("qty").getAsString());
				vo.setCART_PRODUCT_SALE(clickUnit.get("sale").getAsString());
				vo.setCART_PRODUCT_SELL(clickUnit.get("sell").getAsString());

				int count = memberService.haveMemberCartProduct(vo);

				if(count == 0) {
					/* ===== 장바구니 Insert ====== */
					memberService.insertCartProduct(vo);

					j++;
				}
			}

			return j;
		}
	}

}