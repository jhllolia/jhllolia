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
			    	<img src="../resources/image/icon/loading.gif" />
			    </div>
			</div>

			<div class="signup">
				<h2>회원가입</h2>
				
				<!-- 휴대폰 이용동의 -->
				<div id="signUp_01" class="signup_wrapper">

				</div>
				
				<!-- 이용동의 -->
				<div id="signUp_02" class="signup_wrapper">
		
					<label for="all_terms" class="all_terms">
						<div class="all">
							<img src="../resources/image/icon/chk_off.png" alt="chk" />
							<input type="checkbox" id="check_all" name="all_terms_chk" />
						</div>
						<span class="all_terms_chk">어양토속식품 전체 약관에 동의합니다</span>
					</label>

					<article>
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
						
						<div class="alert alert-danger" id="terms-danger"></div>
					</article>

					<button id="btn_signup" class="btn log">회원가입</button>
				</div>

				<!-- 회원정보 수집 -->
				<div id="signUp_03" class="signup_wrapper" style="display:none;">
					<input style="display:none" type="text" name="fakeusernameremembered"/>
					<input style="display:none" type="password" name="fakepasswordremembered"/>
					<input style="display:none" aria-hidden="true" />
					<input type="password" style="display:none" aria-hidden="true" />

					<div class="input-wrap">
						<span>
							<input type="text" name="member_Id" id="member_Id" placeholder="이메일를 입력 해주세요.">
							<button id="id_chk">중복확인</button>
						</span>

						<p class="member_Id"><span>*</span>이메일 인증 후 회원가입이 완료 됩니다. 정확하게 기입해주세요. 또한 메일주소는 비밀번호 수정이나 회원정보 변경에 사용됩니다.</p>
					</div>
		
					<div class="alert alert-success" id="id-success"></div>
					<div class="alert alert-danger" id="id-danger"></div>

					<div class="input-wrap">
						<span><input type="password" name="member_Pw" id="member_Pw" maxlength="20" placeholder="비밀번호"></span>
						<span>
							<input type="password" name="member_Pw" id="member_Pw_chk" maxlength="20" placeholder="비밀번호 확인">
							<p class="member_Pw"><span>*</span>비밀번호는 숫자 + 영문자를 혼용하여 6~20자로 되어야 합니다.</p>
						</span>
					</div>
		
					<div class="alert alert-success" id="pwd-success"></div>
					<div class="alert alert-danger" id="pwd-danger"></div>
		
					<div class="input-wrap">
						<span>
							<input type="text" name="member_Name" id="member_Name" maxlength="20" placeholder="이름을 입력 해주세요.">
							<p class="member_Name"><span>*</span>이름은 20자 이내여야 합니다. 욕설 / 비속어가 사용될 경우 영구 차단 될 수 있습니다.</p>
						</span>
					</div>

					<div class="alert alert-danger" id="name-danger"></div>

					<div class="input-wrap">
						<ul class="sign_phone">
							<li>
								<select name="member_Phone" id="phone_1">
									<option value="">선택</option>
									<option value="010">010</option>
									<option value="017">017</option>
									<option value="011">011</option>
								</select>
							</li>
							<li><input type="text" name="member_Phone" id="phone_2" maxlength="4" /></li>
							<li><input type="text" name="member_Phone" id="phone_3" maxlength="4" /></li>
						</ul>
						<p class="member_Phone"><span>*</span>휴대폰 번호는 제품 구매 / 취소 / 문의 / 질의응답에 이용됩니다. 정확하게 입력해주세요. 또한 주문관리 -> 내 정보 수정에서 변경 하 실수 있습니다.</p>
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

					<button id="btn_signup" class="btn log">회원가입</button>
				</div>
			</div>
		</div>
	</c:when>
	<c:otherwise>
		<div class="member_wrapper">
			<div id="login" class="login">
				<h2>이미 로그인 되어있습니다.</h2>
				<button name="loginBack" class="btn log" onClick="location.href='../main'">메인으로</a>
			</div>
		</div>
	</c:otherwise>
</c:choose>

