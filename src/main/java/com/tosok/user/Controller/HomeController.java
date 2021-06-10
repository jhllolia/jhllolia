package com.tosok.user.Controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.tosok.user.DAO.VisitCountDAO;
import com.tosok.user.Until.ExcelDown;
import com.tosok.user.Until.PageMaker;
import com.tosok.user.Until.ProductSellList;
import com.tosok.user.Until.SearchCriteria;
import com.tosok.user.Until.SendSms;
import com.tosok.user.VO.FileUploadVO;
import com.tosok.user.VO.ImageVO;
import com.tosok.user.VO.MapVO;
import com.tosok.user.VO.MemberVO;
import com.tosok.user.VO.PayVO;
import com.tosok.user.VO.ProductVO;
import com.tosok.user.VO.QnaVO;
import com.tosok.user.VO.ReceipeVO;
import com.tosok.user.VO.ReviewVO;
import com.tosok.user.VO.VisitCountVO;
import com.tosok.user.service.MapService;
import com.tosok.user.service.MemberService;
import com.tosok.user.service.ReceipeService;
import com.tosok.user.service.ReserveService;

@Controller
public class HomeController {
	protected Log log = LogFactory.getLog(HomeController.class);
	// 사업자 : 403 - 15 - 94623
	// 통신사업자 : 
	
	@Autowired
	private MemberService memberService;

	@Autowired
	private ReceipeService ReceipeService;

	@Autowired
	private ReserveService reserveService;

	@Autowired
	private MapService mapService;

	@Autowired
	private VisitCountDAO visitDao;
	
    @Autowired
    private SendSms sendSms;						// 문자 메세지

	@Autowired
	private ProductSellList productList;

	@RequestMapping("/")
	public String home() {
		return "/index";
	}

    /* ========== 회사소개 ========== */
	@RequestMapping("/intro")
	public ModelAndView intro(ImageVO vo) {
		ModelAndView mav = new ModelAndView();
		vo.setAddData("6");

		mav.addObject("list", ReceipeService.selectGallayTotalImage(vo));
		mav.setViewName("/intro");
		return mav;
	}

    /* ========== 제조과정 ========== */
	@RequestMapping("/process")
	public String process() {
		return "/process";
	}

    /* ========== 메인 ========== */
	@RequestMapping(value = "/main")
	public ModelAndView main(ProductVO vo, ReviewVO review) throws Exception {
		ModelAndView mav = new ModelAndView();

		mav.addObject("list", productList.listProductParam(vo, review));
		mav.setViewName("/main");
		return mav;
	}

	/* ========== 갤러리 ADMIN ========== */
	@Secured("ROLE_ADMIN")
	@RequestMapping("/gallary")
	public ModelAndView gallary(ImageVO vo) {
		ModelAndView mav = new ModelAndView();

		mav.addObject("list", ReceipeService.selectGallayTotalImage(vo));
		mav.setViewName("security/gallary");
		return mav;	
	}

	@RequestMapping("/gallary/proc")
	public String sendGallary(ImageVO vo) {

		int result = ReceipeService.updateGallaryData(vo);
		
		if(result > 0) {
			return "redirect:/gallary";
		} else {
			return "";
		}
	}

	@RequestMapping("/gallary/ordProc")
	@ResponseBody
	public void gallaryOrd(HttpServletRequest req) {
		ReceipeService.listGallaryOrdChange(req);
	}
	
	@RequestMapping(value="/intro/addData", method=RequestMethod.POST)
	@ResponseBody
	public List<ImageVO> addGallaryData(HttpServletRequest req, ImageVO vo) {
		vo.setImg_idxs(req.getParameterValues("arr"));
		vo.setAddData("3");

		return ReceipeService.selectGallayTotalImage(vo);
	}
	
	
	/* ========== 방문자 통계 ========== */
	@Secured("ROLE_ADMIN")
	@RequestMapping("/review")
	public ModelAndView review(ReviewVO vo) {
		ModelAndView mav = new ModelAndView();

		mav.addObject("list", memberService.selectReview(vo));
		mav.setViewName("security/review");
		return mav;
	}

