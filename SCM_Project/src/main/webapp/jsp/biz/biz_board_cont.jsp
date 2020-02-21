<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="model.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
<link rel="stylesheet" type="text/css"  href="./css/biz/biz_board_cont.css">
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script src="./js/biz/biz_board_cont.js"></script>
	<title>채용공고 내용보기</title>
</head>

<body>
<!-- 게시판  -->
<input type="hidden" id="boardForm" value="${boardForm }">
<input type="hidden" id="id" value="${id}">
<div id="titlearea" align="center">
	<c:if test="${result!=1 }">
		<p id="title">&nbsp;&nbsp;${bizbean.biz_name} 채용 공고입니다.<p>
	</c:if>
	<c:if test="${result==1 }">
		<script>
			alert('이미 마감된 채용 공고입니다.')
		</script>		
		<p id="title">이미 마감된 채용 공고입니다.<p>
	</c:if>
</div>
<div id="myform" style="margin: auto;">
	<label><span>제목</span></label> 
		<input type="text" name="biz_subject" id="biz_subject" class="inputbox" value="${bizbean.biz_subject }" readonly />

	<label><span>회사명</span></label>
		<input type="text" name="biz_name" id="biz_name" class="inputbox" value="${bizbean.biz_name }" readonly/>

	<label><span>근무 지역</span></label>
		<input type="text" name="biz_loc" id="biz_loc" class="inputbox" value="[${bizbean.biz_zipcode}] ${bizbean.biz_loc_1} ${bizbean.biz_loc_2}" readonly/>

	<label><span>고용형태</span></label> 
		<input type="text" name="biz_pattern" id="biz_pattern" class="inputbox" value="${bizbean.biz_pattern}" readonly/>
						
	<label><span>직종</span></label>
		<input type="text" name="biz_occ" id="biz_occ" class="inputbox" value="${bizbean.biz_occ}" readonly/>
		
	<label><span>직급</span></label>
		<input type="text" name="biz_position" id="biz_position" class="inputbox" value="${bizbean.biz_position}" readonly/>
		
	<label><span>희망경력</span></label>
		<input type="text" name="biz_career" id="biz_career" class="inputbox" value="${bizbean.biz_career}" readonly/>

	<label><span>급여</span></label> 
		<input type="text" name="biz_pay" id="biz_pay" class="inputbox" value="${bizbean.biz_pay}" readonly/>

	<label><span>최종학력</span></label> 
		<input type="text" name="biz_education" id="biz_education" class="inputbox" value="${bizbean.biz_education}" readonly/> 	

	<label><span>접수기한</span></label>
		<input type="text" name="biz_expiry_date" id="biz_expiry_date" class="inputbox" value="${bizbean.biz_expiry_date}" readonly/>
		
	<label><span>내용</span></label>
		<textarea name="biz_content" id="biz_content" readonly>${bizbean.biz_content} </textarea>
		
	<label><span>파일첨부</span></label>
	<c:if test="${!empty bizbean.biz_file}">
		<a href="./jsp/biz/file_down.jsp?file_name=${bizbean.biz_file}" class="inputbox_file" >
			${bizbean.biz_file}</a>
	</c:if>
	<c:if test="${empty bizbean.biz_file}">
			<input type="text" value="첨부 파일 없음" class="inputbox_file" readonly />
	</c:if>
	
	<table cellpadding="0" cellspacing="0" align=center>
		<tr align="right" valign="middle">
			<td>
				<div style="float:left">지원자 수 : ${applicantCount}명</div>
				<div style="float:right">
				<font size=2>
				<c:if test="${bizbean.biz_user==id }">
					<a href="./biz_getCont.biz?biz_num=${bizbean.biz_num}&page=${page}&state=modify&boardForm=${boardForm}">
					[수정]</a>&nbsp;&nbsp;
					<a href="#" onClick="checkDelete(${bizbean.biz_num}, ${page})">
					[삭제]</a>&nbsp;&nbsp;
					</c:if>
					</font>
				</div>
			</td>
		</tr>
	</table>
	<table>
		<tr>
			<td>
				<div style="float:left;">
					<c:if test="${favoriteOnOff==0}">
						<button id="biz_favorite_on" class="biz_cont_btn_nsk"
						onClick="favoriteOn(${bizbean.biz_num}, ${page})">
							<img src="images/biz/favorite_off.png" style="width:15px; height:15px;"/> 즐겨찾기 추가
						</button>
					</c:if>
					<c:if test="${favoriteOnOff==1}">
						<button id="biz_favorite_off" class="biz_cont_btn_nsk"
						onClick="favoriteOff(${bizbean.biz_num}, ${page})">
							<img src="images/biz/favorite_on.png" style="width:15px; height:15px;"/> 즐겨찾기 제거
						</button>
					</c:if>
				</div>
				<div style="float:right;">
					<c:if test="${bizbean.biz_user==id }">
						<input type="button" value="지원자 보기" id="applicant_list_btn" class="biz_cont_btn_nsk" 
						onClick="location='applicant_list.biz?biz_num=${bizbean.biz_num}&page=${page}&boardForm=${boardForm}'"/>
					</c:if>
					<input type="button" value="지원하기" id="apply_this_btn" class="biz_cont_btn_nsk" 
					onClick="checkApply(${bizbean.biz_num}, ${page})"/>
					<input type="button" value="목록" id="list_btn" class="biz_cont_btn_nsk"
					onClick="returnToList(${page})"/>
				</div>
			</td>
		</tr>
	</table>
	<div class="spacer"></div>
</div>
</body>
</html>