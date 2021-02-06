<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<style>
.card-header {
    background-color: #fff;
}
</style>
<div class="col-sm-4 mt-5">
	<div class="accordion" id="accordionExample">

		<div class="card">
			<div class="card-header">
				<button class="btn btn-block text-left" data-toggle="collapse" data-target="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
					수업 관리 
					<img src="${contextPath}/resources/img/down.png" style="float: right">
				</button>
			</div>
			<div id="collapseOne" class="collapse list-group list-group-flush" data-parent="#accordionExample">
				<a href="${contextPath}/course/studentlist.do" class="list-group-item list-group-item-action">수강 목록</a> 
				<a href="${contextPath}/course/studentbeforecourselist.do" class="list-group-item list-group-item-action">지난 수강 목록</a> 
				<a href="${contextPath}/lesson/likesView.do" class="list-group-item list-group-item-action">좋아요한 수업</a>
			</div>
		</div>
		<div class="card">
			<div class="card-header">
				<button class="btn btn-block text-left" data-toggle="collapse" data-target="#collapseTwo" aria-expanded="true" aria-controls="collapseTwo">
					게시판 관리 
					<img src="${contextPath}/resources/img/down.png" style="float: right">
				</button>
			</div>
			<div id="collapseTwo" class="collapse list-group list-group-flush" data-parent="#accordionExample">
				<a href="${contextPath}/board/myboard" class="list-group-item list-group-item-action">내 글 보기</a>
				<a href="${contextPath}/board/myreply" class="list-group-item list-group-item-action">내 댓글 보기</a>
			</div>
		</div>
		<div class="card">
			<div class="card-header">
				<button class="btn btn-block text-left"
					onclick="location.href='${contextPath}/pay/payment.do'">
					결제 내역</button>
			</div>
		</div>
		<div class="card">
			<div class="card-header">
				<button class="btn btn-block text-left"
					onclick="location.href='${contextPath}/member/myPage.do'">
					정보 수정</button>
			</div>
		</div>
	</div>
</div>
