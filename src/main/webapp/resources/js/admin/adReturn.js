jQuery.noConflict();

jQuery(function($) {
	$(document).ready(function() {

		dashDate();
		list();

		/* ====================== 사용자 프로필 ======================= */
		$('#sendDate').click( function(e) {
			list();
		});

		function list() {
			var formData = new FormData();

			formData.append("before", $("input[name='firstDate']").val());
			formData.append("after", $("input[name='lastDate']").val());
			formData.append("search", $('select[name="searchOption"] option:selected').val());
			formData.append("keyword", $('input[name="payment_txt"]').val());

			$.ajax({
	    		url	: '/return/data',
				type : 'POST',
				data : formData,
				processData: false,
				contentType: false,
				success : function(data) {
					return_check(data);
				}, beforeSend: function() {

				}, complete: function() {
	
				}, error: function(error) {
					console.log(error);
				}
			});
		}

		function return_check(data) {
			var html = "";

			if(isEmpty(data) == true) {
				html += "<tr>";
				html += "	<td colspan='7'>검색된 데이터가 없습니다.</td>";
				html += "</tr>";
			} else {

				$.each(data, function(key, val) {
					var arr = new Array();
					var paidState = val[0].paid_STATUS;
					var returnState = val[0].buyer_RETURN_STATE;

					html += "<tr>";
					html += "	<td>" + val[0].order_NUM + "</td>";
					html += "	<td class='product'>";

					$.each(val, function(i, state) {
						html += "<span class='order_check' name='order_name_" + i + "'>" + state.product_NAME + "</span>";
					});

					html += "	</td>";
					html += "	<td>" + val[0].buyer_TEL + "</td>";

					if(paidState == "shipping_return") {
						html += "<td>반품요청</td>";
					} else if(paidState == "shipping_exchange") {
						html += "<td>교환요청</td>";
					} else {
						html += "<td color='red'>오류</td>";
					}

					if(returnState == "A") {
						html += "<td>고객변심</td>";
					} else if(returnState == "B") {
						html += "<td>배송지연</td>";
					} else if(returnState == "C") {
						html += "<td>배송오류</td>";
					} else if(returnState == "D") {
						html += "<td>배송불가지역</td>";
					} else if(returnState == "E") {
						html += "<td>포장불량</td>";
					} else if(returnState == "F") {
						html += "<td>상품 불만족</td>";
					} else if(returnState == "G") {
						html += "<td>상품 정보상이</td>";
					} else if(returnState == "H") {
						html += "<td>상품 불량</td>";
					} else if(returnState == "I") {
						html += "<td>서비스 불만족</td>";
					} else if(returnState == "J") {
						html += "<td>품절</td>";						
					} else {
						html += "<td color='red'>ADMIN 선택</td>";						
					}

					$.each(val, function(i, v) {
						arr.push(v.pay_SEQ);
					});

					html += "	<td>" + val[0].buyer_RETURN_CONTENT + "</td>";
					html += "	<td>";
					html += "		<div class='exchange'>";
					html += "			<button name='payChange' data-order='" + val[0].order_NUM + "' data-arr='" + arr + "' id='payChange'>결제취소</button>";
					html += "		</div>";
					html += "	</td>";
					html += "</tr>";
				});
			}

			$('#user_list').html(html);
			page();

			$('button[name="payChange"]').click(function() {
				adminCancel($(this));
			});
		}

		/* ======================  ======================= */
		function adminCancel(obj) {
			var formData = new FormData();
			var size = obj.closest('tr').find('td.product').find('span').length;
			var order = obj.attr('data-order');
			var arr = obj.attr('data-arr');
			var con = confirm("" + size + " 개의 상품 취소 완료 후에는 취소 철회가 불가합니다. 취소를 하시겠습니까?");

    		if(con == true) {
    			formData.append("order", order);
    			formData.append("size", size);
    			formData.append("arr", arr);

    			$.ajax({
    				url	: '/admin/refund',
    				type : 'POST',
    				data : formData,
    				processData: false,
    				contentType: false,
    				success : function(data) {
						alert(data.message);
						location.reload();
    				}, error: function(error) {
						console.log(error);
    				}
    			});
    		}
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