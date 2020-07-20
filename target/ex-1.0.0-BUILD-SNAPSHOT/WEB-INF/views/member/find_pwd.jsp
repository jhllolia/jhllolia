<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<jsp:include page="../header.jsp" />

<div class="member_wrapper">
	<div class="search">
		<div class="search_main">
			<div class="find_pwd">
				<h2>비밀번호 찾기를 위한 메일이 발송되었습니다.</h2>
				<p class="find_pwd_txt_1">
					${param["send_email"]} 으로 임시 비밀번호가 담긴 메일을 보내드렸습니다. <br />
					임시비밀번호를 발급 받은 후에 <span>내정보 -> 비밀번호 변경</span> 으로 이동해서 변경해주세요.
				</p>
				<p class="find_pwd_txt_2">
					<span>*</span>메일이 보이지 않으면 스팸보관함을 열어보시길 바랍니다.
				</p>
			</div>

			<jsp:include page="./link.jsp" />
		</div>
	</div>
</div>

<jsp:include page="../footer.jsp" />