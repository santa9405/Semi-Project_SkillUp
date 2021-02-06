<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>footer</title>

<style>
.inner-foot{
	margin: 0 auto;
	width: 1100px;
 	padding: 30px 0 20px;
  text-align: center;
}
footer {
	border-top: 1px solid #888;
 	background: white;
}
#home {
	height: 45px;
}
#home-link {
	color: black;
	font-size:25px;
	font-family: 'Jal_Onuel';
}
.link {
	font-size:15px;
	font-family: 'Jal_Onuel';
	padding: 10px
}
#links {
	height: 40px;
}
#menu {
	float:right;
}
#menu-table { 
	text-align: center;
}
td {
	padding: 7px;
	padding-left: 15px;
}
a {
	color: black;
}
a:hover {
	color: black;
	text-decoration: none;
}

/* button */
.btn.btn-primary {
	background: #5f0081;
	border-color: #5f0081;
}
.btn-primary:not(:disabled):not(.disabled):active, 
.btn-primary:not(:disabled):not(.disabled).active, 
.show > .btn-primary.dropdown-toggle {
	background: #5f0081;
	border-color: #5f0081;
}
.btn-primary:not(:disabled):not(.disabled):active:focus, 
.btn-primary:focus {
	box-shadow: 0 0 0 0.2rem #a66edc8c;
}

.btn.btn-outline-primary {
    color: #5f0081;
    border-color: #5f0081;
}

/* badge */
.badge.bg-primary, .badge-primary {
	background: #5f0081 !important;
	padding: 5px;
}

.badge-secondary {
    color: #f8f9fa;
    background-color: #adb5bd;
    padding-top: 8px;
    padding-bottom: 8px;
}

.btn-outline-primary:hover,
.btn-outline-primary:not(:disabled):not(.disabled):active {
	color: #fff;
	background: #5f0081;
	border-color: #5f0081;
}

.btn-outline-primary:focus {
    box-shadow: 0 0 0 0.2rem rgba(200, 123, 255, 0.5);
}

/* alert */
.alert.alert-primary {
	color: #5f0081;
	background-color: #e6dcff;
	border-color: #e6daff;
}

/* list-group */
.list-group-item.list-group-item-primary {
    color: #5f0081;
		background-color: #e6dcff;
}

.list-group-item-primary.list-group-item-action:hover, 
.list-group-item-primary.list-group-item-action:focus {
    color: #5f0081;
    background-color: #e6dcff;
}

/* pagination */
.pagination .page-link {
	 color: #5f0081;
}
/* swal button */
.swal-button,
.swal-button:active {
   background: #5f0081;
   box-shadow: 0 0 0 1px #fff, 0 0 0 3px rgba(143,114,165,.29);
}

.swal-button:not([disabled]):hover {
   background: #5f0081;
   box-shadow: 0 0 0 1px #fff, 0 0 0 3px rgba(143,114,165,.29);
}
</style>
</head>
<body>
<footer>
	<div class="inner-foot">
		<div id="links">
			<a class="link" href="${contextPath}/board/list?boardNo=2">공지사항</a>
			<a class="link" href="#"> 이용약관 </a>
			<a class="link" href="#">정보취급방침</a>
		</div>
		<div>
			Copyright © SkillUp All rights reserved.
		</div>
	</div>
</footer>
</body>
</html>