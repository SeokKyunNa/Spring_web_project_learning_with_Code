<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>



<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- 구분자를 사용하기 위한 js, css 로드 -->
<link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/highlight.js/9.2.0/styles/default.min.css">
<script src="//cdnjs.cloudflare.com/ajax/libs/highlight.js/9.2.0/highlight.min.js"></script>



<script src="http://code.jquery.com/jquery-1.9.1.min.js" type="text/javascript" language="javascript"></script>
<script src="./js/qna/qna_edit.js"></script>

<script type="text/javascript">
	$(document).ready(function(){
		$("#qna_type_selectBox").val("${qnabean.qna_type}").prop("selected", true);
		//alert("${num}"+", "+"${state}"+", "+"${page}")
	});
</script>

<title>질문게시판에 질문하기 화면</title>
<link rel="stylesheet" type="text/css"  href="./css/qna/qna_footer.css">
<link rel="stylesheet" type="text/css"  href="./css/qna/qna_list.css">
<link rel="stylesheet" type="text/css"  href="./css/qna/qna_side.css">
<link rel="stylesheet" type="text/css"  href="./css/qna/qna_write.css">
</head>
<body>
<div class="qna_list_area">
	<!-- 사이드 메뉴 나오는 곳 -->
	<div class="qna_list_side">
		<div class="qna_list_side_picarea">
			<div class="qna_list_userpic">
				<c:if test="${prfbean.PROFILE_PIC != NULL}">
			<img src="./upload/prf/${prfbean.PROFILE_PIC  }"><br>
			</c:if>
				<c:if test="${prfbean.PROFILE_PIC == NULL}">
			<img src="./images/sns/noimg.png"><br>
			</c:if>
			</div>
			<div class="qna_list_userid">${id}</div>
		</div>
		<div class="qna_side_qnabutton" onclick="location.href='/SCM_Project/qna_write.qna'">질문하기</div>
		<div class="qna_side_qnalavel">
			<label>코딩질문</label>
		</div>
		<div class="qna_side_language_box">
			<div class="qna_side_language_button" onclick="location.href='/SCM_Project/qna_list.qna?state=all'">전체보기</div>
			<div class="qna_side_language_button" onclick="location.href='/SCM_Project/qna_list.qna?state=java'">java</div>
			<div class="qna_side_language_button" onclick="location.href='/SCM_Project/qna_list.qna?state='+escape('C++')">C++</div>
			<div class="qna_side_language_button" onclick="location.href='/SCM_Project/qna_list.qna?state=C'">C</div>
			<div class="qna_side_language_button" onclick="location.href='/SCM_Project/qna_list.qna?state=node.js'">node.js</div>
			<div class="qna_side_language_button" onclick="location.href='/SCM_Project/qna_list.qna?state=JavaScript'">JavaScript</div>
			<div class="qna_side_language_button" onclick="location.href='/SCM_Project/qna_list.qna?state=Spring'">Spring</div>
			<div class="qna_side_language_button" onclick="location.href='/SCM_Project/qna_list.qna?state=Oracle'">Oracle</div>
			<div class="qna_side_language_button" onclick="location.href='/SCM_Project/qna_list.qna?state=andsoon'">기타 언어</div>
		</div>
		<div class="qna_side_qnabutton" onclick="location.href='/SCM_Project/qna_list.qna?searchID=${id}'">내 글 보기</div>
		
	</div>
	<!-- 사이드 메뉴 나오는 곳 끝 -->
	
	<!-- qna 컨텐츠 나오는 부분 -->
	<div class="qna_list_content">
		<!-- 현제위치 알려주는 라벨 영역 -->
		<div class="qna_list_content_label_area">
			<div class="qna_list_content_label"><div>&ensp;&ensp;&ensp;&ensp;수정하기</div></div>
		</div>
		<!-- 현제위치 알려주는 라벨 영역 끝 -->
		
		<!-- 글쓰기 영역 -->
		<div class="qna_write_area">
		<center>
			<form name="qna_edit_form" method="post" action="qna_edit_ok.qna" enctype="multipart/form-data">
				<table class="qna_write_area_table">
					<tr>
						<td height="40px">제목</td>
						<td><input type="text" class="qna_write_subject_input" name="qna_subject" value="${qnabean.qna_subject}"/></td>
					</tr>
					<tr>
						<td height="40px">이미지 첨부</td>
						<td id="qna_write_file_td">
							<!-- <input type="file" name="qna_file" id="qna_file"class="multi"/> -->
							<div id="qna_file_input_area">
								<input type="file" name="qna_file1" id="qna_file1" class="qna_file"/>
							</div>
							<div id="qna_file_cancle">
							
							</div>
							<div class="qna_write_file_preview" style="width: 545px;">
								<!-- <img id="qna_file_view1" src="#"/> -->
								
							</div>
							<div class="qna_write_file_mask">
								
							</div>
						</td>
					</tr>
					<tr>
						<td height="40px">내용</td>
						<td rowspan="2" id="qna_write_body">
							<div id="qna_write_editer">
								<select id="changeFontName">
									<option value="돋음">돋음</option>
									<option value="굴림">굴림</option>
									<option value="궁서">궁서</option>
									<option value="나눈고딕">나눔고딕</option>
									<option value="맑은고딕">맑은고딕</option>
								</select>
								<select id="changeFontSize">
									<option value="1">8pt</option>
									<option value="2" selected>10pt</option>
									<option value="3">12pt</option>
									<option value="4">14pt</option>
									<option value="5">18pt</option>
									<option value="6">24pt</option>
									<option value="7">36pt</option>
								</select>
								<ul class="qna_writer_edit">
									<li><input type="button" value="가" class="qna_editer1"/></li>
									<li><input type="button" value="가" class="qna_editer2"/></li>
									<li><input type="button" value="가" class="qna_editer3"/></li>
									<li><input type="button" value="가" class="qna_editer4"/></li>
									<li class="select-color-button">
										
										<button class="qna_editer5">가<div id="qna_fontColor_button_div"></div></button>
										<!-- <input type="button" value="가" class="qna_editer5"/> -->
									</li>
								</ul>
								<div class="qna_select_color">
									<table style="border: 1px solid white">
											<tr>
												<td style="background-color:#FF0000"><input type="button" value="" class="color_box"></td>
												<td style="background-color:#FF5E00"><input type="button" value="" class="color_box"></td>
												<td style="background-color:#FFBB00"><input type="button" value="" class="color_box"></td>
												<td style="background-color:#FFE400"><input type="button" value="" class="color_box"></td>
												<td style="background-color:#ABF200"><input type="button" value="" class="color_box"></td>
												<td style="background-color:#1DDB16"><input type="button" value="" class="color_box"></td>
												<td style="background-color:#00D8FF"><input type="button" value="" class="color_box"></td>
												<td style="background-color:#0054FF"><input type="button" value="" class="color_box"></td>
												<td style="background-color:#0100FF"><input type="button" value="" class="color_box"></td>
												<td style="background-color:#5F00FF"><input type="button" value="" class="color_box"></td>
												<td style="background-color:#FF00DD"><input type="button" value="" class="color_box"></td>
												<td style="background-color:#FF007F"><input type="button" value="" class="color_box"></td>
												<td style="background-color:#000000"><input type="button" value="" class="color_box"></td>
												<td style="background-color:#FFFFFF"><input type="button" value="" class="color_box"></td>
											</tr>
											<tr>
												<td style="background-color:#FFA7A7"><input type="button" value="" class="color_box"></td>
												<td style="background-color:#FFC19E"><input type="button" value="" class="color_box"></td>
												<td style="background-color:#FFE08C"><input type="button" value="" class="color_box"></td>
												<td style="background-color:#FAED7D"><input type="button" value="" class="color_box"></td>
												<td style="background-color:#CEF279"><input type="button" value="" class="color_box"></td>
												<td style="background-color:#B7F0B1"><input type="button" value="" class="color_box"></td>
												<td style="background-color:#B2EBF4"><input type="button" value="" class="color_box"></td>
												<td style="background-color:#B2CCFF"><input type="button" value="" class="color_box"></td>
												<td style="background-color:#B5B2FF"><input type="button" value="" class="color_box"></td>
												<td style="background-color:#D1B2FF"><input type="button" value="" class="color_box"></td>
												<td style="background-color:#FFB2F5"><input type="button" value="" class="color_box"></td>
												<td style="background-color:#FFB2D9"><input type="button" value="" class="color_box"></td>
												<td style="background-color:#D5D5D5"><input type="button" value="" class="color_box"></td>
												<td style="background-color:#BDBDBD"><input type="button" value="" class="color_box"></td>
											</tr>
											<tr>
												<td style="background-color:#CC3D3D"><input type="button" value="" class="color_box"></td>
												<td style="background-color:#CC723D"><input type="button" value="" class="color_box"></td>
												<td style="background-color:#CCA63D"><input type="button" value="" class="color_box"></td>
												<td style="background-color:#C4B73B"><input type="button" value="" class="color_box"></td>
												<td style="background-color:#9FC93C"><input type="button" value="" class="color_box"></td>
												<td style="background-color:#47C83E"><input type="button" value="" class="color_box"></td>
												<td style="background-color:#3DB7CC"><input type="button" value="" class="color_box"></td>
												<td style="background-color:#4374D9"><input type="button" value="" class="color_box"></td>
												<td style="background-color:#4641D9"><input type="button" value="" class="color_box"></td>
												<td style="background-color:#8041D9"><input type="button" value="" class="color_box"></td>
												<td style="background-color:#D941C5"><input type="button" value="" class="color_box"></td>
												<td style="background-color:#D9418C"><input type="button" value="" class="color_box"></td>
												<td style="background-color:#747474"><input type="button" value="" class="color_box"></td>
												<td style="background-color:#5D5D5D"><input type="button" value="" class="color_box"></td>
											</tr>
											<tr>
												<td style="background-color:#670000"><input type="button" value="" class="color_box"></td>
												<td style="background-color:#662500"><input type="button" value="" class="color_box"></td>
												<td style="background-color:#664B00"><input type="button" value="" class="color_box"></td>
												<td style="background-color:#665C00"><input type="button" value="" class="color_box"></td>
												<td style="background-color:#476600"><input type="button" value="" class="color_box"></td>
												<td style="background-color:#22741C"><input type="button" value="" class="color_box"></td>
												<td style="background-color:#005766"><input type="button" value="" class="color_box"></td>
												<td style="background-color:#002266"><input type="button" value="" class="color_box"></td>
												<td style="background-color:#030066"><input type="button" value="" class="color_box"></td>
												<td style="background-color:#2A0066"><input type="button" value="" class="color_box"></td>
												<td style="background-color:#660058"><input type="button" value="" class="color_box"></td>
												<td style="background-color:#660033"><input type="button" value="" class="color_box"></td>
												<td style="background-color:#212121"><input type="button" value="" class="color_box"></td>
												<td style="background-color:#191919"><input type="button" value="" class="color_box"></td>
											</tr>
									</table>
								</div>
							</div>
							
							<div style="clear: both; height: 5px;"></div>
							<input type="hidden" name="qna_body" id="qna_body"/>
							<div contentEditable="true" id="qna_write_body_textarea">
								${qnabean.qna_body}
							</div>
						</td>
					</tr>
					<tr>
						<td></td>
					</tr>
					<tr>
						<td height="40px">사용언어</td>
						<td>
							<select name="qna_type" id="qna_type_selectBox">
								<option value="java">java</option>
								<option value="C++">C++</option>
								<option value="C">C</option>
								<option value="node.js">node.js</option>
								<option value="JavaScript">JavaScript</option>
								<option value="Spring">Spring</option>
								<option value="Oracle">Oracle</option>
								<option value="andsoon">기타언어</option>
							</select>
						</td>
					</tr>
					<tr>
						<td height="40px">
							소스코드<br>
							<input type="button" value="미리보기" class="qna_write_view"/></td>
						<td rowspan="2" id="qna_code_area">
							<textarea rows="20" cols="75" name="qna_code" id="qna_code_text">${qnabean.qna_code}</textarea>
						</td>
					</tr>
					<tr>
						<td></td>
					</tr>
					<tr>
						<td>
							<input type="hidden" name="num" value="${num}">
							<input type="hidden" name="state" value="${state}">
							<input type="hidden" name="page" value="${page}">
						</td>
						<td><input type="submit" value="수정하기" id="qna_write_submit"/><input type="button" value="취소" id="qna_edit_cancle"/></td>
					</tr>
				</table>
			</form>
		</center>
		</div>
		<!-- 글쓰기 영역 끝-->
		
	</div>
	<!-- qna 컨텐츠 나오는 부분 끝 -->
	
	<!-- Footer 영역 -->
	<div style="height: 0px; clear: both"></div>
	<div class="qna_list_footer">
		Copyright ⓒ SCM_Project.All right reserved.
	</div>
	<!-- Footer 영역 -->
	
</div>

</body>
</html>