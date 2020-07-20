package com.tosok.user.DAO.impl;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tosok.user.DAO.MemberDAO;
import com.tosok.user.Until.SearchCriteria;
import com.tosok.user.VO.CartVO;
import com.tosok.user.VO.LoginVO;
import com.tosok.user.VO.MemberVO;
import com.tosok.user.VO.PayVO;
import com.tosok.user.VO.ProductVO;
import com.tosok.user.VO.QnaVO;
import com.tosok.user.VO.ReviewVO;

@Repository("MemberDAO")
public class MemberDAOImpl implements MemberDAO {

    @Autowired
    private SqlSession sqlSession;

    public void setSqlSession(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

	@Override
	public boolean loginCheck(MemberVO vo) {
		/* ========== 로그인 체크 ========== */
		String name = sqlSession.selectOne("member.loginCheck", vo);
		return (name == null) ? false : true;
	}

	@Override
	public int loginAuth(MemberVO vo) {
		return sqlSession.selectOne("loginAuth", vo);	
	}

	@Override
	public MemberVO viewMember(MemberVO vo) {
		return sqlSession.selectOne("member.viewMember", vo);
	}

	@Override
	public void logout(HttpSession session) {

	}

	@Override
	public int idCheck(String member_chk) {
		return sqlSession.selectOne("idCheck", member_chk);
	}

	@Override
	public int pwdCheck(MemberVO vo) {
		return sqlSession.selectOne("pwdCheck", vo);
	}

	@Override
	public int signup(MemberVO vo) {
		return sqlSession.insert("signup", vo);
	}

	@Override
	public int update_pw(MemberVO vo) {
		return sqlSession.update("member.update_pw", vo);
	}

	@Override
	public MemberVO memberInfo(MemberVO vo) {
		return sqlSession.selectOne("member.memberInfo", vo);
	}

	@Override
	public int getProfileUpload(MemberVO vo) {
		return sqlSession.update("member.getProfileUpload", vo);
	}

	@Override
	public MemberVO info_update(MemberVO vo) {
		return sqlSession.selectOne("member.info_update", vo);
	}

	@Override
	public int update_authstatus(MemberVO vo) {
		return sqlSession.update("member.updateAuthstatus", vo);
	}

	@Override
	public int pwdUpdate(MemberVO vo) {
		return sqlSession.update("member.pwdUpdate", vo);
	}

	@Override
	public int userUpdate(MemberVO vo) {
		return sqlSession.update("member.userUpdate", vo);
	}

	@Override
	public int email_update(HttpServletResponse response, MemberVO vo) {
		return sqlSession.update("member.email_update", vo);
	}

	@Override
	public List<PayVO> memberPaymentList(PayVO vo) {
		return sqlSession.selectList("payment.memberPaymentList", vo);
	}

	@Override
	public List<PayVO> adminSelectPayment(PayVO vo) {
		return sqlSession.selectList("payment.adminSelectPayment", vo);
	}

	@Override
	public String selectPaymentProfile(MemberVO vo) {
		return sqlSession.selectOne("payment.selectPaymentProfile", vo);
	}

	@Override
	public List<MemberVO> adminSelectPerson(MemberVO vo) {
		return sqlSession.selectList("member.adminSelectPerson", vo);
	}

	@Override
	public MemberVO memberInfoCnt(MemberVO vo) {
		return sqlSession.selectOne("member.memberInfoCnt", vo);
	}

	@Override
	public int otherSignup(MemberVO vo) {
		return sqlSession.insert("member.otherSignup", vo);
	}

	@Override
	public int insertReviewUpload(ReviewVO vo) {
		sqlSession.update("member.insertAfterChange", vo);

		return sqlSession.insert("member.insertReviewUpload", vo);
	}

	@Override
	public int deleteProductReview(ReviewVO vo) {
		sqlSession.update("member.deleteAfterChange", vo);

		return sqlSession.update("member.deleteProductReview", vo);
	}

	@Override
	public int updateReviewUpload(ReviewVO vo) {
		return sqlSession.update("member.updateReviewUpload", vo);
	}

	@Override
	public String find_Id(String member_chk) {
		return sqlSession.selectOne("member.find_Id", member_chk);
	}

	@Override
	public MemberVO otherSignupWay(MemberVO vo) {
		return sqlSession.selectOne("member.otherSignupWay", vo);
	}

	@Override
	public int addShippingCourier(PayVO vo) {
		return sqlSession.update("payment.addShippingCourier", vo);
	}

	@Override
	public int insertQnAProduct(QnaVO vo) {
		return sqlSession.insert("qna.insertQnAProduct", vo);
	}

	@Override
	public List<QnaVO> selectQnAList(SearchCriteria scri) {
		return sqlSession.selectList("qna.selectQnAList", scri);
	}

	@Override
	public int selectQnACount(QnaVO vo) {
		return sqlSession.selectOne("qna.selectQnACount", vo);
	}

	@Override
	public int updateUserQnAData(QnaVO vo) {
		return sqlSession.update("qna.updateUserQnAData", vo);
	}

	@Override
	public List<QnaVO> selectAdminQnAList(SearchCriteria scri) {
		return sqlSession.selectList("qna.selectAdminQnAList", scri);
	}

	@Override
	public int updateAdminQnAData(QnaVO vo) {
		return sqlSession.update("qna.updateAdminQnAData", vo);
	}

	@Override
	public int updateAdminQnAState(QnaVO vo) {
		return sqlSession.update("qna.updateAdminQnAState", vo);
	}

	@Override
	public List<PayVO> selectProductReturnSEQ(PayVO vo) {
		return sqlSession.selectList("member.selectProductReturnSEQ", vo);
	}

	@Override
	public int selectAdminQnACount(QnaVO vo) {
		return sqlSession.selectOne("qna.selectAdminQnACount", vo);
	}

	@Override
	public List<ReviewVO> selectProductReview(ReviewVO vo) {
		return sqlSession.selectList("member.selectProductReview", vo);
	}

	@Override
	public List<ReviewVO> selectMemberReviewList(SearchCriteria scri) {
		return sqlSession.selectList("member.selectMemberReviewList", scri);
	}

	@Override
	public int selectMemberReviewCount(ReviewVO vo) {
		return sqlSession.selectOne("member.selectMemberReviewCount", vo);
	}

	@Override
	public ReviewVO getProductReviewData(ReviewVO vo) {
		return sqlSession.selectOne("member.getProductReviewData", vo);
	}

	@Override
	public int insertCartProduct(CartVO vo) {
		return sqlSession.insert("cart.insertCartProduct", vo);
	}

	@Override
	public int selectCartCount(CartVO vo) {
		return sqlSession.selectOne("cart.selectCartCount", vo);
	}

	@Override
	public ProductVO getCartProductUnit(ProductVO vo) {
		return sqlSession.selectOne("cart.getCartProductUnit", vo);
	}

	@Override
	public List<CartVO> selectCartMemberList(CartVO vo) {
		return sqlSession.selectList("cart.selectCartMemberList", vo);
	}

	@Override
	public int selectCartProductVaild(CartVO vo) {
		return sqlSession.selectOne("cart.selectCartProductVaild", vo);
	}

	@Override
	public int updateCartProductState(CartVO vo) {
		return sqlSession.update("cart.updateCartProductState", vo);
	}

	@Override
	public int haveMemberCartProduct(CartVO vo) {
		return sqlSession.selectOne("cart.haveMemberCartProduct", vo);
	}

	@Override
	public int updateCartUnitState(CartVO vo) {
		return sqlSession.update("cart.updateCartUnitState", vo);
	}

	@Override
	public int updateCartProductQty(CartVO vo) {
		return sqlSession.update("cart.updateCartProductQty", vo);
	}

	@Override
	public List<PayVO> selectRequestPayment(PayVO vo) {
		return sqlSession.selectList("payment.selectRequestPayment", vo);
	}

	@Override
	public int deleteReviewImg(ReviewVO vo) {
		return sqlSession.update("member.deleteReviewImg", vo);
	}

	@Override
	public int insertLoginData(LoginVO vo) {
		return sqlSession.insert("member.insertLoginData", vo);
	}

}
