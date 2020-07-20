jQuery.noConflict();

jQuery(function($) {
	$(document).ready(function() {

		none_img();

		var mobile = (/iphone|ipad|ipod|android/i.test(navigator.userAgent.toLowerCase())); 

		if(mobile) {
			console.log("mobile : " + mobile);
		}

		/* ================== 리뷰 수정 ==================== */
		$('button[id="rev_update"]').click(function(e) {
			fn_review_update($(this));
		});

		/* =================== 리뷰 삭제 ================== */
		$('button[id="rev_delete"]').click(function(e) {
			fn_review_delete($(this));
		});

		function fn_review_update(obj) {
			var form = "";

			form += "<form action='./rev_update' method='POST'>"; 
			form += "	<input type='hidden' name='order_num' value='" + obj.attr('data-num') + "' />";
			form += "	<input type='hidden' name='order_product' value='" + obj.attr('data-product') + "' />";
			form += "	<input type='hidden' name='order_name' value='" + obj.attr('data-name') + "' />";
			form += "	<input type='hidden' name='order_option' value='" + obj.attr('data-sub') + "' />";
			form += "	<input type='hidden' name='order_qty' value='" + obj.attr('data-qty') + "' />";
			form += "</form>";

			$(form).appendTo("body").submit().remove();
		}

		function fn_review_delete(obj) {
			var formData = new FormData();
			var num = obj.attr('data-num');
			var product = obj.attr('data-product');
			var name = obj.attr('data-name');
			var option = obj.attr('data-sub');
			var qty = obj.attr('data-qty');

			if(!num || !product || !name || !option || !qty) {
				alert("잘못된 접근 입니다.");
				location.reload();
				return false;
			} else {

				if(confirm('삭제 후에는 삭제 철회가 불가합니다. 삭제를 하시겠습니까?') == true) {
					formData.append("order_num", num);
					formData.append("order_product", product);
					formData.append("order_name", name);
					formData.append("order_option", option);
					formData.append("order_qty", qty);

					$.ajax({
						type: "POST",
						url	: "./rev_delete",
						data : formData,
						processData: false,
						contentType: false,
						success : function(data) {
							if(data != 0) {
								alert("고객 상품평이 삭제되었습니다");
								location.href="../info/review_all";
							} else {
								alert("잘못된 접근입니다");
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
		}

		/* ============================================== */
		loadStarCount();

		function loadStarCount() {
			var count = $('.rating').attr('data-point');

			for (var i = 0; i < count; i++) {
				$('p.rating').find('button:eq(' + i + ')').addClass('on');
			}
		}

		/* ============================================== */
		var read = false;

		$(".buy_list p").click(function() {
			$(".buy_list p").addClass("on");  
			$(".buy_list .exp").slideToggle("fast");

			$('div[name^="buy_"]').click(function() {
				var text = "";

				text += "<div name=" + $(this).attr('name') + " class='optionList'>";
				text += "	<li class='product_img'><img class='thumb' src='" + $(this).find('li.product_img').find('img.thumb').attr('src') + "'></li>";
				text += "	<li class='name'>" + $(this).find('li.name').text() + "</li>";
				text += "	<li class='option'>" + $(this).find('li.option').text() + "</li>";
				text += "	<li class='qty'>" + $(this).find('li.qty').text() + "</li>";
				text += "</div>";

				$(".buy_list p").html(text);
				$(".buy_list p").removeClass("on"); 
				$(".buy_list .exp").hide();
				
				read = true;

				$('input[id="order_se"]').attr('value', $(this).attr('data-seq'));
				$('input[id="order_na"]').attr('value', $(this).find('li.name').text())
				$('input[id="order_op"]').attr('value', $(this).find('li.option').text())
				$('input[id="order_qt"]').attr('value', $(this).find('li.qty').text())
			});
		});

		/* ======================= 파일업로드  =========================== */
		var index = 0;
		var uploadFiles = [];

		$("input[type='file']").on("change", fileImageView);

		/* =======================  =========================== */
		/*
		
		class File extends Blob {
			constructor(blobParts, filename, options) {
		        super(blobParts, options);

		        this.name = filename;
		        this.lastModifiedDate = new Date();
		        this.lastModified =+ this.lastModifiedDate;
			}
		}
		
		*/

		/* =======================  =========================== */
		nowView();

		function nowView() {
			var size = $('#thumbnails').find('.thumb').size();

			if(size != "undefined") {
				for (var i = 0; i < size; i++) {
					var f_name = $('.thumb').eq(i).attr('data-name');
					var f = new File(uploadFiles, f_name, { type: "image/png", lastModified: new Date() });

					/*
					
					var f = new Blob(uploadFiles, {type: 'image/png'});
					f.name = f_name;
					f.size = 10000;
					f.lastModified = 1585040644817;
					f.webkitRelativePath = "";
					f.lastModifiedDate = new Date();
					
					var blob = new Blob();
					var f = new File([blob], f_name, {type: 'image/png', lastModified: new Date()});

					var f = new Blob(name, {type: 'image/png'});
					f.lastModifiedDate = new Date();
					
					
					*/

					++index;

					uploadFiles.push(f);
				}
			}
		}

		/* ======================= 이미지 업로드 =========================== */
		function fileImageView(e) {
			var files = e.target.files;
			var arr = Array.prototype.slice.call(files);

			for(var i = 0; i < files.length; i++) {
				if(!checkExtension(files[i].name, files[i].size)) {
					return false;
				}
			}

			if($('#thumbnails').find('.thumb').length > 2) {
				alert("3개 이상 등록 할수 없습니다.");
				$("input[type='file']").val("");
				return false;
			} else {
				preview(arr);
			}
		}

		/* ======================= 이미지 업로드  =========================== */
		function checkExtension(fileName, fileSize) {
			var ext = fileName.slice(fileName.lastIndexOf(".") + 1).toLowerCase();
			var maxSize = 1024 * 1024 * 1024 * 2;

			if(fileSize >= maxSize) {
				alert('3MB 이하 파일만 등록할 수 있습니다. 현재용량 : ' + (Math.round(fileSize / 1024 / 1024 * 100) / 100) + ' MB');
				$("input[type='file']").val("");  //파일 초기화
				return false;
			}

			if (!(ext == "gif" || ext == "jpg" || ext == "png" || ext == "jpeg" || ext == "bmp")) {
				alert('해당 파일은 업로드가 불가능합니다');
				$("input[type='file']").val("");  //파일 초기화
				return false;
			}

			return true;
		}

		function preview(arr) {
			/* ===== 브라우저 ===== */
			var agent = navigator.userAgent.toLowerCase();

			if (agent.indexOf("msie") != -1 || agent.indexOf("edge") != -1) {
				alert("브라우저 IE / Edge는 이미지를 등록하실 수 없습니다. 다른 브라우저를 사용해주세요");
				return false;
			}

			arr.forEach(function(f) {
				var fileName = f.name;

				if(fileName.length > 10) {
					fileName = fileName.substring(0,7)+"...";
				}
 
				if(f.type.match('image.*')) {
					var html = "";
					var reader = new FileReader();

					reader.onload = (function(e) {
						html += "<div class='thumb'>";
						html += "	<span name='img_close' id='img_close' data-idx='" + index + "'>X</span>";
						html += "	<img src='" + e.target.result + "' title='" + f.name + "' />";
						html += "	<input type='file' class='upload_file' name='upload_" + index + "' value='" + f.name + "' />";
						html += "</div>";

						$('#thumbnails').append(html);
						$("input[type='file']").val('');
						
						++index;

						$('span[name="img_close"]').click(function(e) {
							deleteThumb($(this));
						});
					});
				}

				uploadFiles.push(f);
				reader.readAsDataURL(f);
			});
		}

		$('span[name="img_close"]').click(function(e) {
			deleteThumb($(this));
		});

		/* ================= 파일업로드 이미지 삭제 =================== */		
		function deleteThumb(obj) {
			var formData= new FormData();
			
			var index = obj.closest('div.thumb').attr('data-seq');
			var order = $('input[id="num_check"]').attr('data-order');
			var num = $('input[id="order_se"]').val();
			var name = $('input[id="order_na"]').val();
			var option = $('input[id="order_op"]').val();
			var qty = $('input[id="order_qt"]').val();

			var f_name = obj.closest('div.thumb').attr('data-name');
			var f_size = obj.closest('div.thumb').attr('data-size');

			if(!order) {
				alert("잘못된 접근입니다.");
				return false;
			} else {

				if(confirm("이미지를 삭제하시겠습니까?") == true) {
					if(index == "" || index == undefined) {
						obj.parent().remove();  // 프리뷰 삭제
					} else {
						formData.append("index", index);
						formData.append("order", order);
						formData.append("num", num);
						formData.append("name", name);
						formData.append("option", option);
						formData.append("qty", qty);
						formData.append("file_name", f_name);
						formData.append("file_size", f_size);
						
						$.ajax({
							type: "POST",
							url	: "/delete/rev_img",
							data : formData,
							processData: false,
							contentType: false,
							success : function(data) {
								uploadFiles[index].upload = 'disable';
								obj.parent().remove();  // 프리뷰 삭제
							}, error: function(error) {
								console.log(error);
							}
						});
					}
				}
			}
		}

		/* ================= 리뷰 수정 =================== */		
		$('button[name="updateReview"]').click(function() {
			fn_update_review($(this));
		});

		function fn_update_review(obj) {
			var formData = new FormData();
			var seq = $('input[id="order_se"]').val();
			var name = $('input[id="order_na"]').val();
			var option = $('input[id="order_op"]').val();
			var qty = $('input[id="order_qt"]').val();
			var order = $('input[id="num_check"]').attr('data-order');
			var star = $('.rating').find('button.on').length;
			var content = $('textarea[name="reContent"]').val();
			content = content.replace(/(?:\r\n|\r|\n)/g, '<br />');

			if(!content) {
				alert("상품평을 작성해주세요 (400자 이내)");
				return false;
			}

			if(!name || !option || !qty || !star || !order) {
				alert("잘못된 접근입니다.");
				location.reload();
				return false;
			} else {

				formData.append("order", order);
				formData.append("seq", seq);
				formData.append("name", name);
				formData.append("option", option);
				formData.append("qty", qty);
				formData.append("star", star);
				formData.append("content", content);

				$.each(uploadFiles, function(i, file) {
					if(file.upload != 'disable') {
						formData.append('upload_' + i + '', file);
					}
				});

				$.ajax({
					type: "POST",
					url	: "/update/review",
					data : formData,
					processData: false,
					contentType: false,
					success : function(data) {
						if(data != "") {
							alert("주문번호 " + data + "이 고객 상품평이 수정되었습니다");
							location.href="../info/review_all";
						} else {
							alert("잘못된 접근입니다");
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

		/* ======================= 리뷰제출 =========================== */
		$('button[name="reviewSend"]').click(function(e) {
			sendReviewData();
		});

		function sendReviewData() {
			var formData = new FormData();
			var seq = $('input[id="order_se"]').val();
			var name = $('input[id="order_na"]').val();
			var option = $('input[id="order_op"]').val();
			var qty = $('input[id="order_qt"]').val();
			var order = $('input[id="num_check"]').attr('data-order');
			var star = $('.rating').find('button.on').length;
			var content = $('textarea[name="reContent"]').val();
			content = content.replace(/(?:\r\n|\r|\n)/g, '<br />');

			if(read == false) {
				alert("상품평을 남길 제품을 선택해주세요");
				return false;
			}

			if(!content) {
				alert("상품평을 작성해주세요 (400자 이내)");
				return false;
			}

			if(!name || !option || !qty || !star || !order) {
				alert("잘못된 접근입니다.");
				location.reload();
				return false;
			} else {

				formData.append("order", order);
				formData.append("seq", seq);
				formData.append("name", name);
				formData.append("option", option);
				formData.append("qty", qty);
				formData.append("star", star);
				formData.append("content", content);

				$.each(uploadFiles, function(i, file) {
					if(file.upload != 'disable') {
						formData.append('upload_' + i + '', file);
					}
				});

				$.ajax({
					type: "POST",
					url	: "/send/review",
					data : formData,
					processData: false,
					contentType: false,
					success : function(data) {
						if(data != "") {
							alert("성공적으로 상품이 등록되었습니다");
							location.href="../info/info_payment";
						} else {
							alert("잘못된 접근입니다");
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

		/* ======================= 별점 선택  =========================== */
		$('span[name="txt_05"]').addClass('on');

		$(".rating button").hover(function() {
			$(this).parent().children("button").removeClass("on");
			$(this).addClass("on").prevAll("button").addClass("on");

			icon_change($(this));
			return false;
		});

		// EUNMI LEE
		function icon_change(obj) {
			$('span[name^="txt_"]').removeClass('on');

			if(obj.attr("id") == "btn_1") {
				$('span[name="txt_01"]').addClass('on');
			} else if(obj.attr("id") == "btn_2") {
				$('span[name="txt_02"]').addClass('on');
			} else if(obj.attr("id") == "btn_3") {
				$('span[name="txt_03"]').addClass('on');
			} else if(obj.attr("id") == "btn_4") {
				$('span[name="txt_04"]').addClass('on');
			} else if(obj.attr("id") == "btn_5") {
				$('span[name="txt_05"]').addClass('on');
			} else {
				$('span[name="txt_05"]').addClass('on');
			}
		}

		/* ==================== 리뷰 글 제한 ===================== */
		$('textarea[name="reContent"]').keyup(function(e) {
			var content = $(this).val();
			$(this).next('span#limit').html(content.length + '/400');
		});

		$('textarea[name="reContent"]').keyup();
		
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

		/* ======================= 결과 출력 전 =========================== */
	    function resultBefore() {
	    	var html = "";

			html += '<div>';
			html += '	<img src="../resources/image/icon/loading.gif" />';
			html += '</div>';

			$('.wrap-loading').removeClass('display-none');
			$('.wrap-loading').append(html);
	    }

		/* =============================================================== */
		function number_format(x) {
			return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");	
		}

	});
});