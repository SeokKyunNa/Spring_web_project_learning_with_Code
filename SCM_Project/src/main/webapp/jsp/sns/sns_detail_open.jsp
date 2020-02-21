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


		
		<!-- 내용보기 -->
		<c:set var="date" value="${snsbean.write_date }" />
		<c:set var="year" value="${fn:substring(date,0,4 )}" />
		<c:set var="month" value="${fn:substring(date,5,7 )}" />
		<c:set var="day" value="${fn:substring(date,8,10 )}" />
		
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

			<!-- 내용보기 끝-->
			</div>

</body>
</html>