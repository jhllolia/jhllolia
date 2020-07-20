jQuery.noConflict();

jQuery(function($) {
	$(document).ready(function() {

		/* ============================================ */
		$('a[name="qnaContent"]').click(function(e) {
			e.preventDefault();

			$('tr.qna_view').addClass('off');
			var $this = $(this).closest('tr').next('tr.qna_view');

			if($this.hasClass('on')) {
				$this.addClass('off').removeClass('on');
			} else {
				$this.removeClass('off').addClass('on');
			}
		});

		/* ============================================ */
		$('input[name="qnaState"]').click(function(e) {
			adminStateChange($(this));
		});

		/* ============================================ */
		$('button[name="sendPost"]').click(function(e) {
			adminSendReply($(this));
		});

		/* ============================================ */
		function adminStateChange(obj) {
			var num = obj.attr('data-num');
			var state = obj.attr('data-check');

			$.ajax({
				type: "POST",
				url	: "/qnaState/" + num + "/" + state + "",
				processData: false,
				contentType: false,
				success : function(data) {
					location.reload();
				}, error: function(error) {
					console.log(error);
				}
			});
		}

		/* ============================================ */
		function adminSendReply(obj) {
			var formData = new FormData();
			var content = obj.prevAll('textarea[name="qContent"]').val();
			content = content.replace(/(?:\r\n|\r|\n)/g, '<br />');

			if(!content) {
				alert("글을 입력해주세요");
				$('textarea[name="qContent"]').focus();
				return false;
			} else {

				formData.append("num", obj.attr("data-seq"));
				formData.append("content", content);

				$.ajax({
					type: "POST",
					url	: "/qna/sendAdmin",
					dataType : 'json',
					data : formData,
					processData: false,
					contentType: false,
					success : function(data) {
						location.reload();
					}, beforeSend: function() {

					}, complete: function() {

					}, error: function(error) {
						console.log(error);
					}
				});
			}
		}

		/* ============================================ */
	});
});
