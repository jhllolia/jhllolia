<%@ page import="org.springframework.security.core.Authentication" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="s" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<jsp:include page="../header.jsp"></jsp:include>

<div class="error_wrap">
	<div class="error">
		<div class="tit">
			<h1>ERROR 404</h1>
			<p class="error_txt">페이지를 표시할 수 없습니다.</p> 
			<button name="error_back" onClick="location.href='../main'" class="btn log">메인페이지로 이동</button>
		</div>
	</div>
</div>

<jsp:include page="../footer.jsp"></jsp:include>