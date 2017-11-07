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
<title>User List Page</title>
</head>
<style>

table {
    margin-left: auto;
    margin-right: auto;
}
</style>

<body>
<h1 class="text-center text-info">사용자 리스트 </h1><br/>
<table border=1>
<tr>
	<th>사용자 ID</th>
	<th>사용자 이름</th>
	<th>사용자 권한</th>
	<th>사용자 잔고</th>
	<th>사용자 주소</th>
	<th>사용자 번호</th>
	<th>사용자 메일</th>
	<th>사용자 등록일</th>
</tr>
<c:forEach items="${userList}"  var="user">
<tr>
    <td>${user.u_id}</td>
    <td>${user.u_name}</td>
    <td>${user.u_auth_string}</td>
    <td>${user.u_balance}</td>
    <td>${user.u_address}</td>
    <td>${user.u_phone}</td>
    <td>${user.u_email}</td>
    <td>${user.u_registDatetime}</td>
</tr>
</c:forEach>
</table>

</body>
</html>