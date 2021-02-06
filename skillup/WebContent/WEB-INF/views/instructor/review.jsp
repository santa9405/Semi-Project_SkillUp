<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<style>
.reviewLoad{
	text-align: center;
}

.review-row { 
	display:none; 
}
	
.instrNo{
   display:none
}

/*리뷰*/
.reviewWrite>table {
   width: 90%;
   margin-top: 100px;
}

#reviewContentArea {
   width: 90%;
}

#reviewContentArea > textarea {
   resize: none;
   width: 100%;
}

#reviewBtnArea {
   width: 100px;
   text-align: center;
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

#reviewListArea {
   list-style-type: none;
}

.rContent, .reviewBtnArea {
   height: 100%;
   width: 100%;
}

.reviewBtnArea {
   text-align: right;
}

.reviewUpdateContent {
   resize: none;
   width: 100%;
}

.review-row{
   padding : 15px 0;
}

.modal-body{
	padding : 20px;
}
</style>
<div id="review-area">
  <!-- 리뷰 작성 모달창 -->
  <a class="btn btn-primary float-right review" data-toggle="modal" data-target="#exampleModal">리뷰 쓰기</a>

	<div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title" id="exampleModalLabel">리뷰 작성하기</h5>
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	          <span aria-hidden="true">&times;</span>
	        </button>
	      </div>
	      <div class="modal-body">
	        <form class="form-signin" method="post" action="${contextPath}/review/insert.do">
	        
	          <div class="form-group instrNo">
	            <label for="message-text" class="col-form-label">강사 번호</label>
				  <div class="form-group">
				  <input type="text" class="form-control-range" name="instr" value="${instr.instr}">
				  </div>
	          </div>
		        
	          <div class="form-group">
	            <label for="recipient-name" class="col-form-label">리뷰를 작성할<br>수업을 선택해 주세요.</label><br>
	            <div class="row mb-3 form-row">
	            <select id="lessonTitle" name="subjectNo" style="width: 500px;">
	            </select>
	            </div>
	          </div>
	          <div class="form-group">
	            <label for="message-text" class="col-form-label">만족도<p id='rangeValue'>100%</p></label>
				  <div class="form-group">
				    <input type="range" class="form-control-range" id="formControlRange"
				    	name="score" min="0" max="100" step="10" value="100">
				  </div>
	          </div>
	          
	          <div class="mb-3">
				<label for="message-text" class="col-form-label">후기</label>
				<textarea class="form-control" id="message-text"
					name="reviewContent" rows="7" style="resize:none" placeholder="후기를 입력해 주세요." required></textarea>
			  </div>
			<div class="text-center">
		        <button type="button" class="btn btn-secondary" data-dismiss="modal">취소</button>
		        <button type="submit" class="btn btn-primary">등록하기</button>
			</div>    
			  
	        </form>
	      </div>
	      <div class="modal-footer">
	      </div>
	      
	    </div>
	  </div>
	</div>

   


   <!-- 리뷰 출력 부분 -->
   <div class="reviewList mt-5 pt-2">
      <ul id="reviewListArea">
         
         <!-- 로그인 x 또는 리뷰 작성자가 아닌 회원의 화면 -->
         <li class="review-row">
            <div>
               <p class="rScore">만족도 80%</p>
               <p class="rWriter">작성자</p>
               <p class="rDate">작성일 : 2021년 01월 11일 10:30</p>
            </div>
            
            <p class="rContent">리뷰 내용1</p>
         </li>

         
         <!-- 로그인한 회원이 리뷰 작성자인 경우 -->
         <li class="review-row">
            <div>
               <p class="rScore">만족도 80%</p>
               <p class="rWriter">작성자</p>
               <p class="rDate">작성일 : 2021년 01월 11일 10:30</p>
            </div>

            <p class="rContent">리뷰 내용2</p>
            
            <div class="reviewBtnArea">
               <button class="btn btn-primary btn-sm ml-1"  onclick="showUpdateReview(2, this)">수정</button>
               <button class="btn btn-primary btn-sm ml-1"  onclick="deleteReview(2)">삭제</button>
            </div>
         </li>
   
   		<li class="review=row2">
   		</li>
   
      </ul>
      <br>
      <div class="reviewLoad">
      <a href="#" id="load">리뷰 더보기</a>
      </div>
   </div>
</div>

