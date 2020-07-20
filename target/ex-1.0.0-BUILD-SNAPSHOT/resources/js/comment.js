jQuery.noConflict();

jQuery(function($) {
	$(document).ready(function() {
		
		/* ====================================== */
        textLength();
        writeComment();
	});
});

/* ============== comment write ============= */
function writeComment() {
	$(document).ready(function() {
		$('#r_write').click( function() {
			var seq = $('#seq').val();					// 
			var id = $('#session').val();				// 
			var content = $('#r_content').val();		// 

			if( !id ) {
				alert("로그인 후에 작성해주세요.");
				location.href="../member/login";
			} else {
				if( !seq ) {
					alert("글이 없습니다.");
					return false;
				}

				if( !content ) {
					alert("글을 입력해주세요.");
					$('#r_content').focus();
					return false;
				} else {
					var insert_Id;
					var objParams = {
										"b_Seq" 		:	seq,
										"c_Id" 			:	id,
										"c_content" 	:	content,
										"c_parent" 		:	0,
										"c_depth" 		:	0
									};

					$.ajax({
						url : "../comment_write",
						enctype : "multipart/form-data",
						dataType : "json",
						type : "POST",
						data : objParams,
						success : function(retVal) {
							if(retVal.code != "OK") {
								alert(retVal.message);
							} else {
								var date = new Date();
								insert_seq = retVal.seq;

			                    var reply = 
			                        '<li id="point' + insert_seq + '" class="c_wrap" value="0">' +
			                        	'<span>' +
			                        	'<h4>' + id + '</h4>' +
			                        	'<span>' + getFormatDate(date) + '</span>' + 
			                        	'</span>' +
			                        	'<p id="c_' + insert_seq + '">' + content +'</p>' +
			                        	'<ul>' +
			                            	'<li>' +
			                           			'<button onclick="javascript:editComment(' + insert_seq + ');" class="btn btn-default btn-xs">수정</button>' +
			                           		'</li>' +
			                           		'<li>'+
			                           			'<button onclick="javascript:deleteComment(' + insert_seq + ');" class="btn btn-default btn-xs">삭제</button>' +
			                           		'</li>';

			                    if( id != null ) {
			                    	reply += '<li>' +
			                    				'<button onclick="javascript:addComment(' + insert_seq + ');" class="btn btn-default btn-xs">댓글</button>' +
			                    			 '</li>';
			                    }

			                    reply += '</ul>' +
			                    	'</li>';

		                    	$(".r_list > ul > li.end").before(reply); // 출력

			                    $('html, body').animate({
			                    	scrollTop: $('#point' + insert_seq).offset().top
			                    }, 'slow');

			                    $('#r_content').val("");
							}
						}, error : function(request, status, error) {
							console.log(response.message);
						}
					});
				}
			}
		});
	});
}

/* ============== 추가 댓글 ============= */
function addComment(seq) {
	$(document).ready(function(e) {
		$('#c_addComment').remove();

		var id = $('#session').val();
		var last_check = false;

        var html = 
		        	'<div id="c_addComment" class="c_add_' + seq + '" value="0">' +
		        		'<i class="fa fa-share fa-flip-vertical re"><img src="../resources/image/icon/comment.png" alt="" /><p class="txt">댓글쓰기</p></i>' +
		            	'<p><textarea name="reply_reply_content" id="c_addComment_' + seq + '"></textarea>' +
		            	'<button onclick="sendAddComment(' + seq + ')" class="btn btn-default btn-xs">제출</button>' +
		            	'<button onclick="cancelAddComment(' + seq + ')" class="btn btn-default btn-xs">취소</button>' +
		            	'</p>' +
		           	'</div>';

		$(".c_add_" + seq).addClass('on');
		if($(".c_add_" + seq).hasClass('on') == true) {
        	$(".c_add_" + seq).remove();
		} else {
			var prevTr = $("#point" + seq).next();

			if(prevTr.attr("value") != 0 || prevTr.attr("value") != undefined) {
				while(prevTr.attr("value") == 1) {
					prevTr = prevTr.next();
				}

				if(prevTr.attr("value") == 0 || prevTr.attr("value") == undefined) {
					last_check = true;
				} else {
					prevTr = prevTr.prev();
				}
			}

			if(last_check) {
				if(prevTr.attr("value") != 0 || prevTr.attr("value") != undefined) {
					if($("#point" + seq).is(':last-child')) {
						$("#point" + seq + ":last").after(html);
					} else {
						prevTr.before(html);
					}
				} else {
					prevTr.append(html);
				}
			}

            $('html, body').animate({
            	scrollTop: $('#point' + seq).offset().top
            }, 'slow');
		}
	});
}

