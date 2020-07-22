jQuery.noConflict();

jQuery(function($) {
	$(document).ready(function() {
		
		$('button[id="updateRev"]').click(function() {
			fn_review_update($(this));
		});

		$('button[name="revView"]').click(function() {
			fn_review_View($(this));
		});
		
		$('button[name="deleteRev"]').click(function() {
			fn_review_none_exposure($(this));
		});

		/* ================== 삭제 ================== */
		function fn_review_none_exposure(obj) {
			var form = "";
			var idx = obj.attr('data-idx');

			if(confirm("비노출 처리하시겠습니까?") == true) {
				form += "<form action='/rev/delete' method='POST'>"; 
				form += "	<input type='hidden' name='idx' value='" + idx + "' />";
				form += "</form>";

				$(form).appendTo("body").submit().remove();		// 조회
			}
		}
		
		/* ================== 수정 ================== */
		function fn_review_update(obj) {
			var form = "";
			var idx = obj.attr('data-idx');
			var content = $('#adminComment').val();

			if(!idx || !content) {
				alert("내용을 입력해주세요.");
				return false;
			}

			form += "<form action='/rev/admin' method='POST'>"; 
			form += "	<input type='hidden' name='idx' value='" + idx + "' />";
			form += "	<input type='hidden' name='content' value='" + content + "' />";
			form += "</form>";

			$(form).appendTo("body").submit().remove();		// 조회
		}

		/* ================== 리뷰 상세 ================== */
		function fn_review_View(obj) {
			var form = "";
			form += "<form action='/rev/content' method='POST'>"; 
			form += "	<input type='hidden' name='orderNum' value='" + obj.attr('orderNum') + "' />";
			form += "	<input type='hidden' name='rev_id' value='" + obj.attr('rev_id') + "' />";
			form += "	<input type='hidden' name='prdNum' value='" + obj.attr('prdNum') + "' />";
			form += "	<input type='hidden' name='prdName' value='" + obj.attr('prdName') + "' />";
			form += "	<input type='hidden' name='prdOpt' value='" + obj.attr('prdOpt') + "' />";
			form += "	<input type='hidden' name='prdQty' value='" + obj.attr('prdQty') + "' />";
			form += "</form>";

			$(form).appendTo("body").submit().remove();		// 조회
		}

		/* ================== 객체 존재 여부 ================== */
		function isEmpty(obj) {
			return Object.keys(obj).length === 0;
		}

		/* ================== 콤마 세자리 ================== */
		function numberFormat(inputNumber) {
			return inputNumber.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
		}
		
	});
});