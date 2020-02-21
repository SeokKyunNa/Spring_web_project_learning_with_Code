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
<link rel="stylesheet" type="text/css" href="./css/prf/contentpage.css" /> 
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
					<c:if test="${ prfbean.PROFILE_PIC == null }"><img src="./images/sns/noimg.png" width="200px" height="273px"></c:if>
				<img src="./upload/prf/${prfbean.PROFILE_PIC }" width="200px" height="273px">
			</div>
			<div class="user-textarea">
				<div class="user-text1">
					<span class="user-id">@${id }</span><br>
					<span class="user-name"> ${join_name}</span>
		 		</div>
		 			
			
				<div class="user-text2"><div><a href="./prf_write.prf">�������ۼ�</a></div></div>
				<div class="user-text3"><div><a href="./prf_view.prf"> ������ ����</a></div></div>
				<div class="user-text3"><div><a href="./prf_modify.prf"> ������ ����</a></div></div>				
				
			</div>
		</div>
		<!-- ������ ���� ��-->
	
	<!-- �Խ��� ���뺸�� -->
<div class="content-area">
<form name="profile_form" method="post" enctype="multipart/form-data" action="prf_modify.prf">
	<div>
		
		<!-- ���̵�, �̸�, �н����� �������� �κ� -->
		<input type="hidden" name="PROFILE_ID" value="${prfbean.PROFILE_ID }">
		<input type="hidden" name="PROFILE_USER" value="${prfbean.PROFILE_USER }">
		<div id="profile-pic">
			<div id="pic">
				<c:if test="${prfbean.PROFILE_PIC != null }">
					
				<img src="./upload/prf/${prfbean.PROFILE_PIC }" width="200px" height="273px">
 				</c:if>	
				<c:if test="${prfbean.PROFILE_PIC == null }">
					�����ʻ����� ������ �ּ���
				</c:if>	
			</div>
			<div id="present">	
				<ul>
					<li>${prfbean.PROFILE_USER }</li>
					<li> ȸ��� : 
						<c:if test="${ carrer[5] != null }">${carrer[5] }</c:if>
						<c:if test="${ carrer[5].trim() == null }">���� ��ϵ� ������ �����ϴ�.</c:if>
					</li>
					<li>
					 	���ɺо� :
						<c:if test="${ interest[0] != null }">${interest[0] }</c:if>
						<c:if test="${ interest[0].trim() == null }">���� ��ϵ� ���ɺо߰� �����ϴ�.</c:if>
					</li>
					<li>
					 	���� :
						<c:if test="${ age[0] != null }">&nbsp;${age[0]}&nbsp;&nbsp;&nbsp;������� : ${age[1]} </c:if>
						<c:if test="${ age[0].trim() == null }">���� ��ϵ� ���̰� �����ϴ�.</c:if>
					</li>
				</ul>
			</div>		
		</div>
		
		<div id="adu">
			<span id="location-1"></span>�����з�
			<table>
				<tr>
					<th width="165px">���бⰣ</th>
					<th width="130px">�б���</th>
					<th width="130px">����</th>
					<th>����</th>
				</tr>
				
				<tr>
					<td><center>
						${edu[4] } : ${edu[0] } . ${edu[1] } ~&nbsp;&nbsp;&nbsp;<br> 
						&nbsp;&nbsp;&nbsp;${edu[5] } : ${edu[2] } . ${edu[3] }
					</center></td>
					<td><center>
						${edu[6] }<br>
						<c:if test="${ edu[6] == null}">
							�������
						</c:if>
					</center></td>
					<td><center>
						${edu[7] }<br>
					</center></td>
					<td>-</td>
				</tr>
				
				<tr>
					<td><center>
						${edu[12] } : ${edu[8] } . ${edu[9] } ~ &nbsp;&nbsp;&nbsp;<br> 
						&nbsp;&nbsp;&nbsp; ${edu[13] } : ${edu[10] } .  ${edu[11] } 
						<br><br><br><br>
					</center></td>
					<td><center>
						${edu[14] }<br>
						${edu[15] }<br>
						����������������������������<br><br>
						${edu[16] }&nbsp;&nbsp;&nbsp;&nbsp;
						${edu[17] }
					</center></td>
					<td><center>
						${edu[18] }<br>
						${edu[19] }
					</center></td>
					<td>
						${edu[20] }<br>
						/<br>
						${edu[21] }${edu[22] }
					</td>
				</tr>
			</table>
		</div>
		
		<div id="carr">
			<span id="location-2"></span>��»���
			<table>
				<tr>
					<th width="170px">�ٹ��Ⱓ</th>
					<th colspan="4">�󼼰��</th>
				</tr>
				<tr>
					<td rowspan="6" align="left">
						${carrer[0] }<br><br>
						${carrer[1] } . ${carrer[2] } ~ ${carrer[3] } . ${carrer[4] }<br>
						
					</td>
					<th width="70px">ȸ���</th>
					<td colspan="3" align="left">${carrer[5] }</td>
				</tr>
				<tr>
					<th>����</th>
					<td colspan="3" align="left">${carrer[6] }</td>
				</tr>
				<tr>
					<th>�ٹ��μ�</th>
					<td align="left">${carrer[7] }</td>
					<th width="70px">�ٹ�����</th>
					<td  align="left">
						${carrer[8] }
					</td>
				</tr>
				<tr>
					<th>����</th>
					<td align="left">
						${carrer[9] }<br>
						${carrer[10] } ����
					</td>
					<th>����</th>
					<td align="left">${carrer[11] } ${carrer[12] }
					</td>
				</tr>
				<tr>
					<th>������</th>
					<td colspan="3" align="left">${carrer[13] }</td>
				</tr>
				<tr>
					<th>������</th>
					<td colspan="3" align="left">
						${carrer[14] }
					</td>
				</tr>
			</table>
		</div>
		
		<div id="inter">
		<span id="location-3"></span>���ɺо�
			<table>
				<tr>
					<th>���ɺо� �� ��� ���</th>
					<td align="left">
						���� �о� : ${interest[0] }&nbsp;&nbsp;&nbsp;&nbsp;
						�� ��� ��� : ${interest[1] }
					</td>
				</tr>
			</table>
		</div>
		
		<div id="local">
			<span id="location-4"></span>��� ��� ����
			<table>
				<tr>
					<th width="100px">��� ��� ����</th>
					<td align="left">
						&nbsp;&nbsp;&nbsp;&nbsp;
						<c:if test="${local[0] != null }">
							<c:forEach var="lo" items="${local }">
								${lo},
							</c:forEach>
						</c:if>
					</td>
				</tr>
			</table>
		</div>
		
		<div id="lisen">
			<span id="location-5"></span>�ڰ��� ����
			<table>
				<tr>
					<th width="200px">�ڰ���/������</th>
					<th>����ó/������</th>
					<th>�հݱ���</th>
					<th width="100px">�����</th>
				</tr>
				<tr>
					<td>${lisence[0] }</td>
					<td>${lisence[1] }</td>
					<td>${lisence[2] }</td>
					<td>
						${lisence[3] } . ${lisence[4] } 
					</td>
				</tr>
			</table>
		</div>
		
		<div id="lang">
			<span id="location-6"></span>�ܱ���
			<table>
				<tr>
					<th width="100px">���</th>
					<th>���� ����</th>
					<th>����(���)</th>
					<th width="100px">�����</th>
				</tr>
				<tr>
					<td>${langu[0] }</td>
					<td>${langu[1] }</td>
					<td>
						${langu[2] } �� 
						${langu[3] } ��
					</td>
					<td>
						${langu[4] } . ${langu[5] } 
					</td>
				</tr>
			</table>
		</div>
		
		<div id="intro">
			<span id="location-7"></span>�ڱ�Ұ�
			<table>
				<tr>
					<th>�����ִ� �ڱ�Ұ��� �ڽ��� �����ϼ���</th>
				</tr>
				<tr>
					<td>
						${prfbean.PROFILE_INTRO}
					</td>
				</tr>
			</table>
		</div>
		<div id="enter" align="center">
			<input type="submit" value="������ �����ϱ�" id="submit">
		</div>
	</div>
	<div class="spacer"></div>
</form>
</div>
</div>
</body>
</html>