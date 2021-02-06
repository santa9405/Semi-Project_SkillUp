<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>SKILL UP</title>
	<style>
		input[type="number"]::-webkit-outer-spin-button, 
		input[type="number"]::-webkit-inner-spin-button {
			-webkit-appearance: none;
			margin: 0;
		}
		
		#UpdateForm {
			margin: 20px;
		}
	
		.form-group>label{
			line-height: 40px;
			font-size: 17px;
			font-weight: bold;
		}
		
		.pwdLabel, .phoneLabel {
			display: block;
		}
		
		.col-md-3 {
      display: inline-block;
    }
		
		#secession {
			 text-align:right; 
			 margin-top: 10px;
		}
		
		#secession>a {
			color: #333;
			text-decoration: underline;
			font-size: 14px;
		}
		#wrapper {
			height: 1055px;	
		}
	</style>
</head>
<body>
<div id="wrapper">
	<div class="container">
		<jsp:include page="../common/header.jsp"></jsp:include>
		<c:if test="${!empty loginMember}">
			<jsp:include page="/WEB-INF/views/message/messageButton.jsp"></jsp:include>
		</c:if>

		<c:set var="phone" value="${fn:split(loginMember.phone, '-')}"/>
		
		<div class="row my-5">
			<jsp:include page="sideMenu.jsp"></jsp:include>		
				
			<div class="col-sm-8">
				<h4>My Page</h4>
				<div class="rounded shadow-sm container p-3" style="background-color: #fff">
					<form method="POST" action="updateMember.do" id="UpdateForm" onsubmit="return memberUpdateValidate();">
					  <div class="form-group">
					    <label for="name">이름</label>
					    <div class="col-md-9">
					    <input type="text" readonly class="form-control-plaintext" id="name" value="${loginMember.memberName}">
					    </div>
					  </div>
					  <div class="form-group">
					    <label for="email">Email</label>
					    <div class="col-md-9">
					    <input type="text" readonly class="form-control-plaintext" id="email" value="${loginMember.memberEmail}">
					    </div>
					  </div>
					  <div class="form-group">
					    <label class="pwdLabel" for="pwdBtn">비밀번호</label>
					    <div class="col-md-9">
					    <a id="pwdBtn" class="btn btn-primary" href="changePwdForm.do">비밀번호 변경</a>
					    </div>
					  </div>
					  
					  <div class="form-group">
					    <label class="phoneLabel" for="phone1">전화번호</label>
					    <div class="row" style="margin-left: 0px;">
						    <div class="col-md-3">
									<select class="custom-select" id="phone1" name="phone1">
										<option>010</option>
										<option>011</option>
										<option>016</option>
										<option>017</option>
										<option>019</option>
									</select>
								</div>
								
								<div class="col-md-3">
									<input type="number" class="form-control phone" id="phone2" name="phone2" value="${phone[1]}">
								</div>
		
								<div class="col-md-3">
									<input type="number" class="form-control phone" id="phone3" name="phone3" value="${phone[2]}">
								</div>
							</div>
						</div>
						<hr>
					  <div class="form-group">
							<label>관심 분야</label>
							<div class="col-md-12 custom-control custom-checkbox">
								<div class="form-check form-check-inline">
									<input type="checkbox" class="form-check-input custom-control-input" name="interest"
										id="language" value="외국어">
									<label class="form-check-label custom-control-label" for="language">외국어</label>
								</div>
								<div class="form-check form-check-inline">
									<input type="checkbox" class="form-check-input custom-control-input" name="interest"
										id="sports" value="스포츠">
									<label class="form-check-label custom-control-label" for="sports">스포츠</label>
								</div>
								<div class="form-check form-check-inline">
									<input type="checkbox" class="form-check-input custom-control-input" name="interest"
										id="photo" value="사진/영상">
									<label class="form-check-label custom-control-label" for="photo">사진/영상</label>
								</div>
								<br>
								<div class="form-check form-check-inline">
									<input type="checkbox" class="form-check-input custom-control-input" name="interest"
										id="crafts" value="공예">
									<label class="form-check-label custom-control-label" for="crafts">공예</label>
								</div>
								<div class="form-check form-check-inline">
									<input type="checkbox" class="form-check-input custom-control-input" name="interest"
										id="cooking" value="요리">
									<label class="form-check-label custom-control-label" for="cooking">요리</label>
								</div>
								<div class="form-check form-check-inline">
									<input type="checkbox" class="form-check-input custom-control-input" name="interest"
										id="art" value="미술">
									<label class="form-check-label custom-control-label" for="art">미술</label>
								</div>
								<div class="form-check form-check-inline">
									<input type="checkbox" class="form-check-input custom-control-input" name="interest"
										id="music" value="음악">
									<label class="form-check-label custom-control-label" for="music">음악</label>
								</div>
							</div>
						</div>
					  <div style="text-align:center; margin-top:40px;">
							<button class="btn btn-primary" type="submit">정보 수정</button>
						</div>
					</form>
				</div>
				<p id="secession"><a href="secession.do">회원 탈퇴</a></p>
			</div>
		</div>
	</div>
	</div>
	
	<jsp:include page="../common/footer.jsp"></jsp:include>
		
	<script src="${contextPath}/resources/js/member.js"></script>
	<script>
		// 전화번호 첫번째 자리 선택
		(function(){
			$("#phone1>option").each(function(index, item){
				if($(item).text() == "${phone[0]}")
					$(item).prop("selected", true);
			})
		})();
		
		// 관심분야 체크
		(function(){
			var interest = "${loginMember.interest}".split(",");
			
			$("input[name='interest']").each(function(index, item){
				if(interest.indexOf($(item).val()) != -1)
					$(item).prop("checked", true);
			})
		})();
	</script>
</body>
</html>
