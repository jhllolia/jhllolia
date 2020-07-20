<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<jsp:include page="../header.jsp" />

<div class="main">
	<div id="recipe_main" class="recipe_main">
		<p class="recipe_txt_1">SPECIAL PROJECT</p>
		<p class="recipe_txt_2">어양토속순대를 가장 맛있게 먹는 방법</p>
		
		<!-- 

		<div class="recipe_write">
			<input type="hidden" name="session" id="session" value="${sessionScope.member_Id}" />
			<button id="latter" class="btn log">후기 작성</button>
		</div>
 		
		<c:if test="${param['del'] eq 'Y'}">
			<div class="alert alert-success" style="text-align: center;">게시물이 삭제되었습니다.</div>
		</c:if>

		<c:if test="${param['del'] eq 'N'}">
			<div class="alert alert-danger" style="text-align: center;">게시물 삭제에 실패했습니다.</div>
		</c:if>

 		-->

		<c:choose>
			<c:when test="${fn:length(list) == 0}">
				<div class="recipe">
					<div>
						<a class="recipe_img" style="background-image: url(../resources/image/food/food_2.jpg);"></a>
						<a class="recipe_txt">
							<strong class="recipe_txt_1">등록된 글이 없습니다.</strong>
							<p class="recipe_txt_2">등록된 글이 없습니다.</p>
						</a>
						<div class="recipe_txt_3">
							<span>레시피</span>
							<p>0000.00.00 00:00:00<p>
						</div>
					</div>
				</div>
			</c:when>
			<c:otherwise>
				<c:forEach items="${list}" var="dto">
					<div class="recipe">
						<c:choose>
							<c:when test="${dto.recipe_Category == '후기'}">
								<div onclick="location.href='./recipe_view${pageMaker.makeSearch(pageMaker.cri.page)}&intSeq=${dto.intSeq}'">
							</c:when>
							<c:otherwise>
								<div onclick="window.open('${dto.recipe_SubTitle}','new_window');">
							</c:otherwise>	
						</c:choose>
							<a class="recipe_img" style="background-image: url('${dto.recipe_Title}');"></a>
							<a class="recipe_txt">
								<strong class="recipe_txt_1"><c:out value='${dto.recipe_Subject.replaceAll("\\\<.*?\\\>","")}' escapeXml="false" /></strong>
								<c:choose>
									<c:when test="${dto.recipe_Category == '후기'}">
										<p class="recipe_txt_2"><c:out value='${dto.recipe_SubTitle.replaceAll("\\\<.*?\\\>","")}' escapeXml="false" /></p>
									</c:when>
									<c:otherwise>
										<p class="recipe_txt_2"><c:out value='${dto.recipe_Content.replaceAll("\\\<.*?\\\>","")}' escapeXml="false" /></p>
									</c:otherwise>
								</c:choose>
							</a>
							<div class="recipe_txt_3">
								<c:choose>
									<c:when test="${dto.recipe_Category == '후기'}">
										<span style="background-color: #a58045;">${dto.recipe_Category}</span>
									</c:when>
									<c:otherwise>
										<span>${dto.recipe_Category}</span>
									</c:otherwise>	
								</c:choose>
								<p>${dto.user_Id}<p>
							</div>
						</div>
					</div>
				</c:forEach>
			</c:otherwise>
		</c:choose>
	</div>

	<div class="box-footer clearfix">
		<ul class="pagination pagination-sm no-margin">
			<li>
				<c:choose>
					<c:when test="${pageMaker.prev ne false}">
						<a href="/bbs/recipe${pageMaker.makeSearch(pageMaker.startPage - 1)}">&laquo;</a>
					</c:when>
					<c:otherwise>
						<a href="/bbs/recipe${pageMaker.makeSearch(pageMaker.startPage)}">&laquo;</a>
					</c:otherwise>
				</c:choose>
			</li>
			<li>
				<c:forEach var="pageNum" begin="${pageMaker.startPage}" end="${pageMaker.endPage}">
					<c:choose>
						<c:when test="${pageNum eq pageMaker.cri.page}">
							<a href="/bbs/recipe${pageMaker.makeSearch(pageNum)}">${pageNum}</a>
						</c:when>
						<c:otherwise>
							<a href="/bbs/recipe${pageMaker.makeSearch(pageNum)}">${pageNum}</a>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</li>
			<li>
				<c:choose>
					<c:when test="${pageMaker.next ne true && pageMaker.endPage > 0 }">
						<a href="/bbs/recipe${pageMaker.makeSearch(pageMaker.endPage)}">&raquo;</a>
					</c:when>
					<c:otherwise>
						<a href="/bbs/recipe${pageMaker.makeSearch(pageMaker.endPage + 1)}">&raquo;</a>
					</c:otherwise>
				</c:choose>
			</li>
		</ul>
	</div>

	<script type="text/javascript" src="${pageContext.request.contextPath}/js/cook.js"></script>
</div>

<jsp:include page="../footer.jsp" />