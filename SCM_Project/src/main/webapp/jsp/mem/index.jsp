<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<%
  String id=(String)session.getAttribute("id");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>사용자 메인화면</title>
<script src="./js/mem/jquery-1.12.0.js"></script>
</head>
<body>
 <% if(id==null){ %>
     <script>
      // alert("다시 로그인 해주세요!");
       location="/SCM_Project/member_login.nhn";
     </script>
 <%} else { 
 %>
    <c:if test="${state == 'edit'}"> 
        <script>
          alert("수정완료되었습니다.!"); 
        </script>
    </c:if>	
 <div id="main_wrap">
   <h2 class="main_title">사용자 메인화면</h2>  
   <form method="post" action="./jsp/mem/member/member_logout.jsp"> 
    <table id="main_t">
     <tr>
      <th colspan="2">
       <input type="button" value="정보수정" class="input_button"
        onclick="location='member_edit.nhn'" />
       <input type="button" value="회원탈퇴" class="input_button"
       onclick="location='member_del.nhn'" />
       <input type="submit" value="로그아웃" class="input_button" />     
     </th>
    </tr>
    
    <tr>
     <th>회원이름</th>
     <td>${join_name}님 로그인을 환영합니다</td>
    </tr>
    
   <%--  <c:if test="${state != 'edit'}">   --%>
    <tr>
     <th>프로필사진</th>
     <td>
       <c:if test="${empty join_profile}">
          &nbsp;
       </c:if>
       <c:if test="${!empty join_profile}">
           <img src="./upload${join_profile}" height="100" width="100" />
       </c:if>
     </td>
    </tr>
   <%--  </c:if> --%>
    
   </table>   

   </form>
   
   
 </div>
 <% } 
 %>
 
</body>
</html>