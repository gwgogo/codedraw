<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>

<script type="text/javascript">
$(document).ready(function(){
	$('#button').click(function(){
		$.ajax({
			type : "GET",
			url : "/mypage",
			success : function(data){
				location.href='http://localhost:8080/mypage';
			},
			error : function(data){
				location.href='http://localhost:8080/loginForm';
			}
		})
	})
})

</script>

</head>
<body>
아이디 : <%= (String)session.getAttribute("user_id") %> <br/>
<!-- <a href="/mypage">mypage</a> -->
<input type="button" value="mypage" id="button"/>
</body>
</html>