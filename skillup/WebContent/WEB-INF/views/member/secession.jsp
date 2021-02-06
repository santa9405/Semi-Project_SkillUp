<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>SKILL UP</title>
	<style>
		#content-main {
			height: 830px;
		}
		
		#secessionForm {
			margin: 20px;
		}
		
		.form-group>label{
			line-height: 40px;
			font-size: 17px;
			font-weight: bold;
		}
		
		.form-check {
			line-height: 30px;
		}
	</style>
</head>
<body>
	<div class="container" id="content-main">
		<jsp:include page="../common/header.jsp"></jsp:include>
		<c:if test="${!empty loginMember}">
			<jsp:include page="/WEB-INF/views/message/messageButton.jsp"></jsp:include>
		</c:if>

		<div class="row my-5">
			<jsp:include page="sideMenu.jsp"></jsp:include>

			<div class="col-sm-offset-2 col-sm-8">
				<h3>회원 탈퇴</h3>
				<div class="rounded shadow-sm container p-3 bg-light my-5">
					<form method="POST" action="updateStatus.do" id="secessionForm" onsubmit="return pwdValidate();">
						<div class="form-group">
					    <label for="exampleInputEmail1">이메일</label>
					    <div class="col-md-9">
					    <input type="text" readonly class="form-control-plaintext" id="staticEmail" value="${loginMember.memberEmail}">
					    </div>
					  </div>
					  <div class="form-group">
					    <label for="currentPwd">현재 비밀번호</label>
					    <input type="password" class="form-control" id="currentPwd" name="currentPwd">
					  </div>
					  
					  <hr><br>
					  
					  <h4>탈퇴 사유</h4><br>
					  <div class="form-check">
						  <input class="form-check-input" type="radio" name="exampleRadios" id="exampleRadios1">
						  <label class="form-check-label" for="exampleRadios1">
						    다른 계정이름으로 사용하기 위해서
						  </label>
						</div>
						<div class="form-check">
						  <input class="form-check-input" type="radio" name="exampleRadios" id="exampleRadios2">
						  <label class="form-check-label" for="exampleRadios2">
						    사용빈도가 낮고, 개인정보 유출이 우려되서
						  </label>
						</div>
					  <div class="form-check">
						  <input class="form-check-input" type="radio" name="exampleRadios" id="exampleRadios1">
						  <label class="form-check-label" for="exampleRadios1">
						    사이트 이용시 장애가 많아서
						  </label>
						</div>
						<div class="form-check">
						  <input class="form-check-input" type="radio" name="exampleRadios" id="exampleRadios2">
						  <label class="form-check-label" for="exampleRadios2">
						    서비스의 질에 대한 불만이 있어서
						  </label>
						</div>
					  <div class="form-check">
						  <input class="form-check-input" type="radio" name="exampleRadios" id="exampleRadios1">
						  <label class="form-check-label" for="exampleRadios1">
						    사이트 이용시 고객응대가 나빠서
						  </label>
						</div>
						<div class="form-check">
						  <input class="form-check-input" type="radio" name="exampleRadios" id="exampleRadios2">
						  <label class="form-check-label" for="exampleRadios2">
						    기타
						  </label>
						</div>
					  <div style="text-align:center; margin-top:40px;">
							<button class="btn btn-primary" type="submit">회원 탈퇴</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	
	<%@ include file="../common/footer.jsp"%>
	<script src="${contextPath}/resources/js/wsp_member.js"></script>
</body>
</html>
