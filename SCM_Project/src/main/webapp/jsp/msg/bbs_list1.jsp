<%@ page language="java" contentType="text/html; charset=euc-kr"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="dao.msg.BbsDAOImpl.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<html>
<head>
	<title>�޼��� �Խ���</title>
<link rel="stylesheet" type="text/css" href="./css/msg/list.css" />	
	<!-- ����������(SNS) ���̵� ���� CSS�ε� -->
<link rel="stylesheet" type="text/css"  href="./css/sns/sns_side.css">

<!-- ����������(SNS) sns���� ������ �κ� CSS�ε� -->
<link rel="stylesheet" type="text/css"  href="./css/sns/sns_content.css">

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
		 			<form action="./bbs_list4.msg">
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
<!-- �Խ��� ����Ʈ -->
<div class="content-area">
<table align=center width=640 cellpadding="0" cellspacing="0">
 
<!-- ���ڵ尡 ������ -->
<!-- core ���̺귯�� ��� -->
<c:if test="${listcount > 0 }">

	<tr align="center" valign="middle">
		<td colspan="3" class="b1">���� �޼���</td>
		<td align=right class="b2">
			<font size=2>���� ���� : ${listcount }</font>
		</td>
	</tr>
	
	<tr align="center" valign="middle">
		<td class="even" width="50px">
			<div class="even"align="center">��ȣ</div>
		</td>
		<td  class="even"width="220px">
			<divclass="even" align="center">����</div>
		</td>
		<td class="even"  width="120px">
			<div class="even" align="center">�������</div>
		</td>
		<td class="even" width="90px">
			<div class="even"align="center">��¥</div>
	
		
		</td>
		</td>
	</tr>
	
	<c:set var="num" value="${listcount-(page-1)*10}"/> 	
	
	<c:forEach var="b" items="${bbslist}">	
	
	<tr align="center" valign="middle" bordercolor="#333333"
		onmouseover="this.style.backgroundColor='F8F8F8'"
		onmouseout="this.style.backgroundColor=''">
		<td height="23">
		
			<!-- ��ȣ ��� �κ� 
		      <%--   <%=number--%> �̹����� �Ʒ� ó��   --%>
		     -->				
			
			<c:out value="${num}"/>		
			<c:set var="num" value="${num-1}"/>	
			<!-- num = num - 1  -->	
			<td >
			<div align="left">
		
			<a class="a" href="bbs_cont1.msg?num=${b.MESSAGE_NUM}&page=${page}">
				<%--bl.getBOARD_SUBJECT()--%>
				${b.MESSAGE_SUBJECT}
			</a>
			</div>
		</td>
		
					
		</td>
		<td >
			<div align="center"><%--bl.getBOARD_NAME() --%></div>
					${b.MESSAGE_REC}
		</td>
		<td >
			<div align="center"><%--bl.getBOARD_DATE() --%></div>
					${b.MESSAGE_DATE}
		</td>	
	
	</tr>
	
	</c:forEach>
	<%-- </c:if> --%>
	<%//}// for end %>
	
	
	<tr align=center height=20>
		<td colspan=7>
			
			<c:if test="${page <= 1 }">
				<a>��</a>&nbsp;
			</c:if>
			<c:if test="${page > 1 }">			
				 <a href="bbs_list1.msg?page=${page-1}">��</a>&nbsp;
			</c:if>
			
			<c:forEach var="a" begin="${startpage}" end="${endpage}">
				<c:if test="${a == page }">
				<a>${a}</a>
				</c:if>
				<c:if test="${a != page }">
					<a href="bbs_list1.msg?page=${a}" >${a}</a>&nbsp;
				</c:if>
			</c:forEach>
			
			<c:if test="${page >= maxpage }">
				<a >��</a> 
			</c:if>
			<c:if test="${page < maxpage }">
				<a href="bbs_list1.msg?page=${page+1}&user=">��</a>
			</c:if>
			
			
		</td>
	</tr>
	
	</c:if>
	<%
//    }else{
	%>
	
	<!-- ���ڵ尡 ������ -->
	<c:if test="${listcount == 0 }">
	<tr align="center" valign="middle">
		<td colspan="4">���� �޼���</td>
		<td align=right>
			<font size=2>����  �޼�����  �����ϴ�.</font>
		</td>
	</tr>
	</c:if>
	
	<%
//	}
	%>
</table>
</div>
</div>
</body>
</html>