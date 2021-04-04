 <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">
<title>전문가 등록</title>
<style>
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
	
</style>
</head>
<body>
    <jsp:include page="../common/header.jsp"></jsp:include>
   <div class="container">
      
         <div class="col-sm-12">
            <div class="container p-3">
               <form method="POST" id="enrollment" action="enrollment.do" enctype="multipart/form-data" class="form-horizontal" role="form">
                  
                
                  <!-- 이름 -->
                  <div class="row mb-3 form-row">
                     <div class="col-md-3">
                        <h6>이름</h6>
                     </div>
                     <div class="col-md-6">
                        <h5 id="name">${loginMember.memberName}</h5>
                     </div>
                  </div>
   
   
                  <!-- 이메일 -->
                  <hr class="mb-4">
                  <div class="row mb-3 form-row">
                     <div class="col-md-3">
                        <h6>이메일</h6>
                     </div>
                     <div class="col-md-4">
                     	<h5 id="email">${loginMember.memberEmail}</h5>
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
                     </div>
                  </div>
     
   
                  <!-- 프로필 사진 -->
                  <hr class="mb-4">
                  <div class="row mb-3 form-row">
                     <div class="col-md-3">
                        <h6>프로필 사진</h6>
                     </div>
                     <div class="col-md-6">
                     <!-- 프로필 사진 -->
	                <div class="form-inline mb-2">
						<div class="profileImg" id="titleImg">
							<img id="titleImg" width="200" height="200">
						</div>
					</div>

					<!-- 파일 업로드 하는 부분 -->
					<c:choose>
						<c:when test="${!empty fList}">
								<input type="file" id="file0" name="file0" onchange="LoadImg(this,0)" accept=".jpg, .png"> 
							<div id="fileArea">
							</div>
						</c:when>
						
						<c:otherwise>
								<input type="file" id="file0" name="file0" onchange="LoadImg(this,0)" accept=".jpg, .png" required> 
						</c:otherwise>
					</c:choose>
	                     </div>
	                </div>
     
                  
                  <!-- 소개 -->
                  <hr class="mb-4">
                  <div class="row mb-3 form-row">
                     <div class="col-md-3">
                        <label for="introduce">소개</label>
                     </div>
                     <div class="col-md-9">
                     	<textarea class="form-control" rows="7" style="resize:none" aria-label="With textarea" 
                     	id="introduceContent" name="introduceContent" placeholder="소개를 입력해 주세요(250자까지 입력가능)" required>${instr.introduction}</textarea>
                     	<p><span id="counter1">0</span>/250</p>
                     </div>
                  </div>
   
                  <!-- 경력 -->
                  <hr class="mb-4">
                  <div class="row mb-3 form-row">
                     <div class="col-md-3">
                        <label for="career">경력</label>
                     </div>
                     <div class="col-md-9">
                     	<textarea class="form-control" rows="7" style="resize:none" aria-label="With textarea" 
                     	id="careerContent" name="careerContent" placeholder="경력을 입력해 주세요(2000자까지 입력가능)" required>${instr.career}</textarea>
                     	<p><span id="counter2">0</span>/2000</p>
                     </div>
                  </div>
    
                  <!-- 인증 -->
                  <hr class="mb-4">
                  <div class="row mb-3 form-row">
                     <div class="col-md-3">
                        <label for="certification">인증</label>
                     </div>
                     <div class="col-md-9">
                     
                     	<!-- 파일 첨부 -->
                     	<!-- 파일 업로드 하는 부분 -->
						<div class="certificationFileArea">
								<c:choose>
									<c:when test="${!empty fList}">
										<a class="btn" href="${contextPath}/resources/files/" download>저장한 인증 파일 보기</a>
										<input type="file" id="file1" name="file1">
									</c:when>
									
									<c:otherwise>
										<input type="file" id="file1" name="file1" required>
									</c:otherwise>
								</c:choose>
					   </div>
                     </div>
                  </div>
   				<br>
                  	 <div class="text-center">
	                  <button type="reset" class="btn btn-secondary reset">초기화</button>
	                   <button type="button" id="saveBtn" class="btn btn-primary save" formenctype="multipart/form-data"
	                  	onclick="location.href = '${contextPath}/instructor/saveEnrollment.do">저장</button> 
	                  <button type="submit" id="enrollmentBtn" class="btn btn-primary">승인 신청</button>
                     </div>
               </form>
            </div>
          </div>
       </div>
   <br><br>
   
   <jsp:include page="../common/footer.jsp"></jsp:include>
   
   <!-- 회원 관련 Javascript 코드를 모아둘 wsp_member.js 파일을 작성 -->
   <script src="${contextPath}/resources/js/instructor.js"></script>
   
       
   <script>
   	$("#saveBtn").on("click", function(){
   		
   		$("#enrollment").attr("action", "saveEnrollment.do");
   		$("#enrollment").submit();
   	});
   
   
   <%--$("#save").click(function(){
	   var formData = new FormData();
	   var titleImg = $('input[name="titleImg"]');
	   var file1 = $('input[name="file1"]');
	   var categoryCode = $('select[name="categoryCode"');
	   var introduceContent = $('textarea[name="introduceContent"');
	   var careerContent = $('textarea[name="careerContent"');
	   
	   formData.append('titleImmg', titleImg);
	   formData.append('file1', file1);
	   formData.append(categoryCode, "categoryCode");
	   formData.append(introduceContent, "introduceContent");
	   formData.append(careerContent,"careerContent");
	   
	   $.ajax({
	   	contentType: false,
	   	processData : false,
	   	data : formData,
	   	url : "${contextPath}/instructor/saveEnrollment.do",
	   	type : "POST",
	   	seccess : function(result){
	   			
	   	}, error : function(){
	         console.log("전문가 저장 실패");
	      }    
	   });
   });--%>
   
   
	// 프로필 영역에 파일을 첨부 했을 경우 미리 보기가 가능하도록 하는 함수
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
	
   // 초기화 버튼 클릭시 업로드한 이미지 삭제
   $(".reset").on("click", function(){
	   
	   $(".profileImg").children("img").remove();
		
		var img = $("<img>").css("width", "200px").css("height", "200px");
		
		$(".profileImg").append(img);
	});
   
   $(document).ready(function() {
		$("#counter1").text($("#introduceContent").val().length);
	   });
	
	$(document).ready(function() {
		$("#counter2").text($("#careerContent").val().length);
	   });
   
   // 소개 글자수 세기
   $("#introduceContent").on("input", function(){
        $("#counter1").text($(this).val().length);

        // 입력된 글자 수가 150을 넘어서게 되는 경우
        // 글자 수를 빨간색으로 변경
        if($(this).val().length >= 250){
            $("#counter1").css("color", "red");
        }else{
            $("#counter1").removeAttr("style");
        }
    });	

   // 경력 글자수 세기
   $("#careerContent").on("input", function(){
        $("#counter2").text($(this).val().length);

        // 입력된 글자 수가 150을 넘어서게 되는 경우
        // 글자 수를 빨간색으로 변경
        if($(this).val().length >= 250){
            $("#counter2").css("color", "red");
        }else{
            $("#counter2").removeAttr("style");
        }
    });	
   
	// 이미지 배치
	<c:forEach var="file" items="${fList}">
		$(".profileImg").eq( ${file.fileLevel == 1} ).children("img")
			.attr("src", "${contextPath}/resources/files/${file.fileName}");
	</c:forEach>
	
	// 인증 파일 업로드
	<c:forEach var="file" items="${fList}">
		$(".certificationFileArea").eq( ${file.fileLevel == 0} ).children("a")
			.attr("href", "${contextPath}/resources/files/${file.fileName}");
	</c:forEach>
  
	// 카테고리 초기값 지정
	(function(){
		$("#categoryCode > option").each(function(index, item){
			
			if( $(item).val() == "${instr.ctgrCd}" ){
				// 			     속성 추가
				$(item).prop("selected", true);
			}
		});
	})(); 
   </script>

</body>
</html>