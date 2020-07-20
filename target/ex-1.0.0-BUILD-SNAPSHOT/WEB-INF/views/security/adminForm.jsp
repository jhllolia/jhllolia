<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="s" %>

<jsp:include page="../header.jsp" />

<c:url value="j_spring_security_check" var="loginUrl" />

	<div class="member_wrapper">
		<div id="loginbox" class="mainbox col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">                    
			<div class="panel panel-info" >
				<div class="panel-heading">
					<div class="panel-title">Sign In</div>
				</div>
			                 
				<form class="form-horizontal" action="${loginUrl}" method="post">
					<h3 class="form-signin-heading"></h3>
					<c:if test="${param.ng != null}">
						<p> ID와 비밀번호를 확인 해주세요. <br />
					<c:if test="${SPRING_SECURITY_LAST_EXCEPTION != NULL}">
						message : <c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}" />
					</c:if>
						</p>
					</c:if>

					<div style="display:none" id="login-alert" class="alert alert-danger col-sm-12"></div>
						<div style="margin-bottom: 25px" class="input-group">
							<span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
							<input type="text" class="form-control" name="j_username" placeholder="ID 입력"> <br />
						</div>
	
						<div style="margin-bottom: 25px" class="input-group">
							<span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
							<input type="password" class="form-control" name="j_password" placeholder="Password 입력"> <br />
						</div>
	
						<input type="submit" class="btn btn-lg btn-primary btn-block" value="LOGIN"> <br />
				</form>
			</div>
		</div>
	</div>

<jsp:include page="../footer.jsp" />