<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>SKILL UP</title>
	<style>
		.pagination {
			justify-content: center;
		}
		
    .card.shadow-sm {
      margin-bottom: 24px;
    }
    
   .bg-primary {
      color: white;
    }
    
    .album.py-5 a {
    	text-decoration: none;
			color: black;
    }
    
    .card-text {
    	height: 50px;
    	overflow: hidden;
    }
	</style>
</head>
<body>
	<jsp:include page="../common/header.jsp"></jsp:include>
	<c:if test="${!empty loginMember}">
		<jsp:include page="/WEB-INF/views/message/messageButton.jsp"></jsp:include>
	</c:if>
	
	<div class="container my-5">
    ${pInfo.listCount}개의 수업 
		<div class="album py-5">
	    <div class="container">
	      <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3">
					<c:forEach var="lesson" items="${lessonList}">
						<a href="${contextPath}/lesson/myView.do?cp=${pInfo.currentPage}&no=${lesson.lessonNo}">
			        <div class="col">
			          <div class="card shadow-sm">
									<c:forEach var="thumbnail" items="${fList}">
										<c:if test="${lesson.lessonNo == thumbnail.parentLessonNo}">
											<img width="100%" height="225" src="${contextPath}/resources/uploadImages/lesson/${thumbnail.fileName}">
										</c:if>
									</c:forEach>
													
			            <div class="card-body">
			              <div class="d-flex justify-content-end align-items-center">
			                <span class="badge bg-primary">${lesson.categoryName}</span>
			              </div>
			              <div class="d-flex justify-content-between align-items-center">
				              <span class="card-text">${lesson.lessonTitle}</span>
										</div>
			              <div class="d-flex justify-content-between align-items-center">
			                <small class="text-muted"><fmt:formatNumber value="${lesson.price}" type="currency"/></small>
											${lesson.memberName}
			              </div>
			            </div>
			          </div>
			        </div>
						</a>
					</c:forEach>
	      </div>
	    </div>
  	</div>
  
		<%------------------------- Pagination -------------------------%>
		<c:url var="pageUrl" value="/lesson/myList.do"/>
		
		<c:set var="firstPage" value="${pageUrl}?cp=1"/>
		<c:set var="lastPage" value="${pageUrl}?cp=${pInfo.maxPage}"/>
		
		<fmt:parseNumber var="c1" value="${(pInfo.currentPage - 1) / pInfo.pageSize}" integerOnly="true"/>
		<fmt:parseNumber var="prev" value="${c1 * pInfo.pageSize}" integerOnly="true"/>
		<c:set var="prevPage" value="${pageUrl}?cp=${prev}"/>
		
		<fmt:parseNumber var="c2" value="${(pInfo.currentPage + 9) / pInfo.pageSize}" integerOnly="true"/>
		<fmt:parseNumber var="next" value="${c2 * pInfo.pageSize + 1}" integerOnly="true"/>
		<c:set var="nextPage" value="${pageUrl}?cp=${next}"/>
		
		<div class="my-5">
			<ul class="pagination">
				<c:if test="${pInfo.currentPage > 10}">
					<li><a class="page-link" href="${firstPage}">&lt;&lt;</a></li>
					<li><a class="page-link" href="${prevPage}">&lt;</a></li>
				</c:if>
				
				<c:forEach var="page" begin="${pInfo.startPage}" end="${pInfo.endPage}">
					<c:choose>
						<c:when test="${pInfo.currentPage == page}">
							<li><a class="page-link">${page}</a></li>
						</c:when>
						<c:otherwise>
							<li><a class="page-link" href="${pageUrl}?cp=${page}">${page}</a></li>
						</c:otherwise>
					</c:choose>
				</c:forEach>
				
				<c:if test="${next <= pInfo.maxPage}">
					<li><a class="page-link" href="${nextPage}">&gt;</a></li>
					<li><a class="page-link" href="${lastPage}">&gt;&gt;</a></li>
				</c:if>
			</ul>
		</div>
	
	</div>
	<jsp:include page="../common/footer.jsp"></jsp:include>
</body>
</html>
