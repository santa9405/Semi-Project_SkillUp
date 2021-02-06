<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판</title>
<style>
.board {
	border-bottom: 0.5px solid lightgray;
	margin: auto;
}

.title {
	height: 120px;
}

.content {
	height: 800px;
}

.title>div {
	
}

.buttons {
	display: inline;
}
table {
	text-align: center;
}
tbody > *{
	line-height: 12px;
}

container {
	width:1080px;
	margin:auto;
	padding: 30px;
}

td:hover {
	cursor: pointer;
}
.page-link {
	color: black;
}
#boardlist {
	border-bottom: 0.5px solid lightgray;
}
#boardlist * {
	font-family: 'NanumBarunGothic';
}

#boardmenu {
	position: fixed;
	top: 500px;
	left: 300px;
}

</style>
</head>

<body>
	<jsp:include page="../common/header.jsp"/>
	<jsp:include page="/WEB-INF/views/message/messageButton.jsp"/>
	<div class="list-group" id="boardmenu">
  <a href="${contextPath}/board/list?boardNo=2" class="list-group-item list-group-item-action list-group-item-primary">공지사항</a>
  <a href="${contextPath}/board/list?boardNo=1" class="list-group-item list-group-item-action list-group-item-primary">자유게시판</a>
  <a href="${contextPath}/board/list?boardNo=3" class="list-group-item list-group-item-action list-group-item-primary">Q&A</a>
</div>

	<div class="container">
		<div class="board">
			<div class="board title">
				<div class="title-category"
					style="height: 20px; padding-left: 15px;">
					<span>${board.categoryName}</span>
				</div>
				<div class="title-title" style="height: 60px; padding-left: 15px; line-height:1">
					<br> <span style="font-size: 30px;">${board.boardTitle}</span>
				</div>
				<div class="title-info" style="height: 30px; padding-left: 15px;">
					<fmt:formatDate var="createDate" value="${board.boardDt}" pattern="yyyy-MM-dd-HH-mm"/>						
					<fmt:formatDate var="today" value="<%= new java.util.Date() %>" pattern="yyyy-MM-dd-HH-mm"/>
					<span>${board.memberName} | 조회수 ${board.readCount} | 
						<c:choose>
							<c:when test="${createDate.substring(0, 13)==today.substring(0, 13)}">
								${today.substring(14)-createDate.substring(14)}분 전
							</c:when>
							<c:when test="${createDate.substring(0, 10)==today.substring(0, 10)}">
								${today.substring(11, 13)-createDate.substring(11 ,13)}시간 전
							</c:when>
							<c:otherwise >
								<fmt:formatDate value="${board.boardDt}" pattern="MM.dd"/>
							</c:otherwise>
						</c:choose>
					</span>
				</div>
			</div>
			
			<div class="board content" style="padding-left:15px; padding-right:15px;">
				<p style="margin-top:1rem;">${board.boardContent}
				</p>
			</div>
		</div>
		<br>
		<div>
			<div class="buttons">
				<button type="button" class="btn btn-outline-secondary" id="nextDocument">다음글↑</button>
				<button type="button" class="btn btn-outline-secondary" id="prevDocument">이전글↓</button>
				<button type="button" class="btn btn-outline-secondary" id="boardListBtn">목록으로</button>
			</div>
			
			<div class="buttons float-right">
				<c:if test="${!empty loginMember}">
					<button type="button" class="btn btn-outline-dark" id="reportBoard" data-toggle="modal" data-target="#exampleModal">신고</button>
				</c:if>
				<c:if test="${!empty loginMember && (board.memberNo == loginMember.memberNo)}">
					<button type="button" class="btn btn-outline-dark" id="reviseBoard">수정</button>
					<button type="button" class="btn btn-outline-dark" id="deleteBoard">삭제</button>
				</c:if>
			</div>

		</div>
	</div>

	<jsp:include page="reply.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/views/message/messageButton.jsp"></jsp:include>

<div class="container">

<nav class="navbar navbar-expand-lg navbar-light">
	
  <a class="navbar-brand" href="${contextPath}/board/list?boardNo=${boardNo}">
  <c:choose>
		<c:when test="${boardNo==1}">자유게시판</c:when>
		<c:when test="${boardNo==2}">공지사항</c:when>
		<c:when test="${boardNo==3}">Q&A</c:when>
	</c:choose>
  </a>

  <div class="collapse navbar-collapse" id="navbarSupportedContent">
    <ul class="navbar-nav mr-auto">
    </ul>
    <form class="form-inline my-2 my-lg-0">
      <input class="form-control mr-sm-2" type="search" placeholder="Search" aria-label="Search">
      <button class="btn btn-outline-primary my-2 my-sm-0" type="submit">Search</button>
    </form>
  </div>
