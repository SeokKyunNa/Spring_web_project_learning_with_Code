<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="model.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script src="./js/biz/biz_board_modify.js"></script>
<script src="./js/biz/member.js"></script>
<link rel="stylesheet" type="text/css" href="./css/biz/biz_board_write.css">
	<title>채용공고 수정</title>
</head>

<body>
<!-- 게시판 수정 -->

<div id="titlearea" align="center"><p id="title">&nbsp;&nbsp;채용공고 수정 </p></div>
<div id="myform" style="margin: auto;">
	<form id="form" name="form" action="./biz_ModifyOk.biz?biz_num=${bizbean.biz_num}&page=${page}&boardForm=${boardForm}" 
	method="post" enctype="multipart/form-data">
		<input type="hidden" name=page value="${page}">
		<div id="jump"></div>
		<label><span>제목</span></label> 
			<input type="text" name="biz_subject" id="biz_subject" class="inputbox" value="${bizbean.biz_subject }"/>

		<label><span>회사명</span></label>
			<input type="text" name="biz_name" id="biz_name" class="inputbox" value="${bizbean.biz_name }"/>
	
		<label style="margin-bottom: 115px;"><span>근무지역</span></label>
					<input name="biz_zipcode_1" id="biz_zipcode_1" class="inputbox_100" value="${biz_zipcode_0}"
						readonly onclick="post_search()" />
					<font style="float:left; margin-left:20px;">-</font>
					<input name="biz_zipcode_2" id="biz_zipcode_2" class="inputbox_100"  value="${biz_zipcode_1}"
						readonly onclick="post_search()"/>
					<input name="biz_zipcode_btn" id="biz_zipcode_btn" type="button" value="우편번호검색" class="inputbox_100"
						onclick="post_check()" />
					
					<input name="biz_loc_1" id="biz_loc_1" class="inputbox"
						readonly onclick="post_search()" value="${bizbean.biz_loc_1}"/>
					<input name="biz_loc_2" id="biz_loc_2" class="inputbox" value="${bizbean.biz_loc_2}"/>
	
		<label><span>고용형태</span></label> 
			<input type="text" name="biz_pattern" id="biz_pattern" class="inputbox_half" value="${bizbean.biz_pattern}" />
			<select id="biz_pattern_select" >
				<option>선택하세요</option>
				<option>정규직</option>
				<option>계약직</option>
				<option>병역특례</option>
				<option>인턴</option>
				<option>파견직</option>
				<option>프리랜서</option>
				<option>위촉직</option>
				<option>아르바이트</option>
				<option val="0">직접 입력</option>
			</select>
											
		<label><span>직종</span></label>
			<input type="text" name="biz_occ" id="biz_occ" class="inputbox" value="${bizbean.biz_occ}" />
			
		<label><span>직급</span></label>
			<input type="text" name="biz_position" id="biz_position" class="inputbox" value="${bizbean.biz_position}" />
			
		<label><span>희망경력</span></label> 
			<input type="text" name="biz_career" id="biz_career" class="inputbox_half" value="${bizbean.biz_career}"/>
			<select id="biz_career_select" >
				<option>선택하세요</option>
				<option>무관</option>
				<option>신입</option>
				<option val="0">경력(희망경력 입력)</option>
			</select>
				
		<label><span>급여</span></label> 
			<input type="text" name="biz_pay" id="biz_pay" class="inputbox" value="${bizbean.biz_pay}" />
	
		<label><span>최종학력</span></label> 
			<input type="text" name="biz_education" id="biz_education" class="inputbox" value="${bizbean.biz_education}" /> 	
	
		<label><span>접수기한</span></label>
			<input type="date" name="biz_expiry_date" id="biz_expiry_date" class="inputbox" value="${bizbean.biz_expiry_date }"/> 
			
		<label><span>내용</span></label>
			<textarea name="biz_content" id="biz_content" >${bizbean.biz_content} </textarea>
			
		<label><span>파일첨부</span></label>
		<c:if test="${!empty bizbean.biz_file}">
			<div style="float: left; font-size: 12px; width: auto; margin: 0 0 20px 30px; color: #000000;">
               ${bizbean.biz_file}
            </div>
		</c:if>
		<c:if test="${empty bizbean.biz_file}">
				<input type="file" name="biz_file" id="biz_file" class="inputbox" />
		</c:if>
		
		<input type="submit" value="수정하기" id="modify" class="biz_write_btn_nsk" style="margin-left:470px;"/>
		<input type="button" value="취소" id="cancle" class="biz_write_btn_nsk" onClick="history.go(-1)"/>
		<div class="spacer"></div>
	</form>
</div>
</form>
<!-- 게시판 수정 -->
</body>
</html>