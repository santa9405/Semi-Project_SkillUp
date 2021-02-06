<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


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

	<div class="container">
		<jsp:include page="../common/header.jsp" />

		<div class="row my-5">

			<jsp:include page="../member/sideMenu.jsp"></jsp:include>

			<div class="col-sm-7">


				<h1>강사 수강 관련페이지</h1>

				<c:if test="${!empty loginMember })">

				</c:if>
				<div class="list-wrapper">
					<table class="table table-hover table-striped my-5" id="list-table">
						<thead>
							<tr>
								<th>수강번호</th>
								<th>제목</th>
								<th>가격</th>
								<th>조회수</th>
								<th>수업 시작일</th>
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

									<c:forEach var="board" items="${bList}">
										<tr>
											<th>수강번호</th>
											<th>제목</th>
											<th>가격</th>
											<th>조회수</th>
											<th>>수업 시작일</th>
										</tr>
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
	<div class="row">
		<div class="col-md-12">

			<jsp:include page="../common/footer.jsp" />
		</div>
	</div>
	</div>

</body>
</html>