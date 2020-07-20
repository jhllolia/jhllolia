jQuery.noConflict();

jQuery(function($) {
	$(document).ready(function() {

		var sortArr = new Array();

		hashTabChange();
		/* ============ 해시태그 순서변경 =============*/
		function hashTabChange() {
			$("#file_div").sortable({
				placeholder: "ui-state-highlight"
				,helper: fixWidthHelper
				,axis: 'y'
				,stop: function (event, ui) {
					setSortTab();				// 시퀀스 정렬
				}
			});
		}

		/* ============ 시퀀스 배열 =============*/
		function sortArray() {
			sortArr.length = 0;

		    $("#file_div p").each(function(index, value) {
		        var obj = new Object();
		        var idx = $(this).attr('file_idx');

		        if(idx != "" || idx != null) {
			        obj.fileSeq = $(this).attr('file_idx');		// 인덱스

			        sortArr.push(obj);
		        }
		    });

		    return sortArr;
		}

		/* ============ 드래그 레이아웃 =============*/
		function fixWidthHelper(e, ui) {
			ui.children().each(function() {
				$(this).width($(this).width());
			});

		    return ui;
		}

		/* ============ 시퀀스 정렬, 변경 =============*/
		function setSortTab() {
			var formData= new FormData();

		    $("#file_div p").each(function (index, value) {
		        $(this).data("seq", index);
		    });

			sortArray();		// 해시태그 순서변경
			formData.append("arr", JSON.stringify(sortArr));

			$.ajax({
				url	 : "/sort/listChange",
				type : 'POST',
				data : formData,
				processData: false,
				contentType: false
			});
		}
	});
});
