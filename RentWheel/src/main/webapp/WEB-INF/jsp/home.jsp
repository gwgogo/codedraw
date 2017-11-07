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

<title>Insert title here</title>
</head>
<style>
/* h1{
    text-align: center;
    font-family: Arial, Helvetica, sans-serif;
    text-shadow: 2px 2px blue;
} */

table {
    margin-left: auto;
    margin-right: auto;
}
td{
	width:150px;
	font-weight: bold;
}
.divClass{
	position:fixed;
	bottom:50px;
	width: 100%;
	text-align: center;
	font-size: 15px;
	font-weight: bold;
}  
</style>

<body>
<br/>
<div class="container-fluid">
	<div class="row">
		<div class="col-md-12">
			<div class="jumbotron well">
				<h1 class="text-center text-info">RENTWHEEL ADMIN HOME</h1><br/>
				<table>
					<tr>
						<td><a href="/admin/company">업체정보조회</a></td>
						<td><a href="/admin/mobility">모빌리티조회</a></td>
						<td><a href="/admin/user">사용자조회</a></td>
						<td><a href="/admin/reservation">예약내역조회</a></td>
						<td><a href="/admin/administrator">관리자 정보</a></td>
					</tr>
				</table>
			</div>
		</div>
	</div>
</div>
<!-- <div>
	<div class= "divClass">
		문의<br/>
		양동욱 : 010-1111-2222<br/>
		신광원 : 010-2222-3333<br/>
	</div>
</div> -->
</body>
</html>