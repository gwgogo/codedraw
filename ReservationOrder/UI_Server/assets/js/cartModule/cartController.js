app.controller('cartCtrl', function($scope, $http, $window, $cookieStore, $location, $rootScope) {
    $rootScope.title = '장바구니';
    $rootScope.sidebar = 'cart';

    if ($cookieStore.get('session') === undefined) {
        alert('로그인 후 사용해주세요');
        $location.path('/login/cart');
    } else {
        //최초 장바구니에서 상품 불러오기
        $http.get('http://52.231.28.248/apis/baskets')
            .then(function(response) {
                //장바구니에 상품 없는경우 튕김
                if (response.data.length === undefined || response.data.length == 0) {
                    alert('장바구니에 상품이 없습니다.');
                    $rootScope.goto('home');
                }

                $scope.products = response.data; // $scope.products에 reponse.data 저장
                $scope.productsSum = 0; // 고른 상품의 총 합을 저장할 변수

                angular.forEach($scope.products, function(value, key) {
                    $scope.productsSum += value.quantity * value.price;
                });
            }, function(error) {
                var exception = error.data;
                alert('장바구니 상품정보 수신 실패');
                // alert('장바구니 상품정보 수신 실패 : ' + exception.errCode);
                $rootScope.goto('home');
            });
    }

        //장바구니에서 단일 상품 삭제
    $scope.deleteProduct = function(product) {
        if (confirm('정말 삭제하시겠습니까?')) {

            $http({
                    method: 'DELETE',
                    url: 'http://52.231.28.248/apis/baskets/' + product.productID,
                    data: {}
                })
                .then(function(response) {
                    alert('삭제되었습니다.');
                    $scope.productsSum -= product.quantity * product.price;
                    $scope.products.splice($scope.products.indexOf(product), 1);
                    if ($scope.products.length == 0) {
                        alert('장바구니에 상품이 없습니다.');
                        $rootScope.goto('home');
                    }
                }, function(error) {
                    var exception = error.data;
                    if (exception.errCode != undefined) {
                        
                        // alert(exception.errMsg + ':' + exception.errCode);
                        console.log(exception.errMsg +':'+ exception.errCode);
                        alert('오류가 발생했습니다.');
                        
                    } else {
                        //알수 없는 에러
                        // alert(exception);
                        console.log(exception);
                        alert('알 수 없는 오류가 발생했습니다.');
                        
                    }
                });

        }
    };

    //장바구니에 담긴 모든 상품 삭제
    $scope.deleteAll = function() {
        if (confirm('정말 삭제하시겠습니까?')) {
            $http({
                    method: 'DELETE',
                    url: 'http://52.231.28.248/apis/baskets/',
                    params: {}
                })
                .then(function(response) {
                    alert('모든 상품이 삭제되었습니다.');
                    $rootScope.goto('home');
                }, function(error) {
                    var exception = error.data;
                    if (exception.errCode != undefined) {
                        // alert(exception.errMsg + ':' + exception.errCode);
                        alert('오류가 발생했습니다.');
                        console.log(exception.errMsg +':'+ exception.errCode);
                    } else {
                        //알수 없는 에러
                        // alert(exception);
                        alert('알 수 없는 오류가 발생했습니다.');
                        console.log(exception);
                    }
                });
        }
    }

    //상품의 개수 감소
    $scope.amountSub = function(product) {
        if (product.quantity <= 1) {
            alert('1 이하로 선택할 수 없습니다.');
        } else {
            product.quantity--;
            $scope.productsSum -= product.price;
            $http({
                method: 'PUT',
                url: 'http://52.231.28.248/apis/baskets/' + product.productID + '/minus',
                params: {}
            }).then(function() {}, function(error) {
                var exception = error.data;
                if (exception.errCode != undefined && exception.errCode == 609) {
                    alert('잘못된 값입니다.');
                    console.log(exception.errMsg);
                } else {
                    //알수 없는 에러
                    // alert(exception);
                    alert('알 수 없는 에러가 발생했습니다.');
                    console.log(exception);
                }
            });
        }
    };

    //상품의 개수 증가
    $scope.amountAdd = function(product) {
        product.quantity++;
        $scope.productsSum += product.price;
        $http({
            method: 'PUT',
            url: 'http://52.231.28.248/apis/baskets/' + product.productID + '/plus',
            params: {
                productID: product.productID
            }
        }).then(function() {}, function(error) {
            var exception = error.data;
            if (exception.errCode != undefined && exception.errCode == 609) {
                alert('잘못된 값입니다.');
                console.log(exception.errMsg);
            } else {
                //알수 없는 에러
                // alert(exception);
                alert('알 수 없는 에러가 발생했습니다.');
                console.log(exception);
            }
        });
    };
    
});