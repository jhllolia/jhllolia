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
		<div class="adminQna">
			<div class="qna_list">
				<table class="qnaTab">
					<thead>
						<tr>
							<th></th>
							<th></th>
							<th>제품 명</th>
							<th>제품 제목</th>
							<th>등록 회원</th>
							<th>등록 일자</th>
							<th>상태</th>
						</tr>
					</thead>
					<tbody>
						<c:choose>
							<c:when test="${fn:length(list) eq ''}">
								<tr>
									<td colspan="7">검색할 데이터가 없습니다.</td>
								</tr>
							</c:when>
							<c:otherwise>
								<c:forEach items="${list}" var="dto" varStatus="status">
									<c:set var="cmt" value="${fn:replace(dto.ADMIN_QNA_CONTENT,CRLF, BR)}" />
									<c:set var="cmt" value="${fn:replace(cmt,CR, BR)}" />
									<c:set var="cmt" value="${fn:replace(cmt,CR, BR)}" />
									<c:set var="cmt" value="${fn:replace(cmt,' ',SP)}" />

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
										<td class="qnaName">${dto.MEMBER_ID}</td>
										<td class="qnaDate"><fmt:formatDate value="${dto.MEMBER_QNA_DATE}" pattern="yyyy.MM.dd"/></td>
										<td class="qnaAdState">
											<c:choose>
												<c:when test="${dto.MEMBER_QNA_STATE eq 'Y' }">
													<input type="button" name="qnaState" data-num="${dto.INTSEQ}" data-check="${dto.MEMBER_QNA_STATE}" class="state_on" value="활성화" />
												</c:when>
												<c:otherwise>
													<input type="button" name="qnaState" data-num="${dto.INTSEQ}" data-check="${dto.MEMBER_QNA_STATE}" class="state_off" value="비활성화" />	
												</c:otherwise>
											</c:choose>
										</td>
									</tr>
									<tr class="qna_view">
										<td colspan="7">
											<div class="qna_container">
												<div class="area">
													<span class="qna_icon">Q</span>
													<p class="q_txt">${dto.MEMBER_QNA_CONTENT}</p>
												</div>

												<div class="area">
													<span class="qna_icon">A</span>
													<p class="q_txt">
														<% pageContext.setAttribute("newLineChar", "\n"); %>
														<textarea name="qContent" maxlength="1000">${fn:replace(dto.ADMIN_QNA_CONTENT,"<br />",newLineChar)}</textarea>
														<span id="counter"></span>
														<button name="sendPost" data-seq="qna_${dto.INTSEQ}" class="btn log">등록</button>
													</p>
												</div>
											</div>
										</td>
									</tr>
								</c:forEach>
							</c:otherwise>
						</c:choose>
					</tbody>
				</table>
			</div>

			<div class="box-footer clearfix">
				<ul class="pagination pagination-sm no-margin">
					<li>
						<c:choose>
							<c:when test="${pageMaker.prev ne false}">
								<a href="/qna?page=${pageMaker.startPage - 1}">&laquo;</a>
							</c:when>
							<c:otherwise>
								<a href="/qna?page=${pageMaker.startPage}">&laquo;</a>
							</c:otherwise>
						</c:choose>
					</li>
					<li>
						<c:forEach var="pageNum" begin="${pageMaker.startPage}" end="${pageMaker.endPage}">
							<c:choose>
								<c:when test="${pageNum eq pageMaker.cri.page}">
									<a href="/qna?page=${pageNum}">${pageNum}</a>
								</c:when>
								<c:otherwise>
									<a href="/qna?page=${pageNum}">${pageNum}</a>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</li>
					<li>
						<c:choose>
							<c:when test="${pageMaker.next ne true && pageMaker.endPage > 0 }">
								<a href="/qna?page=${pageMaker.endPage}">&raquo;</a>
							</c:when>
							<c:otherwise>
								<a href="/qna?page=${pageMaker.endPage + 1}">&raquo;</a>
							</c:otherwise>
						</c:choose>
					</li>
				</ul>
			</div>
			
		</div>

		<script type="text/javascript" src="${pageContext.request.contextPath}/js/admin/adQnA.js"></script>
	</div>
</li>

</s:authorize>

<jsp:include page="./ad_footer.jsp"></jsp:include>