<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>

<script type="text/javascript">
	$(document).ready(function() {
		$("#button").click(function() {
			callAjax();
		})
	});

	function callAjax() {
		$.ajax({
			type : "POST",
			url : "/join",
			dataType : 'json',
			data : {
				userID : $('#userID').val(),
				userPW : $('#userPW').val()
			},
			success : function(data) {
				alert("success " + data.msg);
			},
			error : function(request,status,error) {
				 alert(request.errCode);
				 alert("fail " + request.responseText)
		    }
		});
	}
</script>

<title>Insert title here</title>

</head>

<body>
	<h3>회원가입</h3>
	<form>
		아이디 : <input type="text" name="userID" id="userID" /><br /> 비번 : <input
			type="password" name="userPW" id="userPW" /><br /> <input
			type="button" value="전송" id="button" />
	</form>
</body>
</html>