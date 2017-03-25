<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>SNS크롤링,빅데이터 분석</title>
    <link href="https://fonts.googleapis.com/css?family=Open+Sans:300,600" rel="stylesheet" type="text/css">
    <link href="css/bootstrap.css" rel="stylesheet" type="text/css" />
    <link href="css/bootstrap.min.css" rel="stylesheet" type="text/css" />
    <link href="css/style.css" rel="stylesheet" type="text/css" />
    <link href="css/jqcloud.css" rel="stylesheet" type="text/css" />
    <!--[if lt IE 9]>
        <script src="https://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->
    <style>
        .wordcloud {
            width: 80%;
            height: 800px;
        }
    </style>
    <script src="js/jquery-2.1.3.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/jqcloud.js"></script>
    <script>
    var myColors= [ ["#F5AAAA", "#F5A0A0", "#F06E6E", "#F06464", "#EB3232", "#EB0000", "#D25A5A", "#CD4646", "#CD0000"],
                    ["#FFC300", "#FFC800", "#FFCD00", "#FFD200", "#FFD700", "#FFAA28", "#FFA01E", "#FF9614", "#FF8200"],
                    [],
                    ["#74D19D", "#6AC793", "#60BD89", "#6DD66D", "#63CC63", "#54BD54", "#4AB34A", "#369F36", "#228B22"],
                    []]
    
	function dataSetting(realData, num) {
		var word_list = new Array();
		for (var i = 0; i < 200; i++) {
			word_list[i] = {
				text : realData[i].word,
				weight : realData[i].count
			}
		}
		$("#wordcloudContainer").jQCloud('destroy');
		$("#wordcloudContainer").jQCloud(word_list, {
			autoResize: true,
			fontSize: {
				from: 0.05,
				to: 0.01
			},
			colors : myColors[num-1],
			shape : 'elliptic'
		});
	}
	function getData(num) {
		$.ajax({			// $는 jQuery를 의미함
			url : '/getData',
			type : 'GET',
			dataType : 'json',
			data : {
				"communityNo" : num		// '/getData' 호출할 때 인자값으로 넘겨줌, key & value 
			},
			contentType : 'application/json',
			success : function(list) {	// 컨트롤러에서 리턴한 modelMap이 list로 들어옴. list는 매개변수이름이므로 아무렇게나 해도 상관없음
				dataSetting(list.kwangwon, num);	// list.kwangwon에서 kwangwon은 컨트롤러에서 modelAttribute의 key값이어야함
				console.log(list.kwangwon);
			},
			error : function() {
				console.log("error");
			}
		});
	}
        $(function () {
            $(window).scroll(function (e) {
                if ($(this).scrollTop() > 750) {
                    $(".menu-top-home-fixed").css("margin-top", "0px");
                } else {
                    $(".menu-top-home-fixed").css("margin-top", "-78px");
                }
            });
        });
        
        $(document).ready(function () {
            $('#btn-nate').click(function () {
            	$('#wordcloud-name').html('NATE');
            	$('#wordcloud-name').css('color', '#D22E3E');
            	getData(1); 
            });
            $('#btn-huv').click(function () {
            	$('#wordcloud-name').html('웃긴대학');
            	$('#wordcloud-name').css('color', '#DA5513');
            	getData(2);
            });
            $('#btn-ppompu').click(function () {
            	$('#wordcloud-name').html('뽐뿌');
            	$('#wordcloud-name').css('color', '#5BC0DE');
            	getData(3); 
            });
            $('#btn-82cook').click(function () {
            	$('#wordcloud-name').html('82cook');
            	$('#wordcloud-name').css('color', '#5CB85C');
            	getData(4); 
            });
            $('#btn-slr').click(function () {
            	$('#wordcloud-name').html('SLR');
            	$('#wordcloud-name').css('color', '#3157AD');
            	getData(5); 
            });
        });

    </script>
