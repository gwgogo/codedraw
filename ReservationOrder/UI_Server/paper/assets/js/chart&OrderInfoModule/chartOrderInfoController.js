app.controller('LineOrderInfoCtrl', ['$rootScope', '$scope', '$http','$cookieStore', function($rootScope, $scope, $http,$cookieStore) {

    $rootScope.title = '대시보드';
    $rootScope.sidebar = 'dashboard';

    $scope.labels = []; // x 축을 담을 배열 
    $scope.seriesOrder = ['거래현황', '']; // 차트 이름
    $scope.seriesCancel = ['취소현황', '']; // 차트 이름
    $scope.orderData = []; // 주문 y축 데이터를 담을 배열 
    $scope.cancelData = []; // 취소 y축 데이터를 담을 배열 
    $scope.orderChart = []; // order_date를 담을 그릇  
    $scope.cancelChart = []; // cancelDate를 담을 그릇 

    $scope.datasetOverrideOrder = [{
        xAxisID: 'x-axis-1'
    }, {
        yAxisID: 'y-axis-1'
    }];
    //주문 차트에 줄 옵션
    $scope.optionsOrder = {
        responsive: true,
        title: {
            display: true,
            text: '시간별 거래현황'
        },
        tooltips: {
            mode: 'index',
            intersect: false,
        },
        hover: {
            mode: 'nearest',
            intersect: true
        },
        scales: {
            xAxes: [{
                id: 'x-axis-1',
                display: true,
                scaleLabel: {
                    display: true,
                    labelString: '시간'
                }
            }],
            yAxes: [{
                id: 'y-axis-1',
                type: 'linear',
                display: true,
                position: 'left',
                ticks: {
                    // y 축의 최대 최소 그리고 스탭 사이즈를 설정한다.
                     min: 0,
                    // max: 20,
                    stepSize: 2
                }
            }]
        }
    };
    $scope.datasetOverrideCancel = [{
        xAxisID: 'x-axis-1'
    }, {
        yAxisID: 'y-axis-1'
    }];
    //취소 차트에 줄 옵션
    $scope.optionsCancel = {
        responsive: true,
        title: {
            display: true,
            text: '시간별 취소현황'
        },
        tooltips: {
            mode: 'index',
            intersect: false,
        },
        hover: {
            mode: 'nearest',
            intersect: true
        },
        scales: {
            xAxes: [{
                id: 'x-axis-1',
                display: true,
                scaleLabel: {
                    display: true,
                    labelString: '시간'
                }
            }],
            yAxes: [{
                id: 'y-axis-1',
                type: 'linear',
                display: true,
                position: 'left',
                ticks: {
                    // y 축의 최대 최소 그리고 스탭 사이즈를 설정한다.
                     min: 0,
                    // max: 20,
                    stepSize: 2
                }

            }]
        }
    };

    // 거래현황 색깔
    $scope.colorsOrder = ['#45b7cd', '#ff6384', '#ff8e72'];
    // 취소현황 색깔
    $scope.colorsCancel = ['#ff6384', '#45b7cd', '#ff8e72'];

    /*
    createchart : 차트 그리는 함수 
    */
    $scope.createchart = function(graphdata) {
        $scope.labels = [];
        $scope.orderData = [];
        $scope.cancelData = [];
        var hours = '';
        var minutes = '';
        angular.forEach(graphdata, function(value, key) {
            var label = $rootScope.formatTime(value.snapshotTime);
            $scope.labels.push(label);
            $scope.orderData.push(value.snapshotOrderCount);
            $scope.cancelData.push(value.snapshotCancelCount);
        });

        $scope.orderChart = [$scope.orderData];
        $scope.cancelChart = [$scope.cancelData];

    };

    /*
    submitChart : 서버로 날짜를 보내 자료를 가져오는 함수
    */
    $scope.submitChart = function() {
        var fromDatetime = $rootScope.formatDateString($scope.startChartYear, $scope.startChartMonth, $scope.startChartDay, $scope.startChartHour) + ':00:00';
        // var toDatetime = $rootScope.formatDateString($scope.endChartYear, $scope.endChartMonth, $scope.endChartDay, $scope.endChartHour) + ':59:59';
        var toDatetime = $rootScope.formatDateString($scope.startChartYear, $scope.startChartMonth, $scope.startChartDay, $scope.endChartHour) + ':59:59';

        if(fromDatetime>=toDatetime){
            alert('시작날짜가 종료날짜보다 크거나 같습니다. 다시 한번 선택해주십시오.');
            return;
        }

        $http({
            url: 'http://52.231.28.248/apis/snapshots',
            method: "GET",
            params: {
                searchInitTime: fromDatetime,
                searchFinishTime: toDatetime
            }
        }).then(function(response) {
            $scope.graphdata = response.data;
            /**/
            if($scope.graphdata.length==0){
                alert('선택한 날짜의 데이터가 없습니다.');
            }
            // console.log($scope.graphdata);
            $scope.createchart($scope.graphdata);

        }, function(error) {
            var exception = error.data;
            alert('날짜 전송 실패 다시 한번 시도해주세요.');
            // alert('전송 실패 : ' + (exception.errCode != undefined ? exception.errCode : ''));
        });

    };

    $scope.startChart = function() {
        var date = new Date();
        var year = date.getFullYear().toString();
        var month = (date.getMonth() + 1).toString();
        var day = date.getDate().toString();


        if (month.length == 1) {
            month = '0' + month;
        }
        if (day.length == 1) {
            day = '0' + day;
        }

        $scope.startChartYear = year;
        $scope.startChartMonth = month;
        $scope.startChartDay = day;
        $scope.startChartHour = "00";

        // $scope.endChartYear = year;
        // $scope.endChartMonth = month;
        // $scope.endChartDay = day;
        // $scope.endChartHour = "23";

        /* 시작 날짜와 같은 날짜로 고정 */
        $scope.endChartYear = $scope.startChartYear;
        $scope.endChartMonth = $scope.startChartMonth;
        $scope.endChartDay = $scope.startChartDay;
        $scope.endChartHour = "23";

        $scope.submitChart();

    }




    $scope.dateView = true;
    $scope.userview = false;
    $scope.userID = '';
    $scope.buttonDate = true;
    $scope.buttonUser = false;

    $scope.getorderList = function() {
        var fromDatetime = $rootScope.formatDateString($scope.startOrderYear, $scope.startOrderMonth, $scope.startOrderDay);
        var toDatetime = $rootScope.formatDateString($scope.endOrderYear, $scope.endOrderMonth, $scope.endOrderDay);

        if(fromDatetime>toDatetime){
            alert('시작날짜가 종료날짜보다 큽니다. 다시 한번 선택해주십시오.');
            return;
        }

        $http({
                method: 'GET',
                url: 'http://52.231.28.248/apis/orders/admin/all',
                params: {
                    searchInitDate: fromDatetime,
                    searchFinishDate: toDatetime
                }
            }) //Success
            .then(function(response) {
                    $scope.orderList = response.data;
                    angular.forEach($scope.orderList, function(order, orderkey) {

                        order.reservationDate = $rootScope.elapseHour(order.reservationDate);
                        if (order.cancelDate != undefined) {
                            order.cancelDate = $rootScope.elapseHour(order.cancelDate);
                        }

                    });


                }, //Failed
                function(response) {
                    var exception = response.data;
                    if (exception.errCode != undefined && exception.errCode == 606) {
                        alert('관리자 권한이 없습니다.');
                    } else {
                        //알수없는 에러
                        // alert('에러발생 : ' + response);
                        alert('주문데이터 전송실패 잠시 후 다시 한번 시도해주세요.');
                        console.log(response);
                    }
                });
    }

    $scope.customClass = function(statusString) {
        var className = '';
        if (statusString === '주문취소') {
            className = 'statusCancel';
        }
        return className;
    };

    $scope.showView = function(view) {
        if (view === 'dateView') {
            $scope.dateView = true;
            $scope.userview = false;

        } else {
            $scope.dateView = false;
            $scope.userview = true;
        }
    };


    $scope.getOrderListWithUser = function() {
        var fromDatetime = $rootScope.formatDateString($scope.startOrderYear, $scope.startOrderMonth, $scope.startOrderDay);
        var toDatetime = $rootScope.formatDateString($scope.endOrderYear, $scope.endOrderMonth, $scope.endOrderDay);
        var userID = $scope.userID;

        if(fromDatetime>toDatetime){
            alert('시작날짜가 종료날짜보다 큽니다. 다시 한번 선택해주십시오.');
            return;
        }
        if(userID==undefined||userID==""){
            alert('사용자 ID를 입력해주세요');
            return;
        }

        $http({
                method: 'GET',
                url: 'http://52.231.28.248/apis/orders/admin/' + userID,
                params: {
                    searchInitDate: fromDatetime,
                    searchFinishDate: toDatetime
                }
            }) //Success
            .then(function(response) {
                    $scope.orderList = response.data;
                }, //Failed
                function(response) {
                    var exception = response.data;
                    if (exception.errCode == 606) {
                        alert('관리자 권한이 없습니다.');
                    }
                });
    }

    $scope.deleteOrderLIst = function(reservationID) {
        // console.log(reservationID);
        $http({
                method: 'DELETE',
                url: 'http://52.231.28.248/apis/orders/' + reservationID
            }) //Success
            .then(function(response) {
                    alert('주문취소가 성공했습니다.');

                    if ($scope.dateView) {
                        $scope.getorderList();

                    } else if ($scope.userview) {
                        $scope.getOrderListWithUser();
                    }

                }, //Failed
                function(response) {
                    var exception = response.data;
                    if (exception.errCode == 606) {
                        alert('관리자 권한이 없습니다.');
                    }else{
                        alert('삭제할 수 없습니다. 잠시뒤에 시도해주십시오.');
                    }
                });
    }



    $scope.startCondition = function() {
        var date = new Date(); // 오늘 날짜 조회
        var dateGap = 3; //3일 전의 날짜조회

        var year = date.getFullYear().toString();
        var month = (date.getMonth() + 1).toString();
        var day = date.getDate().toString();

        var startYear = year;
        var startMonth = month;
        var startDay = (Number(day) - dateGap).toString();

        /* 시작일자가 1일보다 작다면  31일을 더해주고,
            월에서 1을 빼줍니다.
            그러나 월이 1월인경우,
            12월로 바꾸고 년도를 1뺴줍니다.
            */
        if (Number(startDay) < 1) {
            startDay = (Number(startDay) + 31).toString();

            if ((Number(startMonth) - 1) >= 1) {
                startMonth = (Number(startMonth) - 1).toString();
            } else {
                startYear = (Number(startYear) - 1).toString();
                startMonth = "12"; //12월
            }


        }

        if (month.length == 1) {
            month = '0' + month;
        }
        if (day.length == 1) {
            day = '0' + day;
        }



        if (startMonth.length == 1) {
            startMonth = '0' + startMonth;
        }
        if (startDay.length == 1) {
            startDay = '0' + startDay;
        }

        $scope.startOrderYear = startYear;
        $scope.startOrderMonth = startMonth;
        $scope.startOrderDay = startDay;

        $scope.endOrderYear = year;
        $scope.endOrderMonth = month;
        $scope.endOrderDay = day;
        $scope.getorderList();
    };
    $scope.buttonChange = function(view) {

        if (view == "dateView") {
            $scope.buttonDate = true;
            $scope.buttonUser = false;

        } else {
            $scope.buttonDate = false;
            $scope.buttonUser = true;

        }

    };

    


            //로그인되지 않은 사용자를 로그인페이지로 되돌려보냄
    if ($cookieStore.get('privilege') === undefined) {
        alert('관리자 권한이 없습니다.');
        $rootScope.goto('login/dashboard');
    } else {
            /*startChart startCondition 호출*/
        $scope.startChart();
        $scope.startCondition();

    }


}]);