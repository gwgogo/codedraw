app.controller('timeslotCtrl', ['$rootScope', '$scope', '$http','$cookieStore',function($rootScope, $scope, $http,$cookieStore) {

    
    $rootScope.title = '배송시간관리';
    $rootScope.sidebar = 'timeslot';
    var startTime = '';
    var endTime = '';
    var cutoff = '';

    var date = new Date();
    var after3day = new Date();
    after3day.setDate(date.getDate() + 3);

    var searchInitYear = date.getFullYear().toString();
    var searchInitMonth = (date.getMonth() + 1).toString();
    var searchInitDate = date.getDate().toString();

    var searchFinishYear = after3day.getFullYear().toString();
    var searchFinishMonth = (after3day.getMonth() + 1).toString();
    var searchFinishDate = after3day.getDate().toString();


    $scope.cutoffHours = '00';
    $scope.cutoffMinute = '00';


    if (searchInitMonth.length == 1) {
        searchInitMonth = '0' + searchInitMonth;
    }
    if (searchInitDate.length == 1) {
        searchInitDate = '0' + searchInitDate;
    }
    if (searchFinishMonth.length == 1) {
        searchFinishMonth = '0' + searchFinishMonth;
    }
    if (searchFinishDate.length == 1) {
        searchFinishDate = '0' + searchFinishDate;
    }
    $scope.startOrderYear = searchInitYear;
    $scope.startOrderMonth = searchInitMonth;
    $scope.startOrderDate = searchInitDate;
    $scope.endOrderYear = searchFinishYear;
    $scope.endOrderMonth = searchFinishMonth;
    $scope.endOrderDate = searchFinishDate;

    var searchInitDate = $scope.startOrderYear + '-' + $scope.startOrderMonth + '-' + $scope.startOrderDate;
    var searchFinishDate = $scope.endOrderYear + '-' + $scope.endOrderMonth + '-' + $scope.endOrderDate;

    /* 뷰를 보여주기 위한 변수 */
    $scope.nowDeliveryView = true;
    $scope.DeliveryChangeView = false;

    $scope.showView = function(view) {
        if (view === 'nowDeliveryView') {
            $scope.nowDeliveryView = true;
            $scope.DeliveryChangeView = false;
        } else {
            $scope.nowDeliveryView = false;
            $scope.DeliveryChangeView = true;
        }
    };



    $scope.getTimeSlotsetting = function() {
        $http({
                method: 'GET',
                url: 'http://52.231.28.248/apis/timeslotsetting',
                data: $.param({}),
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'
                }
            }) //Success
            .then(function(response) {
                    $scope.timeslotList = response.data;

                    /* 입력받은 데이터중 시간을 시 와 분으로 쪼는 로직 */
                    angular.forEach($scope.timeslotList, function(value, key) {
                        $scope.timeslotList[key].starthours = value.startTime.substring(0, 2);
                        $scope.timeslotList[key].startminute = value.startTime.substring(3, 5);
                        $scope.timeslotList[key].endhours = value.endTime.substring(0, 2);
                        $scope.timeslotList[key].endminute = value.endTime.substring(3, 5);
                        $scope.timeslotList[key].startCutoffHours = value.cutoff.substring(0, 2);
                        $scope.timeslotList[key].endCutoffMinute = value.cutoff.substring(3, 5);
                    });
                }, //Failed
                function(response) {
                    var exception = response.data;
                    if (exception.errCode == 619) {
                        alert('배송시간템플릿을 불러오는데 실패했습니다. 잠시 후 다시 시도해 주십시오.');
                    }else{
                        alert('잠시후 다시 시도해주십시오.');
                    }
                });
    };


    //formatDate() 이거 쓰자
    $scope.getNowTimeSlot = function() {
        var searchInitDate = $scope.startOrderYear + '-' + $scope.startOrderMonth + '-' + $scope.startOrderDate;
        var searchFinishDate = $scope.endOrderYear + '-' + $scope.endOrderMonth + '-' + $scope.endOrderDate;

        if(searchInitDate>=searchFinishDate){
            alert('시작날짜가 종료날짜보다 크거나 같습니다. 다시 한번 선택해주십시오.');
            return;
        }

        $http({
                method: 'GET',
                url: 'http://52.231.28.248/apis/timeslots',
                params: {
                    searchInitDate: searchInitDate,
                    searchFinishDate: searchFinishDate

                },
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'
                }
            }) //Success
            .then(function(response) {
                    $scope.nowTimeslotList = response.data;

                    angular.forEach($scope.nowTimeslotList, function(value, key) {
                        value.deliveryDate = $rootScope.formatDate(value.deliveryDate);
                        angular.forEach($scope.nowTimeslotList[key].timeList, function(valueTimeList, keyTimeList) {
                            valueTimeList.cutoff = $rootScope.elapseHour(valueTimeList.cutoff);


                        });
                    });

                }, //Failed
                function(response) {
                    
                    var exception = response.data;
                    if (exception.errCode == 619) {
                        alert('배송시간현황을 불러오는데 실패했습니다. 잠시 후 다시 시도해 주십시오.');
                    }else{
                        alert('잠시후 다시 시도해주십시오.');
                    }
                });
    };

    $scope.changetimeslotsetting = function(timeslot) {
        if (false) {

        } else {
            startTime = timeslot.starthours + ':' + timeslot.startminute + ':00';
            endTime = timeslot.endhours + ':' + timeslot.endminute + ':00';
            cutoff = timeslot.startCutoffHours + ':' + timeslot.endCutoffMinute + ':00';

            if(startTime>=endTime){
            alert('시작날짜가 종료날짜보다 크거나 같습니다. 다시 한번 선택해주십시오.');
            return;
            }

            $http({
                    method: 'PUT',
                    url: 'http://52.231.28.248/apis/timeslotsetting/' + timeslot.timeslotSettingID,
                    data: {
                        startTime: startTime,
                        endTime: endTime,
                        cutoff: cutoff
                    }
                }) //Success
                .then(function(response) {
                    alert('변경이 성공했습니다.');
                    $scope.getTimeSlotsetting();
                }, function(response) {
                    //Failed
                    var exception = response.data;
                    if (exception.errCode == 619) {
                        alert('동일한 시간이 설정되어있습니다.');
                    }else{
                        alert('잠시후 다시 시도해주십시오.');
                    }
                    
                });
        }
    };


    $scope.insertTimeslotsetting = function() {
        if (false) {

        } else {

            startTime = $scope.starthours + ':' + $scope.startminute + ':00';
            endTime = $scope.endhours + ':' + $scope.endminute + ':00';
            cutoff = $scope.cutoffHours + ':' + $scope.cutoffMinute + ':00';


            if(startTime>=endTime){
            alert('시작날짜가 종료날짜보다 크거나 같습니다. 다시 한번 선택해주십시오.');
            return;
            }

            $http({
                    method: 'POST',
                    url: 'http://52.231.28.248/apis/timeslotsetting',
                    data: {
                        startTime: startTime,
                        endTime: endTime,
                        cutoff: cutoff
                    }
                })
                //Success
                .then(function(response) {
                        alert('삽입이 성공했습니다.');
                        $scope.getTimeSlotsetting();
                    },
                    //Failed
                    function(response) {
                    
                    var exception = response.data;
                    if (exception.errCode == 619) {
                        alert('동일한 시간이 설정되어있습니다.');
                    }else{
                        alert('잠시후 다시 시도해주십시오.');
                    }

                    });
        }

    };


    $scope.deleteTimeslotsetting = function(timeslot) {
        $http({
                method: 'delete',
                url: 'http://52.231.28.248/apis/timeslotsetting/' + timeslot.timeslotSettingID,
                data: $.param({})
            })
            //Success
            .then(function(response) {
                    alert('삭제가 완료되었습니다.');
                    $scope.getTimeSlotsetting();
                },
                //Failed
                function(response) {

                    var exception = response.data;
                    if (exception.errCode == 619) {
                        alert('삭제이상.');
                    }else{
                        alert('잠시후 다시 시도해주십시오.');
                    }

                });

    };



        //로그인되지 않은 사용자를 로그인페이지로 되돌려보냄
    if ($cookieStore.get('privilege') === undefined) {
        alert('관리자 권한이 없습니다.');
        $rootScope.goto('login/timeslot');
    } else {
        //gettimeslot 호출
        $scope.getTimeSlotsetting();
        $scope.getNowTimeSlot();

    }    


}]);