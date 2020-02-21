<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="model.qna.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css"  href="./css/qna/qna_footer.css">
<link rel="stylesheet" type="text/css"  href="./css/qna/qna_list.css">
<link rel="stylesheet" type="text/css"  href="./css/qna/qna_side.css">
</head>
<body>
<div class="qna_list_area">
	<!-- 사이드 메뉴 나오는 곳 -->
	<div class="qna_list_side">
		<div class="qna_list_side_picarea">
			<div class="qna_list_userpic">
			<c:if test="${prfbean.PROFILE_PIC != NULL}">
			<img src="./upload/prf/${prfbean.PROFILE_PIC  }"><br>
			</c:if>
				<c:if test="${prfbean.PROFILE_PIC == NULL}">
			<img src="./images/sns/noimg.png"><br>
			</c:if>
			</div>
			<div class="qna_list_userid">${id}</div>
		</div>
		<div class="qna_side_qnabutton" onclick="location.href='/SCM_Project/qna_write.qna'">질문하기</div>
		<div class="qna_side_qnalavel">
			<label>코딩질문</label>
		</div>
		<div class="qna_side_language_box">
			<div class="qna_side_language_button" onclick="location.href='/SCM_Project/qna_list.qna?state=all'">전체보기</div>
			<div class="qna_side_language_button" onclick="location.href='/SCM_Project/qna_list.qna?state=java'">java</div>
			<div class="qna_side_language_button" onclick="location.href='/SCM_Project/qna_list.qna?state='+escape('C++')">C++</div>
			<div class="qna_side_language_button" onclick="location.href='/SCM_Project/qna_list.qna?state=C'">C</div>
			<div class="qna_side_language_button" onclick="location.href='/SCM_Project/qna_list.qna?state=node.js'">node.js</div>
			<div class="qna_side_language_button" onclick="location.href='/SCM_Project/qna_list.qna?state=JavaScript'">JavaScript</div>
			<div class="qna_side_language_button" onclick="location.href='/SCM_Project/qna_list.qna?state=Spring'">Spring</div>
			<div class="qna_side_language_button" onclick="location.href='/SCM_Project/qna_list.qna?state=Oracle'">Oracle</div>
			<div class="qna_side_language_button" onclick="location.href='/SCM_Project/qna_list.qna?state=andsoon'">기타 언어</div>
		</div>
		<div class="qna_side_qnabutton" onclick="location.href='/SCM_Project/qna_list.qna?searchID=${id}'">내 글 보기</div>
		
	</div>
	<!-- 사이드 메뉴 나오는 곳 끝 -->
	
	<!-- qna 컨텐츠 나오는 부분 -->
	<div class="qna_list_content">
	
		<!-- 현제위치 알려주는 라벨 영역 -->
		<div class="qna_list_content_label_area">
			<div class="qna_list_content_label"><div>&ensp;&ensp;&ensp;&ensp;코딩 질문</div></div>
			<div class="qna_list_content_label1">
				<c:if test="${state == 'all'}">
					전체보기
				</c:if>
				<c:if test="${state == 'andsoon'}">
					기타 언어
				</c:if>
				<c:if test="${state != 'all' && state != 'andsoon'}">
					${state}
				</c:if>
			</div>
		</div>
		<!-- 현제위치 알려주는 라벨 영역 끝 -->
		<!-- 내용나오는 테이블 영역 -->
		<!-- 화면 출력 번호 변수 정의 -->
		<div class="qna_list_content_table">
			<table align="center">
				<tr>
					<td width="300px">제목</td>
					<td width="80px">사용 언어</td>
					<td width="120px">글쓴이</td>
					<td width="50px">댓글 수</td>
					<td width="80px">작성일</td>
					<td width="50px">조회수</td>
				</tr>
				
				<!-- 반복문 시작 -->
				<c:forEach var="b" items="${qnalist}">
				<tr>
					<td onclick="location.href='/SCM_Project/qna_detail.qna?num=${b.qna_num}&page=${page}&state=${state}'">${b.qna_subject}</td>
					<td><a>
						<c:if test="${b.qna_type=='andsoon'}">
							기타 언어
						</c:if>
						<c:if test="${b.qna_type!='andsoon'}">
							${b.qna_type}
						</c:if>
					</a></td>
					<td><a>${b.qna_id}</a></td>
					<td><a>${b.qna_reply}</a></td>
					<td>${b.qna_date}</td>
					<td>${b.qna_hits}</td>
				</tr>
				</c:forEach>
			</table>
		</div>
		<!-- 내용나오는 테이블 영역 -->
		<!-- 페이지 넘기는 숫자 나오는 영역 -->
		<div class="qna_list_content_number">
		
			<ul>
				<li>
				<c:if test="${page <= 1}">
					<a>◀</a>
				</c:if>
				<c:if test="${page > 1}">
					<a href="qna_list.qna?page=${page-1}">◀</a>
				</c:if>
				</li>
			<!-- 페이지 넘어가는 번호 출력 -->
			<c:forEach var="a" begin="${startpage}" end="${endpage}">
				
				<c:if test="${a == page}">
				<li>
					<a>${a}</a>
				</li>
				</c:if>
				<c:if test="${a != page}">
				<li onclick="location.href='qna_list.qna?page=${a}'">
					${a}
				</li>
				</c:if>
				
			</c:forEach>
				<li>
				<c:if test="${page >= maxpage}">
					<a>▶</a>
				</c:if>
				<c:if test="${page < maxpage }">
					<a href="qna_list.qna?page=${page+1}">▶</a>
				</c:if>
				</li>
			</ul>
		</div>
		<!-- 페이지 넘기는 숫자 나오는 영역 끝-->
		
		<!-- 질문 검색 영역 -->
		<div class="qna_list_content_searcharea">
			<form method="post" action="qna_search_ok.qna">
				<center>
					<select name="qna_search_select">
						<option value="all">전체검색</option>
						<option value="java">java</option>
						<option value="C++">C++</option>
						<option value="C">C</option>
						<option value="node.js">node.js</option>
						<option value="JavaScript">JavaScript</option>
						<option value="Spring">Spring</option>
						<option value="Oracle">Oracle</option>
						<option value="andsoon">기타 언어</option>
					</select>
					<input type="text" name="qna_search" class="qna_list_content_searctext"/>
					<input type="submit" value="검색" class="qna_list_content_searchbutton"/>
				</center>
			</form>
		</div>
		<!-- 질문 검색 영역 끝-->
		
	</div>
	<!-- qna 컨텐츠 나오는 부분 끝 -->
	
	<!-- Footer 영역 -->
	<div style="height: 0px; clear: both"></div>
	<div class="qna_list_footer">
		Copyright ⓒ SCM_Project.All right reserved.
	</div>
	<!-- Footer 영역 -->
	
</div>

</body>
</html>