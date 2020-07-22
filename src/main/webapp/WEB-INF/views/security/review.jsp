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
		<div class="day">
			<div class="seDash">
				<div id="dayList" class="seList">
					<table id="return" class="paginated">
						<thead>
							<tr>
								<th>주문번호</th>
								<th>제품 명</th>
								<th>리뷰 포인트</th>
								<th>이름</th>
								<th>이미지</th>
								<th>답변여부</th>
								<th></th>
								<th>상태</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${list}" var="dto" varStatus="status">
								<tr>
									<td>${dto.ORDER_NUM}</td>
									<td>${dto.PRODUCT_NAME}</td>
									<td>${dto.VIEW_POINT}</td>
									<td>${dto.REVIEW_NAME}</td>
									<td>
										<div id="uldData" name="uldData_${dto.PRODUCT_NUM}"></div>
										<script>
											jQuery.noConflict();
									
											jQuery(function($) {
												$(document).ready(function() {

													var idx = '${dto.VIEW_SEQ}';					// 시퀀스
													var upload = JSON.parse('${dto.VIEW_UPLOAD}');	// 이미지 JSON ARR

													for (var i = 0; i < upload.length; i++) {
														$('div[name=uldData_' + idx + ']').append("<img src='../resources/upload/review/" + upload[i].FILE_NAME + "' class='rev_img' alt='' />");
													}
												});
											});
										</script>
									</td>
									<td><fmt:formatDate value="${dto.VIEW_DATE}" pattern="yyyy.MM.dd" /></td>
									<td style="color:${dto.REVIEW_REPLY_YN eq 'Y' ? '#285fde' : '#d61a46'}">${dto.REVIEW_REPLY_YN eq 'Y' ? '답변완료' : '답변대기'}</td>
									<td class="change">
										<button name="revView" orderNum="${dto.ORDER_NUM}" rev_id="${dto.REVIEW_ID}" prdNum="${dto.PRODUCT_NUM}" prdName="${dto.PRODUCT_NAME}" prdOpt="${dto.PRODUCT_OPTION}" prdQty="${dto.PRODUCT_QTY}" class="btn btn-success">상세</button>
										<button name="deleteRev" data-idx="${dto.VIEW_SEQ}" data-state="${dto.REVIEW_REPLY_YN}" class="btn btn-warning">삭제</button>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>

		<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
		<script type="text/javascript" src="https://code.jquery.com/jquery-1.12.4.js"></script>
		<script type="text/javascript" src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/admin/adReview.js"></script>
	</div>
</li>

</s:authorize>

<jsp:include page="./ad_footer.jsp"></jsp:include>