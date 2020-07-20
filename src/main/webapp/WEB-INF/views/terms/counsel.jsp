<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<jsp:include page="../header.jsp" />

<div class="member_wrapper">
	<div class="wrap-loading display-none">
	    <div>
	    	<img src="../resources/image/icon/loading.gif" />
	    </div>
	</div>

	<div id="counsel" class="counsel">
		<div class="tit">
			<img src="../resources/image/icon/send.png" alt="" />
			<span class="txt">E-mail 문의</span>
		</div>

		<div class="content">
			<div class="tip_policy">
				<ul>
					<li name="tip">제품샘플은 금액과 배송비가 부과되며, 요청시 내용에 <span class="ti">사업자등록번호</span> / <span class="ti">핸드폰 번호</span>를 포함시켜주세요.</li>
					<li name="tip">반품/교환시 제품 특성상 반품과 교환이 불가능한 상품이 있을수도 있습니다</li>
					<li name="tip">취소/환불 요청시 고객변심이나 제품이 심하게 회손된다면 취소/환불이 불가능합니다.</li>
					<li name="tip">E-mail 문의시에는 1~2일 이내에 답변을 받으실수 있습니다.</li>
				</ul>
			</div>

			<table id="email_inquiry">
				<tbody>
					<tr>
						<th>
							<p class="in_tit"><span class="in_icon">*</span>유형</p>
						</th>
						<td>
							<select id="in_select" name="in_select" class="tit_bor">
								<option value="">선택해주세요</option>
								<option value="A">샘플 요청</option>
								<option value="B">도매전용 제품구매</option>
								<option value="C">배송</option>
								<option value="D">반품/교환</option>
								<option value="E">주문/결제</option>
								<option value="F">기타</option>
							</select>
						</td>
					</tr>
					<tr>
						<th>
							<p class="in_tit"><span class="in_icon">*</span>제목</p>
						</th>
						<td>
							<input type="text" id="in_subject" name="in_subject" class="tit_bor" placeholder="제목을 입력해주세요" />
						</td>
					</tr>
					<tr>
						<th>
							<p class="in_tit"><span class="in_icon">*</span>내용</p>
						</th>
						<td>
							<textarea name="in_content" id="in_content" class="tit_bor" maxlength="400" placeholder="" ></textarea>
							<span id="limit"></span>
						</td>
					</tr>
					<tr>
						<th>
							<p class="in_tit"><span class="in_icon">*</span>답변메일</p>
						</th>
						<td>
							<c:choose>
								<c:when test="${sessionScope.member_Id == null}">
									<div class="plz_login">
										<input type="text" name="wr_mine" class="mine_txt" placeholder="ex) test@naver.com"/>
									</div>
								</c:when>
								<c:otherwise>
									<div class="plz_login">
										<input type="text" name="wr_mine" class="hide_txt mine_txt" value="${sessionScope.member_Id}" readOnly="readOnly" />
									</div>
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
				</tbody>
			</table>

			<div class="send_mail">
				<button name="email_in" id="email_in" class="btn log">문의하기</button>
			</div>
		</div>
	</div>

	<script type="text/javascript" src="${pageContext.request.contextPath}/js/until/fn_inquiry.js"></script>
</div>

<jsp:include page="../footer.jsp" />