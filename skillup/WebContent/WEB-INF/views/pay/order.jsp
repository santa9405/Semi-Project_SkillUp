<script type="text/javascript"
	src="https://code.jquery.com/jquery-1.12.4.min.js"></script>
<script type="text/javascript"
	src="https://service.iamport.kr/js/iamport.payment-1.1.5.js"></script>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<style>
h6 {
	text-align: center;
}
</style>

<div>
	<h3>주문하기</h3>
	<hr>
	<div class=" container p-3"
		style="text-align: center; margin: 0auto;">
		<form method="POST" action="#" onsubmit="#" class="form-horizontal"
			role="form">
			<h6>수업명</h6> 
			<input type="text" readonly class="form-control-plaintext" id="name"
				style="text-align: center; margin: 0auto;"
				value="${order.lessonTitle}">
			<h6>수업내용</h6>
			<input type="text" readonly class="form-control-plaintext" id="name"
				style="text-align: center; margin: 0auto;"
				value="${order.lessonContent}">
			<h6>가격</h6>
			<input type="text" readonly class="form-control-plaintext" name=price
				id="name" style="text-align: center; margin: 0auto;"
				value="${order.price}"> <br>
			<br>
			<br>
			<div id="btn_group">
				<button class=" btn btn-primary float-right" type="button" id="btn1"
					onclick="location.href = '../pay/payclasshistory.do'">뒤로가기</button>
				<c:choose>
					<c:when test="${!empty sessionScope.loginMember }">
						<button class=" btn btn-primary float-right" id="btn2"
							type="button">결제하기</button>
					</c:when>
				</c:choose>
			</div>
		</form>
		<script>
$("#btn2").click(function () {
var IMP = window.IMP; 
IMP.init("imp49746668");
IMP.request_pay({
pg: 'inicis', 
pay_method: 'card',

merchant_uid: 'merchant_' + new Date().getTime(),
name: '${order.lessonTitle}',
amount: '${order.price}',
buyer_email: '${loginMember.memberEmail}',
buyer_name: '${loginMember.memberName}',
buyer_tel: '${loginMember.phone}',
buyer_addr: ' ',
buyer_postcode: ' ',
m_redirect_url: 'https://www.yourdomain.com/payments/complete'
/*
모바일 결제시,
결제가 끝나고 랜딩되는 URL을 지정
(카카오페이, 페이코, 다날의 경우는 필요없음. PC와 마찬가지로 callback함수로 결과가 떨어짐)
*/
}, function (rsp) {
console.log(rsp);
if (rsp.success) {
var msg = '결제가 완료되었습니다.';
msg += '고유ID : ' + rsp.imp_uid;
msg += '상점 거래ID : ' + rsp.merchant_uid;
msg += '결제 금액 : ' + rsp.paid_amount;
msg += '카드 승인번호 : ' + rsp.apply_num;
	document.location.href ="paychecksave.do";
	} else {
	var msg = ' 실패하였습니다.';
}
alert(msg);
	
});
});
</script>
	</div>