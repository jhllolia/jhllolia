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
				<div class="wrap-loading display-none">
				    <div>
				    	<img src="../resources/image/icon/loading.gif" />
				    </div>
				</div>
			
				<table class="table table-board">
					<tbody>
						<tr>
							<td class="rec_td">
								<div class="rec">
									<ul>
										<li>
											<input type="hidden" name="seq" id="seq" value="${view.intSeq}">
											<input type="hidden" name="id" id="id" value="${view.user_Id}">
											<strong>${view.user_Id}</strong>
										</li>
										<li>
											<input type="text" class="form-control" id="recipe_Subject" value="${view.recipe_Subject}" >
										</li>
										<li>
											<script type="text/javascript" src="//cdn.ckeditor.com/4.8.0/full-all/ckeditor.js"></script>
											<textarea class="form-control" class="ckeditor" name="ckeditor" id="ckeditor">${view.recipe_Content}</textarea>
										</li>
										<li>
											<div class="btn-group">
												<div class="btn-It">
													<c:choose>
														<c:when test="${sessionScope.member_Id == view.user_Id}">
															<input type="submit" class="btn btn-primary btn-lg" id="recipe_Update" name="recipe_Update" value="글 작성">
															<a href="#" onclick="history.back(-1); return false;"  class="btn btn-primary btn-lg">목록</a>
														</c:when>
														<c:otherwise>
															<a href="#" onclick="history.back(-1); return false;" class="btn btn-middle btn-gray btn-lg">목록</a>
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
						
									<div class="auth d_auth">
										<div class="auth_main">
											<span class="close_btn icon">
												<img src="../resources/image/icon/close_icon.png" alt="" />
											</span>
											<h4>현재 비밀번호</h4>
											<hr />
											<p class="auth_txt">
												현재 비밀번호를 입력하시길 바랍니다.
											</p>
											<table>
												<tbody>
													<tr>
														<td>
															<input type="password" name="auth_txt" id="auth_txt">
															<button id="info_pwd_chk" class="btn log">확인</button>
														</td>
													</tr>
												</tbody>
											</table>
										</div>
									</div>

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