</nav>

	<table class="table table-hover table-striped" id="boardlist">
	<thead>
	<tr height="15px">
		<th width="8%">글번호</th>
		<th width="8%"></th>
		<th>제목</th>
		<th width="10%">날짜</th>
		<th width="10%">작성자</th>
		<th width="8%">조회수</th>
	</tr>
	</thead>
	<tbody>
	<c:forEach var="document" items="${list}">
		<tr height="5px" onclick="fnSelect(${document.documentNo})">
			<td width="8%">
			<c:choose>
				<c:when test="${document.documentNo == board.documentNo}">
					<img src="${contextPath}/resources/img/right-arrow.png" style="width:25px; height:12px;">
				</c:when>
				<c:otherwise>
					${document.rowNum}
				</c:otherwise>
			</c:choose>
				
			
			</td>
			<td width="8%">${document.categoryName}</td>
			<td class="boardTitle">${document.boardTitle}</td>
			<fmt:formatDate var="createDate" value="${document.boardDt}" pattern="yyyy-MM-dd-HH-mm"/>						
			<fmt:formatDate var="today" value="<%= new java.util.Date() %>" pattern="yyyy-MM-dd-HH-mm"/>
			<td width="10%"> 
				<c:choose>
					<c:when test="${createDate.substring(0, 13)==today.substring(0, 13)}">
						${today.substring(14)-createDate.substring(14)}분 전
					</c:when>
					<c:when test="${createDate.substring(0, 10)==today.substring(0, 10)}">
						${today.substring(11, 13)-createDate.substring(11 ,13)}시간 전
					</c:when>
					<c:otherwise >
						<fmt:formatDate value="${document.boardDt}" pattern="MM.dd"/>
					</c:otherwise>
				</c:choose>
			</td>
			<td width="10%">${document.memberName}</td>
			<td width="8%">${document.readCount}</td>
			
		</tr>
	</c:forEach>
	</tbody>
	</table>


<div>
<c:if test="${!empty loginMember}">
	<button type="button" class="btn btn-dark" id="write">글작성</button>
</c:if>

		<c:set var="firstPage" value="${contextPath}/board/list?page=1&boardNo=${boardNo}"/>
		<c:set var="lastPage" value="${contextPath}/board/list?page=${pInfo.maxPage}&boardNo=${boardNo}"/>
		
		<fmt:parseNumber var="c1" value="${(pInfo.currentPage-1)/10}" integerOnly="true"/>
		<fmt:parseNumber var="prev" value="${c1*10}" integerOnly="true"/>
		<c:set var="prevPage" value="${contextPath}/board/list?page=${prev}&boardNo=${boardNo}"/>
		
		<fmt:parseNumber var="c2" value="${(pInfo.currentPage+9)/10}" integerOnly="true"/>
		<fmt:parseNumber var="next" value="${c2*10+1}" integerOnly="true"/>
		<c:set var="nextPage" value="${contextPath}/board/list?page=${next}&boardNo=${boardNo}"/>
			
	<ul class="pagination justify-content-center">

	<c:if test="${pInfo.currentPage > 10}">
    <li class="page-item">
      <a class="page-link" href="${contextPath}/board/list?page=${pInfo.startPage}&boardNo=${boardNo}" aria-label="Previous">
        <span aria-hidden="true" style="color: black;">&laquo;</span></a>
    </li>
    <li class="page-item">
      <a class="page-link" href="${prevPage}" aria-label="Previous">
        <span aria-hidden="true" style="color: black;">&lt;</span></a>
    </li>
   </c:if>
    
    <c:forEach var="page" begin="${pInfo.startPage}" end="${pInfo.endPage}">
	    <c:choose>
	    	<c:when test="${pInfo.currentPage==page}">
		    	<li class="page-item"><a class="page-link" style="color: black;">${page}</a></li>
	    	</c:when>
	    	<c:otherwise>
	    		<li class="page-item"><a class="page-link" style="color: black;" href="${contextPath}/board/list?page=${page}&boardNo=${boardNo}">${page}</a></li>
	    	</c:otherwise>
	    </c:choose>
    </c:forEach>
    
   <c:if test="${next <= pInfo.maxPage}">
    <li class="page-item">
      <a class="page-link" href="${nextPage}" aria-label="Next">
        <span aria-hidden="true" style="color: black;">&gt;</span></a>
    </li>
    <li class="page-item">
      <a class="page-link" href="${contextPath}/board/list?page=${pInfo.endPage}&boardNo=${boardNo}" aria-label="Next">
        <span aria-hidden="true" style="color: black;">&raquo;</span></a>
    </li>
   </c:if>
    
  </ul>
