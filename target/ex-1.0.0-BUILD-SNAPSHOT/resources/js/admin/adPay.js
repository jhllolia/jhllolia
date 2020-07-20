jQuery.noConflict();

jQuery(function($) {
	$(document).ready(function() {

	    dashDate();		// Search 입력
		list();			// list 출력

		/* ====================== 사용자 프로필 ======================= */
		$('#payment_search').click(function(e) {
			paySearch($(this));		
		});
		
		$('#payment_excel').click(function() {
			selectDateExcel();
		});

		$('button[name="status_change"]').click(function() {
			order_statusChange($(this));
		});

		/* ====================== 검색 기간 ======================= */
		function selectDateExcel() {
			var formData = new FormData();
			var status = new Array();
			var form = "";

			var before = $("input[name='firstDate']").val();
			var after = $("input[name='lastDate']").val();
			var search_tit = $('select[name="searchOption"] option:selected').val();
			var search_txt = $('input[name="payment_txt"]').val();

			form += "<form action='/payment/excel' method='POST'>"; 
			form += "	<input type='hidden' name='before' value='" + before + "' />";
			form += "	<input type='hidden' name='after' value='" + after + "' />";
			form += "	<input type='hidden' name='search_tit' value='" + search_tit + "' />";
			form += "	<input type='hidden' name='search_txt' value='" + search_txt + "' />";
			form += "</form>";

			$(form).appendTo("body").submit().remove();		// 조회
		}

		/* ====================== 검색 기간 ======================= */
		function dashDate() {
			var firstDate, lastDate;
			var date = new Date();

			firstDay = new Date(date.getYear(), date.getMonth(), 1).getDate();
			lastDay = new Date(date.getYear(), date.getMonth() + 1, 0).getDate();

			$("input[name='firstDate']").val("" + date.getFullYear() + "-" + ("0" + (date.getMonth() + 1)).slice(-2) + "-" + ("0" + firstDay).slice(-2));
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

		/* ====================== 결제 상태 List ======================= */
		function list() {
			$("input[name=th_checkAll]").prop("checked", true);
			$("input[name=confirm]").prop("checked", true);

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
	
		/* ====================== 결제 상태 검색 ======================= */
		function paySearch(obj) {
			if( $("input[name='confirm']").is(":checked") == false ) {
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
			formData.append("search", search_tit);		// 
			formData.append("keyword", search_txt);		// 

			/* ====================== 검색 금액 / 개수 / 요청 출력(1) ======================= */
			productCntCheck(status, before, after, search_tit, search_txt);

			$.ajax({
	    		url	: '/payCheck/admin',
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

		/* ====================== 검색 금액 / 개수 / 요청 출력(2) ======================= */
		function productCntCheck(status, before, after, search_tit, search_txt) {
			var formData = new FormData();

			formData.append("status", status);
			formData.append("before", before);
			formData.append("after", after);
			formData.append("search", search_tit);
			formData.append("keyword", search_txt);

			$.ajax({
	    		url	: '/productCheck/admin',
				type : 'POST',
				data : formData,
				processData: false,
				contentType: false,
				success : function(data) {
					$('#totalPrice').text(numberFormat((parseInt(data.product_PRICE) + parseInt(data.product_SHIPPING_COST))) + " 원");
					$('#totalQty').text(numberFormat(data.product_QTY) + " 개");
					$('#requestPerson').text(numberFormat(data.pay_SEQ) + " 요청");
				}, error: function(error) {
					console.log(error);
				}
			});
		}

		/* ====================== 회원 결제 리스트(2) ======================= */
		function return_check(list) {
			var htmlText = "";
			var len = list.length;
			var date = new Date();

			if(len > 0) {
				for( var i = 0; i < len; i++ ) {
					var status = list[i].paid_STATUS;			// 결제 상태
					var payDate = new Date(list[i].paid_DATE);	// 결제 일

					htmlText += "<tr data-seq='user_" + i + "' name='" + list[i].order_NUM + "'>";
					htmlText += "	<td>";

					if(status != 'cancelled') {
						htmlText += "<label class='action_check'>";
						htmlText += "	<img class='action_img' src='../resources/image/icon/chk_off.png' alt='" + list[i].product_NAME + "' />";
						htmlText += "	<input type='checkbox' name='check_num' data-seq='" + list[i].pay_SEQ + "' data-check='" + status + "' value='' />";
						htmlText += "</label>";
					}

					htmlText += "	</td>";
					htmlText += "	<td></td>";
					htmlText += "	<td></td>";
					htmlText += "	<td></td>";
					htmlText += "	<td><div class='orderAddr'></div></td>";
					htmlText += "	<td>" + list[i].product_NAME + "</td>";
					htmlText += "	<td>" + list[i].product_QTY + " 개</td>";
					htmlText += "	<td>" + numberFormat((list[i].product_PRICE * list[i].product_QTY)) + " 원</td>";
					htmlText += "	<td></td>";
					htmlText += "	<td>";
					htmlText += "		<div class='shipping_num' shipping-courier='" + list[i].product_SHIPPING_COURIER + "' shipping-num='" + list[i].product_SHIPPING_NUM + "' data-seq='" + list[i].order_NUM + "'></div>";
					htmlText += "	</td>";
					htmlText += "	<td>";
					htmlText += "		<span class='dateChk'>" + ("0" + (payDate.getMonth() + 1)).slice(-2) + "월 " + ("0" + payDate.getDate()).slice(-2) + "일 " + ("0" + payDate.getHours()).slice(-2) + "시" +  ("0" + payDate.getMinutes()).slice(-2) + "분" + "</span>";
					htmlText += "	</td>";

					if(status == 'paid') {
						htmlText += "<td class='status_paid'>결제</td>";
					} else if(status == 'cancelled') {
						htmlText += "<td class='status_cancelled'>결제취소</td>";
					} else if(status == 'shipping_waiting') {
						htmlText += "<td class='status_fail'>상품준비중</td>";
					} else if(status == 'shipping_delivery') {
						htmlText += "<td class='status_fail'>";
						htmlText += "	<button id='shipping_check' obj1=" + list[i].product_SHIPPING_NUM + " obj2=" + list[i].product_SHIPPING_COURIER + ">배송중</button>";
						htmlText += "</td>";
					} else if(status == 'shipping_success') {
						htmlText += "<td class='status_fail'>";
						htmlText += "	<button id='shipping_check' obj1=" + list[i].product_SHIPPING_NUM + " obj2=" + list[i].product_SHIPPING_COURIER + ">배송완료</button>";
						htmlText += "</td>";
					} else if(status == 'shipping_return') {
						htmlText += "<td class='status_fail'>반품요청</td>";
					} else if(status == 'shipping_exchange') {
						htmlText += "<td class='status_fail'>교환요청</td>";
					}

					htmlText += "	<td>";
					htmlText += "		<div class='dataMemo'>";

					if(list[i].buyer_MEMO != "" || list[i].buyer_MEMO != null) {
						htmlText += "<button name='memo' id='openMemo' data-order='" + list[i].order_NUM  + "' data-seq='" + list[i].pay_SEQ + "'>메모</button>";
					}

					htmlText += "		</div>";
					htmlText += "	</td>";
					htmlText += "</tr>";
				}
			} else {
				htmlText += "<tr>";
				htmlText += "	<td colspan='13' class='chk_null'>검색결과가 존재하질 않습니다.</td>";
				htmlText += "<tr>";
			}

			$('#user_List').html(htmlText);		// 
			orderNumberCheck(list);				// 
			page();								// 페이징 처리

			/* ===================================================== */
			$('button[id="shipping_check"]').click(function(e) {
				var obj1 = $(this).attr('obj1');
				var obj2 = $(this).attr('obj2');

				window.open('https://tracker.delivery/#/' + obj1 + '/' + obj2 + '');
			});

			/* ===================================================== */
			$('button[id="openMemo"]').click(function(e) {
				$('.memo_re').remove();

				var html = "";
				var formData = new FormData();
				var obj = $(this);
				var seq = obj.attr('data-seq');
				var order = obj.attr('data-order');

				formData.append("seq", seq);
				formData.append("order", order);

				$.ajax({
		    		url	: '/open/memberMemo',
					type : 'POST',
					data : formData,
					processData: false,
					contentType: false,
					success : function(data) {
						html += "<div class='memo_re'>";
						html += "	<span id='memoResult'>" + data.buyer_MEMO + "</span>";
						html += "</div>";

						obj.append(html);
					}, error: function(error) {
						console.log(error);
					}
				});
			});
		}

		/* ===================================================== */
		function orderNumberCheck(data) {
			var len = $("input:checkbox[name='check_num']").length;
	
			/* ================== 전체 선택/취소 ================== */
			$('label.status_check_All').bind('click', function(e) {
				if($(this).find("input[name='check_All']").is(':checked')) {
					$("input:checkbox[name='check_num']").prop("checked", true);
					$(".action_img").attr("src", "../resources/image/icon/chk_on.png");
				} else {
					$("input:checkbox[name='check_num']").prop("checked", false);
					$(".action_img").attr("src", "../resources/image/icon/chk_off.png");
				}
			});

			/* ================== 부분 선택/취소  ================== */
			$('label.action_check').click( function(e) {
				if($(this).find("input[name='check_num']").is(':checked') || $(this).find("input[name='check_num']").attr('data-check') == 'cancelled') {
					$(this).find("input[name='check_num']").prop("checked", true);
					$(this).find('.action_img').attr("src", "../resources/image/icon/chk_on.png");
				} else {
					$(this).find("input[name='check_num']").prop("checked", false);
					$(this).find('.action_img').attr("src", "../resources/image/icon/chk_off.png");
	
					$('label.status_check_All').find("input[name='check_All']").prop("checked", false);
					$('label.status_check_All').find('.action_img').attr("src", "../resources/image/icon/chk_off.png");
				}
			});

			/* =========================================== */
			var htmlText = "";

			htmlText += "<select id='shipping' class='shipping'>";
			htmlText += "	<option value='kr.cjlogistics'>CJ대한통운</option>";
			htmlText += "	<option value='kr.hanjin'>한진택배</option>";
			htmlText += "	<option value='kr.logen'>로젠택배</option>";
			htmlText += "	<option value='kr.epost'>우체국 택배</option>";
			htmlText += "	<option value='kr.kdexp'>경동택배</option>";
			htmlText += "	<option value='kr.lotte'>로젠택배</option>";
			htmlText += "	<option value='kr.chunilps'>천일택배</option>";
			htmlText += "	<option value='kr.cupost'>CU편의점</option>";
			htmlText += "	<option value='kr.cvsnet'>GS Postbox 택배</option>";
			htmlText += "	<option value='kr.daesin'>대신택배</option>";
			htmlText += "</select>";
			htmlText += "<input type='text' id='shipping_txt' name='shipping_txt' value='' />";
			htmlText += "<button id='sendShipp' name='sendShipp' class='btn log'>등록</button>";

			/* ================== 제품 묶기  ================== */
			$.each(data, function(i, val) {
				var index = $('#user_List').find('tr[name=' + data[i].order_NUM + ']').index();
				var $this = $('#user_List').find('tr').eq(index);

				$this.css('border-top','2px solid #303030');
				$this.find('td:nth-child(2)').html("" + data[i].order_NUM + "");
				$this.find('td:nth-child(3)').html("" + data[i].buyer_NAME + "");
				$this.find('td:nth-child(4)').html("" + data[i].buyer_TEL + "");
				$this.find('td:nth-child(5)').find('.orderAddr').html("<p class='txt'>(" + data[i].buyer_POSTCODE + ") " + data[i].buyer_ADDR + "</p>");
				$this.find('td:nth-child(9)').addClass('cost').html("" + numberFormat(data[i].product_SHIPPING_COST) + " 원");

				if(data[i].paid_STATUS != "cancelled") {
					$this.find('td:nth-child(10)').find('.shipping_num').attr('by_mail', data[i].buyer_EMAIL);
					$this.find('td:nth-child(10)').find('.shipping_num').html(htmlText);
				}

				if($this.find('td:nth-child(10)').find('.shipping_num').attr('shipping-courier') != "null") {
					$this.find('td:nth-child(10)').find('.shipping_num').find('select[id="shipping"]').val(data[i].product_SHIPPING_NUM).attr("selected", "selected");
					$this.find('td:nth-child(10)').find('.shipping_num').find('input[name="shipping_txt"]').attr('value', data[i].product_SHIPPING_COURIER);
				}
			});

			/* ================== 이미지 존재 x ================ */
			$('div.pay_item').find('a.pay_thumb').find('img').error(function() {
				$(this).attr('src','../resources/image/icon/none.png').css('padding','10px');
			});

			/* ====================== 배송정보 등록 ======================= */
			$('button[name="sendShipp"]').click(function(e) {
				addShippingNum($(this));
			});
		}

		/* ==================== 선택 상태 변경 ====================== */
		function order_statusChange(obj) {
			var formData = new FormData();
			var arr = new Array();
			var status = $('select[name="ad_check"] option:selected').val();

			$("input[name='check_num']:checked").each(function(index, item) {
				arr[index] = $(this).attr('data-seq');
			});

			if($("input[name='check_num']").is(':checked') == false) {
				alert("주문을 선택해주세요");
				$("input[name='check_num']").focus();
				return false;
			} else {
				formData.append("status", status);
				formData.append("arr", arr);

				$.ajax({
		    		url	: '/orderStatus/change',
					type : 'POST',
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

		/* ==================== 배송정보 등록 / 수정 ====================== */
		function addShippingNum(obj) {
			var html = "";
			var formData = new FormData();
			var seq = obj.closest('div').attr('data-seq');
			var mail = obj.closest('div').attr('by_mail');
			var courier = obj.prevAll('#shipping').val();
			var courTxt = obj.prevAll('input[name="shipping_txt"]').val();

			if(!seq || !courier || !courTxt) {
				alert("잘못된 접근입니다.");
				obj.prevAll('input[name="shipping_txt"]').focus();
				return false;
			} else {
				formData.append("seq", seq);
				formData.append("mail", mail);
				formData.append("courier", courier);
				formData.append("courTxt", courTxt);

				$.ajax({
		    		url	: '/shpping/courier',
					type : 'POST',
					data : formData,
					processData: false,
					contentType: false,
					success : function(data) {
						html += "<div class='res_ok'>";
						html += "	<span>배송번호가 등록 되었습니다</span>";
						html += "</div>";

						obj.closest('div').append(html);
						$('.res_ok').fadeOut(3000, 'swing');
					}, beforeSend: function() {

					}, complete: function() {

					}, error: function(error) {
						console.log(error);
					}
				});
			}
		}

		/* ================== 이전 페이지 ================== */
		function return_before() {
			var htmlText = "";
	
			htmlText += "<tr>";
			htmlText += "	<td colspan='12' class='chk_null'><img src='../resources/image/icon/loading.gif' alt='' /></td>";
			htmlText += "<tr>";
	
			$('#user_List').html(htmlText);
		}

		/* =================== 페이징 처리 ================= */
		function page() {
			$('#paginated').remove();
	
			$('table.paginated').each(function() {
				var pagesu = 10;								// 페이지 수
				var currentPage = 0;							// 현재 페이지
				var numPerPage = 20;							// 출력 개수

				var $table = $(this);
				var numRows = $table.find('tbody tr').length;	// length로 원래 리스트의 전체길이구함
				var numPages = Math.ceil(numRows / numPerPage);	// Math.ceil를 이용하여 반올림
	
				if (numPages == 0) return;						// 리스트가 없으면 종료
	
				var $pager = $('<td colspan="12" id="paginated"><div class="pager"></div></td>'); // pager라는 클래스의 div엘리먼트 작성						  
				var nowp = currentPage;
				var endp = nowp + 10;
	
				$table.bind('repaginate', function() {
					$table.find('tbody tr').hide().slice(currentPage * numPerPage, (currentPage + 1) * numPerPage).show();
					$('#paginated').html('');
	
					if (numPages > 1) {											// 한페이지 이상이면
						if (currentPage < 5 && numPages - currentPage >= 5) {	// 현재 5p 이하이면
							nowp = 0;											// 1부터 
							endp = pagesu;										// 10까지
						} else {
							nowp = currentPage - 5;								// 6넘어가면 2부터 찍고
							endp = nowp + pagesu;								// 10까지
							pi = 1;
						}
	
						if (numPages < endp) {									// 10페이지가 안되면
							endp = numPages;									// 마지막페이지를 갯수 만큼
							nowp = numPages-pagesu;								// 시작페이지를   갯수 -10
						}
	
						if (nowp < 1) {											// 시작이 음수 or 0 이면
							nowp = 0;											// 1페이지부터 시작
						}
					} else {													// 한페이지 이하이면
						nowp = 0;												// 한번만 페이징 생성
						endp = numPages;
					}
	
					// First
					$('<span name="firstPage"></span>').bind('click', { newPage: page }, function(event) {
						currentPage = 0;
						$table.trigger('repaginate');
					}).appendTo($pager).addClass('pg_page');
	
					// Prev
					$('<span name="prevPage"></span>').bind('click', { newPage: page },function(event) {
						if(currentPage == 0) return;
						currentPage = currentPage - 1;
						$table.trigger('repaginate');
					}).appendTo($pager).addClass('pg_page');
	
					// [1,2,3,4,5,6,7,8]
					for (var page = nowp ; page < endp; page++) {
						$('<span name="numPage"></span>').text(page + 1).bind('click', { newPage: page }, function(event) {
							currentPage = event.data['newPage'];
							$table.trigger('repaginate');
						}).appendTo($pager).addClass('pg_page');
					}
	
					// Next
					$('<span name="nextPage"></span>').bind('click', { newPage: page },function(event) {
						if(currentPage == numPages-1) return;
						currentPage = currentPage + 1;
						$table.trigger('repaginate'); 
					}).appendTo($pager).addClass('pg_page');
	
					// Last
					$('<span name="lastPage"></span>').bind('click', { newPage: page },function(event) {
						currentPage = numPages - 1;
						$table.trigger('repaginate');
					}).appendTo($pager).addClass('pg_page');
				});
	
				$pager.appendTo($table);
				$table.trigger('repaginate');
			});
		}

		/* ================== 콤마 세자리 ================== */
		function numberFormat(inputNumber) {
			return inputNumber.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
		}
		
	});
});