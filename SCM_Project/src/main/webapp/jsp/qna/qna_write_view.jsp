<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- 구분자 사용을 위한 js파일 호출 -->
<html>
<head>

<script>
$(document).ready(function() {
	  $('pre code').each(function(i, block) {
	    hljs.highlightBlock(block);
	  });
	});
</script>

<style>
	pre{
		margin: 0px;
		padding: 0px;
		height: 305px;
		width: 545px;
		height: 305px;
	}
</style>
 
</head>
<body>
<pre><code>${param.codetext}</code></pre>


</body>

</html>