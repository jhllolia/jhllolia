<%@ page import="org.springframework.security.core.Authentication" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="s" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="../header.jsp"></jsp:include>

<div class="pay_wrap">
	<div class="wrap-loading display-none"></div>
	<div class="done">
		<h4 class="tit">
			<img src="../resources/image/icon/bill.png" alt="">
			<span class="txt">결제 정보</span>
		</h4>
		<hr />

		<c:choose>
			<c:when test=" ${empty sessionScope.member_Id}">
				<div class="payDone">
					<h4>사용자 정보</h4>
					<p class="txt">사용자 정보가 존재하질 않습니다.</p>
				</div>
			</c:when>
			<c:otherwise>
				<div class="payDone">
					<c:forEach items="${order}" var="dto" varStatus="status">
						<ul name="info_${status.index}">
							<li>
								<div class="goods_item">
									<a href="../tab/${dto.PRODUCT_NUM}" class="goods_link">
										<img src='../resources/upload/table/${dto.PRODUCT_NUM}/${dto.PRODUCT_PROFILE}' alt='${dto.PRODUCT_NAME}' />	
									</a>

									<div class="goods_name">
										<h4>${dto.PRODUCT_NAME}<span name="txt">${dto.PRODUCT_QTY} 개</span></h4>
										<ul class="item">
											<li><fmt:formatNumber type="number" maxFractionDigits="3" value="${dto.PRODUCT_PRICE * dto.PRODUCT_QTY}" /> 원</li>
											<li><fmt:formatDate value="${dto.PAID_DATE}" pattern="yyyy.MM.dd"/></li>
										</ul>
										<p class="txt">
											<c:choose>
												<c:when test="${dto.PAID_STATUS eq 'paid'}">
													<span class="paid">구매 완료</span>
												</c:when>
												<c:otherwise>
													<span class="fail">오류</span>
												</c:otherwise>
											</c:choose>
										</p>
									</div>
								</div>
							</li>
						</ul>
					</c:forEach>
				</div>

				<c:forEach items="${order}" var="dto" varStatus="status" begin="0" end="0">
					<div class="payTotal">
						<ul>
							<li>
								<div class="detail">
									<h4>상품 금액</h4>
									<p class="txt"><fmt:formatNumber type="number" maxFractionDigits="3" value="${dto.BUYER_PAID_TOTAL}" /> 원</p>
								</div>
							</li>
							<li>
								<div class="detail">
									<h4>배송비</h4>
									<p class="txt"><fmt:formatNumber type="number" maxFractionDigits="3" value="${dto.PRODUCT_SHIPPING_COST}" /> 원</p>
								</div>
							</li>
							<li>
								<div class="detail">
									<h4>결제 금액</h4>
									<p class="txt"><fmt:formatNumber type="number" maxFractionDigits="3" value="${dto.BUYER_PAID_TOTAL + dto.PRODUCT_SHIPPING_COST}" /> 원</p>
								</div>
							</li>
						</ul>
					</div>

					<div class="payBuyer" data-seq="${dto.BUYER_SEQ}">
						<h4>구매자 정보</h4>
						<hr />

						<table class="buyerInfo">
							<tbody>
								<tr>
									<th>이름</th>
									<td class="txt">${dto.BUYER_NAME}</td>
									<td class="result" rowspan="5">
										<div class="settle">
											<p><span class="td_01">결제 PG</span>${dto.PAID_PG_PROVIDER}</p>
											<p><span class="td_02">결제 방법</span>${dto.PAID_METHOD}</p>
											<button class="btn log" onClick='window.open("${dto.PAID_RECEIPT}")'>영수증</button>
										</div>
									</td> 
								</tr>
								<tr>
									<th>우편번호</th>
									<td class="txt">${dto.BUYER_POSTCODE}</td>
								</tr>
								<tr>
									<th>주소</th>
									<td class="txt">${dto.BUYER_ADDR}</td>
								</tr>
								<tr>
									<th>이메일</th>
									<td class="txt">${dto.BUYER_EMAIL}</td>
								</tr>
								<tr>
									<th>휴대폰</th>
									<td class="txt">${dto.BUYER_TEL}</td>
								</tr>
							</tbody>
						</table>

						<ul class="sendLink">
							<li><button name="productAgain" onClick="location.href='../tab/${dto.PRODUCT_NUM}'" class="btn log">재 구매 바로가기</button></li>
							<li><button name="myInfo" onClick="location.href='../info/info_payment'" class="btn log">구매 리스트 확인</button></li>
						</ul>
					</div>
				</c:forEach>
			</c:otherwise>
		</c:choose>
	</div>
</div>

<jsp:include page="../footer.jsp"></jsp:include>