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
						<input type="hidden" name="PROFILE_ID" value="admin"><!-- value�ڸ��� ������ ���̵� �;��� -->
						<a onclick=formSubmit1()>������ ����</a>
					</form>
				</li>
				<li>
					<form id="sideform2" method="post" enctype="multipart/form-data" action="ProfileModifyWrite.pro">
						<input type="hidden" name="PROFILE_ID" value="admin"><!-- value�ڸ��� ������ ���̵� �;��� -->
						<a onclick=formSubmit2()>������ �����ϱ�</a>
					</form>
				</li>
				<li>
					<form id="sideform3" method="post" enctype="multipart/form-data" action="ProfileModifyWrite.pro?page=profile_board/profile_modify.jsp#location-1">
						<input type="hidden" name="PROFILE_ID" value="admin"><!-- value�ڸ��� ������ ���̵� �;��� -->
						<a onclick=formSubmit3()>�з»���</a>
					</form>
				</li>
				<li>
					<form id="sideform4" method="post" enctype="multipart/form-data" action="ProfileModifyWrite.pro?page=profile_board/profile_modify.jsp#location-2">
						<input type="hidden" name="PROFILE_ID" value="admin"><!-- value�ڸ��� ������ ���̵� �;��� -->
						<a onclick=formSubmit4()>��»���</a>
					</form>
				</li>
				<li>
					<form id="sideform5" method="post" enctype="multipart/form-data" action="ProfileModifyWrite.pro?page=profile_board/profile_modify.jsp#location-3">
						<input type="hidden" name="PROFILE_ID" value="admin"><!-- value�ڸ��� ������ ���̵� �;��� -->
						<a onclick=formSubmit5()>���ɺо�</a>
					</form>
				</li>
				<li>
					<form id="sideform6" method="post" enctype="multipart/form-data" action="ProfileModifyWrite.pro?page=profile_board/profile_modify.jsp#location-4">
						<input type="hidden" name="PROFILE_ID" value="admin"><!-- value�ڸ��� ������ ���̵� �;��� -->
						<a onclick=formSubmit6()>��� ��� ����</a>
					</form>
				</li>
				<li>
					<form id="sideform7" method="post" enctype="multipart/form-data" action="ProfileModifyWrite.pro?page=profile_board/profile_modify.jsp#location-5">
						<input type="hidden" name="PROFILE_ID" value="admin"><!-- value�ڸ��� ������ ���̵� �;��� -->
						<a onclick=formSubmit7()>�ڰ�������</a>
					</form>
				</li>
				<li>
					<form id="sideform8" method="post" enctype="multipart/form-data" action="ProfileModifyWrite.pro?page=profile_board/profile_modify.jsp#location-6">
						<input type="hidden" name="PROFILE_ID" value="admin"><!-- value�ڸ��� ������ ���̵� �;��� -->
						<a onclick=formSubmit8()>�ܱ���</a>
					</form>
				</li>
				<li>
					<form id="sideform9" method="post" enctype="multipart/form-data" action="ProfileModifyWrite.pro?page=profile_board/profile_modify.jsp#location-7">
						<input type="hidden" name="PROFILE_ID" value="admin"><!-- value�ڸ��� ������ ���̵� �;��� -->
						<a onclick=formSubmit9()>�ڱ�Ұ�</a>
					</form>
				</li>
			</ul>
		</div>
		<!-- <div id="recruit-title">ä������ �ٷΰ���	</div> -->
		<div class="arrow_box">ä������ �ٷΰ���	</div>
		<div id="recruit-list" align="center">
			<ul>
				<li><a href="#">���̹� java ������ ����</a></li>
				<li><a href="#">���� �����α׷��� ����</a></li>
				<li><a href="#">����ũ�μ���Ʈ ������� ����</a></li>
				<li><a href="#">����Ŭ scott ����</a></li>
				<li><a href="#">�Ｚ �������α׷��� ����</a></li>
			</ul>
		</div>
	</div>
</body>
</html>
