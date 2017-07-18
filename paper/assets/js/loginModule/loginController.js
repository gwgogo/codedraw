'use strict';

app.controller('LoginController', ['$http', '$scope', '$cookieStore', '$window',
    function ($http, $scope, $cookieStore, $window) {
        // reset login status
        $scope.dataLoading = false;
        $scope.title = "로그인";

        $scope.cookie = $cookieStore.get('session') || '쿠키없음';
        
        //이미 로그인 된 경우, history.back 한다.
        //로그인페이지 테스트시에는 잠시 주석처리해도 됩니다.
        if($cookieStore.get('session') !== undefined){
            alert('이미 로그인 한 사용자입니다.');
            $window.history.back();
        }
            

        $scope.login = function () {
            $scope.dataLoading = true;
            
            $http({
                    method: 'POST',
                    url: 'http://127.0.0.1:8080/login',
                    data: $.param({
                        user_id: $scope.user_id,
                        user_pw: $scope.user_pw
                    }),
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'
                    }
                })
                //Success
                .then(function (response) {
                        var sessionValue = response.data.session;
                        $cookieStore.put('session', sessionValue);
                        //console.log($cookieStore.get('session'));
                        alert('안녕하세요, ' + $scope.user_id + '님');
                        $window.location.href = '/paper/dealList.html';
                    },
                    //Failed
                    function (response) {
                        alert(response);
                    });

            $scope.dataLoading = false;
        };
    }]);
