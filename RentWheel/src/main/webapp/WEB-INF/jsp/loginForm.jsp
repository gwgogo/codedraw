<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<title>Admin Login Page</title>
</head>
<style>
h1,h2 {
    text-align: center;
}
table {
        margin-left: auto;
        margin-right: auto;
      }
.button{
	color : white;
	background : blue;
}
</style>
<body>
<h1 class="text-primary">Welcome!</h1>
<h2 class="text-primary">RentWheel Admin Page</h2><br/>

<form action="http://localhost:8080/admin/login" method="post" id="submitForm">
<table>
	<tr>
		<td>I D :</td>
		<td><input type=text name=u_id></td>
	</tr>
	<tr>
		<td>PW:</td>
		<td><input type=password name=u_pw></td>
		<td><input type="submit" value="login"></td>
	</tr>
</table>
</form>

</body>
</html>