<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>SKILL UP</title>
<style>
	.col-form-label {
	    min-width: 180px;
	}
	.div.small {
		width: 200px;
	}
	
	#imgArea {
	    padding-top: 15px;
	    padding-left: 0;
	}
	
	.lessonImg {
		width: 181px;
		height: 130px;
	}
</style>
</head>
<body>
	<jsp:include page="../common/header.jsp"></jsp:include>

	<div class="container my-5" style="padding-left: 40px; padding-right: 40px;">

		<form action="${contextPath}/lesson/update.do?no=${lesson.lessonNo}" method="post" enctype="multipart/form-data" role="form" onsubmit="return lessonValidate();">
        <div class="form-group row">
            <label for="lessonTitle" class="col-sm-2 col-form-label">수업명</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="lessonTitle" name="lessonTitle" value="${lesson.lessonTitle}" required>
            </div>
        </div>
        <hr>
        <div class="form-group row">
            <label for="categoryCode" class="col-sm-2 col-form-label">수업 카테고리</label>
            <div class="col-sm-10">
                <select id="categoryCode" name="categoryCode" class="form-control div small" required>
                    <option value="10" selected>외국어</option>
                    <option value="20">스포츠</option>
                    <option value="30">사진/영상</option>
                    <option value="40">공예</option>
                    <option value="50">요리</option>
                    <option value="60">미술</option>
                    <option value="70">음악</option>
                </select>
            </div>
        </div>
        <hr>
        <div class="form-group row">
            <label for="lessonCnt" class="col-sm-2 col-form-label">수업 횟수</label>
            <div class="col-sm-10">
                <input type="number" class="form-control div small" id="lessonCnt" name="lessonCnt" min="1" value="${lesson.lessonCnt}" required>
            </div>
        </div>
        <hr>
        <div class="form-group row">
            <label for="lessonTime" class="col-sm-2 col-form-label">1회 당 수업 시간(분)</label>
            <div class="col-sm-10">
                <input type="number" class="form-control div small" id="lessonTime" name="lessonTime" min="1" value="${lesson.lessonTime}" required>
            </div>
        </div>
        <hr>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">수업 인원</label>
            <div class="col-sm-10">
                <div class="row mb-3 form-row">
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="numOp" id="oneToOne" value="oneToOne">
                        <label class="form-check-label" for="oneToOne">1:1</label>
                    </div>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="numOp" id="group" value="group">
                        <label class="form-check-label" for="group">그룹</label>
                    </div>
                </div>
                <input type="number" class="form-control div small" id="maxNum" name="maxNum" min="1" value="${lesson.maxNum}" required>
            </div>
        </div>
        <hr>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">수업 장소</label>
            <div class="col-sm-10">
                <div class="row mb-3 form-row">
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="placeOp" id="N" value="N">
                        <label class="form-check-label" for="N">온라인</label>
                    </div>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="placeOp" id="F" value="F">
                        <label class="form-check-label" for="F">오프라인</label>
                    </div>
                </div>
                <div class="row mb-3 form-row">
                	<div class="col-md-3">
	                	<select class="form-control" name="sido1" id="sido1" required></select>
                	</div>
                	<div class="col-md-3">
						        <select class="form-control" name="gugun1" id="gugun1" required></select>
                	</div>
                </div>
                <div class="row mb-9 form-row">
						        <input type="text" class="form-control" id="place3" name="place3">
                </div>
            </div>
        </div>
        <hr>
        
        <div class="form-group row">
            <label for="lessonDate" class="col-sm-2 col-form-label">수업 시작일</label>
            <div class="col-sm-10">
                <input type="date" class="form-control div small" id="lessonDate" name="lessonDate" value="${lesson.lessonDate}" required>
            </div>
        </div>
        <hr>
        <div class="form-group row">
            <label for="price" class="col-sm-2 col-form-label">가격</label>
            <div class="col-sm-10">
                <input type="number" class="form-control div small" id="price" name="price" min="1" value="${lesson.price}" required>
            </div>
        </div>
        <hr>
        <div class="form-group row">
            <label for="addImg" class="col-sm-2 col-form-label">이미지</label>
            <div class="col-sm-10">
                <button type="button" class="btn btn-primary" id="addImg">이미지 등록</button>
                <div class="col-sm-12" id="imgArea">
	                <c:forEach var="file" items="${fList}">
										<img src="${contextPath}/resources/uploadImages/lesson/${file.fileName}" class="lessonImg" onclick="deleteImg(this)"  id="${file.fileNo}">
									</c:forEach>
                </div>
            </div>
        </div>
        <hr>
        <div class="form-group row">
            <label for="lessonContent" class="col-sm-2 col-form-label">수업 내용</label>
            <div class="col-sm-10">
                <textarea class="form-control" id="lessonContent" name="lessonContent" rows="10" style="resize: none;" required>${lesson.lessonContent}</textarea>
            </div>
        </div>
        <div class="form-group row">
            <div class="col-sm-12" style="text-align: center;margin-top:30px;">
                <button type="reset" class="btn btn-outline-primary">초기화</button>
                <button type="submit" class="btn btn-primary">수정하기</button>
            </div>
        </div>
    </form>
	</div>

	<jsp:include page="../common/footer.jsp"></jsp:include>
		
	<script>
	   function lessonValidate() {
				// 시작일 
				var date = new Date();
			 
		    var year = date.getFullYear();
		    var month = date.getMonth()+1;
		    var day = date.getDate(); 
		 
		    if ((month+"").length < 2) month = "0" + month;
		    if ((day+"").length < 2) day = "0" + day;
	
		    var getToday = year+"-"+month+"-"+day;
	
		    if(getToday > $("#lessonDate").val()) {
	   	   	swal("수업 시작날짜는 오늘 이후만 가능합니다");
	        $("#lessonDate").focus();
	        $("#imgArea>img").length	
				  return false;
		    }
		    
		    // 이미지 등록
				if ($("#imgArea>img").length < 1) {
				 swal("이미지를 등록해주세요");
				  $("#addImg").focus();
				  return false;
				}
	   }
	
	   // 이미지
	   var i = $("#imgArea>img").length;
		$("#addImg").on("click", function () {
		  var inputFile = $("<input>").attr("type", "file").attr("name", "img" + i++).attr("onchange", "LoadImg(this)").hide();
		  $("#imgArea").append(inputFile);
		  $("input[type=file]").last().click();
		});
	
   function LoadImg(value) {
     if (value.files && value.files[0]) {
         var reader = new FileReader();
         reader.readAsDataURL(value.files[0]);
         reader.onload = function (e) {
             var img = $("<img>").attr("src", e.target.result).addClass("lessonImg").attr("onclick", "deleteImg(this)");
             $("#imgArea").append(img);
         }
     }
   }
	
   // 이미지 삭제
   function deleteImg(value) {
	   $(value).prev("input").remove();
	   var deletedImg = $(value).remove().get(0);
	   
	   var src = $(deletedImg).attr("id");
	   var hidden = $("<input>").attr("type", "hidden").attr("name", "deletedImges").val(src);
	   $("#imgArea").append(hidden);
   }
   
	   // 장소
	   $("input:radio[name=placeOp]").click(function () {
	       if ($("input:radio[name=placeOp]:checked").val() == "N") {
	           $("#sido1").prop("disabled", true);
	           $("#gugun1").prop("disabled", true);
	           $("#place3").prop("disabled", true);
	       } else {
	           $("#sido1").prop("disabled", false);
	           $("#gugun1").prop("disabled", false);
	           $("#place3").prop("disabled", false);
	       }
	   });
	
	   $('document').ready(function () {
	     var area0 = ["시/도 선택", "서울특별시", "인천광역시", "대전광역시", "광주광역시", "대구광역시", "울산광역시", "부산광역시", "경기도", "강원도", "충청북도", "충청남도", "전라북도", "전라남도", "경상북도", "경상남도", "제주도"];
	     var area1 = ["강남구", "강동구", "강북구", "강서구", "관악구", "광진구", "구로구", "금천구", "노원구", "도봉구", "동대문구", "동작구", "마포구", "서대문구", "서초구", "성동구", "성북구", "송파구", "양천구", "영등포구", "용산구", "은평구", "종로구", "중구", "중랑구"];
	     var area2 = ["계양구", "남구", "남동구", "동구", "부평구", "서구", "연수구", "중구", "강화군", "옹진군"];
	     var area3 = ["대덕구", "동구", "서구", "유성구", "중구"];
	     var area4 = ["광산구", "남구", "동구", "북구", "서구"];
	     var area5 = ["남구", "달서구", "동구", "북구", "서구", "수성구", "중구", "달성군"];
	     var area6 = ["남구", "동구", "북구", "중구", "울주군"];
	     var area7 = ["강서구", "금정구", "남구", "동구", "동래구", "부산진구", "북구", "사상구", "사하구", "서구", "수영구", "연제구", "영도구", "중구", "해운대구", "기장군"];
	     var area8 = ["고양시", "과천시", "광명시", "광주시", "구리시", "군포시", "김포시", "남양주시", "동두천시", "부천시", "성남시", "수원시", "시흥시", "안산시", "안성시", "안양시", "양주시", "오산시", "용인시", "의왕시", "의정부시", "이천시", "파주시", "평택시", "포천시", "하남시", "화성시", "가평군", "양평군", "여주군", "연천군"];
	     var area9 = ["강릉시", "동해시", "삼척시", "속초시", "원주시", "춘천시", "태백시", "고성군", "양구군", "양양군", "영월군", "인제군", "정선군", "철원군", "평창군", "홍천군", "화천군", "횡성군"];
	     var area10 = ["제천시", "청주시", "충주시", "괴산군", "단양군", "보은군", "영동군", "옥천군", "음성군", "증평군", "진천군", "청원군"];
	     var area11 = ["계룡시", "공주시", "논산시", "보령시", "서산시", "아산시", "천안시", "금산군", "당진군", "부여군", "서천군", "연기군", "예산군", "청양군", "태안군", "홍성군"];
	     var area12 = ["군산시", "김제시", "남원시", "익산시", "전주시", "정읍시", "고창군", "무주군", "부안군", "순창군", "완주군", "임실군", "장수군", "진안군"];
	     var area13 = ["광양시", "나주시", "목포시", "순천시", "여수시", "강진군", "고흥군", "곡성군", "구례군", "담양군", "무안군", "보성군", "신안군", "영광군", "영암군", "완도군", "장성군", "장흥군", "진도군", "함평군", "해남군", "화순군"];
	     var area14 = ["경산시", "경주시", "구미시", "김천시", "문경시", "상주시", "안동시", "영주시", "영천시", "포항시", "고령군", "군위군", "봉화군", "성주군", "영덕군", "영양군", "예천군", "울릉군", "울진군", "의성군", "청도군", "청송군", "칠곡군"];
	     var area15 = ["거제시", "김해시", "마산시", "밀양시", "사천시", "양산시", "진주시", "진해시", "창원시", "통영시", "거창군", "고성군", "남해군", "산청군", "의령군", "창녕군", "하동군", "함안군", "함양군", "합천군"];
	     var area16 = ["서귀포시", "제주시", "남제주군", "북제주군"];
	
	     // 시/도 선택 박스 초기화
	     $("select[name^=sido]").each(function () {
	         $selsido = $(this);
	         $.each(eval(area0), function () {
	             $selsido.append("<option>" + this + "</option>");
	         });
	         $selsido.parent().next().children().append("<option>구/군 선택</option>");
	     });
	
	     // 시/도 선택시 구/군 설정
	     $("select[name^=sido]").change(function () {
	    	 console.log($(this))
	         var area = "area" + $("option", $(this)).index($("option:selected", $(this))); // 선택지역의 구군 Array
	         var $gugun = $(this).parent().next().children(); // 군구 선택영역 객체
	         $("option", $gugun).remove(); // 구군 초기화
	
	         if (area == "area0")
	             $gugun.append("<option>구/군 선택</option>");
	         else {
	             $.each(eval(area), function () {
	                 $gugun.append("<option>" + this + "</option>");
	             });
	         }
	     });
	     
	     // 장소 설정
	     if ("${lesson.place}" == "") {
	    	 $("#N").prop("checked", true);
				
	    	 $("#sido1").prop("disabled", true);
         $("#gugun1").prop("disabled", true);
         $("#place3").prop("disabled", true);
	     }
	     else 
	    	 $("#F").prop("checked", true);
	     
	     var place = "${lesson.place}".split(" ");
			
			$("#sido1 > option").each(function(index, item) {
				if($(item).text() == place[0])
					$(item).prop("selected", true);
			});
			
			var area = "area" + $("option", $("select[name^=sido]")).index($("option:selected", $("select[name^=sido]"))); // 선택지역의 구군 Array
      var $gugun = $("select[name^=sido]").parent().next().children(); // 군구 선택영역 객체
      $("option", $gugun).remove(); // 구군 초기화
      if (area == "area0")
          $gugun.append("<option>구/군 선택</option>");
      else {
          $.each(eval(area), function () {
              $gugun.append("<option>" + this + "</option>");
          });
      }
	         
			$("#gugun1 > option").each(function(index, item) {
				if($(item).text() == place[1])
					$(item).prop("selected", true);
			});
	 	});
	
	  // 인원
	  $("input:radio[name=numOp]").click(function () {
	      if ($("input:radio[name=numOp]:checked").val() == "oneToOne")
	          $("#maxNum").val("1").prop("disabled", true);
	      else
	          $("#maxNum").prop("disabled", false);
	  });
	  
		(function() {
			// 카테고리
			$("#categoryCode > option").each(function(index, item) {
				if($(item).text() == "${lesson.categoryName}")
					$(item).prop("selected", true);
			});
			
			// 인원
			if(${lesson.maxNum} > 1)
				$("#group").prop("checked", true);
			else {
				$("#oneToOne").prop("checked", true);
				$("#maxNum").prop("disabled", true);
			}
				
			// 장소
			if("${lesson.lessonType}" == "F")
				$("#F").prop("checked", true);
			else 
				$("#N").prop("checked", true);
			
			var first = "${lesson.place}".indexOf(" ");
			var index = "${lesson.place}".indexOf(" ", first+1);
			var place3 = "${lesson.place}".substring(index);
			$("#place3").val(place3);
		})();
	</script>
	<script src="//d1p7wdleee1q2z.cloudfront.net/post/search.min.js"></script>
</body>
</html>