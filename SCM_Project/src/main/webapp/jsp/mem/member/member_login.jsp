<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>로그인</title>
<link rel="stylesheet" type="text/css" href="./css/mem/login.css" />
<script src="./js/mem/jquery-1.12.0.js"></script>
<script src="./js/mem/member.js"></script>
</head>
<body>

<!-- Form-->
<div class="form">
  <div class="form-toggle"></div>
  <div class="form-panel one">
    <div class="form-header">
      <h1>로그인</h1>
    </div>
    <div class="form-content">
      <form method="post" action="member_login_ok.nhn"
           onsubmit="return check()">
        <div class="form-group">
          <label for="username">아이디</label>
          <input  id="id" name="id" />
        </div>
        <div class="form-group">
          <label for="password">비밀번호</label>
          <input type="password" id="pwd" name="pwd" />
        </div>
        <div class="form-group">
          <label class="form-remember">
           <!-- <input type="button" value="비번찾기" class="form-recovery"
             onclick="pwd_find()" /> -->
          </label>
          <a onclick="pwd_find()" class="form-recovery">비밀번호찾기</a>
        </div>
        <div class="form-group">
          <button type="submit">로그인</button>
        </div>
      </form>
    </div>
  </div>
  <div class="form-panel two">
    <div class="form-header">
      <h2>회원가입</h2>
    </div>
    <div class="form-content">
      <form name="f"   method="post"  
            action="member_join_ok.nhn"  
             onsubmit="return join_check()" enctype="multipart/form-data">
        <div class="form-group">
          <label for="username">이름</label>
          <input  id="join_name" name="join_name"/>
        </div>
        <div class="form-group">
          <label for="id" style="width: 100%;">아이디</label>
          <input id="join_id" name="join_id" required="required" style="
    width: 65%;">
       
       <a type="button" class="input_button" onclick="id_check()" style="
    vertical-align: -webkit-center;
    padding-top: 10px;
    font-size: inherit;
    font-weight: 500;
    color: #4285F4;
     width: 28%;
    padding-left: 15px;">아이디 중복체크</a>
      <div id="idcheck"></div>
        </div>
        <div class="form-group">
          <label for="password">비밀번호</label>
          <input type="password" id="join_pwd1" name="join_pwd1"/>
        </div>
        <div class="form-group">
          <label for="cpassword">비밀번호 확인</label>
          <input type="password" id="join_pwd2" name="join_pwd2" />
        </div>
        
        <div class="form-group">
          <label for="email">이메일</label>
          <input type="email" id="join_email" name="join_email" />
        </div>
        <div class="form-group">
          <button type="submit">회원가입</button>
        </div>
      </form>
    </div>
  </div>
</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script> 
<script type="text/javascript">
$(document).ready(function() {
   var panelOne = $('.form-panel.two').height(),
     panelTwo = $('.form-panel.two')[0].scrollHeight;

   $('.form-panel.two').not('.form-panel.two.active').on('click', function(e) {
    // e.preventDefault();

     $('.form-toggle').addClass('visible');
     $('.form-panel.one').addClass('hidden');
     $('.form-panel.two').addClass('active');
     $('.form').animate({
       'height': panelTwo
     }, 200);
   });

   $('.form-toggle').on('click', function(e) {
     e.preventDefault();
     $(this).removeClass('visible');
     $('.form-panel.one').removeClass('hidden');
     $('.form-panel.two').removeClass('active');
     $('.form').animate({
       'height': panelOne
     }, 200);
   });
});
 
</script>
</body>
</html>
