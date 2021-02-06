<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<!DOCTYPE html>
<html>
<head>

<!-- Bootstrap core CSS -->
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">

<!-- Bootstrap core JS -->
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ho+j7jyWK8fNQe+A12Hb8AhRq26LrZ/JpcUGGOn+Y7RsweNrtN/tE3MoK7ZeZDyx" crossorigin="anonymous"></script>

<meta charset="UTF-8">
<title>전문가 승인</title>
<style>
.pagination {
   justify-content: center;
}

#searchForm {
   position: relative;
}

#searchForm>* {
   top: 0;
}

#list-table th {
   text-align: center;
}

#list-table td:not(:nth-of-type(3)) {
   text-align: center;
}

#list-table td{
	text-align: center;
}

.list-wrapper{
   min-height: 540px;
}

#list-table td:hover{
   cursor : pointer;
}

/* 세로 가운데 정렬*/
#list-table td{
  vertical-align: middle;
  /* vertical-align : inline, inline-block 요소에만 적용 가능(td는 inline-block)*/
	
}

</style>

</head>
<body>
   <jsp:include page="../common/header.jsp"></jsp:include>
   <div class="container my-5">
      
         <div class="list-wrapper">
            <table class="table table-hover table-striped my-5" id="list-table">
               <thead>
                  <tr>
                     <th>신청 번호</th>
                     <th>전문가 번호</th>
                     <th>이름</th>
                     <th>카테고리</th>
                     <th>승인 여부</th>
                  </tr>
               </thead>
               
               <tbody>
               
               </tbody>
               
               <%-- 게시글 목록 출력 --%>
               <tbody>
                  <c:choose>
                     <c:when test="${empty iList}">
                        <tr>
                           <td colspan="6">승인을 신청한 전문가가 없습니다.</td>
                        </tr>
                     </c:when>
                     
                     <c:otherwise> <%-- 조회된 게시글 목록이 있을 때 --%>
                        <c:forEach var="instr" items="${iList}">
                           <tr>
                           	  <%-- 강사 번호 --%>
                              <td>${instr.enrollmentNo}</td>
                           	  <%-- 강사 번호 --%>
                              <td>${instr.instr}</td>
                              <%-- 이름 --%>
                              <td>${instr.instrName}</td>
                              
                              <%-- 카테고리 --%>
                              <td>
                              
                              <c:choose>
                              	<c:when test="${instr.ctgrCd == 10}">
                              		외국어
                              	</c:when>
                              	<c:when test="${instr.ctgrCd == 20}">
                              		스포츠
                              	</c:when>
                              	<c:when test="${instr.ctgrCd == 30}">
                              		사진/영상
                              	</c:when>
                              	<c:when test="${instr.ctgrCd == 40}">
                              		공예
                              	</c:when>
                              	<c:when test="${instr.ctgrCd == 50}">
                              		요리
                              	</c:when>
                              	<c:when test="${instr.ctgrCd == 60}">
                              		미술
                              	</c:when>
                              	<c:when test="${instr.ctgrCd == 70}">
                              		음악
                              	</c:when>
                              </c:choose>
                              
                              </td>
                              
                              <%-- 승인 여부 --%>
                              <td>${instr.permitFl}</td>
                           </tr>
                        </c:forEach>
                     </c:otherwise>
                  </c:choose>
               </tbody>
            </table>
         </div>

         <%---------------------- Pagination ----------------------%>
         <%-- 페이징 처리 주소를 쉽게 사용할 수 있도록 미리 변수에 저장 --%>
         <c:choose>
            <%-- 검색 내용이 파라미터에 존재할 때 == 검색을 통해 만들어진 페이지인가? --%>
            <c:when test="${!empty param.sk && !empty param.sv1}">
               <c:url var="pageUrl" value="/search.do"/>
               
               <!-- 쿼리스트링으로 사용할 내용을 변수에 저장 -->
               <c:set var="searchStr" value="&sk=${param.sk}&sv1=${param.sv1}"/>
            </c:when>
            
            <c:otherwise>
              <c:url var="pageUrl" value="/instructor/approveList.do"/>
            </c:otherwise>
            
         </c:choose>
         
         
         
         
         
         <!-- 화살표에 들어갈 주소를 변수로 생성 -->
         <%-- 
            검색을 안했을 때 : /board/list.do?cp=1
            검색을 했을 때 :  /search.do?cp=1&sk=title&sv=49
          --%>
         
         <c:set var="firstPage" value="${pageUrl}?cp=1${searchStr}"/>
         <c:set var="lastPage" value="${pageUrl}?cp=${pInfo.maxPage}${searchStr}"/>
         
         <%-- EL을 이용한 숫자 연산의 단점 : 연산이 자료형에 영향을 받지 않는다. --%>
         <%--
          <fmt : parseNumber> : 숫자 형태를 지정하여 변수 선언 
          integerOnly="true" : 정수로만 숫자 표현(소수점 버림)
         --%>
         
         <fmt:parseNumber var="c1" value="${(pInfo.currentPage - 1) / 10}" integerOnly="true"/>
         <fmt:parseNumber var="prev" value="${c1 * 10}" integerOnly="true"/>
         <c:set var="prevPage" value="${pageUrl}?cp=${prev}${searchStr}" />
         
         <fmt:parseNumber var="c2" value="${(pInfo.currentPage + 9) / 10 }" integerOnly="true" />
         <fmt:parseNumber var="next" value="${ c2 * 10 + 1}" integerOnly="true" />
         <c:set var="nextPage" value="${pageUrl}?cp=${next}${searchStr}" />
         
         
         
         <div class="my-5">
            <ul class="pagination">
               <%-- 현재 페이지가 10페이지 초과인 경우 --%>
               <c:if test="${pInfo.currentPage > 10}">
                  <li> <%-- 첫 페이지로 이동(<<) --%>
                     <a class="page-link" href="${firstPage}">&lt;&lt;</a>
                  </li>
                  <li> <%-- 이전 페이지로 이동(<) --%>
                     <a class="page-link" href="${prevPage}">&lt;</a>
                  </li>
               </c:if>
               
               <!--  페이지 목록   -->
               <c:forEach var="page" begin="${pInfo.startPage}" end="${pInfo.endPage}">
                  <c:choose>
                     <c:when test="${pInfo.currentPage == page}">
                        <li>
                           <a class="page-link">${page}</a>
                        </li>
                     </c:when>
                     
                     <c:otherwise>
                        <li>
                           <a class="page-link" href="${pageUrl}?cp=${page}${searchStr}">${page}</a>
                        </li>
                     </c:otherwise>
                  </c:choose>
               </c:forEach>
               

            <%-- 다음 페이지가 마지막 페이지 이하인 경우 --%>
            <c:if test="${ next <= pInfo.maxPage}">
               <li>
                  <%-- 다음 페이지로 이동(>) --%> 
                  <a class="page-link" href="${nextPage}">&gt;</a>
               </li>
               <li>
                  <%-- 마지막 페이지로 이동(>>) --%> 
                  <a class="page-link" href="${lastPage}">&gt;&gt;</a>
               </li>
            </c:if>

         </ul>
         </div>
      
      
         <!-- 검색창 -->
         <div class="my-5">
            <form action="${contextPath}/search.do" method="GET" class="text-center" id="searchForm">
               <select name="sk" class="form-control" style="width: 130px; display: inline-block;">
                  <option value="instr">전문가 번호</option>
                  <option value="instrName">이름</option>
                  <option value="permitFl">승인 여부</option>
               </select>
               <input type="text" name="sv1" onkeyup="Upper(event, this)" class="form-control" style="width: 25%; display: inline-block;">
               <button class="form-control btn btn-primary" style="width: 100px; display: inline-block;">검색</button>
            </form>

         </div>
   </div>
   <jsp:include page="../common/footer.jsp"></jsp:include>


   <script>
   function Upper(e,r){
	   r.value = r.value.toUpperCase();
   }
  
  
     // 전문가 승인 신청회원 상세보기 기능 (jquery를 통해 작업)
     $("#list-table td").on("click",function(){
        
        // 전문가 번호 얻어오기
        var instrNo = $(this).parent().children().eq(1).text();
        //console.log(instrNo);
        // 클릭이 되는지 테스트
        
        var url = "${contextPath}/instructor/approveView.do?cp=${pInfo.currentPage}&no=" + instrNo + "${searchStr}";
        
        location.href = url;
        
     });
     
     
     // 검색 내용이 있을 경우 검색창에 해당 내용을 작성해두는 기능
     (function(){
   	  var searchKey = "${param.sk}";
   	  // 파라미터 중 sk가 있을 경우 ex)"검색값"
   	  // 파라미터 중 sk가 없을 경우 ex) ""
   	  var searchValue = "${param.sv1}";
   	  
   	  // 검색창 select의 option을 반복 접근
   	  $("select[name=sk] > option").each(function(index, item){
   		  // index : 현재 접근 중인 요소의 인덱스
   		  // item : 현재 접근 중인 요소
   		  
   		  		// title          title
   		  if( $(item).val() == searchKey ){ // 제목으로 검색한 경우
   			 $(item).prop("selected", true);
   		  }
   	  });
   	  
   	  // 검색어 입력창에 searchValue 값 출력
   	  $("input[name=sv1]").val(searchValue);
   	  
     })();
   </script>
</body>
</html>