<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<jsp:include page="../header.jsp" />

<div class="member_wrapper clos">
	<div class="wrap-loading display-none">
	    <div>
	    	<img src="../resources/image/icon/loading.gif" />
	    </div>
	</div>

	<div class="member">
		<div id="info" class="info">
			<div id="user" class="user">
				<div class="info_01">
					<ul>
						<li>
							<div class="user_action">
								<dt id="menu_slide" class="menu">
									<div class="menu_view">
										<img src="../resources/image/icon/menu.png" alt="" />
										<span class="txt">메뉴</span>
									</div>
								</dt>
								<dd>
									<div class="member_menu" onClick="location.href='/info/info_payment'">
										<i class="menu_icon"><img src="../resources/image/menu/product_off.png" alt="" /></i>
										<span class="menu_name">주문관리</span>
									</div>
									<div class="member_menu" onClick="location.href='/info/cart'">
										<i class="menu_icon"><img src="../resources/image/menu/cart_off.png" alt="" /></i>
										<span class="menu_name">장바구니</span>
									</div>
									<div class="member_menu" onClick="location.href='/info/info_qna'">
										<i class="menu_icon"><img src="../resources/image/menu/question_off.png" alt="" /></i>
										<span class="menu_name">상품 QnA</span>
									</div>
									<div class="member_menu" onClick="location.href='/info/review_all'">
										<i class="menu_icon"><img src="../resources/image/menu/opinion_off.png" alt="" /></i>
										<span class="menu_name">고객 상품평</span>
									</div>
									<div class="infoUpdate member_menu" onClick="location.href='/info/info_update'">
										<i class="menu_icon"><img src="../resources/image/menu/contact_off.png" alt="" /></i>
										<span class="menu_name">정보수정</span>
									</div>
									<div class="pwdUpdate member_menu" onClick="location.href='/info/pwd_update'">
										<i class="menu_icon"><img src="../resources/image/menu/password_off.png" alt="" /></i>
										<span class="menu_name">비밀번호 변경</span>
									</div>
									<div class="logout member_menu" onClick="location.href='/member/logout'">
										<i class="menu_icon"><img src="../resources/image/menu/logout_off.png" alt="" /></i>
										<span class="menu_name">로그아웃</span>
									</div>
								</dd>
							</div>
						</li>									
					</ul>
				</div>

				<script type="text/javascript" src="${pageContext.request.contextPath}/js/until/info_animate.js"></script>
			</div>
		
			<div class="dashboard_list">
				<div class="main">
