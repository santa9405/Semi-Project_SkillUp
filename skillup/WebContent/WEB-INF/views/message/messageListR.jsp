<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="${contextPath}/resources/css/bootstrap.css">
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script type="text/javascript"
	src="${contextPath}/resources/js/bootstrap.js"></script>

<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

<style>
.swal-button,
.swal-button:active {
   background: #5f0081;
   box-shadow: 0 0 0 1px #fff, 0 0 0 3px rgba(143,114,165,.29);
}

.swal-button:not([disabled]):hover {
   background: #5f0081;
   box-shadow: 0 0 0 1px #fff, 0 0 0 3px rgba(143,114,165,.29);
}
.page-link {
	color: black;
}
.buttons > div {
	display: inline;
}
.content:hover {
	cursor: pointer;
}
.membername {
	text-align: center;
}
#membername:hover {
	cursor: pointer;
}
tbody {
	border-bottom: 1px solid lightgray;
}
.nav-item {
	width: 50%;
	height: 100%;
	padding-top: 10px;
	padding-bottom: 10px;
	text-align: center;
	font-size: 25px;
}
p {
	margin: 0;
}

@font-face {
    font-family: 'paybooc-Medium';
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_20-07@1.0/paybooc-Medium.woff') format('woff');
    font-weight: normal;
    font-style: normal;
}

* {
	font-family: 'paybooc-Medium';
}

</style>
</head>
<body>

	<div class="container-fluid">
		<div class="row">
			<div class="col-md-12">
				<div class="tabbable" id="tabs-567607">
					<ul class="nav nav-tabs">
						<li class="nav-item"><p class="nav-link">받은 쪽지함</p></li>
						<li class="nav-item"><a class="nav-link" href="${contextPath}/message/smessageList">보낸 쪽지함</a></li>
					</ul>
					<div>
						<div>
							<table class="table table-hover">
								<thead>
									<tr height="10px">
										<th width="3%">
											<div class="form-check">
												<!-- 체크박스 전체선택 -->
												<input class="form-check-input" type="checkbox" value=""id="checkAll"> <label class="form-check-label" for="defaultCheck1"> </label>
											</div>
										</th>
										<th width="17%" class="membername">보낸사람</th>
										<th>내용</th>
									</tr>
								</thead>
								<tbody>
								<c:forEach var="message" items="${list}">
									<tr height="5px">
										<td width="8%">
											<div class="form-check">
												<input class="form-check-input checkbox" type="checkbox"  name="checkbox" value="${message.messageNo}">
											</div>
										</td>
										
										<c:if test="${message.readStatus=='N'}">
											<td class="membername"><a id="membername" onclick="post_to_url('${contextPath}/message/messageSend', 
											{'memberNo':'${message.sendMemberNo}'})">${message.sendMemberName}</a></td>
											<td class="content" onclick="fnSelect(${message.messageNo})"><a href="#">${message.content}</a></td>
										</c:if>
										<c:if test="${message.readStatus=='Y'}">
											<td class="membername"><a id="membername" style="color:gray" onclick="post_to_url('${contextPath}/message/messageSend', 
											{'memberNo':'${message.sendMemberNo}'})">${message.sendMemberName}</a></td>											
											<td class="content" onclick="fnSelect(${message.messageNo})"><a href="#" style="color:gray">${message.content}</a></td>
										</c:if>
									</tr>
								</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="buttons">
			<div class="float-right">
				<button type="button" class="btn btn-outline-secondary" id="delete">삭제</button>
			</div>
		</div>
		<div>

		<c:url var="pageUrl" value="/message/rmessageList"></c:url>
		<c:set var="firstPage" value="${pageUrl}?page=1"/>
		<c:set var="lastPage" value="${pageUrl}?page=${pInfo.maxPage}"/>
		
		<fmt:parseNumber var="c1" value="${(pInfo.currentPage-1)/10}" integerOnly="true"/>
		<fmt:parseNumber var="prev" value="${c1*10}" integerOnly="true"/>
		<c:set var="prevPage" value="${pageUrl}?page=${prev}"/>
		
		<fmt:parseNumber var="c2" value="${(pInfo.currentPage+9)/10}" integerOnly="true"/>
		<fmt:parseNumber var="next" value="${c2*10+1}" integerOnly="true"/>
		<c:set var="nextPage" value="${pageUrl}?page=${next}"/>
			
	<ul class="pagination justify-content-center">

	<c:if test="${pInfo.currentPage > 10}">
    <li class="page-item">
      <a class="page-link" href="${pageUrl}?page=${pInfo.startPage}">
        <span aria-hidden="true" style="color: black;">&laquo;</span></a>
    </li>
    <li class="page-item">
      <a class="page-link" href="${prevPage}">
        <span aria-hidden="true" style="color: black;">&lt;</span></a>
    </li>
   </c:if>
    
    <c:forEach var="page" begin="${pInfo.startPage}" end="${pInfo.endPage}">
	    <c:choose>
	    	<c:when test="${pInfo.currentPage==page}">
		    	<li class="page-item"><a class="page-link" style="color: black;">${page}</a></li>
	    	</c:when>
	    	<c:otherwise>
	    		<li class="page-item"><a class="page-link" style="color: black;" href="${pageUrl}?page=${page}">${page}</a></li>
	    	</c:otherwise>
	    </c:choose>
    </c:forEach>
    
   <c:if test="${next <= pInfo.maxPage}">
    <li class="page-item">
      <a class="page-link" href="${nextPage}" aria-label="Next">
        <span aria-hidden="true" style="color: black;">&gt;</span></a>
    </li>
    <li class="page-item">
      <a class="page-link" href="${pageUrl}?page=${pInfo.endPage}" aria-label="Next">
        <span aria-hidden="true" style="color: black;">&raquo;</span></a>
    </li>
   </c:if>
    
  </ul>
			
		</div>
	</div>

