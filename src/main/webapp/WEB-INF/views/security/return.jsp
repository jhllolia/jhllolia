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
		<div class="day">
			<div class="seDash">
				<ul class="se">
					<li>
						<div class="date">
							<ul class="dataCheck">
								<li><input type="text" name="firstDate" class="dateCheck" /><i class="icon"><img src='../resources/image/icon/calendar.png' alt='' /></i></li>
								<li><input type="text" name="lastDate" class="dateCheck" /><i class="icon"><img src='../resources/image/icon/calendar.png' alt='' /></i></li>
							</ul>

							<select name="searchOption" id="board-search" class="board-search">
								<option value="n" <c:out value="${param.searchType == null ? 'selected' : ''}"/>>:::: 선택 ::::</option>
								<option value="t" <c:out value="${param.searchType eq 't' ? 'selected' : ''}"/>>주문번호</option>
								<option value="c" <c:out value="${param.searchType eq 'c' ? 'selected' : ''}"/>>제품 명</option>
								<option value="tx" <c:out value="${param.searchType eq 'tx' ? 'selected' : ''}"/>>이메일</option>
								<option value="tc" <c:out value="${param.searchType eq 'tc' ? 'selected' : ''}"/>>휴대폰 번호</option>
							</select>

							<input type="text" id="payment_txt" name="payment_txt" class="form-control btn log" />
							<button id="sendDate" name="sendDate" class="btn log">조회</button>
						</div>
					</li>
				</ul>

				<div id="dayList" class="seList">
					<table id="return" class="paginated">
						<thead>
							<tr>
								<th>주문번호</th>
								<th>제품 명</th>
								<th>휴대전화</th>
								<th>유형</th>
								<th>사유</th>
								<th>상세사유</th>
								<th>결제취소</th>
							</tr>
						</thead>
						<tbody id="user_list"></tbody>
					</table>
				</div>
			</div>
		</div>

		<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
		<script type="text/javascript" src="https://code.jquery.com/jquery-1.12.4.js"></script>
		<script type="text/javascript" src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/admin/adReturn.js"></script>
	</div>
</li>

</s:authorize>

<jsp:include page="./ad_footer.jsp"></jsp:include>