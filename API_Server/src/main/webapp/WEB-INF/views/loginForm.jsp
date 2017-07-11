<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="http://code.jquery.com/jquery-latest.min.js"></script>

<script type="text/javascript">
	$(document).ready(function() {
		$("#button").click(function() {
			callAjax();
		})
	});

	function callAjax() {
		$.ajax({
			type : "POST",
			url : "/user/login",
			data : {
				user_id : $('#user_id').val(),
				user_pw : $('#user_pw').val()
			},
			success : function(){
				alert('success!!!');
				$('body').append("로그인성공</br>")
			},
			error : function(){
				alert('fail!!!');
				$('body').append("로그인실패</br>");
			}
		});
	}
	
	
</script>

<title>Insert title here</title>
</head>
<body>
	<form action="/login" method="post">
		아이디 : <input type="text" name="user_id" id="user_id" /></br> 
		비번 : <input type="password" name="user_pw" id="user_pw"/></br> 
		<input type="button" value="전송" id="button" />
	</form>
</body>
</html>