</head>
<body>
    <!-- 헤더 -->
    <div class="header" id="home">
        <div class="menu-top-home">
            <div class="container">
                <div class="row">
                    <div class="col-sm-3">
                        <p style="font-size: 200%"><strong>CodeDraw</strong></p>
                    </div>
                    <div class="col-sm-9 align-right">
                        <ul class="menu-links">
                            <li class="hidden-xs"><a href="#home">Home</a></li>
                            <li class="hidden-xs"><a href="#info-project">Project Info</a></li>
                            <li class="hidden-xs"><a href="#wordcloud-page">Word Cloud</a></li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
        <div class="menu-top-home-fixed hidden-xs">
            <div class="container">
                <div class="row">
                    <div class="col-xs-3">
                        <a href="#" class="logo">
                            <p style="font-size: 200%"><strong>CodeDraw</strong></p>
                        </a>
                    </div>
                    <div class="col-xs-9 align-right">
                        <ul class="menu-links">
                            <li><a href="#home">Home</a></li>
                            <li><a href="#info-project">Project Info</a></li>
                            <li><a href="#wordcloud-page">Word Cloud</a></li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
        <div class="container align-center">
            <h1><strong>빅데이터 <br /> 하둡 기반 SNS 크롤링 <br />데이터 분석 시스템</strong><br>by CodeDraw</h1>
            <h4 class="silver hidden-xs"> 개발자 - 계상우 신광원 임준혁 양동욱</h4>
            <div class="uploader-box box-padding-big-top">
                <div class="btn-group">
                </div>
                <div id="progress" class="progress">
                    <div class="progress-bar progress-bar-danger"></div>
                </div>
                <div id="files" class="files"></div>
            </div>
            <div class="silver small-info margin-top-big hidden-xs">
            </div>
        </div>
    </div>

    <div class="box-padding-big white-bg" id="info-project">
        <div class="container">
            <div class="row">
                <div class="col-sm-5 info-big">
                    <h2>프로젝트 소개<br /><br><strong>SNS 크롤링 빅데이터 <br />분석 및 활용 가치 창출</strong></h2>
                    <p>
                        네이트판, 뽐뿌, 웃긴대학, 82cook 대형 커뮤니티의 게시판 텍스트를 크롤링 하여 데이터를 수집한다.
                        <br />수집한 텍스트 데이터들을 형태소 분석을 통해 의미 있는 데이터로 추출한다.
                        이 데이터를 워드 클라우드 기술을 <br />통해 웹으로 시각화 한다.
                    </p>
                    <p><strong>향후 계획</strong><br>범죄 공공 빅데이터를 통계 분석하여 범죄 예방 솔루션 <br />개발 할 예정이다.</p>
                </div>
                <div class="col-sm-7" style="padding-top:15%">
                    <img src="images/system.png">
                </div>
            </div>
        </div>
    </div>

    <div class="box-padding-big" id="wordcloud-page">
        <div class="container align-center wordcloud" id="wordcloudContainer"></div>
        <div class="container align-center"><p style="font-size: 150%"><strong id="wordcloud-name"></strong></p></div>
        <div class="container align-center" style="margin-top : 30px;">
            <button type="button" class="btn btn-danger btn-sm" id="btn-nate">NATE</button>
            <button type="button" class="btn btn-warning btn-sm" id="btn-huv">웃긴대학</button>
            <button type="button" class="btn btn-info btn-sm" id="btn-ppompu">뽐뿌</button>
            <button type="button" class="btn btn-success btn-sm" id="btn-82cook">82cook</button>
            <button type="button" class="btn btn-primary btn-sm" id="btn-slr">SLR</button>
        </div>
    </div>

    <div class="box-padding-big dark-bg-stat hidden-xs">
        <div class="container">
            <div class="row">
                <div class="col-sm-2 hidden-xs">
                    <div class="info-box align-center">
                        <h3>253</h3>
                        Todays uploads
                    </div>
                </div>
                <div class="col-sm-2 hidden-xs">
                    <div class="info-box align-center">
                        <h3>171092</h3>
                        Projects uploaded
                    </div>
                </div> 
                <div class="col-sm-4 align-center">
                    <a href="#registerModal" role="button" data-toggle="modal" class="btn btn-danger btn-lg">Sign Up <strong>now</strong></a>
                </div>
                <div class="col-sm-2 hidden-xs">
                    <div class="info-box align-center">
                        <h3>931523</h3>
                        Project views
                    </div>
                </div>
                <div class="col-sm-2 hidden-xs">
                    <div class="info-box align-center">
                        <h3>100867</h3>
                        Comments posted
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="dark-footer">
        <footer>
            Copyright(c)2016 by <a href="#">CodeDraw</a> All right reserved | Contact: <a href="mailto:2pelozup@gmail.com">2pelozup@gmail.com</a>
        </footer>
    </div>

</body>
</html>