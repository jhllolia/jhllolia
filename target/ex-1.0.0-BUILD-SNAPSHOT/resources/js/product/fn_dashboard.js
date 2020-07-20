jQuery.noConflict();

jQuery(function($) {
	$(document).ready(function() {

	    var today = null;
	    var year = null;
	    var month = null;
	    var nowDay = null;
	
	    dataTime();
		list();			// list 출력

		/* ====================== 사용자 프로필 ======================= */
		$('#payment_search').click( function(e) {
			paySearch($(this));		
		});
	
		/* ====================== 결제 상태 List ======================= */
		function list() {
			$("input[name=th_checkAll]").prop("checked", true);
			$("input[name=confirm]").prop("checked", true);
	
			var len = $("input:checkbox[name=confirm]").length;
	
			/* ================== 전체 선택/취소 ================== */
			$('label#pay_all').bind('click', function(e) {
				if($(this).find("#th_checkAll").is(':checked')) {
					$("input:checkbox[name=confirm]").prop("checked", true);
					$(".pay_chk").attr("src", "../resources/image/icon/chk_on.png");
				} else {
					$("input:checkbox[name=confirm]").prop("checked", false);
					$(".pay_chk").attr("src", "../resources/image/icon/chk_off.png");
				}
			});

			/* ================== 부분 선택/취소  ================== */
			$('label#pay_chk').click( function(e) {
				if( $(this).find("input[name=confirm]").is(':checked') ) {
					$(this).find('input[name=confirm]').prop("checked", true);
					$(this).find('.pay_chk').attr("src", "../resources/image/icon/chk_on.png");
				} else {
					$(this).find('input[name=confirm]').prop("checked", false);
					$(this).find('.pay_chk').attr("src", "../resources/image/icon/chk_off.png");
	
					$('label#pay_all').find('input[name="th_checkAll"]').prop("checked", false);
					$('label#pay_all').find('.pay_chk').attr("src", "../resources/image/icon/chk_off.png");
				}
			});

			/* ================== 이미지 존재 x ================ */
			$('div.pay_item').find('a.pay_thumb').find('img').error(function() {
				$(this).attr('src','../resources/image/icon/none.png').css('padding','10px');
			});

			selectList();
		}

		function dataTime() {
			var firstDate, lastDate;
			var date = new Date();
			
			var today = new Date();
			var monthNo = today.getMonth() + 1;
			var weekNo = getWeekNo(today);
			
			/*

			document.getElementById('month').innerHTML = monthNo;
			document.getElementById('week').innerHTML = weekNo;

			// Get a date
			var last = new Date(today.getYear(), today.getMonth()+1,0);	// last day
			var lastDay = last.getDate();								// get date from last
			var first = new Date(today.getYear(), today.getMonth(),1);	// first day
			var firstDay = first.getDate();								// get date from first

			// Get the day of the week
			var week = new Array('일요일', '월요일', '화요일', '수요일', '목요일', '금요일', '토요일');	// create array
			var toDay = today.getDay();																// get day from today
			var makeDay = week[toDay];																// Put array in makeDay
			
			console.log(toDay);

			var weekstart = today.getDate() - today.getDay();				// get the first day of each month
			console.log(weekstart);

			if (weekstart < firstDay) {										// weekstart is less than firstDay
				document.getElementById('weekstart').innerHTML = firstDay;	// firstDay output at weekstart
			} else {
				document.getElementById('weekstart').innerHTML = weekstart;	// weekstart output at weekstart
			}

			var weekend = weekstart + 6;								// end day is the first day + 6
			if (weekstart == lastDay) {									// weekstart is equal to lastday
				$(".sameDel").css("display","none");					// weekend Delete
				document.getElementById('makeDay').innerHTML = makeDay;
			}

			if(weekend > lastDay){										// weekend is greater than lastday
				document.getElementById('weekend').innerHTML = lastDay;	// lastDay output at weekend
			} else {
				document.getElementById('weekend').innerHTML = weekend;	// weekend output at weekend
			}
			
			*/

			firstDay = new Date(date.getYear(), date.getMonth(), 1).getDate();
			lastDay = new Date(date.getYear(), date.getMonth() + 1, 0).getDate();
			
			/* (date.getMonth() + 1) */ 
			$("input[name='firstDate']").val("" + date.getFullYear() + "-" + ("0" + (date.getMonth() - 3)).slice(-2) + "-" + ("0" + firstDay).slice(-2));
			$("input[name='lastDate']").val("" + date.getFullYear() + "-" + ("0" + (date.getMonth() + 1)).slice(-2) + "-" + ("0" + lastDay).slice(-2));

			$.datepicker.setDefaults({
				dateFormat: "yy-mm-dd",		//	날짜 형식
				changeMonth: true,			//	달
				changeYear: true,			//	년
				dayNamesMin: ['월', '화', '수', '목', '금', '토', '일'],
				monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월']
			});

			$("input[name='firstDate']").datepicker();                    
			$("input[name='lastDate']").datepicker();
		}

		function getWeekNo(today) {
			 var date = new Date(today);
			 return Math.ceil(date.getDate() / 7);
		}

		/* ====================== 결제 상태 검색 ======================= */
		function paySearch(obj) {
			if( $("input[name=confirm]").is(":checked") == false ) {
				alert("검색할 결제 상태를 하나 이상 선택해주세요.");
				return false;
			} else {
				selectList();
			}
		}

		/* ====================== 회원 결제 리스트(1) ======================= */
		function selectList() {
			var formData = new FormData();
			var status = new Array();
	
			$("input[name='confirm']:checked").each(function(index, item) {
				status[index] = $(this).val();
			});
	
			var before = $("input[name='firstDate']").val();
			var after = $("input[name='lastDate']").val();
			var search_tit = $('select[name="searchOption"] option:selected').val();
			var search_txt = $('input[name="payment_txt"]').val();
	
			formData.append("status", status);			// 결제 상태
			formData.append("before", before);			// 선택 된 시간
			formData.append("after", after);			// 선택 된 시간
			formData.append("search", search_tit);		// 검색 카테고리
			formData.append("keyword", search_txt);		// 검색 텍스트

			$.ajax({
	    		url	: '/payment/list',
				type : 'POST',
				data : formData,
				processData: false,
				contentType: false,
				success : function(data) {
	    			return_check(data);
				}, beforeSend: function() {
	    			return_before();
				}, complete: function() {

				}, error: function(error) {
					console.log(error);
				}
			});
		}

		/* ====================== 회원 결제 리스트(1) ======================= */
		function return_check(data) {
			var count = 0;
			var htmlText = "";

			if(isEmpty(data) == true) {
				htmlText += "<li class='orderNone'>";
				htmlText += "	<div class='chk_null'>";
				htmlText += "		<i class='fas'><img src='../resources/image/icon/box.png' alt='' /></i><p class='txt'>검색결과가 존재하질 않습니다.</p>";
				htmlText += "	</div>";
				htmlText += "<li>";
			} else {
				/*

				const keys = Object.keys(data);

				for (var i = keys.length-1; i >= 0; i--) {
					var key = keys[i];
					var val = data[key];

					console.log(key, val);
				}

				*/

				$.each(data, function(key, val) {
					var payDate = new Date(val[0].paid_DATE);
					var price = 0;
					var qty = 0;

					htmlText += "<li name='user_" + count + "' data-seq='" + key + "'>";
					htmlText += "	<div class='pay_item'>";
					htmlText += "		<a href='../tab/" + val[0].product_NUM + "' class='pay_thumb'>";
					htmlText += "			<img src='../resources/upload/table/" + val[0].product_NUM + "/" + val[0].product_PROFILE + "' alt='" + val[0].product_NAME + "' />";
					htmlText += "		</a>";

					htmlText += "		<div class='pay_info'>";
					htmlText += "			<p class='orderNum'><span class='naming'>주문번호</span>" + val[0].order_NUM + "</p>";
					htmlText += "			<div class='tit'>";

					$.each(val, function(i, state) {
						htmlText += "<span class='order_check' name='order_name_" + i + "'>" + state.product_NAME + "</span>";

						price += parseInt(state.product_PRICE * state.product_QTY);
						qty += parseInt(state.product_QTY);
					});

					htmlText += "			</div>";
					htmlText += "			<ul>";
					htmlText += "				<li><span>" + qty + " 개</span></li>";
					htmlText += "				<li><span>" + numberFormat(parseInt(price) + parseInt(val[0].product_SHIPPING_COST)) + " 원</span></li>";
					htmlText += "				<li class='date'><span>" + ("0" + (payDate.getMonth() + 1)).slice(-2) + "월 " + ("0" + payDate.getDate()).slice(-2) + "일 " + ("0" + payDate.getHours()).slice(-2) + "시 " +  ("0" + payDate.getMinutes()).slice(-2) + "분" + "</span></li>";
					htmlText += "			</ul>";

					$.each(val, function(i, state) {
						if(state.paid_STATUS == 'paid') {
							htmlText += "<span name='status' class='status_paid'>결제 완료</span>";
						} else if(state.paid_STATUS == 'cancelled') {
							htmlText += "<span name='status' class='status_cancelled'>결제 취소</span>";
						} else if(state.paid_STATUS == 'shipping_waiting') {
							htmlText += "<span name='status' class='status_waiting'>상품준비중</span>";
						} else if(state.paid_STATUS == 'shipping_delivery') {
							htmlText += "<span name='status' class='status_waiting'>배송중</span>";
						} else if(state.paid_STATUS == 'shipping_success') {
							htmlText += "<span name='status' class='status_waiting'>배송완료</span>";
						} else if(state.paid_STATUS == 'shipping_return') {
							htmlText += "<span name='status' class='status_paid'>반품요청</span>";
						} else if(state.paid_STATUS == 'shipping_exchange') {
							htmlText += "<span name='status' class='status_paid'>교환요청</span>";
						}
					});

					htmlText += "		</div>";
					htmlText += "		<div class='pay_change'>";
					htmlText += "			<button id='orderStatus' name='orderStatus' data-num='" + key  + "' class='btn log'>";
					htmlText += "				<i><img src='../resources/image/icon/box.png' alt='' /></i><span class='txt'>결제 상태</span>";
					htmlText += "			</button>";

					if(val[0].product_SHIPPING_COURIER != null) {
						htmlText += "<button id='pay_courier' name='pay_courier' data-num='" + val[0].product_SHIPPING_NUM + "' data-courier='" + val[0].product_SHIPPING_COURIER + "' class='btn log'>";
						htmlText += "<i><img src='../resources/image/icon/m_truck.png' alt='' /></i>";
						htmlText += "<span class='txt'>배송 조회</span>";
						htmlText += "</button>";
					}

					htmlText += "		</div>";
					htmlText += "	</div>";
					htmlText += "</li>";

					++count;
				});
			}

			$('#user_List').html(htmlText);
			status_Info();					//
			check_courier();				// 배송조회
			none_img();						// 이미지 none
		}

		/* ====================== 배송정보 확인 ======================= */
		function check_courier() {
			$('button[name="pay_courier"]').click(function(e) {
				window.open('https://tracker.delivery/#/' + $(this).attr('data-num') + '/' + $(this).attr('data-courier') + '');
			});
		}

		/* ====================== 결제 상태 정보 ======================= */
		function status_Info() {
			$('button[name="orderStatus"]').click(function(e) {
				var data_num = $(this).attr('data-num');
				var form = "";

				form += "<form action='./orderStatus' method='POST'>"; 
				form += "	<input type='hidden' name='key' value='" + data_num + "' />";
				form += "</form>";

				$(form).appendTo("body").submit().remove();		// 조회
			});
		}

		/* ================== 이전 페이지 ================== */
		function return_before() {
			var htmlText = "";
	
			htmlText += "<li>";
			htmlText += "	<div class='chk_null'><img src='../resources/image/icon/loading.gif' alt='' /></div>";
			htmlText += "</li>";
	
			$('#user_List').html(htmlText);
		}
		
		/* ======================= 이미지 없음 =========================== */
		function none_img() {
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
		}

		/* ================== 콤마 세자리 ================== */
		function numberFormat(inputNumber) {
			return inputNumber.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
		}
		
		/* ================== 객체 존재 여부 ================== */
		function isEmpty(obj) {
			return Object.keys(obj).length === 0;
		}

	});
});
