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
			<div class="cart_wrap">
				<p class="tit">장바구니<i class='cart_i'><img src='../resources/image/icon/cart.png' alt='cart' /></i></p>

				<ul class="cart">
					<li>
						<table class="cart_list"> 
							<thead>
								<tr>
									<th colspan="6">
										<div class="unit_check">
											<label id="product_all" class="cart_product">
												<img src="../resources/image/icon/chk_off.png" alt="all" />
												<input type="checkbox" name="unit_all" id="unit_all" />
												<span class="txt">전체선택</span>
											</label>
										</div>

										<!-- 
	
										<div class="unit_check">
											<label id="delete_product" class="cart_product">
												<img src="../resources/image/icon/del.png" alt="all" />
												<span class="txt">선택삭제</span>
											</label>
										</div>
										
										-->
									</th>
								</tr>
							</thead>
							<tbody>
								<c:choose>
									<c:when test="${fn:length(list) eq ''}">
										<tr>
											<td colspan="6" class="none">
												<div class="cart_none">
													<img src="../resources/image/icon/shipping_cart.png" alt="cart" />
													<p class="txt">장바구니에 담긴 상품이 없습니다.</p>
												</div>
											</td>
										</tr>
									</c:when>
									<c:otherwise>
										<c:set var="CART_COUNT" value="0"/>

										<c:forEach items="${list}" var="dto" varStatus="status">
											<fmt:parseNumber var="CART_ORDER" integerOnly="true" value="${dto.CART_ORDER}" />
											<c:set var="CART_COUNT" value="${CART_COUNT + 1}"/>

											<tr name="${dto.CART_PRODUCT_NUM}">
												<td class="check">
													<label name="product_check" id="product_check">
														<input type="checkbox" name="status_check" value="${CART_ORDER}">
													</label>
												</td>
												<td class="profile">
													<img src="../resources/upload/table/${CART_ORDER}/${dto.PRODUCT_FILE_NAME}" alt="${dto.PRODUCT_FILE_NAME}" />
												</td>
												<td class="name">${dto.CART_PRODUCT_NAME}</td>
												<td class="option">${dto.CART_PRODUCT_OPTION}</td>
												<td class="qty">
													<div class="click_price">
														<span id="price" class="price"><fmt:formatNumber type="number" maxFractionDigits="3" value="${dto.CART_PRODUCT_PRICE}" /></span>
														<span class="unit">원</span>
													</div>

													<div class="quantity buttons_added">
														<span class="subtract-item">-</span>
														<span class="item-count">
															<input type="text" id="qty" class="qty" name="qty" data-num="${CART_ORDER}" value="${dto.CART_PRODUCT_QTY}" readonly="readonly">
														</span>
														<span class="add-item">+</span>
													</div>
												</td>
												<td class="cart_state">
													<div class="unit">
														<button id="cart_other" name="cart_other" data-num="${dto.CART_PRODUCT_NUM}" class="btn log">
															<img src="../resources/image/icon/box.png" alt="" />
															<span class="txt">수량 변경</span>
														</button>

														<button id="cart_del" name="cart_del" data-num="${dto.CART_SEQ}" class="btn log">
															<img src="../resources/image/icon/del.png" alt="" />
															<span class="txt">제품 삭제</span>
														</button>
													</div>
												</td>
											</tr> 
										</c:forEach>
									</c:otherwise>
								</c:choose>
							</tbody>
						</table>
					</li>
					<li>
						<div class="info">
							<p class="save">
								<img src="../resources/image/icon/m_truck.png" alt="" />
								<span class="txt">저장된 배송지</span>
							</p>

							<p class="save_lct">
								<span class="zip">${info.member_Zip}</span>
								<span class="ad1">${info.member_Addr1}</span>
								<span class="ad2">${info.member_Addr2}</span>
								<button name="link_lct" id="link_lct" class="btn log">배송지 변경</button>
							</p>

							<p class="save">
								<img src="../resources/image/icon/return_product.png" alt="" />
								<span class="txt">선택상품</span>
								<span class="cart_count">${CART_COUNT}</span>
							</p>

							<div class="result_list">
								<div class="de">
									<span class="de_txt order">주문금액</span>
									<span id="order_price" class="price"></span>
								</div>

								<div class="de">
									<span class="de_txt order">배송비</span>
									<span id="delivery_price" class="price"></span>
								</div>

								<div class="de result">
									<span class="de_txt final">결제금액</span>
									<span id="total_price" class="price"></span>
								</div>
							</div>

							<c:choose>
								<c:when test="${fn:length(list) eq ''}">

								</c:when>
								<c:otherwise>
									<div class="sendData">
										<button name="send_Cart" id="send_Cart" class="btn log">
											<img src="../resources/image/icon/cart_send.png" alt="" />
											<span class="txt">주문하기</span>
										</button>
									</div>
								</c:otherwise>
							</c:choose>
						</div>
					</li>
				</ul>
			</div>

			<script type="text/javascript" src="${pageContext.request.contextPath}/js/product/fn_Cart.js"></script>
		</div>
	</c:otherwise>
</c:choose>

<jsp:include page="./info_footer.jsp"></jsp:include>