jQuery.noConflict();

jQuery(function($) {
	$(document).ready(function() {

		window.onload = function() {
			$( window ).resize(function() {
				var m_width = $( window ).width();	// Mobile
				mobileImage(m_width);
			}).resize();
		}

		var w_width = $( window ).width();	// WEB	
		mobileImage(w_width);
		
		function mobileImage(width) {
			if(width <= 700) {
				$('.other ul').find('#kakako').find('img').attr('src','../resources/image/main/kakao_login_mobile.png');
				$('.other ul').find('#naver').find('img').attr('src','../resources/image/main/naver_login_mobile.PNG');
			} else {
				$('.other ul').find('#kakako').find('img').attr('src','../resources/image/main/kakao_login.png');
				$('.other ul').find('#naver').find('img').attr('src','../resources/image/main/naver_login.PNG');
			}
		}
	});
});