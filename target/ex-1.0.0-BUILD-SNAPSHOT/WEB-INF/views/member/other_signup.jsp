<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<jsp:include page="../header.jsp" />

<c:choose>
	<c:when test="${sessionScope.member_Id == null}">
		<div class="member_wrapper">
			<div class="wrap-loading display-none">
			    <div>
			    	<img src="../resources/images/icon/loading.gif" />
			    </div>
			</div>

			<div class="signup_wrap">
				<article>
					<div class="other_signup">
						<h2><span id="otherName" class="otherName">${info.member_Way}</span> 회원가입</h2>
						<div class="title">
							<input style="display:none" type="text" name="fakeusernameremembered"/>
							<input style="display:none" aria-hidden="true" />

							<div class="input-wrap">
								<span><strong>프로필</strong></span>
								<div class="profile">
									<img id="profile_img" class="profile_img" src="${info.member_Profile}" alt="" />							
								</div>
							</div>

							<div class="input-wrap">
								<span>
									<strong>이메일</strong>
									<input type="text" id="member_Id" autocomplete="false" value="${info.member_Id}" readOnly="readOnly" />
									<input type="hidden" id="member_Pw" value="${info.member_Pw}" readOnly="readOnly" />
								</span>
							</div>

							<div class="input-wrap">
								<span>
									<strong>닉네임</strong>
									<input type="text" id="member_Name" value="${info.member_Name}" readOnly="readOnly" />
								</span>
							</div>

							<div class="alert alert-danger" id="info-danger"></div>

							<div class="input-wrap">
								<strong>휴대폰 번호</strong>
								<ul class="sign_phone">
									<li>
										<select name="member_Phone" id="phone_1">
											<option value="">통신사 선택</option>
											<option value="010">010</option>
											<option value="017">017</option>
											<option value="011">011</option>
										</select>
									</li>
									<li><input type="text" name="member_Phone" id="phone_2" maxlength="4" /></li>
									<li><input type="text" name="member_Phone" id="phone_3" maxlength="4" /></li>
								</ul>
								<p class="member_Phone"><span>*</span>네이버 / 카카오 회원가입 시 휴대폰 번호를 통해 수정할 수 있습니다.</p>
							</div>

							<div class="alert alert-danger" id="phone-danger"></div>
				
							<div id="process_layer" class="process_layer"></div>

							<div class="postCode">
								<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
								<script type="text/javascript" src="${pageContext.request.contextPath}/js/until/daumPost.js"></script>

								<input type="text" name="zip" id="zip" placeholder="우편번호" readonly="readonly" />
								<input type="button" value="검색" id="btnAddr" name="btnAddr">
								<input type="text" name="addr1" id="addr1" placeholder="주소" readonly="readonly" />
								<input type="text" name="addr2" id="addr2" placeholder="상세주소" />
							</div>

							<div class="alert alert-danger" id="post-danger"></div>

							<label for="all_terms" class="all_terms">
								<div class="all">
									<img src="../resources/image/icon/chk_off.png" alt="chk" />
									<input type="checkbox" id="check_all" name="all_terms_chk" />
								</div>
								<span class="all_terms_chk">어양토속식품 전체 약관에 동의합니다</span>
							</label>
				
							<article id="other">
								<div class="terms">
									<div class="terms_txt">
										<h4 class="scheme-g">
											<span>*</span>이용약관
											<p>
												<label for="termsUse">
													<input type="checkbox" id="check_1" name="terms_chk" value="1" />
													<img class="sub_check" src="../resources/image/icon/chk_off.png" alt="chk" />
													위의 약관에 동의 합니다
												</label>
												<i id="open_txt_1"></i>
											</p>
										</h4>
										<div class="txt_content">
											<div id="terms_txt_01"></div>
										</div>
									</div>
				
									<div class="terms_txt">
										<h4 class="scheme-g">
											<span>*</span>개인정보취급방침
											<p>
												<label for="termsUse">
													<input type="checkbox" id="check_2" name="terms_chk" value="1" />
													<img class="sub_check" src="../resources/image/icon/chk_off.png" alt="chk" />
													위의 약관에 동의 합니다
												</label>
												<i id="open_txt_2"></i>
											</p>
										</h4>
										<div class="txt_content">
											<div id="terms_txt_02"></div>
										</div>
									</div>
								</div>
								
								<div class="alert alert-danger" id="terms-danger">모든 약관에 동의 하셔야 회원가입이 진행 가능합니다.</div>
							</article>

							<button id="btn_other" class="btn log">회원가입</button>
						</div>
					</div>
				</article>
			</div>
		</div>
	</c:when>
	<c:otherwise>
		<div class="member_wrapper">
			<div id="login" class="login">
				<h2>이미 로그인 되어있습니다.</h2>
				<button name="loginBack" class="btn log" onClick="history.back(-1); return false;">이전으로</a>
			</div>
		</div>
	</c:otherwise>
</c:choose>

<jsp:include page="../footer.jsp" />