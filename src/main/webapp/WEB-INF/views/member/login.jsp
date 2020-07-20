<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<jsp:include page="../header.jsp" />

<c:choose>
	<c:when test="${sessionScope.member_Id == null}">
		<div class="member_wrapper">
			<div id="login" class="login">
				<span class="logo">
					<img src="../resources/image/icon/favicon_logo.png" alt="" />
				</span>

				<p class="tit">
					<img src="../resources/image/main/max_login.png" alt="" />
					<span class="txt">로그인</span>
				</p>

				<script type="text/javascript">
					jQuery.noConflict();

					jQuery(function($) {
						$(document).ready(function() {
							var msg = "${msg}";								// 출력 결과
							var code = "${code}";							// 출력 코드
							var findTarget = "${param['find_target']}";		// 파라미터 1
							var codeTarget = "${param['code']}";			// 파라미터 2

							/* ======================================================== */
							if (msg == "auth") {
				        		$("#danger").show().html("이메일 인증이 정상적으로 완료되지 않았습니다. 이메일을 확인해주세요");
							}

							/* ======================================================== */
							if(code == "SUCCESS") {
				        		$("#success").show().html("인증이 완료되었습니다. :) 로그인해주세요.");
							} else if(code == "FAIL") {
				        		$("#danger").show().html("회원가입에 실패하셨습니다 :/ 다시 시도해주세요");					
							}

							/* ======================================================== */
							if(findTarget == "Y") {
				        		$("#success").show().html("임시 비밀번호가 발급되었습니다 :) 로그인해주세요.");
							} 

							/* ======================================================== */
							if(codeTarget == "OK") {
				        		$("#success").show().html("등록된 메일로 임시 비밀번호가 발급되었습니다. 이메일을 확인해주세요.");
							} else if (codeTarget == "otherWay") {
								$("#danger").show().html("해당 이메일로 일반 회원가입을 하시지 않았습니다. 네이버 / 카카오 로그인을 참고해 주세요. ");
							} else if (codeTarget == "failure") { 
								$("#danger").show().html("이메일 또는 비밀번호가 일치하지 않습니다.");
							} else if (codeTarget == "noneUser") {
								$("#danger").show().html("존재하질 않는 회원 입니다. <a href='/member/signup'>회원가입</a> 해주세요");
							} else if (codeTarget == "Auth") {
								$("#success").show().html("인증이 이뤄지지 않은 계정입니다. 이메일로 전송된 인증번호를 확인해주세요.");
							} else if(codeTarget == "reAuth") {
								$("#success").show().html("등록된 메일로 인증번호를 재 전송 되었습니다. 이메일을 확인해주세요.");
							}
						});
					});
				</script>

				<form name="form_login" method="post">
					<input style="display:none" aria-hidden="true" />
					<input type="password" style="display:none" aria-hidden="true" />

					<div class="input-wrap">
						<span>
							<input type="text" name="member_Id" id="member_Id" class="remember" autocomplete="false" placeholder="이메일">
						</span>
					</div>

					<div class="input-wrap">
						<span>
							<input type="password" name="member_Pw" id="member_Pw" autocomplete="new-password" placeholder="비밀번호">
						</span>
					</div>

					<label class="chk_login">
						<img id="idSave" class="idSave" src="../resources/image/icon/chk_off.png" alt="chk" />
						<input type="checkbox" title="아이디 저장" id="idSaveCheck" />
						<span>아이디 저장</span>
					</label>

					<div class="alert alert-success" id="success"></div>
					<div class="alert alert-danger" id="danger"></div>

					<button id="btn_login" class="btn log">로그인</button>

					<!--

					<script type="text/javascript" src="${pageContext.request.contextPath}/js/until/loginUntil.js"></script>
					<div class="other">
						<ul>
							<li id="kakako" onClick="location.href='https://kauth.kakao.com/oauth/authorize?client_id=8e17e941ff8ec2fc7c002f7273b50ae2&redirect_uri=http://localhost:8080/kakao/login&response_type=code'">
								<img src="../resources/image/main/kakao_login.png" alt="">
							</li>
							<li id="naver" name="naverLogin">
								<img src="../resources/image/main/naver_login.PNG" alt="">
							</li>
						</ul>
					</div>

  					-->
				</form>

				<jsp:include page="./link.jsp" />
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

<jsp:include page="../footer.jsp" />