jQuery.noConflict();

jQuery(function($) {
	$(document).ready(function() {

		/* ============================================ */

		var markers = [];

		var customOverlay;
		var polyline;
		var cur_Page;
		var total_Page = 0;

		var mapContainer = document.getElementById('map');
		mapContainer.style.width = '90%';

		var mapOption = {
			center: new daum.maps.LatLng(35.93221, 126.94403),
			level: 3
		};

		var map = new daum.maps.Map(mapContainer, mapOption);	// 지도를 생성합니다

		var geocoder = new kakao.maps.services.Geocoder();		// 주소-좌표 변환 객체

		/* ============================================ */

		fn_clickSetting();
    	fn_BusListClick();

    	$('#search').keydown( function(e) {
    		var searchTxt = $(this).val();

    		/*

			$.ajax({
				url: "/stationName",
				dataType: "json",
				type: "POST",
				contentType : "application/json",
				data: JSON.stringify({ NODENAME : searchTxt }),
			    success: function(result) {

			    }, error: function(error) {
			    	alert(error);
			    	return false;
			    }
			});

    		*/
    	});

		$("#sc_btn").on("click",function(e) {
			e.preventDefault();
			fn_clickSearchButton();
		});

		function fn_clickSetting() {
		    var clickMarker = null;
		    var clickIcon = new daum.maps.MarkerImage("http://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png", new daum.maps.Size(24, 35));

		    daum.maps.event.addListener(map, 'click', function(mouseEvent) {
				setMarkers(null);

		    	if(clickMarker != null) {
		    		clickMarker.setMap(null);
		    	}

		    	var latlng = mouseEvent.latLng;

		    	var resultDiv1 = document.getElementById('lat');
		        resultDiv1.value = Math.floor(latlng.getLat() * 100000)/100000;
		        var resultDiv2 = document.getElementById('lng');
		        resultDiv2.value = Math.floor(latlng.getLng() * 100000)/100000;

		        clickMarker = new daum.maps.Marker({
		        	position: new daum.maps.LatLng(resultDiv1.value,resultDiv2.value),
		        	image: clickIcon,
		        	clickable: false
		        });

		        clickMarker.setMap(map);

		        fn_clickSearchButton(resultDiv1.value, resultDiv2.value);
		    });
		}

		/* ============================================ */

		function fn_clickSearchButton(lat,lng) {
			setMarkers(null);
			$('#infoBox').stop().animate({ 'left' : '-80%' }, 300);	
			$("#resultBox").children().remove();

			var data = {};

			if( !lat ) {
				data["LAT"] = $('#lat').val();
				data["LNG"] = $('#lng').val();
			} else {			
				data["LAT"] = lat;
				data["LNG"] = lng;
			}

			map.setCenter(new daum.maps.LatLng(data["LAT"], data["LNG"]));

			$.ajax({
				url: "/busList",
				dataType:"json",
				type: "POST",
				contentType : "application/json",
				data: JSON.stringify(data),
			    success: function(result) {
			    	$.each(result, function (index, value) {
			    		var str = "<a href='#this' nodeid='" + value["NODEID"] + "' lat='" + value["LAT"] + "' lng='" + value["LNG"] + "' name='node" + index + "' id='node" 
			    		+ index + "' class='result sc_node_result'>" + value["NODENAME"] + "</a>";
			    		$("#resultBox").append(str);

			    		fn_nodeMarkerMaker($("a[name='node" + index + "']"));
			    	});

			    	fn_BusListClick();
			    }, error: function(error) {
					console.log(error);
			    	return false;
			    }
			});
		}

		/* ======= 배열에 추가된 마커들을 지도에 표시하거나 삭제하는 함수입니다. ====== */
		function setMarkers(map) {
			for(var i = 0; i < markers.length; i++) {
				markers[i].setMap(map);
			}
		}

		function fn_BusListClick() {
		    $("a[name^='node']").on("click",function(e) {
		    	e.preventDefault();
		    	fn_clickNode($(this));
		    });
		}

		function fn_clickNode(obj) {
			setMarkers(null);

			if($("a[name^='node']").hasClass("selected")) {
				$("a[name^='node']").removeClass("selected");
				fn_clickSearchButton();
			} else {
				$("a[name^='node']").addClass("selected");
				fn_nodeRealTime(obj);
			}
		}

		/* ================================================================ */
		function fn_nodeRealTime(obj) {
			if($("#nodeidVal").val() != null && !($("#nodeidVal").hasClass("used"))) {
				var str = "<a href='#this' nodeid='" + $("#nodeidVal").val() + "' lat='" + $("#latVal").val() + "' lng='" + $("#lngVal").val() + 
				"' name='node1' id='node1' class='result sc_node_result'>" + $("#nodenameVal").val() + "</a>";

				$("#resultBox").append(str);

			    obj = $("#node1");
			    $(obj).addClass("selected");
			    $("#nodeidVal").addClass("used");
			}

			map.setCenter(new daum.maps.LatLng(obj.attr("lat"),obj.attr("lng")));

			var divStr = "<div id='routeBox' class='result'><div id='pageDiv'></div></div>";

			obj.siblings().remove(); 
			obj.parent().append(divStr);

			fn_nodeMarkerMaker(obj);

			$.ajax({
				url: "/busRealTime",
				dataType:"json",
				type: "POST",
				contentType : "application/json",
				data: JSON.stringify({ NODEID : obj.attr("nodeid") }),
			    success: function(result) {
			    	if(result != "") {
				    	$.each(result, function (index, value) {
				    		var arrtime = Math.floor( value["ARRTIME"] / 60 );
				    		var str = "<a href='#this' routeno='" + value["ROUTENO"] + "' routeid='" + value["ROUTEID"] + "' name='route" + index + "' id='route" + index + "' class='result sc_real_route_result'>" + value["ROUTENO"] 
				    		+ " ( " + value["ROUTETP"] + " )<br /><span>" + value["ARRPREV"] + "정류장 전( " + arrtime + "분 )</span></a>";

				    		$("#routeBox").append(str);
				    	});
			    	} else {
			    		$("#routeBox").append("<p class='no_result'>운행중인 버스가 없습니다.</a>");
			    	}

			    	$("#routeBox").append("<a href='#this' id='busList_Btn' class='btn' >모든 버스 노선 보기</a>");

			    	$("a[name^=route]").on("click",function(e) {
			    		e.preventDefault();
			            fn_routeInfo($(this));
			    	});

			    	$("#busList_Btn").on("click",function(e) {
			    		e.preventDefault();
			    		fn_nodeToRoute(obj);
			    	});

			    	fn_clickPath(obj);
			    }, error: function(error) {
					console.log(error);
			    	return false;
			    }
			});
		}

		/* =========================================== */

		function fn_nodeMarkerMaker(obj) {
			var markerPosition  = new daum.maps.LatLng(obj.attr("lat"), obj.attr("lng"));

			var marker = new daum.maps.Marker({
			    position: markerPosition,
			    clickable: true
			});

			marker.setMap(map);
			markers.push(marker);

			daum.maps.event.addListener(marker, "click", function(e) {
				if(obj.attr("id").slice(0,4) == "node") {
					fn_clickNode(obj);
				} else if(obj.attr("id").slice(0,4) == "path") {	
					fn_clickPath(obj);
				}
			});
		}

		/* ========================================== */

		function fn_nodeToRoute(obj) {
			$("#routeBox").children().remove();
			fn_NodeClear();

			var data = {};
            data["NODEID"] = obj.attr("nodeid");

			if(typeof cur_Page == "undefined" || cur_Page == null || cur_Page == "") {
				data["PAGE_NO"] = 1;
			} else {
				data["PAGE_NO"] = cur_Page;
			}

			$.ajax({
				url: "/nodeToRoute",
				dataType: "json",
				type: "POST",
				contentType: "application/json",
				data: JSON.stringify(data),
			    success: function(result) {
					setMarkers(null);

			    	if(result != "") {
				    	$.each(result, function (index, value) {
				    		var str = "<a href='#this' routeno='" + value["ROUTENO"] + "' routeid='" + value["ROUTEID"] + "' name='route" + index + "' id='route" + index
				    		+ "' class='result sc_route_result'>" + value["ROUTENO"] + "<span class='routeSpan'>(" + value["STARTNODENM"] + "~" + value["ENDNODENM"] + ")</span></a>";
				    		$("#routeBox").append(str);
				    	});
				    	
						if(typeof result[0]["TOTAL_COUNT"] == "undefined" || result[0]["TOTAL_COUNT"] == null || result[0]["TOTAL_COUNT"] == "") {
							total_Page = 0;
				    	} else {
				    		total_Page = (result[0]["TOTAL_COUNT"] / 10);
				    	}

				    	if(cur_Page > 1) {
				    		$("#routeBox").append("<a href='#this' id='route_Pre' class='btn routeBtn'>이전</a>");
				    	}

	                    if(cur_Page < total_Page) {
	                    	$("#routeBox").append("<a href='#this' id='route_Next' class='btn routeBtn'>다음</a>");
	                    }
			    	} else {
			    		$("#routeBox").append("<p class='no_result'>운행중인 버스가 없습니다.</a>");
			    	}

                    $("#routeBox").append("<a href='#this' id='busList_Btn' class='btn' >실시간 노선 보기</a>");

                    $("#route_Pre").on("click",function(e) {
                        e.preventDefault();
                        cur_Page--;
                        fn_nodeToRoute(obj);
                    });

                    $("#route_Next").on("click",function(e) {
                    	e.preventDefault();
                        cur_Page++;
                        fn_nodeToRoute(obj);
                    });
                     
                    $("#busList_Btn").on("click",function(e) {
                    	e.preventDefault();
                    	fn_nodeRealTime(obj);
                    });

                    $("a[name^=route]").on("click",function(e) {
                    	e.preventDefault();
                    	fn_routeInfo($(this));
                    });
			    }, error: function(error) {
					console.log(error);
			    	return false;
			    }
			});
		}
		
		/* ============================================ */

		function fn_routeInfo(obj) {
			fn_NodeClear();

	    	linePath = [];

			var data = {};
            data["routeno"] = obj.attr("routeno");
			data["routeid"] = obj.attr("routeid");

			$.ajax({
				url: "/routeInfoPage",
				dataType: "json",
				type: "POST",
				contentType: "application/json",
				data: JSON.stringify(data),
			    success: function(result) {
			    	var str = "";
			    	var info = result["info"];
			    	var path = result["path"];

			    	if(info == null) {
			    		alert('해당 데이터가 존재하질  않습니다.');
			    		return false;
			    	} else {
				    	if(typeof info["STARTVEHICLETIME"] == "undefined" || info["STARTVEHICLETIME"] == null || info["STARTVEHICLETIME"] == "") {
				    		info["STARTVEHICLETIME"] = "xxxx";
				    		info["ENDVEHICLETIME"] = "xxxx";
	                    }

				    	$('#infoBox').stop().animate({ 'left' : '0%' }, 300);
				    	$("#routeTable").before("<p>노선 정보</p>");

				    	str += "<tbody><tr><th>노선번호</th><td colspan='3'>" + info["ROUTENO"] +"</td></tr><tr><th>기점</th><td class='nodename'>" + info["STARTNODENM"] + "</td>";
				    	str += "<th>첫차시간</th><td class='vehicletime'>" + info["STARTVEHICLETIME"][0] + info["STARTVEHICLETIME"][1] + ":" + info["STARTVEHICLETIME"][2] + info["STARTVEHICLETIME"][3] + "";
				    	str += "</td></tr><tr><th>종점</th><td class='nodename'>" + info["ENDNODENM"] + "</td><th>막차시간</th><td class='vehicletime'>";
				    	str += info["ENDVEHICLETIME"][0] + info["ENDVEHICLETIME"][1] + ":" + info["ENDVEHICLETIME"][2] + info["ENDVEHICLETIME"][3] + "</td></tr></tbody>";

	                    $("#routeTable").append(str);
	                    $("#routeTable").after("<hr/><p>노선 경로</p><span>(정류장 이름을 클릭하면 해당 정류소 위치로 이동)</span><ul id='routePath'></ul>");

	            		/* ========================================== */

	                    for( var i = 0; i < path.length; i++ ) {
	                    	var str = "";

	                    	str += "<li class='direction_li'><span class='topline_span'></span><img src='http://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png' alt='' />";
	                        str += "<div id='path" + i + "' nodeid=" + path[i]["NODEID"] + " name='nodeinfo" + i + "' lat='" + path[i]["LAT"] + "' lng='" + path[i]["LNG"];
	                        str += "' class='direction_nodename'>" + path[i]["NODENAME"] + "</div></li>";

	                        $("#routePath").append(str);

	                        fn_nodeMarkerMaker($("#path" + i));	//	정류소들을 전부 마커에 표기 해줍니다. 

	                        linePath.push(new daum.maps.LatLng($("#path" + i).attr("lat"), $("#path" + i).attr("lng")));

	                        $("li[class^=direction]").on("click",function(e) {
	                        	e.preventDefault();
	                        	fn_clickPath($(this).children("div[name^=nodeinfo]"));		// 정류장 클릭
	                        });
	                    }

	            		fn_liveRoute();		// 실시간 정류장 검색
	                    fn_nodeLineMaker();	// 마커 표시
	                    fn_pathClose();		// 버튼 선택 후 Close
			    	}
			    }, error: function(error) {
					console.log(error);
			    	return false;
			    }
			});
		}

		/* ============================================ */

		function fn_liveRoute() {
    		$('.direction_li').click( function(e) {
    			if($("div[name^=nodeinfo]").hasClass('markerSelected') == true) {
			    	var obj = new Object();
    				var stationName = $(this).find('div').html();
        			var nodeid = $(this).find('div').attr('nodeid');
        			var name = $(this).find('div').attr('name');
        			var lat = $(this).find('div').attr('lat');
        			var lng = $(this).find('div').attr('lng');

    		    	$.ajax({
    		    		url: "/busRealTime",
    		    		dataType:"json",
    		    		type: "POST",
    		    		contentType : "application/json",
    		    		data: JSON.stringify({ NODEID : nodeid }),
    		    	    success: function(result) {
    		    	    	$('#resultBox').find('a.result').attr('nodeid', nodeid)
    		    	    									.attr('name', name)
    		    	    									.attr('lat', lat)
    		    	    									.attr('lng', lng)
    		    	    									.html(stationName);

    		    	    	$('#routeBox').empty();

    				    	if(result != "") {
    					    	$.each(result, function (index, value) {
    					    		var arrtime = Math.floor( value["ARRTIME"] / 60 );
    					    		var str = "<a href='#this' routeno='" + value["ROUTENO"] + "' routeid='" + value["ROUTEID"] + "' name='route" + index + "' id='route" + index + "' class='result sc_real_route_result'>" + value["ROUTENO"] 
    					    		+ " ( " + value["ROUTETP"] + " )<br /><span>" + value["ARRPREV"] + "정류장 전( " + arrtime + "분 )</span></a>";

    					    		$("#routeBox").append(str);
    					    	});
    				    	} else {
    				    		$("#routeBox").append("<p class='no_result'>운행중인 버스가 없습니다.</a>");
    				    	}

    				    	$("#routeBox").append("<a href='#this' id='busList_Btn' class='btn' >모든 버스 노선 보기</a>");

    				    	obj.name = stationName;
    				    	obj.lat = lat;
    				    	obj.lng = lng;

    				    	$("a[name^=route]").on("click",function(e) {
    				    		e.preventDefault();
        						fn_setOverlay(obj);
    				            fn_routeInfo($(this));
    				    	});

    				    	$("#busList_Btn").on("click",function(e) {
    				    		e.preventDefault();
    				    		fn_setOverlay(obj);
    				    		fn_nodeToRoute($('#resultBox').find('a.result').attr('nodeid', nodeid));
    				    	});

    						fn_setOverlay(obj);
    		    	    }, error: function(error) {
							console.log(error);
    		    	    	return false;
    		    	    }
    		    	});
    			} else {
    				alert('알수없는 명령입니다.');
    				return false;
    			}
    		});
		}

		/* ============================================ */

		function fn_pathClose() {
			$('.close').click(function () {
				fn_NodeClear();
			});
		}

		function fn_NodeClear() {
			setMarkers(null);

			var $info = $('#infoBox');

			$info.stop().animate({ 'left' : '-80%' }, 300);
			$info.find('p').remove();
			$info.find('hr').remove();
			$info.find('span').not('.close').remove();

	    	$('#routeTable').empty();
	    	$('#routePath').empty();
		}

		/* ============================================ */
		function fn_clickPath(obj) {
			$("div[name^=nodeinfo]").removeClass("markerSelected");
			obj.addClass("markerSelected");

			map.setCenter(new daum.maps.LatLng(obj.attr("lat"), obj.attr("lng")));
		}

		/* ============================================ */
		function fn_setOverlay(obj) {
			if(customOverlay != null) {
				customOverlay.setMap(null);
		    }

			geocoder.coord2Address(obj.lng, obj.lat, function(result, status) {
				var content = '<div id="setOverlay" class="setOverlay"><h4>' + obj.name   + '</h4><p class="customOverlay">' + result[0].address.address_name + '</p></div>';

				customOverlay = new daum.maps.CustomOverlay({
					map: map,
					clickable: false,
					content: content,
					position: new daum.maps.LatLng( obj.lat, obj.lng ),
					xAnchor: 0.5,
			        yAnchor: 1.5,
			        zIndex: 3
			    });

				customOverlay.setMap(map);
			});
		}

		/* ============================================ */
		function fn_nodeLineMaker() {
			polyline = new daum.maps.Polyline({
				path: linePath, 				// 선을 구성하는 좌표배열 입니다
				strokeWeight: 3, 				// 선의 두께 입니다
				strokeColor: '#FF0000', 		// 선의 색깔입니다
				strokeOpacity: 0.7,				// 선의 불투명도 입니다 1에서 0 사이의 값이며 0에 가까울수록 투명합니다
				strokeStyle: 'solid' 			// 선의 스타일입니다
			});

			polyline.setMap(map);				// 지도에 선을 표시합니다 
			map.setCenter(linePath[0]);			// 지도의 중심 좌표를 정류장 출발점의 중심으로 위치 변경합니다.
			markers.push(polyline);				// 마커들의 배열에 폴리라인을 같이 넣어 관리합니다.
		}

		/* ========================================== */
        
		
	});
});