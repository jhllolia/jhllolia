<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="ko"> 
<head>
<!-- Global site tag (gtag.js) - Google Analytics -->
<script async src="https://www.googletagmanager.com/gtag/js?id=UA-162325890-1"></script>
<script>
	window.dataLayer = window.dataLayer || [];
	function gtag(){dataLayer.push(arguments);}
	gtag('js', new Date());
	gtag('config', 'UA-162325890-1');
</script>

<title>어양토속식품</title>

<meta charset="UTF-8">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<meta name="HandheldFriendly" content="true" />

<meta name="description" content="속 꽉 찬 진한 맛 전주 토속 찰순대,피순대,김치순대,막창순대 전문 쇼핑몰">
<meta property="og:type" content="website">
<meta property="og:title" content="어양토속식품">
<meta property="og:description" content="속 꽉 찬 진한 맛 전주 토속 찰순대,피순대,김치순대,막창순대 전문 쇼핑몰">
<meta property="og:image" content="https://www.tosok.co.kr/resources/image/icon/footer_logo.png">
<meta property="og:url" content="https://www.tosok.co.kr">

<link rel="apple-touch-icon" sizes="57x57" href="/resources/1a8b107503de7f4cdc8ba8b197facd1a.ico/apple-icon-57x57.png">
<link rel="apple-touch-icon" sizes="60x60" href="/resources/1a8b107503de7f4cdc8ba8b197facd1a.ico/apple-icon-60x60.png">
<link rel="apple-touch-icon" sizes="72x72" href="/resources/1a8b107503de7f4cdc8ba8b197facd1a.ico/apple-icon-72x72.png">
<link rel="apple-touch-icon" sizes="76x76" href="/resources/1a8b107503de7f4cdc8ba8b197facd1a.ico/apple-icon-76x76.png">
<link rel="apple-touch-icon" sizes="114x114" href="/resources/1a8b107503de7f4cdc8ba8b197facd1a.ico/apple-icon-114x114.png">
<link rel="apple-touch-icon" sizes="120x120" href="/resources/1a8b107503de7f4cdc8ba8b197facd1a.ico/apple-icon-120x120.png">
<link rel="apple-touch-icon" sizes="144x144" href="/resources/1a8b107503de7f4cdc8ba8b197facd1a.ico/apple-icon-144x144.png">
<link rel="apple-touch-icon" sizes="152x152" href="/resources/1a8b107503de7f4cdc8ba8b197facd1a.ico/apple-icon-152x152.png">
<link rel="apple-touch-icon" sizes="180x180" href="/resources/1a8b107503de7f4cdc8ba8b197facd1a.ico/apple-icon-180x180.png">
<link rel="icon" type="image/png" sizes="192x192"  href="/resources/1a8b107503de7f4cdc8ba8b197facd1a.ico/android-icon-192x192.png">
<link rel="icon" type="image/png" sizes="32x32" href="/resources/1a8b107503de7f4cdc8ba8b197facd1a.ico/favicon-32x32.png">
<link rel="icon" type="image/png" sizes="96x96" href="/resources/1a8b107503de7f4cdc8ba8b197facd1a.ico/favicon-96x96.png">
<link rel="icon" type="image/png" sizes="16x16" href="/resources/1a8b107503de7f4cdc8ba8b197facd1a.ico/favicon-16x16.png">
<link rel="manifest" href="/resources/1a8b107503de7f4cdc8ba8b197facd1a.ico/manifest.json">
<meta name="msapplication-TileColor" content="#f0ae44">
<meta name="msapplication-TileImage" content="/resources/1a8b107503de7f4cdc8ba8b197facd1a.ico/ms-icon-144x144.png">
<meta name="theme-color" content="#f0ae44">

<link href="https://fonts.googleapis.com/css?family=Black+Han+Sans&display=swap&subset=korean" rel="stylesheet">
<link rel="stylesheet" href="/resources/css/main.css?ver=<%=System.currentTimeMillis() %>" />
<link rel="stylesheet" href="/resources/css/swiper.css?ver=<%=System.currentTimeMillis() %>" />
<link rel="stylesheet" href="/resources/css/sub.css?ver=<%=System.currentTimeMillis() %>" />
<link rel="stylesheet" href="/resources/css/admin.css?ver=<%=System.currentTimeMillis() %>" />
<link rel="stylesheet" href="/resources/css/menu.css?ver=<%=System.currentTimeMillis() %>" />
<link rel="stylesheet" href="/resources/css/bootstrap/bootstrap.css?ver=<%=System.currentTimeMillis() %>" />
<link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css" type="text/css" />

<!--
<script type="text/javascript" src="http://code.jquery.com/jquery-3.2.0.min.js"></script>
-->

