<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
</head>
<body>
<form action="/insertDto" method="post">
아이디 : <input type="text" name="id"/></br>
이름 : <input type="text" name="name"/></br>
<input type="submit" value="전송"/>

${item.code }
${item.msg }
</form>
</body>
</html>