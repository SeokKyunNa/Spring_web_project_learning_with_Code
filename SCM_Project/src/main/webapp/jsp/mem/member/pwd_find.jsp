<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>비번찾기</title>
<link rel="stylesheet" type="text/css" href="./css/mem/pass.css" />
<script src="./js/mem/jquery-1.12.0.js"></script>
<script>
 function check(){
	 if($.trim($("#id").val())==""){
		 alert("아이디를 입력하세요!");
		 $("#id").val("").focus();
		 return false;
	 }
	 if($.trim($("#name").val())==""){
		 alert("회원이름을 입력하세요!");
		 $("#name").val("").focus();
		 return false;
	 }
 }
</script>
</head>
<body>
<div style="
    margin-left: 25px;
">
 
 <c:if test="${empty pwdok}"> 
  <h2>비번찾기</h2>
  <form method="post" action="pwd_find_ok.nhn"
  onsubmit="return check()">  
   <table>
    <tr>
     <th>아이디</th>
     <td><input name="id" id="id" size="14"/></td>
    </tr>
    
    <tr>
     <th>회원이름</th>
     <td><input name="name" id="name" size="14"/></td>
    </tr>
   </table>
<div id="pwd_menu" style="
    margin-left: 50px;
    margin-top : 10px;
">
    <input type="submit" value="찾기" class="button_write2" />
   <input type="reset" value="초기화" class="button_write2" onclick="$('#id').focus();" style="
    padding-left: 5px;">
    <input type="button" value="닫기" class="button_write2"
    onclick="self.close();" />
  </form>
  </c:if>
  
  
  <c:if test="${!empty pwdok}">
    <h2 class="pwd_title2">비번찾기 결과</h2>
    <table id="pwd_t2">
     <tr>
      <th>비밀번호 :</th>
      <td>${pwdok}</td>
     </tr>
    </table>
    <div id="pwd_close2">
    <input type="button" value="닫기" class="button_write2"
    onclick="self.close();" />
    <!-- close()메서드로 공지창을 닫는다. 
         self.close()는 자바스크립트이다. -->
    </div>
  </c:if> 
  
 </div>
</body>
</html>