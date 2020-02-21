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
<script>
		function readURL(input){
			var reader = new FileReader();
			reader.onload=function(e){
				$('#UploadedImg').attr('src',e.target.result);
			}
			reader.readAsDataURL(input.files[0]);
		}
		
		$(document).ready(function() {
			$("form").submit(function() {
				if ($("#PROFILE_PIC").val() == "") {
					alert("프로필사진을 등록하세요");
					$("#PROFILE_PIC").focus();
					return false;
				}
			});
		});
		
	</script>  
</head>
<body>
<div class="main-content">
<!-- 내정보 보기 -->
		<div class="user-area">
			<div class="user-pic">
				<c:if test="${ prfbean.PROFILE_PIC != null }"><img src="./upload/prf/${prfbean.PROFILE_PIC }" width="200px" height="273px"></c:if>
					<c:if test="${ prfbean.PROFILE_PIC == null }"><img src="./images/sns/noimg.png" width="200px" height="273px"></c:if>
			</div>
			<div class="user-textarea">
				<div class="user-text1">
					<span class="user-id">@${id }</span><br>
					<span class="user-name"> ${join_name}</span>
		 		</div>
			
				<div class="user-text2"><div><a href="./prf_write.prf">프로필작성</a></div></div>
				<div class="user-text3"><div><a href="./prf_view.prf"> 내프로필 보기</a></div></div>
				<div class="user-text3"><div><a href="./prf_modify.prf"> 프로필 수정</a></div></div>
			</div>
		</div>
		<!-- 내정보 보기 끝-->
	
	<!-- 게시판 내용보기 -->
