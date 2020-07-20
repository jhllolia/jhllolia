package com.tosok.user.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.tosok.user.DAO.ReserveDAO;
import com.tosok.user.IamPortRequest.CancelData;
import com.tosok.user.Until.SearchCriteria;
import com.tosok.user.Upload.SeatFileDelete;
import com.tosok.user.Upload.SeatFileInsert;
import com.tosok.user.VO.CartVO;
import com.tosok.user.VO.MemberVO;
import com.tosok.user.VO.PayVO;
import com.tosok.user.VO.ProductVO;
import com.tosok.user.VO.QnaVO;
import com.tosok.user.VO.ReviewVO;
import com.tosok.user.service.ReserveService;

@Service("ReserveService")
public class ReserveServiceImpl implements ReserveService {
	protected Log log = LogFactory.getLog(ReserveServiceImpl.class);

	@Resource(name = "ReserveDAO")
	private ReserveDAO reserveDao;

    @Resource
    private SeatFileInsert fileInsert;

    @Resource
    private SeatFileDelete fileDel;

	@Override
	public int insertFileUpload(ProductVO vo, HttpServletRequest request) throws Exception {
		/* ========== 테이블 Insert 슬라이드 이미지 파일업로드 ========== */
		List<Map<String, Object>> list = fileInsert.parseInsertFileInfo(vo, request);

		int result = 0;

		/* ========== 테이블 Insert 슬라이드 이미지 DB 저장 ========== */
		for (int i = 0; i < list.size(); i++) {
			vo.setPRODUCT_SEQ((int) list.get(i).get("PRODUCT_SEQ"));
			vo.setPRODUCT_FILE_TITLE((String) list.get(i).get("PRODUCT_FILE_TITLE"));
			vo.setPRODUCT_FILE_NAME((String) list.get(i).get("PRODUCT_FILE_NAME"));
			vo.setPRODUCT_FILE_SIZE((long) list.get(i).get("PRODUCT_FILE_SIZE"));
			vo.setORD(i + "");

			result = reserveDao.insertFileUpload(vo);

			reserveDao.updateProductOrd(vo);				// ORD 순서
		}

		return result;
	}

	@Override
	public int updateFileUpload(ProductVO vo, HttpServletRequest request) throws Exception {
		/* ========== 테이블 Modify 슬라이드 이미지 파일업로드 ========== */
		List<Map<String, Object>> list = fileInsert.parseInsertFileInfo(vo, request);

		int result = 1;
		int fileExist = 0;

		/* ========== 테이블 Modify 슬라이드 이미지 DB 저장 ========== */
		for (int i = 0; i < list.size(); i++) {
			vo.setPRODUCT_SEQ((int) list.get(i).get("PRODUCT_SEQ"));
			vo.setPRODUCT_FILE_TITLE((String) list.get(i).get("PRODUCT_FILE_TITLE"));
			vo.setPRODUCT_FILE_NAME((String) list.get(i).get("PRODUCT_FILE_NAME"));
			vo.setPRODUCT_FILE_SIZE((long) list.get(i).get("PRODUCT_FILE_SIZE"));
 
			/* ========== True : 1, False : 0 ========== */
			fileExist = reserveDao.selectFileExist(vo);			// 이미지 존재 여부

			if(fileExist > 0) {
				fileExist = reserveDao.updateFileUpload(vo);
			} else {
				fileExist = reserveDao.insertFileUpload(vo);
			}
		}

		/* ============== 이미지 순서 변경 ============== */
		List<ProductVO> ordFile = reserveDao.selectFileOrd(vo);
		reserveDao.deleteFileOrd(vo);

		for (int j = 0; j < ordFile.size(); j++) {
			vo.setPRODUCT_SEQ(ordFile.get(j).getPRODUCT_SEQ());
			vo.setPRODUCT_FILE_TITLE(ordFile.get(j).getPRODUCT_FILE_TITLE());
			vo.setORD(j + "");

			reserveDao.updateProductOrd(vo);
		}

		log.debug("fileExist : " + fileExist);
		return result;
	}

	@Override
	public int insertSeatTable(ProductVO vo) {
		return reserveDao.insertSeatTable(vo);
	}

	@Override
	public int selectTableCount(SearchCriteria scri) {
		return reserveDao.selectTableCount(scri);
	}

	@Override
	public List<ProductVO> listTableOrder(SearchCriteria scri) {
		return reserveDao.listTableOrder(scri);
	}

	@Override
	public int updateSeatTable(ProductVO vo) {
		return reserveDao.updateSeatTable(vo);
	}

	@Override
	public int deleteSeatTable(ProductVO vo, HttpServletRequest request ) throws Exception {
		/* ========== 테이블 상태를 '삭제' 로 변경 시에 해당 테이블 폴더에 있는 이미지 전체 삭제  ========== */
		if(vo.getPRODUCT_STATE().equals("D")) {
			fileDel.tableFileDelete(vo, request);
		}		

		return reserveDao.deleteSeatTable(vo);
	}

	@Override
	public ProductVO selectTableInfo(ProductVO vo) {
		return reserveDao.selectTableInfo(vo);
	}

	@Override
	public List<ProductVO> selectTableFile(ProductVO vo) {
		return reserveDao.selectTableFile(vo);
	}