	@RequestMapping("/rev/content")
	@ResponseBody
	public ModelAndView review_content(ReviewVO vo, HttpServletRequest req) {
		ModelAndView mav = new ModelAndView();

		vo.setORDER_NUM(req.getParameter("orderNum"));
		vo.setREVIEW_ID(req.getParameter("rev_id"));
		vo.setPRODUCT_NUM(req.getParameter("prdNum"));
		vo.setPRODUCT_NAME(req.getParameter("prdName"));
		vo.setPRODUCT_OPTION(req.getParameter("prdOpt"));
		vo.setPRODUCT_QTY(req.getParameter("prdQty"));

		mav.addObject("DATA", memberService.getProductReviewData(vo));
		mav.setViewName("security/reviewForm");
		return mav;
	}

	/* ==========  ========== */
	@RequestMapping("/rev/admin")
	@ResponseBody
	public void adminReview(ReviewVO vo, HttpServletRequest req, HttpServletResponse res) throws IOException {
		res.setContentType("text/html; charset=UTF-8");
		PrintWriter out = res.getWriter();

		vo.setVIEW_SEQ(Integer.parseInt(req.getParameter("idx")));
		vo.setREVIEW_REPLY_YN("Y");
		vo.setREVIEW_REPLY(req.getParameter("content"));

		int result = memberService.updateAdminReview(vo);

		if(result > 0) {
			out.println("<script>alert('등록이 완료되었습니다.'); location.href='../review';</script>");
		} else {
			out.println("<script>alert('등록에 실패했습니다.'); location.href='../review';</script>");
		}
	}

	/* ==========  ========== */
	@RequestMapping("/rev/delete")
	@ResponseBody
	public void adminReviewDelete(ReviewVO vo, HttpServletRequest req, HttpServletResponse res) throws IOException {
		res.setContentType("text/html; charset=UTF-8");
		PrintWriter out = res.getWriter();

		vo.setVIEW_SEQ(Integer.parseInt(req.getParameter("idx")));
		vo.setVIEW_STATE("N");

		int result = memberService.deleteAdminReview(vo);

		if(result > 0) {
			out.println("<script>alert('미노출이 완료되었습니다.'); location.href='../review';</script>");
		} else {
			out.println("<script>alert('미노출에 실패했습니다.'); location.href='../review';</script>");
		}
	}

	/* ========== 방문자 통계 ========== */
	@Secured("ROLE_ADMIN")
	@RequestMapping("/come")
	@ResponseBody
	public ModelAndView come(@ModelAttribute("scri") SearchCriteria scri, @RequestParam Map<String, Object> paramMap) {
		ModelAndView mav = new ModelAndView();
		PageMaker pageMaker = new PageMaker();

		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		Date time = new Date();

		String date = (String) paramMap.get("date");
		scri.setKeyword(date);

		int visitCount = visitDao.selectVisitCount(scri);		// today

		pageMaker.setCri(scri);									// paging
		pageMaker.setTotalCount(visitDao.getVisitCnt(scri));	// count

		mav.addObject("visit", visitDao.listVisitDao(scri));	// 방문자
		mav.addObject("pageMaker", pageMaker);					// 페이징
		mav.addObject("visitCount", visitCount);				// 방문자 통계
		mav.addObject("date", format1.format(time));			// 오늘
		mav.setViewName("security/come");
		return mav;
	}

	/* ========== ADMIN 방문자 EXCEL 다운로드 ========== */
	@RequestMapping("/excelDown")
	public void excelDown(@RequestParam Map<String, Object> paramMap, HttpServletResponse response) throws IOException {
		String keyword = (String) paramMap.get("keyword");				// Parameter
		List<VisitCountVO> list = visitDao.selectExcelList(paramMap);	// List

		ExcelDown excel = new ExcelDown();
		excel.Download(keyword, list, response);
	}

	/* ========== ADMIN 결제정보 EXCEL 다운로드 ========== */
	@RequestMapping("/payment/excel")
	public void paymentExcel(@RequestParam Map<String, Object> paramMap, HttpServletResponse response, PayVO vo) throws IOException {
    	vo.setPAID_BEFORE_DATE((String) paramMap.get("before"));		// 출력할 시간 년도 - 월 - 일
    	vo.setPAID_AFTER_DATE((String) paramMap.get("after"));
		vo.setLIST_STATE((String) paramMap.get("search_tit"));			// 검색 카테고리
		vo.setLIST_KEYWORD((String) paramMap.get("search_txt"));		// 검색 내용

		List<PayVO> list = memberService.selectRequestPayment(vo);

		ExcelDown excel = new ExcelDown();
		excel.PaymentExcel(list, response);
	}

