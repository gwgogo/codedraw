var app = angular.module('module', ['ngCookies', 'ngRoute', 'chart.js']);
var global = new Object();

app.config(function($locationProvider, $httpProvider, $cookiesProvider) {
    // $locationProvider.html5Mode({
    //     enabled: true,
    //     requireBase: false
    // });
    $locationProvider.html5Mode(false);
    $httpProvider.defaults.withCredentials = true;
    $cookiesProvider.defaults.path = '/';
});

app.config(function($routeProvider) {
    $routeProvider
        .when('/home', {
            templateUrl: '/paper/view/productList.html',
            controller: 'productListCtrl'
        })
        .when('/product/:productId', {
            templateUrl: '/paper/view/product.html',
            controller: 'productDetailCtrl'
        })
        .when('/login', {
            templateUrl: '/paper/view/login.html',
            controller: 'loginCtrl'
        })
        .when('/login/:refer', {
            templateUrl: '/paper/view/login.html',
            controller: 'loginCtrl'
        })
        .when('/login/:refer/:productId', {
            templateUrl: '/paper/view/login.html',
            controller: 'loginCtrl'
        })
        .when('/logout', {
            templateUrl: '/paper/view/logout.html',
            controller: 'logoutCtrl'
        })
        .when('/cart', {
            templateUrl: '/paper/view/cart.html',
            controller: 'cartCtrl'
        })
        .when('/order', {
            templateUrl: '/paper/view/order.html',
            controller: 'orderCtrl'
        })
        .when('/order/:where', {
            templateUrl: '/paper/view/order.html',
            controller: 'orderCtrl'
        })
        .when('/order/:where/:productId/:quantity', {
            templateUrl: '/paper/view/order.html',
            controller: 'orderCtrl'
        })
        .when('/mypage', {
            templateUrl: '/paper/view/mypage.html',
            controller: 'mypageCtrl'
        })
        .when('/dashboard', {
            templateUrl: '/paper/view/dashboard.html',
            controller: 'LineOrderInfoCtrl'
        })
        .when('/timeslot', {
            templateUrl: '/paper/view/timeslot.html',
            controller: 'timeslotCtrl'
        })
        .when('/holiday', {
            templateUrl: '/paper/view/holiday.html',
            controller: 'holidayCtrl'
        })
        .otherwise({
            redirectTo: '/home'
        });

});

//검색 기간 SelectBox를 설정하기 위한 필터
app.filter('range', function() {
    return function(input, min, max) {
        min = parseInt(min); //Make string input int
        max = parseInt(max);
        var str = '';

        for (var i = min; i < max; i++) {
            str = String(i);
            if (str.length == 1) {
                str = '0' + str;
            }
            input.push(str);
        }
        return input;
    };
});

//원 단위를 만들기 위한 필터
app.filter('won', function() {
    return function(input) {
        if (input == undefined) return 'undefined';
        else return input.toLocaleString() + ' 원';
    }
});

//헤더 공통
app.controller('header', ['$scope', '$cookieStore', '$rootScope', function($scope, $cookieStore, $rootScope) {
    var userID = ($cookieStore.get('user') || '').toLowerCase();
    $rootScope.user = userID;
}]);


////////////////////////global function////////////////////////
app.run(function($rootScope, $window, $cookieStore, $location,$route) {


    $rootScope.setClassIfCancled = function(statusString) {
        var className = '';
        if (statusString === '주문취소') {
            className = 'statusCancel';
        }
        return className;
    };

    /*각 컨트롤러 마다 삽입*/
    $rootScope.title = '상품리스트';
    $rootScope.sidebar = 'productList';
    $rootScope.privilege = $cookieStore.get('privilege') * 1 || 0;
    $rootScope.user = '';
    //Date 오브젝트를 'yyyy-mm-dd' format 으로 변경
    $rootScope.formatDate = function(date) {
        var date = $rootScope.elapseHour(date),
            month = '' + (date.getMonth() + 1),
            day = '' + date.getDate(),
            year = date.getFullYear();

        if (month.length < 2) month = '0' + month;
        if (day.length < 2) day = '0' + day;

        return $rootScope.formatDateString(year, month, day);
    };

    //Date 오브젝트를 'HH-mm' Date format 으로 변경
    $rootScope.formatTime = function(date) {
        var date = $rootScope.elapseHour(date),
            hours = '' + (date.getHours()),
            minute = '' + date.getMinutes();

        //자릿수 맞추기
        if (hours.length < 2) hours = '0' + hours;
        if (minute.length < 2) minute = '0' + minute;

        return hours + ':' + minute;
    };

    //join '-' each String years, month, day, (hours, minutes..)
    $rootScope.formatDateString = function(year, month, day, hours, minutes, seconds) {
        var defaultResult = [year, month, day].join('-');
        if (hours == undefined) return defaultResult; // return yyyy-mm-dd
        else if (minutes == undefined) return defaultResult += ' ' + hours; // yyyy-mm-dd HH
        else if (seconds == undefined) return defaultResult += ' ' + hours + ':' + minutes;
        else return defaultResult += ' ' + hours + ':' + minutes + ':' + seconds;
    };

    $rootScope.goto = function(url) {
        $location.path('/' + url);
    };

    //서버시간과 맞추기 위해 Date 오브젝트에서 시간을 -9H 해준다
    $rootScope.elapseHour = function(date) {
        date = new Date(date);
        date.setHours(date.getHours() - 9);
        return date;
    }
});