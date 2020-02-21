$(document).ready(function(){

	//사이드 메뉴 길이 지정
	$(".qna_list_side").css("height", $(".qna_list_content").height());
	
	
	
	//미리보기 버튼을 눌렀을때 불러올 페이지
	function codeView(codetext){
		$.ajax({
			url:"./jsp/qna/qna_write_view.jsp",
			type: "post",
			data: {"codetext": codetext},
			success: function(data){
				$("#qna_code_area").empty();
				$("#qna_code_area").append(data);
				
			}
		})//ajax end
	}
	
	//클릭한 상태를 나타내기 위한 변수
	var clickNum = 0;
	//아작스로 보내준 내용을 담아 놓기 위한 변수
	var codeWrite = null;
	$(".qna_write_view").click(function(){
		//textarea에 쓴 글을 변수에 저장한다.
		var codetext = $("#qna_code_text").val();
		
		if(clickNum ==0 ){
			//$("#code_view_hilight").show();
			codeView(codetext)
			codeWrite = codetext;
			clickNum =1;
			$(this).val("코드작성")
		}else{
			//$("#code_view_hilight").hide();
			$("#qna_code_area").empty();
			$("#qna_code_area").append("<textarea rows='20' cols='75' name='qna_code' id='qna_code_text'>"+codeWrite+"</textarea>");
			clickNum =0;
			$(this).val("미리보기")
		}
	})//click end
	
	
	//탭을 눌렀을 때 들여쓰기 되는 것
	$("#qna_code_text").keydown(function(){
	 var e = window.event; 
	  if (e.keyCode == 9) // tab 
	  { 
		  $(this).focus();
		  e.srcElement.value = e.srcElement.value + "\t"; 

		  return false;
	  } 
	})
	$("#qna_code_text").keyup(function(){
		$(this).focus();
	})
	
	
	
	//사진 업로드 시 미리보기 화면 생성
	//추가한 파일의 갯수를 세기위한 변수
	var filenum = 1;
	$("body").on("change","input[type='file']", function(){
		
		//img 태그를 생성하고 업로드할 파일을 미리보기 한다.
		//$(".qna_write_file_preview").append("<div id='qna_img_div"+filenum+"' class='qna_img_div'><img id='qna_file_view"+filenum+"' src='#' class='qna_file_img'/></div>");
				
		//$("#qna_img_div"+filenum).append("<img src='./images/qna/close.png' id='qna_img_Close"+filenum+"' class='qna_img_Close' style='width: 15px;height: 15px;cursor : pointer;margin-left:-25px;margin-bottom:75px'/>");
		
		//readURL(this, filenum)
		
		//현제 선택된 input상자는 가리고 새로운 input 상자를 만든다.
		//$("#qna_file"+filenum).hide();
		
		formData.append("qna_body_img", $(".qna_file")[0].files[0])
		qnaBody()
		
		
		//input 상자에 index를 올려준다.
		//filenum = filenum+1;
				
		//$("#qna_file_input_area").append("<input type='file' name='qna_file"+filenum+"' id='qna_file"+filenum+"' class='qna_file'/>");
		//$("#qna_file_cancle").append("<div><a>x</a>"+this.value+"<div>");
		
		
	});
	
	
	//이미지 첨부시 미리보기 화면에 사용되는 함수
	function readURL(input, num){
		if(input.files && input.files[0]){
			
			var reader = new FileReader();
			reader.onload = function(e){
				$("#qna_file_view"+num).attr('src', e.target.result);
			}
			reader.readAsDataURL(input.files[0]);
		}
	}
	
	//현제 이미지에 ID 인덱스 번호를 얻기 위한 변수
	var imgnum = 0;
	$("body").on("mouseover", ".qna_img_div", function(){
		//현제 이미지에 ID 인덱스 번호를 얻기 위한 변수
		var imgId = $(this).attr("id");
		imgnum = imgId.substring(11, imgId.length)		
	})//이미지에 마우스를 올렸을 때 끝
		
	$("body").on("click", ".qna_img_Close", function(){
		$("#qna_img_div"+imgnum).remove();
		$("#qna_file"+imgnum).remove();
	})//이미지에 마우스가 벗어 났을 때

	
	
	////////////////////////////////////////////////////////
	////글쓰기 화면에 첨부파일 이미지가 들어오고 그 이미지 크기 조절가능하게////
	////////////////////////////////////////////////////////
	//ajax로 넘겨줄 값을 가져온다.
	var formData = new FormData();
	formData.encoding = 'multipart/form-data';
	formData.append("qna_body_img", $("#qna_file1")[0].files[0])
	
	//파일선택시 불러오는 ajax 함수
	function qnaBody(){
		$.ajax({
			url:"/SCM_Project/qna_write_body_ok.qna",
			type: "post",
			contentType : false,
	        processData : false,
			data: formData,
			success: function(data){
				//$("#qna_write_body").empty();
				$("#qna_write_body_textarea").append(data);
			} 
		})//ajax end
	}
	
	
	
	var start_x = 0;
	var start_y = 0;
	
	//현제 객체에 넓이와 높이
	var imgwidth = 0;
	var imgheight = 0;
	
	//마우스를 떼기 전에 나오는 높이와 넓이
	var endwidth = 0;
	var endheight = 0;
			
	//로드된 이미지를 드레그시 사이즈가 변경된다.
	$("body").on("mousedown", "#qna_write_body_textarea > img", function(e){
		
		start_x = event.x
		start_y = event.y
		
		imgwidth = $(this).width();
		imgheight = $(this).height();
		
		
		$("body").on("drag", "#qna_write_body_textarea > img", function(e){
			
			var end_x = event.x
			var end_y = event.y

			if(end_x !=0 && end_y != 0){
				endwidth = imgwidth+(end_x-start_x)
				endheight = imgheight+(end_y-start_y)
			}
			
						
			$(this).attr("width", endwidth)
			$(this).attr("height", endheight)
							
		})
		
	})
	
	
	///////////////////////////////////////
	//////글자 선택하고 굵기 등을 변경할 수 있음///////
	///////////////////////////////////////
	
	//드래그해서 선택한 글자를 담아두는 변수
	var selectText = ""
	
	$("#qna_write_body_textarea").mouseup(function(){
		selectText = document.getSelection()
		
	})
	/*
	function editorDocument(){
		return $("#qna_write_body_textarea").contentDocument || $("#qna_write_body_textarea").contentWindow.document;
	}
	*/
	//글자 굵기 변경
	$(".qna_editer1").click(function(){
		document.execCommand("bold");
	})//글자 굵게
	
	//글자 밑줄 긋기
	$(".qna_editer2").click(function(){
		document.execCommand("underline");
	})
	
	//글자 기울이기
	$(".qna_editer3").click(function(){
		document.execCommand("Italic");
	})
	
	//글자 가운데 줄 긋기
	$(".qna_editer4").click(function(){
		document.execCommand("StrikeThrough");
	})
	
	//글자 색상 바꾸기
	//클릭한 상태를 나타내는 변수
	var colorBox = 0;
	$(".qna_editer5").click(function(){
		if(colorBox ==0){
			$(".qna_select_color").show()
			colorBox = 1
		}else{
			$(".qna_select_color").hide()
			colorBox = 0
		}
		
		return false;
	})
	
	//글자 색상 바꾸기 색상상자 클릭
	$(".color_box").click(function(){
		//alert($(this).parent().css("background-color"))
		document.execCommand("foreColor",false, $(this).parent().css("background-color"));
		var textSelect = document.getSelection();
		alert(textSelect.text())
		
		
	})
	
	
	//글자 크기 변경
	$("#changeFontSize").change(function(){
		document.execCommand("FontSize",false, $(this).val());
	})
	
	//폰트 스타일 변경
	$("#changeFontName").change(function(){
		document.execCommand("FontName",false,$(this).val())
	})
	
	//글자 색상 바꾸는 색상표 숨겨 놓기
	$(".qna_select_color").hide()
	
	
	
	//글쓰기 버튼을 불렀을 때
	$("form").submit(function(){
		
		$("#qna_body").val($("#qna_write_body_textarea").html())
		
		
		if(clickNum ==1 ){
			alert("코드 작성에 미리보기를 해제하세요")
			return false
		}
		
		
		
	})
	
	
	
	
	
	
	
	
	
	
})//document end