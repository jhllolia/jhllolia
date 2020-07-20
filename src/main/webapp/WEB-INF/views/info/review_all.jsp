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
					작성된 리뷰<i class='delivery'><img src='../resources/image/icon/review.png' alt='request' /></i>
				</p>

				<div class="qna_list">
					<table id="rev_tab" class="qnaTab">
						<thead>
							<tr>
								<th>리뷰상품</th>
								<th>리뷰내용</th>
								<th>작성일</th>
								<th></th>
							</tr>
						</thead>
						<tbody>
							<c:choose>
								<c:when test="${fn:length(list) eq ''}">
									<tr>
										<td colspan="4" class="none">검색할 데이터가 없습니다.</td>
									</tr>
								</c:when>
								<c:otherwise>
									<c:forEach items="${list}" var="dto" varStatus="status">
										<tr>
											<td class="rev_product">${dto.PRODUCT_NAME}</td>
											<td class="rev_content">
												<div class="content">
													<span id="clickable">${dto.VIEW_CONTENT}</span>
												</div>
											</td>
											<td><fmt:formatDate value="${dto.VIEW_DATE}" pattern="yyyy.MM.dd"/></td>
											<td class="rev_status">
												<div class="rev">
													<button id="rev_update" data-num="${dto.ORDER_NUM}" data-product="${dto.PRODUCT_NUM}" data-name="${dto.PRODUCT_NAME}" data-sub="${dto.PRODUCT_OPTION}" data-qty="${dto.PRODUCT_QTY}" class="btn log">수정</button>
													<button id="rev_delete" data-num="${dto.ORDER_NUM}" data-product="${dto.PRODUCT_NUM}" data-name="${dto.PRODUCT_NAME}" data-sub="${dto.PRODUCT_OPTION}" data-qty="${dto.PRODUCT_QTY}" class="btn log">삭제</button>
												</div>
											</td>
										</tr>
										<tr class="rev_on on">
											<td colspan="4">
												<div class="num">${dto.ORDER_NUM}</div>
												<div class="star">
													<div class="member_be"><c:forEach begin="0" end="${dto.VIEW_POINT - 1}">★</c:forEach></div>
													<div class="member_af">★★★★★</div>
												</div>
												<div class="view_content">${dto.VIEW_CONTENT}</div>
											</td>
										</tr>
									</c:forEach>
								</c:otherwise>
							</c:choose>
						</tbody>
					</table>

					<c:choose>
						<c:when test="${fn:length(list) ne ''}">
							<div class="box-footer clearfix">
								<c:if test="${pageNum ne ''}">
									<ul class="pagination pagination-sm no-margin">
										<li>
											<c:choose>
												<c:when test="${pageMaker.prev ne false}">
													<a href="/info/review_all${pageMaker.makeSearch(pageMaker.startPage - 1)}">&laquo;</a>
												</c:when>
												<c:otherwise>
													<a href="/info/review_all${pageMaker.makeSearch(pageMaker.startPage)}">&laquo;</a>
												</c:otherwise>
											</c:choose>
										</li>
										<li>
											<c:forEach var="pageNum" begin="${pageMaker.startPage}" end="${pageMaker.endPage}">
												<c:choose>
													<c:when test="${pageNum eq pageMaker.cri.page}">
														<a href="/info/review_all${pageMaker.makeSearch(pageNum)}">${pageNum}</a>
													</c:when>
													<c:otherwise>
														<a href="/info/review_all${pageMaker.makeSearch(pageNum)}">${pageNum}</a>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</li>
										<li>
											<c:choose>
												<c:when test="${pageMaker.next ne true && pageMaker.endPage > 0 }">
													<a href="/info/review_all${pageMaker.makeSearch(pageMaker.endPage)}">&raquo;</a>
												</c:when>
												<c:otherwise>
													<a href="/info/review_all${pageMaker.makeSearch(pageMaker.endPage + 1)}">&raquo;</a>
												</c:otherwise>
											</c:choose>
										</li>
									</ul>
								</c:if>
							</div>
						</c:when>
					</c:choose>
				</div>
			</div>

			<script type="text/javascript" src="${pageContext.request.contextPath}/js/product/fn_Review.js"></script>
		</div>
	</c:otherwise>
</c:choose>

<jsp:include page="./info_footer.jsp"></jsp:include>