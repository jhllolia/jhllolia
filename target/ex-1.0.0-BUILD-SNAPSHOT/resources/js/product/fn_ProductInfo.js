jQuery.noConflict();

jQuery(function($) {
	$(document).ready(function() {

		/* ====================== slide 이미지 개수 ======================= */
		var count = $('#file_div').find('p').length;
	
		/* ====================== 해당 슬라이드 이미지 value 값 입력 ======================= */
		for (var i = 0; i < count; i++) {
			var $obj = $('input[id="file_' + i + '"]');	// 파일 번호
			var value = $obj.attr('value');				// 파일 속성

			$obj.next().text(value);					// 업로드 된 파일 이름 적용
		}

		/* ====================== 다중 ======================= */
		$("input[class='file']").on("change", fileImageView);

		/* ====================== 테이블 상태 변환 ======================= */
		$('button[id="tb_modify"]').on("click", function() {
			linkModify($(this));
		});
	
		$('button[id="tb_del"]').on("click", function() {
			tableDelete($(this));
		});

		/* ====================== 해당 제품 배송비 변경  ======================= */
		$('button[id="shippingCost"]').on("click", function(e) {
			shippingCost($(this));
		});
		
		/* ====================== 해당 제품 배송비 변경  ======================= */
		$('button[name="sell_state"]').on("click", function(e) {
			stateChange($(this));
		});

		/* ====================== 테이블 속성 생성 ======================= */
		$('#addFile').on("click", function(e) {
			addFile($(this));
		});
	
		$('#addProduct').on("click", function(e) {
			addProduct($(this));
		});

		$('button[name="delete"]').on("click", function(e) {
			deleteFile($(this));
		});

		$('button[name="product_Del"]').on("click", function(e) {
			deleteProduct($(this));
		});
	
		$('button[name="sendTable"]').on("click", function(e) {
			sendTableDB(e);
		});
	
		/* ====================== 테이블 속성 변경 (배송비) ======================= */
		$('button[name="modifyTable"]').on("click", function(e) {
			actionModify(e);
		});

		/* ====================== 판매 제품 상태 변경 ======================= */
		function stateChange(obj) {
		    var formData = new FormData();
			var seq = obj.attr('data-seq');										// 제품 시퀀스
			var num = obj.attr('data-num').replace(/[^0-9]/g,"");				// JSON 번호
			var state = obj.attr('data-state');									// 제품 상태
			var data = JSON.parse(obj.parents('td').attr('data-result'));		// DATA

			if( !seq || !state) {
				return false;
			} else {
				/* ====================== 상태 변경 ======================= */
				if( data[num].sell == "Y" ) {
					data[num].sell = 'N';
				} else {
					data[num].sell = 'Y';
				}

				JSON.stringify(data[num].sell);

				formData.append('seq', seq);
				formData.append('arr', JSON.stringify(data));

				$.ajax({
					url	 : "/product/sell",
					type : 'POST',
					data : formData,
					processData: false,
					contentType: false,
					success : function(data) {
						if(data == 1) {
							location.reload();
						} else {
							return false;
						}
					}, beforeSend: function() {

					}, complete: function() {

					}, error: function(error) {
						console.log(error);
					}
				});
			}
		}

		/* ======================  ======================= */
		function shippingCost(e) {
		    var formData = new FormData();
	    	var pattern_num = /[0-9]/;	// 숫자 
	    	var pattern_eng = /[a-zA-Z]/;	// 문자 
	    	var pattern_spc = /[~!@#$%^&*()_+|<>?:{}]/; // 특수문자
	    	var pattern_kor = /[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/; // 한글체크

			var seq = e.attr('seq');
			var input = $('input[name="s_cost"]').val();
			var result = confirm("수정하시겠습니까??");

			if( !seq ) {
				alert("번호가 존재하질 않습니다.");
				return false;
			} else {
				if(result) {
					if((pattern_num.test(input)) && !(pattern_eng.test(input)) && !(pattern_spc.test(input)) && !(pattern_kor.test(input))) {
						formData.append('seq', seq);
						formData.append('input', input);

						$.ajax({
							url	 : "/shipping/cost",
							type : 'POST',
							data : formData,
							processData: false,
							contentType: false,
							success : function(data) {
								if(data == 1) {
									location.reload();
								} else {
									alert("변경에 실패했습니다.");
									return false;
								}
							}, error: function(error) {
								console.log(error);
							}
						});
					} else {
						alert("문자 금지");
						return false;
					}
				}
			}
		}

		/* ====================== 테이블 정보 페이지 이동 ======================= */
		function linkModify(e) {
			var seq = e.attr("seq");

			if( !seq ) {
				alert("번호가 존재하질 않습니다.");
				return false;
			} else {
				location.href = "/modify/" + seq;
			}
		}

		/* ====================== 테이블 정보 수정 ======================= */
		function actionModify(e) {
		    var formData = new FormData();
			var product_Arr = new Array();

			var seq = $('#seq').val();
			var mode = $('select[name="sale_mode"] option:selected').val();
			var title = $('#product_Title').val();
			var info = CKEDITOR.instances.product_Info.getData();
			var content = CKEDITOR.instances.product_Content.getData();
			var shipping_txt = CKEDITOR.instances.product_Shipping.getData();
			var return_txt = CKEDITOR.instances.product_Return.getData();

			if(mode == "" || mode == null) {
				alert("판매 형태를 등록해주세요");
				return false;
			}

			$("input[name^='file_']").each(function(index, item) {
				var fileName = $(this).attr('name');					// 업로드 할 파일 이름
				var fileCheck = $(this).attr('value');					// 업로드 할 파일 속성

				if(fileCheck) {
					formData.append(fileName, $("input[name^='file_']")[index].files[0]);	// 슬라이드 이미지
				}
			});

			$("ul[name^='goods_']").each(function(index, item) {
		    	var obj = new Object();
		    	var num = $(this).attr('name').replace(/[^0-9]/g,"");
		    	var name = $(this).find('input[id="toName"]').val();
		    	var option = $(this).find('input[id="toOption"]').val();
		    	var price = $(this).find('input[id="toPrice"]').val();
		    	var sale = $(this).find('input[id="toSale"]').val();

		    	if(!num || !name || !option || !price || !sale) {
		    		alert("텍스트 칸을 입력 해주세요");
		    		$(this).focus();
					return false;
		    	} else if(price == "") {
		    		alert("숫자만 입력 가능합니다");
		    		$(this).find('input[id="toPrice"]').focus();
					return false;
		    	} else {
			    	obj.num = "product_" + num;
			    	obj.name = $(this).find('input[id="toName"]').val();
			    	obj.option = $(this).find('input[id="toOption"]').val();
			    	obj.price = price;
			    	obj.sale = $(this).find('input[id="toSale"]').val();
			    	obj.sell = "Y";

					product_Arr[index] = obj;
		    	}
			});

			product_Arr.sort();												// 정렬

			formData.append("mode", mode);									// 테이블 제목
			formData.append("title", title);								// 테이블 제목
			formData.append("info", info);									// 테이블 설명
			formData.append("content", content);							// 테이블 설명
			formData.append("shipping_txt", shipping_txt);					// 테이블 설명
			formData.append("return_txt", return_txt);						// 테이블 설명
			formData.append("product_Arr", JSON.stringify(product_Arr));	// 테이블 설명

			$.ajax({
				url	 : "/seat/update/" + seq,
				type : 'POST',
				data : formData,
				processData: false,
				contentType: false,
				success : function(data) {
	    			if(data == "true") {
	    				alert("테이블 수정이 완료되었습니다.");
	    				location.href="/product/table";
	    			} else {
	    				alert("테이블 수정에 실패했습니다.");
	    				return false;
	    			}
				}, beforeSend: function() {
	
				}, complete: function() {

				}, error: function(error) {
					console.log(error);
				}
			});
		}
	
		/* ====================== 테이블 정보 삭제 ======================= */
		function tableDelete(e) {
			var seq = e.attr("seq");

			if(!seq) {
				alert("번호가 존재하질 않습니다.");
				return false;
			} else {
	        	sendAction(e);	// 비밀번호 입력 HTML 생성
	
	    		$('button[id="sendPw"]').click( function() {
	    	        $(".alert").hide();
	
	    	        var state = $('button[name="seat_choose"].active').val();	// 선택된 속성으로 상태 변경
	    	        /* ======== 선택된 D 속성은 Delete ========== */
	
	    			if( !state ) {
	            		$("#notSend").show().html("상태를 선택해주세요.");
						return false;
	    			} else {
	    				if(state == "D") {
	    					if (confirm("정말 삭제하시겠습니까? 삭제하면 돌이킬수 없습니다.") == true) {
	            				stateSendAjax(seq, state);		// Ajax 
	    					} else {
	    						alert("삭제를 취소하셨습니다.");
	    						$('.wrap-loading').remove();	// 레이아웃 삭제
	    						return false;
	    					}
	    				} else {
	        				stateSendAjax(seq, state);			// Ajax 
	    				}
	    			}
	    		});
			}
		}

		/* ====================== 테이블 삭제 Ajax ======================= */
		function stateSendAjax(seq, state) {
			$.ajax({
				url	 : "/seat/delete/" + state + "/" + seq,
				type : 'POST',
		    	success : function(data) {
		    		if(data == "true") {
		    			alert("상태가 변경 되었습니다.");
		    			location.reload();
		    		} else {
		    			alert("상태 변경에 실패했습니다. 다시 시도해주세요.");
		    			return false;
		    		}
		    	}, beforeSend: function() {
					$('.wrap-loading').removeClass('display-none');
				}, complete: function() {
	
				}, error: function(error) {
					console.log(error);
		    	}
			});
		}
	
		/* ====================== 테이블 상태 변경 레이아웃 ======================= */
		function sendAction(e) {
			var html = "";
			var state = "";

			if(e.attr("state") == "D") {
				state = "삭제";
			} else if (e.attr("state") == "N") {
				state = "비활성화";
			} else {
				state = "활성화";
			}
	
			html += "<div class='wrap-loading'>"
			html += "	<div class='pwdInsert'>"
			html += "		<h2>" + e.attr("seq") + " 번 상태 변경</h2>"
			html += "		<hr />"
			html += "		<span id='close' class='close'>x</span>"
			html += "		<p class='txt'>현재 테이블 상태 - <span>" + state  + "</span></p>"
			html += "		<ul class='choose'>"
			html += "			<li><button name='seat_choose' class='choose btn log' value='Y'>활성화</button></li>"
			html += "			<li><button name='seat_choose' class='choose btn log' value='N'>비활성화</button></li>"
			html += "			<li><button name='seat_choose' class='choose btn log' value='D'>삭제</button></li>"
			html += "		</ul>"
			html += "		<p class='alert alert-danger' id='notSend'></p>"
			html += "		<button id='sendPw' name='sendPw' class='btn log'>전송</button>"
			html += "	</div>"
			html += "</div>"
	
			$('body').append(html);	// 추가
		    $(".alert").hide();		// 경고창 삭제

		    sendClose();			// 레이아웃 닫기
		    seatChoose();			// 선택 효과
		}
	
		/* ====================== 테이블 상태 변경 액션 ======================= */
		function seatChoose() {
			$('button[name="seat_choose"]').on("click", function() {
				$('.choose').removeClass('active');	// 기존 버튼 CSS 종료
				$(this).toggleClass('active');		// 선택 CSS 적용
			});
		}
	
		function sendClose() {
			$('#close').click( function() {
				$('.wrap-loading').remove();
			});
		}

		/* ====================== 테이블 정보 DB 저장 ======================= */
		function sendTableDB(e) {
		    var formData = new FormData();

			var product_Arr = new Array();
			var mode = $('select[name="sale_mode"] option:selected').val();
			var title = $('#product_Title').val();
			var info = CKEDITOR.instances.product_Info.getData();
			var content = CKEDITOR.instances.product_Content.getData();
			var shipping_txt = CKEDITOR.instances.product_Shipping.getData();
			var return_txt = CKEDITOR.instances.product_Return.getData();

			if(mode == "" || mode == null) {
				alert("판매 형태를 등록해주세요");
				return false;
			}

			if($("input[type='file']").val() == "" ) {
				alert("슬라이드 / 시그니쳐 이미지를 업로드 해주세요.");
				return false;
			} else {
				var flag = false;

				/* ====================== (1) ======================= */
				$("input[name^='file_']").each(function(index, item) {
					formData.append("file_" + index, $("input[name^='file_']")[index].files[0]);	// 슬라이드 이미지
					flag = true;
				});

				/* ====================== (2) ======================= */
				$("ul[name^='goods_']").each(function(index, item) {
			    	var obj = new Object();
			    	var num = $(this).attr('name').replace(/[^0-9]/g,"");
			    	var name = $(this).find('input[id="toName"]').val();
			    	var option = $(this).find('input[id="toOption"]').val();
			    	var price = $(this).find('input[id="toPrice"]').val();
			    	var sale = $(this).find('input[id="toSale"]').val();

			    	if(!num || !name || !option || !price || !sale) {
			    		alert("텍스트 칸을 입력 해주세요");
			    		$(this).focus();
						return false;
			    	} else if(price == "") {
			    		alert("숫자만 입력 가능합니다");
			    		$(this).find('input[id="toPrice"]').focus();
						return false;
			    	} else {
				    	obj.num = "product_" + num;
				    	obj.name = $(this).find('input[id="toName"]').val();
				    	obj.option = $(this).find('input[id="toOption"]').val();
				    	obj.price = price;
				    	obj.sale = $(this).find('input[id="toSale"]').val();
				    	obj.sell = "Y";

						product_Arr[index] = obj;
			    	}
				});

				if (flag) {
					formData.append("mode", mode);									// 판매 형식
					formData.append("title", title);								// 테이블 제목
					formData.append("info", info);									// 테이블 설명
					formData.append("content", content);							// 테이블 내용
					formData.append("shipping_txt", shipping_txt);					// 배송 정책
					formData.append("return_txt", return_txt);						// 반품 / 환불 정책
					formData.append("product_Arr", JSON.stringify(product_Arr));	// 제품 하위카테고리

			        $.ajax({
						url	 : "/product/insert",
						type : 'POST',
			    		data : formData,
			            processData: false,
			            contentType: false,
			    		success : function(data) {
			    			if(data == "true") {
			    				alert("테이블 등록이 완료되었습니다.");
			    				location.href="/product/table";
			    			} else {
			    				alert("테이블 등록에 실패했습니다.");
			    				return false;
			    			}
			    		}, beforeSend: function() {
		    				$('.wrap-loading').removeClass('display-none');
						}, complete: function() {
		    				$('.wrap-loading').addClass('display-none');
						}, error: function(error) {
							console.log(error);
			    		}
			        });
				}
			}
		}
	
		/* ====================== 추가 파일업로드 ======================= */
		function addFile(obj) {
			var fileCount = $('#file_div').find('p:last-child').find('input[type="file"]').attr('name').replace(/[^0-9]/g,""); // 마지막 파일업로드 추가 위치
			var html = "";

			html += "<p class='cnt_" + count + "'>";
			html += "	<span class='tit'>" + count + "</span>";
			html += "	<input type='file' id='file_" + count + "' name='file_" + ( ++fileCount ) + "' class='file' value='' />";
			html += "	<span id='file_txt' class='naming'>선택된 파일 없음</span>";
			html += "	<span name='imgs_" + count + "'>";
			html += "		<img src='../resources/image/icon/preview.png' />";
			html += "	</span>";
			html += "	<button id='delete' name='delete' class='btn log'>삭제</button>";
			html += "</p>";

			$('#file_div').append(html);
			$("input[class='file']").on("change", fileImageView);
	
			$('button[name="delete"]').on("click", function() {
				deleteFile($(this));
			});
		}

		/* ====================== 추가 제품 리스트 ======================= */
		var product = $('#list').find('ul[name^="goods_"]').length;

		function addProduct(obj) {
			var html = "";

			html += "<ul name='goods_" + product + "' id='goods'>";
			html += "	<li><span>" + product +"</span></li>";
			html += "	<li><input type='text' id='toName' class='form-control' placeholder='제품 명' /></li>";
			html += "	<li><input type='text' id='toOption' class='form-control' placeholder='종류' /></li>";
			html += "	<li><input type='text' id='toPrice' class='form-control' placeholder='가격' /></li>";
			html += "	<li><input type='text' id='toSale' class='form-control' placeholder='할인률' /></li>";
			html += "	<li><button name='product_Del' class='btn log'>삭제</button></li>";
			html += "</li>";

			if(product > 0) {
				$('ul[name^="goods_"]:last-child').after(html);
			} else {
				$('span#list').append(html);				
			}

			++product;

			$('button[name="product_Del"]').on("click", function(e) {
				deleteProduct($(this));
			});
		}

		/* ====================== 해당 파일 리스트 삭제  ================== */
		function deleteProduct(obj) {
			if(product > 1) {
				obj.closest("ul").remove();
			} else {
				alert("1개 이하는 삭제 하실수 없습니다.");
				return false;
			}
		}

		/* ====================== 해당 파일 리스트 삭제  ================== */
		function deleteFile(obj) {
			var fileIndex = $('#file_div').find('p').length;											// 슬라이드 이미지 미리보기 개수

			if(fileIndex > 1) {
				var formData = new FormData();
				formData.append("realFileName", obj.prev().prev().text());								// 삭제 파일업로드 Name
				formData.append("name", obj.prev().prev().prev('input[name^="file_"]').attr('name'));	// 삭제 파일업로드 ID

		        $.ajax({
					url	 : "/fileDel/" + $('#seq').val(),
					type : 'POST',
		    		data : formData,
		            processData: false,
		            contentType: false
		        });

		        obj.parent().remove();																	// 해당 파일 업로드 삭제
			} else {
				alert("1개 이하는 삭제 하실수 없습니다.");
				return false;
			}
		}
	
		/* ====================== 다중 파일업로드 ======================= */
		var sel_files = [];

		function fileImageView(e) {
			var index = 0;
			var nameTarget = $(e.target);
			var fileValue = nameTarget.val().split("\\");
			var fileName = fileValue[fileValue.length - 1]; // 파일명

			var files = e.target.files;							// 파일 속성(1)
			var filesArr = Array.prototype.slice.call(files);	// 파일 속성(2)

			filesArr.forEach(function(f) {
				if(!f.type.match("image.*")) {
					alert("확장자는 이미지 확장자만 가능합니다.");
					return false;
				} else {
					sel_files.push(f);
	
					var reader = new FileReader();
					reader.onload = function(e) {
						nameTarget.attr('value', fileName);										// 
						nameTarget.next('span').text(fileName);									// 이름
						nameTarget.next().next().find('img').attr('src', e.target.result);		// 이미지 미리보기

						index++;
					}
	
					reader.readAsDataURL(f);
				}
			});
		}

	});
});