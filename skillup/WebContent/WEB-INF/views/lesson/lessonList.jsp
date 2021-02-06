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
    
    td {
        padding: 5px;
    }
    table tr>td:nth-of-type(1) {
        width: 110px;
    }
    #region,
    input[name="instr"], input[name="searchStr"] {
        width: 250px;
    }
    input[type="number"] {
        width: 120px;
        display: inline-block;
    }
    input[type="radio"] {
        display: none;
    }
    #order {
        display: block;
        width: 120px;
    }
    
    #sido1, #gugun1 {
        display: inline-block;
        width: 140px;
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
    
    #filter:hover {
    	cursor: pointer;
    }
	</style>
</head>
<body>
	<jsp:include page="../common/header.jsp"></jsp:include>
	<c:if test="${!empty loginMember}">
		<jsp:include page="/WEB-INF/views/message/messageButton.jsp"></jsp:include>
	</c:if>
	
	<div class="container my-5">
		<!-- 검색창 -->
    <form action="${contextPath}/searchLesson.do" id="searchForm" class="collapse show">
        <table>
            <tr>
                <td><label class="col-sm-12 col-form-label" for="sido1">지역</label></td>
						    <td>
						        <select class="form-control" name="sido1" id="sido1" onchange="fnChange(this)">
						        	<option value="">시/도 선택</option>
						        </select>
						        <select class="form-control" name="gugun1" id="gugun1"></select>
						    </td>
            </tr>
            <tr>
                <td><label class="col-sm-12 col-form-label" for="price1">가격</label></td>
                <td>
                    <input class="form-control" type="number" name="price1" id="price1"> 원 ~
                    <input class="form-control" type="number" name="price2"> 원
                </td>
            </tr>
            <tr>
                <td><label class="col-sm-12 col-form-label" for="sv">검색어</label></td>
                <td>
									<input type="text" name="sv" class="form-control col-sm-8" value="${param.sv}" placeholder="제목, 내용 또는 강사명">
								</td>
            </tr>
            <tr>
                <td><label class="col-sm-12 col-form-label">수업 형태</label></td>
                <td>
                    <div class="btn-group btn-group-toggle" data-toggle="buttons">
                        <label class="btn btn-outline-secondary" for="on">
                            <input type="radio" id="on" value="on" name="onoff">온라인
                        </label>
                        <label class="btn btn-outline-secondary" for="off">
                            <input type="radio" id="off" value="off" name="onoff">오프라인
                        </label>
                    </div>
                    <div class="btn-group btn-group-toggle" data-toggle="buttons">
                        <label class="btn btn-outline-secondary">
                            <input type="radio" id="oneday" value="oneday" name="cnt"> 원데이
                        </label>
                        <label class="btn btn-outline-secondary">
                            <input type="radio" id="multiple" value="multiple" name="cnt"> 다회차
                        </label>
                    </div>
                    <div class="btn-group btn-group-toggle" data-toggle="buttons">
                        <label class="btn btn-outline-secondary" for="one">
                            <input type="radio" id="one" value="one" name="num">1:1
                        </label>
                        <label class="btn btn-outline-secondary" for="group">
                            <input type="radio" id="group" value="group" name="num">그룹
                        </label>
                    </div>
                </td>
            </tr>
            <tr>
                <td><label class="col-sm-12 col-form-label"  for="order">정렬 방식</label></td>
                <td>
                    <select class="form-control" id="order" name="order">
                        <option value="latest">최신순</option>
                        <option value="rank">인기순</option>
                        <option value="like">좋아요순</option>
                    </select>
                </td>
                <td><button type="submit" class="btn btn-primary" style="margin-left:50px;">검색</button></td>
            </tr>
        </table>
    </form>
    
    <p style="text-align: right;">
        <img src="${contextPath}/resources/img/filter.png" id="filter" data-toggle="collapse" data-target="#searchForm" aria-expanded="false" aria-controls="searchForm">
    </p>
    
    ${pInfo.listCount}개의 수업 
		<div class="album py-5">
	    <div class="container">
	      <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3">
					<c:forEach var="lesson" items="${lessonList}">
						<a href="${contextPath}/lesson/view.do?cp=${pInfo.currentPage}&no=${lesson.lessonNo}${searchStr}">
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
		<c:url var="pageUrl" value="/searchLesson.do"/>
		<c:set var="searchStr" value="&ctgrName=${param.ctgrName}&sido1=${param.sido1}&gugun1=${param.gugun1}&price1=${param.price1}&price2=${param.price2}
			&sv=${param.sv}&onoff=${param.onoff}&cnt=${param.cnt}&num=${param.num}&order=${param.order}"/>
		
		<c:set var="firstPage" value="${pageUrl}?cp=1${searchStr}"/>
		<c:set var="lastPage" value="${pageUrl}?cp=${pInfo.maxPage}${searchStr}"/>
		
		<fmt:parseNumber var="c1" value="${(pInfo.currentPage - 1) / pInfo.pageSize}" integerOnly="true"/>
		<fmt:parseNumber var="prev" value="${c1 * pInfo.pageSize}" integerOnly="true"/>
		<c:set var="prevPage" value="${pageUrl}?cp=${prev}${searchStr}"/>
		
		<fmt:parseNumber var="c2" value="${(pInfo.currentPage + 9) / pInfo.pageSize}" integerOnly="true"/>
		<fmt:parseNumber var="next" value="${c2 * pInfo.pageSize + 1}" integerOnly="true"/>
		<c:set var="nextPage" value="${pageUrl}?cp=${next}${searchStr}"/>
		
		<div class="">
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
							<li><a class="page-link" href="${pageUrl}?cp=${page}${searchStr}">${page}</a></li>
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

	<script>
		// 검색창
		(function() {
			$("input[name=price1]").val("${param.price1}");
			$("input[name=price2]").val("${param.price2}");
			$("input[name=sv]").val("${param.sv}");
			
			if(${!empty param.onoff})
				$("input[value="+ "${param.onoff}" +"]").prop("checked", true);
			if(${!empty param.cnt})
				$("input[value="+ "${param.cnt}" +"]").prop("checked", true);
			if(${!empty param.num})
				$("input[value="+ "${param.num}" +"]").prop("checked", true);
			
			$("select[name=order] > option").each(function(index, item) {
				if($(item).val() == "${param.order}")				
					$(item).prop("selected", true);
			});
		})();
	
		//$('document').ready(function () {
		     var area0 = ["서울특별시", "인천광역시", "대전광역시", "광주광역시", "대구광역시", "울산광역시", "부산광역시", "경기도", "강원도", "충청북도", "충청남도", "전라북도", "전라남도", "경상북도", "경상남도", "제주도"];
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
		         $selsido.next().append("<option value=''>구/군 선택</option>");
		     });
		
				/* $("select[name=sido1] > option").each(function(index, item) {
					if($(item).val() == "${param.sido1}")				
						$(item).prop("selected", true);
				});
				
				$("select[name=gugun1] > option").each(function(index, item) {
					if($(item).val() == "${param.gugun1}")				
						$(item).prop("selected", true);
				}); */
				$("select[name^=sido]").val("${param.sido1}").trigger("change");
				
				function fnChange(value) {
					 var area = "area" + $("option", $(value)).index($("option:selected", $(value))); // 선택지역의 구군 Array
					 var $gugun = $(value).next(); // 군구 선택영역 객체
	         $("option", $gugun).remove(); // 구군 초기화
						
	         if (area == "area0")
	             $gugun.append("<option value=''>구/군 선택</option>");
	         else {
	             $.each(eval(area), function () {
	                 $gugun.append("<option>" + this + "</option>");
	             });
	         }
				}
		
		     // 시/도 선택시 구/군 설정
		     $("select[name^=sido]").change(function () {
		         var area = "area" + $("option", $(this)).index($("option:selected", $(this))); // 선택지역의 구군 Array
		         var $gugun = $(this).next(); // 군구 선택영역 객체
		         $("option", $gugun).remove(); // 구군 초기화
		    	 console.log(area)
							
		         if (area == "area0")
		             $gugun.append("<option value=''>구/군 선택</option>");
		         else {
		             $.each(eval(area), function () {
		                 $gugun.append("<option>" + this + "</option>");
		             });
		         }
		     });
		 //});
	</script>
</body>
</html>