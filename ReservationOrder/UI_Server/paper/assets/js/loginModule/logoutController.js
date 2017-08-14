app.controller('logoutCtrl', ['$window', '$http', '$scope', '$cookieStore', '$rootScope', function($window, $http, $scope, $cookieStore, $rootScope) {

    if ($cookieStore.get('session') === undefined) {
        alert('로그인하지 않았습니다.');
        $window.history.back();
    } else {
        $http({
            method: 'GET',
            url: 'http://52.231.28.248/apis/user/logout',
            param: {}
        }).then(function(response) {
            alert('로그아웃 하였습니다.');
            $cookieStore.remove('session');
            $cookieStore.remove('user');
            $cookieStore.remove('privilege');

            /* privilege 와 user 를 들고다닌다. */
            $rootScope.privilege = $cookieStore.get('privilege') * 1 || 0;
            $rootScope.user = ($cookieStore.get('user') || '').toLowerCase();

            $rootScope.goto('home');
        }, function(error) {
            alert('로그아웃에 실패하였습니다. 다시 한번 시도해주시기 바랍니다.');
            $rootScope.goto('home');
        });
    }
}]);