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
<title>Company List Page</title>

</head>
<style>
table {
    margin-left: auto;
    margin-right: auto;
}
</style>
<body>

<h1 class="text-center text-info">업체 정보 페이지 </h1> 
<table border=1>
<tr>
	<th>업체 이름</th>
	<th>업체 번호</th>
	<th>업체 주소</th>
	<th>대표자 메일</th>
	<th>대표자 번호</th>
</tr>
<c:forEach items="${companyList}"  var="company">
<tr>
   <td> <a href="/admin/mobility?c_id=${company.c_id}">${company.c_name}</a></td>
    <td>${company.c_id}</td>
    <td>${company.c_address}</td>
    <td>${company.c_email}</td>
    <td>${company.c_phone}</td>
</tr>
</c:forEach>
</table>

</body>
</html>