<div class="content-area">
<form name="profile_form"  method="post" enctype="multipart/form-data" action="prf_write_ok.prf">
	<div>
		<div id="profile-pic">
			<div id="pic">
				<input type="file" onchange="readURL(this)" name="PROFILE_PIC" id="PROFILE_PIC">
				<c:if test="${prfbean.PROFILE_PIC != null }">
				<img src="./upload/prf/${prfbean.PROFILE_PIC}" id="UploadedImg" width="200px">
				</c:if>	
				<c:if test="${prfbean.PROFILE_PIC == null }">
					<img id="UploadedImg" src="" width="200" height="250">
				</c:if>
					
			</div>
			<div id="present">
				<ul>
					<li class="a1">${prfbean.PROFILE_USER }</li>
					<li>
							회사명 :
						<c:if test="${ carrer[5] != null }">${carrer[5] }</c:if>
						<c:if test="${ carrer[5].trim() == null }">현제 등록된 직업이 없습니다.</c:if>
					</li>
					<li>
					관심분야 :
						<c:if test="${ interest[0] != null }">${interest[0] }</c:if>
						<c:if test="${ interest[0].trim() == null }">현제 등록된 관심분야가 없습니다.</c:if>
					</li>
				</ul>
				 &nbsp;&nbsp;&nbsp;&nbsp;나이<input type="text" size="5"value="${age[0] }" name="PROFILE_AGE"/>
		  	   생년월일&nbsp;&nbsp;<input type="text" size="10"value="${age[1]}" name="PROFILE_AGE"/>
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
						<input type="text"  id="year" name="PROFILE_EDU" value="${edu[0] }"/> . <input type="text" id="month" name="PROFILE_EDU" value="${edu[1] }"> ~ <input type="text"  id="year" name="PROFILE_EDU" value="${edu[2] }"/> . <input type="text" id="month" name="PROFILE_EDU" value="${edu[3] }"><br>
						<select id="type-a" name="PROFILE_EDU"> 
							<option>입학</option>
							<option>편입</option>
						</select>&nbsp;&nbsp;&nbsp;
						<select id="type-a" name="PROFILE_EDU">
							<option>졸업</option>
							<option>재학중</option>
							<option>휴학중</option>
							<option>자퇴</option>
							<option>중퇴</option>
						</select>
					</center></td>
					<td><center>
						<input type="text" id="school" name="PROFILE_EDU" value="${edu[6]}"><br>
						<input type="checkbox" id="check1" name="PROFILE_EDU" value="검정고시"> 대입 검정고시 (고졸)
					</center></td>
					<td><center>
						<select id="option4" name="PROFILE_EDU">
							<option>이과 계열</option>
							<option>문과 계열</option>
							<option>예체능 계열</option>
							<option>실업계</option>
						</select><br>
					</center></td>
					<td>-</td>
				</tr>
				
				<tr>
					<td><center>
						<input type="text"  id="year" name="PROFILE_EDU" value="${edu[8] }"/> . <input type="text" id="month" name="PROFILE_EDU" value="${edu[9] }"> ~ <input type="text"  id="year" name="PROFILE_EDU" value="${edu[10] }"/> . <input type="text" id="month" name="PROFILE_EDU" value="${edu[11] }"><br>
						<select id="type-a" name="PROFILE_EDU"> 
							<option>입학</option>
							<option>편입</option>
						</select>&nbsp;&nbsp;&nbsp;
						<select id="type-a" name="PROFILE_EDU">
							<option>졸업</option>
							<option>재학중</option>
							<option>휴학중</option>
							<option>자퇴</option>
							<option>중퇴</option>
						</select><br><br><br><br>
					</center></td>
					<td><center>
						<input type="text" name="PROFILE_EDU" value="${edu[14] }">
						<select id="yearing" name="PROFILE_EDU">
							<option>대학(2,3년)</option>
							<option>대학교(4년)</option>
							<option>대학원(석사)</option>
							<option>대학원(박사)</option>
						</select><br>
						──────────────<br>
						<select id="type-a" name="PROFILE_EDU">
							<option>주간</option>
							<option>야간</option>
						</select>&nbsp;&nbsp;&nbsp;&nbsp;
						<select id="type-a" name="PROFILE_EDU">
							<option>지역</option>
							<option>서울</option>
							<option>대전</option>
							<option>대구</option>
							<option>부산</option>
							<option>목포</option>
							<option>유럽</option>
							<option>북극</option>
						</select>
					</center></td>
					<td><center>
						<select id="option4" name="PROFILE_EDU">
							<option>예체능 계열</option>
							<option>의과 계열</option>
							<option>공과 계열</option>
							<option>컴퓨터 계열</option>
							<option>법학 계열</option>
							<option>경영학 계열</option>
						</select><br>
						<input type="text" id="hakgha" name="PROFILE_EDU" value="${edu[19] }">
						──────────────<br>
						<input type="checkbox" id="check1" name="PROFILE_EDU" value="복수전공"> 부전공(복수전공)<br><br>
					</center></td>
					<td>
						<input type="text" id="year" name="PROFILE_EDU" value="${edu[20] }"><br>
						/<br>
						<input type="text" id="year" name="PROFILE_EDU" value="${edu[21] }">
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
						<select style="color: gray;" name="PROFILE_CARRER">
							<option>퇴사</option>
							<option>재직중</option>
						</select><br><br>
						<input type="text"  id="year" name="PROFILE_CARRER" value="${carrer[1] }"/> . <input type="text" id="month" name="PROFILE_CARRER" value="${carrer[2] }"> ~ <input type="text"  id="year" name="PROFILE_CARRER" value="${carrer[3] }"/> . <input type="text" id="month" name="PROFILE_CARRER" value="${carrer[4] }"><br>
						
					</td>
					<th width="70px">회사명</th>
					<td colspan="3" align="left"><input type="text" style="width: 250px; " name="PROFILE_CARRER" value="${carrer[5] }"></td>
				</tr>
				<tr>
					<th>직종</th>
					<td colspan="3" align="left"><input type="text" style="width: 250px; " name="PROFILE_CARRER" value="${carrer[6] }"></td>
				</tr>
				<tr>
					<th>근무부서</th>
					<td align="left"><input type="text" style="width: 100px; " name="PROFILE_CARRER" value="${carrer[7] }"></td>
					<th width="70px">근무지역</th>
					<td  align="left">
						<select style="width: 80px; color: gray;" name="PROFILE_CARRER">
							<option>서울</option>
							<option>대구</option>
							<option>대전</option>
							<option>부산</option>
							<option>강원</option>
						</select>
					</td>
				</tr>
				<tr>
					<th>직급</th>
					<td align="left">
						<select style="width: 100px; color: gray;" name="PROFILE_CARRER">
							<option>인턴</option>
							<option>사원</option>
							<option>주임</option>
							<option>계장</option>
							<option>대리</option>
							<option>과장</option>
							<option>차장</option>
							<option>부장</option>
						</select><br>
						<input type="text" style="width: 20px; margin-top: 5px;" name="PROFILE_CARRER" value="${carrer[10] }"> 년차
					</td>
					<th>연봉</th>
					<td align="left"><input type="text" style="width: 60px;" name="PROFILE_CARRER" value="${carrer[11] }">
						<select style="color: gray;" name="PROFILE_CARRER">
							<option>만원</option>
							<option>달러</option>
							<option>엔</option>
						</select>
					</td>
				</tr>
				<tr>
					<th>담당업무</th>
					<td colspan="3"><textarea rows="5" cols="45" name="PROFILE_CARRER">${carrer[13]}</textarea></td>
				</tr>
				<tr>
					<th>퇴사사유</th>
					<td colspan="3" align="left">
						<select style="color: gray; width: 130px;" name="PROFILE_CARRER">
							<option>업직종 전환</option>
							<option>근무조건</option>
							<option>경영학화</option>
							<option>계약만료</option>
							<option>학업</option>
						</select>
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
						<select style="width:200px;" name="PROFILE_INTEREST">
							<option>웹기획</option>
							<option>웹디자인</option>
							<option>웹프로그래머</option>
							<option>응용프로그래머</option>
							<option>시스템프로그래머</option>
							<option>ERP.시스템분석.설계</option>
							<option>서버.네트워크.보안</option>
							<option>데이터베이스.DBA</option>
							<option>웹표준.UI개발</option>
							<option>컨텐츠.사이트운영</option>
						</select>&nbsp;&nbsp;&nbsp;&nbsp;
						주 사용 언어 : 
						<select style="width:120px" name="PROFILE_INTEREST">
							<option>C</option>
							<option>C++</option>
							<option>C#</option>
							<option>java</option>
							<option>Python</option>
							<option>Ruby</option>
							<option>node.js</option>
							<option>PHP</option>
						</select>
					</td>
				</tr>
			</table>
		</div>
		
		<div id="local">
			<span id="location-4"></span>희망 취업 지역
			<table>
				<tr>
					<th width="100px">서울</th>
					<td>
						<table id="local-table">
							<tr>
								<td align="left"><input type="checkbox" name="PROFILE_LOCAL" value="서울전체" checked>서울전체</td>
								<td><input type="checkbox" name="PROFILE_LOCAL" value="강남구">강남구</td>
								<td><input type="checkbox" name="PROFILE_LOCAL" value="강동구">강동구</td>
								<td><input type="checkbox" name="PROFILE_LOCAL" value="강서구">강서구</td>
								<td><input type="checkbox" name="PROFILE_LOCAL" value="금천구">금천구</td>
							</tr>
							<tr>
								<td><input type="checkbox" name="PROFILE_LOCAL" value="양천구">양서구</td>
								<td><input type="checkbox" name="PROFILE_LOCAL" value="노원구">노원구</td>
								<td><input type="checkbox" name="PROFILE_LOCAL" value="도봉구">도봉구</td>
								<td><input type="checkbox" name="PROFILE_LOCAL" value="동대문구">동대문구</td>
								<td></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<th>경기</th>
					<td>
						<table id="local-table">
							<tr>
								<td align="left"><input type="checkbox" name="PROFILE_LOCAL" value="경기전체">경기전체</td>
								<td><input type="checkbox" name="PROFILE_LOCAL" value="가평군">가평군</td>
								<td><input type="checkbox" name="PROFILE_LOCAL" value="광명시">광명시</td>
								<td><input type="checkbox" name="PROFILE_LOCAL" value="광주시">광주시</td>
								<td><input type="checkbox" name="PROFILE_LOCAL" value="구리시">구리시</td>
							</tr>
							<tr>
								<td><input type="checkbox" name="PROFILE_LOCAL" value="부천시">부천시</td>
								<td><input type="checkbox" name="PROFILE_LOCAL" value="성남시">성남시</td>
								<td><input type="checkbox" name="PROFILE_LOCAL" value="안양시">안양시</td>
								<td><input type="checkbox" name="PROFILE_LOCAL" value="의왕시">의왕시</td>
								<td><input type="checkbox" name="PROFILE_LOCAL" value="오산시">오산시</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<th>부산</th>
					<td>
						<table id="local-table">
							<tr>
								<td align="left"><input type="checkbox" name="PROFILE_LOCAL" value="부산전체">부산전체</td>
								<td><input type="checkbox" name="PROFILE_LOCAL" value="강서구">강서구</td>
								<td><input type="checkbox" name="PROFILE_LOCAL" value="금정구">금정구</td>
								<td><input type="checkbox" name="PROFILE_LOCAL" value="기장군">기장군</td>
								<td><input type="checkbox" name="PROFILE_LOCAL" value="남구">남구</td>
							</tr>
							<tr>
								<td><input type="checkbox" name="PROFILE_LOCAL" value="동구">동구</td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
						</table>
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
					<td><input type="text" name="PROFILE_LISENCE" value="${lisence[0] }"></td>
					<td><input type="text" name="PROFILE_LISENCE" value="${lisence[1] }"></td>
					<td>
						<select name="PROFILE_LISENCE">
							<option>1차합격</option>
							<option>2차합격</option>
							<option>실기합격</option>
							<option>필기합격</option>
							<option>최종합격</option>
						</select>
					</td>
					<td>
						<input type="text"  id="year" name="PROFILE_LISENCE" value="${lisence[3] }"/> . <input type="text" id="month" name="PROFILE_LISENCE" value="${lisence[4] }"> 
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
					<td>
						<select name="PROFILE_LANGU">
							<option>영어</option>
							<option>중국어</option>
							<option>일어</option>
							<option>독일어</option>
						</select>
					</td>
					<td><input type="text" name="PROFILE_LANGU" value="${langu[1] }"></td>
					<td>
						
						<input type="text" style="width: 35px;" name="PROFILE_LANGU" value="${langu[2] }"> 점 
						<input type="text" style="width: 25px;" name="PROFILE_LANGU" value="${langu[3] }"> 급
					</td>
					<td>
						<input type="text"  id="year" name="PROFILE_LANGU" value="${langu[4] }"/> . <input type="text" id="month" name="PROFILE_LANGU" value="${langu[5] }"> 
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
						<textarea cols="80" rows="6" name="PROFILE_INTRO">${prfbean.PROFILE_INTRO}</textarea>
					</td>
				</tr>
			</table>
		</div>
		<div id="enter" align="center">
			<input type="submit" value="프로필 수정" id="submit">
		</div>
	</div>
	<div class="spacer"></div>
</form>
</div>
</div>
</body>
</html>