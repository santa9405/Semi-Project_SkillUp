<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" scope="application" value="${pageContext.servletContext.contextPath}"></c:set>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="${contextPath}/resources/css/bootstrap.css">
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script type="text/javascript" src="${contextPath}/resources/js/bootstrap.js"></script>
<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
<style>
body {
	font-size: 0.8rem;
}
.navbar {
	height: 110px;
	border-bottom: 1px solid lightgray;
	margin: auto;
}

.nav-link{
	text-align: center;
	width: 120px;
}

* {
	font-family: 'GmarketSansMedium';
}
.navbar-brand {
	font-family: 'Jal_Onuel';
	font-size: 30px;
}

.collapse navbar-collapse {
	float: right;
}

.category {
	color: white;
	font-size: larger;
	font-family: 'GmarketSansMedium';
  font-weight: lighter;
}

.form-control.search {
	width: 180px;
	height: 35px;
}

.blank {
	height: 40px;
}

#search-input {
	width: 150px;
	height: 25px;
	border: 0px;
	margin: 1px ay;
}

@font-face {
	font-family: 'Jal_Onuel';
	src:
		url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_20-10-21@1.0/Jal_Onuel.woff')
		format('woff');
	font-weight: normal;
	font-style: normal;
}

@font-face {
	font-family: 'GmarketSansMedium';
	src:
		url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2001@1.1/GmarketSansMedium.woff')
		format('woff');
	font-weight: normal;
	font-style: normal;
}

@font-face {
 font-family: 'NanumBarunGothic';
 font-style: normal;
 font-weight: 400;
 src: url('//cdn.jsdelivr.net/font-nanumlight/1.0/NanumBarunGothicWeb.eot');
 src: url('//cdn.jsdelivr.net/font-nanumlight/1.0/NanumBarunGothicWeb.eot?#iefix') format('embedded-opentype'), url('//cdn.jsdelivr.net/font-nanumlight/1.0/NanumBarunGothicWeb.woff') format('woff'), url('//cdn.jsdelivr.net/font-nanumlight/1.0/NanumBarunGothicWeb.ttf') format('truetype');
}

body {
  padding-top: 10rem;
  background-color: #f8f9fa;
}
nav {
	background-color: #f8f9fa;
}
.cateNav {
	height: 45px;
	background-color: #5f0081;
	height: 45px;
	
}
.cateNav a {
	line-height: 30px;
}
.cateNav a:hover {
	color: white;
}

/* Modal */
.closeSpan {
	float: right;
	margin-top: 5px;
	margin-right: 15px;
}

.modal-body {
	padding-bottom: 60px;
}

.inputArea {
	margin-bottom: 10px;
}

.checkbox {
	display: inline;
}

.find {
	display: inline-block;
	float: right;
	font-size: 14px;
	color: #6c757d;
}

.find>a {
	color: #6c757d;
	text-decoration: none;
}

#signInBtn {
	margin-top: 35px;
}

