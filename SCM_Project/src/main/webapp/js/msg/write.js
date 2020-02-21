function check(){
			if($("#MESSAGE_REC").val()==""){
				alert("받는이을 입력 하세요?");
				$("#MESSAGE_REC").focus();
				return false;
			}
			
			if($("#MESSAGE_SUBJECT").val()==""){
				alert("제목을 입력 하세요?");
				$("#MESSAGE_SUBJECT").focus();
				return false;
			}
			if($("#MESSAGE_BODY").val()==""){
				alert("내용을 입력 하세요?");
				$("#board_BODY").focus();
				return false;
			}				
	}
function readURL(input){
	var reader = new FileReader();
	var reader1 = new FileReader();
	var reader2 = new FileReader();
	var reader3 = new FileReader();
	reader.onload=function(e){
		$('#UploadedImg').attr('src',e.target.result);
	}
		reader1.onload=function(e){
			$('#UploadedImg1').attr('src',e.target.result);

		}
			reader2.onload=function(e){
				$('#UploadedImg2').attr('src',e.target.result);
			}
				reader3.onload=function(e){
		$('#UploadedImg3').attr('src',e.target.result);
	}
	reader.readAsDataURL(input.files[0]);
	reader1.readAsDataURL(input.files[1]);
	reader2.readAsDataURL(input.files[2]);
	reader3.readAsDataURL(input.files[3]);
	$("#UploadedImg").show();
	$("#UploadedImg1").show();
	$("#UploadedImg2").show();
	$("#UploadedImg3").show();
}