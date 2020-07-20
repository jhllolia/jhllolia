jQuery.noConflict();

jQuery(function($) {
	$(document).ready(function() {

		review();	// review 리스트

		/* ================================== */
		function review() {
			var formData = new FormData();
			formData.append("tab", $('input[name="order_sch"]').val());

			$.ajax({
	    		url	: '/review/product',
				type : 'POST',
				dataType: 'json',
				data : formData,
				processData: false,
				contentType: false,
				success : function(data) {
					review_list(data);
				}, beforeSend: function() {

				}, complete: function() {

				}, error: function(error) {
					console.log(error);
				}
			});
		}

		/* ================================== */
		function review_list(data) {
			var html = "";

			if(isEmpty(data) == true) {
				html += "<tr>";
				html += "	<td colspan='4'>검색결과가 존재하질 않습니다</td>";
				html += "</tr>";
			} else {

				$.each(data, function(key, val) {
					var json = JSON.parse(val.VIEW_UPLOAD);
					var star = val.VIEW_POINT;

					html += "<tr>";
					html += "	<td class='star'>";
					html += "		<div class='in'>";

					for (var i = 0; i < star; i++) {
						html += "★";
					}

					html += "		</div>";
					html += "		<div class='in_back'>★★★★★</div>";
					html += "	</td>";
					html += "	<td class='desc_txt'>";
					html += "		<div class='in'>";

					if(json.length > 0) {
						html += "<i class='has'><img src='../resources/image/icon/art.png' alt='" + json.length + "' /></i>";
					}

					html += "			<span id='clickView'>" + val.VIEW_CONTENT.replace(/<(\/)?([a-zA-Z]*)(\s[a-zA-Z]*=[^>]*)?(\s)*(\/)?>/ig, " ") + "</span>";
					html += "		</div>";
					
					/*
					
					html += "		<div class='sel'>";
					html += "			<span class='op'>" + val.PRODUCT_NAME + "</span>";
					html += "			<span class='op'>" + val.PRODUCT_OPTION + "</span>";
					html += "			<span class=''>" + val.PRODUCT_QTY + " 개</span>";
					html += "		</div>";
					
					*/

					html += "	</td>";
					html += "	<td class='user'>" + val.REVIEW_ID + "</td>";
					html += "	<td class='date'>" + val.VIEW_DATE + "</td>";
					html += "</tr>";
					html += "<tr class='read'>";
					html += "	<td colspan='4'>";
					html += "		<div class='review_wrap'>";
					html += "			<div class='review_txt'>" + val.VIEW_CONTENT + "</div>";
					html += "			<div class='review_img'>";

					$.each(json, function(i, v) {
						html += "<div data-tit='" + v.FILE_TITLE + "' class='upload'>";
						html += "	<img src='../resources/upload/review/" + v.FILE_NAME + "' alt='" + v.FILE_SIZE + "' />";
						html += "</div>";
					});

					html += "			</div>";
					html += "		</div>";
					html += "	</td>";
					html += "</tr>";
				});
			}

			$('#review').html(html);
			page();
			none_img();

			/* ================= 클릭 이미지 확대 ===================== */
			$('.upload').click(function(e) {
				uploadWide($(this));
			});

			/* ================= 리뷰 이미지 확대 =============== */
			$('span[id="clickView"]').click(function(e) {
				e.preventDefault();

				$('tr.read').addClass('off');
				var $this = $(this).closest('tr').next('tr.read');

				if($this.hasClass('on')) {
					$this.addClass('off').removeClass('on');
				} else {
					$this.removeClass('off').addClass('on');
				}
			});
		}

		/* ====================== 클릭 이미지 확대 ======================= */
		function uploadWide(obj) {
			var html = "";
			var img = obj.find('img').attr('src');

			html += "<div class='payStatus'>";
			html += "	<span id='close'><img src='../resources/image/icon/close_icon.png' alt='' /></span>";
			html += "	<img id='clickFile' src='" + img + "' alt='' />";
			html += "</div>";

			$('.display-none').css('display', 'block');
			$('.wrap-loading').html(html);

			$('span#close').click(function() {
				$('.display-none').css('display', 'none');
				$('.wrap-loading div').remove();
			});
		}

		/* =================== 페이징 처리 ================= */
		function page() {
			$('#review').find('tr').removeClass('on');
			$('#paginated').remove();
	
			$('table.paginated').each(function() {
				var pagesu = 10;								// 페이지 수
				var currentPage = 0;							// 현재 페이지
				var numPerPage = 10;							// 출력 개수

				var $table = $(this);
				var numRows = $table.find('tbody tr').length;	// length로 원래 리스트의 전체길이구함
				var numPages = Math.ceil(numRows / numPerPage);	// Math.ceil를 이용하여 반올림
	
				if (numPages == 0) return;						// 리스트가 없으면 종료
	
				var $pager = $('<td colspan="4" id="paginated"><div class="pager"></div></td>'); // pager라는 클래스의 div엘리먼트 작성						  
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

		/* ================== 객체 존재 여부 ================== */
		function isEmpty(obj) {
			return Object.keys(obj).length === 0;
		}

		/* =============================================================== */
		function number_format(x) {
			return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");	
		}
	});
});