<script>
document.addEventListener("DOMContentLoaded", function() {
    document.getElementById('formControlRange').onchange = function () {
        document.getElementById('rangeValue').innerText = this.value + "%";
    }
});

var loginMemberNo = "${loginMember.memberNo}";
var instrNo = ${instr.instr};

//페이지 로딩 완료 시 리뷰 작성 대상, 수업명 확인, 해당 전문가 리뷰 조회
$(function(){
	confirm();
	selectReviewList();
	selectReviewCount();
	selectReviewScore();
});

function confirm(){
   $.ajax({
      url : "${contextPath}/review/confirm.do",
      data : {"instrNo" : instrNo },
      type : "post",
      dataType : "JSON",
      success : function(result){
          if(result == 0){ // 리뷰 작성 불가능
        	  //console.log(result);
          
          	 $(".review").hide();
           }
        },
      error : function(){
         console.log("리뷰 작성 대상 확인 실패");
      }
   });
}

$(".review").on("click", function selectLessonTitle(){
	$.ajax({
	      url : "${contextPath}/review/selectLessonTitle.do",
	      data : { "instrNo" : instrNo },
	      type : "post",
	      dataType : "JSON",
	      success : function(data){
	    	  //console.log(data);
	    	  //console.log(data.lessonTitle);
	    	  //console.log(data.subjectNo);
	    	  
	    	  $(data).each(function(i){
	    		 $("#lessonTitle").append("<option value="+data.subjectNo+">"+data.lessonTitle+"</option>");
	    	  });
	    	  
        },
      error : function(){
         console.log("수업명 조회 실패");
      }
   });
});


//해당 전문가 리뷰 목록 조회 함수(ajax)
function selectReviewList(){
   $.ajax({
      url : "${contextPath}/review/selectList.do",
      data : {"instrNo" : instrNo },
      type : "post",
      dataType : "JSON",
      success : function(rList){
         console.log(rList);
         
         $("#reviewListArea").html("");
         
         $.each(rList, function(index, item){
            var li = $("<li>").addClass("review-row");
            var lTitle = $("<p>").addClass("lTitle").text("수업명 : " + item.status);
            var rScore = $("<p>").addClass("rScore").text("만족도 : " + item.score +"%");
            var rWriter = $("<p>").addClass("rWriter").text(item.writerName);
            var rDate = $("<p>").addClass("rDate").text("작성일 : " + item.reviewDt);
            var div = $("<div>");
            div.append(lTitle).append(rScore).append(rWriter).append(rDate);
            
            var rContent = $("<p>").addClass("rContent").html(item.reviewContent);
            
            li.append(div).append(rContent);
            
            // 현재 리뷰의 작성자와 로그인한 멤버의 아이디가 같을 때 버튼 추가
            if(item.reviewWriter == loginMemberNo){
               // 수정, 삭제 버튼 영역
               var reviewBtnArea = $("<div>").addClass("reviewBtnArea");
               
               // ** 추가되는 리뷰에 onclick 이벤트를 부여하여 버튼 클릭 시 수정, 삭제를 수행할 수 있는 함수를 이벤트 핸들러로 추가함. 
               var showUpdate = $("<button>").addClass("btn btn-primary btn-sm ml-1").text("수정").attr("onclick", "showUpdateReview("+item.reviewNo+", this)");
               var deleteReview = $("<button>").addClass("btn btn-primary btn-sm ml-1").text("삭제").attr("onclick", "deleteReview("+item.reviewNo+")");
               
               reviewBtnArea.append(showUpdate).append(deleteReview);
               
               li.append(reviewBtnArea);
               
            }
            
            $("#reviewListArea").append(li);
         });
         
         if(rList.lenght == 0){
			$("#load").remove();
		 }else{
	        $(".review-row").slice(0, 5).show();
		 }
         
      },
      error : function(){
         console.log("리뷰 목록 조회 실패");
      }
   });
}

// 리뷰 더보기
$("#load").click(function(e) {
    e.preventDefault();
    $(".review-row:hidden").slice(0, 5).show(); 
	    if ($(".review-row:hidden").length == 0) { 
	    	$("#load").remove();
    }
});

// 리뷰 개수 count
function selectReviewCount(){
   $.ajax({
      url : "${contextPath}/review/selectReviewCount.do",
      data : {"instrNo" : instrNo },
      type : "post",
      dataType : "JSON",
      success : function(count){
         //console.log(count);
         
            var div = $("<div>").addClass("col-md-3");
            var count = $("<div>").text(count + "개의 평가");
            div.append(count);
            
            $(".reviewCount").append(div);
      },
      error : function(){
         console.log("리뷰 개수 조회 실패");
      }
   });
}

