<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<jsp:include page="../header.jsp" />

<div class="main">
	<div class="main_tit">
		<div class="txt">
			<h2>SALE PRODUCT</h2>
		</div>
	</div>

	<div id="sale_items" class="product_container">
		<ul>
			<c:choose>
				<c:when test="${fn:length(list) == 0}">

				</c:when>
				<c:otherwise>
					<c:forEach items="${list}" var="dto" varStatus="status">
						<fmt:parseNumber var="SEQ" integerOnly="true" value="${dto.PRODUCT_SEQ}" />
						<fmt:parseNumber var="SELL_COUNT" integerOnly="true" value="${dto.PRODUCT_UPLOAD}" />
						<fmt:parseNumber var="REVIEW_AVG" integerOnly="true" value="${dto.REVIEW_AVG}" />
						<fmt:parseNumber var="REVIEW_COUNT" integerOnly="true" value="${dto.REVIEW_COUNT}" />
	
						<li data-view="list_${status.index}">
							<div class="product" data-unit="${SEQ}">
								<c:choose>
									<c:when test="${SELL_COUNT ne 0}">
										<c:if test="${dto.PRODUCT_SALE_MODE eq 'SLEEVE'}"><span class="positive">판매상품</span></c:if>
										<c:if test="${dto.PRODUCT_SALE_MODE eq 'WHOLE'}"><span class="whole">도매전용</span></c:if>
									</c:when>
									<c:otherwise>
										<span class="negative">품절</span>
									</c:otherwise>
								</c:choose>

								<div data-img="img_prev" file-num="${dto.PRODUCT_MIN_TIT}" style='background-image:url("../resources/upload/table/${SEQ}/${dto.PRODUCT_MIN_IMG}")'></div>
								<div data-img="img_next" file-num="${dto.PRODUCT_MAX_TIT}" style='background-image:url("../resources/upload/table/${SEQ}/${dto.PRODUCT_MAX_IMG}")'></div>
							</div>
	
							<div class="unit_content">
								<h4 class="tit">${dto.PRODUCT_TITLE}</h4>
								<div class="unit">
									<div class="star">
										<c:if test="${REVIEW_AVG > 0}">
											<p class="star_before"><c:forEach begin="0" end="${REVIEW_AVG - 1}">★</c:forEach></p>
										</c:if>
										<p class="star_after">★★★★★</p>
										<span class="count">${REVIEW_COUNT}</span>
									</div>
	
									<div class="proct_info">
										<c:choose>
											<c:when test="${SELL_COUNT ne 0}">
												<c:if test="${dto.PRODUCT_SALE_MODE eq 'SLEEVE'}">
													<span class="price"><fmt:formatNumber type="number" maxFractionDigits="3" value="${dto.PRODUCT_SUB_INFO[0]['PRICE']}" /></span>
												</c:if>
												<c:if test="${dto.PRODUCT_SALE_MODE eq 'WHOLE'}">
													<span class="who"></span>
												</c:if>
												<span id="open" class="open">
													<img src="../resources/image/menu/folder_off.png" alt="folder_off" />
												</span>

												<div name="list_goods" class="list_goods">
													<div class="ls">
														<c:forEach items="${dto.PRODUCT_SUB_INFO}" var="unit" varStatus="status">
															<c:if test="${unit.SELL eq 'Y'}">
																<p class="good_${status.index}">
																	<span>${unit.NAME}</span>
																	<span>${unit.OPTION}</span>
																	<c:if test="${dto.PRODUCT_SALE_MODE eq 'SLEEVE'}">
																		<span><fmt:formatNumber type="number" maxFractionDigits="3" value="${unit.PRICE}" /> 원</span>
																	</c:if>
																</p>
															</c:if>
														</c:forEach>
													</div>
												</div>
											</c:when>
											<c:otherwise>
												<p class="none">품절된 상품입니다.</p>
											</c:otherwise>
										</c:choose>
									</div>
								</div>
							</div>
						</li>
					</c:forEach>
				</c:otherwise>			
			</c:choose>
		</ul>
	</div>

	<script type="text/javascript" src="${pageContext.request.contextPath}/js/until/fn_list.js"></script>
</div>

<jsp:include page="../footer.jsp" />