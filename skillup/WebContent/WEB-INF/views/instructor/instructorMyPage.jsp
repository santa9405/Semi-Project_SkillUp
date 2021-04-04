 <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">
<title>전문가 마이페이지</title>
<style>
	ul{
	    list-style: none;
	    padding-left: 0px;
	}
	.reviewLoad{
	text-align: center;
	}

	.review-row { 
	display:none; 
	}

	#reviewNoArea{
		display:none;
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
	
	.pagination {
   justify-content: center;
   }
   
   .reportCode {
       text-align : left;
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
               <form method="POST" action="updateMyPage.do" enctype="multipart/form-data" class="form-horizontal" role="form">
                  
                  
                  
                <!-- 프로필 사진 -->
                <div class="form-inline mb-2">
					<div class="profileImg" id="titleImgArea">
						<img id="profileImg" width="200" height="200">
					</div>
				</div>


				<!-- 파일 업로드 하는 부분 -->
				<div id="fileArea">
					<input type="file" id="img0" name="img0" onchange="LoadImg(this,0)"> 
				</div>

                  
                  <!-- 강사 번호 -->
                  <hr class="mb-4">
                  <div class="row mb-3 form-row">
                     <div class="col-md-3">
                        <h6>강사 번호</h6>
                     </div>
                     <div class="col-md-6">
                        <h5 id="id">${loginMember.memberNo}</h5>
                     </div>
                  </div>
   
                  <!-- 이름 -->
                  <hr class="mb-4">
                  <div class="row mb-3 form-row">
                     <div class="col-md-3">
                        <h6>이름</h6>
                     </div>
                     <div class="col-md-6">
                        <h5 id="name">${loginMember.memberName}</h5>
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
   
                  <!-- 소개 -->
                  <hr class="mb-4">
                  <div class="row mb-3 form-row">
                     <div class="col-md-3">
                        <label for="introduce">소개</label>
                     </div>
                     <div class="col-md-9">
                     	<textarea class="form-control" rows="7" style="resize:none" aria-label="With textarea" id="introduceContent" 
                     	name="introduceContent" required>${instr.introduction}</textarea>
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
                     	<textarea class="form-control" rows="7" style="resize:none" aria-label="With textarea" id="careerContent" 
                     	name="careerContent" required>${instr.career}</textarea>
                     	<p><span id="counter2">0</span>/2000</p>
                     </div>
                  </div>
   
                  <!-- 리뷰 -->
                  <hr class="mb-4">
                  
                  <div class="row">
                     <div class="col-md-3">
                        <label>리뷰</label>
                     </div>
                     
                     <div class="col-md-9">
                     
                       <!-- 리뷰 출력 부분 -->
                       
					   <div class="reviewList mt-9 pt-2">
					      <ul id="reviewListArea">
					      
					         
					         
					         
					         <li class="review-row">
					            <div>
					               <p class="rScore">만족도 80%</p>
					               <p class="rWriter">작성자</p>
					               <p class="rDate">작성일 : 2021년 01월 11일 10:30</p>
					            </div>
					            
					            <div class="reviewBtnArea">
					               <button type="button" class="btn btn-primary float-right report" data-toggle="modal" data-target="#exampleModal">신고</button>
					            </div>
            
					            <p class="rContent">리뷰 내용</p>
					         </li>
					         <br>
					         
					         
					         
					         
					         
					      </ul>
					      <br>
					     <div class="reviewLoad">
						<a href="#" id="load">리뷰 더보기</a>
						</div>
					   </div>
					      
                        
                     </div>
                  </div>
                  
                  <br>
                  <div class="text-center">
                  <hr class="mb-4">
                  <br>
                  <button class="btn btn-primary" type="submit">정보 수정</button>
                  </div>
               </form>
            </div>
         </div>
      </div>
   </div>
   <br><br>
   
   <%-- 신고 모달창 --%>
	<div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title" id="exampleModalLabel">리뷰 신고하기</h5>
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	          <span aria-hidden="true">&times;</span>
	        </button>
	      </div>
	      <div class="modal-body">
	        <form method="post" action="${contextPath}/review/report.do">
	        
	        <div id="reviewNoArea">
	          </div>
	       
	        <div class="form-group reportCode">
	        		<div>
			        <label for="recipient-name" class="col-form-label">신고 종류</label>
			        </div>
			        <select class="custom-select" id="categoryCode2" name="categoryCode2" style="width: 200px;">
								<option value="10">욕설/비방</option>
								<option value="20">부적절한 리뷰</option>
								<option value="30">기타</option>
					 </select>
	         </div>
	        
	        <hr class="mb-4">
	          <div class="form-group">
	            <label for="recipient-name" class="col-form-label">신고 제목</label>
	            <input type="text" class="form-control" id="recipient-name" name="reportTitle" placeholder="신고 제목을 입력해 주세요." required>
	          </div>
	          
	          <div class="mb-3">
				<label for="message-text" class="col-form-label">신고 내용</label>
				<textarea class="form-control" id="message-text"
					name="reportContent" rows="7" style="resize:none" placeholder="신고 내용을 입력해 주세요." required></textarea>
			  </div>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-secondary" data-dismiss="modal">취소</button>
	        <button type="submit" class="btn btn-primary">신고하기</button>
	      </div>
	        </form>
	    </div>
	  </div>
	</div>
   
   <jsp:include page="../common/footer.jsp"></jsp:include>
   
   <!-- 회원 관련 Javascript 코드를 모아둘 wsp_member.js 파일을 작성 -->
   <script src="${contextPath}/resources/js/wsp_member.js"></script>
   
   <script>
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
   
	// 카테고리 초기값 지정
	(function(){
		$("#categoryCode > option").each(function(index, item){
			
			if( $(item).val() == "${instr.ctgrCd}" ){
				// 			     속성 추가
				$(item).prop("selected", true);
			}
		});
	})();
	
	$(document).ready(function() {
		$("#counter1").text($("#introduceContent").val().length);
	   });
	
	$(document).ready(function() {
		$("#counter2").text($("#careerContent").val().length);
	   });

	// 소개 글자수 세기
	(function(){
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
	})();

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
   
   var loginMemberNo = "${loginMember.memberNo}";
   var instrNo = ${instr.instr};

   //페이지 로딩 완료 시 해당 전문가 리뷰 조회
   $(function(){
	selectReviewList();
});

	//해당 전문가 리뷰 목록 조회 함수(ajax)
	function selectReviewList(){
	   $.ajax({
	      url : "${contextPath}/review/selectList.do",
	      data : {"instrNo" : instrNo },
	      type : "post",
	      dataType : "JSON",
	      success : function(rList){
	         //console.log(rList);
	         
	         $("#reviewListArea").html("");
	         
	         $.each(rList, function(index, item){
	            var li = $("<li>").addClass("review-row");
	            var lTitle = $("<p>").addClass("lTitle").text("수업명 : " + item.status);
	            var rScore = $("<p>").addClass("rScore").text("만족도 : " + item.score +"%");
	            var rWriter = $("<p>").addClass("rWriter").text(item.writerName);
	            var rDate = $("<p>").addClass("rDate").text("작성일 : " + item.reviewDt);
	            var div = $("<div>");
               // ** 추가되는 리뷰에 onclick 이벤트를 부여하여 버튼 클릭 시 수정, 삭제를 수행할 수 있는 함수를 이벤트 핸들러로 추가함. 
               var reportReview = $("<button>").attr("type", "button").addClass("btn btn-primary btn-sm ml-1 float-right report").attr("data-toggle", "modal").attr("data-target", "#exampleModal")
               .attr("onclick", "reportReview("+item.reviewNo+")").text("신고");
               
	            div.append(lTitle).append(rScore).append(rWriter).append(rDate).append(reportReview);
	            
	            var rContent = $("<p>").addClass("rContent").html(item.reviewContent);
	            
	            li.append(div).append(rContent);
	            
               // 신고 버튼 영역
               //var reviewBtnArea = $("<div>").addClass("reviewBtnArea");
               
               
               //reviewBtnArea.append(reportReview);
               
               //li.append(reviewBtnArea);
               
	           $("#reviewListArea").append(li);
	            
	         });
	         
			 if(rList != null){
	         	$(".review-row").slice(0, 5).show();
			 }else{
				$("#load").remove();
			 }  
	         
	      },
	      error : function(){
	         console.log("리뷰 목록 조회 실패");
	      }
	   });
	};
	
	

  $("#load").click(function(e) {
    e.preventDefault();
    $(".review-row:hidden").slice(0, 5).show(); 
	    if ($(".review-row:hidden").length == 0) { 
	    	$("#load").remove();
	    }
  });
	
	// 리뷰 신고 함수
	function reportReview(reviewNo){

		var reviewNo = $("<input>").attr("type", "text").attr("name", "reviewNo")
	   	.attr("value", reviewNo);
	   	
	   	$("#reviewNoArea").append(reviewNo);
	   	
	   };	
   </script>

</body>
</html>