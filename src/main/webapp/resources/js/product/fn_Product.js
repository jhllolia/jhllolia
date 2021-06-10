jQuery.noConflict();

jQuery(function($) {
	$(document).ready(function() {
		/* ======================== 메인 슬라이드 ======================= */
		var intv;
		var current = 0;
		var sIdx = 0;
		var sCnt = $('#top_slider').find('div').length;

		function startTopSlider() {
			/* =============== prev =============== */
			$('a#prev').click(function(e) {
				e.preventDefault();
				$("#ul_btns").find("li").eq( --current % sCnt ).click();	// 클릭 Action
			});

			/* =============== next =============== */
			$('a#next').click(function(e) {
				e.preventDefault();
				$("#ul_btns").find("li").eq( ++current % sCnt ).click();	// 클릭 Action
			});

			/* =============== auto slide =============== */
			intv = setInterval(function () {
				$("#ul_btns").find("li").eq( ++current % sCnt ).click();	// 클릭 Action
			}, 10000);
		}

		function setBnr(idx, bnr, allTab, addCls) {
			$(bnr).css("opacity", "0").eq(idx).css("opacity", "1");			// 
			$(allTab).removeClass(addCls);									// Slide 사라짐 효과
			$(allTab).eq(idx).addClass(addCls);								// Slide 사라짐 효과
		}

		$("#top_0").css("opacity", "1");
		$("#btn_0").addClass("on");

		startTopSlider();

		$("#ul_btns").find("li").click(function() {
			var idx = $(this).attr("id").split("_")[1];
			var bnr = $("#top_slider > div");
			var allTab = $("#ul_btns > li");
	
			setBnr(idx, bnr, allTab, "on");
		});

		/* ======================= 바로 구매 ======================= */
		$('button[id="sendData"]').click(function(e) {
			sendReserve();
		});

		/* ======================= 장바구니 ======================= */
		$('button[id="sendBasket"]').click(function(e) {
			sendCart();
		});

		/* ========================================================= */
		addProduct();

		/* ======================== 제품 선택 (1) ======================= */
		function addProduct() {
			/* ================== 다중 제품 =============== */
			$('#addProduct').change(function(e) {
				$('.cartAdd').css('display','none');							// 장바구니 팝업 초기화
				$('.p_total > dt.wrap').css('display','none');					// 

				var idx = $("#addProduct option").index($("#addProduct option:selected"));	// 선택된 값

				// selectBox 첫번째 클릭시 전체 삭제
				if(idx == 0) {
					$('.p_result ul').find('li').remove();
				} else {

					var html = "";
					var option = "";
					
					var id = $(this).val();											// 
					var name = $(this).find('option:selected').text();				// 
					var state = $(this).find('option:selected').attr('data-state');	// 
					var seq = $(this).find('option:selected').attr('seq');			// 
					var price = $(this).find('option:selected').attr('price');		// 
					var sale = $(this).find('option:selected').attr('sale');		// 
					
					html += "<li name='check_" + seq + "' class='" + id + "'>";
					html += "	<div class='option'>";
					html += "		<div class='option_result'>";
					html += "			<span id='o_chk'>" + name + "</span>";
					html += "		</div>";
					html += "		<div class='quantity buttons_added'>";
					html += "			<span class='subtract-item'>-</span>";
					html += "			<span class='item-count'>";
					html += "				<input type='text' id='qty' class='qty' name='itemcount[]' value='1' readonly />";
					html += "			</span>";
					html += "			<span class='add-item'>+</span>";
					html += "		</div>";
					html += "		<div class='option_chk'>";
					html += "			<span id='price_result'>" + number_format(price) + " 원</span>";		// sale price
					html += "			<span class='option-delete'><img src='../resources/image/main/close.png' alt='rest' /></span>";
					html += "		</div>";
					html += "	</div>";
					html += "</li>";
					
					var alreadyCheck = true;
					
					/* ================== already selected option =============== */
					for(var k = 0; k < $('.p_result > ul').find('li').length; k++) {
						var already = $('.p_result > ul').find('li:eq(' + k + ')').find('#o_chk').text().trim();

						if(already == name) {
							alert('이미 선택된 옵션입니다.');
							$(this).find('option:first').attr('selected', 'selected');
							return;
						}
					}
					
					/*
					
					var temp 	= new Array();
					var obj 	= $('select[name="addProduct"]');
					var result 	= false;
	
					$(obj).each(function(i) {
						temp[i] = $(this).val();
					});
	
					$(temp).each(function(i) {
						var x = 0;
	
						$(obj).each(function(i) {
							if(temp[i] == $(this).val()) {
								x++;
							}
						});
	
						if(x > 1) {
							alert('이미 선택된 옵션입니다.');
							result = true;
							return false;
						}
					});

					 */

					/* ================== add li =============== */
					if(state != "N") {
						$('.p_result > ul').append(html); 			// li add
						$('.p_total dt').css('display','block'); 	// total price add

						calculatePrice();
					} else {
						alert('품절된 제품입니다.');
						return false;
					}
				}
			});

			/* ================== + =============== */
			$(document).on("click", "span.add-item", function(e) {
				var $cntinput = $(this).closest('li').find('input');		// click event
				var count = parseInt($cntinput.val());						// now count (+)

				count++;

				$cntinput.val(count);
				calculatePrice();
			});

			/* ================== - =============== */
			$(document).on("click", "span.subtract-item", function(e) {
				var $cntinput = $(this).closest('li').find('input');		// click event
				var count = parseInt($cntinput.val());						// now count (-)

				count--;

				if(count < 1) {
					alert('상품개수는 1이상 입력해 주십시오.');
					count = 1;
				}

				$cntinput.val(count);
				calculatePrice();
			});

			/* ================== delete =============== */
			$(document).on("click", "span.option-delete", function(e) {
				$(this).closest('li').remove();								// delete click event
				var resultSize = $('.p_result > ul').find('li').length;		// prevent overlap

				/* ================== delete => li size 0 =============== */
				if(resultSize == 0) {
					$('.p_total').css('display','none');
				}

				calculatePrice();
			});
		}

		/* ================== 총 금액 =============== */
		function calculatePrice() {
			var totalprice = 0;

			$('.p_result > ul li').each(function() {
				var itemprice = parseInt($(this).find('#price_result').text().replace(/[^0-9]/g, ''));	// selected item
				var itcnt = parseInt($(this).find('#qty').val());										// item count

				totalprice += (itemprice * itcnt);
			});

			$('#total_price').text(number_format(totalprice) + ' 원');	// result
		}

		/* ======================================== */
		$('button[name="user_qna"]').click(function(e) {
			var form = "";
			var id = $(this).attr('data-seq');

			if(!id) {
				var error = '<p class="txt">테이블 결제를 위해서는 로그인 하셔야합니다.</p><button name="linkLogin" class="btn log">로그인</button>'; 
				reserveError(error);
				return false;
			} else {
				form += "<form action='../linkQna' method='POST'>"; 
				form += "	<input type='hidden' name='id' value='" + id + "' />";
				form += "</form>";

				$(form).appendTo("body").submit().remove();		// 조회
			}
		});

		/* ======================================== */
		$('a.clickable').click(function(e) {
			e.preventDefault();
			var obj = $('#' + $(this).attr('data-target') + '').offset();

			$('html, body').animate({
				scrollTop : obj.top - $('ul.content_menu').height()
			}, 400);
		});

		$(window).resize(function() {
			var m_width = $(window).width();

			subMenu(m_width);
		}).resize();

		/* ======================================== */
		function subMenu(width) {
			var container = $('.tab_container').outerHeight();
			var position = parseInt($('ul#content_menu').css('top'));
			var y_pos = $('ul#content_menu'), 
				slide = y_pos.offset().top;

			$(window).scroll(function(e) {
				var scroll = $(this).scrollTop();
				var obj = scroll + $('ul.content_menu').height() + 5;

				var a = $(".s_content > div#target_1").offset();	// 상품상세정보
				var b = $(".s_content > div#target_2").offset();	// 리뷰
				var c = $(".s_content > div#target_3").offset();	// 상품 QnA
				var d = $(".s_content > div#target_4").offset();	// 배송 반품 교환

				$("a[data-target^='target_']").removeClass('on').addClass('off')

				if(obj >= a.top && obj <= b.top) $("a[data-target='target_1']").removeClass('off').addClass('on');
				if(obj >= b.top && obj <= c.top) $("a[data-target='target_2']").removeClass('off').addClass('on');
				if(obj >= c.top && obj <= d.top) $("a[data-target='target_3']").removeClass('off').addClass('on');
				if(obj >= d.top) $("a[data-target='target_4']").removeClass('off').addClass('on');

				if(scroll > slide) {
					y_pos.removeClass('down').addClass('up');
				} else if(scroll != slide) {
					y_pos.removeClass('up').addClass('down');
				}
			}).scroll();
		}

		/* ========================== 장바구니 저장 ======================== */
		function sendCart() {
		    var formData = new FormData();
		    var arr = new Array();
		    var order = $('input[name="order_sch"]').val();
			var cart_count = Number($("span#cart_num").html());

	    	if($('.p_result').find('li').length == 0) {
	    		var error = '<p class="txt">제품을 선택해주세요.</p>';
	    		reserveError(error);
	    		return false;
	    	} else {
	    		/* ====== 로그인 체크 ===== */
				var check = loginCheck();

				if(!check) {
    				error = '<p class="txt">장바구니를 이용하기 위해서는 로그인이 필요합니다.</p><button name="linkLogin" class="btn log">로그인</button>'; 
    				reserveError(error);
    				return false;
				} else {

					$("li[name^='check_']").each(function(index, item) {
				    	var obj = new Object();

				    	if(!$(this).attr('name') || !$(this).find('div.quantity').find('.item-count').find('input').val()) {
				    		location.reload();
				    	} else {
					    	obj.num = $(this).attr('name');
					    	obj.qty = $(this).find('div.quantity').find('.item-count').find('input').val();

					    	arr[index] = obj;
				    	}
					});

					formData.append("order", order);
					formData.append("arr", JSON.stringify(arr));

		    		$.ajax({
			    		url	: '/cart/product',
						type : 'POST',
						data : formData,
						processData: false,
						contentType: false,
						success : function(data) {
							$('.cartAdd').css('display','block');				// 팝업 block
							$('.p_total > dt.wrap').css('display','none');		// 총 금액 remove

							if(data == 0) {
								$('#cart_result').html("이미 장바구니에 해당 제품이 존재합니다.");
							} else if(data > 0) {
								$('#cart_result').html("" + data + " 개의 상품이 장바구니에 저장되었습니다.");
							}

							arr.length = 0;																			// 배열 초기화
							$('select[name="addProduct"]').val($("select[name='addProduct'] option:first").val());	// 선택 제품 초기화
							$('.p_result ul').html("");																// 선택 제품 초기화
							$("span#cart_num").html(Number(cart_count + data));										// 수량 변경
						}, beforeSend: function() {

						}, complete: function() {

						}, error: function(error) {
							console.log(error);
						}
					});
				}
	    	}
		}

		/* ========================== 장바구니 팝업종료 ======================== */
		$('#shopping').click(function() {
			$('.cartAdd').css('display','none');
		});

		/* ========================== 테이블 결제로 이동 ======================== */
	    function sendReserve() {
			$('.cartAdd').css('display','none');

		    var formData = new FormData();
			var arr = new Array();

	    	if($('.p_result').find('li').length == 0) {
	    		var error = '<p class="txt">제품을 선택해주세요.</p>';
	    		reserveError(error);
	    		return false;
	    	} else {
	    		/* ====== 로그인 체크 ===== */
				var check = loginCheck();

				if( !check ) {
    				error = '<p class="txt">테이블 결제를 위해서는 로그인 하셔야합니다.</p><button name="linkLogin" class="btn log">로그인</button>'; 
    				reserveError(error);
    				return false;
				} else {
    				var form = "";
			    	var profile = $('#top_slider').find('div').eq(0).attr('data-single');
			    	var seq = $('#sendData').attr('seq');

					$("li[name^='check_']").each(function(index, item) {
				    	var obj = new Object();
				    	var unitNum = $(this).attr('name');
				    	var unitQty = $(this).find('div.quantity').find('.item-count').find('input').val();

				    	if(!unitNum || !unitQty || !$('#sendData').attr('seq') || !$('input[name="order_sch"]').val()) {
				    		location.reload();
				    	} else {

							obj.seq = $('#sendData').attr('seq');
							obj.order = $('input[name="order_sch"]').val();
					    	obj.num = unitNum;
					    	obj.qty = unitQty;

					    	arr[index] = obj;
				    	}
					});

    				form += "<form action='../pay/orderDetail' method='POST'>"; 
    				form += "	<input type='hidden' name='seq' value='" + seq + "' />";
    				form += "	<input type='hidden' name='arr' value='" + JSON.stringify(arr) + "' />";
    				form += "</form>";

    				$(form).appendTo("body").submit().remove();	// 결제 전 페이지로 이동
				}
	    	}
	    }
	
		/* ========================== 선택 에러 ======================== */
	    function reserveError(obj) {
	    	var html = "";
	
			html += '<div id="re-Error" class="error">';
			html += '	<span id="close"><img src="../resources/image/icon/close.png" alt="delete" /></span>';
			html += '	<h4><img src="../resources/image/icon/error.png" alt="errorIcon" />구매 오류</h4>';
			html += '	<hr />';
			html += '	' + obj + '';
			html += '</div>';
	
			$('.wrap-loading').removeClass('display-none');
			$('.wrap-loading').append(html);
	
			$('.wrap-loading').click( function(e) {
				var $target = $(e.target).find('div').hasClass('error');		// 존재 여부
	
				if( $target == true ) {
					$('.wrap-loading').addClass('display-none');
					$('.wrap-loading').find('div').remove();
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

		/* ======================= 로그인 유무 ======================== */
	    function loginCheck() {
	    	var m = "";

			$.ajax({
				type: "POST",
    			url: "/check/login",
				processData: false,
				async: false,
				success: function(data) {
					m = data;
				}, error : function(request, status, error) {
					console.log(error);
				}
			});
	
			return m;
	    }

		/* =============================================================== */
		function number_format(x) {
			return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");	
		}

	});
});