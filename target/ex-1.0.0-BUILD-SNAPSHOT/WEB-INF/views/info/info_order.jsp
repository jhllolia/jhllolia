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
			<div class="status">
				<p class="orderTit">주문/배송상세정보<i class='delivery'><img src='../resources/image/icon/delivery.png' alt='delivery' /></i></p>

				<div class="orderStatus">
					<div class="tit">
						<h4>
							<span class="main">주문 일자</span>
							<p class="txt"><fmt:formatDate value="${order[0].PAID_DATE}" pattern="yyyy.MM.dd"/></p>
						</h4>
					</div>

					<div class="tit">
						<h4>
							<span class="main">주문 번호</span>
							<p class="txt">${order[0].ORDER_NUM}</p>
						</h4>
					</div>
				</div>

				<dl class="call">
					<dt class="tit">주문자 정보</dt>
					<dd class="txt">
						<span class="option">${order[0].BUYER_NAME}</span>
						<span class="option">${order[0].BUYER_TEL}</span>
						<span class="option">${order[0].BUYER_EMAIL}</span>
					</dd>
				</dl>

				<dl class="call">
					<dt class="tit">받으시는 주소</dt>
					<dd class="txt">
						<span class="addr01">[${order[0].BUYER_POSTCODE}]</span>
						<span class="addr02">${order[0].BUYER_ADDR}</span>
					</dd>
				</dl>

				<table id="orderInfo" data-size="${fn:length(order)}">
					<thead>
						<tr>
							<th>
								<label name="check_all" id="check_all">
									<img src="../resources/image/icon/chk_off.png" alt="chk">
									<input type="checkbox" name="status_check_all" name="orderStatus" />
								</label>
							</th>
							<th>주문 정보</th>
							<th>주문 가격 / 수량</th>
							<th>총 주문 가격</th>
						</tr>
					</thead>
					<tbody>
						<c:set var="totalPrice" value="0" />
						<c:set var="stateCount" value="0"/>
						<c:set var="paidCount" value="0"/>
						<c:set var="review" value="0"/>

						<c:forEach items="${order}" var="dto" varStatus="status">
							<c:set var="totalPrice" value="${totalPrice + (dto.PRODUCT_PRICE * dto.PRODUCT_QTY)}" />

							<tr name="order_${status.index}">
								<td>
									<c:choose>
										<c:when test="${dto.PAID_STATUS ne 'cancelled' && dto.PAID_STATUS ne 'shipping_return' && dto.PAID_STATUS ne 'shipping_exchange'}">
											<label name="product_check" id="product_check">
												<img class="status_chk" src="../resources/image/icon/chk_off.png" alt="chk">
												<input type="checkbox" name="status_check" data-check="${dto.PAY_SEQ}" />
											</label>
										</c:when>
									</c:choose>
								</td>
								<td>
									<ul class="product_info">
										<li>
											<img src="../resources/upload/table/${dto.PRODUCT_NUM}/${dto.PRODUCT_PROFILE}" data-img="${dto.PRODUCT_PROFILE}" />
										</li>
										<li>
											<div class="detail">
												<c:choose>
													<c:when test="${dto.PAID_STATUS eq 'paid'}">
														<span id="txt_01">결제완료</span>
													</c:when>
													<c:when test="${dto.PAID_STATUS eq 'cancelled'}">
														<span id="txt_02">결제취소</span>
													</c:when>
													<c:when test="${dto.PAID_STATUS eq 'shipping_waiting'}">
														<span id="txt_01">상품준비중</span>
													</c:when>
													<c:when test="${dto.PAID_STATUS eq 'shipping_delivery'}">
														<span id="txt_01">배송중</span>
													</c:when>
													<c:when test="${dto.PAID_STATUS eq 'shipping_success'}">
														<span id="txt_03">배송완료</span>
													</c:when>
													<c:when test="${dto.PAID_STATUS eq 'shipping_return'}">
														<span id="txt_01">반품요청</span>
													</c:when>
													<c:when test="${dto.PAID_STATUS eq 'shipping_exchange'}">
														<span id="txt_01">교환요청</span>
													</c:when>
												</c:choose>

												<!-- 결제취소 카운트 -->
												<c:if test="${dto.PAID_STATUS eq 'paid'}">
													<c:set var="paidCount" value="${paidCount + 1}"/>
												</c:if>

												<c:if test="${dto.PAID_STATUS ne 'cancelled' && dto.PAID_STATUS ne 'paid' && dto.PAID_STATUS ne 'shipping_return' && dto.PAID_STATUS ne 'shipping_exchange'}">
													<c:set var="stateCount" value="${stateCount + 1}"/>
												</c:if>

												<c:if test="${dto.REVIEW_EXIST eq 1 && dto.PAID_STATUS ne 'cancelled'}">
													<c:set var="review" value="${review + 1}"/>
												</c:if>

												<h4>${dto.PRODUCT_NAME}</h4>
												<p class="option">
													<span class="txt">옵션</span>
													${dto.PRODUCT_OPTION}
												</p>
											</div>
										</li>
									</ul>
								</td>
								<td>
									<fmt:formatNumber type="number" maxFractionDigits="3" value="${dto.PRODUCT_PRICE}" />
									<span class="multiply"> * </span> ${dto.PRODUCT_QTY} 개
								</td>
								<td><fmt:formatNumber type="number" maxFractionDigits="3" value="${dto.PRODUCT_PRICE * dto.PRODUCT_QTY}" /> 원</td>
							</tr>
						</c:forEach>

						<tr>
							<td colspan="2" class="result_pay">
								<span class="tit">배송비</span>
								<span class="pay"><fmt:formatNumber type="number" maxFractionDigits="3" value="${order[0].PRODUCT_SHIPPING_COST}" /> 원</span>
							</td>
							<td colspan="2" class="result_pay">
								<span class="tit">총액</span>
								<span class="pay"><fmt:formatNumber type="number" maxFractionDigits="3" value="${totalPrice + order[0].PRODUCT_SHIPPING_COST}" /> 원</span>
							</td>
						</tr>
					</tbody>
				</table>

				<div class="orderResult" data-seq="${paidCount}" data-until="${stateCount}" data-view="${review}">
					<div class="tip_policy">
						<ul>
							<c:if test="${paidCount > 0}">
								<c:choose>
									<c:when test="${stateCount eq 0}">
										<li name="tip">결제 상태가 변경되면 결제취소를 진행하실수 없습니다. <span class="ti">상품 문의</span> / <span class="ti">배송 문의</span>는 제품문의 탭에서 작성하실 수 있습니다.</li>
										<li name="tip">주문 이후 배송 1~3일정도의 시간이 소요됩니다.</li>
									</c:when>
									<c:otherwise>

									</c:otherwise>
								</c:choose>
							</c:if>

							<c:if test="${stateCount > 0}">
								<li name="tip"><span class="ti">반품</span> / <span class="ti">교환</span>은 상품정책에 따라서 제한이 될수 있습니다.</li>
								<li name="tip">구매자 사정으로 반품이나 교환을 할 경우 왕복 배송료 5,000원은 구매자 부담이며, 상품에 이상이 있거나 잘못 배송이 된 경우에는 왕복 배송료를 어양토속식품에서 부담합니다.</li>
								<li name="tip">제품이 훼손 되거나, 고객님의 부주의로 훼손 또는 파손된 제품은 반품이 불가합니다.</li>
							</c:if>

							<li name="tip">배송관련 된 문의는 제품문의 / E-mail 고객센터를 이용해주시면 신속하게 답변을 얻을 수 있습니다</li>
						</ul>
					</div>

					<c:if test="${paidCount > 0}">
						<c:choose>
							<c:when test="${stateCount eq 0}">
								<div class="action"><button name="payCancel" class="btn log">결제취소</button></div>
							</c:when>
						</c:choose>
					</c:if>

					<c:if test="${stateCount > 0}">
						<div class="action"><button name="returnProduct" class="btn log">반품 / 교환신청</button></div>
					</c:if>

					<div class="action"><button name="productQnA" data-seq="${order[0].PRODUCT_NUM}" class="btn log">제품문의</button></div>

					<c:if test="${order[0].PRODUCT_SHIPPING_NUM ne null && order[0].PRODUCT_SHIPPING_COURIER ne null}">
						<div class="action"><button class="btn log" onClick="window.open('https://tracker.delivery/#/${order[0].PRODUCT_SHIPPING_NUM}/${order[0].PRODUCT_SHIPPING_COURIER}')">배송조회</button></div>
					</c:if>

					<c:if test="${stateCount ne review}">
						<div class="action">
							<button name="reviewSend" data-num="${order[0].ORDER_NUM}" class="btn log">상품평작성</button>
							<div class="res_ok">
								<span>고객님의 리뷰가<br />다른 고객들에게 도움이 될 수 있어요!</span>
							</div>
						</div>
					</c:if>
				</div>
			</div>

			<script type="text/javascript" src="${pageContext.request.contextPath}/js/product/fn_OrderInfo.js"></script>
			<script type="text/javascript" src="${pageContext.request.contextPath}/js/product/fn_QnA.js"></script>
		</div>
	</c:otherwise>
</c:choose>

<jsp:include page="./info_footer.jsp"></jsp:include>