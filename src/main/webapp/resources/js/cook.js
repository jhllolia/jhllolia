jQuery.noConflict();

jQuery(function($) {
	$(document).ready(function() {

		/* ======================================= */
/*
		window.onload = function () {
			ckeditor();
		}
*/

		/* ======================================= */
		$('#latter').click( function() {
			var seseionCheck = $('#session').val();
			
			if( !seseionCheck ) {
				alert("로그인 후에 작성해주세요.");
				location.href="../member/login";
			} else {
				location.href="./recipe_write";
			}
		});

		/* ======================================= */
		$("#recipe_write").click(function() {
			var id = $('#user_Id').val();
			var subject = $('#recipe_Subject').val();
			var content = CKEDITOR.instances.ckeditor.getData();

			if (!id) {
				alert("로그인을 해주세요");
				$("#user_Id").focus();
				return false;
			} else if (id.trim() == "") {
				alert("로그인을 해주세요");
				$("#user_Id").focus();
				return false;
			}

			if ( subject.trim() == "" ) {
				alert("제목을 입력 해주세요");
				$("#recipe_Subject").focus();
				return false;
			}

			if ( !content ) {
				alert("글을 입력하세요.");
				return false;
			}

			var objParams = {
								"id" 		:	id,
								"subject"	:	subject,
								"content"	:	content
							};

			$.ajax({
				url : "../recipe_write",
				enctype : "multipart/form-data",
				dataType : "json",
				type : "POST",
				data : objParams,
				success : function(retVal) {
					if (retVal.code == "OK") {
						alert(retVal.message);
						location.href = "../bbs/recipe"
					} else {
						alert(retVal.message)
					}
				}, error : function(request, status, error) {
					alert(response.message)
				}
			});
		});
		
		/* ======================================= */
		$('#recipe_delete').click(function() {
			intSeq = $('#seq').val();

			$('.wrap-loading').addClass('display-none');				
			$('.d_auth').css('display','block');

			bbs_delete_auth();
			bbs_auth_btn();
		});

		/* ======================================= */
		$('#recipe_Update').click(function() {
			intSeq = $('#seq').val();
			subject = $('#recipe_Subject').val();
			content = CKEDITOR.instances.ckeditor.getData();	

			if ( subject.trim() == "" ) {
				alert("제목을 입력 해주세요");
				$("#recipe_Subject").focus();
				return false;
			}

			if ( !content ) {
				alert("글을 입력하세요.");
				return false;
			}
			
			$('.wrap-loading').addClass('display-none');				
			$('.d_auth').css('display','block');

			bbs_update_auth();
			bbs_auth_btn();
		});

		/* ======================================= */
        function bbs_update_auth() {
        	$('#info_pwd_chk').click( function() {
        		var id = $('#id').val();
            	var pwd = $('#auth_txt').val();
            	var member_chk = bbs_pwd(id,pwd);

            	if( !pwd ) {
            		alert("현재 비밀번호를 입력해주세요.");
            		return false;
            	} else {
        			member_chk.success(function(data) {
        				var cnt = data.cnt;

        				if(cnt == 0) {
        					alert("비밀번호가 존재하질 않습니다.");
        					return false;
        				} else {
        			        
        					var objParams = {
    								"intSeq" 	:	intSeq,
    								"subject" 	:	subject,
    								"content" 	:	content
        					};

			    			$.ajax({
			    				url : "../recipe_update",
			    				enctype : "multipart/form-data",
			    				dataType : "json",
			    				type : "POST",
			    				data : objParams,
			    				success : function(retVal) {
			    					if (retVal.code == "OK") {
			    						alert(retVal.message);
			    						location.href = "../bbs/recipe"
			    					} else {
			    						alert(retVal.message)
			    					}
        		    			}, beforeSend: function() {
        		    				$('.wrap-loading').removeClass('display-none');
        						}, complete: function() {
        							$('.wrap-loading').addClass('display-none');
        						}, error : function(request, status, error) {
			    					alert(response.message)
			    				}
			    			});
        				}
        			});
            	}
        	});
        }

		/* ======================================= */
        function bbs_delete_auth() {
        	$('#info_pwd_chk').click( function() {
        		var id = $('#id').val();
            	var pwd = $('#auth_txt').val();
            	var member_chk = bbs_pwd(id,pwd);

            	if( !pwd ) {
            		alert("현재 비밀번호를 입력해주세요.");
            		return false;
            	} else {
        			member_chk.success(function(data) {
        				var cnt = data.cnt;

        				if(cnt == 0) {
        					alert("비밀번호가 존재하질 않습니다.");
        					return false;
        				} else {

        					var objParams = {
        										"del" : "1",
        										"seq" : intSeq,
        										"r_state" : "N"
        					};

			    			$.ajax({
			    				url : "../delete/bbs",
			    				data : objParams,
			    				enctype : "multipart/form-data",
			    				dataType : "json",
			    				type : "POST",
			    				success : function(retVal) {
			    					if (retVal.code == "OK") {
			    						alert(retVal.message);
			    						location.href = "../bbs/recipe"
			    					} else {
			    						alert(retVal.message);
			    					}
        		    			}, beforeSend: function() {
        		    				$('.wrap-loading').removeClass('display-none');
        						}, complete: function() {
        							$('.wrap-loading').addClass('display-none');
        						}, error : function(request, status, error) {
			    					alert(response.message);
			    				}
			    			});
        				}
        			});
            	}
        	});
        }

		/* ======================================= */
        function bbs_auth_btn() {
        	$('.close_btn').click( function() {
				$('.d_auth').css('display','none');
        	});
        }

		/* ======================================= */
		function bbs_pwd(id,pwd) {
    		return $.ajax({
		    			type: 'POST',
		    			data: {
	    						"id" 	:	id,
		    					"pwd" 	:	pwd
		    			}, url: "/member/pwdCheck",
		    			dataType: "json",
		    			success: function(data) {
		    				return data;
		    			}, error : function(request, status, error) {
		    				alert("error : " + error);
		    			}
    				});
		}

		/* ======================================= */
/*
		function ckeditor() {
			var editorConfig = {
					filebrowserUploadUrl : "/bbs/fileUpload", 
					extraPlugins: 'youtube'
			};

			CKEDITOR.plugins.addExternal( 'youtube', '/js/youtube/', 'plugin.js' );
			CKEDITOR.replace( 'ckeditor', editorConfig );
	        CKEDITOR.on('dialogDefinition', function( ev ) {
	        	var dialogName = ev.data.name;
	        	var dialogDefinition = ev.data.definition;
	          
	        	switch (dialogName) {
	        		case 'image': //Image Properties dialog
	        			//dialogDefinition.removeContents('info');
		        		dialogDefinition.removeContents('Link');
		                dialogDefinition.removeContents('advanced');
		                break;
	            }
	        });

			CKEDITOR.config.height = 600;
			CKEDITOR.config.youtube_width = 900;
			CKEDITOR.config.youtube_height = 480;
			CKEDITOR.config.youtube_responsive = true;
			CKEDITOR.config.youtube_related = true;
			CKEDITOR.config.youtube_controls = true;
			CKEDITOR.config.resize_enabled = false;
		}
*/

	});
});