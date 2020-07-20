package com.tosok.user.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.tosok.user.IamPortRequest.CancelData;
import com.tosok.user.Until.SearchCriteria;
import com.tosok.user.VO.CartVO;
import com.tosok.user.VO.MemberVO;
import com.tosok.user.VO.PayVO;
import com.tosok.user.VO.ProductVO;
import com.tosok.user.VO.QnaVO;
import com.tosok.user.VO.ReviewVO;

public interface ReserveService {

	public List<ProductVO> listTableOrder(SearchCriteria scri);
	public List<ProductVO> selectTableFile(ProductVO vo);

	public List<ProductVO> selectTableList(ProductVO vo);
	public List<ProductVO> adminSelectDashBoard(ProductVO vo);

	public int insertSeatTable(ProductVO vo);
	public int insertFileUpload(ProductVO vo, HttpServletRequest request) throws Exception;
	public int deleteSeatTable(ProductVO vo, HttpServletRequest request) throws Exception;

	public int updateSeatTable(ProductVO vo);
	public int updateFileUpload(ProductVO vo, HttpServletRequest request) throws Exception;
	public int deleteSlideImgFile(ProductVO vo);

	public int selectPersonCount(PayVO vo);
	public int selectTableCount(SearchCriteria scri);
	public int insertTablePaid(PayVO vo);

	public ProductVO selectTableInfo(ProductVO vo);
	public ProductVO prevTableInfo(ProductVO vo);
	public ProductVO nextTableInfo(ProductVO vo);
	public ProductVO payTableInfo(ProductVO vo);

	public List<PayVO> selectPayComplete(PayVO vo);
	public List<PayVO> getProductInfo(PayVO vo);

	public MemberVO selectTotalCount(MemberVO vo);

	public int updatePaymentStatus(CancelData cancel);
	public int tableStateYCount(SearchCriteria scri);
	public int updateShippingCost(ProductVO vo);

	public PayVO selectShortPerson(PayVO vo);
	public int updateProductChange(ProductVO vo);
	public List<PayVO> getStatusProductData(PayVO vo);

	public PayVO selectProductBuyCheck(PayVO vo);

	public int updateSeveralStatus(PayVO vo);
	public int selectProductCount(PayVO vo);
	public int selectCancelShippingCost(PayVO vo);

	public Map<String, Object> selectQnaProductInfo(ProductVO vo);

	public int selectMemberValid(PayVO vo);
	public int applyReturnProduct(PayVO vo);
	public List<PayVO> selectShippingCost(PayVO vo);
	public List<PayVO> getProductReturnRequest(PayVO vo);
	public PayVO getStatusMemberMemo(PayVO vo);
	public int selectProductBuyValid(PayVO vo);
	public ReviewVO selectProductReviewCount(ReviewVO vo);
	public List<ProductVO> selectMainPageProductList(ProductVO vo);
	public List<PayVO> selectAdminRefundList(PayVO vo);
	public int selectOrderPayCount(PayVO vo);
	public void listSortChange(ProductVO vo, HttpServletRequest request);

}
