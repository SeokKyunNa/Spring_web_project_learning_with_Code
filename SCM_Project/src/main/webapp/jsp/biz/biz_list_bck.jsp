<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="model.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
	<title>채용공고 게시판</title>
	<script>
	$(document).ready(function(){
		$("#biz_serach_btn").onClick(function(){
			if($("#biz_serach_txt").val()==""){
			alert("검색어를 입력하세요");
			$("#biz_serach_btn").focus();
			return false;
		}
		});
		$("form").submit(function(){				
			if($("#biz_name").val()==""){
				alert("회사명을 입력 하세요");
				$("#biz_name").focus();
				return false;
			}
	});
	</script>
	<style>
		a{
			text-decoration: none;
		}
		body, p, form{
			border: 0px; margin: auto; padding: 0px;
		}
		.spacer{
			clear: both; height: 70px;
		}
		#myform{
			margin: 10px; width: 650px; padding: 14px;
			border-radius: 7px;
			padding: 0px; margin: 0px;
		}
		#title{
			height: 35px; line-height: 35px;
			font-size: 14px; font-weight: bold;
			margin-bottom: 8px;
			font-family: nanumgothic, dotum;
			color: white;
			background-color:#bcbcbc;
		}
		#myform label{
			display: block; font-weight: bold;
			text-align: right; width: 160px;
			float: left; font-family: nanumgothic, dotum;
			color: #545454;
		}
		table{
			float: left;
			padding: 4px 2px;
			border: solid 1px #D2E9FF;
			margin: 2px 0 20px 30px;
			box-shadow: 1px 1px 10px 2px #EAEAEA;
			border-radius: 5px;
		}
		.inputbox{
			float: left;
			font-size: 12px;
			padding: 4px 2px;
			border: solid 1px #D2E9FF;
			width : 400px;
			margin: 2px 0 20px 30px;
			box-shadow: 1px 1px 10px 2px #EAEAEA;
			border-radius: 5px;
		}
		.inputbox_half{
			float: left;
			font-size: 12px;
			padding: 4px 2px;
			border: solid 1px #D2E9FF;
			width : 200px;
			margin: 2px 0 20px 30px;
			box-shadow: 1px 1px 10px 2px #EAEAEA;
			border-radius: 5px;
		}
		#myform textarea{
			float: left;
			font-size: 12px;
			padding: 4px 2px;
			border: solid 1px #D2E9FF;
			width : 400px; height: 300px;
			margin: 2px 0 20px 30px;
			box-shadow: 1px 1px 10px 2px #EAEAEA;
			border-radius: 5px;
		}
		#myform select{
			/* float: left; */
			width: 100px;
			padding: 4px 2px;
			border: solid 1px #D2E9FF;
			margin: 2px 0px 20px 30px;
			box-shadow: 1px 1px 10px 2px #EAEAEA;
			border-radius: 5px;
		}
		#jump{
			height: 15px;
		}
		#submit{
			margin-left: 430px;
			border:0px;
			width: 80px; height: 35px;
			text-align: center; line-height: 35px;
			background-color: #3397e2;
			color: #ffffff;
			font-size: 12px; 
			font-weight: bold;
			font-family: nanumgothic, dotum;	
		}
		#cancle{
			border:0px;
			width: 80px; height: 35px;
			text-align: center; line-height: 35px;
			background-color: #3397e2;
			color: #ffffff;
			font-size: 12px; 
			font-weight: bold;
			font-family: nanumgothic, dotum;
		}
	</style>
</head>

<body>
<!-- 게시판 리스트 -->
<div id="titlearea" align="center"><p id="title">&nbsp;&nbsp;BUSINESS 게시판입니다. </p></div>
<div id="myform">
<table align=center width=600 border="1" cellpadding="0" cellspacing="0">
<%
//if(listcount > 0){
%>

