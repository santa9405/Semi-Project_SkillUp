<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>수업등록</title>
<style>
.insert-label {
	display: inline-block;
	width: 80px;
	line-height: 40px
}

.boardImg {
	cursor: pointer;
}
</style>
</head>
<body>
	<jsp:include page="../common/header.jsp"></jsp:include>

	<div class="container my-5">

		<h3>수업 등록</h3>
		<hr>

		<c:if test="${!empty param.sk && !empty param.sv}">
			<c:set var="searchStr" value="&sk=${param.sk}&sv=${param.sv}" />
		</c:if>

		<form action="${contextPath }/course/insert.do" method="post"
			enctype="multipart/form-data" role="form"
			onsubmit="return updateValidate();">

			<div class="mb-2">
				<label class="input-group-addon mr-3 insert-label">카테고리</label> <select
					class="custom-select" id="categoryCode" name="categoryCode"
					style="width: 150px;">
					<option value="10">외국어</option>
					<option value="20">스포츠</option>
					<option value="30">사진/영상</option>
					<option value="40">공예</option>
					<option value="50">요리</option>
					<option value="60">사진/영상</option>
					<option value="70">미술</option>
					<option value="80">음악</option>

				</select>
			</div>

			<div class="form-inline mb-2">
				<label class="input-group-addon mr-3 insert-label">메인 이미지 </label>
				<div class="boardImg" id="titleImgArea">
					<img id="titleImg" width="200" height="200">
				</div>
			</div>

			<div class="form-inline mb-2">
				<label class="input-group-addon mr-3 insert-label">제목</label> <input
					type="text" class="form-control" id="lessonTitle"
					name="lessonTitle" size="70" value="">
			</div>

			<div class="form-inline mb-2">
				<label class="input-group-addon mr-3 insert-label">가격</label> <input
					type="number" class="form-control" id="price" name="price" size="70" value="">
			</div>

			<div class="form-inline mb-2">
				<label class="input-group-addon mr-3 insert-label">수업 장소</label> <input
					type="text" class="form-control" id="place" name="place" size="70" value="">
			</div>

			<div class="form-inline mb-2">
				<label class="input-group-addon mr-3 insert-label">최대인원</label> <input
					type="number" class="form-control" id="maxNum" name="maxNum" size="70" value="">
			</div>

			<div class="form-inline mb-2">
				<label class="input-group-addon mr-3 insert-label">수업시작날짜</label> <input
					type="date" class="form-control" id="lessonDt" name="lessonDt" size="70" value="">
			</div>

			<div class="form-inline mb-2">
				<label class="input-group-addon mr-3 insert-label">수업횟수</label> <input
					type="number" class="form-control" id="lessonCnt" name="lessonCnt" size="70" value="">
			</div>
			
			<div class="form-inline mb-2">
				<label class="input-group-addon mr-3 insert-label">1회당 수업시간(분)</label> <input
					type="number" class="form-control" id="lessonTime" name="lessonTime" size="70" value="">
			</div>


			<div class="form-inline mb-2">
				<label class="input-group-addon mr-3 insert-label">작성자</label>	
			<input type="text" readonly class="form-control-plaintext" id="name"
				value="${loginMember.memberName}">
			</div>


			<div class="form-inline mb-2">
				<label class="input-group-addon mr-3 insert-label">작성일</label>
				<h5 class="my-0" id="today"></h5>
			</div>
			<hr>

			<!-- 파일 업로드 하는 부분 -->
			<div id="fileArea">
				<!--  multiple 속성
						- input 요소 하나에 둘 이상의 값을 입력할 수 있음을 명시 (파일 여러개 선택 가능)
					 -->
				<input type="file" id="img0" name="img0" onchange="LoadImg(this,0)">
			</div>

			<div class="form-group">
				<div>
					<label for="content">내용</label>
				</div>
				<textarea class="form-control" id="lessonContent"
					name="lessonContent" rows="15" style="resize: none;">${board.boardContent }</textarea>
			</div>


			<hr class="mb-4">

			<div class="text-center">
				<button class=" btn btn-primary "type="submit" data-toggle="modal"
					data-target="#exampleModal">등록하기</button>
				<button type="button" class="btn btn-primary"
					onclick="location.href = 'instructor.do'">이전으로</button>

			</div>

		</form>

		<!-- Modal -->
		<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog"
			aria-labelledby="exampleModalLabel" aria-hidden="true">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title" id="exampleModalLabel">등록 확인</h5>
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body">등록 하시겠습니까?</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-primary"
							onclick="location.href = 'instructor.do'">등록하기</button>
						<button type="button" class="btn btn-secondary"
							data-dismiss="modal">취소하기</button>
					</div>
				</div>
			</div>
		</div>
	</div>

	<jsp:include page="../common/footer.jsp"></jsp:include>


	<script>
		(function printToday() {
			// 오늘 날짜 출력 
			var today = new Date();
			var month = (today.getMonth() + 1);
			var date = today.getDate();

			var str = today.getFullYear() + "-"
					+ (month < 10 ? "0" + month : month) + "-"
					+ (date < 10 ? "0" + date : date);
			$("#today").html(str);
		})();

		// 유효성 검사 
		function boardValidate() {
			if ($("#boardTitle").val().trim().length == 0) {
				alert("제목을 입력해 주세요.");
				$("#title").focus();
				return false;
			}

			if ($("#content").val().trim().length == 0) {
				alert("내용을 입력해 주세요.");
				$("#content").focus();
				return false;
			}
		}

		// 이미지 영역을 클릭할 때 파일 첨부 창이 뜨도록 설정하는 함수

		$(function() {
			$("#fileArea").hide();
			$(".boardImg").on("click", function() {

				var index = $(".boardImg").index(this);
				$("#img" + index).click();

			});
		});
		// 각각의 영역에 파일을 첨부 했을 경우 미리 보기가 가능하도록 하는 함수
		function LoadImg(value, num) {

			if (value.files && value.files[0]) {
				var reader = new FileReader();

				reader.readAsDataURL(value.files[0]);

				reader.onload = function(e) {

					$(".boardImg").eq(num).children("img").attr("src",
							e.target.result);

				}
			}
		}
	</script>

</body>
</html>
