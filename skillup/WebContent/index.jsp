<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>SKILL UP</title>
	<style>
		.card.shadow-sm {
			margin-bottom: 24px;
		}
		
		.album h4 {
			font-weight: bolder;
			line-height: 50px;
		}
    
   .bg-primary {
      color: white;
    }
    
    .album {
    	margin-bottom: 50px;
    }

    .album.over a {
    	text-decoration: none;
			color: black;
    }
    
    a.lessonCard:hover {
      text-decoration: none;
      color: black;
    }   
    
    .card-text {
    	height: 50px;
    	overflow: hidden;
    }

		#message {
			position: fixed;
			bottom: 100px;
			right: 100px;
		}
	</style>
</head>
<body>
	<jsp:include page="WEB-INF/views/common/header.jsp"></jsp:include>
	<c:if test="${!empty loginMember}">
		<jsp:include page="/WEB-INF/views/message/messageButton.jsp"></jsp:include>
	</c:if>
	
	<div class="container">

		<c:if test="${! empty loginMember.interest}">
			<div class="album over">
				<div class="container">
					<h4>내 관심 분야 수업</h4>
					<div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3">
						<c:forEach var="lesson" items="${intList}" varStatus="vs">
							<c:if test="${vs.index < 3}">
								<a href="${contextPath}/lesson/view.do?no=${lesson.lessonNo}">
					        <div class="col">
					          <div class="card shadow-sm">
											<c:forEach var="thumbnail" items="${intfList}">
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
							</c:if>
						</c:forEach>
					</div>
				</div>
			</div>
		</c:if>

		<div class="album over">
			<div class="container">
				<h4>좋아요가 많은 수업</h4>
				<div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3">
					<c:forEach var="lesson" items="${likeList}" varStatus="vs">
						<c:if test="${vs.index < 3}">
							<a href="${contextPath}/lesson/view.do?cp=${pInfo.currentPage}&no=${lesson.lessonNo}">
				        <div class="col">
				          <div class="card shadow-sm">
										<c:forEach var="thumbnail" items="${likefList}">
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
						</c:if>
					</c:forEach>
				</div>
			</div>
		</div>

		<div class="album over">
			<div class="container">
				<h4>인기 수업</h4>
				<div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3">
					<c:forEach var="lesson" items="${rankList}" varStatus="vs">
						<c:if test="${vs.index < 3}">
							<a href="${contextPath}/lesson/view.do?cp=${pInfo.currentPage}&no=${lesson.lessonNo}">
				        <div class="col">
				          <div class="card shadow-sm">
										<c:forEach var="thumbnail" items="${rankfList}">
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
						</c:if>
					</c:forEach>
				</div>
			</div>
		</div>

		<div class="album over">
			<div class="container">
				<h4>최신 수업</h4>
				<div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3">
					<c:forEach var="lesson" items="${latestList}" varStatus="vs">
						<c:if test="${vs.index < 3}">
							<a href="${contextPath}/lesson/view.do?cp=${pInfo.currentPage}&no=${lesson.lessonNo}">
				        <div class="col">
				          <div class="card shadow-sm">
										<c:forEach var="thumbnail" items="${latestFList}">
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
						</c:if>
					</c:forEach>
				</div>
			</div>
		</div>

	</div>
	<jsp:include page="WEB-INF/views/common/footer.jsp"></jsp:include>
</body>
</html>
