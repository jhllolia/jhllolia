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

<jsp:include page="../security/admin.jsp" />

<li id="menu_result">
	<div class="adMain">
		<div class="seat_container">
			<div class="seat">
				<h2>판매 리스트</h2>
				<hr />

				<div class="seatList">
					<table>
						<thead>
							<tr>
								<th></th>
								<th>제품 제목</th>
								<th>제품 정보</th>
								<th>구성 (이름 / 가격 / 할인(%))</th>
								<th>배송비</th>
								<th>판매 상태</th>
								<th>수정</th>
							</tr>
						</thead>
						<tbody id="order">
							<c:choose>
								<c:when test="${fn:length(list) == 0}">
									<tr>
										<td colspan="7" class="none">등록된 테이블이 없습니다.</td>
									</tr>
								</c:when>
								<c:otherwise>
									<c:forEach items="${list}" var="dto" varStatus="status">
										<tr>
											<td>
												<a href="/tab/${dto.PRODUCT_SEQ}" class="tit">
													<img src="../resources/upload/table/${dto.PRODUCT_SEQ}/${dto.PRODUCT_FILE_NAME}" alt="" />
												</a>
											</td>
											<td>
												<h5>${dto.PRODUCT_TITLE}</h5>
												<span>
													<c:if test="${dto.PRODUCT_SALE_MODE eq 'SLEEVE'}">소매</c:if>
													<c:if test="${dto.PRODUCT_SALE_MODE eq 'WHOLE'}">도매</c:if>
												</span>
											</td>
											<td>
												<div class="sub_txt">${dto.PRODUCT_INFO}</div>
											</td>
											<td name="info_${status.index}" class="infoData" seq="${dto.PRODUCT_SEQ}" data-result='${dto.PRODUCT_SUB_INFO}'></td>
											<td>
												<div class="shipping">
													<input type="text" name="s_cost" id="s_cost" value="${dto.PRODUCT_SHIPPING}" numberOnly placeholder="0" />											
													<button id="shippingCost" seq="${dto.PRODUCT_SEQ}" class="btn log">변경</button>
												</div>
											</td>
											<td>
												<c:choose>
													<c:when test="${dto.PRODUCT_STATE eq 'N'}">
														<p class="disabled">비활성화</p>
													</c:when>
													<c:when test="${dto.PRODUCT_STATE eq 'D'}">
														<p class="delete">삭제</p>
													</c:when>
													<c:otherwise>
														<p class="actived">활성화</p>
													</c:otherwise>
												</c:choose>
											</td>
											<td class="change">
												<c:if test="${dto.PRODUCT_STATE ne 'D'}">
													<button id="tb_modify" seq="${dto.PRODUCT_SEQ}" class="btn log">수정</button>
													<button id="tb_del" seq="${dto.PRODUCT_SEQ}" state="${dto.PRODUCT_STATE}" class="btn log">변경</button>
												</c:if>
											</td>
										</tr>
									</c:forEach>
								</c:otherwise>
							</c:choose>
						</tbody>
					</table>
					
					<script type="text/javascript" src="${pageContext.request.contextPath}/js/until/productInfo.js"></script>

					<div class="box-footer clearfix">
						<ul class="pagination pagination-sm no-margin">
							<li>
								<c:choose>
									<c:when test="${pageMaker.prev ne false}">
										<a href="/product/table${pageMaker.makeSearch(pageMaker.startPage - 1)}">&laquo;</a>
									</c:when>
									<c:otherwise>
										<a href="/product/table${pageMaker.makeSearch(pageMaker.startPage)}">&laquo;</a>
									</c:otherwise>
								</c:choose>
							</li>
							<li>
								<c:forEach var="pageNum" begin="${pageMaker.startPage}" end="${pageMaker.endPage}">
									<c:choose>
										<c:when test="${pageNum eq pageMaker.cri.page}">
											<a href="/product/table${pageMaker.makeSearch(pageNum)}">${pageNum}</a>
										</c:when>
										<c:otherwise>
											<a href="/product/table${pageMaker.makeSearch(pageNum)}">${pageNum}</a>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</li>
							<li>
								<c:choose>
									<c:when test="${pageMaker.next ne true && pageMaker.endPage > 0 }">
										<a href="/product/table${pageMaker.makeSearch(pageMaker.endPage)}">&raquo;</a>
									</c:when>
									<c:otherwise>
										<a href="/product/table${pageMaker.makeSearch(pageMaker.endPage + 1)}">&raquo;</a>
									</c:otherwise>
								</c:choose>
							</li>
						</ul>
					</div>
				</div>

				<span class="write">
					<button onClick="location.href='/product/product_Insert'" class="btn log">테이블 생성</button>
				</span>

				<script type="text/javascript" src="${pageContext.request.contextPath}/js/product/fn_ProductInfo.js"></script>
			</div>
		</div>
	</div>
</li>

</s:authorize>

<jsp:include page="../footer.jsp"></jsp:include>