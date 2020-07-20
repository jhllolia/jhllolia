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

<jsp:include page="./admin.jsp" />

<li id="menu_result">
	<div class="adMain">
		<div id="search" class="search">
			<div class="status">
				<ul class="user_check">
					<li>
						<p># 결제 상태 출력</p>
						<label id="pay_all">
							<img class="pay_chk" src="../resources/image/icon/chk_on.png" alt="chk" />
							<input type="checkbox" name="th_checkAll" id="th_checkAll">
							<span>전체</span>
						</label>

						<label id="pay_chk">
							<img class="pay_chk" src="../resources/image/icon/chk_on.png" alt="chk" />
							<input type="checkbox" name="confirm" id="checkbox_id" value="'paid'">
							<span>결제완료</span>
						</label>

						<label id="pay_chk">
							<img class="pay_chk" src="../resources/image/icon/chk_on.png" alt="chk" />
							<input type="checkbox" name="confirm" id="checkbox_id" value="'cancelled'">
							<span>결제취소</span>
						</label>

						<label id="pay_chk">
							<img class="pay_chk" src="../resources/image/icon/chk_on.png" alt="chk" />
							<input type="checkbox" name="confirm" id="checkbox_id" value="'shipping_waiting'">
							<span>상품준비중</span>
						</label>

						<label id="pay_chk">
							<img class="pay_chk" src="../resources/image/icon/chk_on.png" alt="chk" />
							<input type="checkbox" name="confirm" id="checkbox_id" value="'shipping_delivery'">
							<span>배송중</span>
						</label>

						<label id="pay_chk">
							<img class="pay_chk" src="../resources/image/icon/chk_on.png" alt="chk" />
							<input type="checkbox" name="confirm" id="checkbox_id" value="'shipping_success'">
							<span>배송완료</span>
						</label>

						<label id="pay_chk">
							<img class="pay_chk" src="../resources/image/icon/chk_on.png" alt="chk" />
							<input type="checkbox" name="confirm" id="checkbox_id" value="'shipping_return'">
							<span>반품</span>
						</label>

						<label id="pay_chk">
							<img class="pay_chk" src="../resources/image/icon/chk_on.png" alt="chk" />
							<input type="checkbox" name="confirm" id="checkbox_id" value="'shipping_exchange'">
							<span>교환</span>
						</label>
					</li>

					<li id="time">
						<p># 조회 기간</p>
						<ul class="dataCheck">
							<li><input type="text" name="firstDate" class="dateCheck" /><i class="icon"><img src='../resources/image/icon/calendar.png' alt='' /></i></li>
							<li><input type="text" name="lastDate" class="dateCheck" /><i class="icon"><img src='../resources/image/icon/calendar.png' alt='' /></i></li>
						</ul>
					</li>

					<li id="rotate">
						<p># 예약조회</p>
						<select name="searchOption" id="board-search" class="board-search">
							<option value="n" <c:out value="${param.searchType == null ? 'selected' : ''}"/>>:::: 선택 ::::</option>
							<option value="t" <c:out value="${param.searchType eq 't' ? 'selected' : ''}"/>>주문번호</option>
							<option value="c" <c:out value="${param.searchType eq 'c' ? 'selected' : ''}"/>>제품 명</option>
							<option value="tx" <c:out value="${param.searchType eq 'tx' ? 'selected' : ''}"/>>이메일</option>
							<option value="tc" <c:out value="${param.searchType eq 'tc' ? 'selected' : ''}"/>>휴대폰 번호</option>
						</select>

						<input type="text" id="payment_txt" name="payment_txt" class="form-control btn log" />
						<input type="button" id="payment_search" name="payment_search" class="btn log" value="조회" />
						<input type="button" id="payment_excel" name="payment_excel" class="btn log" value="엑셀 출력" />
						<a href="#" class="rotate btn log">
							<img src="../resources/image/icon/rotate.png" alt="rotate" />
						</a>
					</li>
				</ul>
			</div>

			<div class="resultAd">
				<ul class="adTotal">
					<li class="price">
						<i class="icon"><img src='../resources/image/icon/bill.png' alt='' /></i>
						<h4>총 결제 금액</h4>
						<p id="totalPrice" class="result"></p>
					</li>
					<li class="qty">
						<i class="icon"><img src='../resources/image/icon/box.png' alt='' /></i>
						<h4>총 판매 개수</h4>
						<p id="totalQty" class="result"></p>
					</li>
					<li class="request">
						<i class="icon"><img src='../resources/image/icon/return_product.png' alt='' /></i>
						<h4>반품 / 환불 요구</h4>
						<p id="requestPerson" class="result"></p>
					</li>
				</ul>
			</div>

			<div class="list">
				<span id="status_check">
					<select id="ad_check" name="ad_check">
						<option value="shipping_waiting">상품준비중</option>
						<option value="shipping_delivery">배송중</option>
						<option value="shipping_success">배송완료</option>
						<option value="shipping_return">반품</option>
						<option value="shipping_exchange">교환</option>
						<option value="paid">결제</option>
					</select>

					<button name="status_change" id="status_change" class="btn log">수정</button>
				</span>

				<table class="paginated">
					<thead>
						<tr>
							<th>
								<label class="status_check_All">
									<img class='action_img' src='../resources/image/icon/chk_off.png' alt='' />
									<input type="checkbox" name='check_All' value='' />
								</label>
							</th>
							<th>주문번호</th>
							<th>주문자</th>
							<th>주문자 번호</th>
							<th>배송 주소</th>
							<th>결제 제품</th>
							<th>제품 개수</th>
							<th>해당 결제 가격</th>
							<th>배송비</th>
							<th>배송번호</th>
							<th>결제 시간</th>
							<th>상태 변경</th>
							<th>메모</th>
						</tr>
					</thead>
					<tbody id="user_List">

					</tbody>
				</table>
			</div>

			<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
			<script type="text/javascript" src="https://code.jquery.com/jquery-1.12.4.js"></script>
			<script type="text/javascript" src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
			<script type="text/javascript" src="${pageContext.request.contextPath}/js/admin/adPay.js"></script>
		</div>
	</div>
</li>

</s:authorize>

<jsp:include page="./ad_footer.jsp"></jsp:include>