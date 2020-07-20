jQuery.noConflict();

jQuery(function($) {
	$(document).ready(function() {

		/* ====================  ===================== */
		$('button[name="email_in"]').click(function() {
			send_email_inquiry();
		});

		function send_email_inquiry() {
			var formData = new FormData();
	    	var regExp = /([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/; // email check
			var sct = $('select[name="in_select"] option:selected').val();
			var tit = $('input[name="in_subject"]').val();
			var txt = $('textarea[id="in_content"]').val();
			var mail = $('input[name="wr_mine"]').val();
			txt = txt.replace(/(?:\r\n|\r|\n)/g, '<br />');

			if(!sct) {
				alert("문의 유형을 선택해주세요.");
				$('select[name="in_select"]').focus();
				return false;
			}

			if(!tit) {
				alert("문의 제목을 입력해주세요.");
				$('input[name="in_subject"]').focus();
				return false;
			}

			if(!txt) {
				alert("문의 내용을 입력해주세요.");
				$('textarea[id="in_content"]').focus();
				return false;
			}
			
			if(!mail) {
				alert("답변받을 이메일을 입력해주세요.");
				$('input[name="wr_mine"]').focus();
				return false;
			} else if(!regExp.test(mail)) {
				alert("이메일 형식을 맞춰주세요");
				$('input[name="wr_mine"]').focus();
				return false;
			} else {

				if(confirm("문의 메일을 보내시겠습니까? 마지막으로 답변받을 이메일을 확인해주세요.") == true) {
					formData.append("sct", sct);
					formData.append("tit", tit);
					formData.append("txt", txt);
					formData.append("mail", mail);

					$.ajax({
						type: "POST",
						url	: "/terms/request",
						dataType : 'json',
						data : formData,
						processData: false,
						contentType: false,
						success : function(data) {
							if(data != 0) {
								alert("메일 전송이 완료되었습니다. 문의 답변은 작성된 메일로 전송이 되며 1~2일 이내로 답변을 받으실 수 있습니다.");
								history.back();
							} else {
								alert("잘못된 접근입니다");
								location.reload();
							}
						}, beforeSend: function() {
							resultBefore();
						}, complete: function() {
							
						}, error: function(error) {
							console.log(error);
						}
					});
				}
			}
		}

		/* ======================= 결과 출력 전 =========================== */
	    function resultBefore() {
	    	var html = "";

			html += '<div>';
			html += '	<img src="../resources/image/icon/loading.gif" />';
			html += '</div>';

			$('.wrap-loading').removeClass('display-none');		// 
			$('.wrap-loading').append(html);					// 
	    }

		/* ==================== 리뷰 글 제한 ===================== */
		$('textarea[name="in_content"]').keyup(function(e) {
			var content = $(this).val();
			$(this).next('span#limit').html(content.length + '/400');
		});

		$('textarea[name="in_content"]').keyup();

	});
});