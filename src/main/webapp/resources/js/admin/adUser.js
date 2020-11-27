jQuery.noConflict();

jQuery(function($) {
	$(document).ready(function() {

		memberList();
	
		/* =================== 회원가입 인원 조회 ================= */
		$('button[name="sendPerson"]').click(function() {
			memberList();
		});
	
		/* =================== 회원가입 인원 ================= */
		function memberList() {
			var formData = new FormData();
	
			var search_tit = $('select[name="searchOption"] option:selected').val();	// 조회 카테고리
			var search_txt = $('input[name="searchTxt"]').val();						// 검색
	
			formData.append("search", search_tit);
			formData.append("keyword", search_txt);
	
			$.ajax({
	    		url	: '/person/list',
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
			var htmlText = "";
			var len = data.length;
	
			if(len > 0) {
				for( var i = 0; i < len; i++ ) {
					var img = new Image();
					var auth = data[i].member_authstatus;
					var profile = data[i].member_Profile;

					htmlText += "<tr class='mem_" + i + "'>";
					htmlText += "	<td>" + data[i].member_Id + "</td>";
					htmlText += "	<td>" + data[i].member_Name + "</td>";
					htmlText += "	<td>" + data[i].member_Phone + "</td>";
					htmlText += "	<td>" + numberFormat(data[i].member_Payment_Count) +" 원</td>";
					htmlText += "	<td>" + data[i].member_Apply_Count +"</td>";
					htmlText += "	<td>" + data[i].memeber_login_Count +"</td>";

					if( auth == "1" ) {
						htmlText += "	<td><p class='authTrue'>Yes</p></td>";
					} else {
						htmlText += "	<td><p class='authFalse'>No</p></td>";
					}

					htmlText += "	<td>" + data[i].register_Date + "</td>";
					htmlText += "</tr>";
				}
			} else {
				htmlText += "<tr>";
				htmlText += "	<td colspan='6' class='chk_null'>검색결과가 존재하질 않습니다.</td>";
				htmlText += "<tr>";
			}
	
			$('#member_List').html(htmlText);	// 출력
			page();								// 페이징 처리
		}
	
		/* =================== 페이징 처리 ================= */
		function page() {
			$('#paginated').remove();
	
			$('table.paginated').each(function() {
				var pagesu = 10;
				var currentPage = 0;
				var numPerPage = 10;
	
				var $table = $(this);
				var numRows = $table.find('tbody tr').length;	// length로 원래 리스트의 전체길이구함
				var numPages = Math.ceil(numRows / numPerPage);	// Math.ceil를 이용하여 반올림
	
				if (numPages == 0) return;						//리스트가 없으면 종료
	
				var $pager = $('<td colspan="11" id="paginated" style="background-color: #fff;"><div class="pager"></div></td>'); // pager라는 클래스의 div엘리먼트 작성						  
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
