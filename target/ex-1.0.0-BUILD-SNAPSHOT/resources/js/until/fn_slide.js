jQuery.noConflict();

jQuery(function($) {
	$(document).ready(function() {

		window.onload = function () {
			$('#img_slide').addClass('ready');
			$('.intro_main > section > .intro_txt').css('opacity','1');
			$('.process > section > .process_main').css('opacity','1');
			$('.business > section > .business_main').css('opacity','1');
		}

	});
});