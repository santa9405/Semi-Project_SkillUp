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

						<!-- 이름 -->
						<div class="row mb-3 form-row">
							<div class="col-md-3">
								<h6>이름</h6>
							</div>
							<div class="col-md-6">
								<h5 id="name">사용자 이름</h5>
							</div>
						</div>

						<!-- 전화번호 -->
						<div class="row mb-3 form-row">
							<div class="col-md-3">
								<h6>전화번호</h6>
							</div>
							<div class="col-md-6">
								<h5 id="phoneCall">사용자 전화번호</h5>
							</div>
						</div>

						<!-- 이메일 -->
						<div class="row mb-3 form-row">
							<div class="col-md-3">
								<h6>이메일</h6>
							</div>
							<div class="col-md-6">
								<h5 id="email">사용자 이메일</h5>
							</div>
						</div>

						<!-- 금액 -->
						<div class="row mb-3 form-row">
							<div class="col-md-3">
								<h6>금액</h6>
							</div>
							<div class="col-md-6">
								<h5 id="classMoney">수강강좌 금액</h5>
							</div>
						</div>

						<!-- 결제수단 -->
						<div class="row mb-3 form-row">
							<div class="col-md-3">
								<h6>결제수단</h6>
							</div>
							<div class="col-md-6">
								<h5 id="card">사용한카드</h5>
							</div>
						</div>

						<!-- 결제수단 -->
						<div class="row mb-3 form-row">
							<div class="col-md-3">
								<h6>결제 날자</h6>
							</div>
							<div class="col-md-6">
								<h5 id="paymentDate">결제 날자</h5>
							</div>
						</div>
						<!-- 결제수단 -->
						<div class="row mb-3 form-row">
							<div class="col-md-3">
								<h6>결제여부</h6>
							</div>
							<div class="col-md-6">
								<h5 id="paymentDate">결제 완료</h5>
							</div>
						</div>
				</div>
				<button class=" btn btn-primary float-right" type="button"
					data-toggle="modal" data-target="#exampleModal">결제취소하기</button>

				<button type="button" class="btn btn-primary float-right"
					onclick="location.href = '${contextPath}'">메인페이지로</button>
				</form>

				<!-- Modal -->
				<div class="modal fade" id="exampleModal" tabindex="-1"
					role="dialog" aria-labelledby="exampleModalLabel"
					aria-hidden="true">
					<div class="modal-dialog" role="document">
						<div class="modal-content">
							<div class="modal-header">
								<h5 class="modal-title" id="exampleModalLabel">결제 취소</h5>
								<button type="button" class="close" data-dismiss="modal"
									aria-label="Close">
									<span aria-hidden="true">&times;</span>
								</button>
							</div>
							<div class="modal-body">결제 취소 하시겠습니까?</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-primary"
									onclick="location.href = 'paycheck.do'">결제 취소 하기</button>
								<button type="button" class="btn btn-secondary"
									data-dismiss="modal">취소하기</button>
							</div>
						</div>
					</div>
				</div>


			</div>

		</div>
		<div class="row">
			<div class="col-md-12"></div>
		</div>
	</div>

	<!-- WEB-INF/views/common/footer.jsp 여기에 삽입(포함) -->
	<jsp:include page="../common/footer.jsp" />
</body>
</html>