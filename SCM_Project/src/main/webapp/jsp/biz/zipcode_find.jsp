<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- JSTL 태그라이브러리 -->

<html>
<head>
<title>우편번호 검색</title>

<SCRIPT>
	/* select 객체에서 검색된 동을 선택했을때 change 이벤트가 발생합니다.
	   zip1 변수에는 우편번호 앞 세자리를 저장하고 
	   zip2 변수에는 우편번호 뒷자리 세자리를 저장한다. 
	   addr2변수에는 나머지 주소를 저장시켜준다.
	 */

	function selectnow() {

		var zip = document.postform.post_list.value;
		//zip의 값이 '123-456 서울시 개포1동 ~ ' 인 경우
		var zip1 = zip.substring(0, 3);//0이상 3미만사이의 첫번째 우편번호(123 추출)
		var zip2 = zip.substring(4, 7);//4이상 7미만사이의 두번째 우편번호 (456 추출)
		var addr2 = zip.substring(7, (zip.length)); //7이상의 끝문자까지 나머지 주소값을 저장

		//opener객체는 우편번호 공지창에서 본 회원가입창을 가리킵니다.
		/* 회원가입창에 첫번째 세자리 우편번호를 입력합니다. */
		opener.document.biz_board_write_form.biz_zipcode_1.value = zip1;

		/* 회원가입창에 두번째 세자리 우편번호를 입력합니다. */
		opener.document.biz_board_write_form.biz_zipcode_2.value = zip2;

		/* 회원가입창에 나머지 주소를  입력합니다. */
		opener.document.biz_board_write_form.biz_loc_1.value = addr2;

		/* 그리고 팝업 윈도우 창을 닫아줍니다. */
		parent.window.close();
	}

	//동을 입력하지 않을 경우 체크 합니다.
	function check(form) {
		if (form.dong.value == "") {
			alert("동을 입력해 주세요!");
			form.dong.focus();
			return false;
		}
	}
</SCRIPT>
<style>
INPUT, SELECT {
	font-family: Dotum;
	font-size: 9pt;;
}

.style1 {
	color: #466D1B;
	font-weight: bold;
	font-size: 12px;
}
</style>
</head>
<body onload="postform.dong.focus();" bgcolor="#FFFFFF" topmargin="0"
	leftmargin="0">
	<form method="post" action="zicode_find_ok.nhn" name="postform"
		onsubmit="return check(this)">
		<table width="414" height="100" border="0" align="center"
			cellpadding="0" cellspacing="0">
			<tr>
				<td bgcolor="#999999" align="center">
					<input type="image" src="images/biz/ZipCode_img01.gif" width="413" height="58">
				</td>
			</tr>
			<tr>
				<td bgcolor="f5ffea" align="center">
					<strong><font color="#466d1b">
					<span class="style1">[거주지의 면.동을 입력하고 '찾기'버튼을 누르세요!!!]</span> 
					</font></strong>
				</td>
			</tr>
			<tr height="30">
				<td bgcolor="f5ffea" align="center">
					<input type="text" name="dong" size="10" maxlength="10">
					 &nbsp; <input type="image" src="images/biz/ZipCode_img02.gif" width="69" height="19">
				</td>
			</tr>

			<!-- 동을 입력했다면 실행되는 JSTL if 문  -->
			<c:if test="${!empty dong}">
				<!-- 검색결과 주소값이 있을 경우만 실행되는 JSTL if 문 -->
				<c:if test="${!empty zipcodelist}">
					<tr>
						<td bgcolor="f5ffea" height="30" align="center">
						<SELECT NAME="post_list">
							<option value="">----주소를 선택하세요----</option>
							<!-- items 속성에는 검색 결과의 주소값을 담고 있는 키값을 적습니다.
    		                  addr2 변수에는 주소값을 받아서 저장합니다.-->
								<c:forEach var="addr2" items="${zipcodelist}">
								<%-- addr2.zipcode에는 ZipcodeBean 클래스의 getZipcode()메서드에서 구해온   우편번호,
                      				addr2.addr에는 ZipcodeBean 클래스의 getAddr()메서드를 가져와 시도 구군 동을 합친 값을 가져와서
									변수 totaladdr에 저장합니다. 우편번호 시도 구군 동이 저장됩니다. 
									예)[123-456] 서울시 개포1동 ~    --%>
									<c:set var="totaladdr" value="${addr2.zipcode}${addr2.addr}" />
									<%-- 결과 받아온 대로 option을 만듭니다. --%>
									<option value="${totaladdr}">[${addr2.zipcode}]&nbsp;${addr2.addr}</option>
								</c:forEach>
								<!-- JSTL c:forEach 반복문 -->
						</SELECT> <input type="button" value="확인" onclick="selectnow()" />
						</td>
					</tr>
				</c:if>

				<!-- JSTL 에서 검색 주소값이 없을 경우 실행되는 if 문. -->
				<c:if test="${empty zipcodelist}">
					<tr>
						<td bgcolor="f5ffea" height="30" align="center">
						<font color="#466d1b"><span class="style1">검색 결과가 없습니다.</span></font>
						</td>
					</tr>
				</c:if>
			</c:if>
			<tr>
				<td bgcolor="508C0F" colspan="3" height="3"></td>
			</tr>
		</table>
	</form>
</body>
</html>