function checkDelete(biz_num, page){
	var boardForm = $("#boardForm").val();
	if(confirm("삭제하시겠습니까?")){
		location.href="biz_Delete.biz?biz_num="+biz_num+"&page="+page+"&boardForm="+boardForm;
		alert("삭제되었습니다.");
	} else{
		return;
	}
}

function favoriteOn(biz_num, page){
	var boardForm = $("#boardForm").val();
	$.ajax({
		type:"POST",
		url:"biz_contFavorite_on.biz",
		cache:false,
		data: {"biz_num" : biz_num, "page" : page, "boardForm" : boardForm},
		success: function (data) { 
			location.href="biz_getCont.biz?biz_num="+biz_num+"&page="+page+"&state=cont&boardForm="+boardForm;
		},
		error: function(){
			alert("data error");
		}
	});
}

function favoriteOff(biz_num, page){
	var boardForm = $("#boardForm").val();
	$.ajax({
		type:"POST",
		url:"biz_contFavorite_off.biz",
		cache:false,
		data: {"biz_num" : biz_num, "page" : page, "boardForm" : boardForm},
		success: function (data) { 
			location.href="biz_getCont.biz?biz_num="+biz_num+"&page="+page+"&state=cont&boardForm="+boardForm;
		},
		error: function(){
			alert("data error");
		}
	});
}

function checkApply(biz_num, page){
	// 마감일 확인
	var id = $("#id").val();
	$.ajax({
		type:"POST",
		url:"apply_datecheck.biz",
		chche:false,
		data: {"biz_num" : biz_num},
		success: function (data){
			if(data==1){
				alert("마감일이 지난 채용공고입니다.");
				return false;
			} else{
				// 지원자 중복확인
				$.ajax({
					type:"POST",
					url:"apply_idcheck.biz",
					cache:false,
					data: {"id" : id, "biz_num" : biz_num, "page" : page},
					success: function (data) { 
						var boardForm = $("#boardForm").val();
						if(data==1){// applicant DB에 중복되는 아이디가 있으면
							alert("이미 지원한 채용공고입니다.");
							return false;
				     
						}else{//중복아이디가 없으면
							if(confirm("지원하시겠습니까?")){
								location.href="biz_apply.biz?biz_num="+biz_num+"&page="+page+"&boardForm="+boardForm;
							} else{
								return;
							}
						}
					}
					,
					error: function(){
						alert("data error" + id);
					}
				});
			}
		},
		error: function(){
			alert("data error" + data);
		}
	});
}

function returnToList(page){
	var boardForm = $("#boardForm").val();
	if(boardForm=="myBoard"){
		location.href="biz_list_mine.biz?boardForm=myBoard&page="+page;
	} else if(boardForm=="favoriteBoard"){
		location.href="biz_list_favorite.biz?boardForm=favoriteBoard&page="+page;
	} else if(boardForm=="searchBoard"){
		location.href="biz_list_search.biz?biz_search_select=bizName&boardForm=searchBoard&biz_search=1&search_btn=검색&page="+page;
	} else {
		location.href="biz_list.biz?boardForm=all&page="+page;
	}
}
