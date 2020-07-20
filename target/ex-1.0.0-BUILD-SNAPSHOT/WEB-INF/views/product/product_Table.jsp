<%@ page import="org.springframework.security.core.Authentication" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="s" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="../header.jsp"></jsp:include>

<div class="tab_wrap">
	<div class="wrap-loading display-none"></div>
	<input type="hidden" name="order_sch" value="${seat.PRODUCT_SEQ}" />

	<c:choose>
		<c:when test="${seat.PRODUCT_STATE eq 'D' || seat.PRODUCT_STATE eq 'N' }">
			<div class="tab_disable">
				<div class="state">
					<img src="../resources/images/icon/refund.png" alt="" />
					<h4>TABLE ERROR</h4>
					<p class="txt">테이블 정보가 존재하질 않습니다.</p>
					<button name="back" class="btn log" onClick='location.href="../seat/list"'>Table List 바로가기</button>
				</div>
			</div>
		</c:when>
		<c:otherwise>
			<div class="tab_container">
				<div class="rerve_main">
					<div class="slide">
						<a href="#" id="prev" class="slideMove left carousel-control"><span class="glyphicon glyphicon-chevron-left"></span></a>
						<a href="#" id="next" class="slideMove right carousel-control"><span class="glyphicon glyphicon-chevron-right"></span></a>

						<div id="top_slider" class="top">
							<c:choose>
								<c:when test="${fn:length(file) eq 0}">

								</c:when>
								<c:otherwise>
									<c:forEach items="${file}" var="dto" varStatus="status">
										<c:if test="${fn:contains(dto.PRODUCT_FILE_TITLE, 'file_')}">
											<div seq="${status.index}" id="top_${status.index}" data-single="${dto.PRODUCT_FILE_NAME}" style='background-image:url("../resources/upload/table/${dto.PRODUCT_SEQ}/${dto.PRODUCT_FILE_NAME}")'></div>
										</c:if>
									</c:forEach>
								</c:otherwise>
							</c:choose>
						</div>

						<div id="top_Slider_btn" class="sub">
							<ul id="ul_btns">
								<c:choose>
									<c:when test="${seat.PRODUCT_STATE eq 'D'}">	

									</c:when>
									<c:otherwise>
										<c:choose>
											<c:when test="${fn:length(file) eq 0}">
												<li id="btn_0"><dd></dd></li>
											</c:when>
											<c:otherwise>
												<c:forEach items="${file}" var="dto" varStatus="status">
													<c:if test="${fn:contains(dto.PRODUCT_FILE_TITLE, 'file_')}">
														<li id="btn_${status.index}"><dd></dd></li>
													</c:if>
												</c:forEach>
											</c:otherwise>
										</c:choose>
									</c:otherwise>
								</c:choose>
							</ul>
						</div>
					</div>

					<div class="info_txt" data-add='${seat.PRODUCT_SHIPPING}'>
						<div class="naming">
							<h4>${seat.PRODUCT_TITLE}</h4>

							<div class="avg">
								<c:choose>
									<c:when test="${REVIEW_AVG > 0}">
										<div class="avg_be"><c:forEach begin="0" end="${REVIEW_AVG - 1}">★</c:forEach></div>
										<div class="avg_af">★★★★★</div>
										<span class="avg_cnt">(${REVIEW_COUNT})</span>
									</c:when>
								</c:choose>
							</div>

							<c:if test="${seat.PRODUCT_SALE_MODE eq 'SLEEVE'}">
								<div class="price">
									<fmt:formatNumber type="number" maxFractionDigits="3" value="${myPushList[0].price}" var="coPrice" />
									<span id="mTxt">${coPrice} 원</span>
								</div>
							</c:if>

							<div class="txt">
								<p>${seat.PRODUCT_INFO}</p>			
							</div>
						</div>

						<!-- info_txt -->
						<c:choose>
							<c:when test="${seat.PRODUCT_SALE_MODE eq 'SLEEVE'}">
								<div class="billing">
									<c:choose>
										<c:when test="${fn:length(myPushList) == 0}">

										</c:when>
										<c:otherwise>
											<div class="result">
												<ul>
													<li price="${myPushList[0].price}" sale="${myPushList[0].sale}">
														<select id="addProduct" name="addProduct">
															<option value="">제품을 선택 해주세요</option>

															<c:forEach items="${myPushList}" var="dto" varStatus="status">
																<c:set var="price" value="${dto.price}" />
																<c:set var="sale" value="${dto.sale}" />
			
																<c:if test="${dto.sell eq 'Y'}">
																	<c:choose>
																		<c:when test="${sale eq 0 }">
																			<option data-state="${dto.sell}" seq="${status.index}" sale="${sale}" price="${price}" value="${dto.option}">${dto.name}</option>
																		</c:when>
																		<c:otherwise>
																			<option data-state="${dto.sell}" seq="${status.index}" sale="${sale}" price="${price * (1 - sale / 100)}" value="${dto.option}">${dto.name} (${dto.sale} %)</option>
																		</c:otherwise>
																	</c:choose>
																</c:if>
															</c:forEach>
														</select>
													</li>
												</ul>
											</div>
										</c:otherwise>
									</c:choose>
			
									<div class="p_result">
										<ul></ul>
									</div>

									<div class="p_total">
										<dt class="wrap">총 상품 금액 <span id="total_price"></span></dt>
									</div>

									<div class="sendData">
										<ul>
											<li>
												<button id="sendBasket" name="send" class="btn log">장바구니</button>
												<div class="cartAdd">
													<div class="link">
														<p id="cart_result"></p>
			
														<div class="send">
															<p id="shopping" class="go btn log">쇼핑 계속하기</p>
															<p id="cart" class="go btn log" onClick="location.href='../info/cart'">장바구니가기</p>
														</div>
													</div>
												</div>
											</li>
											<li><button id="sendData" name="send" seq="${seat.PRODUCT_SEQ}" class="btn log">바로 구매</button></li>
										</ul>
									</div>
								</div>
							</c:when>
							<c:otherwise>
								<div class="whl_sale">
									<p class="tit">
										<img src="../resources/image/menu/product_off.png" alt="" />
										<span class="txt">판매제품</span>
									</p>

									<table id="sale_list">
										<thead>
											<tr>
												<th>판매제품</th>
												<th>옵션</th>
												<th>상태</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${myPushList}" var="dto" varStatus="status">
												<tr>
													<td>${dto.name}</td>
													<td>${dto.option}</td>
													<td>
														<div class="unit_status">
															<c:choose>
																<c:when test="${dto.sell eq 'Y'}">
																	<span class="yes">판매</span>
																</c:when>
																<c:otherwise>
																	<span class="no">품절</span>
																</c:otherwise>
															</c:choose>
														</div>
													</td>
												</tr>
											</c:forEach>
										</tbody>
									</table>

									<div class="sale_role">
										<ul>
											<li>소매가와 도매가가 다소 차이 날 수 있습니다.</li>
											<li>해당 상품을 구매하고 싶으신 고객님께서는 E-mail 고객센터에서 요청하시면 가능합니다.</li>
											<li>제품샘플은 금액과 배송비가 부과되며, 요청시 내용에 사업자등록번호 / 핸드폰 번호를 포함시켜주세요.</li>
										</ul>
									</div>

									<div class="cus_service">
										<button name="customer" class="btn log" onClick="location.href='../terms/counsel'">
											<img src="../resources/image/main/support.png" alt="" />
											<span class="txt">E-mail 고객센터 바로가기</span>
										</button>
									</div>
								</div>
							</c:otherwise>							
						</c:choose>
					</div>
				</div>

				<div class="s_content">
					<% pageContext.setAttribute("newLine", "/n"); %>
					<div class="subMenu">
						<ul id="content_menu" class="content_menu">
							<li><a data-target="target_1" class="clickable">상품상세정보</a></li>
							<li><a data-target="target_2" class="clickable">리뷰<span>(${REVIEW_COUNT})</span></a></li>
							<li><a data-target="target_3" class="clickable">상품 Q&A</a></li>
							<li><a data-target="target_4" class="clickable">배송/반품/교환 안내</a></li>
						</ul>
					</div>

					<div id="target_1" class="txt">
						<div class="tit">
							<h4>상품상세정보</h4>
						</div>

						<p class="post">${fn:replace(seat.PRODUCT_CONTENT, newLine, "<br />")}</p>
					</div>

					<div id="target_2" class="txt">
						<div class="tit">
							<h4>고객상품평 <span id="in_num"></span></h4>
						</div>

						<div class="review_list">
							<p class="expli">고객 상품평은 주문관리 < 결제 상태에서 결제확인후 작성하실수 있습니다</p>

							<!-- 

							<div class="star_avr">
								<div class="area">
									<strong>구매고객 총별점</strong>
								</div>
								<div class="area"></div>
							</div>

							-->

							<table id="list" class="paginated">
								<tbody id="review"></tbody>
							</table>
						</div>
					</div>

					<div id="target_3" class="txt">
						<div class="tit">
							<h4>상품 Q&A</h4>
						</div>

						<p class="post">
							상품 / 배송 / 반품 / 환불에 대한 문의는 E-mail 고객센터을 이용해 주세요.
							<button name="user_qna" data-seq="${sessionScope.member_Id}" class="btn log">상품 문의</button>
							<button name="user_mail" class="btn log" onClick="location.href='../terms/counsel'">E-mail 고객센터</button>
						</p>
					</div>

					<div id="target_4" class="txt">
						<div class="tit">
							<h4>배송정보</h4>
						</div>

						<p class="post">${fn:replace(seat.PRODUCT_SHIPPING_TXT, newLine, "<br />")}</p>
					</div>

					<div class="txt">
						<div class="tit">
							<h4>반품 및 교환정보</h4>
						</div>

						<p class="post">${fn:replace(seat.PRODUCT_RETURN_TXT, newLine, "<br />")}</p>
					</div>
				</div>
			</div>
		</c:otherwise>
	</c:choose>

	<script type="text/javascript" src="${pageContext.request.contextPath}/js/product/fn_Product.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/product/fn_ReviewOutput.js"></script>
</div>

<jsp:include page="../footer.jsp"></jsp:include>