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
		$('#mypage').click(function(){
			mypage();
		})
		
		$('#logout').click(function(){
			logout();
		})
		
		$('#adminForm').click(function(){
			adminForm();
		})
		
		
		
	})
	function mypage(){
		$.ajax({
			type : "GET",
			url : "/user/mypage",
			dataType : 'json',	// 상관없음
			success : function(data){
				alert(data.user_id);
				location.href='http://localhost:8080/user/mypageForm';
			},
			error : function(data){
				alert(data.responseText);
				location.href='http://localhost:8080/user/loginForm';
			}
		})
	}
	
	function logout(){
		$.ajax({
			type : "GET",
			url : "/user/logout",
			success : function(data){
				location.href='http://localhost:8080/user/main';
			},
			error : function(data){
				location.href='http://localhost:8080/user/loginForm';
			}
		})
	}
	
	function adminForm(){
		$.ajax({
			type : "GET",
			url : "/user/admin",
			success : function(data){
				location.href='http://localhost:8080/user/adminForm';
			},
			error : function(data){
				location.href='http://localhost:8080/user/loginForm';
			}
		})
	}
	

</script>

</head>
<body>



<input type="button" value="mypage" id="mypage"/><br/>
<input type="button" value="logout" id="logout"/><br/>
<input type="button" value="adminForm" id="adminForm"/><br/>

</body>
</html>