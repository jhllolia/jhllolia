jQuery.noConflict();

jQuery(function($) {
	$(document).ready(function() {

		/* ======================================== */
		$( window ).resize(function() {
			var m_width = $( window ).width();

			subMenu(m_width);
		}).resize();

		/* ======================================== */
		function subMenu(width) {
			var container = $('#info').outerHeight();
			var position = parseInt($('#user').css('top'));	
			var y_pos = $('#user'), slide =	y_pos.offset().top;

			$(window).scroll(function(e) {
				var scroll = $(this).scrollTop();

				if(scroll + $(window).height() < $(document).height()) {
					if(scroll > slide) {
						y_pos.removeClass('down').addClass('up');
					} else if(scroll != slide) {
						y_pos.removeClass('up').addClass('down');
					}
				}
			}).scroll();
		}

		/* ==================== #,### ==================== */
		function numberFormat(inputNumber) {
			return inputNumber.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
		}
	});
});
