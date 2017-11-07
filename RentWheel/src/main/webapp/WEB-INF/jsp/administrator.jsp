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
<title>Administrator Information Page</title>
</head>
<style>
table {
    margin-left: auto;
    margin-right: auto;
}
</style>
<body>
<h1 class="text-center text-info">관리자 정보 페이지 </h1> 
<table border=1>
<tr>
	<th>관리자 이름</th>
	<th>관리자 번호</th>
	<th>은행</th>
	<th>계좌번호</th>
	<th>잔액</th>
</tr>
<tr>
   	<td>${administrator.a_name}</td>
    <td>${administrator.a_phone}</td>
    <td>${administrator.a_acnt_bank}</td>
    <td>${administrator.a_acnt_num}</td>
    <td>${administrator.a_balance}</td>
</tr>
</table>
</body>
</html>