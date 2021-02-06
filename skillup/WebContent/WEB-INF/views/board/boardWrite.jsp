<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글 작성</title>
<style>
input {
	border: 0;
	padding: 0;
	margin: 0;
}
#category {
	border: 0;
	border-right: thin solid lightgray;
	padding: 0;
	margin: 0;
	height: 48px;
	width: 17%;
}
#category-title > div {
	display: inline;
}
#category-title {
	margin: auto;
	height: 52px;
	border: thin solid lightgray;
}
#title {
	border-left: thin solid lightgray;
	height: 48px;
	width: 82%;
	margin: 0;
	border-left: 0;
}
#content {
	border: thin solid lightgray;
	border-top: 0;
}

button#submit {
	position: relative;
	left: 950px;
}
textarea {
	border: 0;
	padding: 15px;
	width: 1108px;
	margin: auto;
}
input, textarea {outline:none;}

#wrapper {
	height: 1080px;
}
</style>
</head>
<body>
<jsp:include page="../common/header.jsp"/>
<jsp:include page="/WEB-INF/views/message/messageButton.jsp"/>
<div id="wrapper">
<div class="container">

<nav class="navbar navbar-expand-lg navbar-light">
	<a class="navbar-brand" href="${contextPath}/board/list?boardNo=${boardNo}">
  <c:choose>
		<c:when test="${boardNo==1}">자유게시판</c:when>
		<c:when test="${boardNo==2}">공지사항</c:when>
		<c:when test="${boardNo==3}">Q&A</c:when>
	</c:choose>
  </a>
</nav>

	<form action="${contextPath}/board/insert?boardNo=${boardNo}" method="post" role="form" onsubmit="return boardValidate();">
		<div id="category-title">
				
		<c:choose>
			<c:when test="${boardNo==1}">
				<select id="category" name="category">
				<option value="1">자유</option>
				<option value="2">질문</option>
				<option value="3">홍보</option>
				<option value="4">후기</option>
				</select>
			</c:when>
			<c:when test="${boardNo==2}">
				<select id="category" name="category">
				<option value="1000">공지사항</option>
				</select>
			</c:when>
			<c:when test="${boardNo==3}">
				<select id="category" name="category">
				<option value="2000">Q&A</option>
				</select>
			</c:when>
		</c:choose>
				
				<input id="title" name="title" placeholder="제목을 입력해 주세요">
		</div>
		
		<div id="content">
			<textarea rows="25" name="content" style="outline:none"></textarea>
		</div>
		<br>
		<button type="button" class="btn btn-dark" id="boardlistbtn">뒤로가기</button>
		<button type="submit" class="btn btn-dark" id="submit">등록</button>
	</form>
</div>
</div>
<jsp:include page="../common/footer.jsp"/>
<script>
	$("#boardlistbtn").on("click", function() {
		path = "${contextPath}/board/list?boardNo=${boardNo}"
		location.href = path;
	});
	
	function boardValidate() {
		if ($("#title").val().trim().length == 0) {
			alert("제목을 입력해 주세요.");
			$("#boardTitle").focus();
			return false;
		}

		if ($("#content > textarea").val().trim().length == 0) {
			alert("내용을 입력해 주세요.");
			$("#boardContent").focus();
			return false;
		}
	}
</script>
</body>
</html>