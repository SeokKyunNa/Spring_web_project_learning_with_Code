$(document).ready(function() {
	/* 주소 입력 부분 */
	$('#biz_loc_2').focus(function(){	// biz_loc_2에 포커스가 잡히면 실행
			$(this).val()=='나머지 주소를 입력하세요' ? 	// 값이 '나머지 주소를 입력하세요'와 같으면 참, 다르면 거짓
					$(this).val('') : // 같으면(참) 공백 출력
					$(this).val($(this).val()).css("color", "black");	// 다르면(거짓) 입력된 문장 그대로 출력
		})			.blur(function(){
			$(this).val()=='' ?
					$(this).val('나머지 주소를 입력하세요').css("color", "gray") :
					$(this).val($(this).val()).css("color", "black");
		});

	function post_search(){
		alert("우편번호 검색 버튼을 클릭하세요!");
	};

	function post_check(){
		window.open("zipcode_find.nhn","우편번호검색", "width=420,height=200,scrollbars=yes");
		//폭이 420이고 높이가 200,스크롤바가 생성되는 새로운 공지창을 만듬
	};
	/* 주소 입력 부분  끝 */
	
	$('#biz_pattern').attr("readonly", true);	// 처음 화면 input box readonly
	$("#biz_pattern_select").change(function(){
		if($('#biz_pattern_select option:selected').val() == "직접 입력"){
			$('#biz_pattern').attr("readonly",false);
			$('#biz_pattern').val('');
			$('#biz_pattern').css('background-color', 'white');
			
		} else if($('#biz_pattern_select option:selected').val() == "선택하세요"){
			$('#biz_pattern').attr("readonly",true);
			$('#biz_pattern').val('');
			$('#biz_pattern').css('background-color', 'white');
			
		} else {
			$('#biz_pattern').attr("readonly",true);
			$('#biz_pattern').val($('#biz_pattern_select option:selected').val());
			$('#biz_pattern').css('background-color', '#D8D8D8');
		}
    });	// biz_pattern change 끝
	
	$('#biz_career').attr("readonly", true);	// 처음 화면 input box readonly
	$("#biz_career_select").change(function(){
		if($('#biz_career_select option:selected').val() == "경력(희망경력 입력)"){
			$('#biz_career').attr("readonly",false);
			$('#biz_career').val('');
			$('#biz_career').css('background-color', 'white');
			
		} else if($('#biz_career_select option:selected').val() == "선택하세요"){
			$('#biz_career').attr("readonly",true);
			$('#biz_career').val('');
			$('#biz_career').css('background-color', 'white');
			
		} else {
			$('#biz_career').attr("readonly",true);
			$('#biz_career').val($('#biz_career_select option:selected').val());
			$('#biz_career').css('background-color', '#D8D8D8');
		}
    });	// biz_career chagne 끝
	
	
	$("form").submit(function() {
		if ($("#biz_name").val() == "") {
			alert("회사명을 입력 하세요");
			$("#biz_name").focus();
			return false;
		}
		if ($("#biz_loc_1").val() == "") {
			alert("근무지역을 입력 하세요");
			$("#biz_zipcode_btn").focus();
			return false;
		}
		if ($("#biz_loc_2").val() == "나머지 주소를 입력하세요") {
			alert("상세한 근무지역을 입력 하세요");
			$("#biz_loc_2").focus();
			return false;
		}
		if ($("#biz_pattern").val() == "") {
			alert("고용형태를 선택하세요");
			$("#biz_pattern_select").focus();
			return false;
		}
		if ($("#biz_occ").val() == "") {
			alert("직종을 입력 하세요");
			$("#biz_occ").focus();
			return false;
		}
		if ($("#biz_position").val() == "") {
			alert("직급을 입력 하세요");
			$("#biz_position").focus();
			return false;
		}
		if ($("#biz_career").val() == "") {
			alert("희망경력을 선택하세요");
			$("#biz_career_select").focus();
			return false;
		}
		if ($("#biz_pay").val() == "") {
			alert("급여를 입력 하세요");
			$("#biz_pay").focus();
			return false;
		}
		if ($("#biz_education").val() == "") {
			alert("직종을 입력 하세요");
			$("#biz_education").focus();
			return false;
		}
		if ($("#biz_expiry_date").val() == "") {
			alert("접수기한을 선택하세요");
			$("#biz_expiry_date").focus();
			return false;
		}
		if ($("#biz_subject").val() == "") {
			alert("제목을 입력 하세요");
			$("#biz_subject").focus();
			return false;
		}
		if ($("#biz_content").val() == "") {
			alert("내용을 입력 하세요");
			$("#biz_content").focus();
			return false;
		}
		
		if(confirm("수정하시겠습니까?")){
			alert("수정되었습니다.");
		} else{
			return false;
		}
	});	// 유효성 검사 끝
	
}); // ready end
