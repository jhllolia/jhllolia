<%@ page import="org.springframework.security.core.Authentication" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="s" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="../header.jsp"></jsp:include>

<s:authorize ifNotGranted="ROLE_ADMIN">
	<div class="">
		<p>로그아웃</p>
	</div>
</s:authorize>

<!-- <s:authentication property="name"/> -->

<s:authorize ifAnyGranted="ROLE_ADMIN">
	<div class="wrap">
		<div class="ad">
			<ul class="adContain">
				<li id="menu_list">
					<nav id="adMenu">
						<form method="post" action="admin">
						   	<div class="iamAdmin">
						    	<a href="${pageContext.request.contextPath}/j_spring_security_logout" class="btn log">로그 아웃</a>
							</div>
						</form>

						<ul>
							<li><a href="../admin" class="cliEvent">결제정보</a></li>
							<li><a href="../qna" class="cliEvent">상품QnA</a></li>
							<li><a href="../return" class="cliEvent">반품/교환</a></li>
							<li><a href="../review" class="cliEvent">고객 상품평</a></li>
							<li><a href="../product/table" class="cliEvent">상품 리스트</a></li>
							<li><a href="../memberList" class="cliEvent">회원 리스트</a></li>
							<li><a href="../come" class="cliEvent">방문자 리스트</a></li>
							<li>
								<div class="web">
									<h4>방문자</h4>
									<p class="wb_txt"><span>Total</span><fmt:formatNumber value="${sessionScope.totalCount}" pattern="#,###" /></p>
								</div>
							</li>
						</ul>
					</nav>
				</li>

</s:authorize>
