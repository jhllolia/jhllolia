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

	<div class="search">
		<h2>비밀번호 찾기</h2>
		<div class="search_main">
			<div class="input-wrap">
				<span>
					<input type="text" name="member_Id" id="member_Id" placeholder="이메일을 입력 해주세요.">
				</span>
			</div>

			<div class="alert alert-danger" id="danger"></div>
			
			<div class="chk_search">
				<span>*</span>
				임시 비밀번호는 가입시 등록한 메일 주소로 알려드리게 됩니다. <br />
				전송되는 이메일을 정확하게 작성해주시기 바랍니다.
			</div>

			<button id="btn_search" class="btn log">비밀번호 찾기</button>

			<jsp:include page="./link.jsp" />
		</div>
	</div>
</div>

<jsp:include page="../footer.jsp" />