<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<footer class="footer">
	<div class="policy">
		<ul>
			<li><a href="../terms/termsUse" class="link">이용약관</a></li>
			<li><a href="../terms/termsPrivacy" class="link">개인정보보호</a></li>
			<li><a href="../terms/counsel" class="link">E-mail 고객센터</a></li>
		</ul>
	</div>

	<div class="policy">
		<a href="../main" class="logo">
			<img src="/resources/image/icon/footer_logo.png" alt="어양토속순대" />
		</a>

		<div class="foot_txt"> 
	 		<p class="txt">
	 			<span class="comm">상호명 : 어양토속식품</span>
	 			<span class="comm">전북 익산시 춘포면 화신길 49 <span class="father">대표 전병준</span></span>
	 		</p>
	 		<p class="txt">
	 			<span class="comm">사업자등록번호 : 403 - 15 - 94623</span>
	 			<span class="comm">통신판매업신고 : 제 2004-전북익산-184 호</span>
	 		</p>
	 		<p class="txt">
	 			<span class="comm">대표전화 : (063) 841-9582, 842-8300</span>
	 			<span class="comm">팩스번호 : (063) 841-9583</span>
	 		</p>
	 		<p class="txt">
	 			<span class="comm">이메일 : jeom6942@naver.com</span>
		 		<span class="comm today">오늘 : <fmt:formatNumber value="${sessionScope.todayCount}" pattern="#,###" /></span>
				<span class="comm total">전체 : <fmt:formatNumber value="${sessionScope.totalCount}" pattern="#,###" /></span>
	 		</p>
		</div>
	</div>
</footer>

<script type="text/javascript" src="//wcs.naver.net/wcslog.js"> </script> 
<script type="text/javascript"> 
	if (!wcs_add) var wcs_add={};
	wcs_add["wa"] = "s_2d9e04a65061";
	if (!_nasa) var _nasa={};
	wcs.inflow();
	wcs_do(_nasa);
</script>
</body>
</html>