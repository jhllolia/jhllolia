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
						<p id="default"><img src="../resources/image/icon/box.png" alt="box.png" />상품을 선택하세요</p>
						<div id="optionData" class="exp">
							<c:forEach items="${list}" var="dto" varStatus="status">
								<div name="buy_${status.index}" data-seq="${dto.PRODUCT_NUM}" class="optionList">
									<li class="product_img"><img class="thumb" src="../resources/upload/table/${dto.PRODUCT_NUM}/${dto.PRODUCT_PROFILE}" alt="${dto.ORDER_NUM}" /></li>
									<li class="name">${dto.PRODUCT_NAME}</li>
									<li class="option">${dto.PRODUCT_OPTION}</li>
									<li class="qty">${dto.PRODUCT_QTY} 개</li>
								</div>
							</c:forEach>
						</div>
					</div>

					<ul id="star" class="star">
						<li><p class="title">별점을 선택해주세요.</p></li>
						<li>
							<p class="rating">
								<button id="btn_1" class="on">★</button>
								<button id="btn_2" class="on">★</button>
								<button id="btn_3" class="on">★</button>
								<button id="btn_4" class="on">★</button>
								<button id="btn_5" class="on">★</button>
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
						<textarea name="reContent" id="reContent" maxlength="400"></textarea>
						<span id="limit"></span>
					</div>

					<div class="untilFile">
						<input type="file" name="uploadFile" id="uploadFile" accept="image/*" class="file" multiple="multiple">

						<div id="thumbnails"></div>

						<ul class="sendData">
							<li>
								<input type="hidden" id="num_check" name="num_check" data-order="${ORDER}" />
								<input type="hidden" id="order_se" value="" />
								<input type="hidden" id="order_na" value="" />
								<input type="hidden" id="order_op" value="" />
								<input type="hidden" id="order_qt" value="" />
								<button name="reviewSend" class="btn log">선택사항 제출</button>
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