<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<style>
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

	<%-- 
      프로젝트의 시작주소(context root)를 얻어와 간단하게 사용할 수 있도록
      별도의 변수를 생성
   --%>
	<c:set var="contextPath" scope="application"
		value="${pageContext.servletContext.contextPath}" />


	<div class="container-fluid">
		<div class="row">
			<div class="col-md-12">
				<!-- 상단고정 -->
				<!-- WEB-INF/views/common/header.jsp 여기에 삽입 (포함) -->
				<jsp:include page="../common/header.jsp" />
			</div>
		</div>
		<div class="row">
			<div class="col-md-2"></div>
			<div class="col-md-8">

				<h3>결제 내역</h3>
				<hr>
				<div class="bg-white rounded shadow-sm container p-3">
					<form method="POST" action="#" onsubmit="#" class="form-horizontal"
						role="form">


						<label class="input-group-addon mr-3 insert-label">결제 번호</label>
						<h5 class="my-0" id="writer">${ck.paymentNo}</h5>
					
						<label class="input-group-addon mr-3 insert-label">이름</label>
						<h5 class="my-0" id="writer">${loginMember.memberName}</h5>

						<label class="input-group-addon mr-3 insert-label">이메딜</label>
						<h5 class="my-0" id="writer">${loginMember.memberEmail}</h5>


						<label class="input-group-addon mr-3 insert-label">금액</label>
						<h5 class="my-0" id="writer">${ck.pay}</h5>


						<label class="input-group-addon mr-3 insert-label">결제 날짜</label>
						<h5 class="my-0" id="writer">${ck.paymentDt}</h5>


						<label class="input-group-addon mr-3 insert-label">결제 여부</label>
						<h5 class="my-0" id="writer">${ck.paymentCk}</h5>
	
						<c:if test="${ck.paymentCk} != 'T' ">
						
						<label class="input-group-addon mr-3 insert-label">결제 여부</label>
						<h5 class="my-0" id="writer">결제 완료</h5>
						</c:if>
						
						<button type="button" class="btn btn-primary float-right"
							onclick="location.href = '../pay/payment.do'">결제 목록으로</button>
						<button class="btn btn-primary float-right" id="deleteBtn">결제
							취소하기</button>

					</form>

				</div>
			</div>

		</div>

	</div>

	<!-- WEB-INF/views/common/footer.jsp 여기에 삽입(포함) -->
	<jsp:include page="../common/footer.jsp" />



	<script>
	$("#deleteBtn").on("click",function(){
		if(confirm("정말 삭제 하시겠습니까?")){
			
			location.href="delete.do?no=${ck.paymentNo}";
		}
	});    
    </script>


</body>
</html>