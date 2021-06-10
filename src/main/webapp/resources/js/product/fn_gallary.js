jQuery.noConflict();

jQuery(function($) {
	$(document).ready(function() {

		var sortedItems = new Array();

	    $("#sortable").sortable({
	    	placeholder: "ui-state-highlight"
	    	,helper: fixWidthHelper
	    	,axis: 'y'
	    	,stop: function (event, ui){
	    		SetSortOrder();
	            var items = PopulateSortItems();
	            SubmitData(items);
	    	}
	    });

	    $("#sortable").disableSelection();

	    SetSortOrder();

	    //Set the data-order attribute to the index
	    function SetSortOrder() {
	        $("#sortable tr").each(function (index, value) {
	            $(this).data("seq", index);
	        });
	    }

	    //AJAX
	    function SubmitData(items) {
	    	var formData = new FormData();
	    	formData.append("arr",JSON.stringify(items));

			$.ajax({
				url	 : "/gallary/ordProc",
				type : 'POST',
				data : formData,
				processData: false,
				contentType: false
			});
	    }

	    //Get the current order state
	    function PopulateSortItems() {
	        sortedItems.length = 0;

	        $("#sortable tr").each(function (index, value) {
	        	var obj = new Object();

	        	obj.idx		= $(this).attr('data-idx');
	        	obj.title	= $(this).attr('data-title');
	        	obj.ord		= index;

	        	sortedItems.push(obj);
	        });

	        return sortedItems;
	    }

		function fixWidthHelper(e, ui) {
		    ui.children().each(function() {
		        $(this).width($(this).width());
		    });
		    return ui;
		}
		
		/* =========== 등록 / 수정 =========== */
		$("#sendGallary").click(function() {
			sendGallaryData();
		});

		/* =========== 삭제 =========== */
		$("#deleteGallary").click(function() {
			$('#del_yn').val("Y");
			sendGallaryData();
		});
		
		function sendGallaryData() {
			
			if(!$("#pcWidth").val() || !$("#pcHight").val()) {
				alert("큰 이미지 사이즈를 적용해주세요.");
				return false;
			}

			if(!$("#mobWidth").val() || !$("#mobHight").val()) {
				alert("작은 이미지 사이즈를 적용해주세요.");
				return false;
			}
/*
			if(!$('#imageFile').val()) {
				alert("이미지들 등록해주세요.")
				return false;
			}
*/
			$("f").submit();
		}

		/* =========== 이미지 사이즈 수정 =========== */
		$("#imageFile").on("change", resizeImage);

		function resizeImage() {
			var filesToUpload = document.getElementById('imageFile').files;
			var file = filesToUpload[0];

			// 문서내에 img 객체를 생성합니다
			var img = document.createElement("img");
			// 파일을 읽을 수 있는 File Reader 를 생성합니다
			var reader = new FileReader();

			reader.onload = function(e) {
				img.src = e.target.result;
				console.log("img.src ===> " + img.src)
				$('#output').attr('src',img.src);
/*
				// HTML5 canvas 객체를 생성합니다
		        var canvas = document.createElement("canvas");      
		        var ctx = canvas.getContext("2d");
		        
		        // 최대폭을 400 으로 정했다고 가정했을때
		        // 최대폭을 넘어가는 경우 canvas 크기를 변경해 줍니다.
		        var MAX_WIDTH = 400;
		        var MAX_HEIGHT = 400;
		        var width = img.width;
		        var height = img.height;
		        
		        if (width > height) {
		        	if (width > MAX_WIDTH) {
		                height *= MAX_WIDTH / width;
		                width = MAX_WIDTH;
		        	}
		        } else {
		        	if (height > MAX_HEIGHT) {
		                width *= MAX_HEIGHT / height;
		                height = MAX_HEIGHT;
		        	}
		        }

		        canvas.width = width;
		        canvas.height = height;

		        // canvas에 변경된 크기의 이미지를 다시 그려줍니다.
		        var ctx = canvas.getContext("2d");
		        ctx.drawImage(img, 0, 0, width, height);

		        // canvas 에 있는 이미지를 img 태그로 넣어줍니다
		        var dataurl = canvas.toDataURL("image/png");
		        document.getElementById('subOutput').src = dataurl;
*/
			}

			reader.readAsDataURL(file);
		}

		$('#deleteGallary').hide();

		$('[name="clickGallary"]').click(function() {

			$('#img_idx').val($(this).attr('data-idx'));
			$('#title').val($(this).attr('data-title'));
			$('#web_image').val($(this).attr('data-web'));
			$('#mob_image').val($(this).attr('data-mob'));

			$('#deleteGallary').show();
			$('.img-fluid').attr('src','../resources/upload/gallary/' + $(this).attr('data-web'));
			$('#imageFile').val($(this).attr('data-web'));
		});

	});
});