<script>

function fnSelect(i) {
	path = "${contextPath}/message/messageView?messageNo=" + i;
	location.href = path;
}

function fnSendMsg(no, name) {
	path = "${contextPath}/message/messageSend?memberNo=" + no + "&memberName=" + name;
	location.href = path;
}

function post_to_url(path, params, method) {
    method = method || "post"; // Set method to post by default, if not specified.
    // The rest of this code assumes you are not using a library.
    // It can be made less wordy if you use one.
    var form = document.createElement("form");
    form.setAttribute("method", method);
    form.setAttribute("action", path);
    for(var key in params) {
        var hiddenField = document.createElement("input");
        hiddenField.setAttribute("type", "hidden");
        hiddenField.setAttribute("name", key);
        hiddenField.setAttribute("value", params[key]);
        form.appendChild(hiddenField);
    }
    document.body.appendChild(form);
    form.submit();
}

// 체크박스 전체선택
$("#checkAll").on("change", function() {
	if($(this).prop("checked")){
		$(".checkbox").prop("checked", true);
	}
	else {
		$(".checkbox").prop("checked", false);
	}
});

// 삭제버튼 클릭
$("#delete").on("click", function() {
	var checkedMsg = document.getElementsByName("checkbox");
	var queryString = "";
	var flag = true;
	
	for(var i=0; i<checkedMsg.length; i++) {
		if(checkedMsg[i].checked) {
			flag = false;
		}
	}
	
	if(flag) {
		window.alert("삭제 할 메세지를 선택하세요");
	}
	
	else {
		
		for(var i=0; i<checkedMsg.length; i++) {
			
			if(checkedMsg[i].checked) {
				queryString += checkedMsg[i].value + "-";
			}
		}
		deletemsg(queryString);
	}
});


function deletemsg(queryString) {
	swal("메세지를 삭제하시겠습니까?", {
		buttons: {
			ok: {
				text: "확인",
				value: "ok",
			},
			cancle: "취소"
		},
	})
	.then((value) => {
	switch (value) {
	case "cancle":
		break;
	case "ok":
		swal({
			title: "메세지를 삭제했습니다!",
			icon: "success",
			button: "확인",
		})
		.then(() => {
			location.replace("${contextPath}/message/deleteR?checkedMsgNo=" + queryString);
		})
	}
});
}

</script>
</body>
</html>