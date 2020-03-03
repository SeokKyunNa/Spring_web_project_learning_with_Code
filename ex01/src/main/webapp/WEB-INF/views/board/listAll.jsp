<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@include file="../include/header.jsp"%>

<script>
	var result = '${msg}'
	
	if(result == 'SUCCESS'){
		alert("처리가 완료되었습니다.");
	}
</script>

<table class="table table-bordered">
	<tr>
		<th style="width: 10px">BNO</th>
		<th>TITLE</th>
		<th>WRITER</th>
		<th>REGDATE</th>
		<th>UPDATEDATE</th>
		<th style="width: 40px">VIEWCNT</th>
	</tr>
</table>

<%@include file="../include/footer.jsp"%>
