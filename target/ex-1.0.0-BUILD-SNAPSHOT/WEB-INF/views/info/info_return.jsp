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

		<div id="search" class="search">
			<div class="status " name="wip_loading">
				<p class="orderTit">
					반품 / 교환신청<i class='delivery'><img src='../resources/image/icon/delivery.png' alt='delivery' /></i>
				</p>

				<table id="orderReturn">
					<tbody>
						<c:forEach items="${list}" var="dto" varStatus="status">
							<tr>
								<td class="tab_01">
									<div class="the">
										<a href="../tab/${dto.PRODUCT_NUM}" class="profile">
											<span class="profile_img">
												<img src="../resources/upload/table/${dto.PRODUCT_NUM}/${dto.PRODUCT_PROFILE}" alt="" />											
											</span>
										</a>
									</div>
								</td>
								<td class="tab_02">
									<div class="product_unit">
										<p class="unit"><span>주문번호</span>${dto.ORDER_NUM}</p>
										<p class="unit"><span>구매제품</span>${dto.PRODUCT_NAME}</p>
										<p class="unit"><span>구매수량</span>${dto.PRODUCT_QTY} <span>개</span></p>
									</div>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>

				<div class="returnReason">
					<table id="returnWhy">
						<tbody>
							<tr>
								<th>유형</th>
								<td>
									<div class="check_label">
										<label name="question_select" class="qSelect">
											<input type="radio" name="boxId" value="shipping_return" />
											<span class="txt">반품신청</span>
										</label>
										<label name="question_select" class="qSelect">
											<input type="radio" name="boxId" value="shipping_exchange" />
											<span class="txt">교환신청</span>
										</label>
									</div>
								</td>
							</tr>
							<tr id="checkSelect">
								<th>사유</th>
								<td>
									<select name="reason_select" id="reason_select">
										<option value="" selected="selected">→ 선택하세요</option>
										<option value="A">고객변심</option>
										<option value="B">배송지연</option>
										<option value="C">배송오류</option>
										<option value="D">배송불가지역</option>
										<option value="E">포장불량</option>
										<option value="F">상품 불만족</option>
										<option value="G">상품 정보상이</option>
										<option value="H">상품 불량</option>
										<option value="I">서비스 불만족</option>
										<option value="J">품절</option>
									</select>
								</td>
							</tr>
							<tr>
								<th>상세사유</th>
								<td>
									<div class="why_until">
										<textarea name="whyText" id="whyText"></textarea>
									</div>
								</td>
							</tr>
						</tbody>
					</table>

					<div class="sendReturn">
						<input type="hidden" name="keyword" value="${keyword}" />
						<button name="returnData" id="returnData" class="btn log">선택사항 신청</button>
						<button name="prevPage" id="prevPage" onClick="history.go(-1)" class="btn log">이전페이지</button>
					</div>
				</div>
			</div>

			<script type="text/javascript" src="${pageContext.request.contextPath}/js/product/fn_OrderInfo.js"></script>
		</div>
	</c:otherwise>
</c:choose>

<jsp:include page="./info_footer.jsp"></jsp:include>