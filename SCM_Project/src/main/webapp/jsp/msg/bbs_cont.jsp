<%@ page language="java" contentType="text/html; charset=euc-kr"%>
<%@ page import="dao.msg.BbsDAOImpl.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<html>
<head>
	<title>메세지 게시판</title>
	<link rel="stylesheet" type="text/css" href="./css/msg/cont.css" />
	<!-- 메인컨텐츠(SNS) 사이드 영역 CSS로드 -->
<link rel="stylesheet" type="text/css"  href="./css/sns/sns_side.css">

<!-- 메인컨텐츠(SNS) sns내용 나오는 부분 CSS로드 -->
<link rel="stylesheet" type="text/css"  href="./css/sns/sns_content.css">
<script src="./js/msg/jquery.js"></script>
<script src="./js/msg/cont.js"></script>
</head>
<body>
<div class="main-content">
	
		<!-- 내정보 보기 -->
		<div class="user-area">
			<div class="user-pic">
<c:if test="${ prfbean.PROFILE_PIC != null }"><img src="./upload/prf/${prfbean.PROFILE_PIC }" width="200px" height="273px"></c:if>
					<c:if test="${ prfbean.PROFILE_PIC == null }"><img src="./images/sns/noimg.png" width="200px" height="273px"></c:if>
			</div>
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
<!-- 게시판 내용보기 -->
<div class="content-area">
<table cellpadding="0" cellspacing="0">
	<tr align="center" valign="middle">
		<td class="even" colspan="5" height="30px"  >내용 확인</td>
	</tr>
	<tr >
	<td width="100px" height="21px">
			<div align="center" >보낸사람&nbsp;&nbsp;</div>
		</td>
		<td align="left" >
		${bbsbean.MESSAGE_USER}		
		</td>
	</tr>
	
	<tr>
		<td align="left" height="21px" >
			<div align="center">제 목&nbsp;&nbsp;</div>
		</td>
		
		<td align="left">
		${bbsbean.MESSAGE_SUBJECT}		
		</td>
	</tr>
	
	</tr>
	
	<tr >
		<td height="220px">
			<div align="center">내 용</div>
		<td valign=top>
					<pre align="left">${bbsbean.MESSAGE_BODY}</pre>
					</td>
		</td>
	</tr>

	<tr >
		<td height="16">
			<div align="center">첨부파일</div>
		</td>
		<td >
		<c:if test="${!empty bbsbean.MESSAGE_FILE}">
			<a class="a"
			href="/SCM_Project/jsp/msg/file_down.jsp?file_name=${bbsbean.MESSAGE_FILE}">
				${bbsbean.MESSAGE_FILE}</a>
		</c:if>		
		
		</td>
	</tr>
	
	<tr align="center" valign="middle">
		<td colspan="5" class="link-effect-9" id="link-effect-9">
			<font size=2>
			<a class="a"
			href="bbs_write2.msg?num=${bbsbean.MESSAGE_NUM}&page=${page}&user=${bbsbean.MESSAGE_USER}" >
			답장</a>&nbsp;&nbsp;&nbsp;&nbsp;
			<a class="a"
			href="#" onclick="yesorno(${bbsbean.MESSAGE_NUM},${page})">삭제</a>&nbsp;&nbsp;&nbsp;&nbsp;
			
			<a class="a"
			href="bbs_list2.msg?page=${page}" >목록</a>&nbsp;&nbsp;			
			</font>
		</td>
	</tr>
</table>
<!-- 게시판 내용보기
 -->
 </div>
 </div>
</body>
</html>