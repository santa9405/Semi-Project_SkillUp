<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>내정보</title>
</head>
<style>
	#content-main{
		height: 830px;
	}
	
	#pwdForm {
		margin: 20px;
	}
	
	.form-group>label{
			line-height: 40px;
			font-size: 17px;
			font-weight: bold;
		}
</style>
<body>
	<div class="container" id="content-main">
		<jsp:include page="../common/header.jsp"></jsp:include>
		<c:if test="${!empty loginMember}">
			<jsp:include page="/WEB-INF/views/message/messageButton.jsp"></jsp:include>
		</c:if>
	
		<div class="row my-5">
			<jsp:include page="sideMenu.jsp"></jsp:include>

			<div class="col-sm-8">
				<h3>비밀번호 변경</h3>
				<div class="rounded shadow-sm container p-3 bg-light">
					<form method="POST" action="updatePwd.do" id="pwdForm" onsubmit="return pwdValidate();">
					  <div class="form-group">
					    <label for="currentPwd">현재 비밀번호</label>
					    <input type="password" class="form-control" id="currentPwd" name="currentPwd" required>
					  </div>
					  <div class="form-row">
					    <div class="form-group col-md-6">
					      <label for="newPwd">새 비밀번호</label>
					      <input type="password" class="form-control" id="newPwd" name="newPwd" required>
					    </div>
					    <div class="form-group col-md-6">
					      <label for="newPwd2">새 비밀번호  확인</label>
					      <input type="password" class="form-control" id="newPwd2" name="newPwd2" required>
					    </div>
					  </div>
					  <div style="text-align:center; margin-top:40px;">
							<button class="btn btn-primary" type="submit">변경하기</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	
	<jsp:include page="../common/footer.jsp"></jsp:include>
	<script src="${contextPath}/resources/js/member.js"></script>
</body>
</html>
