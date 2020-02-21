<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<!-- 메인컨텐츠(SNS) 사이드 영역 CSS로드 -->
<link rel="stylesheet" type="text/css" href="./css/sns/sns_side.css">

<!-- 메인컨텐츠(SNS) sns내용 나오는 부분 CSS로드 -->
<link rel="stylesheet" type="text/css" href="./css/sns/sns_content.css">

<!-- 메인컨텐츠(SNS) 친구목록 나오는 부분 CSS로드 -->
<link rel="stylesheet" type="text/css" href="./css/sns/sns_friend.css">
<!-- 메인컨텐츠(SNS) sns내용 나오는 부분 CSS로드 -->
<link rel="stylesheet" type="text/css" href="./css/sns/sns_write.css">
<script
	src="//github.com/fyneworks/multifile/blob/master/jQuery.MultiFile.min.js"
	type="text/javascript" language="javascript"></script>
<script
	src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"
	type="text/javascript" language="javascript"></script>
<script src="http://code.jquery.com/jquery-1.11.0.min.js"></script>
 <script src="./js/sns/content.js"></script> 
<script src="./js/sns/write.js"></script>
		<script type="text/javascript" src="js/jquery-1.7.2.min.js"></script>
		<script type="text/javascript" src="js/jquery.alsEN-1.0.min.js"></script>
		
<body>
	<div class="main-content">

		<!-- 내정보 보기 -->
		<div class="user-area">
			<div class="user-pic">
				<img src="images/sns/img01.jpg" height="200px">
			</div>
			<div class="user-textarea">
				<div class="user-text1">
					<span class="user-id"> ${id}</span><br> <span
						class="user-name"> ${join_name }</span>
				</div>
				<div class="user-text2">
					<img src="images/sns/img05.jpg" />&nbsp;&nbsp;프로필 제목이나 자기 소개 글이 여기
					나온다
				</div>
				<div class="user-text3">
					<img src="images/sns/img02.jpg" /> 가입일: 2016년 3월
				</div>
				<div class="user-text4">
					작성한 게시물 : <span>${count_sns }</span>
				</div>
				<div class="user-text5">
					<div>
						<a href="./sns_list_mine.sns">내 글 바로가기</a>
					</div>
				</div>
				<div class="user-text6">
					<div>
						<a>내 프로필 바로가기</a>
					</div>
				</div>
			</div>
		</div>
		<!-- 내정보 보기 끝-->

		<!-- 친구 목록 보기 -->
		<!-- 제일 오른쪽에 영역임 주의 바람 -->
		<div class="friend-area">
			<div class="friend-info">
				<img src="images/sns/thum01.jpg" width="30px"> 조커
			</div>
			<div class="friend-info">
				<img src="images/sns/thum02.jpg" width="30px"> 여자사람
			</div>
			<div class="friend-info">
				<img src="images/sns/thum03.jpg" width="30px"> 개
			</div>
			<div class="friend-info">
				<img src="images/sns/thum01.jpg" width="30px"> 조커
			</div>
			<div class="friend-info">
				<img src="images/sns/thum02.jpg" width="30px"> 여자사람
			</div>
			<div class="friend-info">
				<img src="images/sns/thum03.jpg" width="30px"> 개
			</div>
			<div class="friend-info">
				<img src="images/sns/thum01.jpg" width="30px"> 조커
			</div>
			<div class="friend-info">
				<img src="images/sns/thum02.jpg" width="30px"> 여자사람
			</div>
			<div class="friend-info">
				<img src="images/sns/thum03.jpg" width="30px"> 개
			</div>
			<div class="friend-search">
				<input type="text"> <input type='button' value="검색"
					class="friend-search-button" />
			</div>
		</div>
		<!-- 친구 목록 보기 끝-->

		<!-- 내용보기 -->
		<div class="content-area">
			<form id="myform" name="myform" action="./sns_write_ok.sns"
				method="post" enctype="multipart/form-data">
				<table>
					<tr>
						<td colspan="2" style="font-size:13pt;"><label>&nbsp;&nbsp;게시글 올리기</label></td>
					</tr>
					<tr>
						<td colspan="2"><div id="jump"></div></td>
					</tr>
					<tr>
						<td align="right"><label>&nbsp;&nbsp;내용</label></td>
						<td><textarea id="write_body" name="write_body"></textarea>
							<div id="image_preview">
								<img id="UploadedImg1" src="" width="200" height="111">
								<img id="UploadedImg2" src="" width="200" height="111">
								<img id="UploadedImg3" src="" width="200" height="111">
							</div></td>
					</tr>
					<tr>
						<td colspan="2"><div id="jump"></div></td>
					</tr>
					<tr>
						<td style="width: 100px;"><label>&nbsp;사진첨부&nbsp;&nbsp;&nbsp;&nbsp;</label></td>
						<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<input multiple type="file" name='write_file[]' id="write_file" accept="image/*"/>
						</td>
					</tr>
				<tr>
				<td colspan=2"><div id="dvPreview"></div></td>
				</tr>
				</table>
				<div>
					<input type="submit" id="submit" value="등록" /> <input type="button"
						value="취소" id="cancle" onClick="history.go(-1)" />
				</div>
			</form>
		</div>
</body>
</html>