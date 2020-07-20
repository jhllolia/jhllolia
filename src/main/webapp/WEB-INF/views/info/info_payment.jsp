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
						<p># 상태 조회</p>
						<select name="searchOption" id="board-search" class="board-search">
							<option value="n" <c:out value="${param.searchType == null ? 'selected' : ''}"/>>:::: 선택 ::::</option>
							<option value="t" <c:out value="${param.searchType eq 't' ? 'selected' : ''}"/>>주문번호</option>
							<option value="c" <c:out value="${param.searchType eq 'c' ? 'selected' : ''}"/>>상품명</option>
						</select>

						<input type="text" id="payment_txt" name="payment_txt" class="form-control btn log" />
						<input type="button" id="payment_search" name="payment_search" class="btn log" value="조회" />
						<a href="./info_payment" class="rotate btn log">
							<img src="../resources/image/icon/rotate.png" alt="rotate" />
						</a>
					</li>
				</ul>
			</div>

			<div class="list">
				<ul id="user_List"></ul>
			</div>

			<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css?ver=<%=System.currentTimeMillis() %>">
			<script type="text/javascript" src="https://code.jquery.com/jquery-1.12.4.js?ver=<%=System.currentTimeMillis() %>"></script>
			<script type="text/javascript" src="https://code.jquery.com/ui/1.12.1/jquery-ui.js?ver=<%=System.currentTimeMillis() %>"></script>
			<script src="https://cdnjs.cloudflare.com/ajax/libs/underscore.js/1.8.3/underscore-min.js?ver=<%=System.currentTimeMillis() %>"></script>
			<script type="text/javascript" src="${pageContext.request.contextPath}/js/product/fn_dashboard.js?ver=<%=System.currentTimeMillis() %>"></script>
		</div>

	</c:otherwise>
</c:choose>

<jsp:include page="./info_footer.jsp"></jsp:include>