<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ page import="model.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="./js/sns/write.js"></script>

<!-- 메인컨텐츠(SNS) 사이드 영역 CSS로드 -->
<link rel="stylesheet" type="text/css"  href="./css/sns/sns_side.css">

<!-- 메인컨텐츠(SNS) sns내용 나오는 부분 CSS로드 -->
<link rel="stylesheet" type="text/css"  href="./css/sns/sns_content.css">


<!-- 메인컨텐츠(SNS) 친구목록 나오는 부분 CSS로드 -->
<link rel="stylesheet" type="text/css"  href="./css/sns/sns_friend.css">

<script
	src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"
	type="text/javascript" language="javascript"></script>
<script src="http://code.jquery.com/jquery-1.11.0.min.js"></script>
	<script src="./js/sns/content_like.js"></script>


	
	

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
		최신글 SNS 보기
		<hr>
		</div>
		<c:forEach var="user" items="${snsuser}" end="7">
			<div class="friend-info">
				<a href="#" onClick="window.open('sns_detail_open.sns?num=${user.write_num}','더보기',
				'width=550, height=500, toolbar=no, menubar=no, scrollbars=no, resizable=yes ,left=400px,top=100px'
				);return false;">
				<img src="./upload/prf/${user.write_pic}" width="30px" height="30px">
				${user.write_user}
				</a>
			</div>
			</c:forEach>
		</div>
		<!-- 불러오기 끝-->
		
		<!-- 내용보기 -->
		<div class="content-area">
			<!-- 글을 바로 쓸수 있게 만들어 놓은 영역 -->
			<div class="user-write">
				<div>
					<div class="write-label"><img src="images/sns/img06.jpg"/>&nbsp;&nbsp; 공유하고 싶은 글을 작성하세요</div>
				
				</div>
				<div>
				<form id="myform" name="myform" action="./sns_write_ok.sns" method="post" enctype="multipart/form-data">
					<textarea rows="4" cols="65" id="write_body" name="write_body" ></textarea>
					<h4 id="rtext">150/150</h4>
					<div id="dvPreview"></div>
				</div>
				<div class="write-button-area">
					<div class="write-file"><input multiple type="file" name='write_file[]' id="write_file" accept="image/*"/>
					<c:if test="${prfbean.PROFILE_PIC != null}">
							<input type="hidden" value="${prfbean.PROFILE_PIC }" id="write_pic" name="write_pic"></c:if>
							<c:if test="${prfbean.PROFILE_PIC == null}">
							<input type="hidden" value="mul.png" id="write_pic" name="write_pic"></c:if>
					</div>
					<div class="write-submit">
						<input type="submit" value="공유하기"/>	
						</form>
					</div>
				</div>				
			</div>
			
			<!-- 글을 바로 쓸수 있게 만들어 놓은 영역 끝 -->
			
			
			<!-- 구인 광고 나오는 영역 -->
			
			<div class="biz-area">
				<div class="biz-label">신규 채용 공고</div>
				<c:forEach var="biz" items="${bizlist}" end="2">
				<div class="biz-content">
				<img src="./upload/prf/${biz.biz_prf_img}" width="100px" height="60px"><br> 
					<span>${biz.biz_name }</span>
					<p>${biz.biz_occ }</p>
					<input type="hidden" value="${biz.biz_num }">
					<input type="button" value="채용 공고 보기" 
					onclick="location='biz_getCont.biz?biz_num=${biz.biz_num}&state=cont&boardForm=all'">
				</div>
				</c:forEach>
				<div style="clear: both"></div>
				<div class="biz-click">
				<a href="/SCM_Project/biz_list.biz?boardForm=all">더 보기 ▶</a>
				</div>
			</div>
			<!-- 구인 광고 나오는 영역 끝-->
			
			
			<!-- 인기 글 나오는 영역 -->
			<!-- 인기글 영역이 무한 스크롤 되면서 값을 불러 옵니다. -->
			<c:forEach var="b" items="${snslist}">
			<c:set var="date" value="${b.write_date }"/>
		<c:set var="month" value="${fn:substring(date,5,7 )}"/>
		<c:set var="day" value="${fn:substring(date,8,10 )}"/>
		<c:if test="${b.good_on==1}">
			<div class="popularity-area" id="popularity-area${b.write_num}">
				<div class="popularity-user">
				<input type="hidden" id="page" value="${page+1}">
				<input type="hidden" id="maxpage" value="${maxpage}">
					 <img src="./upload/prf/${b.write_pic}" width="40px" height="40px" />
					<div><a href="./sns_list_mine.sns?id=${b.write_user}">${b.write_user}</a><br><span>${month}월  ${day}일</span></div>
				</div>
				<div id="picture">				
				<c:forTokens var="file" items="${b.write_file}" delims="'">
				 <c:if test="${b.fileNum >= 4}">
				<img src="./upload/${file }" class="img4"/> 
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
				<div id ="detail">
				<a href="./sns_detail.sns?num=${b.write_num}&state=cont" style="text-decoration: none;" >더 보기 ▶</a>
				</div>
				
				<div class="popularity-count" >
					좋아요 ${b.write_good }개&nbsp;&nbsp;&nbsp; 댓글 ${b.write_reply}개
				</div>
				<div class="popularity-button" id="popularity-button">
				
			
				
				
						<img id="my_image${b.write_num}" src="images/sns/icon01_1.jpg"><span>
						<a href="sns_good_off.sns?sns_num_good=${b.write_num}&cont=like#popularity-area${b.write_num}" onclick="hiddenbtn()">
						좋아요</a></span>
		
				<img src="images/sns/icon02.jpg"><span><a href="./sns_detail.sns?num=${b.write_num}&state=cont">댓글보기</a></span>
				</div>
				<div></div>
			</div>
			
			<!-- 댓글달기 영역 -->
			<div class="reply-area" >
			<form id="reply_form">
				<c:if test="${prfbean.PROFILE_PIC != null}">
						<img src="./upload/prf/${prfbean.PROFILE_PIC }" width="30px" height="30px"> <input type="hidden"
							value="${prfbean.PROFILE_PIC }" id="reply_pic" name="reply_pic"> 
						</c:if>
						<c:if test="${prfbean.PROFILE_PIC == null}">
						<img src="./images/sns/mul.png" width="30px" height="30px"> <input type="hidden"
							value="mul.png" id="reply_pic" name="reply_pic"> 
						</c:if>
				<input type="text" value="${b.reply_body}" name="reply_body" id="reply_body">
				<input type="hidden" value="${b.write_num }" name="num" id="num">			
				<input type="submit" value="댓글" class="reply-write-button">
				</form>
			</div>
				</c:if>
			</c:forEach>
	 <c:forEach begin="2" end="${maxpage}" var="d">
			<div id="end${d}" name="end"></div>
			</c:forEach> 
			
				
			<!-- 댓글달기 영역 끝-->
			<!-- 인기 글 나오는 영역 끝-->
		</div>
		<!-- 내용보기 끝-->
	</div>
</body>
</html>