$(document).ready(function(){
		
	//js파일을 따로 넣으면 el태그를 불러 올 수 없기 때문에 태그에 넣은 값을 불러 온다.
	var state = $("#state").val();
	var page = $("#page").val();
	var qna_num = $("#qna_num").val();
	
	

	//사이드메뉴 길이 지정
	var sideHeight = null;
	
	
	
	
	/* 처음 시작시 댓글 영역 로드 시키기 위해 아작스를 함수 처리함 */
	function replyReady(){
		$.ajax({
			url:"/SCM_Project/qna_reply_view.qna",
			type: "post",
			data: {"qna_num":qna_num },
			success: function(data){
				$(".qna_detail_reply_area").empty();
				$(".qna_detail_reply_area").append(data);
				$("#qna_reply_count").empty();
				$("#qna_reply_count").append($("#qna_re_count").val());
				
				
				
				sideHeight = $(".qna_list_content").height();
				if(sideHeight > 910){
					$(".qna_list_side").css("height", sideHeight);
				}
				
			},
			error: function(data){
				alert("에러" + data.val());
			}
		})//페이지 로드 될때 아작스 처리
		
	}
	replyReady();
	
	//리플 등록 버튼 처리하기
	$("body").on("click","#reply_button", function(event) { 
					
		var qna_re_body = $(".qna_detail_reply_write_text").val()
		if($.trim(qna_re_body) == ""){
			return false;
		}
		
		$.ajax({
			url:"/SCM_Project/qna_reply_ok.qna",
			type: "post",
			data: {"qna_num":qna_num, "qna_re_body": qna_re_body },
			success: function(data){
				replyReady();
			},
			error: function(data){
				alert("에러" + data.val());
			}
		})//아작스 end
	});//on end	
	
	
	
	//글 삭제 눌렀을 때 배경 검정으로 만들고 팝업창 띄우기
	function wrapWindowByMask(){
		//화면의 높이와 너비를 구한다.
		var maskHeight = $(document).height();  
		var maskWidth = $(window).width();  

		//마스크의 높이와 너비를 화면 것으로 만들어 전체 화면을 채운다.
		$('#qna_mask').css({'width':maskWidth,'height':maskHeight});  
		
		//애니메이션 효과 - 일단 0.5초동안 까맣게 됐다가 80% 불투명도로 간다.
		//$('#qna_mask').fadeIn(500);      
		$('#qna_mask').fadeTo("fast",0.8);    

		//윈도우 같은 거 띄운다.
		$('.qna_delete_modal1').show();
	}
	//댓글 삭제 눌렀을 때 배경 검정으로 만들고 팝업창 띄우기
	function wrapWindowByMask2(){
		//화면의 높이와 너비를 구한다.
		var maskHeight = $(document).height();  
		var maskWidth = $(window).width();  

		//마스크의 높이와 너비를 화면 것으로 만들어 전체 화면을 채운다.
		$('#qna_mask').css({'width':maskWidth,'height':maskHeight});  
		
		//애니메이션 효과 - 일단 0.5초동안 까맣게 됐다가 80% 불투명도로 간다.
		//$('#qna_mask').fadeIn(500);      
		$('#qna_mask').fadeTo("fast",0.8);    

		//윈도우 같은 거 띄운다.
		$('.qna_delete_modal2').show();
	}
	
	
	$(".qna_list").click(function(){
		location.href="qna_list.qna?page="+page+"&state="+state;
	});//목록 보기 클릭 end
	
	$("#qna_prev").click(function(){
		history.go(-1)
	});//
	
	//모달 창 띄우기 화면
	$(".qna_delete").click(function(e){
		e.preventDefault();
		wrapWindowByMask();
	});//글 삭제 버튼 end
	
	//검은 막을 눌렀을 때
	$('#qna_mask').click(function () {  
	    $(this).hide();  
	    $('.qna_delete_modal1').hide();  
	    $('.qna_delete_modal2').hide(); 
	});
	
	/*팝업창에 '아니오'버튼을 눌렀을때*/
	$(".qna_reply_cancle").click(function(){
	    $('#qna_mask, .qna_delete_modal1, .qna_delete_modal2').hide(); 
	})
	
	/*팝업창에서 글 삭제를 눌렀을 때*/
	$(".qna_delete_detail").click(function(){
		location.href="qna_delete_ok.qna?page="+page+"&state="+state+"&qna_num="+qna_num;
	});
	
	//load 된 qna_reply.jsp에서 값을 읽어 오기 위해 변수를 선언한다.
	var qna_re_ref = null;
	var qna_re_seq = null;
	var qna_re_body = null;
	//댓글 삭제 버튼을 눌렀을 때
	$("body").on("click", "#qna_reply_delete2", function(envent){
		
		var str = $(this).attr('class');
		var strArr = str.split("_");
		
		qna_re_ref = strArr[0];
		qna_re_seq = strArr[1];
		wrapWindowByMask2();
	});
	
	//리플삭제에서 '예'버튼을 눌렀을 때
	$("#qna_reply_delete").click(function(){
		$('#qna_mask, .qna_delete_modal1, .qna_delete_modal2').hide();
		$.ajax({
			url:"/SCM_Project/qna_reply_del_ok.qna",
			type : "post",
			data : {"qna_re_ref": qna_re_ref, "qna_num": qna_num,
				"qna_re_seq": qna_re_seq},
			success : function(data){
				replyReady();
			},
			error : function(){
				
				alert("리플 삭제 에러 = " +qna_re_seq);
			}
		})//아작스 end
	});
	
	
	
	//리플 답글 버튼을 눌렀을 때
	//클릭된 상태를 나타내는 변수
	var toggleNum = 0;
	//방금전에 클릭한 버튼을 가리키는 변수
	var clickButton = $('button');
	$("body").on("click", "#qna_reply_reply", function(event){

		var str = $(this).attr('class');
		var strArr = str.split("_");
		
		qna_re_ref = strArr[0];
		qna_re_seq = strArr[1];
		
		
		$(".qna_reply_append_box").empty();
		
		var inputbox = "<input type='text' class='qna_re_re_text'>" +
				"<input type='button' value='등록' id='qna_re_re_button' class="+qna_re_ref+">";

		//다른 버튼을 클릭했다면 그전에 버튼은 원 상태로 돌려 놓는다.
		clickButton.empty();
		clickButton.append("<span style='font-size: 8pt; color: #5781b5'>└답글</span>");
		
		
		if(toggleNum ==0){
			$(this).empty();
			$(this).append("<span style='color:red; font-size: 8pt'>답글 취소</span>");
			$("[id="+qna_re_ref+qna_re_seq+"]").append(inputbox);
			toggleNum = 1;
		}else if(toggleNum != 0){
			$(this).empty();
			$(this).append("<span style='font-size: 8pt; color: #5781b5'>└답글</span>");
			$("[id="+qna_re_ref+qna_re_seq+"]").empty();
			toggleNum =0;
		}
		
		clickButton = $(this)
	})//qna_reply_reply end
	
	
	
	//댓글의 댓글을 클릭하고 글을 작성한 후 등록 버튼을 눌렀을 때
	$("body").on("click", "#qna_re_re_button", function(event){
		//댓글의 댓글 내용을 담는 변수
		var qna_re_body = $(".qna_re_re_text").val();
		var qna_re_lev = 1;
		//댓글의 번호도 같이 넣어준다.
		qna_re_ref = $(this).attr('class');
		
		
		$.ajax({
			url:"/SCM_Project/qna_reply_ok.qna",
			type: "post",
			data: {"qna_num":qna_num, "qna_re_body": qna_re_body, 
					"qna_re_lev": qna_re_lev, "qna_re_ref": qna_re_ref },
			success: function(data){
				replyReady();
			},
			error: function(data){
				alert("에러" + data.val());
			}
		})//아작스 end
		
	});//댓글의 댓글 버튼 end
	
	
	/*댓글 수정 버튼을 눌렀을 때*/
	//'수정'버튼 클릭한 상태를 나타내는 변수
	var editToggle = 0;
	$("body").on("click", "#qna_reply_edit", function(event){
		var str = $(this).attr('class');
		var strArr = str.split("_");
		
		qna_re_ref = strArr[0];
		qna_re_seq = strArr[1];
		qna_re_body = strArr[2];
		
		
		var inputbox = "<input type='text' class='qna_re_re_text' value='"+qna_re_body+"'>" +
		"<input type='button' value='등록' id='qna_re_edit_button' class="+qna_re_ref+">";
		
		if(editToggle == 0){
			//답글 버튼 제거-수정 중에 댓글 중복으로 못쓰게 하기
			$("."+qna_re_ref).empty();
			//댓글 나오는 곳을 비우고 인풋상자를 넣는다.
			$("#qna_re_body"+qna_re_ref+qna_re_seq).empty();
			$("#qna_re_body"+qna_re_ref+qna_re_seq).append(inputbox);
			//버튼을 변경한다.'수정' -> '수정취소'
			$(this).empty();
			$(this).append("<span style='color:red; font-size:9pt'>수정 취소</span>");
			editToggle =1;
		}else{
			$("."+qna_re_ref).append("└답글");
			$("#qna_re_body"+qna_re_ref+qna_re_seq).empty();
			$("#qna_re_body"+qna_re_ref+qna_re_seq).append(qna_re_body);
			$(this).empty();
			$(this).append("수정");
			editToggle =0;
		}
		
	});//댓글 수정 버튼을 눌렀을 때
	
	
	
	
	
	
});//document end