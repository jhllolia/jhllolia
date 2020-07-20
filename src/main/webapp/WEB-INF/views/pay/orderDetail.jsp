<%@ page import="org.springframework.security.core.Authentication" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="s" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="../header.jsp"></jsp:include>

<div class="pay_wrap">
	<div class="wrap-loading display-none"></div>
	<div class="main">
		<c:choose>
			<c:when test="${empty sessionScope.member_Id}">
				<div class="payUser">
					<h4>사용자 정보</h4>
					<p class="txt">사용자 정보가 존재하질 않습니다.</p>
				</div>
			</c:when>
			<c:otherwise>
				<div class="payUser">
					<div class="order" data-seq="${sessionScope.member_Seq}">
						<h4 class="tit">
							<img src="../resources/image/menu/product_off.png" alt="" />
							<span class="txt">주문 정보</span>
						</h4>

						<div id="result">
							<table id='option'>
								<thead>
									<tr>
										<th>상품</th>
										<th>옵션</th>
										<th>수량</th>
										<th>합계</th>
									</tr>
								</thead>
								<tbody>
									<c:set var="totalPrice" value="0" />

									<c:forEach items="${ARR}" var="dto" varStatus="status">
										<c:set var="totalPrice" value="${totalPrice + (dto.price * dto.qty)}"/>

										<tr>
											<td><div class='profile' style="background-image: url('../resources/upload/table/${dto.seq}/${dto.profile}');"></div></td>
											<td>
												<p name='dataIndex' data-count='${dto.num}'>
													<span>${dto.name}</span>
													<span>개 당 <fmt:formatNumber value="${dto.price}" pattern="#,###"/> 원</span>
												</p>
											</td>
											<td><div id='totalCount'>${dto.qty} 개</div></td>
											<td>
												<div><fmt:formatNumber value="${dto.price * dto.qty}" pattern="#,###"/> 원</div>
											</td>
										</tr>
									</c:forEach>

									<c:if test="${totalPrice <= LIMIT_COST}">
										<c:set var="totalPrice" value="${totalPrice - COST}"/>
										<c:set var="COST" value="0"/>
									</c:if>

									<tr class='totalAll'>
										<td colspan='2' class='all'>
											<div class='orderTotal'>
												<span class='txt'>상품 가격</span>
												<span id='add1'><fmt:formatNumber value="${totalPrice}" pattern="#,###"/></span>
												<span class='add'> + </span>
												<span class='txt'>배송비</span>
												<span id='add2'><fmt:formatNumber value="${COST}" pattern="#,###"/></span>
											</div>
										</td>
										<td colspan='2' class='all'>
											<div id='re' class='orderTotal'>
												<span class='txt'>총액</span><span id='txtResult' data-Seq='${SEQ}'><fmt:formatNumber value="${totalPrice + COST}" pattern="#,###"/> 원</span>
											</div>
										</td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>

					<div class="tip_policy">
						<ul>
							<li name="tip">제품을 <span class="ti">30,000원</span> 이상 구매시 배송비는 <span class="ti">무료</span>입니다.</li>
							<li name="tip">반품/교환시 제품 특성상 반품과 교환이 불가능한 상품이 있을수도 있습니다</li>
							<li name="tip">취소/환불 요청시 고객변심이나 제품이 심하게 훼손된다면 취소/환불이 <span class="ti">불가능</span>합니다.</li>
							<li name="tip">일부 제품은 생산라인 특성상 시간이 소요 될수도 있습니다.</li>
							<li name="tip">배송은 결제일로부터 1~3일 정도 소요됩니다.</li>
						</ul>
					</div>

					<div class="method">
						<h4 class="tit">
							<img src="../resources/image/icon/m_truck.png" alt="" />
							<span class="txt">배송지 정보</span>
						</h4>

						<ul>
							<li>
								<div class="orderTxt">
									<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
									<script type="text/javascript" src="${pageContext.request.contextPath}/js/until/daumPost.js"></script>

									<table>
										<tbody>
											<tr>
												<th class="row">이름</th>
												<td><input type="text" name="pay_user_Nm" value="${user.member_Name}" /></td>
											</tr>
											<tr>
												<th class="row">우편번호</th>
												<td>
													<p class="txt"><input type="text" id="zip" value="${user.member_Zip}" /><input type="button" id="btnAddr" value="검색" /></p>
													<p class="txt"><input type="text" id="addr1" placeholder="주소" value="${user.member_Addr1}" readonly="readonly" /></p>

													<div id="process_layer" class="process_layer">
														<span id="layer_close">닫기</span>
													</div>
												</td>
											</tr>
											<tr>
												<th class="row">상세주소</th>
												<td><input type="text" id="addr2" value="${user.member_Addr2}" /></td>
											</tr>
											<tr>
												<th class="row">휴대전화</th>
												<td><input type="text" name="pay_user_Ph" id="phone" value="${user.member_Phone}" /></td>
											</tr>
											<tr>
												<th class="row">이메일</th>
												<td>
													<input type="text" name="pay_user_Em" value="${user.member_Id}" />
													<dd>제품 구입시 이메일을 통해 주문처리과정을 보내 드립니다.</dd>
												</td>
											</tr>
											<tr>
												<th class="row">Message</th>
												<td>
													<select id="pay_memo" name="pay_memo" >
														<option value="">배송기사에게 전달되는 메세지 입니다. 선택하여 주세요.</option>
														<option value="A">부재시, 경비(관리)실에 맡겨주세요.</option>
														<option value="B">부재시, 문앞에 놓아주세요.</option>
														<option value="C">직접 받겠습니다.</option>
														<option value="D">배송전에 연락주세요</option>
													</select>
												</td>
											</tr>
										</tbody>
									</table>
								</div>
							</li>
							<li>
								<ul>
									<li name="pay">
										<label name="payChk">
											<input type="radio" class="input-radio" name="pay_method" pg="html5_inicis" method="card" />
											<img src="../resources/image/icon/chk_off.png" alt="payment_01">
											<h4>신용카드 결제</h4>
										</label>
										<div class="beforeTxt">
											<p class="txt">결제 버튼을 클릭하시면 신용카드 결제창이 나타나 결제를 진행하실 수 있습니다.</p>
										</div>
									</li>
									<li name="pay">
										<label name="payChk">
											<input type="radio" class="input-radio" name="pay_method" pg="html5_inicis" method="trans" />
											<img src="../resources/image/icon/chk_off.png" alt="payment_02">
											<h4>실시간계좌이체 결제</h4>
										</label>
										<div class="beforeTxt">
											<p class="txt">결제 버튼을 클릭하시면 실시간계좌이체 결제창이 나타나 결제를 진행하실 수 있습니다.</p>
										</div>
									</li>
									<!--

									<li name="pay">
										<label name="payChk">
											<input type="radio" class="input-radio" name="pay_method" pg="kakaopay" method="card" />
											<img src="../resources/image/icon/chk_off.png" alt="payment_03">
											<h4>카카오 페이</h4>
										</label>
										<div class="beforeTxt">
											<p class="txt">결제 버튼을 클릭하시면 카카오페이 결제창이 나타나 결제를 진행하실 수 있습니다.</p>
										</div>
									</li>

									-->
								</ul>

								<div class="send">
									<ul>
										<li><button id="sendPay" name="btnPay" class="btn log">결제</button></li>
										<li><button id="payClose" name="btnPay" class="btn log">주문 취소</button></li>
									</ul>
								</div>

								<div class="tip">
									<ul>
										<li></li>
										<li></li>
									</ul>
								</div>
							</li>
						</ul>
					</div>
				</div>
			</c:otherwise>
		</c:choose>

		<script type="text/javascript" src="https://cdn.iamport.kr/js/iamport.payment-1.1.5.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/product/fn_Payment.js"></script>
	</div>
</div>

<jsp:include page="../footer.jsp"></jsp:include>