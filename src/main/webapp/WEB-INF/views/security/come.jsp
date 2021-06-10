<%@ page import="org.springframework.security.core.Authentication" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
	<div class="t_wrap">
		<div class="t_container">
			<table class="t_tab">
				<tbody>
					<tr>
						<td colspan="4">
							<h2>방문자 조회</h2>
							<hr />
						</td>
					</tr>
					<tr>
						<td class="today">오늘 방문자</td>
						<td class="today">전체 방문자</td>
						<td class="today">날짜 선택</td>
						<td class="total">엑셀 다운</td>
					</tr>
					<tr>
						<td class="t_txt">${visitCount}</td>
						<td class="t_txt"><fmt:formatNumber value="${sessionScope.totalCount}" pattern="#,###" /></td>
						<td class="t_txt"><input type="text" id="datepicker" class="datepicker" value="" /></td>
						<td class="t_txt"><input type="button" name="excel" id="excel" value="제출" /></td>
					</tr>
				</tbody>
			</table>

			<table class="t_list">
				<thead>
					<tr>
						<td>번호</td>
						<td>접속 IP</td>
						<td></td>
						<td>접속 시간</td>
						<td>이전 URL</td>
						<td>접속 기기</td>
					</tr>
				</thead>
				<tbody>
					<c:choose>
						<c:when test="${fn:length(visit) == 0}">
							<tr>
								<td colspan="5" align="center">조회 결과가 없습니다.</td>
							</tr>
						</c:when>
						<c:otherwise>
							<c:forEach items="${visit}" var="dto">
								<tr>
									<td>${dto.visit_id}</td>
									<td><span id="visitIp" class="visitIp">${dto.visit_ip}</span></td>
									<td><fmt:formatDate value="${dto.visit_time}" pattern="yyyy.MM.dd HH:MM:SS"/></td>
									<td>${dto.visit_product}</td>
									<c:choose>
										<c:when test="${ empty dto.visit_refer }">
											<td>X</td>
										</c:when>
										<c:otherwise>
											<td><a href="${dto.visit_refer}" target="_blank">${dto.visit_refer}</a></td>
										</c:otherwise>
									</c:choose>
									<td>
										<p>${dto.visit_agent}</p>
									</td>
								</tr>
							</c:forEach>
						</c:otherwise>
					</c:choose>
				</tbody>
			</table>

			<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
			<script type="text/javascript" src="https://code.jquery.com/jquery-1.12.4.js"></script>
			<script type="text/javascript" src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
			<script type="text/javascript" src="https://openapi.map.naver.com/openapi/v3/maps.js?ncpClientId=ezmq64t203"></script>
			<script type="text/javascript" src="${pageContext.request.contextPath}/js/admin/time.js"></script>

			<div class="box-footer clearfix">
				<ul class="pagination pagination-sm no-margin">
					<li>
						<c:choose>
							<c:when test="${pageMaker.prev ne false}">
								<a href="/come?page=${pageMaker.startPage - 1}&date=${param.date}">&laquo;</a>
							</c:when>
							<c:otherwise>
								<c:choose>
									<c:when test="${ empty param.date }">
										<a href="/come?page=${pageMaker.startPage}&date=${date}">&laquo;</a>
									</c:when>
									<c:otherwise>							
										<a href="/come?page=${pageMaker.startPage}&date=${param.date}">&laquo;</a>
									</c:otherwise>
								</c:choose>
							</c:otherwise>
						</c:choose>
					</li>
					<li>
						<c:forEach var="pageNum" begin="${pageMaker.startPage}" end="${pageMaker.endPage}">
							<c:choose>
								<c:when test="${pageNum eq pageMaker.cri.page}">
									<c:choose>
										<c:when test="${ empty param.date }">
											<a href="/come?page=${pageNum}&date=${date}">${pageNum}</a>
										</c:when>
										<c:otherwise>
											<a href="/come?page=${pageNum}&date=${param.date}">${pageNum}</a>
										</c:otherwise>
									</c:choose>
								</c:when>
								<c:otherwise>
									<c:choose>
										<c:when test="${ empty param.date }">
											<a href="/come?page=${pageNum}&date=${date}">${pageNum}</a>
										</c:when>
										<c:otherwise>
											<a href="/come?page=${pageNum}&date=${param.date}">${pageNum}</a>
										</c:otherwise>
									</c:choose>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</li>
					<li>
						<c:choose>
							<c:when test="${pageMaker.next ne true && pageMaker.endPage > 0 }">
								<c:choose>
									<c:when test="${ empty param.date }">
										<a href="/come?page=${pageMaker.endPage}&date=${date}">&raquo;</a>
									</c:when>
									<c:otherwise>
										<a href="/come?page=${pageMaker.endPage}&date=${param.date}">&raquo;</a>
									</c:otherwise>
								</c:choose>
							</c:when>
							<c:otherwise>
								<c:choose>
									<c:when test="${ empty param.date }">
										<a href="/come?page=${pageMaker.endPage + 1}&date=${date}">&raquo;</a>
									</c:when>
									<c:otherwise>
										<a href="/come?page=${pageMaker.endPage + 1}&date=${param.date}">&raquo;</a>
									</c:otherwise>
								</c:choose>
							</c:otherwise>
						</c:choose>
					</li>
				</ul>
			</div>
		</div>
	</div>
</li>

</s:authorize>

<jsp:include page="./ad_footer.jsp"></jsp:include>