<!-- 레코드가 있으면 -->
<!-- core 라이브러리 사용 -->
<c:if test="${listcount > 0 }">

	<tr align="center" valign="middle">
		<td colspan="4">채용공고 게시판</td>
		<td align=right>
		</td>
	</tr>
	
	<tr align="center" valign="middle" bordercolor="#333333">
		<td style="font-family:Tahoma;font-size:8pt;" width="16%" height="26">
			<div align="center">회사명</div>
		</td>
		<td style="font-family:Tahoma;font-size:8pt;" width="48%">
			<div align="center">공고 내용</div>
		</td>
		<td style="font-family:Tahoma;font-size:8pt;" width="16%">
			<div align="center">지원 마감일</div>
		</td>
		<td style="font-family:Tahoma;font-size:8pt;" width="10%">
			<div align="center">지원자수</div>
		</td>
		<td style="font-family:Tahoma;font-size:8pt;" width="10%">
			<div align="center">조회수</div>
		</td>
	</tr>
	 
	<!-- 화면 출력 번호 
	     listcount-(page-1)*10 : 각 페이지의 시작 번호 값 
	 -->		
	<c:set var="num" value="${listcount-(page-1)*10}"/> 	
	
	<c:forEach var="b" items="${bizboardlist}">	
	
	<tr align="center" valign="middle" bordercolor="#333333"
		onmouseover="this.style.backgroundColor='F8F8F8'"
		onmouseout="this.style.backgroundColor=''">
		<td height="23" style="font-family:Tahoma;font-size:10pt;">
			<!-- 회사명 출력 부분	-->				
			<c:out value="${b.BIZ_NAME}"/>		
			<c:set var="num" value="${num-1}"/>	
			<!-- num = num - 1  -->	
		</td>
		
		<td style="font-family:Tahoma;font-size:10pt;">
			<div align="left" style="padding-left: 20px;">
				<a href="./BizBoardDetailAction.do?num=${b.BIZ_NUM}&page=${page}">
					<%--bl.getBOARD_SUBJECT()--%>
					<font size="3">${b.BIZ_OCC} / ${b.BIZ_CAREER} <br /></font> 
					${b.BIZ_LOC }
				</a>
			</div>
		</td>
		
		<td style="font-family:Tahoma;font-size:10pt;">
			<div align="center"><%--bl.getBOARD_NAME() --%></div>
					${b.BIZ_EXPIRY_DATE}
		</td>
		<td style="font-family:Tahoma;font-size:10pt;">
			<div align="center"><%--bl.getBOARD_DATE() --%></div>
					${b.BIZ_COUNT}
		</td>	
		<td style="font-family:Tahoma;font-size:10pt;">
			<div align="center"><%--bl.getBOARD_READCOUNT() --%></div>
					${b.BIZ_CLICK}
		</td>
	</tr>
	
	</c:forEach>
	<%-- </c:if> --%>
	<%//}// for end %>
	
	
	<tr align=center height=20>
		<td colspan=7 style=font-family:Tahoma;font-size:10pt;>
			<%--if(nowpage<=1){ --%>
					<!-- [이전]&nbsp; -->			
			<%--}else{ --%>	
			<%-- <a href="./BoardList.bo?page=<%=nowpage-1 %>">[이전]</a>&nbsp; --%>			
			<%--} --%>
			
			<c:if test="${page <= 1 }">
				[이전]&nbsp;
			</c:if>
			<c:if test="${page > 1 }">			
				 <a href="./BizBoardList.bo?page=${page-1}">[이전]</a>&nbsp;
			</c:if>
			
			<c:forEach var="a" begin="${startpage}" end="${endpage}">
				<c:if test="${a == page }">
					[${a}]
				</c:if>
				<c:if test="${a != page }">
					<a href="./BizBoardList.do?page=${a}">[${a}]</a>&nbsp;
				</c:if>
			</c:forEach>
			
			<c:if test="${page >= maxpage }">
				[다음] 
			</c:if>
			<c:if test="${page < maxpage }">
				<a href="./BizBoardList.do?page=${page+1}">[다음]</a>
			</c:if>
			
		</td>
	</tr>
	
	</c:if>
	<%
//    }else{
	%>
	
	<!-- 레코드가 없으면 -->
	<c:if test="${listcount == 0 }">
	<tr align="center" valign="middle">
		<td colspan="4">BUSINESS 게시판</td>
		<td align=right>
			<font size=2>등록된 글이 없습니다.</font>
		</td>
	</tr>
	</c:if>
	
	<%
//	}
	%>

	<tr align="center" height="20">
		<td colspan="5">
			<select>
				<option>선택하세요</option>
				<option>회사명</option>
				<option>직종</option>
				<option>주소</option>
			</select>
			<input type="text" height="18" width="50" name="BIZ_SEARCH_TXT" id="biz_search_txt" />
			<input type="button" height="18" width="30" name="BIZ_SEARCH_BTN" id="biz_serach_btn" value="검색" />
		</td>
	</tr>
</table>
</div>
</body>
</html>