<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="model.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css"  href="./css/applicant/applicant_list.css">
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script>
    $(document).ready(function(){
 	     str = "#viewcount option:eq(" + ${index} + ")" ;
 	    $(str).prop("selected",true);
    });
</script>
<title>채용공고 목록</title>
</head>
<body>
	<div class="biz_main_nsk">
		<div class="biz_top_nsk">
			<div style="float: left">
				<input type="button" value="채용공고로 돌아가기" 
				 id="applicant_list_btn_nsk" name="applicant_list_btn_nsk" class="applicant_list_btn_nsk"
				 onclick="location='biz_getCont.biz?biz_num=${biz_num}&page=${page}&state=cont&boardForm=${boardForm}'"/>
			</div>
			<div style="float: right">
				<select id="viewcount">
					<option value="5">5명씩 보기</option>
					<option value="10">10명씩 보기</option>
					<option value="15">15명씩 보기</option>
					<option value="20">20명씩 보기</option>
				</select>
			</div>
		</div>
		
		<table class="biz_list_nsk">
			<thead>
				<tr bgcolor= "#F5F8F8">
					<td width="130px">프로필 사진</td>
					<td width="150px">이름</td>
					<td width="100px">나이</td>
					<td width="200px">지원일</td>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="applicant" items="${applicantList}">
				<tr>
					<td>
						<c:if test="${applicant.applicant_pic!=null}">
							<img src='./upload/prf/${applicant.applicant_pic}' style="width:100px; height:130px;"/>
						</c:if>
					</td>
					<td>
						<a href="#" onClick="window.open('./applicant_detail.biz?biz_num=${applicant.biz_num}&page=${page}&applicant_id=${applicant.applicant_id}',
						'지원자', 'width=1000, height=800, toolbar=no, menubar=no, scrollbars=no, resizable=yes, left=200px');return false;" 
						style="font-size: 16px;">${applicant.applicant_name }</a>
					</td>
					<td style="text-align: center;">${fn:substring(applicant.applicant_age, 0, 2) }</td>
					<td>${applicant.apply_date }</td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
		<div class="biz_list_pageNum_nsk">
			<ul>
				<c:if test="${a_page <= 1 }">
					<li>《&nbsp;&nbsp;</li>
				</c:if>
				<c:if test="${a_page > 1 }">
					 <li><a href="./applicant_list.biz?biz_num=${biz_num}&page=${page}&a_page=${a_page-1}">《&nbsp;&nbsp;</a></li>
				</c:if>
				<c:forEach var="a" begin="${startpage}" end="${endpage}">
					<c:if test="${a == a_page }">
						<li>${a}</li>
					</c:if>
					<c:if test="${a != a_page }">
						<li><a href="./applicant_list.biz?biz_num=${biz_num}&page=${page}&a_page=${a}">${a}</a></li>
					</c:if>
				</c:forEach>
				<c:if test="${a_page >= maxpage }">
					<li>&nbsp;&nbsp;》</li>
				</c:if>
				<c:if test="${a_page < maxpage }">
					<li><a href="./applicant_list.biz?biz_num=${biz_num}&page=${page}&a_page=${a_page+1}">&nbsp;&nbsp;》</a></li>
				</c:if>
			</ul>
		</div>
	</div>
	<div class="spacer"></div>
</body>
</html>