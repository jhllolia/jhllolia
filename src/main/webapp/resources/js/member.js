jQuery.noConflict();

jQuery(function($) {
	$(document).ready(function() {
		$(".alert").hide();

		userProfile();

		var idck = "";
    	var regExp = /([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/; // email check

    	/* ====================== 사용자 프로필 ======================= */
    	function userProfile() {
    		$('#u_profile').change( function(e) {
    			$("input[name='f_profile']").click();
    		});

    		$("input[name='f_profile']").change(function(e) {
    			fileUpload(this);
    		});
    	}

    	/* ====================== 프로필 업로드 ======================= */
    	function fileUpload(input) {
    		if (input.files && input.files[0]) {
    			$('#uploadbutton').submit();

    	        var frm = document.getElementById('fileForm');
    	        frm.method = 'POST';
    	        frm.enctype = 'multipart/form-data';
    	        var fileData = new FormData(frm);

                $.ajax({
                    type : 'POST',
        			url	 : "/info/upload",
            		data : fileData,
                    processData: false,
                    contentType: false,
            		success : function(data) {
            			if(data.code == "OK") {
            				window.location.reload();
            			} else if(data.code == "SIZE") {
            				alert("프로필 이미지 사이즈는 1M를 넘을수 없습니다.");
            				return false;
            			} else if(data.code == "FORMAT") {
            				alert("파일 형식이 올바르지 못합니다. ex) PNG,JPG");
            				return false;
            			} else {
            				alert("파일 업로드에 실패했습니다. 관리자에게 문의해주세요.");
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

		/* ==================== 메인 이용약관 / 개인정보보호 체크 ==================== */
		$('label[for="all_terms"]').click(function(e) {
			$(this).toggleClass('active');

			if($(this).hasClass('active')) {
				$(this).find("input:checkbox[name='all_terms_chk']").prop("checked", true);
				$(this).find("img").attr("src", "../resources/image/icon/chk_on.png");
			} else {
				$(this).find("input:checkbox[name='all_terms_chk']").prop("checked", false);
				$(this).find("img").attr("src", "../resources/image/icon/chk_off.png");
			}

			if($("input:checkbox[name='all_terms_chk']").is(':checked')) {
				$("input:checkbox[name='terms_chk']").prop("checked", true);
				$("input:checkbox[name='terms_chk']").next('img').attr("src", "../resources/image/icon/chk_on.png");
			} else {
				$("input:checkbox[name='terms_chk']").prop("checked", false);
				$("input:checkbox[name='terms_chk']").next('img').attr("src", "../resources/image/icon/chk_off.png");
			}
		});

		/* ==================== 서브 이용약관 / 개인정보보호 체크 ==================== */
		$('label[for="termsUse"]').click(function(e) {
			$(this).toggleClass('active');

			if($(this).hasClass('active')) {
				$(this).find("input[type='checkbox']").prop("checked", true);
				$(this).find('img').attr("src", "../resources/image/icon/chk_on.png");
			} else {
				$(this).find("input[type='checkbox']").prop("checked", false);
				$(this).find('img').attr("src", "../resources/image/icon/chk_off.png");
			}

			$("input[name='terms_chk']:checked").each(function(index, value) {
				var $this = $('label[for="all_terms"]');

				if(index == 1) {
					$this.addClass('active');
					$this.find("input:checkbox[name='all_terms_chk']").prop("checked", true);
					$this.find("img").attr("src", "../resources/image/icon/chk_on.png");
				} else {
					$this.removeClass('active');
					$this.find("input:checkbox[name='all_terms_chk']").prop("checked", false);
					$this.find("img").attr("src", "../resources/image/icon/chk_off.png");
				}
			});
		});

		/* ============================================================ */
		$('#open_txt_1').click( function() {
			var html = "";

			html += "<div class='term'>";
			html += "	<span id='close'><img src='../resources/image/icon/close.png' alt='close' /></span>";
			html += "	<div id='check_use' class='check'></div>";
			html += "</div>";

			$('#terms_txt_01').css('display','block').html(html);
			$('#check_use').load('../terms/termsUse #mTerms');
			
			termsClose();
		});

		/* ============================================================ */
		$('#open_txt_2').click( function() {
			var html = "";

			html += "<div class='term'>";
			html += "	<span id='close'><img src='../resources/image/icon/close.png' alt='close' /></span>";
			html += "	<div id='check_pri' class='check'></div>";
			html += "</div>";

			$('#terms_txt_02').css('display','block').html(html);
			$('#check_pri').load('../terms/termsPrivacy #mTerms');
			
			termsClose();
		});

		/* ============================================================ */
		function termsClose() {
			$('span#close').click(function() {
				$('.txt_content div').css('display','none');
			});
		}

		/* ============================================================ */
		$('#next').click(function() {
    		if($("input:checkbox[id='check_1']").is(":checked") == false) {
        		$("#terms-danger").show().html("모든 약관에 동의 하셔야 회원가입이 진행 가능합니다.");
            	$("input:checkbox[id='check_1']").focus(); 
    			return false;
    		} else if($("input:checkbox[id='check_2']").is(":checked") == false) {
        		$("#terms-danger").show().html("모든 약관에 동의 하셔야 회원가입이 진행 가능합니다.");
            	$("input:checkbox[id='check_2']").focus(); 
    			return false;
    		} else {
    			$('#terms').css('display','none');
    			$('#signup').css('display','block');
    		}
		});

		/* ==================== 네이버 로그인 code ==================== */
		$('li[name="naverLogin"]').click(function(e) {
			naverCode();
		});

		function naverCode() {
			$.ajax({
	    		url	: '/naver/accessToken',
				type : 'POST',
				processData: false,
				contentType: false,
				success : function(data) {
					location.href = data;
				}, error: function(error) {
					console.log(error);
				}
			});
		}

		/* ==================== 외부 로그인 / 회원가입   ==================== */
		$('button[id="btn_other"]').click(function(e) {
			other_Signup();
		});

		function other_Signup() {
			var formData = new FormData();

    		var pattern_num = /[0-9]/;					// 숫자
        	var pattern_eng = /[a-zA-Z]/;				// 문자 
        	var pattern_spc = /[~!@#$%^&*()_+|<>?:{}]/; // 특수문자
        	var pattern_kor = /[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/; 		// 한글체크
			
			var way = $('#otherName').text();
			var profile = $('#profile_img').attr('src');
			var id = $('input[id="member_Id"]').val();
			var pw = $('input[id="member_Pw"]').val();
			var name = $('input[id="member_Name"]').val();
			var phone = "";

        	var member_Zip = $("#zip").val();
        	var member_Addr1 = $("#addr1").val();
        	var member_Addr2 = $("#addr2").val();

        	var m_first = $('select[id="phone_1"]').val();	// 통신사
        	var m_second = $('input[id="phone_2"]').val();	// 첫 번째
        	var m_third = $('input[id="phone_3"]').val();	// 두 번째

			/* ================== Phone Check ================== */
			if(!id || !name) {
        		$("#info-danger").show().html("정보를 입력해주세요.");
    	        $("#member_Id").focus();
    			return false;
			}

			/* ================== Phone Check ================== */
    		if( m_first == "" || !m_second || !m_third ) {
    			$("#phone-danger").show().html("휴대폰 번호를 입력해주세요.");
    			$("select[name='member_Phone']").focus();
    			return false;
    		} else {
    			if((pattern_num.test(m_second)) && !(pattern_eng.test(m_second)) && !(pattern_spc.test(m_second)) && !(pattern_kor.test(m_second)) 
    				&& (pattern_num.test(m_third)) && !(pattern_eng.test(m_third)) && !(pattern_spc.test(m_third)) && !(pattern_kor.test(m_third))) {

    				phone = m_first + "-" + m_second + "-" + m_third;
        		} else {
        			$("#phone-danger").show().html("특수 문자를 사용할수 없습니다.");
        			$("input[name='member_Phone']").focus();
        			return false;
        		}
    		}

			/* ================== Post Check ================== */
    		if( !member_Zip || !member_Addr1 ) {
    			$("#post-danger").show().html("주소를 선택해주세요.");
    			$("#addr1").focus();
    			return false;
    		}

			if($('input[name="terms_chk"]:checked').length == 2) {
				formData.append("way", way);
				formData.append("profile", profile);
				formData.append("id", id);
				formData.append("pw", pw);
				formData.append("name", name);
				formData.append("phone", phone);
				formData.append("zip", member_Zip);
				formData.append("addr1", member_Addr1);
				formData.append("addr2", member_Addr2);

				$.ajax({
		    		url	: '/other/signup',
					type : 'POST',
					data : formData,
					processData: false,
					contentType: false,
					success : function(data) {
						if(data == 1) {
							location.href='../info/info_payment';							
						} else {
							location.href='../member/login?code=FAIL';							
						}
					}, beforeSend: function() {

					}, complete: function() {

					}, error: function(error) {
						console.log(error);
					}
				});
	    	} else {
		        $("#terms-danger").show().html("모든 약관에 동의 하셔야 회원가입이 진행 가능합니다.");
    			return false;
	    	}
		}

		/* ====================  로그인   ==================== */
		$('#btn_login').click( function() {
	        $(".alert").hide();

			var member_Id = $('#member_Id').val();
			var member_Pw = $('#member_Pw').val();

			if( !member_Id ) {
        		$("#danger").show().html("이메일를 입력해주세요.");
				$("#member_Id").focus();
				return false;
			}

			if( !member_Pw ) {
        		$("#danger").show().html("비밀번호를 입력해주세요");
				$("#member_Id").focus();
				return false;
			}

			document.form_login.action = "/member/loginCheck";
			document.form_login.submit();
		});

		/* ================= search check ===================== */
		$('#btn_search').click( function() {
			var email = $('#member_Id').val();

			if(!$('input[name="member_Id"]').val()) {
        		$("#danger").show().html("이메일을 입력해주세요");
				$("#member_Id").focus();
				return false;
			}

			/* ======= id 존재여부 ====== */
			var member_chk = chk_member(email);

			member_chk.success(function(data) {
				member_chk = data.cnt;

				if( !email ) {
	        		$("#danger").show().html("이메일를 입력해주세요.");
					$("#member_Id").focus();
					return false;
				} else if( member_chk == 0 ) {
	        		$("#danger").show().html("등록되지 않은 이메일 입니다. <a href='/member/signup'>회원가입</a> 해주세요.");
					$("#member_Id").focus();
			        return false;
				} else if(!regExp.test(email)) {
	        		$("#danger").show().html("이메일 주소가 유효하지 않습니다.");
					$("#member_Id").focus();
			        return false;
	    		} else {
					/* ======= 일반 회원가입 / 카카오 네이버 로그인 ====== */
	    			chk_way(email).success(function(data) {
	    				if(data.member_Way != 'Y') {
	    					/* ======= N ===== */

	    	        		$("#danger").show().html("등록된 이메일은 카카오 / 네이버 로그인 전용입니다. 다시 <a href='/member/login'>로그인</a> 해주세요.");
	    					$("#member_Id").focus();
	    					return false;
	    				} else {
	    					/* ======= Y ===== */

	    		        	$.ajax({
	    		    			url	: "/member/pwdSearch",
	    		    			enctype : "multipart/form-data",
	    		    			dataType : "json",
	    		    			type : "POST",
	    		    			data : { "member_Id" : email } ,
	    		    			beforeSend: function() {
	    		    				$('.wrap-loading').removeClass('display-none');
	    		    			}, complete: function() {
	    		    				$('.wrap-loading').addClass('display-none');
	    		    			}, error: function(error) {
	    							console.log(error);
	    		    			}
	    		        	});

	    		        	location.href="/member/find_pwd?send_email=" + email;
	    				}
	    			});
	    		}
			});
        });

		/* ====================== ID Check ====================== */
		$('#id_chk').click( function() {
	        $(".alert").hide();

			var member_Id = $("#member_Id").val();
			var member_chk = chk_member(member_Id); // id 존재여부

        	if( !member_Id ) {
        		$("#id-danger").show().html("이메일를 입력해주세요.");
				$("#member_Id").focus();
				return false;
        	} else if(!regExp.test(member_Id)) {
		        $("#id-danger").show().html("이메일 형식과 일치 하지 않습니다.");
				$("#member_Id").focus();
        		return false;
        	} else {
    			member_chk.success(function(data) {
    				member_chk = data.cnt;

    				if(data.cnt > 0) {
    	        		$("#id-danger").show().html("이미 등록된 이메일입니다. <a href='/member/search_password'>비밀번호 찾기 바로가기</a>");
    					$("#submit").attr("disabled", "disabled");
    					$("#member_Id").focus();
    				} else {
    	        		$("#id-success").show().html("사용할 수 있는 이메일 입니다.");
    					$("#submit").removeAttr("disabled");

    					idck = 1; // 중복확인 체크

    					if(idck == 1) {
    						$("#overlap-danger").hide();
    					}
    				}
    			});
        	}
		});

		/* ================= Pwd Check(Sign up) ================= */
        $("input[name=member_Pw]").keyup(function() {
			pwd_chk();
        });

        function pwd_chk(e) {
	        $(".alert").hide();

        	var pwd1 = $("#member_Pw").val();
        	var pwd2 = $("#member_Pw_chk").val();

			var checkNumber = pwd1.search(/[0-9]/g);
			var checkEnglish = pwd1.search(/[a-z]/ig);

        	if(pwd1 != "" || pwd2 != "") {
        		if(pwd1 == pwd2) {
        			if(checkNumber < 0 || checkEnglish < 0) {
    	        		$("#pwd-danger").show().html("비밀번호가 일치하지 않습니다.");	// Fail
                    	return false; 
        			} else {
        				$("#pwd-success").show().html("비밀번호가 일치합니다.");		// Success
        			}
        		} else {
        			if(checkNumber < 0 || checkEnglish < 0) {
    	        		$("#pwd-danger").show().html("숫자 + 영문자를 혼용하여야 합니다.");
        			} else {
        				$("#pwd-danger").show().html("비밀번호가 일치하지 않습니다.");
        			}

                	return false; 
        		}
        	}
        }

        /* ====================== Pwd Check(My Info) ================= */
        $('#pwd_update').click( function(e) {
	        $(".alert").hide();

        	var now = $("#pwd_now").val();
        	var pwd1 = $("#member_Pw").val();
        	var pwd2 = $("#member_Pw_chk").val();

			var checkNumber = pwd1.search(/[0-9]/g);
			var checkEnglish = pwd1.search(/[a-z]/ig);

			var member_chk = chk_pwd(now);

			member_chk.success(function(data) {
				if(data.cnt == 0) {
	        		$("#pwd-danger").show().html("현재 비밀번호가 일치 하지 않습니다.");
					return false;
				} else if(data.cnt == 1) {
		        	if(pwd1 != "" || pwd2 != "") {
		        		if(pwd1 == pwd2) {
		        			if(checkNumber < 0 || checkEnglish < 0) {
		    	        		$("#pwd-danger").show().html("비밀번호가 일치하지 않습니다.");	// Fail
		                    	return false; 
		        			} else {
		        				$("#pwd-success").show().html("비밀번호가 일치합니다.");		// Success
		        			}
		        		} else {
		        			if(checkNumber < 0 || checkEnglish < 0) {
		    	        		$("#pwd-danger").show().html("숫자 + 영문자를 혼용하여야 합니다.");
		        			} else {
		        				$("#pwd-danger").show().html("비밀번호가 일치하지 않습니다.");
		        			}

		                	return false; 
		        		}

		        		var con_test = confirm("비밀번호를 변경하시겠습니까??");
		        		if(con_test == true) {

			            	var objParams = {
			            						"now_Pw"		:	now,
			            						"insert_pwd1" 	:	pwd1,
			            						"insert_pwd2" 	:	pwd2
											};

			            	$.ajax({
				    			type: "POST",
			        			data : objParams,
			        			url	: "/info/info_pwd_update",
				    			dataType: "json",
			        			success : function(data) {
			        				if(data == 1) {
			        					alert("비밀번호가 성공적으로 변경되었습니다.");
			        					location.href="/info/info_payment";
			        				} else {
			        					alert("비밀번호 수정에 실패했습니다. 다시 한번 시도해주세요.");
			        					location.reload();
			        				}
			        			}, beforeSend: function() {
			        				$('.wrap-loading').removeClass('display-none');
								}, complete: function() {
									$('.wrap-loading').addClass('display-none');
								}, error: function(error) {
									console.log(error);
			        			}
			            	});
		        		} else {
		        			alert("비밀번호 수정을 취소하셨습니다.");
		        			return false;
		        		}
		        	}
				}
			});
        });

		/* ======================= Sign Up ================== */  
        function chk_way(member_chk) {
    		return $.ajax({
		    			type: 'POST',
		    			data: member_chk,
		    			url: "/member/WayCheck",
		    			dataType: "json",
		    			contentType: "application/json; charset=UTF-8",
		    			success: function(data) {
		    				return data.member_Way;
		    			}, beforeSend:function() {
		
						}, error : function(error) {
							console.log(error);
		    			}
    		});
        }

		/* ======================= Sign Up ================== */ 
		function chk_member(member_chk) {
    		return $.ajax({
		    			type: 'POST',
		    			data: member_chk,
	        			url: "/member/idCheck",
		    			dataType: "json",
		    			contentType: "application/json; charset=UTF-8",
		    			success: function(data) {
		    				return data;
		    			}, beforeSend:function() {

						}, error : function(error) {
							console.log(error);
		    			}
    		});
		}

		/* ======================= Sign Up ================== */ 
		function chk_pwd(pwd) {
    		return $.ajax({
		    			type: 'POST',
		    			data: {
		    					"pwd"	: pwd
		    				  },
		    			url: "/member/pwdCheck",
		    			dataType: "json",
		    			success: function(data) {
		    				return data;
		    			}, error : function(request, status, error) {
							console.log(error);
		    			}
    		});
		}
		
		/* ==================  Sign Up  ================== */
        $('#btn_signup').click( function() {
        	$('.alert').hide();

    		var pattern_num = /[0-9]/;					// 숫자
        	var pattern_eng = /[a-zA-Z]/;				// 문자 
        	var pattern_spc = /[~!@#$%^&*()_+|<>?:{}]/; // 특수문자
        	var pattern_kor = /[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/; 		// 한글체크

        	var member_Id = $("#member_Id").val();
        	var member_Pw = $("#member_Pw").val();
        	var member_Pw_chk = $("#member_Pw_chk").val();
        	var member_Name = $("#member_Name").val();
        	var member_Phone = "";

        	var member_Zip = $("#zip").val();
        	var member_Addr1 = $("#addr1").val();
        	var member_Addr2 = $("#addr2").val();

        	var m_first = $('select[id="phone_1"]').val();	// 통신사
        	var m_second = $('input[id="phone_2"]').val();	// 첫 번째
        	var m_third = $('input[id="phone_3"]').val();	// 두 번째

			var member_chk = chk_member(member_Id); 		// id 존재여부
			
			/* ==================  ID check  ================== */
        	if( !member_Id ) {
        		$("#id-danger").show().html("이메일를 입력해주세요.");
            	$("#member_Id").focus(); 
            	return false; 
        	} else if(!regExp.test(member_Id)) {
		        $("#id-danger").show().html("이메일 형식과 일치 하지 않습니다.");
				$("#member_Id").focus();
        		return false;
        	} else if(idck == "" || idck == 0) {
		        $("#id-danger").show().html("중복확인을 진행 해주세요.");
				$("#member_Id").focus();
            	return false; 
        	}

			/* ================== Password Check ================== */
        	if( !member_Pw ) {
        		$("#pwd-danger").show().html("비밀번호를 입력해주세요.");
				$("#member_Pw").focus();
            	return false;
        	} else if( !member_Pw_chk ) {
        		$("#pwd-danger").show().html("비밀번호를 재입력 해주세요.");
				$("#member_Pw_chk").focus();
            	return false; 
        	} else if(member_Pw != member_Pw_chk) {
        		$("#pwd-danger").show().html("숫자 + 영문자를 혼용하여야 합니다.");
            	return false; 
        	}

			/* ================== Nickname Check ================== */
    		if( !member_Name ) {
        		$("#name-danger").show().html("이름을 입력해주세요.");
    			$("#member_Name").focus();
    			return false;
    		} else if(member_Name.length > 20) {
        		$("#name-danger").show().html("이름을 입력해주세요.");
    			$("#member_Name").focus();
    			return false;
    		}

			/* ================== Phone Check ================== */
    		if( m_first == "" || !m_second || !m_third ) {
    			$("#phone-danger").show().html("휴대폰 번호를 입력해주세요.");
    			$("select[name='member_Phone']").focus();
    			return false;
    		} else {
    			if((pattern_num.test(m_second)) && !(pattern_eng.test(m_second)) && !(pattern_spc.test(m_second)) && !(pattern_kor.test(m_second)) 
    				&& (pattern_num.test(m_third)) && !(pattern_eng.test(m_third)) && !(pattern_spc.test(m_third)) && !(pattern_kor.test(m_third))) {

        		} else {
        			$("#phone-danger").show().html("특수 문자를 사용할수 없습니다.");
        			$("input[name='member_Phone']").focus();
        			return false;
        		}
    		}

			/* ================== Post Check ================== */
    		if( !member_Zip || !member_Addr1 ) {
    			$("#post-danger").show().html("주소를 선택해주세요.");
    			$("#addr1").focus();
    			return false;
    		}

    		/* =================== 이용약관 =================== */
			if($('input[name="terms_chk"]:checked').length == 2) {
				member_chk.success(function(data) {
					member_chk = data.cnt;

		    		if(!member_Id) {
		        		$("#id-danger").show().html("이메일를 입력해주세요.");
		    	        $("#member_Id").focus();
		    			return false;
		    		} else if(member_chk == 1) {
		        		$("#id-danger").show().html("이미 등록된 이메일입니다. <a href='/member/search_password'>비밀번호 찾기 바로가기</a>");
		        		$("#member_Id").focus();
		        		return false;
		    		} else if(!regExp.test(member_Id)) {
				        $("#id-danger").show().html("이메일 형식과 일치 하지 않습니다.");
		    	        $("#member_Id").focus();
		    			return false;
		    		} else {

			            var objParams = {
			            					"id" 			:	member_Id, 
			            					"pw_01" 		:	member_Pw,
			            					"pw_02" 		:	member_Pw_chk,
			            					"name" 			:	member_Name,
			            					"m_first" 		:	m_first,
			            					"m_second" 		:	m_second,
			            					"m_third" 		:	m_third,
			            					"phone" 		:	member_Phone,
			            					"zip" 			: 	member_Zip,
			            					"addr1"			:	member_Addr1,
			            					"addr2"			:	member_Addr2
			            				};
			            
			            $.ajax({
			        		url	: "/member/member_signup",
			        		enctype : "multipart/form-data",
			        		dataType : "json",
			        		type : "POST",
			        		data : objParams,
			        		success : function(data) {
			        			var html = "";

			        			if(data == "0") {
			        				alert("잘못된 접근입니다");
			        			} else {
						            html += "<div class='wrap-loading'>";
						            html += "	<div>";
							        html += "		<div class='signUpSuccess'>";
							        html += "			<h2>회원가입 완료</h2>";
							        html += "			<p>입력하신 이메일로 인증메일이 전송 되었습니다.<br />인증 후 로그인을 하실 수 있습니다.</p>";
							        html += "			<a href='./login?code=OK' class=''>로그인 페이지로 이동</a>";
							        html += "		</div>";
							        html += "	</div>";
						            html += "</div>";

						            $('body').append(html);
			        			}
			        		}, beforeSend: function() {
			        			$('.wrap-loading').removeClass('display-none');
							}, complete: function() {

							}, error: function(error) {
								console.log(error);
			        		}
			            });
		    		}
				});
			} else {
		        $("#terms-danger").show().html("모든 약관에 동의 하셔야 회원가입이 진행 가능합니다.");
    			return false;
			}
        });

		/* ================================================== */
        $('#btn_update').click( function() {
	        $(".alert").hide();

    		var pattern_num = /[0-9]/;					// 숫자
        	var pattern_eng = /[a-zA-Z]/;				// 문자 
        	var pattern_spc = /[~!@#$%^&*()_+|<>?:{}]/; // 특수문자
        	var pattern_kor = /[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/; 		// 한글체크

        	var id = $('#id').val();
        	var member_Name = $('#name').val();
        	var member_Zip = $("#zip").val();
        	var member_Addr1 = $("#addr1").val();
        	var member_Addr2 = $("#addr2").val();

        	var m_first = $('select[id="tel_01"]').val();	// 통신사
        	var m_second = $('input[id="tel_02"]').val();	// 첫 번째
        	var m_third = $('input[id="tel_03"]').val();	// 두 번째

    		if( !member_Name ) {
    			alert("이름을 입력해주세요");
    	        $("#name").focus();
    			return false;
    		} else if(member_Name.length > 20) {
    			alert("입력범위를 초과하셨습니다");
    	        $("#name").focus();
    			return false;
    		}

			/* ================== Phone Check ================== */
    		if( m_first == "" || !m_second || !m_third ) {
    			alert("휴대폰 번호를 입력해주세요");
    			$("select[name='member_Phone']").focus();
    			return false;
    		} else {
    			if((pattern_num.test(m_second)) && !(pattern_eng.test(m_second)) && !(pattern_spc.test(m_second)) && !(pattern_kor.test(m_second)) 
    				&& (pattern_num.test(m_third)) && !(pattern_eng.test(m_third)) && !(pattern_spc.test(m_third)) && !(pattern_kor.test(m_third))) {

        		} else {
        			alert("특수 문자를 사용할수 없습니다");
        			$("input[name='member_Phone']").focus();
        			return false;
        		}
    		}

			$('.wrap-loading').addClass('display-none');
			$('#btn_update').prop('disabled', true);

			var html = "";

			html += "<div class='auth_main'>";
		    html += "	<span class='close_btn icon'><img src='../resources/image/icon/close.png' alt='' /></span>";
		    html += "	<h4>현재 비밀번호</h4>";
		    html += "	<hr />";
		    html += "	<p class='auth_txt'>현재 비밀번호를 입력하시길 바랍니다.</p>";
		    html += "	<table><tbody><tr><td>";
		    html += "		<input type='password' name='auth_txt' id='auth_txt'>";
		    html += "		<button id='info_pwd_chk' class='btn log'>확인</button>";
		    html += "	</td></tr></tbody></table>";
		    html += "</div>";

			$('#d_auth').append(html).css('display','block');

			info_auth();			// 
			info_auth_btn();		// 
        });

		/* ================================================== */
        function info_auth() {
        	$('#info_pwd_chk').click( function() {
            	var pwd = $('#auth_txt').val();
            	var member_chk = chk_pwd(pwd);
            	
            	if( !pwd ) {
            		alert("현재 비밀번호를 입력해주세요.");
            		return false;
            	} else {
        			member_chk.success(function(data) {
        				var cnt = data.cnt;

        				if(cnt == 0) {
        					alert("비밀번호가 일치하지 않습니다.");
        					return false;
        				} else {

        			        var objParams = {
        										"name" 		:	$('#name').val(),
        										"zip" 		:	$("#zip").val(),
        										"addr1"		:	$("#addr1").val(),
        										"addr2"		:	$("#addr2").val(),
        										"m_first" 	:	$('select[id="tel_01"]').val(),
        										"m_second"	:	$('input[id="tel_02"]').val(),
        										"m_third"	:	$('input[id="tel_03"]').val()
        									};

        			        $.ajax({
        			    		type: "POST",
        			    		data : objParams,
        			    		url	: "/info/member_Update",
        			    		dataType: "json",
        			    		success : function(data) {
        			    			if(data == "1") {
        			    				alert("" + $('#name').val() + " 님의 회원정보가 수정되었습니다.");
        			    			} else {
        			    				alert("" + $('#name').val() + " 님의 회원정보 수정에 실패했습니다.");
        			    			}

        			    			window.location.reload();
        		    			}, beforeSend: function() {
        		    				$('.wrap-loading').removeClass('display-none');
        						}, complete: function() {
        							$('.wrap-loading').addClass('display-none');
        						}
        			        });
        				}
        			});
            	}
        	});
        }

        /* =================== info_auth_btn ================== */
        function info_auth_btn() {
        	$('.close_btn').click( function() {
				$('#name').prop('readonly', false);
				$('#phone').prop('readonly', false);
				$('#btn_update').prop('disabled', false);

				$('.auth_main').remove();
				$('#d_auth').css('display','none');
        	});
        }
    	
        /* =================== idSaveCheck ================== */
        idSaveCheck();

        function idSaveCheck() {
        	var key = getCookie("key");
        	$(".remember").val(key);

        	if($(".remember").val() != "") {
        		$("#idSaveCheck").attr("checked", true);
        		$("#idSave").attr("src", "../resources/image/icon/chk_on.png");
        	}

        	$("#idSaveCheck").click( function() {
    			$(this).toggleClass('active');

    			if($(this).hasClass('active')) {
    	    		setCookie("key", $(".remember").val(), 7);

    	    		$(this).prop("checked", true);
    				$("#idSave").attr("src", "../resources/image/icon/chk_on.png");
    			} else {
        			$(this).prop("checked", false);
        			$("#idSave").attr("src", "../resources/image/icon/chk_off.png");
    			}
        	});

        	$(".remember").keyup(function() {
        		if($("#idSaveCheck").is(":checked")) {
        			setCookie("key", $(".remember").val(), 7);
        		}
        	});
        }

        /* ======================================================== */
    	function setCookie(cookieName, value, exdays) {
    		var exdate = new Date();
    		exdate.setDate(exdate.getDate() + exdays);
    		
    		var cookieValue = escape(value) + ((exdays==null) ? "" : "; expires=" + exdate.toGMTString());
    		document.cookie = cookieName + "=" + cookieValue;
    	}

        /* ======================================================== */ 
    	function deleteCookie(cookieName) {
    		var expireDate = new Date();
    		expireDate.setDate(expireDate.getDate() - 1);
    		document.cookie = cookieName + "= " + "; expires=" + expireDate.toGMTString();
    	}

        /* ======================================================== */
    	function getCookie(cookieName) {
    		cookieName = cookieName + '=';
    		var cookieData = document.cookie;
    		var start = cookieData.indexOf(cookieName);
    		var cookieValue = '';

    	    if(start != -1) {
    	    	start += cookieName.length;
    	    	var end = cookieData.indexOf(';', start);
    	    	if(end == -1)end = cookieData.length;
    	    	cookieValue = cookieData.substring(start, end);
    	    }

    	    return unescape(cookieValue);
    	}
	});
});
