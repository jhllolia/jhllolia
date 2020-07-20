package com.tosok.user.DAO.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tosok.user.DAO.ReserveDAO;
import com.tosok.user.IamPortRequest.CancelData;
import com.tosok.user.Until.SearchCriteria;
import com.tosok.user.VO.MemberVO;
import com.tosok.user.VO.PayVO;
import com.tosok.user.VO.ProductVO;
import com.tosok.user.VO.ReviewVO;

@Repository("ReserveDAO")
public class ReserveDAOImpl implements ReserveDAO {

    @Autowired
    private SqlSession sqlSession;

    public void setSqlSession(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

	@Override
	public int insertSeatTable(ProductVO vo) {
		return sqlSession.insert("reserve.insertSeatTable", vo);
	}

	@Override
	public int insertFileUpload(ProductVO vo) {
		return sqlSession.insert("reserve.insertFileUpload", vo);
	}

	@Override
	public List<ProductVO> listTableOrder(SearchCriteria scri) {
		return sqlSession.selectList("reserve.listTableOrder", scri);
	}

	@Override
	public int selectTableCount(SearchCriteria scri) {
		return sqlSession.selectOne("reserve.selectTableCount", scri);
	}

	@Override
	public int deleteSeatTable(ProductVO vo) {
		return sqlSession.update("reserve.deleteSeatTable", vo);
	}

	@Override
	public ProductVO selectTableInfo(ProductVO vo) {
		return sqlSession.selectOne("reserve.selectTableInfo", vo);
	}

	@Override
	public List<ProductVO> selectTableFile(ProductVO vo) {
		return sqlSession.selectList("reserve.selectTableFile", vo);
	}

	@Override
	public int updateSeatTable(ProductVO vo) {
		return sqlSession.update("reserve.updateSeatTable", vo);
	}

	@Override
	public int updateFileUpload(ProductVO vo) {
		return sqlSession.update("reserve.updateFileUpload", vo);
	}

	@Override
	public int selectFileExist(ProductVO vo) {
		return sqlSession.selectOne("reserve.selectFileExist", vo);
	}

	@Override
	public int deleteSlideImgFile(ProductVO vo) {		
		return sqlSession.update("reserve.deleteSlideImgFile", vo);
	}

	@Override
	public ProductVO prevTableInfo(ProductVO vo) {
		return sqlSession.selectOne("reserve.prevTableInfo", vo);
	}

	@Override
	public ProductVO nextTableInfo(ProductVO vo) {
		return sqlSession.selectOne("reserve.nextTableInfo", vo);
	}

	@Override
	public ProductVO payTableInfo(ProductVO vo) {
		return sqlSession.selectOne("reserve.payTableInfo", vo);
	}

	@Override
	public int insertTablePaid(PayVO vo) {
		return sqlSession.insert("payment.insertTablePaid", vo);
	}

	@Override
	public List<PayVO> selectPayComplete(PayVO vo) {
		return sqlSession.selectList("payment.selectPayComplete", vo);
	}

	@Override
	public int selectPersonCount(PayVO vo) {
		return sqlSession.selectOne("payment.selectPersonCount", vo);
	}

	@Override
	public int updatePaymentStatus(CancelData cancel) {
		return sqlSession.update("payment.updatePaymentStatus", cancel);
	}

	@Override
	public List<ProductVO> adminSelectDashBoard(ProductVO vo) {
		return sqlSession.selectList("payment.adminSelectDashBoard", vo);
	}

	@Override
	public List<ProductVO> selectTableList(ProductVO vo) {
		return sqlSession.selectList("reserve.selectTableList", vo);
	}

	@Override
	public int tableStateYCount(SearchCriteria scri) {
		return sqlSession.selectOne("reserve.tableStateYCount", scri);
	}

	@Override
	public MemberVO selectTotalCount(MemberVO vo) {
		return sqlSession.selectOne("member.selectTotalCount", vo);
	}

	@Override
	public List<PayVO> selectPayTimeCheck(PayVO vo) {
		return sqlSession.selectList("payment.selectPayTimeCheck", vo);
	}

	@Override
	public PayVO selectShortPerson(PayVO vo) {
		return sqlSession.selectOne("payment.selectShortPerson", vo);
	}

	@Override
	public int updateShippingCost(ProductVO vo) {
		return sqlSession.update("reserve.updateShippingCost", vo);
	}

	@Override
	public int updateProductChange(ProductVO vo) {
		return sqlSession.update("reserve.updateProductChange", vo);
	}

	@Override
	public List<ProductVO> selectProductInfo(ProductVO vo) {
		return sqlSession.selectList("reserve.selectProductInfo", vo);
	}

	@Override
	public List<PayVO> getProductInfo(PayVO vo) {
		return sqlSession.selectList("reserve.getProductInfo", vo);
	}

	@Override
	public List<PayVO> getStatusProductData(PayVO vo) {
		return sqlSession.selectList("payment.getStatusProductData", vo);
	}

	@Override
	public PayVO selectProductBuyCheck(PayVO vo) {
		return sqlSession.selectOne("payment.selectProductBuyCheck", vo);
	}

	@Override
	public int updateSeveralStatus(PayVO vo) {
		return sqlSession.update("payment.updateSeveralStatus", vo);
	}

	@Override
	public int selectProductCount(PayVO vo) {
		return sqlSession.selectOne("payment.selectProductCount", vo);
	}

	@Override
	public int selectCancelShippingCost(PayVO vo) {
		return sqlSession.selectOne("payment.selectCancelShippingCost", vo);
	}

	@Override
	public Map<String, Object> selectQnaProductInfo(ProductVO vo) {
		return sqlSession.selectOne("qna.selectQnaProductInfo", vo);
	}

	@Override
	public int selectMemberValid(PayVO vo) {
		return sqlSession.selectOne("payment.selectMemberValid", vo);
	}

	@Override
	public int applyReturnProduct(PayVO vo) {
		return sqlSession.update("payment.applyReturnProduct", vo);
	}

	@Override
	public List<PayVO> selectShippingCost(PayVO vo) {
		return sqlSession.selectList("payment.selectShippingCost", vo);
	}

	@Override
	public List<PayVO> getProductReturnRequest(PayVO vo) {
		return sqlSession.selectList("payment.getProductReturnRequest", vo);
	}

	@Override
	public PayVO getStatusMemberMemo(PayVO vo) {
		return sqlSession.selectOne("payment.getStatusMemberMemo", vo);
	}

	@Override
	public int selectProductBuyValid(PayVO vo) {
		return sqlSession.selectOne("payment.selectProductBuyValid", vo);
	}

	@Override
	public ReviewVO selectProductReviewCount(ReviewVO vo) {
		return sqlSession.selectOne("reserve.selectProductReviewCount", vo);
	}

	@Override
	public List<PayVO> selectPaymentCartProduct(PayVO vo) {
		return sqlSession.selectList("cart.selectPaymentCartProduct", vo);
	}

	@Override
	public List<ProductVO> selectMainPageProductList(ProductVO vo) {
		return sqlSession.selectList("reserve.selectMainPageProductList", vo);
	}

	@Override
	public List<PayVO> selectAdminRefundList(PayVO vo) {
		return sqlSession.selectList("payment.selectAdminRefundList", vo);
	}

	@Override
	public int selectOrderPayCount(PayVO vo) {
		return sqlSession.selectOne("payment.selectOrderPayCount", vo);
	}

	@Override
	public int selectProductValid(ProductVO vo) {
		return sqlSession.selectOne("reserve.selectProductValid", vo);
	}

	@Override
	public int updateProductOrd(ProductVO vo) {
		return sqlSession.update("reserve.updateProductOrd", vo);
	}

	@Override
	public List<ProductVO> selectFileOrd(ProductVO vo) {
		return sqlSession.selectList("reserve.selectFileOrd", vo);
	}

	@Override
	public void deleteFileOrd(ProductVO vo) {
		sqlSession.delete("reserve.deleteFileOrd", vo);
	}

	@Override
	public int updateListSort(ProductVO vo) {
		return sqlSession.update("reserve.updateListSort", vo);
	}
}
