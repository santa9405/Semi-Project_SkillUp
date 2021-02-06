 <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">
<title>전문가 신청 목록 상세조회 페이지</title>
<style>
   #categoryCode{
   	 display: none;
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
	
	.report{
	  float : right;
	}
	
	.pagination {
   	  justify-content: center;
   }
   
   .reportCode {
       text-align : left;
   }
   
   .certificationFileArea a{
	   padding : 0px;
   }
   
}
</style>
</head>
<body>
   <jsp:include page="../common/header.jsp"></jsp:include>
   <div class="container">
      
      <div class="row my-5">
         <div class="col-sm-12">
            <div class="container p-3">
               <form method="POST" action="approve.do" class="form-horizontal" role="form">
                  
                  
                  
                <!-- 프로필 사진 -->
                <div class="row mb-3 form-row">
                	<div class="col-md-3">
                        <h6>프로필 사진</h6>
                     </div>
					<div class="profileImg" id="titleImgArea">
						<img id="profileImg" width="200" height="200">
					</div>
				</div>


                  <!-- 강사 번호 -->
                  <hr class="mb-4">
                  <div class="row mb-3 form-row">
                     <div class="col-md-3">
                        <h6>전문가 번호</h6>
                     </div>
                     <div class="col-md-6">
                        <p>${instr.instr}</p>
                     </div>
                  </div>
   
                  <!-- 이름 -->
                  <hr class="mb-4">
                  <div class="row mb-3 form-row">
                     <div class="col-md-3">
                        <h6>이름</h6>
                     </div>
                     <div class="col-md-6">
                        <p>${instr.instrName}</p>
                     </div>
                  </div>
   
                  <!-- 카테고리 -->
                  <hr class="mb-4">
                  <div class="row mb-3 form-row">
                     <div class="col-md-3">
                        <h6>카테고리</h6>
                     </div>
                     <div class="col-md-3">
                     <select class="custom-select" id="categoryCode" name="categoryCode" style="width: 150px;">
						<option value="10">외국어</option>
						<option value="20">스포츠</option>
						<option value="30">사진/영상</option>
						<option value="40">공예</option>
						<option value="50">요리</option>
						<option value="60">미술</option>
						<option value="70">음악(악기, 보컬)</option>
					 </select>
					 <p id="ctgrNm"></p>
                     </div>
                  </div>
   
                  <!-- 소개 -->
                  <hr class="mb-4">
                  <div class="row mb-3 form-row">
                     <div class="col-md-3">
                        <label for="introduce">소개</label>
                     </div>
                     <div class="col-md-9">
                     <div id="introduceContent">${instr.introduction}</div>
                     </div>
                  </div>
   
                  <!-- 경력 -->
                  <hr class="mb-4">
                  <div class="row mb-3 form-row">
                     <div class="col-md-3">
                        <label for="career">경력</label>
                     </div>
                     <div class="col-md-9">
                     	<div id="introduceContent">${instr.career}</div>
                     </div>
                  </div>
   
                  <!-- 리뷰 -->
                  <hr class="mb-4">
                  
                  <div class="row">
                     <div class="col-md-3">
                        <label>인증 파일</label>
                     </div>
                     
                     <div class="col-md-9">
					     <div class="certificationFileArea">
								<a class="btn" href="${contextPath}/resources/files/" download>다운로드</a>
					   </div>
                     </div>
                  </div>
                  <br><br>
                  <div class="text-center">
                  
                  <c:choose>
						<c:when test="${!empty param.sk && !empty param.sv1}">
							<c:url var="goToList" value="../search.do">
								<c:param name="cp">${param.cp}</c:param>
								<c:param name="sk">${param.sk}</c:param>
								<c:param name="sv1">${param.sv1}</c:param>
							</c:url>
						</c:when>
						
						<c:otherwise>
							<c:url var="goToList" value="approveList.do">
								<c:param name="cp">${param.cp}</c:param>
							</c:url>
						</c:otherwise>
					</c:choose>
                  
                  <c:if test="${instr.permitFl == 'N'}">
                  	<a href="approve.do?cp=${param.cp}&no=${param.no}" class="btn btn-primary ml-1 mr-1">승인하기</a>
                  </c:if>
                  
                  <a href="${goToList}" class="btn btn-primary">목록으로</a>
                  </div>
               </form>
            </div>
         </div>
      </div>
   </div>
   <br><br>
   
   <%-- <jsp:include page="../common/footer.jsp"></jsp:include> --%>
   
   <jsp:include page="../common/footer.jsp"></jsp:include>
   
   <!-- 회원 관련 Javascript 코드를 모아둘 wsp_member.js 파일을 작성 -->
   <script src="${contextPath}/resources/js/wsp_member.js"></script>
   
   <script>
	// 이미지 배치
	<c:forEach var="file" items="${fList}">
		$(".profileImg").eq( ${file.fileLevel == 1} ).children("img")
			.attr("src", "${contextPath}/resources/files/${file.fileName}");
	</c:forEach>
	
	// 인증 파일 다운로드
	<c:forEach var="file" items="${fList}">
		$(".certificationFileArea").eq( ${file.fileLevel == 0} ).children("a")
			.attr("href", "${contextPath}/resources/files/${file.fileName}");
	</c:forEach>
	
   
	// 카테고리 초기값 지정
	(function(){
		$("#categoryCode > option").each(function(index, item){
			
			// 첫 번째 요소인 운동
			if( $(item).val() == "${instr.ctgrCd}" ){
				// 			     속성 추가
				$(item).prop("selected", true);
			}
		});
	})();
	
	(function(){
		var ctgrNm = $("#categoryCode > option:checked").text();
		document.getElementById('ctgrNm').innerHTML = ctgrNm;
	})();
   </script>

</body>
</html>