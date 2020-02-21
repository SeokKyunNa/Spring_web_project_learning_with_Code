<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
  
	<link rel="stylesheet" type="text/css" href="./profile_board/css/sidepage.css" /> 
	<script type="text/javascript">
		function formSubmit1(){
			document.getElementById("sideform1").submit();
		}
		function formSubmit2(){
			document.getElementById("sideform2").submit();
		}
		function formSubmit3(){
			document.getElementById("sideform3").submit();
		}
		function formSubmit4(){
			document.getElementById("sideform4").submit();
		}
		function formSubmit5(){
			document.getElementById("sideform5").submit();
		}
		function formSubmit6(){
			document.getElementById("sideform6").submit();
		}
		function formSubmit7(){
			document.getElementById("sideform7").submit();
		}
		function formSubmit8(){
			document.getElementById("sideform8").submit();
		}
		function formSubmit9(){
			document.getElementById("sideform9").submit();
		}
	</script>
	<style>
		a{
			cursor : pointer;
		}
	</style>
</head>
<body>
	<div>
		<div id="deco"></div>
		<div id="profile-title">
			<span id="p-text">P</span><span id="title-top">ROFILE</span><br><span id="title-bottom" >WRITE
				ACCISTANT</span>
		</div>
		<div id="profile-menu">
			<ul>
				<li>
					<form id="sideform1" method="post" enctype="multipart/form-data" action="ProfileView.pro">
						<input type="hidden" name="PROFILE_ID" value="admin"><!-- value자리에 접속자 아이디가 와야함 -->
						<a onclick=formSubmit1()>프로필 보기</a>
					</form>
				</li>
				<li>
					<form id="sideform2" method="post" enctype="multipart/form-data" action="ProfileModifyWrite.pro">
						<input type="hidden" name="PROFILE_ID" value="admin"><!-- value자리에 접속자 아이디가 와야함 -->
						<a onclick=formSubmit2()>프로필 수정하기</a>
					</form>
				</li>
				<li>
					<form id="sideform3" method="post" enctype="multipart/form-data" action="ProfileModifyWrite.pro?page=profile_board/profile_modify.jsp#location-1">
						<input type="hidden" name="PROFILE_ID" value="admin"><!-- value자리에 접속자 아이디가 와야함 -->
						<a onclick=formSubmit3()>학력사항</a>
					</form>
				</li>
				<li>
					<form id="sideform4" method="post" enctype="multipart/form-data" action="ProfileModifyWrite.pro?page=profile_board/profile_modify.jsp#location-2">
						<input type="hidden" name="PROFILE_ID" value="admin"><!-- value자리에 접속자 아이디가 와야함 -->
						<a onclick=formSubmit4()>경력사항</a>
					</form>
				</li>
				<li>
					<form id="sideform5" method="post" enctype="multipart/form-data" action="ProfileModifyWrite.pro?page=profile_board/profile_modify.jsp#location-3">
						<input type="hidden" name="PROFILE_ID" value="admin"><!-- value자리에 접속자 아이디가 와야함 -->
						<a onclick=formSubmit5()>관심분야</a>
					</form>
				</li>
				<li>
					<form id="sideform6" method="post" enctype="multipart/form-data" action="ProfileModifyWrite.pro?page=profile_board/profile_modify.jsp#location-4">
						<input type="hidden" name="PROFILE_ID" value="admin"><!-- value자리에 접속자 아이디가 와야함 -->
						<a onclick=formSubmit6()>취업 희망 지역</a>
					</form>
				</li>
				<li>
					<form id="sideform7" method="post" enctype="multipart/form-data" action="ProfileModifyWrite.pro?page=profile_board/profile_modify.jsp#location-5">
						<input type="hidden" name="PROFILE_ID" value="admin"><!-- value자리에 접속자 아이디가 와야함 -->
						<a onclick=formSubmit7()>자격증정보</a>
					</form>
				</li>
				<li>
					<form id="sideform8" method="post" enctype="multipart/form-data" action="ProfileModifyWrite.pro?page=profile_board/profile_modify.jsp#location-6">
						<input type="hidden" name="PROFILE_ID" value="admin"><!-- value자리에 접속자 아이디가 와야함 -->
						<a onclick=formSubmit8()>외국어</a>
					</form>
				</li>
				<li>
					<form id="sideform9" method="post" enctype="multipart/form-data" action="ProfileModifyWrite.pro?page=profile_board/profile_modify.jsp#location-7">
						<input type="hidden" name="PROFILE_ID" value="admin"><!-- value자리에 접속자 아이디가 와야함 -->
						<a onclick=formSubmit9()>자기소개</a>
					</form>
				</li>
			</ul>
		</div>
		<!-- <div id="recruit-title">채용정보 바로가기	</div> -->
		<div class="arrow_box">채용정보 바로가기	</div>
		<div id="recruit-list" align="center">
			<ul>
				<li><a href="#">네이버 java 개발자 모집</a></li>
				<li><a href="#">구글 웹프로그래머 모집</a></li>
				<li><a href="#">마이크로소프트 영업사원 모집</a></li>
				<li><a href="#">오라클 scott 모집</a></li>
				<li><a href="#">삼성 응용프로그래머 모집</a></li>
			</ul>
		</div>
	</div>
</body>
</html>
