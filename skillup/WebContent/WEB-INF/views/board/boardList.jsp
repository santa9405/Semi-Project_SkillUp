<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판</title>
<style>
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
#boardlist 	 {
	border-bottom: 1px solid lightgray;
}

#boardlist * {
	font-family: 'NanumBarunGothic';
}
#boardmenu {
	position: fixed;
	top: 500px;
	left: 300px;
}
#wrapper {
	height: 1080px;
}
</style>
</head>

<body>
<jsp:include page="../common/header.jsp"/>
<jsp:include page="/WEB-INF/views/message/messageButton.jsp"/>
<div id="wrapper">
<div class="list-group" id="boardmenu">
	<a href="${contextPath}/board/list?boardNo=2" class="list-group-item list-group-item-action list-group-item-primary">공지사항</a>
  <a href="${contextPath}/board/list?boardNo=1" class="list-group-item list-group-item-action list-group-item-primary">자유게시판</a>
  <a href="${contextPath}/board/list?boardNo=3" class="list-group-item list-group-item-action list-group-item-primary">Q&A</a>
</div>
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
    <form class="form-inline my-2 my-lg-0" action="${contextPath}/search" method="post">
    
	    <select class="form-control" name="sk">
				<option value="titcont">제목+내용</option>
	      <option value="title">제목</option>
				<option value="content">내용</option>
				<option value="writer">작성자</option>
	    </select>
      <input class="form-control mr-sm-2" type="search" placeholder="검색어를 입력하세요" name="sv">
      
      
      <button class="btn btn-outline-primary my-2 my-sm-0" type="submit">검색</button>
      
     <input type="hidden" name="boardNo" value="${boardNo}"/>
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
	<c:forEach var="board" items="${list}">
		<tr height="5px" onclick="fnSelect(${board.documentNo})">
			<td width="8%">${board.rowNum}</td>
			<td width="8%">${board.categoryName}</td>
			<td class="boardTitle">${board.boardTitle}</td>
			<fmt:formatDate var="createDate" value="${board.boardDt}" pattern="yyyy-MM-dd-HH-mm"/>						
			<fmt:formatDate var="today" value="<%= new java.util.Date() %>" pattern="yyyy-MM-dd-HH-mm"/>
			<td width="10%"> 
				<c:choose>
					<c:when test="${createDate.substring(0, 13)==today.substring(0, 13)}">
						${today.substring(14)-createDate.substring(14)}분 전
					</c:when>
					<c:when test="${createDate.substring(0, 10)==today.substring(0, 10)}">
						${today.substring(11, 13)-createDate.substring(11 ,13)}시간 전
					</c:when>
					<c:otherwise>
						<fmt:formatDate value="${board.boardDt}" pattern="MM.dd"/>
					</c:otherwise>
				</c:choose>
			</td>
			<td width="10%">${board.memberName}</td>
			<td width="8%">${board.readCount}</td>
		</tr>
	</c:forEach>
	</tbody>
	</table>

<div>
<c:if test="${!empty loginMember}">
	<button type="button" class="btn btn-dark" id="write">글작성</button>
</c:if>
<c:choose>
	<c:when test="${!empty param.sk && !empty param.sv}">
		<c:url var="pageUrl" value="/search"/>
		<c:set var="searchStr" value="&sk=${param.sk}&sv=${param.sv}"/>
	</c:when>
	<c:otherwise>
		<c:url var="pageUrl" value="/board/list"></c:url>
	</c:otherwise>
</c:choose>
		<c:set var="firstPage" value="${pageUrl}?page=1&boardNo=${boardNo}${searchStr}"/>
		<c:set var="lastPage" value="${pageUrl}?page=${pInfo.maxPage}&boardNo=${boardNo}${searchStr}"/>
		
		<fmt:parseNumber var="c1" value="${(pInfo.currentPage-1)/10}" integerOnly="true"/>
		<fmt:parseNumber var="prev" value="${c1*10}" integerOnly="true"/>
		<c:set var="prevPage" value="${pageUrl}?page=${prev}&boardNo=${boardNo}${searchStr}"/>
		
		<fmt:parseNumber var="c2" value="${(pInfo.currentPage+9)/10}" integerOnly="true"/>
		<fmt:parseNumber var="next" value="${c2*10+1}" integerOnly="true"/>
		<c:set var="nextPage" value="${pageUrl}?page=${next}&boardNo=${boardNo}${searchStr}"/>
			
	<ul class="pagination justify-content-center">

	<c:if test="${pInfo.currentPage > 10}">
    <li class="page-item">
      <a class="page-link" href="${pageUrl}?page=${pInfo.startPage}&boardNo=${boardNo}${searchStr}">
        <span aria-hidden="true" style="color: black;">&laquo;</span></a>
    </li>
    <li class="page-item">
      <a class="page-link" href="${prevPage}">
        <span aria-hidden="true" style="color: black;">&lt;</span></a>
    </li>
   </c:if>
    
    <c:forEach var="page" begin="${pInfo.startPage}" end="${pInfo.endPage}">
	    <c:choose>
	    	<c:when test="${pInfo.currentPage==page}">
		    	<li class="page-item"><a class="page-link" style="color: black;">${page}</a></li>
	    	</c:when>
	    	<c:otherwise>
	    		<li class="page-item"><a class="page-link" style="color: black;" href="${pageUrl}?page=${page}&boardNo=${boardNo}${searchStr}">${page}</a></li>
	    	</c:otherwise>
	    </c:choose>
    </c:forEach>
    
   <c:if test="${next <= pInfo.maxPage}">
    <li class="page-item">
      <a class="page-link" href="${nextPage}" aria-label="Next">
        <span aria-hidden="true" style="color: black;">&gt;</span></a>
    </li>
    <li class="page-item">
      <a class="page-link" href="${pageUrl}?page=${pInfo.endPage}&boardNo=${boardNo}${searchStr}" aria-label="Next">
        <span aria-hidden="true" style="color: black;">&raquo;</span></a>
    </li>
   </c:if>
    
  </ul>
</div>

</div>
</div>
<footer>
	<jsp:include page="../common/footer.jsp"/>
</footer>
<script>

function fnSelect(i) {
	path = "${contextPath}/board/view?document=" + i + "&page=${pInfo.currentPage}&boardNo=${boardNo}";
	location.href = path;
}

$("#write").on("click", function() {
	path = "${contextPath}/board/write?boardNo=${boardNo}";
	location.href = path;
});
</script>
</body>

</html>