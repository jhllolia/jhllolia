jQuery.noConflict();

jQuery(function($) {
	$(document).ready(function() {

		/* ====================== 결제 관련 버튼 ======================= */
		$('button[id="sendPay"]').click(function(e) {
			sendPay($(this));
		});

		$('button[name="buyer_link"]').click(function(e) {
			linkMyInfo($(this));
		});

		$('label[name="payChk"]').click(function(e) {
			paymentSelect($(this));
		});

		$('#payClose').click(function(e) {
			paymentClose();
		});

		/* ====================== 결제 방법 설정 ======================= */
		function paymentSelect($this) {
			if($this.find('input:radio[name="pay_method"]').is(':checked') == true) {
				$this.find('img').attr('src', '../resources/image/icon/chk_on.png');
				$this.next('.beforeTxt').slideDown();

				$('button[id="sendPay"]').addClass('on');
			} else {
				$('label[name="payChk"]').find('img').attr('src', '../resources/image/icon/chk_off.png');
				$('label[name="payChk"]').next('.beforeTxt').slideUp();
			}
		}

		/* ====================== 결제 진행 취소 ======================= */
		function paymentClose() {
			var con = confirm("결제 진행을 취소하시겠습니까?");

			if(con == true) {
				alert('결제 진행을 취소하셨습니다.');
				location.href = "../bbs/product";
			}
		}

		/* ====================== 결제 방법 설정 ======================= */
		function linkMyInfo(obj) {
			var seq = obj.attr('by-seq');

			if(!seq) {
				alert("로그인 만료 되었습니다.");
				location.href = "../member/login";
				return false;
			} else {
				location.href = "../info/info_payment";
			}
		}

		/* ====================== 결제 모듈 ======================= */
		function sendPay(obj) {
		    var formData = new FormData();

			var selectPg = $('input:radio[name="pay_method"]:checked').attr('pg');			// PG사 선택
			var selectMethod = $('input:radio[name="pay_method"]:checked').attr('method');	// PG사 방법

			var userNum = $('.payUser .order').attr('data-seq');
			var user_name = $('input[name="pay_user_Nm"]').val();
			var user_email = $('input[name="pay_user_Em"]').val();
			var user_phone = $('input[name="pay_user_Ph"]').val();

			var zip = $('#zip').val();
			var addr1 = $('#addr1').val();
			var addr2 = $('#addr2').val();

			var checkNm = "";
			var checkSize = $('p[name="dataIndex"]').length;
			var checkPrice = $('div.orderTotal span#txtResult').text().replace(/[^0-9]/g, '');
			var memo = $('select[name="pay_memo"] option:selected').val();

			if(checkSize > 1) {
				for (var i = 0; i < checkSize; i++) {
					checkNm += $('p[name="dataIndex"]:eq(' + i + ')').find('span:first-child').text() + " / ";
				}

				checkNm = checkNm.slice(0, -2);
			} else {
				checkNm += $('p[name="dataIndex"]').find('span:first-child').text();
			}

			if(memo != "") {
				memo = $('select[name="pay_memo"] option:selected').text();
			}

			if(!selectPg || !selectMethod) {
	    		alert("PG 정보가 존재하질 않습니다.");
	    		return false;
			}

	    	if( !userNum ) {
	    		var error = '<p class="txt">회원 고유번호가 존재하질 않습니다.</p>';
	    		returnError(error);
	    		return false;
	    	}

	    	if(!zip || !addr1 || !addr2 || !user_name || !user_email || !user_phone) {
	    		var error = '<p class="txt">회원 정보가 존재하질 않습니다.</p>';
	    		returnError(error);
	    		return false;
	    	} else {

	    		formData.append("selectPg", selectPg);
	    		formData.append("selectMethod", selectMethod);
	    		formData.append("checkNm", checkNm);
	    		formData.append("checkPrice", checkPrice);
	    		formData.append("user_email", user_email);
	    		formData.append("user_name", user_name);
	    		formData.append("user_phone", user_phone);
	    		formData.append("zip", zip);
	    		formData.append("addr1", addr1);
	    		formData.append("addr2", addr2);

	    		$.ajax({
		    		url	: '/final/price',
					type : 'POST',
					data : formData,
					processData: false,
					contentType: false,
					success : function(data) {
						result_data_send(data);
					}, error: function(error) {
						console.log(error);
					}
				});
	    	}
		}

		/* ======================  ======================= */
		function result_data_send(data) {
		    var nowURL = window.location.protocol + "//" + window.location.host;

			if(isEmpty(data) == true) {
				location.reload();
			} else {

				IMP.init(data.key);
				IMP.request_pay({
					pg 				: data.pg, 								// PG 사
					pay_method 		: data.method, 							// PG 사
					merchant_uid	: 'merchant_' + new Date().getTime(), 	// 가맹점 
					name 			: data.checkNm, 							// 
					amount 			: data.price, 							// 
					buyer_email 	: data.email, 
					buyer_name 		: data.name, 
					buyer_tel 		: data.phone, 
					buyer_addr 		: data.addr1 + " " + data.addr2, 
					buyer_postcode  : data.zip,
					m_redirect_url 	: nowURL + '/pay/orderMobile'
				}, function(rsp) {
					result_data_dbSave(rsp);
				});
			}
		}

		/* ======================  ======================= */
		function result_data_dbSave(rsp) {
			var formData = new FormData();

			if (rsp.success) {
				formData.append("memo", $('select[name="pay_memo"] option:selected').val());
				formData.append("rsp", JSON.stringify(rsp));

				$.ajax({
					url	 : "/payment/payInsert",
					dataType : 'json',
					type : 'POST',
					data : formData,
					processData: false,
					contentType: false,
					success : function(data) {
						orderComplete(data);
					}, beforeSend: function() {
						resultBefore();
					}, complete: function() {
						$('.wrap-loading').find('div').remove();
						$('.wrap-loading').addClass('display-none');
					}, error: function(error) {
						console.log(error);
					}
				});
			} else {
				var error = '<p class="txt">' + rsp.error_msg + '</p>';
				returnError(error);
			}
		}
		
		/* ================== 객체 존재 여부 ================== */
		function isEmpty(obj) {
			return Object.keys(obj).length === 0;
		}

		/* ====================== 결제 완료 ( 페이지 이동 ) ======================= */
		function orderComplete(obj) {
			var form = "";

			form += "<form action='./orderComplete' method='POST'>"; 
			form += "	<input type='hidden' name='order' value='" + obj.order + "' />";
			form += "	<input type='hidden' name='imp' value='" + obj.imp + "' />";
			form += "</form>";

			$(form).appendTo("body").submit().remove();		// 조회
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

		/* ======================= 결과 출력 오류 =========================== */
		function returnError(obj) {
	    	var html = "";

			html += '<div id="re-Error" class="error">';
			html += '	<span id="close"><img src="../resources/image/icon/close.png" alt="delete" /></span>';
			html += '	<h4><img src="../resources/image/icon/error.png" alt="errorIcon" />예약 오류</h4>';
			html += '	<hr />';
			html += '	' + obj + '';
			html += '</div>';

			$('.wrap-loading').removeClass('display-none');
			$('.wrap-loading').append(html);

			$('.wrap-loading').click( function(e) {
				var $target = $(e.target).find('div').hasClass('error');		// 존재 여부

				if( $target == true ) {
					$('.wrap-loading').addClass('display-none');				// 
					$('.wrap-loading').find('div').remove();					// 
				}
			});

	    	$('span#close').click(function(e) {
				$('.wrap-loading').find('div').remove();
				$('.wrap-loading').addClass('display-none');
	    	});

	    	$('button[name="linkLogin"]').click( function(e) {
	    		location.href="../member/login";
	    	});

			$(document).keyup(function(e) {
	    		if(e.keyCode == 27) {
	    			$('.wrap-loading').find('div').remove();
	    			$('.wrap-loading').addClass('display-none');
	    		}
	    	});
		}

		/* =============================================================== */
		function number_format(x) {
			return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");	
		}

	});
});
		