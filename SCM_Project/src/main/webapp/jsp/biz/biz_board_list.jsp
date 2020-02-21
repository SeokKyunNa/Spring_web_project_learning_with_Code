<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="model.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css"  href="./css/biz/biz_board_list.css">
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script src="./js/biz/biz_board_list.js"></script>
<script>
    $(document).ready(function(){
 	     str = "#viewcount option:eq(" + ${index} + ")" ;
 	    $(str).prop("selected",true);
    });
</script>
<title>채용공고 목록</title>
</head>
<body>
	<input type="hidden" id="boardForm" value="${boardForm }">
	<div class="biz_main_nsk">
		<div class="biz_btnList_nsk">
			<ul style="float: left">
				<a href="biz_list.biz?boardForm=all">
					<c:if test="${boardForm==''||boardForm==null||boardForm=='all'}">
						<li class="li_select_nsk">
							전체 목록
						</li>
					</c:if>
					<c:if test="${boardForm=='myBoard'||boardForm=='favoriteBoard'}">
						<li class="li_nsk">
							전체 목록
						</li>
					</c:if>
				</a>
				<a href="biz_list_mine.biz?boardForm=myBoard">
					<c:if test="${boardForm=='myBoard'}">
						<li class="li_select_nsk">
							내가 등록한 채용공고
						</li>
					</c:if>
					<c:if test="${boardForm==''||boardForm==null||boardForm=='all'||boardForm=='favoriteBoard'}">
						<li class="li_nsk">
							내가 등록한 채용공고
						</li>
					</c:if>
				</a>
				<a href="biz_list_favorite.biz?boardForm=favoriteBoard">
					<c:if test="${boardForm=='favoriteBoard'}">
						<li class="li_select_nsk">
							즐겨찾기한 채용공고
						</li>
					</c:if>
					<c:if test="${boardForm==''||boardForm==null||boardForm=='all'||boardForm=='myBoard'}">
						<li class="li_nsk">
							즐겨찾기한 채용공고
						</li>
					</c:if>
				</a>
				<c:if test="${boardForm=='searchBoard'}">
					총 검색 개수 : ${listcount } 개
				</c:if>
			</ul>
			
		</div>
		<div class="biz_top_nsk">
			<div style="float: left">
				<input type="button" class="biz_list_btn_nsk" value="채용공고 등록" 
				onClick="location='biz_write.biz'"/>
			</div>
			<div style="float: right">
				<select id="viewcount">
					<option value="3">3줄씩 보기</option>
					<option value="5">5줄씩 보기</option>
					<option value="10" selected>10줄씩 보기</option>
					<option value="15">15줄씩 보기</option>
				</select>
			</div>
		</div>
		
		<table class="biz_list_nsk">
			<thead>
				<tr bgcolor= "#F5F8F8">
					<td width="200px">회사명</td>
					<td width="450px">직무(고용형태/급여/학력/경력)</td>
					<td width="130px">지역</td>
					<td width="90px">수정일</td>
					<td width="90px">마감일</td>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="b" items="${bizList}">
				<tr>
					<td>
						<c:if test="${b.biz_favorite_on==0}">
							<a href="./biz_favorite_on.biz?biz_num_favorite=${b.biz_num}&page=${page}&boardForm=${boardForm}"><img src="images/biz/favorite_off.png" width="15px" height="15px" ></a>
						</c:if>
						<c:if test="${b.biz_favorite_on==1}">
							<a href="./biz_favorite_off.biz?biz_num_favorite=${b.biz_num}&page=${page}&boardForm=${boardForm}"><img src="images/biz/favorite_on.png" width="15px" height="15px" ></a>
						</c:if>
					&nbsp;${b.biz_name }
					</td>
					<td><a href="./biz_getCont.biz?biz_num=${b.biz_num}&page=${page}&state=cont&boardForm=${boardForm}" style="font-size: 16px;">${b.biz_subject }</a></br>
					<font style="font-size: 11px; color: gray;">${b.biz_pattern }&nbsp;|&nbsp;${b.biz_pay }
												&nbsp;|&nbsp;${b.biz_education }&nbsp;|&nbsp;${b.biz_career }</font></td>
					<td style="text-align: center;">${b.biz_loc_1 }</td>
					<td>${b.biz_date }</td>
					<td>${b.biz_expiry_date }</td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
		<div class="biz_list_pageNum_nsk">
			
			<ul>
				<c:if test="${page <= 1 }">
					<li>《&nbsp;&nbsp;</li>
				</c:if>
				<c:if test="${page > 1 }">
					 <li><a href="#" onClick="prePage(${page-1})">《&nbsp;&nbsp;</a></li>
				</c:if>
				<c:forEach var="a" begin="${startpage}" end="${endpage}">
					<c:if test="${a == page }">
						<li>${a}</li>
					</c:if>
					<c:if test="${a != page }">
						<li><a href="#" onClick="movePage(${a})">${a}</a></li>
					</c:if>
				</c:forEach>
				<c:if test="${page >= maxpage }">
					<li>&nbsp;&nbsp;》</li>
				</c:if>
				<c:if test="${page < maxpage }">
					<li><a href="#" onClick="nextPage(${page+1})">&nbsp;&nbsp;》</a></li>
				</c:if>
			</ul>
		</div>
		<div class="biz_search_nsk">
			<form id="biz_search_form" name="biz_search_form" 
			action="./biz_list_search1.biz" method="get">
				<select id="biz_search_select" name="biz_search_select">
					<option value="bizName" selected="selected">회사명</option>
					<option value="bizSubject">제목</option>
					<option value="bizLoc">지역</option>
				</select>
				<input type="hidden" id="boardForm" name="boardForm" value="searchBoard">
				<input type="text" id="biz_search" name="biz_search" class="search_text_nsk" />
				<input type="submit" id="search_btn" name="search_btn" value="검색" class="search_btn_nsk" />
			</form>
		</div>
		<div class="spacer"></div>
	</div>
	
</body>
</html>