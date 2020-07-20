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
				<p class="orderTit">
					상품평작성<i class='delivery'><img src='../resources/image/icon/review.png' alt='request' /></i>
				</p>

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

					<ul id="star" class="star">
						<li><p class="title">별점을 선택해주세요.</p></li>
						<li>
							<p class="rating" data-point="${DATA.VIEW_POINT}">
								<button id="btn_1">★</button>
								<button id="btn_2">★</button>
								<button id="btn_3">★</button>
								<button id="btn_4">★</button>
								<button id="btn_5">★</button>
							</p>
						</li>
						<li>
							<p class="rating_result">
								<span name="txt_01" class="star_ico">아쉽네요!</span>
								<span name="txt_02" class="star_ico">그냥그래요!</span>
								<span name="txt_03" class="star_ico">보통이에요!</span>
								<span name="txt_04" class="star_ico">좋아요!</span>
								<span name="txt_05" class="star_ico">최고예요!</span>
							</p>
						</li>
					</ul>

					<div class="review_content" >
						<p class="re_comment">고객님의 리뷰가 다른 고객들에게 도움이 될 수 있어요!</p>
						<% pageContext.setAttribute("newLineChar", "\n"); %>
						<textarea name="reContent" id="reContent" maxlength="400">${fn:replace(DATA.VIEW_CONTENT,"<br />",newLineChar)}</textarea>
						<span id="limit"></span>
					</div>

					<div class="untilFile">
						<input type="file" name="uploadFile" id="uploadFile" accept="image/*" class="file" multiple="multiple">

						<div id="thumbnails">
							<c:forEach items="${FILE}" var="dto" varStatus="status">
								<div class="thumb" data-seq="${status.index}" data-name="${FILE[status.index].FILE_NAME}" data-title="${FILE[status.index].FILE_TITLE}" data-size="${FILE[status.index].FILE_SIZE}">
									<span name="img_close" id="img_close" data-idx="${status.index}">X</span>
									<img src="../resources/upload/review/${FILE[status.index].FILE_NAME}" alt="${FILE[status.index].FILE_TITLE}" />
									<input type="file" class="upload_file" name="upload_${status.index}" value="${FILE[status.index].FILE_NAME}" />
								</div>
							</c:forEach>
						</div>

						<ul class="sendData">
							<li>
								<input type="hidden" id="num_check" name="num_check" data-order="${DATA.ORDER_NUM}" />
								<input type="hidden" id="order_se" value="${DATA.PRODUCT_NUM}" />
								<input type="hidden" id="order_na" value="${DATA.PRODUCT_NAME}" />
								<input type="hidden" id="order_op" value="${DATA.PRODUCT_OPTION}" />
								<input type="hidden" id="order_qt" value="${DATA.PRODUCT_QTY}" />
								<button name="updateReview" class="btn log">선택사항 제출</button>
							</li>
						</ul>
					</div>
				</div>
			</div>

			<script type="text/javascript" src="${pageContext.request.contextPath}/js/product/fn_Review.js"></script>
		</div>
	</c:otherwise>
</c:choose>

<jsp:include page="./info_footer.jsp"></jsp:include>