<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-2.1.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/main.js?ver=<%=System.currentTimeMillis() %>"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/member.js?ver=<%=System.currentTimeMillis() %>"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsrender.min.js?ver=<%=System.currentTimeMillis() %>"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/swiper.min.js?ver=<%=System.currentTimeMillis() %>"></script>

</head>

<div class="ad_main">
	<div class="ad_container">
		<div class="weather">
			<ul>
				<li><p class="tit">전북 익산시 춘포면 현재기온</p></li>
				<li><p class="pop"><span>강수 확률</span>${weather.pop} %</p></li>
				<li><p class="temp"><span>온도</span>${weather.temp} ℃</p></li>
				<li><p class="reh"><span>습도</span>${weather.reh} %</p></li>
				<li><p class="wfKor"><span>날씨</span>${weather.wfKor}</p></li>
			</ul>
		</div>

		<div class="email">
			<a href="../terms/counsel" class="link">
				<img src="../resources/image/icon/arrow.png" alt="" />
				<span class="txt">E-mail 고객센터</span>
			</a>
		</div>
	</div>
</div>

<header class="header">
	<div class="header_sub">
		<div class="spinner-master">
			<input type="checkbox" id="spinner-form" />
			<label for="spinner-form" class="spinner-spin">
				<div class="spinner diagonal part-1"></div>
				<div class="spinner horizontal"></div>
				<div class="spinner diagonal part-2"></div>
			</label>
		</div>

		<div class="spinner-master-main">
	 		<div class="mem_menu">
				<ul>
					<s:authorize ifAnyGranted="ROLE_ADMIN">
						<li><a href="${pageContext.request.contextPath}/admin">관리자모드</a></li>
					</s:authorize>

					<c:choose>
						<c:when test="${sessionScope.member_Id == null}">
							<li class="member_login">
								<label name="lct" onClick="location.href='${pageContext.request.contextPath}/member/login'">
									<img class="link_icon" src="../resources/image/main/login.png" alt="" />
									<span class="txt">로그인</span>
								</label>
							</li>
							<li class="member_register">
								<label name="lct" onClick="location.href='${pageContext.request.contextPath}/member/signup'">
									<img class="link_icon" src="../resources/image/main/edit.png" alt="" />
									<span class="txt">회원가입</span>
								</label>
							</li>
						</c:when>
						<c:otherwise>
							<li class="member_cart">
								<label name="lct" onClick="location.href='${pageContext.request.contextPath}/info/cart'">
									<img class="link_icon" src="../resources/image/main/shopping-cart.png" alt="" />
									<span class="txt">장바구니</span>
									<span id="cart_num" class="cart_num"></span>
								</label>
							</li>
							<li class="member_login">
								<label name="lct" onClick="location.href='${pageContext.request.contextPath}/info/info_payment'">
									<img class="link_icon" src="../resources/image/main/box.png" alt="" />
									<span class="txt">주문관리</span>
								</label>
							</li>
							<li class="member_register">
								<label name="lct" onClick="location.href='${pageContext.request.contextPath}/member/logout'">
									<img class="link_icon" src="../resources/image/main/exit.png" alt="" />
									<span class="txt">로그아웃</span>
								</label>
							</li>
						</c:otherwise>
					</c:choose>
				</ul>
			</div>
		</div>

		<div id="dimmed_layer" class="dimmed_layer">
			<nav id="topMenu" class="topMenu">
				<a class="img_main" href="/">
					<img class="header_micon" src="/resources/image/icon/footer_logo.png">
				</a>

				<ul id="MenuUl">
					<li class="topMenuLi"><a href="/intro" class="menuLink">회사 소개</a></li>
					<li class="topMenuLi"><a href="/process" class="menuLink">제조 시설</a></li>
					<li class="topMenuLi"><a href="/bbs/product" class="menuLink">판매 제품</a></li>
					<li class="topMenuLi"><a href="/bbs/recipe" class="menuLink">SPECIAL</a></li>
					<li class="topMenuLi"><a href="/business" class="menuLink">제휴 지점</a></li>
				</ul>
			</nav>
		</div>
	</div>

	<!-- 

		<div id="user" class="user">
			<c:choose>
				<c:when test="${sessionScope.member_Id == null}">
					<ul>
						<li class="noneUser"><a href="${pageContext.request.contextPath}/member/login">로그인</a></li>
						<li class="noneUser"><a href="${pageContext.request.contextPath}/member/signup">회원가입</a></li>
					</ul>
				</c:when>
				<c:otherwise>
					<div id="hasUser" class="hasUser"></div>
				</c:otherwise>
			</c:choose>
		</div>

	 -->
</header>

<body>