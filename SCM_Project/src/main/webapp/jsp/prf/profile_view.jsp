<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="./css/prf/contentpage.css" /> 
<link rel="stylesheet" type="text/css"  href="./css/sns/sns_side.css">

<!-- 메인컨텐츠(SNS) sns내용 나오는 부분 CSS로드 -->
<link rel="stylesheet" type="text/css"  href="./css/sns/sns_content.css"> 
</head>
<body>
<div class="main-content">
<!-- 내정보 보기 -->
		<div class="user-area">
			<div class="user-pic">
					<c:if test="${ prfbean.PROFILE_PIC != null }"><img src="./upload/prf/${prfbean.PROFILE_PIC }" width="200px" height="273px"></c:if>
					<c:if test="${ prfbean.PROFILE_PIC == null }"><img src="./images/sns/noimg.png" width="200px" height="273px"></c:if>
				<img src="./upload/prf/${prfbean.PROFILE_PIC }" width="200px" height="273px">
			</div>
			<div class="user-textarea">
				<div class="user-text1">
					<span class="user-id">@${id }</span><br>
					<span class="user-name"> ${join_name}</span>
		 		</div>
		 			
			
				<div class="user-text2"><div><a href="./prf_write.prf">프로필작성</a></div></div>
				<div class="user-text3"><div><a href="./prf_view.prf"> 프로필 보기</a></div></div>
				<div class="user-text3"><div><a href="./prf_modify.prf"> 프로필 수정</a></div></div>				
				
			</div>
		</div>
		<!-- 내정보 보기 끝-->
	
	<!-- 게시판 내용보기 -->
