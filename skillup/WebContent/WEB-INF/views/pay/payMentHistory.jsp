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

#list-table td:hover {
	cursor: pointer;
}
#wrapper {
	height: 1055px;	
}
</style>


</head>
<body>
	<div id="wrapper">
	<div class="container">
		<jsp:include page="../common/header.jsp" />

		<div class="row my-5">

			<jsp:include page="../member/sideMenu.jsp"></jsp:include>

			<div class="col-sm-7">


				<h1>결제 목록</h1>


				<div class="list-wrapper">
					<table class="table table-hover table-striped my-5" id="list-table">
						<thead>
							<tr>
								<th>결제번호</th>
								<th>결제일</th>
								<th>수강번호</th>
								<th>결제여부</th>
								<th>수강생번호</th>
							</tr>
						</thead>

						<tbody>

							<%--결제한 것이 없을 경우 텍스트출력. --%>
							<c:choose>
								<c:when test="${empty list }">
									<tr>
										<td colspan="6">존재하지 않습니다</td>
									</tr>
								</c:when>
								<%--결제한 강의가 존재 할 떄 --%>
								<c:otherwise>

									<c:forEach var="pay" items="${list}">
										<c:if test="${loginMember.memberNo==pay.memberNo}">
											<tr>
												<td>${pay.paymentNo}</td>
												<td>${pay.paymentDt}</td>
												<td>${pay.lessonNo}</td>
												<td>${pay.paymentCk}</td>
												<td>${pay.memberNo}</td>
											


											</tr>

										</c:if>
									</c:forEach>

								</c:otherwise>
							</c:choose>
						</tbody>
					</table>
				</div>

			</div>
		</div>
		<div class="col-md-2"></div>
	</div>
	</div>
	<div class="col-md-12">

		<jsp:include page="../common/footer.jsp" />
	</div>
	<script>
		// 공지사항 상세보기 기능 (jquery를 통해 작업)
		$("#list-table td").on("click", function() {

			var CheckOrder = $(this).parent().children().eq(0).text();

			location.href = "${contextPath}/pay/paycheck.do?no=" + CheckOrder;
		});
	</script>
</body>
</html>