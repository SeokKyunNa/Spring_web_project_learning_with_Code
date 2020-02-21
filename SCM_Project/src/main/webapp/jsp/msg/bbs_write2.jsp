<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
	<title>메세지 게시판</title>
<link rel="stylesheet" type="text/css" href="./css/msg/write.css" />
	<!-- 메인컨텐츠(SNS) 사이드 영역 CSS로드 -->
<link rel="stylesheet" type="text/css"  href="./css/sns/sns_side.css">

<!-- 메인컨텐츠(SNS) sns내용 나오는 부분 CSS로드 -->
<link rel="stylesheet" type="text/css"  href="./css/sns/sns_content.css">
<script src="./js/msg/jquery.js"></script>
<script src="./js/msg/write.js"></script>
</head>
<body>
<div class="main-content">
	
		<!-- 내정보 보기 -->
		<div class="user-area">
			<div class="user-pic">
<c:if test="${ prfbean.PROFILE_PIC != null }"><img src="./upload/prf/${prfbean.PROFILE_PIC }" width="200px" height="273px"></c:if>
					<c:if test="${ prfbean.PROFILE_PIC == null }"><img src="./images/sns/noimg.png" width="200px" height="273px"></c:if>			</div>
			<div class="user-textarea">
				<div class="user-text1">
					<span class="user-id">@${id }</span><br>
					<span class="user-name"> ${join_name}</span>
		 		</div>
		 		<form action="./bbs_list3.msg">
		 			<tr>
			 			<td>
			 			 보낸사람 ID를 입력하세요
		 				<input type="text" size="17" id="text" name="text">
		 				</td>
		 			
		 				<td>
		 				<input type=submit  value="검색" class="button_write2">
		 				</td>
		 			</tr>
		 		</form>
			
				<div class="user-text2"><div><a href="./bbs_write.msg">메세지 보내기</a></div></div>
				<div class="user-text3"><div><a href="./bbs_list.msg"> 전체 메세지</a></div></div>
				<div class="user-text3"><div><a href="./bbs_list1.msg"> 보낸 메세지</a></div></div>
				<div class="user-text3"><div><a href="./bbs_list2.msg"> 받은 메세지</a></div></div>
			</div>
		</div>
		<!-- 내정보 보기 끝-->
		<div class="content-area">
<form action="bbs_write2_ok.msg" method="post" 
	enctype="multipart/form-data" onsubmit="return check()" >
<table cellpadding="0" cellspacing="0" align=center >
	<tr align="center" valign="middle">
		<td  colspan="5" class="even">답장 보내기</td>
	</tr>
	<tr >
		<td>
			<div align="center">받는 사람</div>
		</td>
		<td>
				
	<input name="MESSAGE_REC" id="MESSAGE_REC" type="text" size="10"  maxlength="100" 
				value="${user}"  readonly="readonly"/>
			</a>
		</td>
	</tr>
		<tr class="row">
		<td>
			<div align="center">제 목</div>
		</td>
		<td>
			<input name="MESSAGE_SUBJECT" id="MESSAGE_SUBJECT" type="text" size="50" maxlength="100" 
				value=""/>
		</td>
	</tr>
	<tr>
		<td>
			<div align="center">내 용</div>
		</td>
		<td>
			<textarea name="MESSAGE_BODY" id="MESSAGE_BODY" cols="67" rows="15" ></textarea>
		</td>
	</tr>
	<tr class="row">
		<td>
			<div align="center">파일 첨부</div>
		</td>
		<td>
			<div class="filebox">
  		 	<label for="ex_file" class="cell">파일선택</label>
 		 	<input  name="MESSAGE_FILE" type="file"id="ex_file"/>
 		 	</div>
		</td>
	</tr>
	<tr align="center" valign="middle">
		<td colspan="5">			
			<input class="button_write1" type=submit value="보내기">
			<input class="button_write1" value="취소" type=button onClick="history.go(-1)" >
		</td>
	</tr>
</table>
</form>
</div>
</div>
</body>
</html>