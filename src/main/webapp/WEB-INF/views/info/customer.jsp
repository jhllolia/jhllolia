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
					제품 Q&A<i class='delivery'><img src='../resources/image/icon/request.png' alt='request' /></i>
				</p>

				<div class="custom_wrap">
					<ul class="policy">
						<li>상품과 관련없는 내용, 비방, 광고, 불건전한 내용의 글은 사전 동의 없이 삭제될 수 있습니다.</li>
						<li>문의 내역 및 답변은 상품 Q&A에서 조회 가능합니다.</li>
					</ul>

					<div class="question_wrap">
						<table id="paper">
							<tbody>
								<tr>
									<th>유형</th>
									<td>
										<div class="check_label">
											<label name="question_select" class="qSelect">
												<input type="radio" name="boxId" value="PRODUCT_QA" />
												<span class="txt">상품문의</span>
											</label>
											<label name="question_select" class="qSelect">
												<input type="radio" name="boxId" value="SHIPPING_QA" />
												<span class="txt">배송문의</span>
											</label>
											<label name="question_select" class="qSelect">
												<input type="radio" name="boxId" value="UNTIL_QA" />
												<span class="txt">기타문의</span>
											</label>
										</div>
									</td>
								</tr>
								<tr class="view_option">
									<th>선택 상품</th>
									<td>
										<div class="product_sale">
											<select class="form-control" name="sale">
												<option value="">↓ 현재 판매중인 제품</option>
												<c:forEach items="${list}" var="dto" varStatus="status">
													<option value="${dto.PRODUCT_SEQ}">${dto.PRODUCT_TITLE}</option>
												</c:forEach>
											</select>
										</div>
									</td>
								</tr>
								<tr>
									<th>제목</th>
									<td>
										<div class="product_sale">
											<input type="text" name="qna_tit" class="tit form-control" />
										</div>
									</td>
								</tr>
								<tr>
								<th>내용</th>
									<td>
										<div class="product_sale">
											<textarea name="qContent" class="qustion_content" maxlength="200"></textarea>
											<span id="counter"></span>
										</div>
									</td>
								</tr>
							</tbody>
						</table>

						<div class="send_wrap">
							<p class="txt"><span>*</span>답변등록시 등록된 휴대폰 번호로 알림이 도착합니다.</p>
							<button name="sendData" id="sendData" class="btn log">제출</button>
						</div>
					</div>
				</div>
			</div>

			<script type="text/javascript" src="${pageContext.request.contextPath}/js/product/fn_QnA.js"></script>
		</div>
	</c:otherwise>
</c:choose>

<jsp:include page="./info_footer.jsp"></jsp:include>