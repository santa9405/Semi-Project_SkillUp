<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
	<!DOCTYPE html>
	<html>

	<head>
		<meta charset="UTF-8">
		<title>SKILL UP</title>
		<style>
			#findPwdForm {
				margin: 20px;
			}
		</style>
	</head>

	<body>
		<div class="container">
			<jsp:include page="../common/header.jsp"></jsp:include>

			<div class="py-5 text-center">
				<h2>비밀번호 찾기</h2>
			</div>

			<div class="row">
				<div class="col-md-6 offset-md-3 bg-light rounded p-3" style="margin-bottom:50px;">

					<form method="get" action="${contextPath}/member/findPwd.do" class="needs-validation" id="findPwdForm" onsubmit="return pwdValidate();">
            <div class="col-md-12 mb-3">
	            <label for="findEmail">이메일</label>
	            <div class="row">
								<div class="col-md-9">
	                <input type="text" class="form-control" id="findEmail" name="findEmail" placeholder="이메일을 입력해주세요" required autofocus>
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

						<div id="pwdArea" style="display:none">
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
            
						<button type="submit" class="btn btn-primary btn-lg btn-block" style="margin-top:40px;">비밀번호 변경</button>
						</div>
					</form>

				</div>
			</div>
		</div>

		<jsp:include page="../common/footer.jsp"></jsp:include>
		<script src="${contextPath}/resources/js/member.js"></script>
		<script>
			$(function() {
			//	$("#pwdArea").css("display", "none");
			});
			
			// 이메일
			$("#findEmail").on("input", function () {
			    var regExp = /^[\w]{4,}@[\w]+(\.[\w]+){1,3}$/; // 4글자 이상 아무단어 @ 아무단어 . * 3

			    if (!regExp.test($("#findEmail").val())) {
							$("#findEmail").removeClass("is-valid").addClass("is-invalid");
			        $(".emailiCheck").removeClass("valid-feedback").addClass("invalid-feedback").css("display", "block").text("이메일 형식이 유효하지 않습니다.");
							$("#certify").prop("disabled", true);
			    } else {
							$.ajax({
				        url: "emailDupCheck.do",
				        type: "POST",
				        data: { "email": $("#findEmail").val() },
				        success: function(result) {
				            if(result == 0) {
												$("#findEmail").removeClass("is-valid").addClass("is-invalid");
												$(".emailiCheck").removeClass("valid-feedback").addClass("invalid-feedback").text("가입되지 않은 이메일입니다.");
												$("#certify").prop("disabled", true);
				            } else {
												$("#findEmail").removeClass("is-invalid").addClass("is-valid");
												$(".emailiCheck").removeClass("invalid-feedback").addClass("valid-feedback").text("이메일이 확인되었습니다.");
				                $("#certify").prop("disabled", false);
				                
				                var hidden = $("<input>").attr("type", "hidden").attr("name", "email").val($("#findEmail").val());
				                $("#findPwdForm").append(hidden);
				            }
				        },
				        error: function() {
				            console.log("이메일 확인 실패");
				        }
			      });
			    }
			});
			
			$("#certify").on("click", function(){
				var label = $("<label>").text("인증 코드").css("line-height", "40px");
				var input = $("<input>").attr("type", "text").attr("id", "code").attr("class", "form-control");
				
				$("#certifyArea").children().eq(0).empty();
				$("#certifyArea").children().eq(0).append(label);
				$("#certifyArea").children().eq(1).empty();
				$("#certifyArea").children().eq(1).append(input);
				
				$.ajax({
			    url: "${contextPath}/sendMail.do",
			    type: "POST",
			    data: { "email": $("#findEmail").val() },
			    success: function(code) {
			    	$("#code").on("input", function () {
				    	if($("#code").val() == code) {
				    		$("#code").removeClass("is-invalid").addClass("is-valid");
				    		$(".emailiCheck").removeClass("invalid-feedback").addClass("valid-feedback").css("display", "block").text("인증 코드가 확인되었습니다");
				        validateCheck.email = true;

				        $("#pwdArea").css("display", "block");
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