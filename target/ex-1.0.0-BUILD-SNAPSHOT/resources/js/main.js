jQuery.noConflict();

jQuery(function($) {
	$(document).ready(function() {

		/* ============================================== */
		/*
	    if (document.location.protocol == 'http:') {
	        document.location.href = document.location.href.replace('http:', 'https:');
		}
		*/

		/* ============================================== */
		WebFontConfig = {
							custom: {
								families: ['Black Han Sans'],
								urls: ['https://fonts.googleapis.com/css?family=Black+Han+Sans&display=swap']
							}
						};
	
		(function() {
			var wf = document.createElement('script');
			wf.src = ('https:' == document.location.protocol ? 'https' : 'http') + '://ajax.googleapis.com/ajax/libs/webfont/1.4.10/webfont.js';
			wf.type = 'text/javascript';
			wf.async = 'true';
	
			var s = document.getElementsByTagName('script')[0];
			s.parentNode.insertBefore(wf, s);
		})();
	
		/* ======================== Member Menu Hover ======================= */
		$('.member_menu').hover(function() {
			$(this).find('i.menu_icon').find('img').attr('src', $(this).find('i.menu_icon').find('img').attr('src').replace("_off","_on"));
		}, function() {
			$(this).find('i.menu_icon').find('img').attr('src', $(this).find('i.menu_icon').find('img').attr('src').replace("_on","_off"));
		});
	
		/* ======================== Member Menu Click ======================= */
		var link = $(location).attr("pathname");
	
		if(link == "/info/info_payment" || link == "/info/orderStatus" || link == "/info/orderStatus" || link == "/info/returnProduct") {
			$('.member_menu').eq(0).addClass('on');
		} else if(link == "/info/cart") {
			$('.member_menu').eq(1).addClass('on');
		} else if(link == "/info/info_qna" || link == "/info/customer") {
			$('.member_menu').eq(2).addClass('on');
		} else if(link == "/info/review_all" || link == "/info/rev_update" || link == "/info/review") {
			$('.member_menu').eq(3).addClass('on');
		} else if(link == "/info/info_update") {
			$('.member_menu').eq(4).addClass('on');
		} else if(link == "/info/pwd_update") {
			$('.member_menu').eq(5).addClass('on');
		} else {
	
		}
	
		/* ======================== Cart Count ======================= */
		cart_count();

		function cart_count() {

			$.ajax({
	    		url	: '/count/cart',
				type : 'POST',
				processData: false,
				contentType: false,
				success : function(data) {
					$('.cart_num').html(data);
				}, error: function(error) {
					console.log(error);
				}
			});
		}
	
		/* ======================== member menu slide ======================= */
		$('dt#menu_slide').click(function() {
			if($(this).next('dd').hasClass('on') == true) {
				$(this).removeClass('on').next('dd').removeClass('on');
			} else {
				$(this).addClass('on').next('dd').addClass('on');
			}
		});
	
		/* ======================== board hover ======================= */
		$('.main_category > ul > li.main_icon').hover( function() {
			$(this).children('.item').find('img').css('transform','scale(1.2)');
		}, function() {
			$(this).children('.item').find('img').css('transform','scale(1)');
		});
	
		/* ======================== 사용자 정보 ======================= */
		/*
	
		mobileInfo();
	
		function mobileInfo() {
			var id = $('div#mobileUser').attr('name');
	
			if( id != "" ) {
				$.ajax({
		    		url	: '/menuInfo/' + id,
					type : 'POST',
					processData: false,
					contentType: false,
					success : function(data) {
						var html = "";
	
						html += "<article name='profile'>";
						html += "	<a href='../info/member_Info'>";
	
						if(data.member_Profile == null || data.member_Profile == "") {
							html += "	<img class='none' src='../resources/image/icon/profile.png' alt='' />";						
						} else {
							if(data.member_Way == "Y") {
								html += "<img class='has' src='../resources/upload/profile/" + data.member_Id + "/" + data.member_Profile + "' alt='' />";						
							} else {
								html += "<img class='has' src='" + data.member_Profile + "' alt='' />";						
							}
						}
	
						html += "	</a>";
						html += "</article>";
						html += "<article name='info'>";
						html += "	<a href='../info/member_Info'>";
	
						if(data.member_Way == "kakao") {
							html += "	<p class='k_login'>카카오 회원</p>";						
						} else if(data.member_Way == "naver") {
							html += "	<p class='n_login'>네이버 회원</p>";			
						} else {
							html += "	<p class='t_login'>일반 회원</p>";						
						}
	
						html += "		<p class='name'>" + data.member_Name + " 님</p>";			
						html += "	</a>";
	
						html += "	<p class='logout'><a href='../member/logout'>로그아웃</a></p>";		
						html += "</article>";
	
						$('#hasUser').append(html);
					}, beforeSend: function() {
	
					}, complete: function() {
	
					}, error: function(error) {
						alert(error);
					}
				});
			}
		}
		
		*/
	
		/* ======================== main slider  ======================= */
		var intv;
		var current = 0;
		var sIdx = 0;
		var sCnt = 4;
	
		width = $(window).width();
	
		function startTopSlider() {
			intv = setInterval(function () {
				$("#ul_btns").find("li").eq(current++ % sCnt).click();
			}, 10000000);
		}
		
		function setBnr(idx, bnr, allTab, addCls) {
			$(bnr).css("opacity", "0").eq(idx).css("opacity", "1");
			$(allTab).removeClass(addCls);
			$(allTab).eq(idx).addClass(addCls);
		}
	
		$("#top_0").css("opacity", "1");
		$("#btn_0").addClass("on");
		startTopSlider();
	
		$("#ul_btns").find("li").click(function() {
			var idx = $(this).attr("id").split("_")[1];
			var bnr = $("#TopSlider > div");
			var allTab = $("#ul_btns > li");
	
			setBnr(idx, bnr, allTab, "on");
		});
	
		/* ======================== Menu Control ======================= */
		var size = window.outerWidth;	// Size
	
		pageFind(size);					// PageFind
		topMenu(size);					// TopMenu
	
		$(window).resize(function() {
			var width = window.outerWidth;
	
			pageFind(width);
			topMenu(width);
		});
	
		/* ======================== Top Menu ======================= */
		function topMenu(width) {
			$('.spinner-master').off('click').on('click', function(e) {
				$('#dimmed_layer').css('display','block');
				$('#topMenu').stop().animate({ "left" : "0%" }, 300);
	
				// menu open
				$('#topMenu > ul > li > a').off('click').on('click', function(e) {
				    var t = $(this);
				    t.next('ul').toggleClass('hover');
				});
	
				// menu close
				$('#dimmed_layer').click( function(e) {
					var $e_target = $(e.target);
					var $target_layer = $e_target.hasClass('dimmed_layer');
					if( $target_layer == true ) {
						menuClose(width);
					}
				});
	
				// menu close
				$('.topMenu_close').click(function() {
					menuClose(width);
				});
			});
		}
	
		/* ======================== Menu Close ======================= */
		function menuClose(width) {
			$('#topMenu').stop().animate({ "left" : "-80%" }, 300).find('ul').removeClass('hover');
			$('#dimmed_layer').fadeOut(500);
		}
	
		/* ======================== page Find ======================= */
		function pageFind(width) {
			$('#slideMenu a').click( function(e) {
				var headerHeight = $('header').outerHeight();		// Header Hight
	
				if(width < 1050) {
					menuClose(width);
				}
			});
		}

		/* ============================================== */
	
	});
});