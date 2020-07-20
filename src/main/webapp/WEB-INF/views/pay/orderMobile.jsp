<%@ page import="org.springframework.security.core.Authentication" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="s" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="../header.jsp"></jsp:include>

<div class="pay_wrap">
	<c:choose>
		<c:when test="${empty sessionScope.member_Id}">
			<div class="done">
				<h4>사용자 정보</h4>
				<p class="txt">사용자 정보가 존재하질 않습니다.</p>
			</div>
		</c:when>
		<c:otherwise>
			<c:choose>
				<c:when test="${param.imp_success eq ''}">

				</c:when>
				<c:when test="${param.imp_success eq false}">
					<div class="orderFail">
						<h4>결제 중단</h4>
						<hr>

						<p class="txt">결제가 중단되었습니다. 재시도 해주세요.</p>
						<button name="fail_back" id="fail_back" class="btn log" onClick="location.href='../bbs/product'">상품구매 바로가기</button>
					</div>
				</c:when>
				<c:otherwise>

				</c:otherwise>
			</c:choose>
		</c:otherwise>
	</c:choose>

	<script type="text/javascript" src="https://cdn.iamport.kr/js/iamport.payment-1.1.5.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/payment.js"></script>
</div>

<jsp:include page="../footer.jsp"></jsp:include>