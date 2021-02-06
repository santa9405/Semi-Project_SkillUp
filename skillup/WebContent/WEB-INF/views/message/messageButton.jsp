<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>쪽지</title>
<style>
	#message {
				position: fixed;
				bottom: 100px;
				right: 100px;
	}
</style>
</head>
<body>

	
<c:if test="${!empty loginMember}">
	<button type="button" class="btn" id="message"> <img src="${contextPath}/resources/img/message.png" width="40" height="40"></button>
</c:if>

<script>
	$("#message").on("click", function() {
		window.open("${contextPath}/message/rmessageList", "popup", "width=600, height=700, toolbars=no, scrollbars=no, menubar=no left=1000 top=200");
	});
</script>
</body>
</html>