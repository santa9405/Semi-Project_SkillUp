<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
	<!DOCTYPE html>
	<html>

	<head>
		<meta charset="UTF-8">
		<title>SKILL UP</title>
		<style>
			/* number 태그 화살표 제거 */
			input[type="number"]::-webkit-outer-spin-button,
			input[type="number"]::-webkit-inner-spin-button {
				-webkit-appearance: none;
				margin: 0;
			}

			#signUpForm {
				margin: 20px;
			}
		</style>
	</head>

	<body>
		<div class="container">
			<jsp:include page="../common/header.jsp"></jsp:include>

			<div class="py-5 text-center">
				<h2>회원 가입</h2>
			</div>

			<div class="row">
				<div class="col-md-6 offset-md-3 bg-light rounded p-3" style="margin-bottom:50px;">

					<form method="get" action="signUp.do" class="needs-validation" id="signUpForm"
						onsubmit="return validate();">
						<div class="col-md-12 mb-3">
                <label for="name">이름</label>
                <input type="text" class="form-control" id="name" name="name" placeholder="이름을 입력해주세요" required autofocus>
                <div class="nameCheck"></div>
            </div>
            
            <div class="col-md-12 mb-3">
	            <label for="name">이메일</label>
	            <div class="row">
								<div class="col-md-9">
	                <input type="text" class="form-control" id="email" name="email" placeholder="이메일을 입력해주세요" required>
								</div>
								<div class="col-md-3">
									<button type="button" id="certify" class="btn btn-primary btn-block" disabled>인증</button>
								</div>
							</div>
							<div id="certifyArea" class="row" style="margin-top:10px;">
								<div class="col-md-3">
								</div>
								<div class="col-md-9">
								</div>
							</div>
							<div class="emailiCheck"></div>
            </div>

						<div class="col-md-12 mb-3">
							<label for="pwd">비밀번호</label>
							<input type="password" class="form-control" id="pwd" name="pwd" placeholder="비밀번호를 입력해주세요" required>
							<small id="passwordHelpBlock" class="form-text text-muted">
								6~12자 (영문, 숫자)로 입력해주세요
							</small>
              <div class="pwdCheck"></div>
            </div>
            
						<div class="col-md-12 mb-3">
							<label for="pwd2">비밀번호 확인</label>
							<input type="password" class="form-control" id="pwd2" placeholder="비밀번호를 재입력해주세요" required>
              <div class="pwdCheck2"></div>
            </div>
            
						<div class="col-md-12 mb-3">
              <label for="phone1">전화번호</label>
							<div class="row">
								<div class="col-md-4">
									<select class="custom-select" id="phone1" name="phone1" required>
										<option>010</option>
										<option>011</option>
										<option>016</option>
										<option>017</option>
										<option>019</option>
									</select>
								</div>
								<div class="col-md-4">
									<input type="number" class="form-control phone" id="phone2" name="phone2" required>
								</div>
								<div class="col-md-4">
									<input type="number" class="form-control phone" id="phone3" name="phone3" required>
								</div>
              	<div class="phoneCheck col-md-12" ></div>
							</div>
            </div>
						<hr>

						<div class="form-group">
							<div class="col-md-12 mb-3">
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
						</div>
						<button type="submit" class="btn btn-primary btn-lg btn-block"
							style="margin-top:40px;">회원가입</button>
					</form>

				</div>
			</div>
		</div>

		<jsp:include page="../common/footer.jsp"></jsp:include>
		<script src="${contextPath}/resources/js/member.js"></script>
		<script>
			$("#certify").on("click", function(){
				var label = $("<label>").text("인증 코드");
				var input = $("<input>").attr("type", "text").attr("id", "code").attr("class", "form-control");
				
				$("#certifyArea").children().eq(0).empty();
				$("#certifyArea").children().eq(0).append(label);
				$("#certifyArea").children().eq(1).empty();
				$("#certifyArea").children().eq(1).append(input);
				
				$.ajax({
			    url: "${contextPath}/sendMail.do",
			    type: "POST",
			    data: { "email": $("#email").val() },
			    success: function(code) {
			    	$("#code").on("input", function () {
				    	if($("#code").val() == code) {
				    		$("#code").removeClass("is-invalid").addClass("is-valid");
				    		$(".emailiCheck").removeClass("invalid-feedback").addClass("valid-feedback").css("display", "block").text("인증 코드가 확인되었습니다");
				        validateCheck.email = true;
				    	} else {
				    		$("#code").removeClass("is-valid").addClass("is-invalid");
				    		$(".emailiCheck").removeClass("valid-feedback").addClass("invalid-feedback").css("display", "block").text("인증 코드가 일치하지 않습니다");
				        validateCheck.email = false;
				    	}
			    	});
			    },
			    error: function() {
			        console.log("이메일 전송 실패");
			    }
			  });
			});
		</script>
	</body>

	</html>