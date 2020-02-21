
 function join_check(){
	 if($.trim($("#join_id").val())==""){
		 alert("회원아이디를 입력하세요!");
		 $("#join_id").val("").focus();
		 return false;
	 }
	 if($.trim($("#join_pwd1").val())==""){
		 alert("회원비번을 입력하세요!");
		 $("#join_pwd1").val("").focus();
		 return false;
	 }
	 if($.trim($("#join_pwd2").val())==""){
		 alert("회원비번확인을 입력하세요!");
		 $("#join_pwd2").val("").focus();
		 return false;
	 }
	 function chkPass(str){
     	  var reg_pwd = /^.*(?=.{5,8})(?=.*[0-9])(?=.*[a-zA-Z]).*$/;
     	  if(!reg_pwd.test(str)){
     	   return false;
     	  }
     	  return true;
     	 }
     	 if(!chkPass( $.trim($('#join_pwd1').val()))){ 
     	  alert('비밀번호를 확인하세요.(영문,숫자를 혼합하여 5~8자 이내)');    
     	  $('#join_pwd1').val('');
     	  $('#join_pwd2').val('');
     	  $('#join_pwd1').focus(); 
     	  return false;
     	  }
	 if($.trim($("#join_pwd1").val()) != $.trim($("#join_pwd2").val())){
		 //!=같지않다 연산. 비번이 다를 경우
		 alert("비번이 다릅니다!");
		 $("#join_pwd1").val("");
		 $("#join_pwd2").val("");
		 $("#join_pwd1").focus();
		 return false;
	 }
	 if($.trim($("#join_name").val())==""){
		 alert("회원이름을 입력하세요!");
		 $("#join_name").val("").focus();
		 return false;
	 }
	
	 if($.trim($("#join_email").val())==""){
		 alert("메일 주소를 입력하세요!");
		 $("#join_email").val("").focus();
		 return false;
	 }	 	 
 }
 function check(){
	 if($.trim($("#id").val())==""){
		 alert("로그인 아이디를 입력하세요!");
		 $("#id").val("").focus();
		 return false;
	 }
	 if($.trim($("#pwd").val())==""){
		 alert("비밀번호를 입력하세요!");
		 $("#pwd").val("").focus();
		 return false;
	 }
 }

function pwd_find(){
	 window.open("pwd_find.nhn","비번찾기","width=300,height=230,left=100px,top=100px");
	 //자바 스크립트에서 window객체의 open("공지창경로와 파일명","공지창이름","공지창속성")
	 //메서드로 새로운 공지창을 만듬.폭이 300,높이가 300인 새로운 공지창을 만듬.단위는 픽셀
}

/* 아이디 중복 체크*/
function id_check(){
	
	$("#idcheck").hide();//idcheck span 아이디 영역을 숨긴다.
	
	var memid=$("#join_id").val();
	//1.입력글자 길이 체크
	if($.trim($("#join_id").val()).length < 4){
		var newtext='<font color="red">아이디는 4자 이상이어야 합니다.</font>';
		$("#idcheck").text('');
		$("#idcheck").show();
		$("#idcheck").append(newtext);//span 아이디 영역에 경고문자 추가
		$("#join_id").val("").focus();
		return false;
	};
	
	if($.trim($("#join_id").val()).length >12){
		var newtext='<font color="red">아이디는 12자 이하이어야 합니다.</font>';
		$("#idcheck").text('');
		$("#idcheck").show();
		$("#idcheck").append(newtext);//span 아이디 영역에 경고문자 추가
		$("#join_id").val("").focus();
		return false;
	};
	
	//입력아이디 유효성 검사
	if(!(validate_userid(memid))){		
		var newtext='<font color="red">아이디는 영문소문자,숫자,_ 조합만 가능합니다.</font>';
		$("#idcheck").text('');//문자 초기화
		$("#idcheck").show();//span 아이디 영역을 보이게 한다.
		$("#idcheck").append(newtext);
		$("#join_id").val("").focus();
		return false;
	};
	
	
	//아이디 중복확인
    $.ajax({
        type:"POST",
        url:"member_idcheck.nhn",
        cache:false,
        data: {"memid" : memid},
        success: function (data) { 
      	  if(data==1){//중복 아이디가 있으면
      		var newtext='<font color="red">중복 아이디입니다.</font>';
      			$("#idcheck").text('');
        		$("#idcheck").show();
        		$("#idcheck").append(newtext);
          		$("#join_id").val('').focus();
          		return false;
	     
      	  }else{//중복아이디가 없으면
      		var newtext='<font color="blue">사용가능한 아이디입니다.</font>';
      		$("#idcheck").text('');
      		$("#idcheck").show();
      		$("#idcheck").append(newtext);
      		$("#join_pwd1").focus();
      	  }  	    	  
        }
        ,
    	  error:function(){
    		  alert("data error" + memid);
    	  }
      });//$.ajax	
};
/*아이디 중복 체크 끝*/
function validate_userid(memid)
{ /* 
     /^[a-z0-9_]+$/  : 영문 소문자,숫자 ,_ 가능한 정규표현식
     1. /   : 시작과 끝에 붙입니다.
     2. a-z : 영어 소문자 a부터 z까지
     3. 0-9 : 숫자 0부터 9까지
     4.   + :  앞의 문자나 부분식을 1개 이상 찾습니다. 
     5.   $ : 문자열의 끝을 의미합니다. 
     
          예)/^[a-z0-9_-]{3,16}$/
       / : 시작과 끝은 나타납니다.
               문자열의 시작부분을 찾는 ^ 다음에 소문자(a-z)나 숫자(0-9), 언더스코어(_), 하이픈(-)가 
               나올 수 있고 {3, 16}은 앞의 캐릭터들( [a-z0-9_-] )이 최소 3개에서 16개 이하로 
               나와야 하고 문자열의 끝을 의미하는 $가 마지막에 나옵니다
          예) var pattern = /a/;
        pattern.test('abcd'); //true
        pattern.test('bcd');  //false
   */
  var pattern = /^[a-z0-9_]+$/;
  //var pattern= new RegExp(/^[a-z0-9_]+$/);
  var result = pattern.test(memid);
  alert(result);
  return result;
};
