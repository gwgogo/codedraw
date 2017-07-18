'use strict';

app.controller('orderController', ['$http', '$scope', '$cookieStore', '$window',
    function ($http, $scope, $cookieStore, $window) {
        //로그인되지 않은 사용자를 로그인페이지로 되돌려보냄
        if ($cookieStore.get('session') === undefined) {
            alert('로그인 후 사용해주세요');
            $window.location.href = '/paper/login.html';
        }

        // 기초정보 초기화
        $scope.title = "주문하기";
        $scope.cookie = $cookieStore.get('session') || '없음';

        $scope.userName = '';
        $scope.userPhone = '';
        $scope.userAddress = '';

        // 컨트롤러 생성 즉시 정보를 가져옴.
        $http.get('http://127.0.0.1:8080/mypageData')
            //Success
            .then(
                function (userData) {
                    $scope.userName = userData.data.user_id;
                    $scope.userPhone = userData.data.phone;
                    $scope.userAddress = userData.data.address;
                    $scope.userEmail = userData.data.email;
                },
                function (error) {
                    alert(error);
                    //$window.history.back();
                });
    }]);