</div>

</div>
	
	<div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title" id="exampleModalLabel">글 신고하기</h5>
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	          <span aria-hidden="true">&times;</span>
	        </button>
	      </div>
	      <div class="modal-body">
	        <form method="post" action="${contextPath}/board/report">
	        
	        <div id="reviewNoArea">
	          </div>
	       
	        <div class="form-group reportCode">
	        <input type="hidden" name="documentNo" value="${board.documentNo}">
	        		<div>
			        <label for="recipient-name" class="col-form-label">신고 종류</label>
			        </div>
			        <select class="custom-select" id="categoryCode2" name="reportCategory" style="width: 200px;">
								<option value="10">욕설/비방</option>
								<option value="20">부적절한 글</option>
								<option value="30">기타</option>
					 </select>
	         </div>
	          <div class="mb-3">
				<label for="message-text" class="col-form-label">신고 내용</label>
				<textarea class="form-control" id="message-text"
					name="reportContent" rows="7" style="resize:none" placeholder="신고 내용을 입력해 주세요." required></textarea>
			  </div>
	      </div>
	      <div class="modal-footer">
	        <button type="submit" class="btn btn-primary">신고하기</button>
	        <button type="button" class="btn btn-secondary" data-dismiss="modal">취소</button>
	      </div>
	        </form>
	    </div>
	  </div>
	</div>
	<jsp:include page="../common/footer.jsp"/>
	<script>
	
	// 목록에서 다른글 조회
	function fnSelect(item) {
		path = "${contextPath}/board/view?document=" + item + "&page=${pInfo.currentPage}&boardNo=${boardNo}";
		location.href = path;
	}
	
	// 목록으로 가기 버튼
	$("#boardListBtn").on("click", function() {
		path = "${contextPath}/board/list?boardNo=${boardNo}"
		location.href = path;
	});
	
	// 다음글 비활성화
	if($("#boardlist > tbody > tr:first-child() > td:nth-child(1)").text() == 0 && ${pInfo.currentPage==pInfo.startPage}) {
		document.getElementById("nextDocument").disabled = true;
	}
	// 다음글 버튼
	$("#nextDocument").on("click", function() {
		if($("#boardlist > tbody > tr:first-child() > td:nth-child(1)").text() == 0) {
			path = "${contextPath}/board/next?document=" + ${board.documentNo} + "&boardNo=${boardNo}&page=${pInfo.currentPage-1}"
		}
		else {
			path = "${contextPath}/board/next?document=" + ${board.documentNo} + "&boardNo=${boardNo}&page=${pInfo.currentPage}"
		}
		location.href = path;
	});
	
	// 이전글 비활성화
	if($("#boardlist > tbody > tr:last-child() > td:nth-child(1)").text() == 0 && ${pInfo.currentPage==pInfo.endPage}) {
		document.getElementById("prevDocument").disabled = true;
	}
	// 이전글 버튼
	$("#prevDocument").on("click", function() {
		if($("#boardlist > tbody > tr:last-child() > td:nth-child(1)").text() == 0) {
			path = "${contextPath}/board/prev?document=" + ${board.documentNo} + "&boardNo=${boardNo}&page=${pInfo.currentPage+1}"
		}
		else {
			path = "${contextPath}/board/prev?document=" + ${board.documentNo} + "&boardNo=${boardNo}&page=${pInfo.currentPage}"
		}
		location.href = path;
	});
	
	// 삭제버튼
	$("#deleteBoard").on("click", function() {
		if(window.confirm("정말 삭제하시겠습니까?")) {
			path = "${contextPath}/board/delete?document=${board.documentNo}";
			location.href = path;
		}
	});
	
	// 수정 버튼
	$("#reviseBoard").on("click", function() {
		path = "${contextPath}/board/revise?document=${board.documentNo}&page=${pInfo.currentPage}&boardNo=${boardNo}"
		location.href = path;
	});
	
	// 글작성 버튼
	$("#write").on("click", function() {
		path = "${contextPath}/board/write?boardNo=${boardNo}";
		location.href = path;
	});
	
</script>
</body>
</html>