// 수강 평균 만족도
function selectReviewScore(){
   $.ajax({
      url : "${contextPath}/review/selectReviewScore.do",
      data : {"instrNo" : instrNo },
      type : "post",
      dataType : "JSON",
      success : function(score){
         console.log(score);
         
            var div = $("<div>").addClass("col-md-3");
            var score = $("<div>").text("수강 평균 만족도 " + score + "%");
            div.append(score);
            
            $(".reviewScore").append(div);
      },
      error : function(){
         console.log("수강 평균 만족도 조회 실패");
      }
   });
}

//-----------------------------------------------------------------------------------------
var beforeReviewRow;

// 리뷰 수정 폼 출력 함수
function showUpdateReview(reviewNo, el){
   
   //console.log($(".reviewUpdateContent").length);
   
   // 이미 열려있는 리뷰 수정 창이 있을 경우 닫아주기
   if($(".reviewUpdateContent").length > 0){
      $(".reviewUpdateContent").eq(0).parent().html(beforeReviewRow);
   }
      
   
   // 댓글 수정화면 출력 전 요소를 저장해둠.
   beforeReviewRow = $(el).parent().parent().html();
   
   
   // 작성되어있던 내용(수정 전 댓글 내용) 
   var beforeContent = $(el).parent().prev().html();
   
   
   // 이전 댓글 내용의 크로스사이트 스크립트 처리 해제, 개행문자 변경
   // -> 자바스크립트에는 replaceAll() 메소드가 없으므로 정규 표현식을 이용하여 변경
   beforeContent = beforeContent.replace(/&amp;/g, "&");   
   beforeContent = beforeContent.replace(/&lt;/g, "<");   
   beforeContent = beforeContent.replace(/&gt;/g, ">");   
   beforeContent = beforeContent.replace(/&quot;/g, "\"");   
   
   beforeContent = beforeContent.replace(/<br>/g, "\n");   
   
   // 기존 리뷰 영역을 삭제하고 textarea를 추가 
   $(el).parent().prev().remove();
   var textarea = $("<textarea>").addClass("reviewUpdateContent").attr("rows", "3").val(beforeContent);
   $(el).parent().before(textarea);
   
   //console.log(reviewBtnArea);
   
   
   // 수정 버튼
   var updateReview = $("<button>").addClass("btn btn-primary btn-sm ml-1 mb-4").text("리뷰 수정").attr("onclick", "updateReview(" + reviewNo + ", this)");
   
   // 취소 버튼
   var cancelBtn = $("<button>").addClass("btn btn-primary btn-sm ml-1 mb-4").text("취소").attr("onclick", "updateCancel(this)");
   
   var reviewBtnArea = $(el).parent();
   
   $(reviewBtnArea).empty(); 
   $(reviewBtnArea).append(updateReview); 
   $(reviewBtnArea).append(cancelBtn); 
}

//-----------------------------------------------------------------------------------------


// 리뷰 수정 함수
function updateReview(reviewNo, el){
   
   // 수정된 리뷰 내용
   var reviewContent = $(el).parent().prev().val();
   
   $.ajax({
       url : "${contextPath}/review/updateReview.do",
      type : "post",
      data : {"reviewNo" : reviewNo, "reviewContent" : reviewContent},
      success : function(result){
         if(result > 0){
            
            selectReviewList(instrNo);
            
            swal({"icon" : "success" , "title" : "리뷰 수정 성공"});
         }
         
      }, error : function(){
         console.log("리뷰 수정 실패");
      }      
   });
}

//-----------------------------------------------------------------------------------------

// 댓글 수정 취소 시 원래대로 돌아가는 함수
function updateCancel(el){
   $(el).parent().parent().html(beforeReviewRow);
}

//-----------------------------------------------------------------------------------------

//리뷰 삭제 함수
function deleteReview(reviewNo){
   $.ajax({
      url : "${contextPath}/review/deleteReview.do",
      data : {"reviewNo" : reviewNo},
      success : function(result){
         if(result > 0){
         	
            selectReviewList(instrNo);
            
            swal({"icon" : "success" , "title" : "리뷰 삭제 성공"});
         }
         
      }, error : function(){
         console.log("리뷰 삭제 실패");
      }
   });
}

</script>