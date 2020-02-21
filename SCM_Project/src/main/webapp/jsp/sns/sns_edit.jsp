<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="model.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

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
<link rel="stylesheet" type="text/css" href="./css/sns/sns_edit.css">
<script type="text/javascript" src="./js/sns/write.js"></script>
</head>
<body>
	<div class="main-content">

		<!-- 내정보 보기 -->
		<div class="user-area">
			<div class="user-pic">
				<c:if test="${prfbean.PROFILE_PIC == null}">
					<img src="images/sns/noimg.png" height="200px" width="200px">
				</c:if>
				<c:if test="${ prfbean.PROFILE_PIC != null }">
					<img src="./upload/prf/${prfbean.PROFILE_PIC }" width="200px"
						height="273px">
				</c:if>

			</div>
			<div class="user-textarea">
				<div class="user-text1">
					<span class="user-id"> @${prfbean.PROFILE_ID}</span><br> <span
						class="user-name"> ${prfbean.PROFILE_USER}</span>
				</div>
				<div class="user-text2">
					<img src="images/sns/img05.jpg" />&nbsp;&nbsp;
					<c:if test="${prfbean.PROFILE_INTRO != ' '}">${prfbean.PROFILE_INTRO}</c:if>
					<c:if test="${prfbean.PROFILE_INTRO == ' '}"> 자기소개글을 입력해주세요.</c:if>
				</div>
				<div class="user-text3">
					<c:set var="join_date" value="${join_date}" />
					<c:set var="join_year" value="${fn:substring(join_date,0,4)}" />
					<c:set var="join_month" value="${fn:substring(join_date,5,7 )}" />
					<img src="images/sns/img02.jpg" /> 가입일: ${join_year}년
					${join_month}월
				</div>
				<div class="user-text4">
					작성한 게시물 : <span>${count_sns }개</span>
				</div>
				<div class="user-text5">
					<div>
						<a href="./sns_list_mine.sns?id=${id }">내 글 바로가기</a>
					</div>
				</div>

				<div class="user-text5">
					<div>
						<a href="./sns_list_good.sns">좋아요 한 글 보기</a>
					</div>
				</div>
			</div>
		</div>
		<!-- 내정보 보기 끝-->

		<!-- 최신글 기준으로 글쓴사용자 불러오기 -->
		<!-- 제일 오른쪽에 영역임 주의 바람 -->
		<div class="friend-area">
		<div class="update-area">
		<img src="images/sns/label_new.png">
		최신글 SNS 사용자
		<hr>
		</div>
		<c:forEach var="user" items="${snsuser}" end="7">
			<div class="friend-info">
				<a href="./sns_list_mine.sns?id=${user.write_user}">
				<img src="./upload/prf/${user.write_pic}" width="30px" height="30px">
				${user.write_user}
				</a>
			</div>
			</c:forEach>
		</div>
		<!-- 불러오기 끝-->

		<!-- 내용보기 -->
		<div class="content-area">

			<div class="user-write">
				<div>
					<div class="write-label">
						<img src="images/sns/img06.jpg" />&nbsp;&nbsp; 공유하고 싶은 글을 수정하세요
					</div>

				</div>
				<div>
					<form id="myform" action="./sns_edit_ok.sns" 
					method="post" enctype="multipart/form-data">
						<input type="hidden" name="write_num" value="${snsbean.write_num}">
						<textarea rows="4" cols="65" id="write_body" name="write_body">${snsbean.write_body }</textarea>
						<!-- <h4 id="rtext">150/150</h4> -->
						<div id="Preview">
							<br> <span>기존파일</span><br>
							<br>
							<c:forTokens var="file" items="${snsbean.write_file}" delims="'">
								<c:if test="${snsbean.fileNum >= 4}">
									<img src="./upload/${file }" class="img4" />
								</c:if>
								<c:if test="${snsbean.fileNum == 3}">
									<img src="./upload/${file }" class="img3" />
								</c:if>
								<c:if test="${snsbean.fileNum == 2}">
									<img src="./upload/${file}" class="img2" />
								</c:if>
								<c:if test="${snsbean.fileNum == 1}">
									<img src="./upload/${file }" class="img1" />
								</c:if>

							</c:forTokens>
						</div>
						<div class="dvPreview">
							<span>수정된 파일</span><br>
							<div id="dvPreview">
								<br>
							</div>
						</div>
				</div>
				<div class="write-button-area">
					<div class="write-file">
						<input multiple type="file" name='write_file[]' id="write_file"
							accept="image/*" />
					</div>

					<div class="write-submit">
						<input type="submit" value="공유하기" /> <input type="button"
							onclick="history.go(-1)" value="취소" />
						</form>
					</div>
				</div>
			</div>
</body>
</html>