	/* ==========  ========== */
	@Secured("ROLE_ADMIN")
    @RequestMapping("/orderStatus/change")
    @ResponseBody
    public int orderStatus( HttpServletRequest request, PayVO vo ) throws Exception {
		vo.setPAID_STATUS(request.getParameter("status"));
		vo.setLIST_STATE(request.getParameter("arr"));
		
		return reserveService.updateSeveralStatus(vo);
	}

	/* ==========  ========== */
    @RequestMapping("/shpping/courier")
    @ResponseBody
    public int courier_add(PayVO vo, MemberVO mem, HttpServletRequest request) {
    	vo.setORDER_NUM(request.getParameter("seq"));
    	vo.setPAID_STATUS("shipping_delivery");
    	vo.setBUYER_EMAIL(request.getParameter("mail"));
    	vo.setPRODUCT_SHIPPING_NUM(request.getParameter("courier"));
    	vo.setPRODUCT_SHIPPING_COURIER(request.getParameter("courTxt"));

    	int result = memberService.addShippingCourier(vo);

    	if(result > 0) {
    		List<PayVO> list = reserveService.getProductInfo(vo);

    		if(list.isEmpty() == false) {
    			/* =================== 행동 문자 ================ */
    			mem.setMember_Phone(list.get(0).getBUYER_TEL());
    			mem.setMember_Content("어양토속식품 주문번호 => " + list.get(0).getORDER_NUM() + " 에 배송번호가 등록되었습니다.");
    			sendSms.send(mem);

    			return memberService.addShippingCourier(vo);
    		} else {
    			return 0;
    		}
    	} else {
    		return 0;
    	}
    }

	/* ========== 테이블 결제 리스트 ========== */
	@Secured("ROLE_ADMIN")
    @RequestMapping("/payCheck/admin")
    @ResponseBody
    public List<PayVO> list( HttpSession session, HttpServletRequest request, PayVO vo ) throws Exception {
    	vo.setPAID_BEFORE_DATE(request.getParameter("before"));		// 출력할 시간 년도 - 월 - 일
    	vo.setPAID_AFTER_DATE(request.getParameter("after"));
		vo.setLIST_STATE(request.getParameter("search"));			// 검색 카테고리
		vo.setLIST_KEYWORD(request.getParameter("keyword"));		// 검색 내용
		vo.setPAID_STATUS(request.getParameter("status"));

		return memberService.adminSelectPayment(vo);
	}

	/* ========== 선택 된 결제 데이터 ========== */
	@Secured("ROLE_ADMIN") 
	@RequestMapping("/productCheck/admin")
	@ResponseBody
	public PayVO product_total_count( HttpServletRequest request, PayVO vo ) throws Exception {
		int ship = 0;

		vo.setPAID_BEFORE_DATE(request.getParameter("before"));
		vo.setPAID_AFTER_DATE(request.getParameter("after"));
		vo.setLIST_STATE(request.getParameter("search"));  			// 검색 카테고리
		vo.setLIST_KEYWORD(request.getParameter("keyword"));		// 검색 내용
		vo.setPAID_STATUS(request.getParameter("status"));

		/* ========== 배송비 가져오기 ========== */
		List<PayVO> cost = reserveService.selectShippingCost(vo);

		for (int i = 0; i < cost.size(); i++) {
			ship += Integer.parseInt(cost.get(i).getPRODUCT_SHIPPING_COST());
		}

		/* =========== 배송비 set ============ */
		PayVO result = reserveService.selectProductBuyCheck(vo);
		result.setPRODUCT_SHIPPING_COST(Integer.toString(ship));

		return result;
	}