<div class="content-area">
<form name="profile_form" method="post" enctype="multipart/form-data" action="prf_modify.prf">
	<div>
		
		<!-- 아이디, 이름, 패스워드 가져가는 부분 -->
		<input type="hidden" name="PROFILE_ID" value="${prfbean.PROFILE_ID }">
		<input type="hidden" name="PROFILE_USER" value="${prfbean.PROFILE_USER }">
		<div id="profile-pic">
			<div id="pic">
				<c:if test="${prfbean.PROFILE_PIC != null }">
					
				<img src="./upload/prf/${prfbean.PROFILE_PIC }" width="200px" height="273px">
 				</c:if>	
				<c:if test="${prfbean.PROFILE_PIC == null }">
					프로필사진을 설정해 주세요
				</c:if>	
			</div>
			<div id="present">	
				<ul>
					<li>${prfbean.PROFILE_USER }</li>
					<li> 회사명 : 
						<c:if test="${ carrer[5] != null }">${carrer[5] }</c:if>
						<c:if test="${ carrer[5].trim() == null }">현제 등록된 직업이 없습니다.</c:if>
					</li>
					<li>
					 	관심분야 :
						<c:if test="${ interest[0] != null }">${interest[0] }</c:if>
						<c:if test="${ interest[0].trim() == null }">현제 등록된 관심분야가 없습니다.</c:if>
					</li>
					<li>
					 	나이 :
						<c:if test="${ age[0] != null }">&nbsp;${age[0]}&nbsp;&nbsp;&nbsp;생년월일 : ${age[1]} </c:if>
						<c:if test="${ age[0].trim() == null }">현제 등록된 나이가 없습니다.</c:if>
					</li>
				</ul>
			</div>		
		</div>
		
		<div id="adu">
			<span id="location-1"></span>최종학력
			<table>
				<tr>
					<th width="165px">재학기간</th>
					<th width="130px">학교명</th>
					<th width="130px">전공</th>
					<th>학점</th>
				</tr>
				
				<tr>
					<td><center>
						${edu[4] } : ${edu[0] } . ${edu[1] } ~&nbsp;&nbsp;&nbsp;<br> 
						&nbsp;&nbsp;&nbsp;${edu[5] } : ${edu[2] } . ${edu[3] }
					</center></td>
					<td><center>
						${edu[6] }<br>
						<c:if test="${ edu[6] == null}">
							검정고시
						</c:if>
					</center></td>
					<td><center>
						${edu[7] }<br>
					</center></td>
					<td>-</td>
				</tr>
				
				<tr>
					<td><center>
						${edu[12] } : ${edu[8] } . ${edu[9] } ~ &nbsp;&nbsp;&nbsp;<br> 
						&nbsp;&nbsp;&nbsp; ${edu[13] } : ${edu[10] } .  ${edu[11] } 
						<br><br><br><br>
					</center></td>
					<td><center>
						${edu[14] }<br>
						${edu[15] }<br>
						──────────────<br><br>
						${edu[16] }&nbsp;&nbsp;&nbsp;&nbsp;
						${edu[17] }
					</center></td>
					<td><center>
						${edu[18] }<br>
						${edu[19] }
					</center></td>
					<td>
						${edu[20] }<br>
						/<br>
						${edu[21] }${edu[22] }
					</td>
				</tr>
			</table>
		</div>
		
		<div id="carr">
			<span id="location-2"></span>경력사항
			<table>
				<tr>
					<th width="170px">근무기간</th>
					<th colspan="4">상세경력</th>
				</tr>
				<tr>
					<td rowspan="6" align="left">
						${carrer[0] }<br><br>
						${carrer[1] } . ${carrer[2] } ~ ${carrer[3] } . ${carrer[4] }<br>
						
					</td>
					<th width="70px">회사명</th>
					<td colspan="3" align="left">${carrer[5] }</td>
				</tr>
				<tr>
					<th>직종</th>
					<td colspan="3" align="left">${carrer[6] }</td>
				</tr>
				<tr>
					<th>근무부서</th>
					<td align="left">${carrer[7] }</td>
					<th width="70px">근무지역</th>
					<td  align="left">
						${carrer[8] }
					</td>
				</tr>
				<tr>
					<th>직급</th>
					<td align="left">
						${carrer[9] }<br>
						${carrer[10] } 년차
					</td>
					<th>연봉</th>
					<td align="left">${carrer[11] } ${carrer[12] }
					</td>
				</tr>
				<tr>
					<th>담당업무</th>
					<td colspan="3" align="left">${carrer[13] }</td>
				</tr>
				<tr>
					<th>퇴사사유</th>
					<td colspan="3" align="left">
						${carrer[14] }
					</td>
				</tr>
			</table>
		</div>
		
		<div id="inter">
		<span id="location-3"></span>관심분야
			<table>
				<tr>
					<th>관심분야 및 사용 언어</th>
					<td align="left">
						관심 분야 : ${interest[0] }&nbsp;&nbsp;&nbsp;&nbsp;
						주 사용 언어 : ${interest[1] }
					</td>
				</tr>
			</table>
		</div>
		
		<div id="local">
			<span id="location-4"></span>희망 취업 지역
			<table>
				<tr>
					<th width="100px">희망 취업 지역</th>
					<td align="left">
						&nbsp;&nbsp;&nbsp;&nbsp;
						<c:if test="${local[0] != null }">
							<c:forEach var="lo" items="${local }">
								${lo},
							</c:forEach>
						</c:if>
					</td>
				</tr>
			</table>
		</div>
		
		<div id="lisen">
			<span id="location-5"></span>자격증 정보
			<table>
				<tr>
					<th width="200px">자격증/면허증</th>
					<th>발행처/발행기관</th>
					<th>합격구분</th>
					<th width="100px">취득일</th>
				</tr>
				<tr>
					<td>${lisence[0] }</td>
					<td>${lisence[1] }</td>
					<td>${lisence[2] }</td>
					<td>
						${lisence[3] } . ${lisence[4] } 
					</td>
				</tr>
			</table>
		</div>
		
		<div id="lang">
			<span id="location-6"></span>외국어
			<table>
				<tr>
					<th width="100px">언어</th>
					<th>시험 종류</th>
					<th>점수(등급)</th>
					<th width="100px">취득일</th>
				</tr>
				<tr>
					<td>${langu[0] }</td>
					<td>${langu[1] }</td>
					<td>
						${langu[2] } 점 
						${langu[3] } 급
					</td>
					<td>
						${langu[4] } . ${langu[5] } 
					</td>
				</tr>
			</table>
		</div>
		
		<div id="intro">
			<span id="location-7"></span>자기소개
			<table>
				<tr>
					<th>개성있는 자기소개로 자신을 어필하세요</th>
				</tr>
				<tr>
					<td>
						${prfbean.PROFILE_INTRO}
					</td>
				</tr>
			</table>
		</div>
		<div id="enter" align="center">
			<input type="submit" value="프로필 수정하기" id="submit">
		</div>
	</div>
	<div class="spacer"></div>
</form>
</div>
</div>
</body>
</html>