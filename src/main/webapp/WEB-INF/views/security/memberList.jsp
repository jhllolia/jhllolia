<%@ page import="org.springframework.security.core.Authentication" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="s" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<s:authorize ifNotGranted="ROLE_ADMIN">
	<p>로그아웃</p>
</s:authorize>

<s:authorize ifAnyGranted="ROLE_ADMIN">

<jsp:include page="./admin.jsp" />

<li id="menu_result">
	<div class="adMain">
		<div class="userCheck">
			<ul>
				<li>
					<select name="searchOption" id="board-search" class="form-control">
						<option value="n" <c:out value="${param.searchType == null ? 'selected' : ''}"/>>:::: 선택 ::::</option>
						<option value="t" <c:out value="${param.searchType eq 't' ? 'selected' : ''}"/>>아이디</option>
						<option value="c" <c:out value="${param.searchType eq 'c' ? 'selected' : ''}"/>>이름</option>
						<option value="tc" <c:out value="${param.searchType eq 'tc' ? 'selected' : ''}"/>>휴대폰</option>
					</select>
				</li>
				<li>
					<input type="text" id="searchTxt" name="searchTxt" class="form-control" value="" />
				</li>
				<li>
					<button id="sendPerson" name="sendPerson" class="btn log">조회</button>
				</li>
			</ul>
		</div>

		<div class="memResult">
			<table class="paginated">
				<thead>
					<th>아이디</th>
					<th>이름</th>
					<th>휴대폰 번호</th>
					<th>결제 금액</th>
					<th>결제 횟수</th>
					<th>로그인 횟수</th>
					<th>인증 여부</th>
					<th>가입 일자</th>
				</thead>
				<tbody id="member_List">

				</tbody>
			</table>
		</div>

		<script type="text/javascript" src="${pageContext.request.contextPath}/js/admin/adUser.js"></script>
	</div>
</li>

</s:authorize>

<jsp:include page="./ad_footer.jsp"></jsp:include>