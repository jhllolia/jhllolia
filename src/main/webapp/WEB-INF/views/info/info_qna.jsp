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

				<div class="qna_list">
					<span id="qna_insert">
						<button onclick='location.href="../info/customer"' class="btn log">제품문의</button>
					</span>

					<table class="qnaTab">
						<thead>
							<tr>
								<th></th>
								<th></th>
								<th>제품 명</th>
								<th>제품 제목</th>
								<th>등록 일자</th>
							</tr>
						</thead>
						<tbody>
							<c:choose>
								<c:when test="${fn:length(list) eq ''}">
									<tr>
										<td colspan="5" class="none">검색할 데이터가 없습니다.</td>
									</tr>
								</c:when>
								<c:otherwise>
									<c:forEach items="${list}" var="dto" varStatus="status">
										<tr>
											<td class="qnaWhat">
												<c:choose>
													<c:when test="${dto.MEMBER_QNA_CAT eq 'PRODUCT_QA'}">
														<div class="in">상품문의</div>
													</c:when>
													<c:when test="${dto.MEMBER_QNA_CAT eq 'SHIPPING_QA'}">
														<div class="in">배송문의</div>
													</c:when>
													<c:when test="${dto.MEMBER_QNA_CAT eq 'UNTIL_QA'}">
														<div class="in">기타</div>
													</c:when>
												</c:choose>
											</td>

											<td class="qnaResult">
												<c:choose>
													<c:when test="${dto.MEMBER_QNA_RESULT eq 'WAIT'}">답변대기</c:when>
													<c:when test="${dto.MEMBER_QNA_RESULT eq 'DONE'}">답변완료</c:when>
												</c:choose>
											</td>

											<td class="qnaName">${dto.PRODUCT_NAME}</td>
											<td class="qnaTxt"><a name="qnaContent" data-name="qna_${dto.INTSEQ_RE_REF}" class="naming">${dto.MEMBER_QNA_TITLE}</a></td>
											<td class="qnaDate"><fmt:formatDate value="${dto.MEMBER_QNA_DATE}" pattern="yyyy.MM.dd"/></td>
										</tr>
										<tr class="qna_view">
											<td colspan="5">
												<div class="qna_container">
													<div class="area">
														<span class="qna_icon">Q</span>
														<p class="q_txt">
															<c:choose>
																<c:when test="${dto.INTSEQ_RE_REF eq 1}">
																	${fn:replace(dto.MEMBER_QNA_CONTENT,"<br />",newLineChar)}
																</c:when>
																<c:otherwise>
																	<% pageContext.setAttribute("newLineChar", "\n"); %>
																	<textarea name="qContent" maxlength="200">${fn:replace(dto.MEMBER_QNA_CONTENT,"<br />",newLineChar)}</textarea>
																	<span id="counter"></span>
																	<button name="qUpdate" data-seq="qna_${dto.INTSEQ}" class="btn log">수정</button>
																</c:otherwise>
															</c:choose>
														</p>
													</div>

													<c:if test="${dto.INTSEQ_RE_REF eq 1}">
														<div class="area">
															<span class="qna_icon">A</span>
															<p class="q_txt">${dto.ADMIN_QNA_CONTENT}</p>
															<span class="result_date">답변일 : <fmt:formatDate value="${dto.ADMIN_QNA_DATE}" pattern="yyyy.MM.dd"/></span>
														</div>
													</c:if>
												</div>
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
													<a href="/info/info_qna${pageMaker.makeSearch(pageMaker.startPage - 1)}">&laquo;</a>
												</c:when>
												<c:otherwise>
													<a href="/info/info_qna${pageMaker.makeSearch(pageMaker.startPage)}">&laquo;</a>
												</c:otherwise>
											</c:choose>
										</li>
										<li>
											<c:forEach var="pageNum" begin="${pageMaker.startPage}" end="${pageMaker.endPage}">
												<c:choose>
													<c:when test="${pageNum eq pageMaker.cri.page}">
														<a href="/info/info_qna${pageMaker.makeSearch(pageNum)}">${pageNum}</a>
													</c:when>
													<c:otherwise>
														<a href="/info/info_qna${pageMaker.makeSearch(pageNum)}">${pageNum}</a>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</li>
										<li>
											<c:choose>
												<c:when test="${pageMaker.next ne true && pageMaker.endPage > 0 }">
													<a href="/info/info_qna${pageMaker.makeSearch(pageMaker.endPage)}">&raquo;</a>
												</c:when>
												<c:otherwise>
													<a href="/info/info_qna${pageMaker.makeSearch(pageMaker.endPage + 1)}">&raquo;</a>
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

			<script type="text/javascript" src="${pageContext.request.contextPath}/js/product/fn_QnA.js"></script>
		</div>
	</c:otherwise>
</c:choose>

<jsp:include page="./info_footer.jsp"></jsp:include>