jQuery.noConflict();

jQuery(function($) {
	$(document).ready(function() {
	
		var imageSrc = "http://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png";
		var imageSize = new daum.maps.Size(24, 35); 
	    var markerImage = new daum.maps.MarkerImage(imageSrc, imageSize);
	
		var m_idx = $('#listCnt').val();
	    var m_point = $( 'input[name="m_point"]' ).get();
		var mapArray = new Array();
		var selectedMarker = null;
		var iwRemoveable = true;
	
		var mapContainer = document.getElementById('map');
		mapContainer.style.width = '90%';

		var mapOption = {
			center: new daum.maps.LatLng(35.947108,126.9566381),
			level: 7
		};
	
		var map = new daum.maps.Map(mapContainer, mapOption); // 지도를 생성합니다

		for (var i = 0; i < m_idx; i++) {
			var obj = new Object;
			obj.id = $("input[id='id']").eq(i).attr("value");
			obj.code = $("input[id='code']").eq(i).attr("value");
			obj.name = $("input[id='name']").eq(i).attr("value");
			obj.m_x = $("input[id='m_x']").eq(i).attr("value");
			obj.m_y = $("input[id='m_y']").eq(i).attr("value");
			obj.address = $("input[id='m_address']").eq(i).attr("value");
			obj.road = $("input[id='m_road']").eq(i).attr("value");
			obj.url = $("input[id='m_url']").eq(i).attr("value");
			obj.number = $("input[id='m_number']").eq(i).attr("value");
			html = m_html(obj.name, obj.road, obj.code, obj.address, obj.number, obj.url); // m_html info
			obj.m_html = html;

			mapArray.push(obj);
		}

		var JsonArray = JSON.stringify(mapArray);
		var positions = $.parseJSON(JsonArray);
	    
	    /* ========================== list_click  ============================= */
	
	    m_pointClick(map, markerImage, iwRemoveable);
	
	    function m_pointClick(map, markerImage, iwRemoveable) {
	    	$(".point").bind('click', function(e) {
	    		$m_point = $(this);
	    		$m_point.addClass('active');
	            $(".point").not($m_point).removeClass('active'); // class remove

	    		var code = $m_point.children('ul').children('li:nth-child(2)').find('#code').val();
	    		var name = $m_point.children('ul').children('li:nth-child(2)').find('#name').val();
	    		var m_x = $m_point.children('ul').children('li:nth-child(2)').find('#m_x').val();
	    		var m_y = $m_point.children('ul').children('li:nth-child(2)').find('#m_y').val();
	    		var m_address = $m_point.children('ul').children('li:nth-child(2)').find('#m_address').val();
	    		var m_road = $m_point.children('ul').children('li:nth-child(2)').find('#m_road').val();
	    		var m_url = $m_point.children('ul').children('li:nth-child(2)').find('#m_url').val();
	    		var m_number = $m_point.children('ul').children('li:nth-child(2)').find('#m_number').val();
	    		html = m_html(name,m_road,code,m_address,m_number,m_url);
	            if (!selectedMarker || selectedMarker !== marker) {
	                var coords = new daum.maps.LatLng(m_x, m_y);
	                var marker = new daum.maps.Marker({
	            		map: map,
	            		position: coords,
	            		title: name,
	            		image : markerImage,
	            		removable : iwRemoveable
	            	});
	
	                var infowindow = new daum.maps.InfoWindow({
	                    content: html
	                });
	
	                map.setCenter(coords);
	                map.setLevel(2);
	                map.relayout();
	
	                !!selectedMarker && selectedMarker.close();
	        		infowindow.open(map, marker);
	            }
	
	            selectedMarker = infowindow;
	    	});
	    }
	    
	    /* ========================== map_click  ============================= */

	    for(var i = 0; i < positions.length; i++) {
	        addMarker(positions[i], imageSize, markerImage);
	    }
	
	    function addMarker(position,imageSize,markerImage,iwRemoveable) {
	        var coords = new daum.maps.LatLng(positions[i].m_x, positions[i].m_y);
	        var marker = new daum.maps.Marker({
	    		map: map,
	    		position: coords,
	    		title: positions[i].name,
	    		image : markerImage,
	    		removable : iwRemoveable
	    	});
	        
	        var infowindow = new daum.maps.InfoWindow({
	            content: positions[i].m_html
	        });
	
	        daum.maps.event.addListener(marker, 'click', function(e) {
				m_position(marker);
			/*  m_point();	*/
	        });

	        function m_position(marker) {
	        	var search_num = $('.point').length;
	    		var $m_click = $('.point');
	
	        	for (var i = 0; i < search_num; i++) {
	        		var search_name = $m_click.eq(i).children('ul').children('li:nth-child(2)').find('#name').val(); // 지점 이름
	        		
	        		// marker.Yd 와 search_name (지점 이름)이 동일해야 한다.
	            	if(marker.Yd == search_name) {
	            		$m_click.eq(i).addClass('active');
	            		$m_click.not(".point:eq(" + i + ")").removeClass('active'); // class remove
	            	}
	        	}
	        }

			function m_point() {
	            if (!selectedMarker || selectedMarker !== marker) {
	            	var moveLatLon = new daum.maps.LatLng(coords.Ga, coords.Fa);
	
	                map.setCenter(moveLatLon);
	                map.setLevel(2);
	                map.relayout();
	 
	                !!selectedMarker && selectedMarker.close(); // info Close
	        		infowindow.open(map, marker);
	            }
	
	            selectedMarker = infowindow;
			}
	    }
	
	    function m_html(name, road, code, address, number, url) {
			var html = "";
	
			html = "<div class='m_main'>";
				html += "<h4>" + name + "</h4>";
				html += "<p class='m_load'>" + road + "</p>";
				html += "<div class='close' onclick='closeOverlay()' title='닫기'></div>";
				html += "<div class='m_info'>";
					html += "<table>";
						html += "<tbody>";
							html += "<tr>";
								html += "<td>종류</td>";
								html += "<td>" + code + "</td>";
							html += "</tr>";
							html += "<tr>";
								html += "<td>주소</td>";
								html += "<td>" + address + "</td>";
							html += "</tr>";
							html += "<tr>";
								html += "<td>번호</td>";
								html += "<td>" + number + "</td>";
							html += "</tr>";
						html += "</tbody>";
					html += "</table>";
				html += "</div>";
				html += "<div class='m_link'>";
					html += "<a href='" + url + "' target='_blank' id='m_story' class='btn log'>지점 상세보기</a>";
				html += "</div>";
			html += "</div>";
			
			return html;
	    }
	});
});