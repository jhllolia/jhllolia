<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<jsp:include page="../header.jsp" />

<div class="main">
	<div class="view">
		<div class="wrap-loading display-none">
		    <div>
		    	<img src="../resources/image/icon/loading.gif" />
		    </div>
		</div>

		<c:choose>
			<c:when test="${view.r_state eq 'Y'}">
				<input type="hidden" name="seq" id="seq" value="${view.intSeq}">
				<input type="hidden" name="id" id="id" value="${view.user_Id}">

				<div class="title">
					<strong>${view.recipe_Category}</strong>
					<h3>${view.recipe_Subject}</h3>
					<p>${view.register_Date}</p>
				</div>
				<div class="content">
					${view.recipe_Content}
				</div>

				<div class="btn-group">
					<div class="btn-It">
						<c:choose>
							<c:when test="${sessionScope.member_Id == view.user_Id}">
								<a id="btn_pack" href="./recipe?page=${param.page}" class="btn btn-middle btn-gray btn-lg">목록</a>
								<a id="update" href="../update/${view.intSeq}" class="btn btn-primary btn-lg">글 수정</a>
								<button id="recipe_delete" class="btn btn-primary btn-lg">글 삭제</button>
							</c:when>
							<c:otherwise>
								<a href="./recipe?page=${param.page}" class="btn btn-middle btn-gray btn-lg">목록</a>
							</c:otherwise>
						</c:choose>
					</div>
				</div>		
			</c:when>
			<c:otherwise>
				<div class="error">

				</div>
			</c:otherwise>
		</c:choose>

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

		<div class="r_comment">
			<script type="text/javascript" src="${pageContext.request.contextPath}/js/comment.js"></script>
			<div class="r_list">
				<ul>
					<c:forEach items="${comment}" var="dto">
						<li id="point${dto.c_Seq}" class="c_wrap" value="${dto.c_depth}">
							<c:if test="${dto.c_depth eq 0}">

							</c:if>
							<c:if test="${dto.c_depth eq 1}">
								<img src="../resources/image/icon/comment.png" alt="" />
							</c:if>
							<span>
								<h4>${dto.c_Id}</h4>
								<span class="date"><fmt:formatDate value="${dto.c_date}" pattern="yyyy.MM.dd"/></span>						
							</span>
							<p id="c_${dto.c_Seq}">${dto.c_content}</p>				
							<ul>
								<c:if test="${sessionScope.member_Id == dto.c_Id}">
									<li>
										<button onclick="javascript:editComment(${dto.c_Seq});" class="btn btn-default btn-xs">수정</button>
									</li>
									<li>
										<button onclick="javascript:deleteComment(${dto.c_Seq});" class="btn btn-default btn-xs">삭제</button>
									</li>
								</c:if>
								<c:if test="${sessionScope.member_Id != null}">
									<c:if test="${dto.c_depth eq 0}">
										<li class="re_reply">
											<button onclick="javascript:addComment(${dto.c_Seq});" class="btn btn-default btn-xs">댓글</button>
										</li>
									</c:if>
									<c:if test="${dto.c_depth eq 1}">
										<li class="re_reply"></li>									
									</c:if>
								</c:if>
							</ul>
						</li>
					</c:forEach>
 					<li class="end"></li>
				</ul>
			</div>
			<div class="r_input">
				<input type="hidden" id="session" value="${sessionScope.member_Id}" />
				<input type="hidden" id="name" value="${sessionScope.member_Name}" />
				<h2>댓글</h2>
				<ul>
					<li><textarea class="r_chk" id="r_content"></textarea></li>
					<li><button id="r_write" class="btn btn-default btn-xs">등록</button></li>
				</ul>
			</div>
		</div>
	</div>

	<ul class="board-view-list">
		<li>
			<c:choose>
			    <c:when test="${prev.intSeq == null}">
					<a href="javascript:void(0)">
						<div class="prev">
							<div class="prev_txt">
								<p class="prev_txt_1">이미지 없음</p>
								<p class="prev_txt_2">이전 게시물이 없습니다</p>
							</div>
						</div>
					</a>
	    		</c:when>
		   		<c:otherwise>
					<a href="./recipe_view?page=${param.page}&intSeq=${prev.intSeq}">
						<div class="prev" style="background-image: url(${prev.recipe_Title});">
							<div class="prev_txt">
								<p class="prev_txt_1">Previous Product</p>
								<p class="prev_txt_2">${prev.recipe_Subject}</p>
							</div>
						</div>
					</a>
	 			</c:otherwise>
			</c:choose>
		</li>
		<li>
			<c:choose>
			    <c:when test="${next.intSeq == null}">
					<a href="javascript:void(0)">
						<div class="next">
							<div class="next_txt">
								<p class="next_txt_1">이미지 없음</p>
								<p class="next_txt_2">다음 게시물이 없습니다</p>
							</div>
						</div>
					</a>
	   			</c:when>
	    		<c:otherwise>
					<a href="./recipe_view?page=${param.page}&intSeq=${next.intSeq}">
						<div class="next" style="background-image: url(${next.recipe_Title});">
							<div class="next_txt">
								<p class="next_txt_1">Next Product</p>
								<p class="next_txt_2">${next.recipe_Subject}</p>
							</div>
						</div>
					</a>
				</c:otherwise>
			</c:choose>
		</li>
	</ul>

	<script type="text/javascript" src="${pageContext.request.contextPath}/js/cook.js"></script>
</div>

<jsp:include page="../footer.jsp" />