jQuery.noConflict();

jQuery(function($) {
	$(document).ready(function() {

		/* ================== 이미지 존재 x ================ */
		$("img").each(function(i, ele) {
			var uri = "../resources/image/icon/none.png";

			if(ele.src != '' && ele.complete == true && ele.naturalWidth == 0 ) {
	            $(this).attr("src", uri).css("padding", "10px");
			}

			$(this).load(function(n) {

			}).error(function() {
	            $(this).attr("src", uri).css("padding", "10px");
			});
		});

		/* ==================  ================ */
		$('button[name="productQnA"]').click(function(e) {
			product_QnA($(this));
		});

		$('button[name="qUpdate"]').click(function(e) {
			qnaContentUpdate($(this));
		});

		$('button[id="sendData"]').click(function(e) {
			sendQuestionData();
		});

		/* ============================================ */
		$('label[name="question_select"]').click( function(e) {
			if($(this).find("input:radio[name='boxId']:checked").val() == "PRODUCT_QA") {
				$('tr.view_option').css('display','table-row');
			} else {
				$('tr.view_option').css('display','none');
			}
		});

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

		/* ============================================= */
		$('textarea[name="qContent"]').keyup(function(e) {
			var content = $(this).val();
			$(this).next('span#counter').html(content.length + '/200');
		});

		$('textarea[name="qContent"]').keyup();

		/* ============================================= */		
		function sendQuestionData() {
			var formData = new FormData();
			var check = $('input:radio[name="boxId"]:checked').val();
			var select = $('select[name="sale"] option:selected').val();
			var txt = $('select[name="sale"] option:selected').text();
			var tit = $('input[name="qna_tit"]').val();
			var content = $('textarea[name="qContent"]').val();

			if(!check) {
				alert("유형을 선택해주세요.");
				return false;
			}

			if(check == "PRODUCT_QA") {
				if(!select || select == "") {
					alert("상품을 선택해주세요.");
					return false;
				} else {
					formData.append("select", select);
					formData.append("txt", txt);
				}
			}

			if(!tit || !content) {
				alert("제목과 내용을 확인해주세요");
				$('input[name="qna_tit"]').focus();
				return false;
			}

			formData.append("check", check);
			formData.append("tit", tit);
			formData.append("content", content);

			$.ajax({
				type: "POST",
				url	: "/qna/questionRes",
				dataType : 'json',
				data : formData,
				processData: false,
				contentType: false,
				success : function(data) {
					if(data == "0") {
						alert("잘못된 접근입니다.");
						location.reload();
					} else {
						alert("성공적으로 등록되었습니다.");
						location.href="../info/info_qna";
					}
				}, beforeSend: function() {
					resultBefore();
				}, complete: function() {

				}, error: function(error) {
					console.log(error);
				}
			});
		}

		/* ==================== QNA 수정(1) =================== */
		function qnaContentUpdate(obj) {
			var formData = new FormData();
			var content = obj.prevAll('textarea[name="qContent"]').val();

			if(!content) {
				alert("글을 입력해주세요");
				$('textarea[name="qContent"]').focus();
				return false;
			} else {

				formData.append("num", obj.attr("data-seq"));
				formData.append("content", content);

				$.ajax({
					type: "POST",
					url	: "/qna/userUpdate",
					dataType : 'json',
					data : formData,
					processData: false,
					contentType: false,
					success : function(data) {
						if(data == "0") {
							alert('수정에 실패했습니다. 다시 확인 해주세요');
						} else {
							alert('수정되었습니다');
						}

						location.reload();
					}, beforeSend: function() {
						resultBefore();
					}, complete: function() {

					}, error: function(error) {
						console.log(error);
					}
				});
			}
		}

		/* ==================== 제품 문의 (1) =================== */
		function product_QnA(obj) {
			var num = obj.attr('data-seq');

			$.ajax({
				type: "POST",
				url	: "/productLoad/" + num + "",
				dataType : 'json',
				contentType: "application/json;charset=UTF-8",
				async: false,
				success : function(data) {
					productQnALoad(data);
				}, beforeSend: function() {

				}, complete: function() {

				}, error: function(error) {
					console.log(error);
				}
			});
		}

		function productQnALoad(data) {
			var html = "";
			var phone = data.member_Phone;
			var send = phone.split("-");
			
			html += "<div class='payStatus'>";
			html += "	<span id='close'><img src='../resources/image/icon/close_icon.png' alt='' /></span>";
			html += "	<h4>제품문의</h4>";
			html += "	<hr />";

			html += "	<div id='productPolicy'>";
			html += "		<ul>";
			html += "			<li>상품과 관련없는 내용, 비방, 광고, 불건전한 내용의 글은 사전 동의 없이 삭제될  수 있습니다.</li>";
			html += "			<li>문의 내역 및 답변은 상품 Q&A에서 조회 가능합니다.</li>";
			html += "		</ul>";
			html += "	</div>";
/*

			2020.06.14
			html += "	<table id='prodcutInfo' data-seq='" + data.PRODUCT_SEQ + "' data-naming='" + data.PRODUCT_TITLE + "'>";
			html += "		<tbody>";
			html += "			<tr>";
			html += "				<th>";
			html += "					<div class='thumb'>";
			html += "						<img src='../resources/upload/table/" + data.PRODUCT_SEQ + "/" + data.PRODUCT_FILE_NAME + "' alt=" + data.PRODUCT_FILE_TITLE + " />";
			html += "					</div>";
			html += "				</th>";
			html += "				<td>";
			html += "					<div class='product_txt'>";
			html += "						<p class='txt'>" + data.PRODUCT_TITLE + "</p>"
			html += "					</div>"
			html += "				</td>";
			html += "			</tr>";
			html += "		</tbody>";
			html += "	</table>";
*/

			html += "	<fieldset>";
			html += "		<table id='sendInfo'>";
			html += "			<tbody>";
			html += "				<tr>";
			html += "					<th>유형</th>";
			html += "					<td>";
			html += "						<ul class='check_type'>";
			html += "							<li><label><input type='radio' name='sendType' value='PRODUCT_QA' />상품문의</label></li>";
			html += "							<li><label><input type='radio' name='sendType' value='SHIPPING_QA' />배송문의</label></li>";
			html += "							<li><label><input type='radio' name='sendType' value='UNTIL_QA' />기타</label></li>";
			html += "						</ul>";
			html += "					</td>";
			html += "				</tr>";
			html += "				<tr>";
			html += "					<th>제목</th>";
			html += "					<td><input type='text' name='sendTxt' class='form-control' /></td>";
			html += "				</tr>";
			html += "				<tr>";
			html += "					<th>내용</th>";
			html += "					<td><textarea name='sendContent' class='form-control' /></td>";
			html += "				</tr>";
			html += "				<tr>";
			html += "					<td colspan='2'>";
			html += "						<span class='myQn'>답변 등록시 전송번호</span>";
			html += "						<input type='text' name='phone' id='phone_01' maxlength='4' value='" + send[0] + "' readonly />";
			html += "						<input type='text' name='phone' id='phone_02' maxlength='4' value='" + send[1] + "' readonly />";
			html += "						<input type='text' name='phone' id='phone_03' maxlength='4' value='" + send[2] + "' readonly />";
			html += "					</td>";
			html += "				</tr>";
			html += "			</tbody>";
			html += "		<table>";
			html += "	</fieldset>";

			html += "	<ul class='sendButton'>";
			html += "		<li><button name='sendAdmin' class='btn log'>확인</button></li>";
			html += "	</ul>";
			html += "</div>";

			$('.display-none').css('display', 'block');
			$('.wrap-loading').html(html);

			/* ====================== 사용자 프로필 ======================= */
			$('span#close').click(function() {
				$('.display-none').css('display', 'none');
				$('.wrap-loading div').remove();
			});

			sendProductQnA();
		}

		function sendProductQnA() {
			$('button[name="sendAdmin"]').click(function(e) {
			    var formData = new FormData();
		    	var pattern_num = /[0-9]/;						// 숫자 
		    	var pattern_eng = /[a-zA-Z]/;					// 문자 
		    	var pattern_spc = /[~!@#$%^&*()_+|<>?:{}]/; 	// 특수문자
		    	var pattern_kor = /[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/; 			// 한글체크

			    var num = $('table#prodcutInfo').attr('data-seq');
			    var name = $('table#prodcutInfo').attr('data-naming');
				var type = $('input[name="sendType"]:checked').val();
				var txt = $('input[name="sendTxt"]').val();
				var phone_01 = $('input[id="phone_01"]').val();
				var phone_02 = $('input[id="phone_02"]').val();
				var phone_03 = $('input[id="phone_03"]').val();
				var content = $('textarea[name="sendContent"]').val();
				content = content.replace(/(?:\r\n|\r|\n)/g, '<br />');

				if(!type) {
					alert("유형을 선택해주세요");
					$('input[name="sendType"]').focus();
					return false;
				}

				if(!txt) {
					alert("제목을 입력해주세요");
					$('input[name="sendTxt"]').focus();
					return false;
				}

				if(!content) {
					alert("문의 내용을 입력해주세요");
					$('textarea[name="sendContent"]').focus();
					return false;
				}

				if((pattern_num.test(phone_01)) && (pattern_num.test(phone_02)) && (pattern_num.test(phone_03)) && 
					!(pattern_eng.test(phone_01)) && !(pattern_spc.test(phone_01)) && !(pattern_kor.test(phone_01)) && 
					!(pattern_eng.test(phone_02)) && !(pattern_spc.test(phone_02)) && !(pattern_kor.test(phone_02)) && 
					!(pattern_eng.test(phone_03)) && !(pattern_spc.test(phone_03)) && !(pattern_kor.test(phone_03))) {

					formData.append("num", num);
					formData.append("name", name);
					formData.append("type", type);
					formData.append("txt", txt);
					formData.append("phone_01", phone_01);
					formData.append("phone_02", phone_02);
					formData.append("phone_03", phone_03);
					formData.append("content", content);

					$.ajax({
						type: "POST",
						url	: "/send/productQnA",
						dataType : 'json',
						data : formData,
						processData: false,
						contentType: false,
						success : function(data) {
							if(data == "0") {
								alert("잘못된 접근입니다.")
								location.reload();
							} else {
								alert("등록 되었습니다.");
								location.href="../info/info_qna";
							}
						}, beforeSend: function() {
							$('button[name="sendAdmin"]').attr('disabled','disabled');
							resultBefore();
						}, complete: function() {

						}, error: function(error) {
							console.log(error);
						}
					});
				} else {
		    		alert("문자가 있다면 문자전송 서비스를 이용 하실수 없습니다.");
		    		$('input[id="phone_01"]').focus();
		    		return false;
				}
			});
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

		/* =============================================================== */
		function number_format(x) {
			return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");	
		}

	});
});