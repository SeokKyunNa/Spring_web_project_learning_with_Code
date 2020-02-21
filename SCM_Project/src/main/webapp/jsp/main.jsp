<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script  src="http://code.jquery.com/jquery-latest.min.js"></script>
<script src="./js/main.js"></script>

<!-- 상단 네비게이션 CSS 로드 -->
<link rel="stylesheet" type="text/css"  href="./css/main.css">

<style type="text/css">
	@import url(http://fonts.googleapis.com/earlyaccess/notosanskr.css);
</style>
</head>
<body>
<div>
	<!-- 메뉴부분 -->
	<div class="menu-background">
		<div class="menu-area">
			<!-- 로고 들어가는 부분 -->
			<div class="menu-logo">
				<a href="./sns_list.sns"><img src="images/main/main-logo3.png"></a>
			</div>
			<!-- 로고 들어가는 부분 끝-->
			
			<!-- 검색 들어가는 부분 -->
			<div class="menu-search">
				<div>
					<select class="menu-select">
						<option>전체</option>
						<option>SNS</option>
						<option>비지니스</option>
						<option>질문</option>
						<option>메세지</option>
					</select>
					<input type="text" value="검색하세요" class="menu-search-input" onfocus="this.value='';">
					<input type="button" value="검색" class="menu-search-button">
				</div>
			</div> 
			<!-- 검색 들어가는 부분 끝-->
			
			<!-- 메뉴버튼 들어가는 부분 -->
			<div class="menu-button">
				<div class="menu-list">
					<ul>
						<li><a href="/SCM_Project/prf_view.prf">프로필</a></li>
						<li><a href="/SCM_Project/biz_list.biz?boardForm=all">비지니스</a></li>
						<li><a href="/SCM_Project/qna_list.qna">질문</a></li>
						<li><a href="/SCM_Project/bbs_list.msg">메세지</a></li>
					</ul>
				</div>
				<div class="drop-box">
				<!-- 짝대기 3개 나오는 메뉴버튼을 위해 만든 div영역 -->
					<div class="drop-bg">
						<div class="drop-button"></div>
						<div class="drop-button"></div>
						<div class="drop-button"></div>
					</div>
				<!-- 짝대기 3개 나오는 메뉴버튼을 위해 만든 div영역 끝-->
					
					<div style="clear: both"></div>
					<div class="drop-menu">
						<ul>
							<a href="./member_logout.nhn"><li>로그아웃</li></a>
							<a><li>_____________</li></a>
							<a href="#" onClick="window.open('http://www.incruit.com/','인크루트','width=800, height=700, toolbar=no, menubar=no, scrollbars=no, resizable=yes');return false;">
							<li>인크루트</li></a>
							<a href="#" onClick="window.open('http://www.saramin.co.kr/','사람인','width=800, height=700, toolbar=no, menubar=no, scrollbars=no, resizable=yes');return false;">
							<li>사람인</li></a>
							<a href="#" onClick="window.open('http://www.jobkorea.co.kr/','잡코리아','width=800, height=700, toolbar=no, menubar=no, scrollbars=no, resizable=yes');return false;">
							<li>잡코리아</li></a>
							<a href="#" onClick="window.open('http://www.work.go.kr/','워크넷','width=800, height=700, toolbar=no, menubar=no, scrollbars=no, resizable=yes');return false;">
							<li>워크넷</li></a>
						</ul>
					</div>
				</div>
			</div>
			<!-- 메뉴버튼 들어가는 부분 끝-->
		</div>
	</div>
	<!-- 메뉴부분 끝-->
	
	<!-- 인크루드 부분 -->
	<div class="content_load">
	
		<jsp:include page='${bodyAdd}.jsp'/>
	</div>

	<!-- 인크루드 부분 끝-->
</div>
</body>
</html>