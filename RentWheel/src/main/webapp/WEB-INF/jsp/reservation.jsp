<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/jsp/home.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<title>Reservation List Page</title>
</head>
<style>

table {
    margin-left: auto;
    margin-right: auto;
}
</style>
<body>

<h1 class="text-center text-info">예약 현황 페이지 </h1><br/>
<table border=1>
<tr>
	<th>예약 번호</th>
	<th>예약자 이름</th>
	<th>예약 모빌리티</th>
	<th>예약 금액</th>
	<th>예약 시작 시간</th>
	<th>예약 종료 시간</th>
	<th>예약 상태</th>
	<th>예약 업체 이름</th>
	<th>예약 업체 번호</th>
</tr>
<c:forEach items="${reservationList}"  var="reservation">
<tr>
    <td>${reservation.rs_id}</td>
    <td>${reservation.rs_user_id}</td>
    <td>${reservation.rs_mobility_name}</td>
    <td>${reservation.rs_price}</td>
    <td>${reservation.rs_start_time}</td>
    <td>${reservation.rs_end_time}</td>
    <td>${reservation.rs_flag_string}</td>
    <td>${reservation.rs_company_name}</td>
    <td>${reservation.rs_company_id}</td>
</tr>
</c:forEach>
</table>

</body>
</html>