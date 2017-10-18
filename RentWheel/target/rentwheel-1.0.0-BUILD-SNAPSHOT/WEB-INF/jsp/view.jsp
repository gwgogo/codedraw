<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
</head>
<body>
hhh</br>
${u_id }</br>
${u_pw }</br>
${u_name }</br>
${u_phone }</br>

<%
	String id="";
	id = (String)session.getAttribute("u_id");
%>
<a href = "http://localhost:8080/logout">로그아웃</a>
</body>
</html>