.form-signin .checkbox {
  font-weight: 400;
}
.form-signin .form-control {
  position: relative;
  box-sizing: border-box;
  height: auto;
  padding: 10px;
  font-size: 16px;
}
.form-signin .form-control:focus {
  z-index: 2;
}
.form-signin input[type="email"], 
.form-signin input[type="text"] {
  margin-bottom: -1px;
  border-bottom-right-radius: 0;
  border-bottom-left-radius: 0;
}
.form-signin input[type="password"] {
  margin-bottom: 10px;
  border-top-left-radius: 0;
  border-top-right-radius: 0;
}
</style>
</head>
<body>
	<c:if test="${!empty sessionScope.swalTitle}">
	 	<script>
	 		swal({icon: "${swalIcon}", title: "${swalTitle}", text: "${swalText}"});
	 	</script>
	 	
	 	<c:remove var="swalIcon"/>
	 	<c:remove var="swalTitle"/>
	 	<c:remove var="swalText"/>
	</c:if>
	
	<div class="fixed-top">
	<nav class="navbar navbar-expand-lg">
		<div class="container" style="width: 1120px;">

			<!-- 로고 -->
			<a class="navbar-brand" href="${contextPath}"> 
				<img src="${contextPath}/resources/img/skilluplogo.png" width="80" height="80"></a>

			<ul class="navbar-nav" style="font-family: 'GmarketSansMedium'; margin-left: 450px;">
				<c:choose>
					<c:when test="${empty sessionScope.loginMember}"><%-- 로그아웃 상태 --%>
						<li class="nav-item"><a class="nav-link" data-toggle="modal" data-target="#loginModal" style="cursor:pointer;">로그인</a></li>
						<li class="nav-item"><a class="nav-link" href="${contextPath}/member/signUpForm.do">회원가입</a></li>
					</c:when>
					
					<c:when test="${!empty sessionScope.loginMember && loginMember.memberGrade == 'A'}"><%-- 관리자인 경우 --%>
						<li class="nav-item active">
							<a class="nav-link" href="${contextPath}/instructor/approveList.do">전문가 승인</a>
						</li>	
						<li class="nav-item active">
							<a class="nav-link" href="${contextPath}/member/logout.do">Logout</a>
						</li>	
					</c:when>
					
						
					<c:otherwise>
						<c:choose>
							<c:when test="${!empty sessionScope.loginMember && loginMember.memberGrade == 'G'}">
								<li class="nav-item active">
									<a class="dropdown-item" href="${contextPath}/instructor/enrollmentForm.do">전문가 등록</a>
								</li>
							</c:when>
							<c:when test="${empty sessionScope.instrMode}">
								<li class="nav-item">
									<a class="nav-link" href="${contextPath}/lesson/myList.do">전문가 모드</a>
								</li>
							</c:when>
							<c:when test="${!empty sessionScope.instrMode}">
								<li class="nav-item active">
									<a class="nav-link" href="${contextPath}/lesson/memberMode.do">회원 모드</a>
								</li>
							</c:when>
						</c:choose>
						<div class="dropdown" style="padding-left: 30px;">
						  <a class="dropdown-toggle" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
						    <img src="${contextPath}/resources/img/user.png">
						  </a>
							
							<c:choose>
								<c:when test="${!empty sessionScope.loginMember && loginMember.memberGrade == 'G'}">
								  <div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
								    <a class="dropdown-item" href="${contextPath}/member/myPage.do">마이 페이지</a>
								    <a class="dropdown-item" href="${contextPath}/member/logout.do">로그 아웃</a>
								  </div>
							  </c:when>
							  <c:otherwise>
							  	<div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
								    <a class="dropdown-item" href="${contextPath}/instructor/myPage.do">프로필 관리</a>
								    <a class="dropdown-item" href="${contextPath}/member/myPage.do">마이 페이지</a>
								    <a class="dropdown-item" href="${contextPath}/member/logout.do">로그 아웃</a>
								  </div>
							  </c:otherwise>
							</c:choose>
						</div>
					</c:otherwise>
				</c:choose>
				
			</ul>
			
			<form class="form-inline" action="${contextPath}/searchLesson.do">
				<input class="form-control search" name="sv">&nbsp;&nbsp;
				<button style="width: 35px; height: 35px; background-color: rgb(0,0,0,0); border: 0px">
					<img src="${contextPath}/resources/img/magnifying-glass.png" width="20px" height="20px">
				</button>
			</form>

		</div>
	</nav>

	<nav class="cateNav">
		<ul class="nav justify-content-center">
			<c:choose>
				<c:when test="${!empty sessionScope.instrMode}">
					<li class="nav-item"><a class="nav-link category" href="${contextPath}/lesson/myList.do">수업 관리</a></li>
					<li class="nav-item"><a class="nav-link category" href="${contextPath}/lesson/insertForm.do">수업 등록</a></li>
					<!-- <li class="nav-item"><a class="nav-link category" href="#">신청 수락</a></li>
					<li class="nav-item"><a class="nav-link category" href="#">리뷰 관리</a></li> -->
					<li class="nav-item"><a class="nav-link category" href="${contextPath}/instructor/myPage.do">프로필 관리</a></li>
				</c:when>
				<c:otherwise>
					<li class="nav-item"><a class="nav-link category" href="${contextPath}/searchLesson.do?ctgrName=외국어">외국어</a></li>
					<li class="nav-item"><a class="nav-link category" href="${contextPath}/searchLesson.do?ctgrName=스포츠">스포츠</a></li>
					<li class="nav-item"><a class="nav-link category" href="${contextPath}/searchLesson.do?ctgrName=사진/영상">사진/영상</a></li>
					<li class="nav-item"><a class="nav-link category" href="${contextPath}/searchLesson.do?ctgrName=공예">공예</a></li>
					<li class="nav-item"><a class="nav-link category" href="${contextPath}/searchLesson.do?ctgrName=요리">요리</a></li>
					<li class="nav-item"><a class="nav-link category" href="${contextPath}/searchLesson.do?ctgrName=미술">미술</a></li>
					<li class="nav-item"><a class="nav-link category" href="${contextPath}/searchLesson.do?ctgrName=음악">음악</a></li>
					<li class="nav-item"><a class="nav-link category" href="${contextPath}/board/list?boardNo=2">게시판</a></li>
				</c:otherwise>
			</c:choose>
		</ul>
	</nav>
	</div>
	<div class="blank"></div>

	<!-- Modal -->
	<div class="modal fade" id="loginModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true" class="closeSpan">&times;</span>
				</button>

				<div class="modal-body">
					<form class="form-signin" action="${contextPath}/member/login.do" method="POST">
						<div class="text-center mb-4">
							<img src="${contextPath}/resources/img/skilluplogo.png" width="150" height="150">
						</div>
						<div class="inputArea">
							<label for="inputEmail" class="sr-only">이메일</label> <!-- email --> 
							<input type="text" id="inputEmail" name="inputEmail" class="form-control" placeholder="이메일" required  value="${cookie.saveId.value}"> 
							<label for="inputPwd" class="sr-only">비밀번호</label> 
							<input type="password" id="inputPwd" name="inputPwd" class="form-control" placeholder="비밀번호" required>
						</div>
						<div class="checkbox mb-3">
							<label>
								<input type="checkbox" name="save" id="save"
									<c:if test="${!empty cookie.saveId.value}">
										checked
									</c:if>
								> 아이디 저장
							</label>
						</div>
						<div class="find">
							<a href="${contextPath}/member/findPwdForm.do">비밀번호 찾기</a>
						</div>

						<button id="signInBtn" class="btn btn-lg btn-primary btn-block" type="submit">로그인</button>
						<a href="${contextPath}/member/signUpForm.do" class="btn btn-lg btn-outline-primary btn-block" type="button">회원가입</a>
					</form>
				</div>
			</div>
		</div>
	</div>
	<script>
		$("#loginModal").on("shown.bs.modal", function () {
			if($("#inputEmail").val().length > 0)
			  $("#inputPwd").focus()
			else
			  $("#inputEmail").focus()
		})
	</script>
</body>
</html>