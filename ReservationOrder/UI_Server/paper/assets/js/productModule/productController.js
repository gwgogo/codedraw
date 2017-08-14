app.controller('productDetailCtrl', ['$scope', '$location', '$http', '$cookieStore', '$window', '$routeParams', '$rootScope', function($scope, $location, $http, $cookieStore, $window, $routeParams, $rootScope) {

    $rootScope.title = '상품 상세페이지';
    $rootScope.sidebar = '';
    $scope.params = $routeParams;

    /*
     * 상품의 개수를 입력 하는 로직
     */
    $scope.amount = 1;

    //GET parameter가 없는 경우 dealList로 추방
    if ($scope.params.productId === undefined) {
        alert('잘못된 접근입니다.');
        $location.path('/home');
    } else {

        var refresh = function() {
            $http({
                url: 'http://52.231.28.248/apis/products/' + $scope.params.productId,
                method: "GET",
                params: {}
            }).then(function(response) {
                $scope.List = response.data;
            }, function(response) {
                var exception = response.data;
                if (exception.errCode = 604) {
                    alert('상품이 존재하지 않습니다.');
                    $rootScope.goto('home');
                    return;
                }
                if (response.data != null) {
                    alert(response.data.errCode);
                }
            });
        }
        refresh();
        

    } //else 문 종료

    
    $scope.amountSub = function() {
        if ($scope.amount <= 1) {
            alert('1 이하로 선택할 수 없습니다.');
        } else {
            $scope.amount--;
        }
    };
    $scope.amountAdd = function() {
        if ($scope.amount > ($scope.List.maxQuantity - $scope.List.sellQuantity)) {
            alert('구매 가능량을 초과하였습니다.');
        } else {
            $scope.amount++;
        }
    };

    //현재 페이지에서 선택하려는 총량이 1미만 혹은 최대총량 이상이 되려는 경우 고정
    //버튼이 아닌 input 에 직접 입력하는 경우에 한함. 물론 아닌 경우에도 적용됨
    $scope.validate = function() {
        if ($scope.amount < 1) {
            $scope.amount = 1;
        } else if ($scope.amount > ($scope.List.maxQuantity - $scope.List.sellQuantity)) {
            $scope.amount = $scope.List.maxQuantity - $scope.List.sellQuantity;
        }
    }

    $scope.submit = function() {
        //장바구니 담기시, 로그인이 되어있지 않은 사용자는 사용할 수 없다.
        if ($cookieStore.get('session') === undefined) {
            alert('로그인 후 사용해주세요');
            $rootScope.goto('login/product/' + $scope.params.productId);
        } else {
            $http({
                    // url: 'http://52.231.28.248/apis/baskets/' + ($location.search().productID * 1) + '/' + $scope.amount,
                    url: 'http://52.231.28.248/apis/baskets/' + $scope.params.productId + '/' + $scope.amount,
                    method: "POST",
                    params: {}
                })
                .then(function(response) {
                    $scope.List = response.data;
                    var isConfirmed = confirm('장바구니에 성공적으로 삽입했습니다.\n확인하시겠습니까?');
                    if (isConfirmed) {
                        $location.path('/cart');
                    } else {
                        refresh();
                    }
                }, function(response) {
                    var exception = response.data;
                    if (exception.errCode) {
                        alert('장바구니에 이미 상품이 있습니다.');
                        console.log(exception.errMsg);
                    } else {
                        alert('처리되지 않은 오류 - ' + exception.errCode);
                        console.log(exception.errMsg);
                    }
                });
        }
    }

    $scope.directBuy = function() {
        // var refer = $window.location.href = '/paper/order.html?from=direct&id=' +
        //     $location.search().productID + '&quantity=' + $scope.amount;
        if ($cookieStore.get('session') === undefined) {
            alert('로그인 후 사용해주세요');
            $rootScope.goto('login/product/' + $scope.params.productId);
        } else {
            // $window.location.href = refer;
            $rootScope.goto('order/direct/' + $scope.params.productId + '/' + $scope.amount);
        }

    }

}]);