<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.net.*"%>
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
<link rel="stylesheet" type="text/css" href="./css/sns/sns_detail.css">
<!-- 메인컨텐츠(SNS) sns내용 나오는 부분 CSS로드 -->
<link rel="stylesheet" type="text/css" href="./css/sns/sns_content.css">

<!-- 메인컨텐츠(SNS) 친구목록 나오는 부분 CSS로드 -->
<link rel="stylesheet" type="text/css" href="./css/sns/sns_friend.css">
<script type="text/javascript" src="./js/sns/detail.js"></script>


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
		<c:set var="date" value="${snsbean.write_date }" />
		<c:set var="year" value="${fn:substring(date,0,4 )}" />
		<c:set var="month" value="${fn:substring(date,5,7 )}" />
		<c:set var="day" value="${fn:substring(date,8,10 )}" />
		<div class="content-area">
			<div id="sns_border" style="margin-top: 0px; padding-top: 20px;">
				<div id="sns_top-menu">
					<ul>
						<li><img src="./upload/prf/${snsbean.write_pic}" width="30px"
						height="30px" class="user_pic">&nbsp;&nbsp;
						작성자 : <c:out value="${snsbean.write_user }" /></li>
					</ul>
					<ul class="sns_count">
						<li id="sns_content_li_date">${year}년${month }월 ${day }일</li>

					</ul>
				</div>
				<div>
					<div id="sns_good">
						공감수&nbsp;(<img id="my_image" src="images/sns/icon01_1.jpg">)&nbsp;
						<c:out value="${snsbean.write_good }" />
					</div>
					<br>
					<div id="sns_img-content">
						<c:forTokens var="file" items="${snsbean.write_file}" delims="'">
							<c:if test="${snsbean.fileNum >= 4}"><img
									src="./upload/${file }" class="img4" alt="" />
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


					<div id="sns_content">
						<pre><c:out value="${snsbean.write_body }" />
						</pre>
					</div>

				</div>

				<div id="sns_modify">

					<ul>
						<c:if test="${id == snsbean.write_user}">
							<li style="margin-left: 300px;"><a
								href="./sns_detail.sns?num=${snsbean.write_num}&state=edit">수정</a></li>
							<li><a href="#" onclick="yesorno(${snsbean.write_num })">삭제</a></li>
							<li><a href="./sns_list.sns">메인으로</a></li>
						</c:if>
						<c:if test="${id != snsbean.write_user}">
							<li style="margin-left: 370px;"><a href="./sns_list.sns">메인으로</a></li>
						</c:if>
					</ul>
				</div>
				<div class="reply-area">
					<div id="sns_re-title">댓글 보기</div>

					<c:forEach var="b" items="${reply_bean}">
						<div>
							<div id="replybody">
								<img src="./upload/prf/${b.reply_pic }" class="reply_img">
								<div class="reply_user">${b.reply_user }<c:if
										test="${id == b.reply_user}">
										<%-- <a href="./reply_delete_ok.sns?wnum=${b.reply_wnum }&num=${b.reply_num}" class="reply_del"> --%>
										<a href="#"
											onclick="yesorno_r(${b.reply_wnum },${b.reply_num})"
											class="reply_del">[삭제]</a>
									</c:if>
									<br>
									<span>${b.reply_body }</span>

								</div>
							</div>
						</div>
						<br>
						<div style="clear: both;"></div>

					</c:forEach>
					<form id="reply_form" action="sns_reply_de.sns" method="post">
						<c:if test="${prfbean.PROFILE_PIC != null}">
						<img src="./upload/prf/${prfbean.PROFILE_PIC }" width="30px" height="30px"> <input type="hidden"
							value="${prfbean.PROFILE_PIC }" id="reply_pic" name="reply_pic"> 
						</c:if>
						<c:if test="${prfbean.PROFILE_PIC == null}">
						<img src="./images/sns/mul.png" width="30px" height="30px"> <input type="hidden"
							value="mul.png" id="reply_pic" name="reply_pic"> 
						</c:if>
						<input type="text" name="reply_body" id="reply_body"> <input
							type="hidden" value="${snsbean.write_num }" name="num" id="num">
						<input type="submit" value="댓글" class="reply-write-button"
							onclick="#replybody">
					</form>
				</div>

				<!-- 내용보기 끝-->
			</div>

		</div>
</body>
</html>