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
<title>Mobility List Page</title>
</head>
<style>

table {
    margin-left: auto;
    margin-right: auto;
}
</style>
<body>

<h1 class="text-center text-info">모빌리티 리스트</h1><br/>
<table border=1>
<tr>
	<th>모빌리티 번호</th>
	<th>모빌리티 이름</th>
	<th>모빌리티 가격</th>
	<th>모빌리티 등록일</th>
	<th>모빌리티 보유업체</th>
	<th>모빌리티 브랜드</th>
</tr>
<c:forEach items="${mobilityList}"  var="mobility">
<tr>
    <td>${mobility.m_id}</td>
    <td>${mobility.m_name}</td>
    <td>${mobility.m_price}</td>
    <td>${mobility.m_registDate}</td>
    <td>${mobility.m_company_name}</td>
    <td>${mobility.m_brand_name}</td>
</tr>
</c:forEach>
</table>
</body>
</html>