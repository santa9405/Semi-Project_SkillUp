<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="${contextPath}/resources/css/bootstrap.css">
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script type="text/javascript"
	src="${contextPath}/resources/js/bootstrap.js"></script>
<title>쪽지함</title>
<style>
	 
.message {
	border: thin solid lightgray;
	border-radius: 15px;
	padding-bottom: 10px;
	padding-top: 10px;
}

.message-content {
	height: 390px;
	padding: 20px;
}

tbody > tr:nth-child(3) {
	border-bottom: thin solid lightgray;
}
body > div > div > div.message > div.message-title > table > tbody > tr:nth-child(1) > td {
	border-top: 0;
}
body > div > div > div.message > div.message-title > table > tbody > tr > td:nth-child(1) {
	width: 100px;
}
.table {
	margin-bottom: 0;
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
.sendlink:hover {
	cursor: pointer;
}
</style>
</head>
<body>
	<div class="page">
	<div class="container" style="margin-top:8%">
	
		<div class="message">
			<div class="message-title">
				<table class="table">
					<tbody>
					<tr>
						<td>보낸사람</td>
						<c:choose>
							<c:when test="${loginMember.memberNo == message.sendMemberNo}">
								<td>${message.sendMemberName}</td>
							</c:when>
							<c:otherwise>
								<td> <a class="sendlink" onclick="post_to_url('${contextPath}/message/messageSend', {'memberNo':'${message.sendMemberNo}'})">${message.sendMemberName}</a></td>
							</c:otherwise>
						</c:choose>
						
					</tr>
					<tr>
						<td>받은사람</td>
						<c:choose>
							<c:when test="${loginMember.memberNo == message.receiveMemberNo}">
								<td>${message.receiveMemberName}</td>
							</c:when>
							<c:otherwise>
								<td> <a class="sendlink" onclick="post_to_url('${contextPath}/message/messageSend', {'memberNo':'${message.receiveMemberNo}'})">${message.receiveMemberName}</a></td>
							</c:otherwise>
						</c:choose>
					</tr>
					<tr>
						<td>작성일</td>
						<td>${message.recDate}</td>
					</tr>
					</tbody>
				</table>
				
			</div>

			<div class="message-content">
				<p>${message.content}</p>
			</div>
		</div>
		
		<br>
		<div>
			<div class="buttons">
				<button type="button" class="btn btn-outline-dark" id="messageListbtn" style="float: right">목록으로</button>
			</div>
		</div>
	</div>
	</div>
	
	<script>
		$("#messageListbtn").on("click", function() {
			path = "${contextPath}/message/rmessageList"
			location.href = path;
		});
		
		function post_to_url(path, params, method) {
		    method = method || "post";
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
	</script>
</body>
</html>