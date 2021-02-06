 <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">
<title>전문가 정보조회</title>
<style>
	.badge{
		color : white;
	}

	.box{
		position : relative;
	}
	
	.profileImg{
		margin-left : 20px;
	}
	
	.name{
		margin-left : 10px;
	}
	
	.introduce, .career, .class, .review1{
		font-size : 20px;
	}
	
	.category1 div, .reviewCount div, .reviewScore div{
		font-size : 15px;
	}
	
	.classWrapper{
		width : 100%;
		height : 100%;
	}

   .rWriter {
	  display: inline-block;
	  margin-right: 30px;
	  vertical-align: top;
	}
	
	.rDate {
	  display: inline-block;
	  font-size: 0.5em;
	  color: gray;
	}
	
	.rContent, .replyBtnArea {
	  height: 100%;
	  width: 100%;
	}
	
	.album.py-5 a {
    	text-decoration: none;
			color: black;
    }
    
    .card-text {
    	height: 50px;
    	overflow: hidden;
    }
	
	.report{
	  float : right;
	}
	
	.pagination {
      justify-content: center;
	}
	
</style>
</head>
<body>
   <jsp:include page="../common/header.jsp"></jsp:include>
   <div class="container">
      
         <div class="col-sm-12">
            <div class="container p-3">
                
                  <!-- 프로필 사진 -->
                  <div class="row mb-3 form-row">
                     <div class="col-md-12 box">
                     <!-- 프로필 사진 -->
						<div class="profileImg float-left" id="profileImg">
							<!-- <img id="profileImg" width="150" height="150"> -->
							<img width="200" height="200">
					</div>
					
							<!-- 이름 -->
                  <div class="row mb-3 form-row">
                     <div class="col-md-3 name">
                        <br><h5 id="name">${instr.instrName}</h5>
                     </div>
                  </div>
                  <br>
							<!-- 카테고리 -->
                  <div class="row mb-3">
                     <div class="category1">
                     
                     
                     <c:choose>
                              	<c:when test="${instr.ctgrCd == 10}">
                              		<div class="col md-3">
                              			<div>외국어의 전문가</div>
                              		</div>
                              	</c:when>
                              	<c:when test="${instr.ctgrCd == 20}">
                              		<div class="col md-3">
                              			스포츠의 전문가
                              		</div>
                              	</c:when>
                              	<c:when test="${instr.ctgrCd == 30}">
                              		<div class="col md-3">
                              			사진/영상의 전문가
                              		</div>
                              	</c:when>
                              	<c:when test="${instr.ctgrCd == 40}">
                              		<div class="col md-3">
                              			공예의 전문가
                              		</div>
                              	</c:when>
                              	<c:when test="${instr.ctgrCd == 50}">
                              		<div class="col md-3">
                              			요리의 전문가
                              		</div>
                              	</c:when>
                              	<c:when test="${instr.ctgrCd == 60}">
                              		<div class="col md-3">
                              			미술의 전문가
                              		</div>
                              	</c:when>
                              	<c:when test="${instr.ctgrCd == 70}">
                              		<div class="col md-3">
                              			음악의 전문가
                              		</div>
                              	</c:when>
                              </c:choose>
                     			
                        	
                     </div>
                  </div>
							<!-- 리뷰 개수 -->
                  <div class="row mb-3 reviewCount">
                  </div>
                  
							<!-- 평균 만족도 -->
                  <div class="row mb-3 reviewScore">
                  </div>
                  
                  <button type="submit" class="btn btn-primary float-right message">메세지 보내기</button>
						</div>
						</div>
                  
                  <!-- 소개 -->
                  <hr class="mb-4">
                  <div class="row mb-3 form-row">
                     <div class="col-md-3">
                        <label for="introduce" class="introduce">전문가를<br>소개합니다.</label>
                     </div>
                  </div>
   
   					<div class="row mb-3 form-row">
                     <div class="col-md-12" id="introduceContent">${instr.introduction}</div>
                     </div>
                     
                     
                   <!-- 경력 -->
                  <hr class="mb-4">
                  <div class="row mb-3 form-row">
                     <div class="col-md-3">
                        <label for="career" class="career">전문가의<br>경력 사항입니다.</label>
                     </div>
                  </div>
   
   					<div class="row mb-3 form-row">
                     <div class="col-md-12" id="careerContent">${instr.career}</div>
                     </div>  
                     
                     <!-- 수업 -->
                     <hr class="mb-4">
                  <div class="row mb-3 form-row">
                     <div class="col-md-3">
                        <label for="class" class="class">진행 중인<br>수업입니다.</label>
                     </div>
                  </div>
                  
                  
                  ${pInfo.listCount}개의 수업 
					<div class="album py-5">
				    <div class="container">
				      <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3">
								<c:forEach var="lesson" items="${lessonList}">
									<a href="${contextPath}/lesson/view.do?cp=${pInfo.currentPage}&no=${lesson.lessonNo}${searchStr}">
						        <div class="col">
						          <div class="card shadow-sm">
												<c:forEach var="thumbnail" items="${lList}">
													<c:if test="${lesson.lessonNo == thumbnail.parentInstrNo}">
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
                
                
			<c:url var="pageUrl" value="/instructor/reperence.do"/>
		
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
                  
                     <!-- 리뷰 -->
                  <hr class="mb-4">
                  <div class="row mb-3 form-row">
                     <div class="col-md-3">
                        <label for="review" class="review1">실제 수강생의<br>리뷰입니다.</label>
                     </div>
                  </div>
   
   					<div class="row mb-3 form-row">
                     <div class="col-md-12" id="reviewContent">
                  <jsp:include page="review.jsp"></jsp:include>
                     </div>
                     </div>
            </div>
          </div>
       </div>
   <br><br>
   
   <jsp:include page="../common/footer.jsp"></jsp:include>
      
   <script>
   $(".message").on("click", function() {
	      window.open('${contextPath}/message/messageSend?memberNo=${instr.instr}', "popup", "width=600, height=700, toolbars=no, scrollbars=no, menubar=no left=1000 top=200");
   });
   
	// 각각의 영역에 파일을 첨부 했을 경우 미리 보기가 가능하도록 하는 함수
   function LoadImg(value, num) {
     if (value.files && value.files[0]) {
       var reader = new FileReader();
       // 자바스크립트 FileReader
      	// 웹 애플리케이션이 비동기적으로 데이터를 읽기 위하여 읽을 파일을 가리키는 File 혹은 Blob객체를 이용해 파일의 내용을 읽고 사용자의 컴퓨터에 저장하는 것을 가능하게 해주는 객체
				
       reader.readAsDataURL(value.files[0]);
       // FileReader.readAsDataURL()
     	// 지정된의 내용을 읽기 시작합니다. Blob완료되면 result속성 data:에 파일 데이터를 나타내는 URL이 포함 됩니다.
     	
      	// FileReader.onload
				// load 이벤트의 핸들러. 이 이벤트는 읽기 동작이 성공적으로 완료 되었을 때마다 발생합니다.
       reader.onload = function (e) {
       	//console.log(e.target.result);
       	// e.target.result
       	// -> 파일 읽기 동작을 성공한 객체에(fileTag) 올라간 결과(이미지 또는 파일)
       	
         $(".profileImg").eq(num).children("img").attr("src", e.target.result);
       }
     }
   }
	
	// 이미지 배치
	<c:forEach var="file" items="${fList}">
		$(".profileImg").eq( ${file.fileLevel} ).children("img")
			.attr("src", "${contextPath}/resources/files/${file.fileName}");
	</c:forEach>
   </script>

</body>
</html>