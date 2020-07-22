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
				<div class="custom_wrap">
					<div class="buy_list">
						<table id="update_data">
							<tbody>
								<tr>					
									<td>
										<div class="update_img">
											<img src="../resources/upload/table/${DATA.PRODUCT_NUM}/${DATA.FILE_NAME}" alt="" />
										</div>
									</td>
									<td>${DATA.PRODUCT_NAME}</td>
									<td>${DATA.PRODUCT_OPTION}</td>
									<td>${DATA.PRODUCT_QTY} 개</td>
								</tr>
							</tbody>
						</table>
					</div>

					<% pageContext.setAttribute("newLineChar", "\n"); %>
					<div class="review_content" >
						<p class="re_comment">고객 리뷰</p>
						<p>${fn:replace(DATA.VIEW_CONTENT,"<br />",newLineChar)}</p>
					</div>

					<div class="review_content" >
						<p class="re_comment">관리자 답변</p>
						<textarea name="reContent" id="adminComment" maxlength="400">${fn:replace(DATA.REVIEW_REPLY,"<br />",newLineChar)}</textarea>
					</div>

					<div class="untilFile">
						<ul class="sendData">
							<li><button name="updateReview" id="updateRev" data-idx="${DATA.VIEW_SEQ}" class="btn log">입력사항 저장</button></li>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>

	<script type="text/javascript" src="${pageContext.request.contextPath}/js/admin/adReview.js"></script>
</li>

</s:authorize>

<jsp:include page="./ad_footer.jsp"></jsp:include>