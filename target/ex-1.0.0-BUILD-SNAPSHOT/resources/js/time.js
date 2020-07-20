jQuery.noConflict();

jQuery(function($) {
	$(document).ready(function() {
	
		comeTable();
	
		visitIp();
		excelDown();
	
		/* ================== Link IP ======================= */
		function excelDown() {
			$('#excel').click(function(e) {
				var date = $('#datepicker').val();	// date
	
				if(!date) {
					alert("날짜를 선택해 주세요.");
					return false;
				} else {
					var form  = "<form action='/excelDown' method='POST'>"; 
					form += "<input type='hidden' name='keyword' value='" + date + "' />";
					form += "</form>";
	
					$(form).appendTo("body").submit().remove();
				}
			});
		}
	
		/* ================== Link IP ======================= */
		function visitIp() {
			$('span#visitIp').click(function(evt) {
				$('span#visitIp').removeClass('selected');
	
				var $this = $(this);
				var nowIp = $this.text();
	
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
	
		function ipStatus(obj, $this) {
			$('#info').remove();
			$this.addClass('selected');
	
			var html = "";
			var scrollTop = $this.position().top;
			var scrollLeft = $this.position().left;
			var JsonData = JSON.parse(obj);
	
			html += "<div class='visitInfo' id='info'>";
				html += "<table id='infoTable'>";
					html += "<tbody>";
	
						if(JsonData["status"] != "success") {
							html += "<tr>";
								html += "<td class='infoTd'>검색 결과가 존재 하질 않습니다</td>";
							html += "</tr>";
						} else {
							html += "<tr>";
								html += "<td colspan='2' class='infoTd'><div id='map' style='width: 100%; height: 200px;'></div></td>";
							html += "</tr>";
							html += "<tr>";
								html += "<td colspan='2'><h4>" + JsonData["query"] + "</h4></td>";
							html += "</tr>";
							html += "<tr>";
								html += "<td><h4>통신사</h4></td>";
								html += "<td>" + JsonData["as"] + "</td>";
							html += "</tr>";
							html += "<tr>";
								html += "<td><h4>나라</h4></td>";
								html += "<td>" + JsonData["timezone"] + "</td>";
							html += "</tr>";
							html += "<tr>";
								html += "<td><h4>도시</h4></td>";
								html += "<td>" + JsonData["regionName"]  + " ( " + JsonData["zip"] + " " + JsonData["city"] + " ) " + "</td>";
							html += "</tr>";
						}
	
					html += "</tbody>";
				html += "</table>";
			html += "</div>";
	
			$this.append(html);
	
			var mapContainer = document.getElementById('map');
			var mapOption = {
				center: new daum.maps.LatLng(JsonData["lat"], JsonData["lon"]),
				level: 10
			};
	
			var map = new daum.maps.Map(mapContainer, mapOption);
			var marker = new kakao.maps.Marker();
			
			var circle = new kakao.maps.Circle({
				center : new kakao.maps.LatLng(JsonData["lat"], JsonData["lon"]),  // 원의 중심좌표 입니다 
				radius: 10000, // 미터 단위의 원의 반지름입니다 
				strokeWeight: 5,
				strokeColor: '#75B8FA',
				strokeOpacity: 1,
				strokeStyle: 'dashed',
				fillColor: '#CFE7FF',
				fillOpacity: 0.7
			}); 
	
			circle.setMap(map); 
	
			kakao.maps.event.addListener(map, 'tilesloaded', displayMarker);
	
			function displayMarker() {
			    marker.setPosition(map.getCenter());
			    marker.setMap(map);
			}
	
			$('#info').css({ "top" : scrollTop, "left" : scrollLeft }).draggable();
		}
	
		/* ================== Get Date / Select ======================= */
	
		function comeTable() {
			var date = getParam("date");
	
	    	if(typeof date == "undefined" || date == null || date == "") {
				$("#datepicker").val(today());
			} else {
				$("#datepicker").val(date);
			}
	
			$("#datepicker").datepicker({
				dateFormat: "yy-mm-dd",
				changeMonth: true,
				changeYear: true,
				onClose: function(obj) {
					var form  = "<form action='/admin?date=" + obj + "' method='post'>"; 
					form += "<input type='hidden' name='date' value='" + obj + "' />";
					form += "</form>";
	
					$(form).appendTo("body").submit().remove();
				}
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
	
	});
});
