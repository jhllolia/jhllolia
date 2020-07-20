jQuery.noConflict();

jQuery(function($) {
	$(document).ready(function() {

		product_check();

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

		/* ============================================ */
		$('button[name="payCancel"]').click(function(e) {
			status_Cancel($(this));
		});

		$('button[name="returnProduct"]').click(function(e) {
			return_Product($(this))
		});

		$('button[name="returnData"]').click(function(e) {
			returnApplyData($(this));
		});

		$('button[name="reviewSend"]').click(function(e) {
			reviewInsert($(this));
		});

		/* ==================  ================== */
		function reviewInsert(obj) {
			var form = "";
			var num = obj.attr('data-num');

			if(!num) {
				location.reload();
			} else {

				form += "<form action='../info/review' method='POST'>"; 
				form += "	<input type='hidden' name='num' value='" + num + "' />";
				form += "</form>";

				$(form).appendTo("body").submit().remove();		// 조회
			}
		}

		/* ==================  ================== */
		function returnApplyData(obj) {
			var formData = new FormData();
			var productNum = $('input[name="keyword"]').val();
			var radioCheck = $('input[name="boxId"]:checked').val();
			var boxCheck = $('select[name="reason_select"] option:selected').val();
			var whyText = $('textarea[name="whyText"]').val();
			whyText = whyText.replace(/(?:\r\n|\r|\n)/g, '<br />');

			if(!productNum) {
				alert("잘못된 접근입니다");
				location.reload();
			}

			if(!radioCheck) {
				alert("유형을 선택해주세요");
				return false;
			}

			if(!boxCheck) {
				alert("사유를 선택해주세요");
				$('select[name="reason_select"]').focus();
				return false;
			}

			if(!whyText) {
				alert("상세사유를 입력해주세요");
				$('textarea[name="whyText"]').focus();
				return false;
			} else if(whyText.length < 5) {
				alert("상세사유는 최소 5글자 이상은 작성해야 합니다");
				$('textarea[name="whyText"]').focus();
				return false;
			} else {

				formData.append("productNum", productNum);
				formData.append("radioCheck", radioCheck);
				formData.append("boxCheck", boxCheck);
				formData.append("whyText", whyText);

				$.ajax({
					type: "POST",
					url	: "/productReturn/apply",
					dataType : 'json',
					data : formData,
					processData: false,
					contentType: false,
					success : function(data) {
						if(data != "0") {
							alert("반품 / 교환 신청이 완료되었습니다");
							location.href="../info/info_payment";
						} else {
							alert("잘못된 접근입니다.");
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

		function product_check() {
			/* ================== 전체 선택/취소 ================== */
			$('label[name="check_all"]').bind('click', function(e) {
				if($(this).find("input[name='status_check_all']").is(':checked')) {
					$("input:checkbox[name='status_check']").prop("checked", true);
					$("label[name='product_check']").find('img').attr("src", "../resources/image/icon/chk_on.png");
					$('table#orderInfo').find('tbody').find('tr').addClass('active');

					$(this).find('img').attr("src", "../resources/image/icon/chk_on.png");
				} else {
					$("input:checkbox[name='status_check']").prop("checked", false);
					$("label[name='product_check']").find('img').attr("src", "../resources/image/icon/chk_off.png");
					$('table#orderInfo').find('tbody').find('tr').removeClass('active');

					$(this).find('img').attr("src", "../resources/image/icon/chk_off.png");
				}
			});

			/* ================== 부분 선택/취소  ================== */
			$('label[name="product_check"]').click( function(e) {
				var len = $("input:checkbox[name='status_check']").length;

				/* ========== 전체선택 해제 ======== */
				if(len == $('input[name="status_check"]:checked').length) {
					$('label#check_all').find('input[name="status_check_all"]').prop("checked", true);
					$('label#check_all').find('img').attr("src", "../resources/image/icon/chk_on.png");
				} else {
					$('label#check_all').find('input[name="status_check_all"]').prop("checked", false);
					$('label#check_all').find('img').attr("src", "../resources/image/icon/chk_off.png");
				}

				/* ========== 부분 해제 ======== */
				if($(this).find("input[name='status_check']").is(':checked')) {
					$(this).find("input[name='status_check']").prop("checked", true);
					$(this).find('img').attr("src", "../resources/image/icon/chk_on.png");
					$(this).closest('tr').addClass('active');
				} else {
					$(this).find("input[name='status_check']").prop("checked", false);
					$(this).find('img').attr("src", "../resources/image/icon/chk_off.png");
					$(this).closest('tr').removeClass('active');

					$('label#check_all').find('input[name="status_check_all"]').prop("checked", false);
					$('label#check_all').find('img').attr("src", "../resources/image/icon/chk_off.png");
				}
			});
		}

		/* ====================== 반품 / 교환 체크 ======================= */
		function return_Product(obj) {
			var form = "";
			var arr = new Array();

			if($('input:checkbox[name="status_check"]:checked').length == 0) {
				alert("반품 / 교환을 진행할 상품을 선택해주세요.");
				$('input[name="status_check_all"]').focus();
				return false;
			} else {

				$('input:checkbox[name="status_check"]:checked').each(function(index, item) {
					arr[index] = $(this).attr('data-check');
				});

				form += "<form action='./returnProduct' method='POST'>"; 
				form += "	<input type='hidden' name='arr' value='" + arr + "' />";
				form += "</form>";

				$(form).appendTo("body").submit().remove();		// 조회
			}
		}

		/* ====================== 결제 취소 정보 ======================= */
		function status_Cancel(obj) {
		    var formData = new FormData();
			var arr = new Array();

			if($('input:checkbox[name="status_check"]:checked').length == 0) {
				alert("취소를 진행할 상품을 선택해주세요.");
				$('input[name="status_check_all"]').focus();
				return false;
			} else {

				$('input:checkbox[name="status_check"]:checked').each(function(index, item) {
					arr[index] = $(this).attr('data-check');
				});

				formData.append("arr", arr);

				$.ajax({
					type: "POST",
					url	: "/order/changeProduct",
					dataType : 'json',
					data : formData,
					processData: false,
					contentType: false,
					success : function(data) {
						changeProductStatus(data);
					}, beforeSend: function() {

					}, complete: function() {

					}, error: function(error) {
						console.log(error);
					}
				});
			}
		}

		/* ====================== 결제 취소 Action ======================= */
		function changeProductStatus(data) {
			var html = "";

			html += "<div class='payStatus'>";
			html += "	<span id='close'><img src='../resources/image/icon/close_icon.png' alt='' /></span>";			
			html += "	<h4>결제 취소요청</h4>";
			html += "	<hr />";

			html += "	<div id='productPolicy'>";
			html += "		<ul>";
			html += "			<li>결제를 취소하시면 당일결제는 당일 취소가 됩니다.</li>";
			html += "			<li>이후에 취소를 하시면 PG사 정책에 따라 7일정도 소요됩니다.</li>";
			html += "			<li>현금결제 취소는 취소수수료가 부과됩니다.</li>";
			html += "		</ul>";
			html += "	</div>";

			html += "	<table id='order_num' class='check' data-num='" + data[0].order_NUM + "'>";
			html += "		<thead>";
			html += "			<tr>";
			html += "				<th>상품 정보</th>";
			html += "				<th>상품 가격</th>";
			html += "			</tr>";
			html += "		</thead>";
			html += "		<tbody>";

			for (var i = 0; i < data.length; i++) {				
				html += "		<tr>";
				html += "			<td>";
				html += "				<ul class='info'>";
				html += "					<li><img src='../resources/upload/table/" + data[i].product_NUM +"/" + data[i].product_PROFILE + "' data-img='" + data[i].product_PROFILE + "' /></li>";
				html += "					<li>"
				html += "						<dl class='option'>"
				html += "							<dt class='txt'><span class='op'>옵션</span>" + data[i].product_OPTION + "</dt>"
				html += "							<dt class='txt'><span class='op'>수량</span>" + data[i].product_QTY + " 개</dt>"
				html += "						</dl>"
				html += "					</li>";
				html += "				</ul>";
				html += "			</td>";
				html += "			<td><span class='total'>" + number_format(parseInt(data[i].product_PRICE * data[i].product_QTY)) + " 원</span></td>";
				html += "		</tr>";
			}

			html += "		</tbody>";
			html += "	</table>";

			html += "	<div class='user'>";
			html += "		<table id='tab_user'>";
			html += "			<tbody>";
			html += "				<tr>";
			html += "					<th>취소 요청사유</th>";
			html += "					<td><input type='text' name='cancelTit' maxlength='30' /></td>";
			html += "				</tr>";
			html += "				<tr>";
			html += "					<th>상세사유</th>";
			html += "					<td><textarea name='cancelText' maxlength='200'></textarea><span id='counter'></span></td>";
			html += "				</tr>";
			html += "			</tbody>";
			html += "		</table>";
			html += "		<div class='sendPlease'>";
			html += "			<button name='pay-change' class='btn log'>취소요청</button>";
			html += "		</div>";
			html += "	</div>";
			html += "</div>";

			$('.display-none').css('display', 'block');
			$('.wrap-loading').html(html);

			/* ====================== 사용자 프로필 ======================= */
			$('span#close').click(function() {
				$('.display-none').css('display', 'none');
				$('.wrap-loading div').remove();
			});

			sendPayCancel(data);

			/* ============================================= */
			$('textarea[name="cancelText"]').keyup(function(e) {
				var content = $(this).val();
				$(this).next('span#counter').html(content.length + '/200');
			});

			$('textarea[name="cancelText"]').keyup();
		}

		/* ============================================= */
		function sendPayCancel(data) {
			$('button[name="pay-change"]').one('click', function(e) {
				var formData = new FormData();
				var size = $('table#orderInfo').attr('data-size');
				var num = $('table#order_num').attr('data-num');
				var tit = $('input[name="cancelTit"]').val();
				var txt = $('textarea[name="cancelText"]').val();

				if(!tit || !txt) {
					alert('사유를 작성해주세요');
					$('textarea[name="cancelText"]').focus();
					return false;
				} else {
					if(confirm('취소 완료 후에는 취소 철회가 불가합니다. 취소를 하시겠습니까?') == true) {
						formData.append("size", size);
						formData.append("num", num);
						formData.append("tit", tit);
						formData.append("txt", txt);
						formData.append("data", JSON.stringify(data));

						$.ajax({
							type: "POST",
							url	: "/payment/cancel",
							dataType : 'json',
							data : formData,
							processData: false,
							contentType: false,
							success : function(data) {
								alert(data.message);
								location.reload();
							}, beforeSend: function() {
								$('button[name="pay-change"]').attr('disabled','disabled');

								resultBefore();
							}, error: function(error) {
								console.log(error);
							}
						});
					}
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