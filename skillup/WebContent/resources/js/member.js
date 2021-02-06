var validateCheck = {
    "name": false,
    "email": false,
    "pwd": false,
    "pwd2": false,
    "phone": false,
}

// 이름
$("#name").on("input", function () {
    var regExp = /^[가-힣]{2,}$/; // 한글 두글자 이상

    if (!regExp.test($("#name").val())) {
				$("#name").removeClass("is-valid").addClass("is-invalid");
        $(".nameCheck").removeClass("valid-feedback").addClass("invalid-feedback").text("이름 형식이 유효하지 않습니다");
        validateCheck.name = false;
    } else {
				$("#name").removeClass("is-invalid").addClass("is-valid");
        $(".nameCheck").removeClass("invalid-feedback").addClass("valid-feedback").text("유효한 이름 형식입니다");
        validateCheck.name = true;
    }
});

// 이메일
$("#email").on("input", function () {
    var regExp = /^[\w]{4,}@[\w]+(\.[\w]+){1,3}$/; // 4글자 이상 아무단어 @ 아무단어 . * 3

    if (!regExp.test($("#email").val())) {
				$("#email").removeClass("is-valid").addClass("is-invalid");
        $(".emailiCheck").removeClass("valid-feedback").addClass("invalid-feedback").css("display", "block").text("이메일 형식이 유효하지 않습니다");
				$("#certify").prop("disabled", true);
    } else {
				$.ajax({
	        url: "emailDupCheck.do",
	        type: "POST",
	        data: { "email": $("#email").val() },
	        success: function(result) {
	            if(result == 0) {
									$("#email").removeClass("is-invalid").addClass("is-valid");
									$(".emailiCheck").removeClass("invalid-feedback").addClass("valid-feedback").text("사용 가능한 이메일입니다");
									$("#certify").prop("disabled", false);
	            } else {
									$("#email").removeClass("is-valid").addClass("is-invalid");
									$(".emailiCheck").removeClass("valid-feedback").addClass("invalid-feedback").text("이미 사용 중인 이메일입니다");
	                $("#certify").prop("disabled", true);
	            }
	        },
	        error: function() {
	            console.log("이메일 중복 검사 실패");
	        }
      });
    }
});

// 비밀번호
$("#pwd, #pwd2").on("input", function () {
    var regExp = /^[a-zA-Z\d]{6,12}$/;

    var v1 = $("#pwd").val();
    var v2 = $("#pwd2").val();

    if (!regExp.test(v1)) {
				$("#pwd").removeClass("is-valid").addClass("is-invalid");
				$(".pwdCheck").removeClass("valid-feedback").addClass("invalid-feedback").text("비밀번호 형식이 유효하지 않습니다");
        validateCheck.pwd = false;
    } else {
				$("#pwd").removeClass("is-invalid").addClass("is-valid");
				$(".pwdCheck").removeClass("invalid-feedback").addClass("valid-feedback").text("유효한 비밀번호 형식입니다");
        validateCheck.pwd = true;
    }

    if (!validateCheck.pwd && v2.length > 0) {
        swal("유효한 비밀번호를 먼저 작성해주세요");
        $("#pwd2").val(""); 
        $("#pwd").focus();

    } else { 
        if (v1.length == 0 || v2.length == 0)
            $(".pwdCheck2").html("&nbsp");
        else if (v1 == v2) {
						$("#pwd2").removeClass("is-invalid").addClass("is-valid");
						$(".pwdCheck2").removeClass("invalid-feedback").addClass("valid-feedback").text("비밀번호 일치");
            validateCheck.pwd2 = true;
        }
        else {
						$("#pwd2").removeClass("is-valid").addClass("is-invalid");
						$(".pwdCheck2").removeClass("valid-feedback").addClass("invalid-feedback").text("비밀번호 불일치");
            validateCheck.pwd2 = false;
        }
    }
});

// 전화번호
$(".phone").on("input", function () {

    if ($(this).val().length > 4)
        $(this).val($(this).val().slice(0, 4));

    var regExp1 = /^\d{3,4}$/;
    var regExp2 = /^\d{4}$/;

    if (!regExp1.test($("#phone2").val()) || !regExp2.test($("#phone3").val())) {
				$(".phoneCheck").removeClass("valid-feedback").addClass("invalid-feedback").css("display", "block").text("전화번호가 유효하지 않습니다");
        validateCheck.phone = false;
    } else {
				$(".phoneCheck").removeClass("invalid-feedback").addClass("valid-feedback").css("display", "block").text("유효한 형식의 전화번호입니다");
        validateCheck.phone = true;
    }
});

function validate() {
    for (var key in validateCheck)
        if (!validateCheck[key]) {
            var msg;
            switch (key) {
                case "name": msg = "이름이"; break;
                case "email": msg = "이메일이"; break;
                case "pwd":
                case "pwd2": msg = "비밀번호가"; break;
                case "phone": msg = "전화번호가"; break;
            }
            swal(msg + " 유효하지 않습니다");
            $("#" + key).focus();

            return false;
        }
}

// 회원정보 수정 유효성 검사 ---------------------------------------------------------------------------------------------
function memberUpdateValidate() {
	
    var regExp1 = /^\d{3,4}$/; // 숫자 3~4글자
		var regExp2 = /^\d{4}$/; // 숫자 4글자

    // 전화번호
    if(!regExp1.test($("#phone2").val()) || !regExp2.test($("#phone3").val())) {
			swal("전화번호가 유효하지 않습니다");
      return false;
		}
}

// 비밀번호 수정
function pwdValidate() {

    var regExp = /^[a-zA-Z\d]{6,12}$/;

    if(!regExp.test($("#newPwd").val())) {
        swal("비밀번호 형식이 유효하지 않습니다");
        $("#newPwd").focus();
        return false;
    } 
    else if($("#newPwd").val() != $("#newPwd2").val()) {
        swal("새로운 비밀번호가 일치하지 않습니다");
        $("#newPwd").focus();
        $("#newPwd").val("");
        $("#newPwd2").val("");
        return false;
    }
}