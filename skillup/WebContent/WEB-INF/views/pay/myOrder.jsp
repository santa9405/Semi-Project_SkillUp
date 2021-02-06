<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<script type="text/javascript"
	src="https://code.jquery.com/jquery-1.12.4.min.js"></script>
<script type="text/javascript"
	src="https://service.iamport.kr/js/iamport.payment-1.1.5.js"></script>




<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<style>
h3 {
	text-align: center;
}

.bg-image-full {
	background: no-repeat center center scroll;
	-webkit-background-size: cover;
	-moz-background-size: cover;
	background-size: cover;
	-o-background-size: cover;
}

div.bg-image-full {
	height: 300px;
	text-align: center;
}

@font-face {
	font-family: 'GmarketSansBold';
	src:
		url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2001@1.1/GmarketSansBold.woff')
		format('woff');
	font-weight: normal;
	font-style: normal;
}

div.bg-image-full>h1 {
	color: white;
	position: relative;
	top: 75px;
	font-size: 3em;
	font-family: 'GmarketSansBold';
	text-shadow: -2px 0 black, 0 2px black, 2px 0 black, 0 -2px black;
}
</style>
</head>
<body>
	<div class="container">


		<jsp:include page="../common/header.jsp" />


		<div class="col-sm-12">
			<hr>

			<h3>결제 페이지</h3>
			<hr>
			<div class="bg-white rounded shadow-sm container p-3">
			<h6>이름</h6>
				<input type="text" readonly class="form-control-plaintext"
					style="text-align: center;"email" value="${loginMember.memberName}">
			<h6>이메일</h6>
				<input type="text" readonly class="form-control-plaintext"
					style="text-align: center;"email" value="${loginMember.memberEmail}">
			<h6>전화번호</h6>
				<input type="text" readonly class="form-control-plaintext"
					style="text-align: center;"email" value="${loginMember.phone}">
				<div class="col-sm-12">
					<jsp:include page="../pay/order.jsp" />
				</div>
			</div>
		</div>
	</div>
	<!-- WEB-INF/views/common/footer.jsp 여기에 삽입(포함) -->
	<jsp:include page="../common/footer.jsp" />


</body>
</html>