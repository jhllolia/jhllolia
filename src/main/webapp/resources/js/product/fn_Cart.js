jQuery.noConflict();

jQuery(function($) {
	$(document).ready(function() {
		
		cart_check();

		/* =========================================== */
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

		/* =========================================== */
		$('button[name="link_lct"]').click(function() {
			location.href="/info/info_update";
		});
		
		$('label[name="product_check"]').click(function() {
			cart_result_change($(this));
		});

		$('label[id="delete_product"]').click(function() {
			cart_delete_product($(this));
		});

		$('button[name="cart_other"]').click(function() {
			cart_qty_change($(this));
		});

		$('button[name="send_Cart"]').click(function() {
			cart_product_payment();
		});

		$('button[name="cart_del"]').click(function() {
			cart_product_delete($(this));
		});

		/* =========================================== */
		function cart_qty_change(obj) {
			var formData = new FormData();
			var num = obj.attr('data-num');
			var name = obj.closest('tr').find('td.name').text();
			var option = obj.closest('tr').find('td.option').text();
			var qty = obj.closest('tr').find('td.qty').find('input[name="qty"]').val();

			if(!num || !name || !option || !qty) {
				alert("잘못된 접근입니다.");
				location.reload();
			} else {
				formData.append("num", num);
				formData.append("name", name);
				formData.append("option", option);
				formData.append("qty", qty);

				$.ajax({
					type: "POST",
					url	: "/update/qty",
					dataType : 'json',
					data : formData,
					processData: false,
					contentType: false,
					success : function(data) {
						if(data != "0") {
							alert("" + name + "의 수량이 변경되었습니다.");
						} else {
							alert("잘못된 접근 입니다.");
							location.reload();
						}
					}, error: function(error) {
						console.log(error);
					}
				});
			}
		}

		/* =========================================== */
		function cart_check() {
			window.onhashchange = function() {
				$("input:checkbox[name='status_check']").prop("checked", false);
				$("img.status_chk").attr("src", "../resources/image/icon/chk_off.png");
			}

			/* ================== 전체 선택/취소 ================== */
			$('label#product_all').bind('click', function(e) {
				if($(this).find("#unit_all").is(':checked')) {
					$("input:checkbox[name='status_check']").prop("checked", true);
					$("img.status_chk").attr("src", "../resources/image/icon/chk_on.png");

					$(this).find('input[type="checkbox"]').prop("checked", true);
					$(this).find('img').attr("src", "../resources/image/icon/chk_on.png");
				} else {
					$("input:checkbox[name='status_check']").prop("checked", false);
					$("img.status_chk").attr("src", "../resources/image/icon/chk_off.png");

					$(this).find('input[type="checkbox"]').prop("checked", false);
					$(this).find('img').attr("src", "../resources/image/icon/chk_off.png");
				}
				
				calculatePrice();
			});

			/* ================== 부분 선택/취소  ================== */
			$('label[name="product_check"]').click( function(e) {
				var len = $("input:checkbox[name='status_check']").length;
				var len_chk = $('input[name="status_check"]:checked').length;

				if(len == len_chk) {
					$('label#product_all').find('input[name="unit_all"]').prop("checked", true);
					$('label#product_all').find('img').attr("src", "../resources/image/icon/chk_on.png");
				} else {
					$('label#product_all').find('input[name="unit_all"]').prop("checked", false);
					$('label#product_all').find('img').attr("src", "../resources/image/icon/chk_off.png");
				}

				/* ========== 전체선택 해제 ======== */
				if($(this).find("input[name='status_check']").is(':checked')) {
					$(this).find("input[name='status_check']").prop("checked", true);
					$(this).find('img').attr("src", "../resources/image/icon/chk_on.png");
				} else {
					$(this).find("input[name='status_check']").prop("checked", false);
					$(this).find('img').attr("src", "../resources/image/icon/chk_off.png");

					$('label#product_all').find("input:checkbox[name='unit_all']").prop("checked", false);
					$('label#product_all').find("img").attr("src", "../resources/image/icon/chk_off.png");
				}
				
				calculatePrice();
			});
		}

		/* ==========  ======== */
		function cart_product_payment() {
			var form = "";
			var arr = new Array();

			if($('input:checkbox[name="status_check"]:checked').length == 0) {
				alert("구매할 제품을 선택해주세요");
				return false;
			} else {
				$('input:checkbox[name="status_check"]:checked').closest('tr').each(function(index, item) {
					var obj = new Object();

					obj.seq = $(this).find('td.qty').find('input[id="qty"]').attr('data-num');
					obj.order = $(this).find('td.check').find('input[name="status_check"]').attr('value');
					obj.num = $(this).attr('name');
					obj.qty = $(this).find('td.qty').find('input[name="qty"]').val();

					arr[index] = obj;
				});
			}

			form += "<form action='../cart/orderDetail' method='POST'>"; 
			form += "	<input type='hidden' name='arr' value='" + JSON.stringify(arr) + "' />";
			form += "</form>";

			$(form).appendTo("body").submit().remove();	// 결제 전 페이지로 이동
		}

		/* ==========  ======== */
		function cart_delete_product(obj) {
			var formData = new FormData();
			var arr = new Array();

			if($('input:checkbox[name="status_check"]:checked').length == 0) {
				alert("선택된 상품이 없습니다.");
				return false;
			} else {
				var ex = confirm("선택 된 " + $('input:checkbox[name="status_check"]:checked').length + "개의 상품을 삭제하시겠습니까?");

				if(ex == true) {
					$('input:checkbox[name="status_check"]:checked').each(function(index, item) {
						arr[index] = $(this).attr('value');
					});

					formData.append("arr", arr);

					$.ajax({
						type: "POST",
						url	: "/unit/delete",
						dataType : 'json',
						data : formData,
						processData: false,
						contentType: false,
						success : function(data) {
							if(data != 0) {
								location.reload();
							} else {
								alert("잘못된 접근입니다");
								location.reload();
								return false;
							}
						}, error: function(error) {
							console.log(error);
						}
					});
				}
			}
		}

		/* ==========  ======== */
		function cart_product_delete(obj) {
			var formData = new FormData();
			var arr = obj.attr('data-num');
			var ex = confirm("해당 상품을 삭제하시겠습니까?");

			if(typeof arr == "" || typeof arr == 0) {
				alert("잘못된 접근입니다");
				location.reload();
				return false;
			} else {

				if(ex == true) {
					formData.append("arr", arr);

					$.ajax({
						type: "POST",
						url	: "/unit/delete",
						dataType : 'json',
						data : formData,
						processData: false,
						contentType: false,
						success : function(data) {
							if(data != 0) {
								location.reload();
							} else {
								alert("잘못된 접근입니다");
								location.reload();
								return false;
							}
						}, error: function(error) {
							console.log(error);
						}
					});
				}
			}
		}

		/* ================ + =============== */
		$('span.add-item').click(function(e) {
			var $this = $(this).prev('.item-count').find('input');
			var count = parseInt($this.val());

			count++;

			$this.val(count);
			calculatePrice();
		});

		/* ================ - =============== */
		$('span.subtract-item').click(function(e) {
			var $this = $(this).next('.item-count').find('input');
			var count = parseInt($this.val());

			count--;

			if(count < 1) {
				alert('상품개수는 1이상 입력해 주십시오.');
				count = 1;
			}

			$this.val(count);
			calculatePrice();
		});

		/* =========================================== */
		function cart_result_change(obj) {
			var totalprice = 0;
			var delivery = 3000;

			$('input:checkbox[name="status_check"]:checked').closest('tr').each(function() {
				var itemprice = parseInt($(this).find('td.qty').find('span#price').text().replace(/[^0-9]/g, ''));
				var itcnt = parseInt($(this).find('td.qty').find('input[id="qty"]').val());

				totalprice += (itemprice * itcnt);
			});

			if(totalprice >= Number(30000)) {
				delivery = 0;
			}

			$('span.cart_count').text($('input:checkbox[name="status_check"]:checked').length);
			$('#order_price').text(numberFormat(totalprice));
			$('#delivery_price').text(numberFormat(delivery));

			$('#total_price').text(numberFormat(totalprice + delivery));
		}

		/* ================== 총 금액 =============== */
		calculatePrice();

		function calculatePrice() {
			var totalprice = 0;
			var cart_count = 0;
			var delivery = 3000;

			if($('input:checkbox[name="status_check"]:checked').length > 0) {
				$('input:checkbox[name="status_check"]:checked').each(function() {
					var itemprice = parseInt($(this).closest('tr').find('td.qty').find('span#price').text().replace(/[^0-9]/g, ''));	// 
					var itcnt = parseInt($(this).closest('tr').find('td.qty').find('input[id="qty"]').val());							// 

					totalprice += (itemprice * itcnt);
				});

				if(totalprice >= Number(30000)) {
					delivery = 0;
				}

				cart_count = $('input:checkbox[name="status_check"]:checked').length;
			} else {
				delivery = 0;
			}

			$('.cart_count').text(numberFormat(cart_count));
			$('#order_price').text(numberFormat(totalprice));
			$('#delivery_price').text(numberFormat(delivery));
			$('#total_price').text(numberFormat(totalprice + delivery));
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
