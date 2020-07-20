<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<jsp:include page="header.jsp" />

<div class="main">
	<div class="intro_main">
		<section id="img_slide" class="hero image bg-image">
			<div class="intro_txt">
				<p class="intro_txt_1">대한민국의 전통음식 "순대"</p>	
			</div>
		</section>
		<p class="intro_txt_2">대한민국을 대표하는 음식 "순대",  대한민국을 대표하는 식품 "어양토속순대"</p>

		<ul class="intro">
			<li class="pro_main_2" />
			<li>
				<p class="pro_main_1_txt">HISTORY</p>
				<p class="pro_main_2_txt">
					94년 3월 성일종합식품 근무<br />
					99년 9월 순대 국밥 재료 - 성일부산물 개업, 유통<br />
					15년 1월 어양토속순대 인수<br />
					15월 4일 성일부산물, 어양토속순대 합병 -> 어양토속식품으로 개명<br />
					17년 10월 HACCAP 인증 획득과 동시에 매출 신장<br />
					현재 전라북도 주 거래처에서 각종 식자재, 일반 마트에 공급
				</p>
				<p class="pro_main_3_txt">온가족이 먹을 수 있는 순대!</p>
				<p class="pro_main_4_txt">
					공장 내부는 물론이고, 만든 순대에 이상이 없는지 기계로 결함 판단을 하고<br />
					식품을 안전하게 관리하여 HACCAP 인증마크도 가지고 있으며 자체 위생 검사까지 <br />
					철저하게 하며 유통기한을 철저하게 자가 품질 검사에 힘을 쏟고 있습니다.<br />
					우리의 생활 속에 언제나 스며들어 있는 다소비 식품 중 하나인 순대<br /> 
					맛과 품질과 정성을 다해서 끊임없는 열정과 노력을 아끼지 않겠습니다.<br />
				</p>
				<p class="pro_main_5_txt">"좋은 원료는 좋은 제품을 만들고 정성을 다하면 좋은 맛을 낸다."</p>
			</li>
		</ul>
	
		<div class="stf_intro">
			<p class="stf_txt_1">거짓없이 맛으로 승부하는 "<span>어양토속순대</span>"</p>
			<hr />
			<p class="pro_main" />
			<p class="food_main_1" />
			<p class="pro_main_1" />
			<p class="food_main_2" />
		</div>
	</div>

	<div class="direction">
		<p class="direction_txt">어양토속순대 <span>오시는길</span></p>
		<hr />
		<p class="direction_txt_2">전북 익산시 춘포면 화신길 49</p>

		<div id="map" style="width:100%;height:450px;"></div>
		<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=8e17e941ff8ec2fc7c002f7273b50ae2&libraries=services,clusterer,drawing"></script>
		<script type="text/javascript">
			jQuery.noConflict();

			jQuery(function($) {
				$(document).ready(function() {

					var mapContainer = document.getElementById('map'),
						mapOption = {
							center: new kakao.maps.LatLng('35.912231', '127.007618'),
							level: 7
						};

					var map = new kakao.maps.Map(mapContainer, mapOption);

					var iwContent = '<div class="mapWrap">' +
										'<div class="info">' +
											'<div class="title">' +
												'<h3>어양토속순대</h3>' +
											'</div>' +
										'</div>' +
										'<div class="map_txt">' +
											'<ul>' +
												'<li>' +
													'<img src="../resources/image/icon/favicon_logo.png" alt="favicon_logo" />' +
												'</li>' +
												'<li>' +
													'<p class="map_txt_1"><span>대표전화</span> : (063) 841-9282, 842-8300</p>' +
													'<p class="map_txt_2"><span>팩스번호</span> : (063) 841-9583</p>' +
												'</li>' +
											'</ul>' +
										'</div>' +
									'</div>',
						iwPosition = new kakao.maps.LatLng('35.8982935', '127.0079837'),
						iwRemoveable = true;

					var marker = new kakao.maps.Marker({
						map: map,
						position: iwPosition
					});

					var infowindow = new kakao.maps.InfoWindow({
						map : map,
						position : iwPosition, 
						content : iwContent,
						removable : iwRemoveable
					});
				});
			});
		</script>
	</div>

	<script type="text/javascript" src="${pageContext.request.contextPath}/js/until/fn_slide.js"></script>
</div>

<jsp:include page="footer.jsp" />