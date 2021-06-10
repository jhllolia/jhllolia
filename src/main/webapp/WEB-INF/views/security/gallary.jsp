<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<s:authorize ifNotGranted="ROLE_ADMIN">
	<p>로그아웃</p>
</s:authorize>

<s:authorize ifAnyGranted="ROLE_ADMIN">

<jsp:include page="./admin.jsp" />

<li id="menu_result">
	<div class="adMain">
		<div class="seat_container">
			<div class="seat">
				<h2>갤러리 리스트</h2>
				<hr />

				<table class="table table-bordered">
					<thead class="thead-dark">
						<tr>
      						<th scope="col">PC_SIZE</th>
      						<th scope="col">MOBILE_SIZE</th>
      						<th scope="col">TITLE</th>
							<th scope="col">ORD</th>
    					</tr>
					</thead>
					<tbody id="sortable">
						<c:choose>
							<c:when test="${fn:length(list) == 0}">
								<tr>
									<td colspan="4" text-align="center">검색 결과가 없습니다.</td>
								</tr>
							</c:when>
							<c:otherwise>

								<c:forEach items="${list}" var="dto" varStatus="status">
									<tr name="clickGallary" data-idx="${dto.img_idx}" data-title="${dto.title}" data-web="${dto.web_image}" data-mob="${dto.mob_image}">
										<td><img src="../resources/upload/gallary/${dto.web_image}" alt="" width="100" /></td>
										<td><img src="../resources/upload/gallary/${dto.mob_image}" alt="" width="100" /></td>
										<th scope="row">${dto.title eq '' ? '##' : dto.title}</th>
				      					<td>${dto.ord}</td>
									</tr>				
								</c:forEach>
							</c:otherwise>
						</c:choose>
					</tbody>
				</table>

				<form id="f" name="f" method="POST" action="/gallary/proc" enctype="multipart/form-data">
					<input type="hidden" name="img_idx" id="img_idx" value="" />
					<input type="hidden" name="web_image" id="web_image" value="" />
					<input type="hidden" name="mob_image" id="mob_image" value="" />
					<input type="hidden" name="del_yn" id="del_yn" value="N" />

					PC : 1920 x 781<br/>
					MOBILE : 750 x 866<br/>

					<table class="table table-bordered">
						<thead class="thead-dark">
							<tr>
	      						<th scope="col">큰 이미지 사이즈(pc)</th>
	      						<th scope="col">작은 이미지 사이즈(mobile)</th>
								<th scope="col">텍스트(필수 x)</th>
								<th scope="col"></th>
	    					</tr>
						</thead>
						<tbody>
							<tr>
								<th>
									<input type="text" class="form-control my-2" name="pcWidth" id="pcWidth" value="1050" />
									<input type="text" class="form-control my-2" name="pcHight" id="pcHight" value="700" />
								</th>
								<th>
									<input type="text" class="form-control" name="mobWidth" id="mobWidth" value="690" />
									<input type="text" class="form-control" name="mobHight" id="mobHight" value="460" />
								</th>
								<th>
									<input type="text" class="form-control" id="title" name="title" value="" />
								</th>
	      						<td>
									<img src="../resources/image/icon/none.png" id="output" class="img-fluid img-thumbnail" width="100">
									<input type="file" name="files" id="imageFile" class="form-control" />

									<div class="bg-secondary">
			      						<button class="btn btn-danger btn-block" id="sendGallary">등록/수정</button>
										<button class="btn btn-danger btn-block" id="deleteGallary">삭제</button>
									</div>
	      						</td>
							</tr>
						</tbody>
					</table>
				</form>

				<script type="text/javascript" src="https://code.jquery.com/ui/1.12.1/jquery-ui.js" ></script>
				<script type="text/javascript" src="${pageContext.request.contextPath}/js/product/fn_gallary.js"></script>
			</div>
		</div>
	</div>
</li>

</s:authorize>
