<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="model.qna.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<style type="text/css">
	@import url(http://fonts.googleapis.com/earlyaccess/notosanskr.css);
</style>
			<!-- forEach문으로 반복해서 댓글이 나오게 하는 영역 -->
			<c:forEach var="r" items="${relist}">
			<!-- 댓글의 댓글일 경우 padding으로 칸을 늘려 준다 -->
			<c:if test="${r.qna_re_lev!=0}">
				<div class="qna_detail_reply_print" style="padding-left: 40px;">
					<div>
						<div style="float: left; margin-bottom: 7px;">
						
							<span style="font-family: 'Noto Sans KR', sans-serif;">${r.qna_re_id}</span>
							<span style="font-family: Dotum; color: #5d5d5d; padding-top: 2px; margin: 0px 7px 0px 7px;">${r.qna_re_date}</span>
							<span style="font-size: 8pt; color: #5781b5; cursor : pointer;font-family: 'Noto Sans KR', sans-serif;" id="qna_reply_reply" class="${r.qna_re_ref}_${r.qna_re_seq}">└답글</span>
							
						</div>
						<div style="float: right; font-size: 9pt; color: #8C8C8C;">
							<c:if test="${id==r.qna_re_id}">
							<span style="color: #5D5D5D; cursor : pointer" id="qna_reply_delete2" class="${r.qna_re_ref}_${r.qna_re_seq}">삭제</span>
							<span style="color:#c1c1c1; font-size: 9pt; margin: 0px 5px 0px 5px">|</span>
							<span style="color: #5D5D5D; cursor : pointer" id="qna_reply_edit" class="${r.qna_re_ref}_${r.qna_re_seq}_${r.qna_re_body}">수정</span>
							</c:if>
						</div>
						<div style="clear: both;"></div>
					</div>
					
					<div style="color: #707070">
						<pre  style="color: #595959; font-size: 9pt;" id="qna_re_body${r.qna_re_ref}${r.qna_re_seq}">${r.qna_re_body}</pre>
					</div>
					<div id="${r.qna_re_ref}${r.qna_re_seq}" class="qna_reply_append_box">
					<!-- 댓글의 댓글을 작성시 이곳에 작성 폼이 나타난다. -->
					</div>
				</div>
			</c:if>
			<c:if test="${r.qna_re_lev==0}">
				<div class="qna_detail_reply_print">
					<div>
						<div style="float: left; margin-bottom: 7px;">
						
							<span style="font-family: 'Noto Sans KR', sans-serif;">${r.qna_re_id}</span>
							<span style="font-family: Dotum; color: #5d5d5d; padding-top: 2px; margin: 0px 7px 0px 7px;">${r.qna_re_date}</span>
							<span style="font-size: 8pt; color: #5781b5; cursor : pointer;font-family: 'Noto Sans KR', sans-serif;" id="qna_reply_reply" class="${r.qna_re_ref}_${r.qna_re_seq}">└답글</span>
						</div>
						<div style="float: right; font-size: 9pt; color: #8C8C8C;">
							<c:if test="${id==r.qna_re_id}">
							<span style="color: #5D5D5D; cursor : pointer" id="qna_reply_delete2" class="${r.qna_re_ref}_${r.qna_re_seq}">삭제</span>
							<span style="color:#c1c1c1; font-size: 9pt; margin: 0px 5px 0px 5px">|</span>
							<span style="color: #5D5D5D; cursor : pointer" id="qna_reply_edit" class="${r.qna_re_ref}_${r.qna_re_seq}_${r.qna_re_body}">수정</span>
							</c:if>
						</div>
						<div style="clear: both;"></div>
					</div>
					
					<div style="color: #707070">
						<pre  style="color: #595959; font-size: 9pt;" id="qna_re_body${r.qna_re_ref}${r.qna_re_seq}">${r.qna_re_body}</pre>
					</div>
					<div id="${r.qna_re_ref}${r.qna_re_seq}" class="qna_reply_append_box">
					<!-- 댓글의 댓글을 작성시 이곳에 작성 폼이 나타난다. -->
					</div>
				</div>
			</c:if>
			</c:forEach>
				<!-- forEach문으로 반복해서 댓글이 나오게 하는 영역 끝-->
				
				<input type="hidden" value="${replyCount}" id="qna_re_count"/>
				
				
				<div class="qna_detail_reply_write">
					
					<TEXTAREA class="qna_detail_reply_write_text" COLS=69 ROWS=4 name="qna_re_body"></TEXTAREA>
					<input type="button" value="등록" class="qna_detail_reply_write_button" id="reply_button"/>
				
				</div>