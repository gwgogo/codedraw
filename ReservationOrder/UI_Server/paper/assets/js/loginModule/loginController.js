'use strict';

app.controller('loginCtrl', ['$http', '$scope', '$cookieStore', '$window', '$location', '$routeParams', '$route', '$rootScope', function($http, $scope, $cookieStore, $window, $location, $routeParams, $route, $rootScope) {
    // reset login status
    $scope.dataLoading = false;
    //  /login/xxxx
    // 만일 xxxx가 존재 한다면 그 url로 보낸다.
    $scope.params = $routeParams;

    //이미 로그인 된 경우, history.back 한다.
    //로그인페이지 테스트시에는 잠시 주석처리해도 됩니다.
    if ($cookieStore.get('session') !== undefined) {
        alert('이미 로그인 한 사용자입니다.');
        $window.history.back();
    }


    $scope.login = function() {
        $scope.dataLoading = true;

        if ($scope.userID === '' || $scope.userPassword === '') {
            alert('아이디, 패스워드를 입력해주세요');
        } else {
            $http({
                    method: 'POST',
                    url: 'http://52.231.28.248/apis/user/login',
                    data: $.param({
                        userID: $scope.userID,
                        userPW: $scope.userPassword
                    }),
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'
                    }
                })
                //Success
                .then(function(response) {
                        var successObject = response.data;
                        $cookieStore.put('session', successObject.session);
                        $cookieStore.put('privilege', successObject.privilege);
                        $cookieStore.put('user', $scope.userID);
                        $rootScope.user = $scope.userID;
                        $rootScope.privilege = $cookieStore.get('privilege') * 1 || 0;
                        alert('안녕하세요, ' + $scope.userID + '님');

                        if ($scope.params.refer === undefined) {
                            $location.path('/home');
                        } else if ($scope.params.productId === undefined) {
                            $location.path('/' + $scope.params.refer);
                        } else {
                            $location.path('/' + $scope.params.refer + '/' + $scope.params.productId);
                        }

                    }, //Failed
                    function(response) {
                        var exception = response.data;
                        if (exception.errCode == 602) {
                            alert('잘못된 아이디 혹은 비밀번호 입니다.');
                            console.log(exception.errMsg);
                        } else {
                            alert('서버의 일시적 오류입니다. 다시 한번 시도해주십시오.');
                        }
                    });
        }
        $scope.dataLoading = false;
    };

    //비밀번호 위치에서 엔터치면 로그인을 불러옴
    $scope.enter = function(keyEvent) {
        if (keyEvent.which === 13)
            $scope.login();
    }
}]);