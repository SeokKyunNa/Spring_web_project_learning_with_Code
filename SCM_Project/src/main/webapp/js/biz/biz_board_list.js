$(document).ready(function() {
	
	$("#viewcount").change(function() {
		var boardForm = $("#boardForm").val();
		var scount = $("#viewcount").val();
		var biz_search_select = $("#bizSearchSelected").val();
		var biz_search = $("#bizSearch").val();
		if(boardForm=="myBoard"){
			$.ajax({
				type : "POST",
				url : "biz_list_mine.biz",
				data : {
					"limit" : scount,
					"boardForm" : boardForm
				},
				cache : false,
				headers : {
					"cache-control" : "no-cache",
					"pragma" : "no-cache"
				},
				async : true, // 동기식 = false, 비동기식 = true(기본값)
				success : function(data) { // 아작스 전송 성공시
					$("body").html(data);
				},
				complete : function() {// 통신에 실패 했어도 완료가 되었을 때 이 함수를 실행합니다.
				},
				error : function() {
					alert("data error");
				}
			});// $.ajax
		} else if(boardForm=="favoriteBoard"){
			$.ajax({
				type : "POST",
				url : "biz_list_favorite.biz",
				data : {
					"limit" : scount,
					"boardForm" : boardForm
				},
				cache : false,
				headers : {
					"cache-control" : "no-cache",
					"pragma" : "no-cache"
				},
				async : true, // 동기식 = false, 비동기식 = true(기본값)
				success : function(data) { // 아작스 전송 성공시
					$("body").html(data);
				},
				complete : function() {// 통신에 실패 했어도 완료가 되었을 때 이 함수를 실행합니다.
				},
				error : function() {
					alert("data error");
				}
			});// $.ajax
		} else if(boardForm=="searchBoard"){
			$.ajax({
				type : "POST",
				url : "biz_list_search.biz",
				data : {
					"limit" : scount,
					"boardForm" : boardForm,
					"biz_search_select" :biz_search_select,
					"biz_search" : biz_search
				},
				cache : false,
				headers : {
					"cache-control" : "no-cache",
					"pragma" : "no-cache"
				},
				async : true, // 동기식 = false, 비동기식 = true(기본값)
				success : function(data) { // 아작스 전송 성공시
					$("body").html(data);
				},
				complete : function() {// 통신에 실패 했어도 완료가 되었을 때 이 함수를 실행합니다.
				},
				error : function() {
					alert("data error");
				}
			});// $.ajax
		} else {
			$.ajax({
				type : "POST",
				url : "biz_list.biz",
				data : {
					"limit" : scount,
					"boardForm" : boardForm
				},
				cache : false,
				headers : {
					"cache-control" : "no-cache",
					"pragma" : "no-cache"
				},
				async : true, // 동기식 = false, 비동기식 = true(기본값)
				success : function(data) { // 아작스 전송 성공시
					$("body").html(data);
				},
				complete : function() {// 통신에 실패 했어도 완료가 되었을 때 이 함수를 실행합니다.
				},
				error : function() {
					alert("data error");
				}
			});// $.ajax
		}
		
	}); // change end
	

	
	$("form").submit(function() {
		if ($("#biz_search").val() == "") {
			alert("검색어를 입력 하세요");
			$("#biz_search").focus();
			return false;
		}
	});	// 유효성 검사 끝
});

function prePage(page){
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

function nextPage(page){
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

function movePage(page){
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