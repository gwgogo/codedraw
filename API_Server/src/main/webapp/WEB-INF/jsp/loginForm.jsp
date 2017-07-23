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
			dataType : 'json',
			data : {
				user_id : $('#user_id').val(),
				user_pw : $('#user_pw').val()
			},
			
			success : function(data) {
				document.cookie = "session=" + data.session;
				alert("success, session : " + data.session);
				location.href='http://localhost:8080/user/main';
				
			},
			error : function(data) {
				 alert("fail : " + data.responseText)
		    }
		});
	}
	
	
</script>

<title>Insert title here</title>

</head>
<body>
<h3>로그인</h3>
	<form>
		아이디 : <input type="text" name="user_id" id="user_id" /><br/> 
		비번 : <input type="password" name="user_pw" id="user_pw"/><br/> 
		<input type="button" value="전송" id="button" />
	</form>
</body>
</html>