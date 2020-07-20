<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:choose>
	<c:when test="${msg == 'failure'}">
		<div class="error">사용자 정보가 존재하질 않습니다.</div>
	</c:when>
	<c:otherwise>
		<jsp:include page="./member_Info.jsp" />

		<div id="info_update" class="info_update">
			<input style="display:none" aria-hidden="true" />
			<input type="password" style="display:none" aria-hidden="true" />

			<h4>비밀번호 변경</h4>

			<table>
				<tbody>
					<tr>
						<td>아이디</td>
						<td>${info.member_Id}</td>
					</tr>
					<tr>
						<td>현재 비밀번호</td>
						<td>
							<span>
								<c:choose>
									<c:when test="${sessionScope.member_Way ne 'Y'}">
										<input type="password" name="pwd_now" id="pwd_now" autocomplete="new-password" disabled="disabled" />
									</c:when>
									<c:otherwise>
										<input type="password" name="pwd_now" id="pwd_now" autocomplete="new-password" />
									</c:otherwise>
								</c:choose>
							</span>
						</td>
					</tr>
					<tr>
						<td>비밀번호</td>
						<td>
							<span>
								<c:choose>
									<c:when test="${sessionScope.member_Way ne 'Y'}">
										<input type="password" name="member_Pw" id="member_Pw" autocomplete="new-password" disabled="disabled" />
									</c:when>
									<c:otherwise>
										<input type="password" name="member_Pw" id="member_Pw" autocomplete="new-password" />
									</c:otherwise>
								</c:choose>

								<p><span>*</span>비밀번호는 숫자 + 영문자를 혼용하여 6~20자로 되어야 합니다.</p>
							</span>
						</td>
					</tr>
					<tr>
						<td>비밀번호 확인</td>
						<td>
							<span>
								<c:choose>
									<c:when test="${sessionScope.member_Way ne 'Y'}">
										<input type="password" name="member_Pw" id="member_Pw_chk" autocomplete="new-password" disabled="disabled" />
									</c:when>
									<c:otherwise>
										<input type="password" name="member_Pw" id="member_Pw_chk" autocomplete="new-password" />
									</c:otherwise>
								</c:choose>
							</span>
						</td>
					</tr>
				</tbody>
			</table>

			<div class="alert alert-success" id="pwd-success"></div>
			<div class="alert alert-danger" id="pwd-danger"></div>

			<c:choose>
				<c:when test="${sessionScope.member_Way ne 'Y'}">
					<div class="otherWay">
						<p class="txt">일반 회원가입 이외에는 정보를 수정하실 수 없습니다.</p>
					</div>
				</c:when>
				<c:otherwise>
					<button id="pwd_update" class="btn log">비밀번호 변경</button>
					<button id="previous" class="btn log" onclick="window.history.go(-1);">이전으로</button>
				</c:otherwise>
			</c:choose>
		</div>
	</c:otherwise>
</c:choose>

<jsp:include page="./info_footer.jsp"></jsp:include>