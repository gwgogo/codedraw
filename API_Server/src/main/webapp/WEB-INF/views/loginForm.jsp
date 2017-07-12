<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<h3>로그인</h3>
	<form action="/login" method="post">
		아이디 : <input type="text" name="user_id" id="user_id" /><br/> 
		비번 : <input type="password" name="user_pw" id="user_pw"/><br/> 
		<input type="submit" value="전송"/>
	</form>
</body>
</html>