function sendAddComment(reply_num) {
	$(document).ready(function(e) {
		textLength();

		var board_num = $('#seq').val();
		var id = $('#session').val();
		var content = $("#c_addComment_" + reply_num).val();
		
		if(!id) {
			alert("로그인해주세요.");
			location.href="../member/login";
		} else {
			if(!content) {
				alert("글을 입력해주세요.");
				$("#c_addComment_" + reply_num).focus();
				return false;
			} else {
				var last_check = false;
				var insert_seq;
				var objParams = {
		        		"b_Seq" : board_num,
		        		"c_Id"	: id,
		        		"c_content" : content,
		        		"c_parent" : reply_num,
		        		"c_depth" : 1
		        };

				$.ajax({
					url : "../comment_write",
					enctype : "multipart/form-data",
					dataType : "json",
					type : "POST",
					data : objParams,
					success : function(retVal) {
						date = new Date();
						insert_seq = retVal.seq;

	                    var reply = 
	                        '<li id="point' + insert_seq + '" class="c_wrap" value="1">' +
	                        	'<img src="../resources/image/icon/comment.png" alt="">' +
	                        	'<span>' +
	                        	'<h4>' + id + '</h4>' +
	                        	'<span>' + getFormatDate(date) + '</span>' + 
	                        	'</span>' +
	                        	'<p id="c_' + board_num + '">' + content + '</p>' + 
	                        	'<ul>' +
	                        		'<li>' + 
	                        			'<button onclick="javascript:editComment(' + insert_seq + ');" class="btn btn-default btn-xs">수정</button>' + 
	                        		'</li>' +
	                        		'<li>' + 
	                        			'<button onclick="javascript:deleteComment(' + insert_seq +');" class="btn btn-default btn-xs">삭제</button>' +
	                        		'</li>' + 
	                        	'</ul>' +
	                       	'</li>';
	                    
	        			var prevTr = $("#point" + reply_num).next();

	        			if(prevTr.attr("value") == 0 || prevTr.attr("value") == undefined) {
	        				prevTr.prev().after(reply);
	        			} else {
	        				while(prevTr.attr("value") == 1) {
	        					prevTr = prevTr.next();
	        				}

	        				if(prevTr.attr("value") == 0 || prevTr.attr("value") == undefined) {
	        					last_check = true;
	        				} else {
	        					prevTr = prevTr.prev();
	        				}
	        			}
	        			
	        			if(last_check) {
	        				prevTr.before(reply);
	        			}
	        			$("#c_addComment").remove();
					}, error : function(request, status, error) {
						console.log(response.message);
					}
				});
			}
		}
	});
}

function deleteComment(seq) {
	$(document).ready(function(e) {
		var check = false;
		var flag = confirm("해당 댓글을 삭제하시겠습니까?");

		if(flag) {
			
			var objParams = {
	        		"del" : "2",
	        		"seq" : seq
	        };
			
			$.ajax({
				url : "../c_delete/comment_num",
				enctype : "multipart/form-data",
				dataType : "json",
				type : "POST",
				data : objParams,
				success : function(data) {
					if(data != true) {
						alert("댓글 삭제에 실패했습니다. 다시 시도해주세요");
						return false;
					} else {
						check = data;
						if(check) {
							var prevTr;

							if($("#point" + seq).attr("value") == "0") {
								prevTr = $("#point" + seq).next();
								while(prevTr.attr("value") == "1") {
									prevTr = prevTr.next();
									prevTr.prev().remove();
		        				}
							} else {
								prevTr = $("#point" + seq);
							}

							$("#point" + seq).remove();
						}
					}
				}, error : function(request, status, error) {
					console.log(response.message);
				}
			});
		}
	});
}

function editComment(seq) {
	$(document).ready(function() {
		textLength();
		$('#c_update').remove();

		var id = $("#point" + seq).find("h4").html().trim();
		var content = $("#point" + seq).find("p").html().trim();

        var html = 
            '<div id="c_update" class="c_edit_' + seq + '">' +
            	'<input type="hidden" id="c_id_' + seq + '" value="' + id + '" />' +
            	'<h4>댓글 수정</h4>' +
            	'<p><textarea class="r_chk" id="c_content_' + seq + '">' + content +'</textarea>' +
            	'<button onclick="sendEdit(' + seq + ')" class="btn btn-default btn-xs">수정</button>' +
            	'<button onclick="cancelEdit(' + seq + ')" class="btn btn-default btn-xs">취소</button>' +
            	'</p>' +
           	'</div>';

		$(".c_edit_" + seq).addClass('on');
		if($(".c_edit_" + seq).hasClass('on') == true) {
        	$(".c_edit_" + seq).remove();
		} else {
	        $("#point" + seq).append(html);
		}
	});
}

function sendEdit(seq) {
	$(document).ready(function() {
		var flag = confirm("해당 댓글을 수정하시겠습니까?");
		var id = $("#c_id_" + seq).val();
		var content = $("#c_content_" + seq).val();
		
		if(flag) {
			if( !id ) {
				alert("로그인 후에 작성해주세요.");
				location.href="../member/login";
				return false;
			}

			if( !content ) {
				alert("글을 입력해주세요.");
				$("#c_content_" + seq).focus();
				return false;
			} else {
				var objParams = {
						"c_Seq" : seq,
						"c_Id" : id,
						"c_content" : content,
						"c_parent" : 0,
						"c_depth" : "N"
					};

				$.ajax({
					url : "../comment_update",
					enctype : "multipart/form-data",
					dataType : "json",
					type : "POST",
					data : objParams,
					success : function(data) {
						if(data.code != "OK") {
							alert("수정실패!!");
							return false;
						} else {
							$('#c_' + seq).html(content);
							$(".c_edit_" + seq).remove();
						}
					}, error : function(request, status, error) {
						console.log(response.message);
					}
				});
			}
		}
	});
}

function cancelEdit(seq) {
	$(document).ready(function() {
		$(".c_edit_" + seq).remove();
	});
}

function cancelAddComment(seq) {
	$(document).ready(function(e) {
		$(".c_add_" + seq).remove();
	});
}

function getFormatDate(date) {
	var year = date.getFullYear();
	var month = (1 + date.getMonth());
	month = month >= 10 ? month : '0' + month;

	var day = date.getDate(); 
	day = day >= 10 ? day : '0' + day;

	return year + '.' + month + '.' + day;
}

function textLength() {
	$(document).ready(function(e) {
	    $('.r_chk').on('keyup', function() {
	    	if($(this).val().length > 300) {
	    		alert("300자 이상 작성하실 수 없습니다.");
	    		$(this).val($(this).val().substring(0, 300));
	    	}
	    });
	});
}
