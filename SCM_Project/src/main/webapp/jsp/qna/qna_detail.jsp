<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">


<link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/highlight.js/9.2.0/styles/mono-blue.min.css">
<script src="//cdnjs.cloudflare.com/ajax/libs/highlight.js/9.2.0/highlight.min.js"></script>
<script>hljs.initHighlightingOnLoad();</script>




<title>Insert title here</title>
<link rel="stylesheet" type="text/css"  href="./css/qna/qna_footer.css">
<link rel="stylesheet" type="text/css"  href="./css/qna/qna_list.css">
<link rel="stylesheet" type="text/css"  href="./css/qna/qna_side.css">
<link rel="stylesheet" type="text/css"  href="./css/qna/qna_detail.css">

<script src="http://code.jquery.com/jquery-latest.js"></script>
<script src="./js/qna/qna_detail.js"></script>

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
		<div class="qna_detail_button1">
			<input type="button" value="다음글"/>
			<input type="button" value="이전글"/>
		</div>
		<div class="qna_detail_button2">
			<input type="button" value="목록" class="qna_list"/>
		</div>
		<div style="clear: both;"></div>
		<div class="qna_detail_content">
			<div class="qna_detail_underline">
				<div class="qna_detail_subject1">
					<div>
						${qnalist[1].qna_subject}
					</div>
					<div style="color:#c1c1c1; font-size: 9pt;">
						|
					</div>
					<div style="font-family: Dotum; color: #5d5d5d; padding-top: 2px;">
						${qnalist[1].qna_type}
					</div>
				</div>
				<div class="qna_detail_subject2">
				<c:if test="${id==qnalist[1].qna_id}">
					<div>
						<a class="qna_delete">삭제</a>
					</div>
					<div style="color:#c1c1c1; font-size: 9pt;">
						|
					</div>
					<div>
						<div style="cursor : pointer;"onclick="location.href='/SCM_Project/qna_edit.qna?num=${qnalist[1].qna_num}&page=${page}&state=${state}'">수정</div>
					</div>
					<div style="color:#c1c1c1; font-size: 9pt;">
						|
					</div>
				</c:if>
					<div style="font-family: Dotum; color: #5d5d5d; padding-top: 2px;">
						${qnalist[1].qna_date}
					</div>
				</div>
			</div>
			<div style="clear: both"></div>
			<div style="padding-top: 7px;">
				<div class="qna_detail_writeuser">
					<span style="color: gray">작성자 :</span> ${qnalist[1].qna_id}
				</div>
				<%-- <div class="qna_detail_file">
				<c:if test="${qnabean.qna_file != null}">
					<c:forEach var="f" items="${qnaFileStr}">
						${f}<br>
					</c:forEach>
				</c:if>
				
				</div> --%>
			</div>
			<div style="clear: both; height: 30px;"></div>
			
			<!-- 글 내용이 나오는 영역 -->
			<div>
				<div align="center">
				<%-- <c:if test="${qnabean.qna_file != null}">
					<c:forEach var="img" items="${qnaFileStr}">
						<img src="./upload/qna${img}" style="width: auto;">
					</c:forEach>
				</c:if> --%>
				</div>
				<div>
					${qnalist[1].qna_body}
				</div>
				<div>
					<pre><code>${qnalist[1].qna_code}</code></pre>
				</div>
			</div>
			<!-- 글 내용이 나오는 영역 끝-->
			
			
			<div class="qna_detail_underline2"></div>
			<div class="qna_detail_reply_info">조회수(${qnalist[1].qna_hits})
				<span style="color:#c1c1c1; font-size: 9pt; margin: 0px 10px 0px 10px">|</span>
				댓글수(
					<span id="qna_reply_count" class="qna_detail_reply_info">
						<!-- 이곳에 댓글 수를 불러 온다. -->
					</span>
				)
				<span style="color:#c1c1c1; font-size: 9pt; margin: 0px 10px 0px 10px">|</span>
				최신순
			</div>
			<div class="qna_detail_reply_area">
			<!-- 
			
				댓글 관련 영역 
				이곳에 아작스를 통해 페이지를 로드 시킨다.
			
			-->
			</div>
			
			<!-- 수정, 삭제, 목록보기 등 버튼 나오는 영역 -->
			<div class="qna_detail_button_collection">
				<input type="hidden" value="${page}" id="page"/>
				<input type="hidden" value="${state}" id="state"/>
				<input type="hidden" value="${qnalist[1].qna_num}" id="qna_num"/>
				
				
				<c:if test="${id==qnalist[1].qna_id}">
					<input type="button" value="수정" class="qna_edit"/>
					<input type="button" value="삭제" class="qna_delete"/>
				</c:if>
				
				
				
				<input type="button" value="이전" id="qna_prev"/>
				<input type="button" value="목록" class="qna_list"/>
			</div>
			<!-- 수정, 삭제, 목록보기 등 버튼 나오는 영역 끝-->
			<div style="clear: both"></div>
		</div>
		
		
		<!-- 글 삭제 , 댓글 삭제 버튼을 눌렀을 때 올라 올 모달 화면 -->
		<div id="qna_mask"></div>
		<div class="qna_delete_modal1">
			<div style="text-align: center; height: 50px; line-height: 50px;">
				글을 삭제 하시 겠습니까?
			</div>
			<div style="text-align: center; height: 50px; line-height: 30px;">
				<input type="button" value="예" class="qna_delete_detail"/>&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="button" value="아니요" class="qna_reply_cancle"/>
			</div>
		</div>
		
		<!-- 댓글 삭제를 눌렀을 때 나온다. -->
		<div class="qna_delete_modal2">
			<div style="text-align: center; height: 50px; line-height: 50px;">
				댓글을 삭제 하시 겠습니까?
			</div>
			<div style="text-align: center; height: 50px; line-height: 30px;">
				<input type="button" value="예" id="qna_reply_delete"/>&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="button" value="아니요" class="qna_reply_cancle"/>
			</div>
		</div>
		<!-- 글 삭제 , 댓글 삭제 버튼을 눌렀을 때 올라 올 모달 화면 끝-->
		
		
		<!-- 다음글 이전글 보기 나오는 부분 -->
		<div class="qna_detail_prevNext">
			
			<div style="margin:2px 0px 5px 0px;">
			<c:if test="${qnalist[0].qna_num == null}">
				<div>이전글 : 이전글이 없습니다.</div>
			</c:if>
			
			<c:if test="${qnalist[0].qna_num != null}">
				<div style="cursor : pointer" onclick="location.href='/SCM_Project/qna_detail.qna?num=${qnalist[0].qna_num}&page=${page}&state=${state}'">이전글 : ${qnalist[0].qna_subject}</div>
			</c:if>
			
			
			
			</div>
			<div style="border-bottom: 1px solid #c1c1c1;"></div>
			<c:if test="${qnalist[2].qna_num != null}">
			<div style="margin:5px 0px 2px 0px;cursor : pointer"onclick="location.href='/SCM_Project/qna_detail.qna?num=${qnalist[2].qna_num}&page=${page}&state=${state}'">다음글 : ${qnalist[2].qna_subject}</div>
			</c:if>
			<c:if test="${qnalist[2].qna_num == null}">
			<div style="margin:5px 0px 2px 0px;">다음글 : 다음글이 없습니다.</div>
			</c:if>
		</div>
		<!-- 다음글 이전글 보기 나오는 부분 끝-->		
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