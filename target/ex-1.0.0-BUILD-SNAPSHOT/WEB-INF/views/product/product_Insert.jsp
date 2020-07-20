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
				<h2>테이블 등록</h2>
				<hr />

				<div class="insert">
					<hr class="slide_hr" />
					<h2 class="slide_tit">도매 / 소매 선택</h2>

					<select id="sale_mode" name="sale_mode" class="form-control">
						<option value="">판매 형태를 선택해주세요</option>
						<option value="SLEEVE">소매</option>
						<option value="WHOLE">도매</option>
					</select>
				</div>

				<div class="insert">
					<hr class="slide_hr" />
					<h2 class="slide_tit">슬라이드 이미지 등록</h2>
	
					<div id="file_div">
						<p class="cnt_0">
							<span class="tit">0</span>
							<input type="file" id="file_0" name="file_0" class="file" value="" />
							<span id="file_txt" class="naming">선택된 파일 없음</span>
							<span name="imgs_0">
								<img src="../resources/image/icon/preview.png" />
							</span>
							<button id="delete" name="delete" class="btn log">삭제</button>
						</p>
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
										<textarea class="product_Info" name="product_Info" id="product_Info"></textarea>
									</span>
								</div>
							</div>
						</li>
						<li>
							<div id="table_sub">
								<div class="input-wrap">
									<span id="list">
										<strong>제품 리스트</strong>
										<ul name="goods_0" id="goods">
											<li><span>0</span></li>
											<li><input type="text" id="toName" class="form-control" placeholder="제품 명" /></li>
											<li><input type="text" id="toOption" class="form-control" placeholder="종류" /></li>
											<li><input type="text" id="toPrice" class="form-control" placeholder="기격" /></li>
											<li><input type="text" id="toSale" class="form-control" placeholder="할인률" /></li>
											<li><button name="product_Del" class='btn log'>삭제</button></li>
										</ul>
									</span>

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
							<input type="text" name="product_Title" id="product_Title" />
						</span>
					</div>

					<div class="input-wrap">
						<span>
							<strong>내용</strong>
							<div>
								<textarea class="product_Content" name="product_Content" id="product_Content"></textarea>
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
								<textarea class="product_Shipping" name="product_Shipping" id="product_Shipping"></textarea>
							</div>
						</span>
					</div>

					<div class="input-wrap">
						<span>
							<strong>반품안내</strong>
							<div>
								<textarea class="product_Return" name="product_Return" id="product_Return"></textarea>
							</div>
						</span>
					</div>
	
					<div class="send-wrap" >
						<ul>
							<li>
								<button id="sendTable" name="sendTable" class="btn log">등록</button>
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

				<script type="text/javascript" src="${pageContext.request.contextPath}/js/product/fn_ProductInfo.js"></script>
			</div>
		</div>
	</div>
</li>

</s:authorize>

<jsp:include page="../security/ad_footer.jsp"></jsp:include>