	@Override
	public int deleteSlideImgFile(ProductVO vo) {
		log.debug("=====================================");
		log.debug("FileSeq : " + vo.getPRODUCT_SEQ());
		log.debug("FileTitle : " + vo.getPRODUCT_FILE_TITLE());

		/* ============== 이미지 순서 변경 ============== */
		List<ProductVO> ordFile = reserveDao.selectFileOrd(vo);
		reserveDao.deleteFileOrd(vo);

		for (int j = 0; j < ordFile.size(); j++) {
			vo.setPRODUCT_SEQ(ordFile.get(j).getPRODUCT_SEQ());
			vo.setPRODUCT_FILE_TITLE(ordFile.get(j).getPRODUCT_FILE_TITLE());
			vo.setORD(j + "");

			reserveDao.updateProductOrd(vo);
		}

		return reserveDao.deleteSlideImgFile(vo);
	}

	@Override
	public ProductVO prevTableInfo(ProductVO vo) {
		return reserveDao.prevTableInfo(vo);
	}

	@Override
	public ProductVO nextTableInfo(ProductVO vo) {
		return reserveDao.nextTableInfo(vo);
	}

	@Override
	public ProductVO payTableInfo(ProductVO vo) {
		return reserveDao.payTableInfo(vo);
	}

	@Override
	public int insertTablePaid(PayVO vo) {
		return reserveDao.insertTablePaid(vo);
	}

	@Override
	public List<PayVO> selectPayComplete(PayVO vo) {
		return reserveDao.selectPayComplete(vo);
	}

	@Override
	public int selectPersonCount(PayVO vo) {
		return reserveDao.selectPersonCount(vo);
	}

	@Override
	public int updatePaymentStatus(CancelData cancel) {
		return reserveDao.updatePaymentStatus(cancel);
	}

	@Override
	public List<ProductVO> adminSelectDashBoard(ProductVO vo) {
		return reserveDao.adminSelectDashBoard(vo);
	}

	@Override
	public List<ProductVO> selectTableList(ProductVO vo) {
		return reserveDao.selectTableList(vo);
	}

	@Override
	public int tableStateYCount(SearchCriteria scri) {
		return reserveDao.tableStateYCount(scri);
	}

	@Override
	public MemberVO selectTotalCount(MemberVO vo) {
		return reserveDao.selectTotalCount(vo);
	}

	@Override
	public PayVO selectShortPerson(PayVO vo) {
		return reserveDao.selectShortPerson(vo);
	}

	@Override
	public int updateShippingCost(ProductVO vo) {
		return reserveDao.updateShippingCost(vo);
	}

	@Override
	public int updateProductChange(ProductVO vo) {
		return reserveDao.updateProductChange(vo);
	}

	@Override
	public List<PayVO> getProductInfo(PayVO vo) {
		return reserveDao.getProductInfo(vo);
	}

	@Override
	public List<PayVO> getStatusProductData(PayVO vo) {
		return reserveDao.getStatusProductData(vo);
	}

	@Override
	public PayVO selectProductBuyCheck(PayVO vo) {
		return reserveDao.selectProductBuyCheck(vo);
	}

	@Override
	public int updateSeveralStatus(PayVO vo) {
		return reserveDao.updateSeveralStatus(vo);
	}

	@Override
	public int selectProductCount(PayVO vo) {
		return reserveDao.selectProductCount(vo);
	}

	@Override
	public int selectCancelShippingCost(PayVO vo) {
		return reserveDao.selectCancelShippingCost(vo);
	}

	@Override
	public Map<String, Object> selectQnaProductInfo(ProductVO vo) {
		return reserveDao.selectQnaProductInfo(vo);
	}

	@Override
	public int selectMemberValid(PayVO vo) {
		return reserveDao.selectMemberValid(vo);
	}

	@Override
	public int applyReturnProduct(PayVO vo) {
		return reserveDao.applyReturnProduct(vo);
	}

	@Override
	public List<PayVO> selectShippingCost(PayVO vo) {
		return reserveDao.selectShippingCost(vo);
	}

	@Override
	public List<PayVO> getProductReturnRequest(PayVO vo) {
		return reserveDao.getProductReturnRequest(vo);
	}

	@Override
	public PayVO getStatusMemberMemo(PayVO vo) {
		return reserveDao.getStatusMemberMemo(vo);
	}

	@Override
	public int selectProductBuyValid(PayVO vo) {
		return reserveDao.selectProductBuyValid(vo);
	}

	@Override
	public ReviewVO selectProductReviewCount(ReviewVO vo) {
		return reserveDao.selectProductReviewCount(vo);
	}

	@Override
	public List<ProductVO> selectMainPageProductList(ProductVO vo) {
		return reserveDao.selectMainPageProductList(vo);
	}

	@Override
	public List<PayVO> selectAdminRefundList(PayVO vo) {
		return reserveDao.selectAdminRefundList(vo);
	}

	@Override
	public int selectOrderPayCount(PayVO vo) {
		return reserveDao.selectOrderPayCount(vo);
	}

	@Override
	public void listSortChange(ProductVO vo, HttpServletRequest request) {
		JsonParser parser = new JsonParser();
		String str = request.getParameter("arr");
    	JsonArray arr = (JsonArray) parser.parse(str);

    	for (int i = 0; i < arr.size(); i++) {
			JsonObject data = (JsonObject) arr.get(i);

			vo.setFILE_SEQ(data.get("fileSeq").getAsInt());	// 해당 인덱스
			vo.setORD(i + "");								// 순서

			reserveDao.updateListSort(vo);
    	}
	}

}
