jQuery.noConflict();

jQuery(function($) {
	$(document).ready(function() {

		var swiper = new Swiper('.swiper-container', {

			pagination: {
				el: '.swiper-pagination',
				type: 'bullets',
				clickable : true,
			},
			
			scrollbar: {
			    el: '.swiper-scrollbar',
			    clickable: true,
			},

			autoplay: {
				delay: 3000,
			},
		});

		/* =================== =================== */
/*
		$('li[data-view^="list_"]').find('.product').hover(function() {
			$(this).find('div[data-img="img_prev"]').css('opacity','0');
			$(this).find('div[data-img="img_next"]').css('opacity','1');
		}, function() {
			$(this).find('div[data-img="img_prev"]').css('opacity','1');
			$(this).find('div[data-img="img_next"]').css('opacity','0');
		});
*/
		/* =================== =================== */
		$('li[data-view^="list_"]').find('.product').click(function() {
			var unit = $(this).attr('data-unit');

			if(unit == "" || unit == null) {
				location.reload();
			} else {
				location.href="../tab/" + unit + "";
			}
		});

		/* =================== =================== */
		$('span#open').hover(function() {
			$(this).find('img').attr('src', $(this).find('img').attr('src').replace("_off","_on"));
		}, function() {
			$(this).find('img').attr('src', $(this).find('img').attr('src').replace("_on","_off"));
		});

		/* =================== =================== */
		$('span#open').click(function() {
			$('span#open').next('.list_goods').removeClass('on');
			$(this).next('.list_goods').addClass('on');
		});

		$('#product_list').load('../bbs/product #sale_items');
	});
});