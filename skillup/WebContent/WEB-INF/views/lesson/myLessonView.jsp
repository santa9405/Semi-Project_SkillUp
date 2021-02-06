<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>SKILL UP</title>
<style>
  h4 {
    margin-top: 5px;
  }

  .mainImg {
    width: 100%;
    margin-bottom: 30px;
  }

  .lessonArea>.row>.col-12 {
    margin-bottom: 45px;
  }

  .title {
    font-size: 20px;
    font-weight: bolder;
    line-height: 50px;
  }

  .lessonArea {
  	margin-bottom: 50px;
  }
  
  .payArea {
    display: inline-block;
    position: sticky !important;
    top: 200px;
    width: 300px;
    height: 100px;
  }

  #profileImg {
    width: 150px;
    height: 150px;
    margin-top: 15px;
  }

  #likeBtn {
    border: 1px solid #ddd;
    border-radius: 5px;
    padding: 5px;
    background-color: rgba(255, 255, 255, 0);
  }

	.likeCnt {
		color: #6c757d;
	}
	
  .like {
    background-image: url('${contextPath}/resources/img/like2.png');
    background-repeat: no-repeat;
  }
	
  .bg {
    display: inline;
    padding: 6px;
    border-radius: 12px;
    font-size: 14px;
    color: #555969;
    background-color: rgb(231, 229, 229);
  }
  
  .lessonImg {
  	width: 90%;
  }
  
	.payArea img, .payArea h6 {
		cursor:pointer;
	}
	
</style>
</head>
<body>
	<jsp:include page="/WEB-INF/views/common/header.jsp"></jsp:include>
	<c:if test="${!empty loginMember}">
		<jsp:include page="/WEB-INF/views/message/messageButton.jsp"></jsp:include>
	</c:if>
	 <div class="container">
      <div class="row g-3">
        <div class="col-md-7 col-lg-8 lessonArea">
					<h5><span class="badge badge-primary">${lesson.categoryName}</span></h5>
          <h4 class="d-flex justify-content-between align-items-center mb-3">
            	${lesson.lessonTitle}
          </h4>
          <c:forEach var="file" items="${fList}" varStatus="vs">
          	<c:if test="${vs.first}">
							<img class="mainImg" id="${file.fileNo}" src="${contextPath}/resources/uploadImages/lesson/${file.fileName}">
          	</c:if>
					</c:forEach>

          <div class="row g-3">

            <div class="col-12">
              <div class="title">수업 인원</div>
              <div class="content">
              	<h4 style="display:inline;">
	              	<span class="badge badge-pill badge-secondary">
	              		<c:choose>
											<c:when test="${lesson.lessonCnt} == 1">1:1 수업</c:when>
											<c:otherwise>그룹 수업 </c:otherwise>
										</c:choose>
	              	</span>
              	</h4>&nbsp;&nbsp;${lesson.lessonCnt}
              </div>
            </div>

            <div class="col-12">
              <div class="title">수업 장소</div>
              <div class="content">
              	<h4 style="display:inline;">
	              	<span class="badge badge-pill badge-secondary">
	              		<c:choose>
										<c:when test="${lesson.lessonType eq 'N'.charAt(0)}">온라인</c:when>
										<c:otherwise>오프라인</c:otherwise>
									</c:choose>
	              	</span>
              	</h4>&nbsp;&nbsp;${lesson.place}
              </div>
            </div>

            <div class="col-12">
              <div class="title">수업 시작일</div>
              <div class="content">
               	${lesson.lessonDate}
              </div>
            </div>

            <div class="col-12">
              <div class="title">강사 소개</div>
              <div class="content">
			           ${instructor.introduction}        
              </div>
            </div>

            <div class="col-12">
              <div class="title">수업 내용</div>
              <div class="content">
              	${lesson.lessonContent}
              </div>
            </div>
            
            <div class="col-12 my-5">
              <div class="content">
              	<c:forEach var="file" items="${fList}" varStatus="vs">
									<c:if test="${!vs.first}">
										<img class="d-block lessonImg" id="${file.fileNo}" src="${contextPath}/resources/uploadImages/lesson/${file.fileName}">
			          	</c:if>
								</c:forEach>
              </div>
            </div>
          </div>
        </div>

        <!-- -------------------------------------------------------------------------------------------- -->

        <div class="col-md-4 order-md-2 mb-4 payArea">

          <ul class="list-group mb-3">
            <li class="list-group-item d-flex justify-content-between lh-condensed" style="padding-bottom: 30px;">
              <div class="col-12" style="text-align: center;">
                <div class="profileImg" id="titleImgArea" >
									<img id="profileImg" width="200" height="200" onclick="location.href='${contextPath}/instructor/reperence.do?no=${instructor.instr}'">
								</div>
                <h6 class="my-0" style="line-height: 40px;" onclick="location.href='${contextPath}/instructor/reperence.do?no=${instructor.instr}'">${lesson.memberName}</h6>
              </div>
            </li>
            <li class="list-group-item d-flex justify-content-between lh-condensed">
              <div>
                <h6 class="my-0">총 ${lesson.lessonCnt}회</h6>
                <small class="text-muted">1회당 ${lesson.lessonTime}분</small>
              </div>
              <strong><fmt:formatNumber value="${lesson.price}" type="currency"/></strong>
            </li>
            <c:if test="${loginMember.memberNo == lesson.instr}">
	            <li class="list-group-item justify-content-between">
		              <div class="btnArea" style="text-align: center;">
										<button onclick="location.href='updateStatus.do?cp=${param.cp}&no=${param.no}'" class="btn btn-primary ml-1 mr-1"
											<c:if test="${lesson.lessonAva eq 'N'.charAt(0)}">disabled</c:if>>신청 종료</button>
										<button onclick="location.href='updateForm.do?cp=${param.cp}&no=${param.no}'" class="btn btn-outline-primary ml-1 mr-1" 
											<c:if test="${lesson.lessonAva eq 'N'.charAt(0)}">disabled</c:if>>수정</button>
		                <button id="deleteBtn" class="btn btn-outline-primary">삭제</button>
		              </div>
	            </li>
            </c:if>
          </ul>
        </div>
      </div>
  </div>
  
	<jsp:include page="/WEB-INF/views/common/footer.jsp"></jsp:include>
	<script>
		$("#deleteBtn").on("click", function() {
			if(confirm("정말 삭제하시겠습니까?"))
				location.href = "delete.do?cp=${param.cp}&no=${lesson.lessonNo}";
		});
		
    $("#likeBtn").on("click", function () {
      $("#heart").toggleClass("like");
    });
    
    <c:forEach var="file" items="${instrfList}">
			$(".profileImg").eq( ${file.fileLevel} ).children("img")
				.attr("src", "${contextPath}/resources/files/${file.fileName}");
		</c:forEach>
  </script>
</body>
</html>