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
		$('#mypageData').click(function(){
			mypageData();
		})
		
		$('#logout').click(function(){
			logout();
		})
		
		$('#admin').click(function(){
			admin();
		})
		
		
		
	})
	function mypageData(){
		$.ajax({
			type : "GET",
			url : "/mypageData",
			dataType : 'json',	// 상관없음
			success : function(data){
				alert(data.user_id);
				location.href='http://localhost:8080/mypage';
			},
			error : function(data){
				alert(data.responseText);
				location.href='http://localhost:8080/loginForm';
			}
		})
	}
	
	function logout(){
		$.ajax({
			type : "GET",
			url : "/logout",
			success : function(data){
				location.href='http://localhost:8080/main';
			},
			error : function(data){
				location.href='http://localhost:8080/loginForm';
			}
		})
	}
	
	function admin(){
		$.ajax({
			type : "GET",
			url : "/admin",
		})
	}
	

</script>

</head>
<body>



<input type="button" value="mypageData" id="mypageData"/><br/>
<input type="button" value="logout" id="logout"/><br/>
<input type="button" value="admin" id="admin"/><br/>

</body>
</html>