<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="model.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<!-- 메인컨텐츠(SNS) 사이드 영역 CSS로드 -->
<link rel="stylesheet" type="text/css" href="./css/sns/sns_side.css">

<!-- 메인컨텐츠(SNS) sns내용 나오는 부분 CSS로드 -->
<link rel="stylesheet" type="text/css" href="./css/sns/sns_content.css">


<!-- 메인컨텐츠(SNS) 친구목록 나오는 부분 CSS로드 -->
<link rel="stylesheet" type="text/css" href="./css/sns/sns_friend.css">

<script
	src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"
	type="text/javascript" language="javascript"></script>
<script src="http://code.jquery.com/jquery-1.11.0.min.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$("#reply_form").submit(function(event) {
			
			event.preventDefault();
			
			if ($(this).find('#reply_body').val() == "") {
				alert("댓글을 입력하세요");
				$("#reply_body").focus();
				return false;
			}
			
			var reply_pic = $(this).find('#reply_pic').val();
			
			var reply_body = $(this).find("#reply_body").val();
			var num = $(this).find("#num").val();
			var reply_boddy = $(this).find("#reply_body");

			$.ajax({ // post 방식으로 자료를 전달합니다.
				url : "sns_reply_ok.sns",
				data : {
					"reply_pic" : reply_pic,
					"reply_body" : reply_body,
					"num" : num
				},
				type : "POST",
				async : false,
				success : alert("성공"),
				complete : function() {
					reply_boddy.val("");
					reply_boddy.focus();
					alert(reply_boddy.val());
					return false;
					
				}

			});
			
			

		});
		

	});
</script>


</head>
<body>

	<!-- 인기 글 나오는 영역 -->
	<!-- 인기글 영역이 무한 스크롤 되면서 값을 불러 옵니다. -->
	<c:forEach var="b" items="${snslist}">
		<c:set var="date" value="${b.write_date }" />
		<c:set var="month" value="${fn:substring(date,5,7 )}" />
		<c:set var="day" value="${fn:substring(date,8,10 )}" />
		<div class="popularity-area" id="popularity-area${b.write_num }">
			<div class="popularity-user">
				<input type="hidden" id="page" value="${page+1}"> <input
					type="hidden" id="maxpage" value="4">
					 <img src="./upload/prf/${b.write_pic}" width="40px" height="40px" />
				<div><a href="./sns_list_mine.sns?id=${b.write_user}">${b.write_user}</a><br>
					<span>${month}월 ${day}일</span>
				</div>
			</div>
			<div id="picture">
				<c:forTokens var="file" items="${b.write_file}" delims="'">
					<c:if test="${b.fileNum >= 4}">
						<img src="./upload/${file }" class="img4" />
					</c:if>
					<c:if test="${b.fileNum == 3}">
						<img src="./upload/${file }" class="img3" />
					</c:if>
					<c:if test="${b.fileNum == 2}">
						<img src="./upload/${file}" class="img2" />
					</c:if>
					<c:if test="${b.fileNum == 1}">
						<img src="./upload/${file }" class="img1" />
					</c:if>

				</c:forTokens>
			</div>
			<div class="popularity-text">
				<pre style="margin-bottom: 0px; margin-top: 0px;">${b.write_body}</pre>
			</div>
			<div id="detail">
				<a href="./sns_detail.sns?num=${b.write_num}&state=cont"
					style="text-decoration: none;">더 보기 ▶</a>
			</div>

			<div class="popularity-count">좋아요 ${b.write_good }개&nbsp;&nbsp;&nbsp;
				댓글 ${b.write_reply}개</div>
			<div class="popularity-button" id="popularity-button">



				<c:if test="${b.good_on==1}">
					<img id="my_image${b.write_num}" src="images/sns/icon01_1.jpg">
					<span> <a
						href="sns_good_off.sns?sns_num_good=${b.write_num}#popularity-area${b.write_num}"
						onclick="hiddenbtn()"> 좋아요</a></span>
				</c:if>

				<c:if test="${b.good_on==0}">
					<img id="my_image${b.write_num}" src="images/sns/icon01.jpg">
					<span> <a
						href="sns_good_on.sns?sns_num_good=${b.write_num}#popularity-area${b.write_num}">
							좋아요</a></span>
				</c:if>

				<img src="images/sns/icon02.jpg"><span><a
					href="./sns_detail.sns?num=${b.write_num}&state=cont">댓글보기</a></span>
			</div>
			<div></div>
		</div>

		<!-- 댓글달기 영역 -->
		<div class="reply-area">
		<form id="reply_form" >
				<c:if test="${prfbean.PROFILE_PIC != null}">
						<img src="./upload/prf/${prfbean.PROFILE_PIC }" width="30px" height="30px"> <input type="hidden"
							value="${prfbean.PROFILE_PIC }" id="reply_pic" name="reply_pic"> 
						</c:if>
						<c:if test="${prfbean.PROFILE_PIC == null}">
						<img src="./images/sns/mul.png" width="30px" height="30px"> <input type="hidden"
							value="mul.png" id="reply_pic" name="reply_pic"> 
						</c:if><input
					type="text" value="${b.reply_body }" name="reply_body"
					id="reply_body"> <input type="hidden"
					value="${b.write_num }" name="num" id="num"> <input
					type="submit" value="댓글" class="reply-write-button">
			</form>
		</div>


	</c:forEach>
	<!-- 댓글달기 영역 끝-->
	<!-- 인기 글 나오는 영역 끝-->
</body>
</html>