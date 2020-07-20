<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<jsp:include page="./header.jsp" />

<div class="main business">
	<div class="map_main">
		<div id="bodyBox">
			<div id="infoBox">
				<table id="routeTable" class="routeTable" border="1">

				</table>
				<span class="close">
					<img src="../resources/image/icon/close.png" alt="" />
				</span>
			</div>
		</div>

		<div id="map"></div>

		<div id="m_list">
			<div class="list">
				<div class="bus" id="p_chk">
					<ul>
						<li id="searchMenu">
							<input type="hidden" id="lat" value="35.93154" readOnly />
							<input type="hidden" id="lng" value="126.94399" readOnly />

							<div class="searchInsert">
								<input type="text" name="search" id="search" />
							</div>

               				<a id="sc_btn" class="btn" >찾기</a>
						</li>
						<li>
							<div id="resultBox" class="leftBox">

							</div>
						</li>
					</ul>
				</div>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=8e17e941ff8ec2fc7c002f7273b50ae2&libraries=services,clusterer,drawing"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/busInfo.js" ></script>

<jsp:include page="./footer.jsp" />