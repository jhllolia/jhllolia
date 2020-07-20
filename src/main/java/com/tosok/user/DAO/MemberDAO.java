package com.tosok.user.DAO;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.tosok.user.Until.SearchCriteria;
import com.tosok.user.VO.CartVO;
import com.tosok.user.VO.LoginVO;
import com.tosok.user.VO.MemberVO;
import com.tosok.user.VO.PayVO;
import com.tosok.user.VO.ProductVO;
import com.tosok.user.VO.QnaVO;
import com.tosok.user.VO.ReviewVO;

public interface MemberDAO {

	public boolean loginCheck(MemberVO vo);

	public int loginAuth(MemberVO vo);
	public int idCheck(String member_chk);
	public int pwdCheck(MemberVO vo);
	public int pwdUpdate(MemberVO vo);
	public int signup(MemberVO vo);
	public int userUpdate(MemberVO vo);
	public int update_pw(MemberVO vo);
	public int update_authstatus(MemberVO vo);
	public int getProfileUpload(MemberVO vo);

	public int email_update(HttpServletResponse response, MemberVO vo);

	public void logout(HttpSession session);
	public String selectPaymentProfile(MemberVO vo);

	public MemberVO memberInfo(MemberVO vo);
	public MemberVO viewMember(MemberVO vo);
	public MemberVO memberInfoCnt(MemberVO vo);
	public MemberVO info_update(MemberVO vo);

	public List<MemberVO> adminSelectPerson(MemberVO vo);
	public List<PayVO> memberPaymentList(PayVO vo);
	public List<PayVO> adminSelectPayment(PayVO vo);

	public int otherSignup(MemberVO vo);

	public String find_Id(String member_chk);

	public MemberVO otherSignupWay(MemberVO vo);

	public int addShippingCourier(PayVO vo);
	public int insertQnAProduct(QnaVO vo);

	public List<QnaVO> selectQnAList(SearchCriteria scri);
	public int selectQnACount(QnaVO vo);
	public int updateUserQnAData(QnaVO vo);

	public List<QnaVO> selectAdminQnAList(SearchCriteria scri);
	public int updateAdminQnAData(QnaVO vo);
	public int updateAdminQnAState(QnaVO vo);

	public List<PayVO> selectProductReturnSEQ(PayVO vo);
	public int selectAdminQnACount(QnaVO vo);
	public int insertReviewUpload(ReviewVO vo);
	public int updateReviewUpload(ReviewVO vo);

	public List<ReviewVO> selectProductReview(ReviewVO vo);
	public List<ReviewVO> selectMemberReviewList(SearchCriteria scri);
	public int selectMemberReviewCount(ReviewVO vo);

	public ReviewVO getProductReviewData(ReviewVO vo);
	public int deleteProductReview(ReviewVO vo);

	public int insertCartProduct(CartVO vo);
	public int selectCartCount(CartVO vo);

	public ProductVO getCartProductUnit(ProductVO vo);

	public List<CartVO> selectCartMemberList(CartVO vo);

	public int selectCartProductVaild(CartVO vo);
	public int updateCartProductState(CartVO vo);
	public int haveMemberCartProduct(CartVO vo);
	public int updateCartUnitState(CartVO vo);
	public int updateCartProductQty(CartVO vo);
	public List<PayVO> selectRequestPayment(PayVO vo);

	public int deleteReviewImg(ReviewVO vo);
	public int insertLoginData(LoginVO vo);

	
}
