'use strict';

app.controller('mypageCtrl', ['$http', '$scope', '$cookieStore', '$window', '$location', '$rootScope', function($http, $scope, $cookieStore, $window, $location, $rootScope) {

    $rootScope.title = '마이페이지';
    $rootScope.sidebar = 'mypage';
    // reset login status
    $scope.dataLoading = true;
    $scope.userName = '';
    $scope.userPhone = '';
    $scope.userAddress = '';
    $scope.orderList = '';
    $scope.orderSum = [];

    var today = new Date();
    var year = today.getFullYear().toString();
    var month = (today.getMonth() + 1).toString();
    var day = today.getDate().toString();

    $scope.startYear = today.getFullYear().toString();
    $scope.startMonth = month.length == 1 ? '0' + month : month;
    $scope.startDay = day.length == 1 ? '0' + day : day;
    //$scope.startHour = '00';

    $scope.endYear = today.getFullYear().toString();
    $scope.endMonth = month.length == 1 ? '0' + month : month;
    $scope.endDay = day.length == 1 ? '0' + day : day;
    //$scope.endHour = '23';

    //사용자가 입력한 시작 - 끝 시간만큼의 주문현황을 조회한다
    //아직 시작 - 끝 시간에 대한 validation은 없다
    $scope.filter = function() {
        var searchInitDate = $rootScope.formatDateString($scope.startYear, $scope.startMonth, $scope.startDay);
        var searchFinishDate = $rootScope.formatDateString($scope.endYear, $scope.endMonth, $scope.endDay);

        if(searchInitDate>searchFinishDate){
            alert('시작날짜가 종료날짜보다 큽니다. 다시 한번 선택해주십시오.');
            return;
        }

        $http({
            url: 'http://52.231.28.248/apis/orders',
            method: "GET",
            params: {
                'searchInitDate': searchInitDate,
                'searchFinishDate': searchFinishDate
            }
        }).then(function(response) {

            $scope.orderList = response.data;

            $scope.orderSum = [];
            angular.forEach($scope.orderList, function(order, orderkey) {
                $scope.orderSum.push(0);
                order.reservationDate = $rootScope.elapseHour(order.reservationDate);
                if (order.cancelDate != undefined) {
                    order.cancelDate = $rootScope.elapseHour(order.cancelDate);
                }
                angular.forEach(order.productList, function(product, productkey) {
                    $scope.orderSum[orderkey] += product.quantity * product.price;
                });
            });
        }, function(error) {
            var exception = error.data;
            alert('잠시 후 다시 시도해주십시오.');
            // if (exception.errCode != undefined) {
            //     alert('에러 발생 : ' + exception.errCode);
            // } else {
            //     alert(error);
            //     console.log(error);
            // }
        });
    }

    $scope.customClass = function(statusString) {
        var className = '';
        if (statusString === '주문취소') {
            className = 'statusCancel';
        }
        return className;
    };

    $scope.deleteOrderLIst = function(reservationID) {
        $http({
                method: 'DELETE',
                url: 'http://52.231.28.248/apis/orders/' + reservationID
            }) //Success
            .then(function(response) {
                    alert('주문취소가 성공했습니다.');
                    $scope.filter(); // refresh
                }, //Failed
                function(response) {
                    var exception = response.data;
                    if (exception.errCode == 606) {
                        alert('부정 삭제입니다.');
                    }else{
                        alert('삭제할 수 없습니다. 잠시뒤에 시도해주십시오.');
                    }
                });
    }

    //로그인되지 않은 사용자를 로그인페이지로 되돌려보냄
    if ($cookieStore.get('session') === undefined) {
        alert('로그인 후 사용해주세요');
        $rootScope.goto('login/mypage');
    } else {
        //사용자 정보 및 거래현황을 불러온다
        // 유저 정보 수신
        $http.get('http://52.231.28.248/apis/user/mypage')
            //Success
            .then(
                function(userData) {
                    $scope.userName = userData.data.userID;
                    $scope.userPhone = userData.data.phone;
                    $scope.userAddress = userData.data.address;
                    $scope.userEmail = userData.data.email;
                    $scope.filter(); // 초기 접속시 당일 주문현황 확인 가능
                },
                function(error) {
                    alert('사용자 정보를 불러오는데 실패했습니다. 잠시 후 다시한번 시도해주십시오.');
                    $window.history.back();
                });
    }
}]);