	/* ========== 결제 페이지 ========== */
	@Secured("ROLE_ADMIN")
	@RequestMapping("/pay")
	public ModelAndView pay() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("security/pay");
		return mav;
	}

	/* ========== ADMIN 회원 멤버 수 ========== */
	@Secured("ROLE_ADMIN")
	@RequestMapping("/person/list")
	@ResponseBody
	public List<MemberVO> person_list(HttpServletRequest request, MemberVO vo) throws Exception {
		vo.setLIST_STATE(request.getParameter("search"));		// 리스트 상태
		vo.setLIST_KEYWORD(request.getParameter("keyword"));	// 리스트 카테고리

		return memberService.adminSelectPerson(vo);
	}

	/* ========== ADMIN 상품 QnA 페이지 ========== */
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/qna")
	public ModelAndView admin_qna(@ModelAttribute("scri") SearchCriteria scri, QnaVO vo) {
		ModelAndView mav = new ModelAndView();
		PageMaker pageMaker = new PageMaker();

		pageMaker.setCri(scri);
		pageMaker.setTotalCount(memberService.selectAdminQnACount(vo));

		mav.addObject("list", memberService.selectAdminQnAList(scri));
		mav.addObject("pageMaker", pageMaker);
		mav.setViewName("security/qna");
		return mav;
	}

	/* ========== ADMIN 대시보드 페이지 ========== */
	@Secured("ROLE_ADMIN")
	@RequestMapping("/return")
	public ModelAndView dashboard() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("security/return");
		return mav;
	}

	/* ============================== */
	@RequestMapping("/open/memberMemo")
	@ResponseBody
	public PayVO openMemo(HttpServletRequest request, PayVO vo) {
		vo.setPAY_SEQ(Integer.parseInt(request.getParameter("seq")));
		vo.setORDER_NUM(request.getParameter("order"));

		return reserveService.getStatusMemberMemo(vo);
	}
	
	@RequestMapping("/return/data")
	@ResponseBody
	public NavigableMap<String, List<PayVO>> returnData(HttpServletRequest request, PayVO vo) {
		String before = request.getParameter("before");
		String after = request.getParameter("after");

		vo.setLIST_STATE(request.getParameter("search"));			// 검색 카테고리
		vo.setLIST_KEYWORD(request.getParameter("keyword"));		// 검색 내용
		vo.setPAID_BEFORE_DATE(before);
		vo.setPAID_AFTER_DATE(after);

		List<PayVO> list = reserveService.getProductReturnRequest(vo);
		TreeMap<String, List<PayVO>> data = new TreeMap<>();

		for(PayVO p : list) {
			if(!data.containsKey(p.getORDER_NUM())) {
				data.put(p.getORDER_NUM(), new ArrayList<>()); 
			}

			data.get(p.getORDER_NUM()).add(p);
		}

		return data.descendingMap();
	}

	/* ========== ADMIN 회원리스트 페이지  ========== */
	@Secured("ROLE_ADMIN")
	@RequestMapping("/memberList")
	public ModelAndView memberList() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("security/memberList");
		return mav;
	}

	/* ========== ADMIN 대시보드 페이지 ========== */
	@Secured("ROLE_ADMIN")
	@RequestMapping("/admin")
	public ModelAndView admin() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("security/pay");
		return mav;
	}

	/* ============== 파일업로드 ============== */
	@RequestMapping(value = "/bbs/fileUpload")
	public String fileUpload(@ModelAttribute("fileUploadVO") FileUploadVO fileUploadVO, HttpServletRequest request, Model model) {
		HttpSession session = request.getSession();
		String rootPath = session.getServletContext().getRealPath("/");
		String attachPath = "resources/upload/content/";

		MultipartFile upload = fileUploadVO.getUpload();
		String filename = "";
		String CKEditorFuncNum = "";

		if (upload != null) {
			try {

				UUID uuid = UUID.randomUUID();
				filename = uuid.toString() + "_" + upload.getOriginalFilename();

				int idx = filename.lastIndexOf(".");
				String extension = filename.substring(idx + 1);
				String regEx = "(jpg|jpeg|JPEG|JPG|png|PNG|git|GIF|BMP|bmp)";
				CKEditorFuncNum = fileUploadVO.getCKEditorFuncNum();

				if (extension.matches(regEx)) {
					File file = new File(rootPath + attachPath + filename);
					upload.transferTo(file);

					log.info(file);
				} else {
					log.error("File 업로드 실패");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		model.addAttribute("file_path", "../" + attachPath + filename);
		model.addAttribute("CKEditorFuncNum", CKEditorFuncNum);

		return "/bbs/fileUploadComplete";
	}

	/* ============== 제휴 지점 ============== */
	@RequestMapping(value = "/business")
	@ResponseBody
	public ModelAndView business(MapVO vo, @RequestParam Map<String, Object> paramMap, Model model) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("list", mapService.listMapDao(paramMap));
		mav.addObject("cnt", mapService.listMapCnt());		
		return mav;
	}

	/* ========== ADMIN 로그인 ========== */
	@RequestMapping("/adminForm")
	public String loginForm(Model model) {
		return "security/adminForm";
	}
	
	/* ========== 이용약관 페이지 ========== */
	@RequestMapping("/terms/termsUse")
	public String termsUse() {
		return "/terms/termsUse";
	}

	/* ========== 개인정보보호 페이지 ========== */	
	@RequestMapping("/terms/termsPrivacy")
	public String termsPrivacy() {
		return "/terms/termsPrivacy";
	}

	/* ============== Email 상담 ============== */
	@RequestMapping(value = "/terms/counsel")
	public String email_counsel() {
		return "/terms/counsel";
	}

	/* ==============  ============== */
	@RequestMapping(value = "/terms/request")
	@ResponseBody
	public int email_request(HttpServletRequest request, MemberVO vo) {
		String regex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
		String inquiry_sct = request.getParameter("sct");
		String inquiry_tit = request.getParameter("tit");
		String inquiry_txt = request.getParameter("txt");
		String mail = request.getParameter("mail");

		if(inquiry_sct.equals("") || inquiry_tit.equals("") || inquiry_txt.equals("")) {
			return 0;
		} else if(!inquiry_sct.equals("A") && !inquiry_sct.equals("B") && !inquiry_sct.equals("C") && !inquiry_sct.equals("D") && !inquiry_sct.equals("E") && !inquiry_sct.equals("F")) {
			return 0;
		} else if(mail.matches(regex) == false) {
			return 0;
		} else {

			if(inquiry_sct.equals("A")) {
				inquiry_sct = "샘플 요청";
			} else if(inquiry_sct.equals("B")) {
				inquiry_sct = "도매전용 제품구매";
			} else if(inquiry_sct.equals("C")) { 
				inquiry_sct = "배송";
			} else if(inquiry_sct.equals("D")) {
				inquiry_sct = "반품/교환";
			} else if(inquiry_sct.equals("E")) {
				inquiry_sct = "주문/결제";
			} else if(inquiry_sct.equals("F")) { 
				inquiry_sct = "기타";
			}

			vo.setMember_Id(mail);
			vo.setMember_Inquiry(inquiry_sct);
			vo.setMember_Subject(inquiry_tit);
			vo.setMember_Content(inquiry_txt);

			memberService.send_Opinion(vo);

			return 1;
		}
	}

    /* ==========  ========== */
	@RequestMapping(value = "/bbs/ImageUpload", method=RequestMethod.POST)
	public void mmsImageUpload(MultipartHttpServletRequest request, HttpServletResponse response) throws IOException {

	    try {

	        String filePath = null;
	        Map<String, MultipartFile> fileMap = request.getFileMap();

	        for (MultipartFile multipartFile : fileMap.values()) {
	        	HttpSession session = request.getSession();
	            String rootPath = session.getServletContext().getRealPath("/");
	            String attachPath = "resources/upload/";

	        	String originalFileName = multipartFile.getOriginalFilename();            
	            filePath = rootPath + attachPath + originalFileName;
	            File file = new File(filePath);

	            if (originalFileName != null && !originalFileName.equals("")) { 
	                if (file.exists()) {
	                	originalFileName = System.currentTimeMillis() + "_" + originalFileName;                 
	                    file = new File(rootPath + attachPath + originalFileName); 
	                }
	            }

	            log.info("file : " + file);

		        if(!file.exists()) {
	                file.mkdirs();
	            }

	            multipartFile.transferTo(file);
	        }

	        response.setContentType("text;");
	        response.getWriter().write(filePath);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	/* ================= 매일 24시  ===================== */
	@Scheduled(fixedDelay=86400000)
	public void DataScheduled() throws Exception {
		
		ReceipeVO r_vo = new ReceipeVO();
		MapVO m_vo = new MapVO();

		String clientSecret = "KakaoAK 8e17e941ff8ec2fc7c002f7273b50ae2";

		try {

			JSONParser jsonParser = new JSONParser();
			String text = URLEncoder.encode("어양토속순대", "UTF-8");

			/* ============================ map ==================================*/
			String m_x = "35.9539899";
			String m_y = "126.9773429";
			String m_radius = "20000";
			String m_sort = "accuracy";

			String m_URL = "https://dapi.kakao.com/v2/local/search/keyword.json?query=" + text + "&x=" + m_x + "&y=" + m_y + "&radius=" + m_radius + "&sort=" + m_sort;

			URL m_url = new URL(m_URL);
			HttpURLConnection m_con = (HttpURLConnection)m_url.openConnection();
			m_con.setRequestMethod("GET");
			m_con.setRequestProperty("Authorization", clientSecret);
			int m_responseCode = m_con.getResponseCode();
			BufferedReader m_br;

			if(m_responseCode == 200) {	// 정상 호출
				m_br = new BufferedReader(new InputStreamReader(m_con.getInputStream()));
			} else {  					// 에러 발생
				m_br = new BufferedReader(new InputStreamReader(m_con.getErrorStream()));
			}

			String m_inputLine;
			StringBuffer m_response = new StringBuffer();

			while ((m_inputLine = m_br.readLine()) != null) {
				m_response.append(m_inputLine);
			}

			m_br.close();

			/* ============================ blog ==================================*/
			String b_URL = "https://dapi.kakao.com/v2/search/blog?size=50&query=" + text; // json 결과

			URL b_url = new URL(b_URL);
			HttpURLConnection b_con = (HttpURLConnection)b_url.openConnection();
			b_con.setRequestMethod("GET");
			b_con.setRequestProperty("Authorization", clientSecret);
			int b_responseCode = b_con.getResponseCode();
			BufferedReader b_br;

			if(b_responseCode == 200) {	// 정상 호출
				b_br = new BufferedReader(new InputStreamReader(b_con.getInputStream()));
			} else {  					// 에러 발생
				b_br = new BufferedReader(new InputStreamReader(b_con.getErrorStream()));
			}

			String b_inputLine;
			StringBuffer b_response = new StringBuffer();

			while ((b_inputLine = b_br.readLine()) != null) {
				b_response.append(b_inputLine);
			}

			b_br.close();
			
			/* =========================== result ==============================*/
			JSONObject m_Obj = (JSONObject) jsonParser.parse(m_response.toString());
			JSONArray m_Array = (JSONArray) m_Obj.get("documents");

			JSONObject b_Obj = (JSONObject) jsonParser.parse(b_response.toString());
			JSONArray b_Array = (JSONArray) b_Obj.get("documents");

			/* ==================================================================== */
			for(int i = 0 ; i < m_Array.size() ; i++) {
				JSONObject tempObj = (JSONObject) m_Array.get(i);

                m_vo.setM_Id((String) tempObj.get("id"));
                m_vo.setM_cat_code((String) tempObj.get("category_group_code"));
                m_vo.setM_cat_Name((String) tempObj.get("category_name"));
                m_vo.setM_X((String) tempObj.get("x"));
                m_vo.setM_Y((String) tempObj.get("y"));
                m_vo.setM_place_Name((String) tempObj.get("place_name"));
                m_vo.setM_place_Address((String) tempObj.get("address_name"));
                m_vo.setM_place_Road((String) tempObj.get("road_address_name"));
                m_vo.setM_place_Url((String) tempObj.get("place_url"));
                m_vo.setM_place_Number((String) tempObj.get("phone"));

                mapService.insertMapApi(m_vo);
			}

			/* ==================================================================== */
			for(int i = 0 ; i < b_Array.size() ; i++) {
				JSONObject tempObj = (JSONObject) b_Array.get(i);
				
				String sub = (String) tempObj.get("url").toString();
				String title = (String) tempObj.get("title").toString();
				String contents = (String) tempObj.get("contents").toString();
				
				sub.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
				title.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
				contents.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");

				byte sub_chk[] = sub.getBytes("euc-kr");
				byte title_chk[] = title.getBytes("euc-kr");
				byte con_chk[] = contents.getBytes("euc-kr");

                r_vo.setRecipe_Category("블로그");
                r_vo.setUser_Id((String) tempObj.get("blogname"));
                r_vo.setRecipe_Title((String) tempObj.get("thumbnail").toString());
                r_vo.setRecipe_SubTitle(new String(sub_chk,"euc-kr"));
                r_vo.setRecipe_Subject(new String(title_chk,"euc-kr"));
                r_vo.setRecipe_Content(new String(con_chk,"euc-kr"));
                r_vo.setRegister_Date((String) tempObj.get("datetime"));

                if(!"".equals(tempObj.get("thumbnail").toString())) {
                	ReceipeService.insertBlog(r_vo);
                }
			}
			
			log.info("=============================");
			log.info("map size : " + m_Array.size());
			log.info("blog size : " + b_Array.size());
			
        } catch(Exception e) {
        	System.out.println(e);
        }
	}

}