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
			<input aria-hidden="true" style="display:none" />
			<input type="password" style="display:none" aria-hidden="true" />

			<h4>회원정보 수정</h4>

			<table>
				<tbody>
					<tr>
						<td>이메일</td>
						<td>${info.member_Id}</td>
					</tr>
					<tr>
						<td>이름</td>
						<td>
							<span>
								<input type="text" name="name" id="name" value="${info.member_Name}" maxlength="20" />
								<p><span>*</span>악성 / 타유저 유사 이름 사용할 경우 영구 차단 될 수 있습니다.</p>
							</span>
						</td>
					</tr>
					<tr>
						<td>주소</td>
						<td>
							<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
							<script type="text/javascript" src="${pageContext.request.contextPath}/js/until/daumPost.js"></script>

							<div class="zip_txt">
								<input type="text" name="zip" id="zip" value="${info.member_Zip}" readOnly="readOnly" />
								<button value="검색" id="btnAddr" name="btnAddr">우편번호</button>
							</div>

							<div class="addr_txt">
								<input type="text" id="addr1" name="addr1" value="${info.member_Addr1}" readOnly="readOnly" />
								<input type="text" id="addr2" name="addr2" value="${info.member_Addr2}" />
							</div>

							<div id="process_layer" class="process_layer">
								<span id="layer_close">닫기</span>
							</div>
						</td>
					</tr>
					<tr>
						<td>휴대전화</td>
						<td>
							<div class="tel_txt">
								<c:set var="tel" value="${fn:split(info.member_Phone,'-')}" />
								<select name="tel_01" id="tel_01" value="${tel[0]}">
									<option value="010">010</option>
									<option value="017">017</option>
									<option value="019">019</option>
								</select>

								<input type="text" name="tel_02" id="tel_02" value="${tel[1]}" maxlength="4" />
								<input type="text" name="tel_03" id="tel_03" value="${tel[2]}" maxlength="4" />
							</div>

							<span>
								<p><span>*</span>휴대폰 번호는 제품 구매 / 취소 / 문의 / 질의응답에 이용됩니다. 정확하게 입력해주세요.</p>
							</span>
						</td>
					</tr>
				</tbody>
			</table>

			<button id="btn_update" class="btn log">회원정보 수정</button>
			<button id="previous" class="btn log" onclick="window.history.go(-1);">이전으로</button>

			<div class="auth d_auth" id="d_auth">
			
			</div>
		</div>

	</c:otherwise>
</c:choose>

<jsp:include page="./info_footer.jsp"></jsp:include>