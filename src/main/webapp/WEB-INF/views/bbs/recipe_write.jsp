<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<jsp:include page="../header.jsp" />

	<c:choose>
		<c:when test="${msg == 'failure'}">
			<div class="error">
				123
			</div>
		</c:when>
		<c:otherwise>
			<div class="main">
				<table class="table table-board">
					<tbody>
						<tr>
							<td class="rec_td">
								<div class="rec">
									<ul>
										<li>
											<input type="hidden" id="user_Id" value="${sessionScope.member_Id}"  readonly />
											<strong>${sessionScope.member_Id}</strong>
										</li>
										<li>
											<input type="text" class="form-control" id="recipe_Subject" placeholder="제목을 입력해주세요">
										</li>
										<li>
											<script type="text/javascript" src="//cdn.ckeditor.com/4.8.0/full-all/ckeditor.js"></script>
											<textarea class="form-control" class="ckeditor" name="ckeditor" id="ckeditor"></textarea>
										</li>
										<li>
											<div class="btn-group">
												<div class="btn-It">
													<c:choose>
														<c:when test="${sessionScope.member_Id == null}">
															<a href="#" onclick="history.back(-1); return false;" class="btn btn-middle btn-gray btn-lg">목록</a>
														</c:when>
														<c:otherwise>
															<input type="submit" class="btn btn-primary btn-lg" id="recipe_write" name="recipe_write" value="글 작성">
															<a href="#" onclick="history.back(-1); return false;" class="btn btn-primary btn-lg">목록</a>
														</c:otherwise>
													</c:choose>
												</div>
											</div>

<%-- 											
											<s:authorize ifAnyGranted="ROLE_ADMIN">
												<input type="submit" class="btn btn-primary btn-lg" id="recipe_write" name="recipe_write" value="글 작성">
												<a href="../admin" class="btn btn-primary btn-lg">목록</a>
											</s:authorize>
												<s:authorize ifNotGranted="ROLE_ADMIN">
												<a href="../admin" class="btn btn-middle btn-gray btn-lg">목록</a>
											</s:authorize>
--%>
										</li>
									</ul>
								</div>
							</td>
						</tr>
					</tbody>
				</table>
				
				<script type="text/javascript" src="${pageContext.request.contextPath}/js/cook.js"></script>
			</div>
		</c:otherwise>
	</c:choose>

<jsp:include page="../footer.jsp" />