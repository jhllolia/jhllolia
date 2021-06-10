<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<jsp:include page="./header.jsp" />

<div class="main business">
	<div class="map_main">
		<div id="map"></div>
		<div id="m_list">
			<div class="list">
				<input type="hidden" name="listCnt" id="listCnt" value="${cnt}" />
				<div class="tit">
					<h4>어양토속식품 제휴식당</h4>
				</div>

				<c:forEach items="${list}" var="dto">
					<div class="point" id="p_chk">
						<ul>
							<li>
								<p class="chk">추천</p>
								<img src="https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png" alt="img" />
							</li>
							<li>
								<input type="hidden" name="m_point" id="seq" value="${dto.m_Seq}" />
								<input type="hidden" name="m_point" id="id" value="${dto.m_Id}" />
								<input type="hidden" name="m_point" id="code" value="${dto.m_cat_Name}" />
								<input type="hidden" name="m_point" id="name" value="${dto.m_place_Name}" />
								<input type="hidden" name="m_point" id="m_x" value="${dto.m_Y}" />
								<input type="hidden" name="m_point" id="m_y" value="${dto.m_X}" />
								<input type="hidden" name="m_point" id="m_address" value="${dto.m_place_Address}" />
								<input type="hidden" name="m_point" id="m_road" value="${dto.m_place_Road}" />
								<input type="hidden" name="m_point" id="m_url" value="${dto.m_place_Url}" />
								<input type="hidden" name="m_point" id="m_number" value="${dto.m_place_Number}" />
								<p class="p_cat">${dto.m_cat_Name}</p>
								<h4>${dto.m_place_Name}</h4>
								<dd>${dto.m_place_Address}</dd>
							</li>
						</ul>
					</div>
				</c:forEach>
			</div>
		</div>
	
		<script type="text/javascript" src="https://ssl.daumcdn.net/dmaps/map_js_init/roughmapLoader.js"></script>
		<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=c302e22b0d93d89b5c0144391757e420&libraries=services,clusterer,drawing"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/admin/map.js" ></script>

		<p id="result"></p>
	</div>
</div>

<jsp:include page="./footer.jsp" />