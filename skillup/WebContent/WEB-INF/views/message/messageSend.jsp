<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메세지 보내기</title>
<link rel="stylesheet" href="${contextPath}/resources/css/bootstrap.css">
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script type="text/javascript"
	src="${contextPath}/resources/js/bootstrap.js"></script>
<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>

<style>
.swal-button,
.swal-button:active {
   background: #5f0081;
   box-shadow: 0 0 0 1px #fff, 0 0 0 3px rgba(143,114,165,.29);
}

.swal-button:not([disabled]):hover {
   background: #5f0081;
   box-shadow: 0 0 0 1px #fff, 0 0 0 3px rgba(143,114,165,.29);
}
#wrapper {
	width: 100%;
	height: 700px;
}
#submit {
	float:right;
}
.form {
	margin-top: 30px;
}
.navbar {
	padding-left: 0;
	border-bottom: thick solid black;
}
.navbar-brand {
	margin-top:35px;
	font-size: 30px;
}
@font-face {
    font-family: 'paybooc-Medium';
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_20-07@1.0/paybooc-Medium.woff') format('woff');
    font-weight: normal;
    font-style: normal;
}
* {
	font-family: 'paybooc-Medium';
}
</style>
</head>
<body>
<div id="wrapper">
	<div class="container">
	<nav class="navbar navbar-light">
  <span class="navbar-brand mb-0">${memberName} 님에게 쪽지 보내기</span>
	</nav>
		<form action="${contextPath}/message/insert" method="post" onsubmit="return boardValidate();">
		<div class="form">
		  <div class="form-group">
		    <label for="exampleFormControlInput1">받는사람</label>
		    <input class="form-control" id="receiveMember" name="email" value="${email}">
		  </div>
		  <div class="form-group">
		    <label for="exampleFormControlTextarea1">내용</label>
		    <textarea class="form-control" id="content" name="content" rows="12"></textarea>
		  </div>
		  <button type="button" class="btn btn-secondary" id="messageListbtn">뒤로가기</button>
		  <button type="submit" class="btn btn-outline-dark" id="submit">보내기</button>
		</div>
		</form>
	</div>
</div>  


<script>
function boardValidate() {
	if ($("#receiveMember").val().trim().length == 0) {
		swal("받는사람을 입력해 주세요.");
		$("#receiveMember").focus();
		return false;
	}

	if ($("#content").val().trim().length == 0) {
		swal("내용을 입력해 주세요.");
		$("#content").focus();
		return false;
	}
}
$("#messageListbtn").on("click", function() {
	path = "${contextPath}/message/rmessageList";
	location.href = path;
});

</script>
</body>
</html>