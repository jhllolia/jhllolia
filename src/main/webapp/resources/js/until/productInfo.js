jQuery.noConflict();

jQuery(function($) {
	$(document).ready(function() {

		data_Info();

		function data_Info() {
			$('td[name^="info_"]').each(function(index, value) {
				var html = "";
				var count = 0;

				var data = $(this).attr('data-result');
				var product = JSON.parse(data);

				$.each(product, function(key, value) {
					count = ++key;
				});

				$(this).attr('data-count' , count);

				for (var i in product) {
					var sell = product[i].sell;

					html += "<div class='info_option'>";
					html += "	<p>";
					html += "		<span>" + product[i].option + "</span>" + "/";
					html += "		<span>" + product[i].price + " 원</span>" + "/";
					html += "		<span>" + product[i].sale + " %</span>";
					
					if(sell == "Y") {
						html += "		<button name='sell_state' data-num='" + product[i].num + "' data-seq='" + $(this).attr('seq') + "' data-state='" + sell + "' class='btn log sell_Yes'>판매</button>";
					} else {
						html += "		<button name='sell_state' data-num='" + product[i].num + "' data-seq='" + $(this).attr('seq') + "' data-state='" + sell + "' class='btn log sell_No'>품절</button>";
					}

					html += "	</p>";
					html += "</div>";
				}

				$(this).append(html);
			});
		}
	});
});