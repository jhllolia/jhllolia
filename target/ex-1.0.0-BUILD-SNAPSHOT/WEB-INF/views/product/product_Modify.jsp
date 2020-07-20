<%@ page import="org.springframework.security.core.Authentication" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="s" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<s:authorize ifNotGranted="ROLE_ADMIN">
	<p>로그아웃</p>
</s:authorize>

<s:authorize ifAnyGranted="ROLE_ADMIN">

<jsp:include page="../security/admin.jsp" />

<li id="menu_result">
	<div class="adMain">
		<div class="seat_container">
			<div class="seat">
				<h2>테이블 수정</h2>
				<hr />

				<c:choose>
					<c:when test="${seat.PRODUCT_STATE eq 'D' }">
						<div class="nullTable">
							<h2>해당 테이블이 존재하질 않습니다.</h2>
							<button onClick="history.back(-1); return false;">이전으로</button>
						</div>
					</c:when>
					<c:otherwise>
						<div class="insert">
							<hr class="slide_hr" />
							<h2 class="slide_tit">도매 / 소매 선택</h2>

							<select id="sale_mode" name="sale_mode" class="form-control">
								<option value="${seat.PRODUCT_SALE_MODE}" style="font-weight: 600;">
									<c:if test="${seat.PRODUCT_SALE_MODE eq 'SLEEVE'}">소매</c:if>
									<c:if test="${seat.PRODUCT_SALE_MODE eq 'WHOLE'}">도매</c:if>
								</option>
								<option value="SLEEVE">소매</option>
								<option value="WHOLE">도매</option>
							</select>
						</div>

						<div class="insert">
							<hr class="slide_hr" />
							<h2 class="slide_tit">슬라이드 이미지 등록</h2>

							<div id="file_div" class="sortable">
								<c:choose>
									<c:when test="${fn:length(file) == 0}">
										<p class="cnt_0">
											<span class="tit">0</span>
											<input type="file" id="file_0" name="file_0" class="file" value="" />
											<span id="file_txt" class="naming">선택된 파일 없음</span>
											<span name="imgs_0">
												<img src="../resources/image/icon/photo.png" />
											</span>
											<button id="delete" name="delete" class="btn log">삭제</button>
										</p>
									</c:when>
									<c:otherwise>
										<c:forEach items="${file}" var="dto" varStatus="status">
											<c:if test="${fn:contains(dto.PRODUCT_FILE_TITLE, 'file_')}">
												<p class="cnt_${status.index}" seq="${dto.ORD}" file_idx="${dto.FILE_SEQ}">
													<span class="tit">${status.index}</span>
													<input type="file" id="file_${status.index}" name="${dto.PRODUCT_FILE_TITLE}" class="file" value="${dto.PRODUCT_FILE_NAME}" />
													<span id="file_txt" class="naming">선택된 파일 없음</span>
													<span name="imgs_${status.index}">
														<img src="../resources/upload/table/${dto.PRODUCT_SEQ}/${dto.PRODUCT_FILE_NAME}" />
													</span>
													<button id="delete" name="delete" class="btn log">삭제</button>
												</p>
											</c:if>
										</c:forEach>
									</c:otherwise>
								</c:choose>
							</div>

							<div class="action">
								<div id="file_action">
									<button id="addFile" class="btn log">파일 추가</button>
								</div>
							</div>
						</div>

						<div class="insert">
							<hr class="slide_hr" />
							<h2 class="slide_tit">제품 상세 내역_1</h2>

							<ul class="infoArea">
								<li>
									<div id="table_sub">
										<div class="input-wrap">
											<span>
												<strong>제품 정보</strong>
												<textarea class="product_Info" name="product_Info" id="product_Info">${seat.PRODUCT_INFO}</textarea>
											</span>
										</div>
									</div>
								</li>
								<li>
									<div id="table_sub">
										<div class="input-wrap">
											<span id="list">
												<strong>제품 리스트</strong>
											</span>
											
											<script>
												jQuery.noConflict();
													
												jQuery(function($) {
													$(document).ready(function() {
														var html = "";
														var json = JSON.parse('${seat.PRODUCT_SUB_INFO}');
	
														for (var i = 0; i < json.length; i++) {
															html += "<ul name='goods_" + i + "' id='goods'>";
															html += "	<li><span>" + i + "</span></li>";
															html += "	<li><input type='text' id='toName' class='form-control' value='" + json[i].name + "' /></li>";
															html += "	<li><input type='text' id='toOption' class='form-control' value='" + json[i].option + "' /></li>";
															html += "	<li><input type='text' id='toPrice' class='form-control' value='" + json[i].price + "' /></li>";
															html += "	<li><input type='text' id='toSale' class='form-control' value='" + json[i].sale + "' /></li>";
															html += "	<li><button name='product_Del' class='btn log'>삭제</button></li>";
															html += "</ul>";
														}
	
														$('span[id="list"]').append(html);
													});
												});
											</script>

											<div class="addList">
												<input type="button" id="addProduct" class="btn log" value="제품 추가" />
											</div>
										</div>
									</div>
								</li>
							</ul>
						</div>

						<div class="insert">
							<hr class="slide_hr" />
							<h2 class="slide_tit">제품 상세 내역_2</h2>

							<div class="input-wrap">
								<span>
									<strong>제품 명</strong>
									<input type="text" name="product_Title" id="product_Title" value="${seat.PRODUCT_TITLE}" />
								</span>
							</div>

							<div class="input-wrap">
								<span>
									<strong>내용</strong>
									<div>
										<textarea class="product_Content" name="product_Content" id="product_Content">${seat.PRODUCT_CONTENT}</textarea>
									</div>
								</span>
							</div>
						</div>

						<div class="insert">
							<hr class="slide_hr" />
							<h2 class="slide_tit">제품 상세 내역_3</h2>

							<div class="input-wrap">
								<span>
									<strong>배송안내</strong>
									<div>
										<textarea class="product_Shipping" name="product_Shipping" id="product_Shipping">${seat.PRODUCT_SHIPPING_TXT}</textarea>
									</div>
								</span>
							</div>

							<div class="input-wrap">
								<span>
									<strong>교환안내</strong>
									<div>
										<textarea class="product_Return" name="product_Return" id="product_Return">${seat.PRODUCT_RETURN_TXT}</textarea>
									</div>
								</span>
							</div>

							<div class="send-wrap" >
								<input type="hidden" id="seq" value="${seat.PRODUCT_SEQ}" />

								<ul>
									<li>
										<button id="modifyTable" name="modifyTable" class="btn log">등록</button>
									</li>
									<li>
										<button onClick="history.back(-1); return false;" class="btn log">이전으로</button>
									</li>
								</ul>
							</div>
						</div>

						<script type="text/javascript" src="//cdn.ckeditor.com/4.8.0/full-all/ckeditor.js"></script>
						<script type="text/javascript">
		
							window.onload = function() {
								getCkedit();
							}

							function getCkedit() {
								var editorConfig = {
										filebrowserUploadUrl	:	"/bbs/fileUpload", 
										extraPlugins			:	'youtube'
								};

								CKEDITOR.plugins.addExternal('youtube', '/js/youtube/', 'plugin.js');	// add Youtube
								CKEDITOR.replace('product_Content', editorConfig);
								CKEDITOR.replace('product_Shipping', editorConfig);
								CKEDITOR.replace('product_Return', editorConfig);
								CKEDITOR.replace('product_Info', {
									allowedContent:true,
									toolbar :[['NewPage','Preview','Bold','Italic','Underline','Strike','-','JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock'],['Styles','Format','Font','FontSize']]
								});

								CKEDITOR.config.fontSize_defaultLabel = '12px';
								CKEDITOR.config.fontSize_sizes='8/8px;9/9px;10/10px;11/11px;12/12px;14/14px;16/16px;18/18px;20/20px;22/22px;24/24px;26/26px;28/28px;36/36px;48/48px;';
								CKEDITOR.config.enterMode = CKEDITOR.ENTER_BR;							//엔터키 입력시 br 태그 변경
								CKEDITOR.config.uiColor = '#EEEEEE';									// Height
								CKEDITOR.config.height = 200;											// Height
								CKEDITOR.config.toolbarCanCollapse = true;
								CKEDITOR.config.youtube_width = 200;									// Youtube Width
								CKEDITOR.config.youtube_height = 100;									// Youtube Height
								CKEDITOR.config.youtube_responsive = true;
								CKEDITOR.config.youtube_related = true;
								CKEDITOR.config.youtube_controls = true;
							}

						</script>

						<script type="text/javascript" src="https://code.jquery.com/ui/1.12.1/jquery-ui.js" ></script>
						<script type="text/javascript" src="${pageContext.request.contextPath}/js/product/fn_ProductInfo.js"></script>
						<script type="text/javascript" src="${pageContext.request.contextPath}/js/product/fn_Sortable.js"></script>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</div>
</li>

</s:authorize>

<jsp:include page="../footer.jsp"></jsp:include>