<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판</title>
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
	
	<div class="container-fluid">
	
		<div class="row">
		
			<div class="col-md-6">
			
				<h3>수업 제목</h3>
		<hr>

		<c:if test="${!empty param.sk && !empty param.sv}">
			<c:set var="searchStr" value="&sk=${param.sk}&sv=${param.sv}" />
		</c:if>

		<div class="form-inline mb-2">
			<label class="input-group-addon mr-3 insert-label"> </label>
			<div class="boardImg" id="titleImgArea">
				<img id="titleImg" width="200" height="200">
			</div>
		</div>

		<form action="update.do?cp=${param.cp}&no=${param.no}${searchStr}"
			method="post" enctype="multipart/form-data" role="form"
			onsubmit="return updateValidate();">

			<div class="form-inline mb-2">
				<label class="input-group-addon mr-3 insert-label">수업 제목</label>
				<h5 class="my-0" id="writer">${course.lessonTitle }</h5>
			</div>


			<div class="form-inline mb-2">
				<label class="input-group-addon mr-3 insert-label">수업 장소</label>
				<h5 class="my-0" id="writer">${course.place }</h5>
			</div>


			<div class="form-inline mb-2">
				<label class="input-group-addon mr-3 insert-label">수강최대 인원</label>
				<h5 class="my-0" id="writer">${course.maxNum }</h5>
			</div>


			<div class="form-inline mb-2">
				<label class="input-group-addon mr-3 insert-label">수업 시작 날짜</label>
				<h5 class="my-0" id="writer">${course.lessonDate }</h5>
			</div>

			<div class="form-inline mb-2">
				<label class="input-group-addon mr-3 insert-label">수업 횟수</label>
				<h5 class="my-0" id="writer">${course.lessonCnt }</h5>
			</div>

			<div class="form-inline mb-2">
				<label class="input-group-addon mr-3 insert-label">1회당 수업 시간</label>
				<h5 class="my-0" id="writer">${course.lessonTime }</h5>
			</div>


			<div class="form-inline mb-2">
				<label class="input-group-addon mr-3 insert-label">결제 여부</label>
				<h5 class="my-0" id="writer">${course.paymentCk }</h5>
			</div>

		</form>	
			</div>
			<br><br><br><br><br><br><br><br>
			<div class="col-md-6">

			<div class="form-group">
				<div>
					<label for="content">내용</label>
				</div>
				<textarea class="form-control" id="boardContent" name="boardContent"
					rows="15" style="resize: none;">${course.lessonContent }</textarea>
			</div>


			<hr class="mb-4">
	
			<div class="text-center">
				<button class=" btn btn-primary " type="button"
				onclick="location.href = '../course/studentlist.do'"
				>뒤로가기</button>
	<br><br>
			</div>
			</div>
			
		</div>
	
 </div>

	<jsp:include page="../common/footer.jsp"></jsp:include>



</body>
</html>
