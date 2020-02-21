<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
	<title>�޼��� �Խ���</title>
<link rel="stylesheet" type="text/css" href="./css/msg/write.css" />
	<!-- ����������(SNS) ���̵� ���� CSS�ε� -->
<link rel="stylesheet" type="text/css"  href="./css/sns/sns_side.css">

<!-- ����������(SNS) sns���� ������ �κ� CSS�ε� -->
<link rel="stylesheet" type="text/css"  href="./css/sns/sns_content.css">
<script src="./js/msg/jquery.js"></script>
<script src="./js/msg/write.js"></script>
</head>
<body>
<div class="main-content">
	
		<!-- ������ ���� -->
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
			 			 ������� ID�� �Է��ϼ���
		 				<input type="text" size="17" id="text" name="text">
		 				</td>
		 			
		 				<td>
		 				<input type=submit  value="�˻�" class="button_write2">
		 				</td>
		 			</tr>
		 		</form>
			
				<div class="user-text2"><div><a href="./bbs_write.msg">�޼��� ������</a></div></div>
				<div class="user-text3"><div><a href="./bbs_list.msg"> ��ü �޼���</a></div></div>
				<div class="user-text3"><div><a href="./bbs_list1.msg"> ���� �޼���</a></div></div>
				<div class="user-text3"><div><a href="./bbs_list2.msg"> ���� �޼���</a></div></div>
			</div>
		</div>
		<!-- ������ ���� ��-->
		<div class="content-area">
<form action="bbs_write2_ok.msg" method="post" 
	enctype="multipart/form-data" onsubmit="return check()" >
<table cellpadding="0" cellspacing="0" align=center >
	<tr align="center" valign="middle">
		<td  colspan="5" class="even">���� ������</td>
	</tr>
	<tr >
		<td>
			<div align="center">�޴� ���</div>
		</td>
		<td>
				
	<input name="MESSAGE_REC" id="MESSAGE_REC" type="text" size="10"  maxlength="100" 
				value="${user}"  readonly="readonly"/>
			</a>
		</td>
	</tr>
		<tr class="row">
		<td>
			<div align="center">�� ��</div>
		</td>
		<td>
			<input name="MESSAGE_SUBJECT" id="MESSAGE_SUBJECT" type="text" size="50" maxlength="100" 
				value=""/>
		</td>
	</tr>
	<tr>
		<td>
			<div align="center">�� ��</div>
		</td>
		<td>
			<textarea name="MESSAGE_BODY" id="MESSAGE_BODY" cols="67" rows="15" ></textarea>
		</td>
	</tr>
	<tr class="row">
		<td>
			<div align="center">���� ÷��</div>
		</td>
		<td>
			<div class="filebox">
  		 	<label for="ex_file" class="cell">���ϼ���</label>
 		 	<input  name="MESSAGE_FILE" type="file"id="ex_file"/>
 		 	</div>
		</td>
	</tr>
	<tr align="center" valign="middle">
		<td colspan="5">			
			<input class="button_write1" type=submit value="������">
			<input class="button_write1" value="���" type=button onClick="history.go(-1)" >
		</td>
	</tr>
</table>
</form>
</div>
</div>
</body>
</html>