<script>
jQuery.noConflict();

jQuery(function($) {
	$(document).ready(function() {

		/* ==================== 메인 이용약관 / 개인정보보호 체크 ==================== */
		$('label[for="all_terms"]').click(function(e) {
			$(this).toggleClass('active');

			if($(this).hasClass('active')) {
				$(this).find("input:checkbox[name='all_terms_chk']").prop("checked", true);
				$(this).find("img").attr("src", "../resources/image/icon/chk_on.png");
			} else {
				$(this).find("input:checkbox[name='all_terms_chk']").prop("checked", false);
				$(this).find("img").attr("src", "../resources/image/icon/chk_off.png");
			}

			if($("input:checkbox[name='all_terms_chk']").is(':checked')) {
				$("input:checkbox[name='terms_chk']").prop("checked", true);
				$("input:checkbox[name='terms_chk']").next('img').attr("src", "../resources/image/icon/chk_on.png");
			} else {
				$("input:checkbox[name='terms_chk']").prop("checked", false);
				$("input:checkbox[name='terms_chk']").next('img').attr("src", "../resources/image/icon/chk_off.png");
			}
		});

		/* ==================== 서브 이용약관 / 개인정보보호 체크 ==================== */
		$('label[for="termsUse"]').click(function(e) {
			$(this).toggleClass('active');

			if($(this).hasClass('active')) {
				$(this).find("input[type='checkbox']").prop("checked", true);
				$(this).find('img').attr("src", "../resources/image/icon/chk_on.png");
			} else {
				$(this).find("input[type='checkbox']").prop("checked", false);
				$(this).find('img').attr("src", "../resources/image/icon/chk_off.png");
			}

			$("input[name='terms_chk']:checked").each(function(index, value) {
				var $this = $('label[for="all_terms"]');

				if(index == 1) {
					$this.addClass('active');
					$this.find("input:checkbox[name='all_terms_chk']").prop("checked", true);
					$this.find("img").attr("src", "../resources/image/icon/chk_on.png");
				} else {
					$this.removeClass('active');
					$this.find("input:checkbox[name='all_terms_chk']").prop("checked", false);
					$this.find("img").attr("src", "../resources/image/icon/chk_off.png");
				}
			});
		});

		/* ============================================================ */
		$('#open_txt_1').click( function() {
			var html = "";

			html += "<div class='term'>";
			html += "	<span id='close'><img src='../resources/image/icon/close.png' alt='close' /></span>";
			html += "	<div id='check_use' class='check'></div>";
			html += "</div>";

			$('#terms_txt_01').css('display','block').html(html);
			$('#check_use').load('../terms/termsUse #mTerms');
			
			termsClose();
		});

		/* ============================================================ */
		$('#open_txt_2').click( function() {
			var html = "";

			html += "<div class='term'>";
			html += "	<span id='close'><img src='../resources/image/icon/close.png' alt='close' /></span>";
			html += "	<div id='check_pri' class='check'></div>";
			html += "</div>";

			$('#terms_txt_02').css('display','block').html(html);
			$('#check_pri').load('../terms/termsPrivacy #mTerms');
			
			termsClose();
		});

		/* ============================================================ */
		$('#next').click(function() {
    		if($("input:checkbox[id='check_1']").is(":checked") == false) {
        		$("#terms-danger").show().html("모든 약관에 동의 하셔야 회원가입이 진행 가능합니다.");
            	$("input:checkbox[id='check_1']").focus(); 
    			return false;
    		} else if($("input:checkbox[id='check_2']").is(":checked") == false) {
        		$("#terms-danger").show().html("모든 약관에 동의 하셔야 회원가입이 진행 가능합니다.");
            	$("input:checkbox[id='check_2']").focus(); 
    			return false;
    		} else {
    			$('#terms').css('display','none');
    			$('#signup').css('display','block');
    		}
		});

		/* ============================================================ */
		function termsClose() {
			$('span#close').click(function() {
				$('.txt_content div').css('display','none');
			});
		}

	});
});

</script>

<jsp:include page="../footer.jsp" />