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
<script>
		function readURL(input){
			var reader = new FileReader();
			reader.onload=function(e){
				$('#UploadedImg').attr('src',e.target.result);
			}
			reader.readAsDataURL(input.files[0]);
		}
		
		$(document).ready(function() {
			$("form").submit(function() {
				if ($("#PROFILE_PIC").val() == "") {
					alert("�����ʻ����� ����ϼ���");
					$("#PROFILE_PIC").focus();
					return false;
				}
			});
		});
		
	</script>  
</head>
<body>
<div class="main-content">
<!-- ������ ���� -->
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
			
				<div class="user-text2"><div><a href="./prf_write.prf">�������ۼ�</a></div></div>
				<div class="user-text3"><div><a href="./prf_view.prf"> �������� ����</a></div></div>
				<div class="user-text3"><div><a href="./prf_modify.prf"> ������ ����</a></div></div>
			</div>
		</div>
		<!-- ������ ���� ��-->
	
	<!-- �Խ��� ���뺸�� -->
<div class="content-area">
<form name="profile_form"  method="post" enctype="multipart/form-data" action="prf_write_ok.prf">
	<div>
		<div id="profile-pic">
			<div id="pic">
				<input type="file" onchange="readURL(this)" name="PROFILE_PIC" id="PROFILE_PIC">
				<c:if test="${prfbean.PROFILE_PIC != null }">
				<img src="./upload/prf/${prfbean.PROFILE_PIC}" id="UploadedImg" width="200px">
				</c:if>	
				<c:if test="${prfbean.PROFILE_PIC == null }">
					<img id="UploadedImg" src="" width="200" height="250">
				</c:if>
					
			</div>
			<div id="present">
				<ul>
					<li class="a1">${prfbean.PROFILE_USER }</li>
					<li>
							ȸ��� :
						<c:if test="${ carrer[5] != null }">${carrer[5] }</c:if>
						<c:if test="${ carrer[5].trim() == null }">���� ��ϵ� ������ �����ϴ�.</c:if>
					</li>
					<li>
					���ɺо� :
						<c:if test="${ interest[0] != null }">${interest[0] }</c:if>
						<c:if test="${ interest[0].trim() == null }">���� ��ϵ� ���ɺо߰� �����ϴ�.</c:if>
					</li>
				</ul>
				 &nbsp;&nbsp;&nbsp;&nbsp;����<input type="text" size="5"value="${age[0] }" name="PROFILE_AGE"/>
		  	   �������&nbsp;&nbsp;<input type="text" size="10"value="${age[1]}" name="PROFILE_AGE"/>
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
						<input type="text"  id="year" name="PROFILE_EDU" value="${edu[0] }"/> . <input type="text" id="month" name="PROFILE_EDU" value="${edu[1] }"> ~ <input type="text"  id="year" name="PROFILE_EDU" value="${edu[2] }"/> . <input type="text" id="month" name="PROFILE_EDU" value="${edu[3] }"><br>
						<select id="type-a" name="PROFILE_EDU"> 
							<option>����</option>
							<option>����</option>
						</select>&nbsp;&nbsp;&nbsp;
						<select id="type-a" name="PROFILE_EDU">
							<option>����</option>
							<option>������</option>
							<option>������</option>
							<option>����</option>
							<option>����</option>
						</select>
					</center></td>
					<td><center>
						<input type="text" id="school" name="PROFILE_EDU" value="${edu[6]}"><br>
						<input type="checkbox" id="check1" name="PROFILE_EDU" value="�������"> ���� ������� (����)
					</center></td>
					<td><center>
						<select id="option4" name="PROFILE_EDU">
							<option>�̰� �迭</option>
							<option>���� �迭</option>
							<option>��ü�� �迭</option>
							<option>�Ǿ���</option>
						</select><br>
					</center></td>
					<td>-</td>
				</tr>
				
				<tr>
					<td><center>
						<input type="text"  id="year" name="PROFILE_EDU" value="${edu[8] }"/> . <input type="text" id="month" name="PROFILE_EDU" value="${edu[9] }"> ~ <input type="text"  id="year" name="PROFILE_EDU" value="${edu[10] }"/> . <input type="text" id="month" name="PROFILE_EDU" value="${edu[11] }"><br>
						<select id="type-a" name="PROFILE_EDU"> 
							<option>����</option>
							<option>����</option>
						</select>&nbsp;&nbsp;&nbsp;
						<select id="type-a" name="PROFILE_EDU">
							<option>����</option>
							<option>������</option>
							<option>������</option>
							<option>����</option>
							<option>����</option>
						</select><br><br><br><br>
					</center></td>
					<td><center>
						<input type="text" name="PROFILE_EDU" value="${edu[14] }">
						<select id="yearing" name="PROFILE_EDU">
							<option>����(2,3��)</option>
							<option>���б�(4��)</option>
							<option>���п�(����)</option>
							<option>���п�(�ڻ�)</option>
						</select><br>
						����������������������������<br>
						<select id="type-a" name="PROFILE_EDU">
							<option>�ְ�</option>
							<option>�߰�</option>
						</select>&nbsp;&nbsp;&nbsp;&nbsp;
						<select id="type-a" name="PROFILE_EDU">
							<option>����</option>
							<option>����</option>
							<option>����</option>
							<option>�뱸</option>
							<option>�λ�</option>
							<option>����</option>
							<option>����</option>
							<option>�ϱ�</option>
						</select>
					</center></td>
					<td><center>
						<select id="option4" name="PROFILE_EDU">
							<option>��ü�� �迭</option>
							<option>�ǰ� �迭</option>
							<option>���� �迭</option>
							<option>��ǻ�� �迭</option>
							<option>���� �迭</option>
							<option>�濵�� �迭</option>
						</select><br>
						<input type="text" id="hakgha" name="PROFILE_EDU" value="${edu[19] }">
						����������������������������<br>
						<input type="checkbox" id="check1" name="PROFILE_EDU" value="��������"> ������(��������)<br><br>
					</center></td>
					<td>
						<input type="text" id="year" name="PROFILE_EDU" value="${edu[20] }"><br>
						/<br>
						<input type="text" id="year" name="PROFILE_EDU" value="${edu[21] }">
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
						<select style="color: gray;" name="PROFILE_CARRER">
							<option>���</option>
							<option>������</option>
						</select><br><br>
						<input type="text"  id="year" name="PROFILE_CARRER" value="${carrer[1] }"/> . <input type="text" id="month" name="PROFILE_CARRER" value="${carrer[2] }"> ~ <input type="text"  id="year" name="PROFILE_CARRER" value="${carrer[3] }"/> . <input type="text" id="month" name="PROFILE_CARRER" value="${carrer[4] }"><br>
						
					</td>
					<th width="70px">ȸ���</th>
					<td colspan="3" align="left"><input type="text" style="width: 250px; " name="PROFILE_CARRER" value="${carrer[5] }"></td>
				</tr>
				<tr>
					<th>����</th>
					<td colspan="3" align="left"><input type="text" style="width: 250px; " name="PROFILE_CARRER" value="${carrer[6] }"></td>
				</tr>
				<tr>
					<th>�ٹ��μ�</th>
					<td align="left"><input type="text" style="width: 100px; " name="PROFILE_CARRER" value="${carrer[7] }"></td>
					<th width="70px">�ٹ�����</th>
					<td  align="left">
						<select style="width: 80px; color: gray;" name="PROFILE_CARRER">
							<option>����</option>
							<option>�뱸</option>
							<option>����</option>
							<option>�λ�</option>
							<option>����</option>
						</select>
					</td>
				</tr>
				<tr>
					<th>����</th>
					<td align="left">
						<select style="width: 100px; color: gray;" name="PROFILE_CARRER">
							<option>����</option>
							<option>���</option>
							<option>����</option>
							<option>����</option>
							<option>�븮</option>
							<option>����</option>
							<option>����</option>
							<option>����</option>
						</select><br>
						<input type="text" style="width: 20px; margin-top: 5px;" name="PROFILE_CARRER" value="${carrer[10] }"> ����
					</td>
					<th>����</th>
					<td align="left"><input type="text" style="width: 60px;" name="PROFILE_CARRER" value="${carrer[11] }">
						<select style="color: gray;" name="PROFILE_CARRER">
							<option>����</option>
							<option>�޷�</option>
							<option>��</option>
						</select>
					</td>
				</tr>
				<tr>
					<th>������</th>
					<td colspan="3"><textarea rows="5" cols="45" name="PROFILE_CARRER">${carrer[13]}</textarea></td>
				</tr>
				<tr>
					<th>������</th>
					<td colspan="3" align="left">
						<select style="color: gray; width: 130px;" name="PROFILE_CARRER">
							<option>������ ��ȯ</option>
							<option>�ٹ�����</option>
							<option>�濵��ȭ</option>
							<option>��ุ��</option>
							<option>�о�</option>
						</select>
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
						<select style="width:200px;" name="PROFILE_INTEREST">
							<option>����ȹ</option>
							<option>��������</option>
							<option>�����α׷���</option>
							<option>�������α׷���</option>
							<option>�ý������α׷���</option>
							<option>ERP.�ý��ۺм�.����</option>
							<option>����.��Ʈ��ũ.����</option>
							<option>�����ͺ��̽�.DBA</option>
							<option>��ǥ��.UI����</option>
							<option>������.����Ʈ�</option>
						</select>&nbsp;&nbsp;&nbsp;&nbsp;
						�� ��� ��� : 
						<select style="width:120px" name="PROFILE_INTEREST">
							<option>C</option>
							<option>C++</option>
							<option>C#</option>
							<option>java</option>
							<option>Python</option>
							<option>Ruby</option>
							<option>node.js</option>
							<option>PHP</option>
						</select>
					</td>
				</tr>
			</table>
		</div>
		
		<div id="local">
			<span id="location-4"></span>��� ��� ����
			<table>
				<tr>
					<th width="100px">����</th>
					<td>
						<table id="local-table">
							<tr>
								<td align="left"><input type="checkbox" name="PROFILE_LOCAL" value="������ü" checked>������ü</td>
								<td><input type="checkbox" name="PROFILE_LOCAL" value="������">������</td>
								<td><input type="checkbox" name="PROFILE_LOCAL" value="������">������</td>
								<td><input type="checkbox" name="PROFILE_LOCAL" value="������">������</td>
								<td><input type="checkbox" name="PROFILE_LOCAL" value="��õ��">��õ��</td>
							</tr>
							<tr>
								<td><input type="checkbox" name="PROFILE_LOCAL" value="��õ��">�缭��</td>
								<td><input type="checkbox" name="PROFILE_LOCAL" value="�����">�����</td>
								<td><input type="checkbox" name="PROFILE_LOCAL" value="������">������</td>
								<td><input type="checkbox" name="PROFILE_LOCAL" value="���빮��">���빮��</td>
								<td></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<th>���</th>
					<td>
						<table id="local-table">
							<tr>
								<td align="left"><input type="checkbox" name="PROFILE_LOCAL" value="�����ü">�����ü</td>
								<td><input type="checkbox" name="PROFILE_LOCAL" value="����">����</td>
								<td><input type="checkbox" name="PROFILE_LOCAL" value="�����">�����</td>
								<td><input type="checkbox" name="PROFILE_LOCAL" value="���ֽ�">���ֽ�</td>
								<td><input type="checkbox" name="PROFILE_LOCAL" value="������">������</td>
							</tr>
							<tr>
								<td><input type="checkbox" name="PROFILE_LOCAL" value="��õ��">��õ��</td>
								<td><input type="checkbox" name="PROFILE_LOCAL" value="������">������</td>
								<td><input type="checkbox" name="PROFILE_LOCAL" value="�Ⱦ��">�Ⱦ��</td>
								<td><input type="checkbox" name="PROFILE_LOCAL" value="�ǿս�">�ǿս�</td>
								<td><input type="checkbox" name="PROFILE_LOCAL" value="�����">�����</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<th>�λ�</th>
					<td>
						<table id="local-table">
							<tr>
								<td align="left"><input type="checkbox" name="PROFILE_LOCAL" value="�λ���ü">�λ���ü</td>
								<td><input type="checkbox" name="PROFILE_LOCAL" value="������">������</td>
								<td><input type="checkbox" name="PROFILE_LOCAL" value="������">������</td>
								<td><input type="checkbox" name="PROFILE_LOCAL" value="���屺">���屺</td>
								<td><input type="checkbox" name="PROFILE_LOCAL" value="����">����</td>
							</tr>
							<tr>
								<td><input type="checkbox" name="PROFILE_LOCAL" value="����">����</td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
						</table>
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
					<td><input type="text" name="PROFILE_LISENCE" value="${lisence[0] }"></td>
					<td><input type="text" name="PROFILE_LISENCE" value="${lisence[1] }"></td>
					<td>
						<select name="PROFILE_LISENCE">
							<option>1���հ�</option>
							<option>2���հ�</option>
							<option>�Ǳ��հ�</option>
							<option>�ʱ��հ�</option>
							<option>�����հ�</option>
						</select>
					</td>
					<td>
						<input type="text"  id="year" name="PROFILE_LISENCE" value="${lisence[3] }"/> . <input type="text" id="month" name="PROFILE_LISENCE" value="${lisence[4] }"> 
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
					<td>
						<select name="PROFILE_LANGU">
							<option>����</option>
							<option>�߱���</option>
							<option>�Ͼ�</option>
							<option>���Ͼ�</option>
						</select>
					</td>
					<td><input type="text" name="PROFILE_LANGU" value="${langu[1] }"></td>
					<td>
						
						<input type="text" style="width: 35px;" name="PROFILE_LANGU" value="${langu[2] }"> �� 
						<input type="text" style="width: 25px;" name="PROFILE_LANGU" value="${langu[3] }"> ��
					</td>
					<td>
						<input type="text"  id="year" name="PROFILE_LANGU" value="${langu[4] }"/> . <input type="text" id="month" name="PROFILE_LANGU" value="${langu[5] }"> 
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
						<textarea cols="80" rows="6" name="PROFILE_INTRO">${prfbean.PROFILE_INTRO}</textarea>
					</td>
				</tr>
			</table>
		</div>
		<div id="enter" align="center">
			<input type="submit" value="������ ����" id="submit">
		</div>
	</div>
	<div class="spacer"></div>
</form>
</div>
</div>
</body>
</html>