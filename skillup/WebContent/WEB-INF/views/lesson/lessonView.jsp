<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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

	.payArea img, .payArea h6 {
		cursor:pointer;
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
    width: 60px;
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
        	<c:if test="${lesson.lessonAva eq 'N'.charAt(0)}">
	        	<div class="alert alert-primary" role="alert">신청 종료된 수업입니다.</div>
        	</c:if>
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
            <li class="list-group-item justify-content-between">
              <div class="btnArea" style="text-align: center;">
                <button type="button" id="likeBtn">
                  <img src="${contextPath}/resources/img/like1.png" id="heart" class='<c:if test="${likes > 0}">like</c:if>'> <span class="likeCnt">${lesson.likesCnt}</span>
                </button>&nbsp;
                <button type="button" class="btn btn-primary" onclick="location.href = '../pay/myorder.do?no=${param.no}'">결제하기</button>
              </div>
            </li>
          </ul>
        </div>
      </div>
  </div>
  
	<jsp:include page="/WEB-INF/views/common/footer.jsp"></jsp:include>
	<script>
    $("#likeBtn").on("click", function () {
    	
    	if("${loginMember}" == "")
    		swal({"title": "로그인 후 이용해주세요", "icon": "warning"});
    	else {
	      if($("#heart").attr("class") == "like") {
	        $.ajax({
				    url: "${contextPath}/likes/delete.do",
				    type: "POST",
				    data: { "lessonNo": "${lesson.lessonNo}",
				    				"memberNo": "${loginMember.memberNo}"},
				    success: function(result) {
				    	if(result > 0) {
				    		$(".likeCnt").text(Number($(".likeCnt").text()) - 1);
					      $("#heart").toggleClass("like");
				    	}
				    },
				    error: function() {
				        console.log("좋아요 취소 실패");
				    }
				  });
	      } else {
	        $.ajax({
				    url: "${contextPath}/likes/insert.do",
				    type: "POST",
				    data: { "lessonNo": "${lesson.lessonNo}",
				    				"memberNo": "${loginMember.memberNo}"},
				    success: function(result) {
				    	if(result > 0) {
				    		$(".likeCnt").text(Number($(".likeCnt").text()) + 1);
					      $("#heart").toggleClass("like");
				    	}
				    },
				    error: function() {
				        console.log("좋아요 등록 실패");
				    }
				  });
	      }
    	}
    });
    
    <c:forEach var="file" items="${instrfList}">
			$(".profileImg").eq( ${file.fileLevel} ).children("img")
				.attr("src", "${contextPath}/resources/files/${file.fileName}");
		</c:forEach>
  </script>
</body>
</html>