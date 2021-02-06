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

				<h3>수업명</h3>
				<hr>
				<div class="bg-white rounded shadow-sm container p-3">
					<form method="POST" action="#" onsubmit="#" class="form-horizontal"
						role="form">

						<!-- 이름 -->
						<div class="row mb-3 form-row">
							<div class="col-md-3">
								<h6>강사명</h6>
							</div>
							<div class="col-md-6">
								<h5 id="name"></h5>
							</div>
						</div>

						<!-- 전화번호 -->
						<div class="row mb-3 form-row">
							<div class="col-md-3">
								<h6>시간</h6>
							</div>
							<div class="col-md-6">
								<h5 id="time"></h5>
							</div>
						</div>

						<!-- 이메일 -->
						<div class="row mb-3 form-row">
							<div class="col-md-3">
								<h6>장소</h6>
							</div>
							<div class="col-md-6">
								<h5 id="place"></h5>
							</div>
						</div>

						<!-- 금액 -->
						<div class="row mb-3 form-row">
							<div class="col-md-3">
								<h6>진도율</h6>
							</div>
							<div class="col-md-6">
								<h5 id="progresslate"></h5>
							</div>
						</div>

						<br>

						<div class="list-wrapper">
							<table class="table table-hover table-striped my-5"
								id="list-table">
								<thead>
									<tr>
										<th>강좌번호</th>
										<th>소제목</th>
										<th>완료여부</th>
									</tr>
								</thead>

								<tbody>
									<c:choose>
										<c:when test="${empty list }">
											<tr>
												<td colspan="5">존재하지 않습니다</td>
											</tr>
										</c:when>
										<c:otherwise>

											<c:forEach var="notice" items="${list}">
												<tr>
													<th>강좌번호</th>
													<th>소제목</th>
													<th>완료여부</th>
												</tr>
											</c:forEach>

										</c:otherwise>
									</c:choose>
								</tbody>
							</table>
						</div>
				</div>

				<button type="button" class="btn btn-primary float-right"
					onclick="location.href = 'studentlist.do'">이전페이지로</button>
				</form>



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