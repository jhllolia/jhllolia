package com.tosok.user.DAO;

import java.util.List;
import java.util.Map;

import com.tosok.user.IamPortRequest.CancelData;
import com.tosok.user.Until.SearchCriteria;
import com.tosok.user.VO.MemberVO;
import com.tosok.user.VO.PayVO;
import com.tosok.user.VO.ProductVO;
import com.tosok.user.VO.ReviewVO;

public interface ReserveDAO {

	public List<ProductVO> listTableOrder(SearchCriteria scri);
	public List<ProductVO> selectTableFile(ProductVO vo);

	public int insertSeatTable(ProductVO vo);
	public int insertFileUpload(ProductVO vo);

	public int updateSeatTable(ProductVO vo);
	public int updateFileUpload(ProductVO vo);
	public int selectFileExist(ProductVO vo);
	public int deleteSlideImgFile(ProductVO vo);

	public int deleteSeatTable(ProductVO vo);
	public int selectTableCount(SearchCriteria scri);
	public int selectPersonCount(PayVO vo);	
	public int insertTablePaid(PayVO vo);

	public ProductVO selectTableInfo(ProductVO vo);
	public ProductVO prevTableInfo(ProductVO vo);
	public ProductVO nextTableInfo(ProductVO vo);
	public ProductVO payTableInfo(ProductVO vo);

	public List<PayVO> selectPayComplete(PayVO vo);

	public List<ProductVO> adminSelectDashBoard(ProductVO vo);
	public List<ProductVO> selectTableList(ProductVO vo);

	public int tableStateYCount(SearchCriteria scri);
	public int updatePaymentStatus(CancelData cancel);

	public MemberVO selectTotalCount(MemberVO vo);

	public List<PayVO> selectPayTimeCheck(PayVO vo);
	public List<PayVO> getProductInfo(PayVO vo);

	public PayVO selectShortPerson(PayVO vo);
	public int updateShippingCost(ProductVO vo);
	public int updateProductChange(ProductVO vo);

	public List<ProductVO> selectProductInfo(ProductVO vo);
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
	public List<PayVO> selectPaymentCartProduct(PayVO vo);
	public List<ProductVO> selectMainPageProductList(ProductVO vo);
	public List<PayVO> selectAdminRefundList(PayVO vo);
	public int selectOrderPayCount(PayVO vo);

	public int selectProductValid(ProductVO vo);
	public int updateProductOrd(ProductVO vo);
	public List<ProductVO> selectFileOrd(ProductVO vo);
	public void deleteFileOrd(ProductVO vo);
	public int updateListSort(ProductVO vo);

}
