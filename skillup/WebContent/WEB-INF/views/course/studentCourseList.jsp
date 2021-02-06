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


				<h1>수강생 수강 확인(now)</h1>


				<div class="list-wrapper">
					<table class="table table-hover table-striped my-5" id="list-table">
						<thead>
							<tr>
								<th>강좌번호</th>
								<th>강좌이름</th>
								<th>장소</th>
								<th>시간</th>
								<th>수강횟수</th>
								<th>수업 시작일</th>
							</tr>
						</thead>

						<tbody>
							<!-- 공지사항 목록 -->
							<%--공지사항이 존재할 떄와 존재하지 않을 떄에 맞는 출력 형식을 지정해야함 . --%>
							<c:choose>
								<c:when test="${empty list }">
									<tr>
										<td colspan="5">존재하지 않습니다</td>
									</tr>
								</c:when>
								<%--공지사항이 존재 할 떄 --%>
								<c:otherwise>

									<c:forEach var="course" items="${list}">
										<c:if test="${loginMember.memberNo==course.memberNo}">
											<tr>
												<td>${course.lessonNo}</td>
												<td>${course.lessonTitle}</td>
												<td>${course.place}</td>
												<td>${course.lessonTime}</td>
												<td>${course.lessonCnt}</td>
												<td>${course.lessonDate}</td>
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

	</div>
</div>	
	<div class="row">
	</div>
	<jsp:include page="../common/footer.jsp" />

	<script>
		// 공지사항 상세보기 기능 (jquery를 통해 작업)
		$("#list-table td").on("click",function() {
			var noticeNo = $(this).parent().children().eq(0).text();

			location.href = "${contextPath}/course/instructorclass.do?no="+ noticeNo;});
	</script>


</body>
</html>