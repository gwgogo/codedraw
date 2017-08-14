app.controller('holidayCtrl', ['$rootScope', '$scope', '$http','$cookieStore', function($rootScope, $scope, $http,$cookieStore) {

    $rootScope.title = '공휴일관리';
    $rootScope.sidebar = 'holiday';

    $scope.holidayList = '';
    var holidayYear = '';
    var holidayMonth = '';
    var holidayDay = '';
    var holiydayTitle = "";
    var holiydayDate = "";
    var date = new Date();

    $scope.searchYear=date.getFullYear().toString();
    $scope.minYear=$scope.searchYear;// 조회입력가능한 최소 년도는 이번년도
    $scope.holidayYear = date.getFullYear().toString();
    $scope.holidayMonth = (date.getMonth() + 1).toString();
    $scope.holidayDay = date.getDate().toString();

    if ($scope.holidayMonth.length == 1) {
        $scope.holidayMonth = "0" + $scope.holidayMonth;
    }
    if ($scope.holidayDay.length == 1) {
        $scope.holidayDay = "0" + $scope.holidayDay;
    }


    $scope.getHolidayList = function() {

        $http({
                method: 'GET',
                url: 'http://52.231.28.248/apis/holidays/' + $scope.searchYear
            }) //Success
            .then(function(response) {
                    $scope.holidayList = response.data;
                    // console.log($scope.holidayList);

                    /* 아래 로직은 long type으로 오는 날짜를 각각의 select box로 넣어주기 위한 로직. */
                    angular.forEach($scope.holidayList, function(value, key) {
                        var holiday = new Date(value.holidayDate);

                        $scope.holidayList[key].holidayYear = (holiday.getFullYear()).toString();
                        $scope.holidayList[key].holidayMonth = (holiday.getMonth() + 1).toString();
                        $scope.holidayList[key].holidayDay = (holiday.getDate()).toString();

                        if ($scope.holidayList[key].holidayMonth.length < 2) {
                            $scope.holidayList[key].holidayMonth = '0' + $scope.holidayList[key].holidayMonth;
                        }

                        if ($scope.holidayList[key].holidayDay.length < 2) {
                            $scope.holidayList[key].holidayDay = '0' + $scope.holidayList[key].holidayDay;
                        }
                    });
                }, //Failed
                function(response) {
                    var exception = response.data;

                if (exception.errCode != undefined && exception.errCode ==  617) {
                    alert('잠시후 다시 한번 시도해주시기 바랍니다.');
                    console.log(exception.errMsg);
                } else {
                    //알수 없는 에러
                    // alert(exception);
                    alert('알 수 없는 오류 - 잠시 후 시도해주시기 바랍니다.');
                    console.log(exception);
                }

                });
    };

    $scope.minusYear = function() {
        /* 조회년도를 1년 빼주고 다시 검색 */
        $scope.searchYear--;
        $scope.getHolidayList();
        
    }

    
    $scope.pulsYear = function() {
        /* 조회년도를 1년 더해주고 다시 검색 */
        $scope.searchYear++;
        $scope.getHolidayList();

    }

    $scope.insertHoliday = function() {
        holiydayTitle = $scope.holidayTitle;
        holiydayDate = $rootScope.formatDate($scope.holidayYear + '-' + $scope.holidayMonth + '-' + $scope.holidayDay);

        if (holiydayTitle === undefined || holiydayTitle.trim().length == 0) {
            alert('공휴일을 입력해주세요');
            return;
        }


        $http({
                method: 'POST',
                url: 'http://52.231.28.248/apis/holidays',
                data: {
                    holidayDate: holiydayDate,
                    holidayTitle: holiydayTitle
                }
            }) //Success
            .then(function(response) {
                    alert('공휴일 삽입이 성공했습니다.');
                    $scope.getHolidayList();
                }, //Failed
                function(error) {
                // alert(response);
                var exception = error.data;
                if (exception.errCode != undefined && exception.errCode ==  617) {
                    alert('삽입 이상 중복된 날짜가 있는지 확인해주십시오.');
                    console.log(exception.errMsg);
                } else {
                    //알수 없는 에러
                    // alert(exception);
                    alert('알 수 없는 오류 - 잠시 후 시도해주시기 바랍니다.');
                    console.log(exception);
                }
                });

    };


    $scope.deleteHolidy = function(holiday) {
        if (confirm('정말 삭제하시겠습니까?')) {

            $http({
                    method: 'DELETE',
                    url: 'http://52.231.28.248/apis/holidays/' + holiday.holidayID,
                    data: {}
                })
                .then(function(response) {
                    alert('삭제되었습니다.');
                    $scope.getHolidayList();

                }, function(error) {
                    var exception = error.data;
                    if (exception.errCode != undefined && exception.errCode ==  617) {
                        alert('삭제 이상 선택한 날짜가 존재하는지 확인해주십시오.');
                        console.log(exception.errMsg);
                    } else {
                        //알수 없는 에러
                        // alert(exception);
                        alert('알 수 없는 오류 - 잠시 후 시도해주시기 바랍니다.');
                        console.log(exception);
                    }
                });


        }

    };


    $scope.changeHoliday = function(holiday) {
        holiydayTitle = holiday.holidayTitle;
        holiydayDate = $rootScope.formatDate(holiday.holidayYear + '-' + holiday.holidayMonth + '-' + holiday.holidayDay);
        if (confirm('정말 변경하시겠습니까?')) {
            $http({
                method: 'PUT',
                url: 'http://52.231.28.248/apis/holidays/' + holiday.holidayID,
                data: {

                    holidayDate: holiydayDate,
                    holidayTitle: holiydayTitle
                }
            }).then(function(response) {
                alert('변경이 성공했습니다.');
                $scope.getHolidayList();

            }, function(error) {
                var exception = error.data;
                if (exception.errCode != undefined && exception.errCode ==  617) {
                    alert('변경 이상 선택한 날짜가 존재하는지 확인해주십시오.');
                    console.log(exception.errMsg);
                } else {
                    //알수 없는 에러
                    // alert(exception);
                    alert('알 수 없는 오류 - 잠시 후 시도해주시기 바랍니다.');
                    console.log(exception);
                }
            });
        }
    };




    //로그인되지 않은 사용자를 로그인페이지로 되돌려보냄
    if ($cookieStore.get('privilege') === undefined) {
        alert('관리자 권한이 없습니다.');
        $rootScope.goto('login/holidayDay');
    } else {
        /* 만들어진 함수 호출*/
        $scope.getHolidayList();

    }


}]);