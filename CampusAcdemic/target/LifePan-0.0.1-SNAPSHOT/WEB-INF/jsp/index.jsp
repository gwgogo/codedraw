<!DOCTYPE html>

<html lang="en" xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=no" />
<title>code draw</title>
<link href="css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link href="css/jqcloud.css" rel="stylesheet" type="text/css" />
<link href="css/jquery.fullPage.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/jquery-2.1.3.min.js"></script>
<script type="text/javascript" src="js/jquery.fullPage.min.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
<script type="text/javascript" src="js/jqcloud.js"></script>
<style>
* {
	-webkit-text-size-adjust: none;
	max-width: 100%;
}

body {
	margin-top: 10px;
}
</style>
<script>
	var realData;

	$(document).ready(function() {
		$('#fullpage').fullpage();
	});

	function dataSetting(realData) {
		var word_list = new Array();
		for (var i = 0; i < 800; i++) {
			word_list[i] = {
				text : realData[i].word,
				weight : realData[i].count
			}
		}
		$("#wordcount1").jQCloud(word_list);
	}
	function getData(num) {
		$.ajax({
			url : '/getData',
			type : 'GET',
			dataType : 'json',
			data : {
				"communityNo" : num
			},
			contentType : 'application/json',
			success : function(data) {
				dataSetting(data.data);
				console.log(data);
			},
			error : function() {
				console.log("error");
			}
		});
	}
</script>
</head>
<body>
	<div class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<div class="navbar-header">
				<a class="navbar-brand" href="#">Code Draw</a>
				<button class="navbar-toggle collapsed" data-toggle="collapse"
					data-target="#target">
					<span class="sr-only">Toggle Navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
			</div>
			<div class="collapsed navbar-collapse" id="target">
				<ul class="nav navbar-nav">
					<li><a href="#" class="dropdown-toggle" data-toggle="dropdown">
							<span>개발진</span> <span class="caret"></span>
					</a>
						<ul class="dropdown-menu">
							<li><a href="#">양동욱</a></li>
							<li><a href="#">계상우</a></li>
							<li><a href="#">신광원</a> </lir>
							<li class="divider"></li>
							<li><a href="#">임준혁</a></li>
						</ul></li>
					<li><a href="#" class="dropdown-toggle" data-toggle="dropdown">
							<span>WordCount</span> <span class="caret"></span>
					</a>
						<ul class="dropdown-menu">
							<li><a href="#1stWordCount" id="natePan"
								onclick="getData(1)">네이트판</a></li>
							<li><a href="#2ndWordCount" id="huv" onclick="getData(2)">웃긴대학</a></li>
							<li><a href="#3rdWordCount" id="ppompu" onclick="getData(3)">뽐뿌</a></li>
							<li><a href="#4thWordCount" id="cook" onclick="getData(4)">82cook</a></li>
						</ul></li>
				</ul>
				<ul class="nav navbar-nav navbar-right">
					<li><a href="#">후원</a></li>
					<li><a href="#">팀원 모집</a></li>
				</ul>
				<form class="navbar-form navbar-right">
					<input type="text" class="form-control" placeholder="Search" />
				</form>
			</div>
		</div>
	</div>

	<div id="fullpage">
		<div class="section" data-anchor="1stWordCount">
			<div class="container">
				<div style="margin: auto">
					<div><h1>네이트판<h1></div>
					<div id="wordcount1"
						style="width: 960px; height: 720px; border: 1px solid #ccc;">
				</div>
				</div>
			</div>
		</div>

		<div class="section" id="wordcount2" data-anchor="2ndWordCount">
			<div class="container">
				<div style="width: 960px; margin: auto">
					<h1>Word Count 2</h1>
					<img src="img/img2.jpg" width="960px" height="720px" />
				</div>
			</div>
		</div>

		<div class="section" id="wordcount3" data-anchor="3rdWordCount">
			<div class="container">
				<div style="width: 960px; margin: auto">
					<h1>Word Count 3</h1>
					<img src="img/img3.jpg" width="960px" height="720px" />
				</div>
			</div>
		</div>

		<div class="section" id="wordcount4" data-anchor="4thWordCount">
			<div class="container">
				<div style="width: 960px; margin: auto">
					<h1>Word Count 4</h1>
					<img src="img/img4.jpg" width="960px" height="720px" />
				</div>
			</div>
		</div>
	</div>

</body>
</html>