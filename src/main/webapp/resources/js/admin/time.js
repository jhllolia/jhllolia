jQuery.noConflict();

jQuery(function($) {
	$(document).ready(function() {
	
		comeTable();
		visitIp();
		excelDown();
	
		/* ================== Excel Download ===================== */
		function excelDown() {
			$('#excel').click(function(e) {
				var date = $('#datepicker').val();					// 조회 날짜
	
				if( !date ) {
					alert("날짜를 선택해 주세요.");
					return false;
				} else {
					var form = "";
	
					form += "<form action='/excelDown' method='POST'>"; 
					form += "	<input type='hidden' name='keyword' value='" + date + "' />";
					form += "</form>";
	
					$(form).appendTo("body").submit().remove();		// 조회
				}
			});
		}
	
		/* ================== Link IP ======================= */
		function visitIp() {
			$('span#visitIp').click(function(evt) {
				$('span#visitIp').removeClass('selected');
	
				var $this = $(this);			// 해당 아이피
				var nowIp = $this.text();		// 해당 아이피
	
		    	$.ajax({
					url	: "../ipSearch",
					dataType : "json",
					type : "POST",
					data : { "ip" : nowIp },
					success : function(data) {
						ipStatus(data, $this);
					}, error : function(request, status, error) {
						console.log(response.message);
					}
		    	});
			});
		}
	
		/* ================== IP Status ======================= */
		function ipStatus(obj, $this) {
			$('#info').remove();
			$this.addClass('selected');
	
			var html = "";
			var scrollTop = $this.position().top;
			var scrollLeft = $this.position().left;
			var JsonData = JSON.parse(obj);
	
			html += "<div class='visitInfo' id='info'>";
			html += "	<table id='infoTable'>";
			html += "		<tbody>";
	
			if(JsonData["status"] != "success") {
				html += "		<tr>";
				html += "			<td class='infoTd'>검색 결과가 존재 하질 않습니다</td>";
				html += "		</tr>";
			} else {
				html += "		<tr>";
				html += "			<td colspan='2' class='infoTd'><div id='map' style='width: 100%; height: 200px;'></div></td>";
				html += "		</tr>";
				html += "		<tr>";
				html += "			<td colspan='2'><h4>" + JsonData["query"] + "</h4></td>";
				html += "		</tr>";
				html += "		<tr>";
				html += "			<td><h4>통신사</h4></td>";
				html += "			<td>" + JsonData["as"] + "</td>";
				html += "		</tr>";
				html += "		<tr>";
				html += "			<td><h4>나라</h4></td>";
				html += "			<td>" + JsonData["timezone"] + "</td>";
				html += "		</tr>";
				html += "		<tr>";
				html += "			<td><h4>도시</h4></td>";
				html += "			<td>" + JsonData["regionName"]  + " ( " + JsonData["zip"] + " " + JsonData["city"] + " ) " + "</td>";
				html += "		</tr>";
			}
	
			html += "		</tbody>";
			html += "	</table>";
			html += "</div>";
	
			$this.append(html);
	
			/* ================== Map position ======================= */
			var mapContainer = document.getElementById('map');
			var position = new naver.maps.LatLng(JsonData["lat"], JsonData["lon"]);
			var mapOption = {
				center: position,
				zoom: 7
			};
	
			/* ================== Map marker ======================= */
			var map = new naver.maps.Map(mapContainer, mapOption);
			var marker = new naver.maps.Marker({
			    position: position,
			    map: map
			});
		
			/* ================== Map circle ======================= */
			var circle = new naver.maps.Circle({
			    map: map,
			    center: position,
			    radius: 1000,
			    fillColor: '#f2f2f2',
			    fillOpacity: 0.8
			});
	
			$('#info').css({ "top" : scrollTop, "left" : scrollLeft }).draggable();
		}
	
		/* ================== Get Date / Select ======================= */
		function comeTable() {
			var date = getParam("date");	// Parameter
	
			if( !date ) {
				$("#datepicker").val(today());
			} else {
				$("#datepicker").val(date);
			}
	
			$("#datepicker").datepicker({
				dateFormat: "yy-mm-dd",		//	날짜 형식
				changeMonth: true,			//	달
				changeYear: true,			//	년
				onClose: function(obj) {
					var form = "";
					form += "<form action='/come?number=999&date=" + obj + "' method='post'>"; 
					form += "	<input type='hidden' name='date' value='" + obj + "' />";
					form += "</form>";
	
					$(form).appendTo("body").submit().remove();
				}	// 출력
	        });
		}
	
		/* ================== URL Param ======================= */
		function getParam(sname) {
			var params = location.search.substr(location.search.indexOf("?") + 1);
			var sval = "";
			params = params.split("&");
	
			for (var i = 0; i < params.length; i++) {
				temp = params[i].split("=");
		        if ([temp[0]] == sname) { 
		        	sval = temp[1]; 
		        }
		    }
	
		    return sval;
		}
	
		/* ================== getDateStr ======================= */
		function getDateStr(myDate) {
			return  (myDate.getFullYear() + '-' + ("0" + (myDate.getMonth() + 1)).slice(-2) + '-' + ("0" + myDate.getDate()).slice(-2));
		}
	
		/* ================== Today ======================= */
		function today() {
			var d = new Date();
			return getDateStr(d);
		}
	
		/* ================================================ */
	});
});
