'use strict';

app.controller('orderCtrl', ['$http', '$scope', '$cookieStore', '$window', '$location', '$rootScope', '$routeParams',
    function ($http, $scope, $cookieStore, $window, $location, $rootScope, $routeParams) {

        $scope.params = $routeParams;
        $rootScope.title = '주문페이지';
        $rootScope.sidebar = 'order';
        // 기초정보 초기화
        $scope.userName = '';
        $scope.userPhone = '';
        $scope.userAddress = '';
        $scope.productList = [];
        $scope.productPriceSum = 0;
        $scope.timeslotSelected = false;

        //상품정보를 {상품ID, 주문개수} 형태로 변환한다.
        var makeOrderList = function (productList) {
            var resultList = new Array();
            for (var index in productList) {
                var order = new Object();
                order.productID = productList[index].productID;
                order.quantity = productList[index].quantity;

                resultList.push(order);
            }
            return resultList;
        }

        ////////////////Controller Method////////////////
        $scope.isCutoffPassedOrisCountPassed = function (timeslot) {
            var now = new Date(); // long type time
            
            //!! 임시. DB에서 오는 시간이 +9시간 되어서 오기 때문에 강제로 줄여서씀.
            var cutoff = $rootScope.elapseHour(timeslot.cutoff);

            //타임슬롯 시간이 현재시간보다 이전이거나
            //타임슬롯카운트가 5 이상인 경우
            if ((cutoff.getTime() <= now.getTime()) || timeslot.count >= 5)
                return true; // cutoff time passed && count >= 5
            else return false;
        }

        //배송희망 시간 클릭시 해당 날짜의 타임슬롯 출력
        $scope.getTimeslots = function (index) {
            $scope.currentTimeslotID = null;

            $scope.timeslotSelected = false;
            $scope.currentTimeslots = $scope.timeslots[index];
            $scope.timeslotSelected = true;
        }

        //타임슬롯 선택시 확인
        $scope.setCurrentTimeslot = function (selectedTimeslot) {
            //발생하지 않아야 한다. HTML에서도 처리가 있기 때문이다
            if (selectedTimeslot.count >= 5 || selectedTimeslot.cutoff <= $scope.now) {
                alert('해당 예약시간은 주문이 가득찼습니다. 다른 시간대를 선택해주세요');
                $scope.currentTimeslotID = null;
                $scope.radioSelectedTimeslot.value = null;
            } else {
                $scope.currentTimeslotID = selectedTimeslot.timeslotID;
            }
        }

        //주문하기버튼 클릭시 발생
        $scope.order = function () {
            if ($scope.currentTimeslotID === null || $scope.currentTimeslotID === undefined) {
                alert('배송시간을 선택해주세요');
            } else {
                var orderJson = {};

                //바로구매의 경우 주소변경, json에 productList 제외됨
                // if ($location.search().from === 'direct') {
                if ($scope.params.where === 'direct') {
                    orderJson['fromBasket'] = false;
                }
                //카트구매의 경우 주소그대로, json에 productList 추가됨
                else {
                    orderJson['fromBasket'] = true;
                }

                //타임슬롯은 동일
                orderJson['timeslotID'] = ($scope.currentTimeslotID);
                orderJson['productList'] = makeOrderList($scope.productList);
                var parsedJson = angular.toJson(orderJson);

                $http({
                        method: 'POST',
                        url: 'http://52.231.28.248/apis/orders',
                        data: parsedJson, //{timeslotID, fromBasket, productList[]}
                        headers: {
                            'Content-Type': 'application/json'
                        }
                    })
                    .then(function (response) {
                        alert('주문이 완료되었습니다.');
                        $rootScope.goto('mypage');
                    }, function (response) {
                        var exception = response.data;
                        if (exception.errCode == 610) {
                            alert('해당 시간을 선택할 수 없습니다. 다른 시간을 선택해주세요');
                            console.log(exception.errMsg);
                        } else {
                            alert('주문에 실패했습니다. 관리자에게 문의하세요 - ' + exception.errCode);
                            console.log(exception.errMsg);
                        }
                    });
            }
        }
        ////////////////Controller Method END////////////////


        //로그인되지 않은 사용자를 로그인페이지로 되돌려보냄
        if ($cookieStore.get('session') === undefined) {
            alert('로그인 후 사용해주세요');
            $rootScope.goto('login/mypage');
        }
        //로그인 된 정상 사용자인 경우
        else {
            $scope.days = [];
            $scope.timeslotSelected = false;
            $scope.timeslots = [];

            // 유저 정보 수신
            $http.get('http://52.231.28.248/apis/user/mypage')
                //Success
                .then(
                    function (userData) {
                        $scope.userName = userData.data.userID;
                        $scope.userPhone = userData.data.phone;
                        $scope.userAddress = userData.data.address;
                        $scope.userEmail = userData.data.email;
                    },
                    function (error) {
                        alert('사용자 정보를 불러오는데 실패했습니다.');
                        $window.history.back();
                    });

            // 상품 정보 수신. ?from=cart 와 ?from=direct&... 으로 나눠진다.
            if ($scope.params.where === 'cart') {
                //API서버를 통해 카트정보를 불러온다.
                $http.get('http://52.231.28.248/apis/baskets')
                    .then(function (response) {
                        $scope.productList = response.data;
                        if ($scope.productList.length == 0 || $scope.productList === undefined) {
                            alert('장바구니에 상품이 없습니다.');
                            $rootScope.goto('home');
                        }
                        $scope.productPriceSum;
                        angular.forEach($scope.productList, function (product, index) {
                            $scope.productPriceSum += product.price * product.quantity;
                        });
                    }, function (error) {
                        var exception = error.data;
                        if (exception.errCode != undefined) {
                            alert('장바구니 상품정보 수신 실패 : ' + exception.errCode);
                            $rootScope.goto('home');
                        } else {
                            alert('알수 없는 에러 : ' + exception);
                        }
                    });
            }
            /*
            만약 dealDetail에서 바로구매 버튼을 눌러 들어왔다면 상품은 하나.
            그런 경우 상품번호로 상품정보를 조회, quantity만 GET Param으로 입력.
            */
            else if ($scope.params.where === 'direct') {
                $http({
                    url: 'http://52.231.28.248/apis/products/' + $scope.params.productId, //($location.search().id * 1),
                    method: "GET",
                    params: {}
                }).then(function (response) {
                    var product = response.data;
                    product.quantity = $scope.params.quantity; //$location.search().quantity;
                    $scope.productList.push(product);
                    $scope.productPriceSum += $scope.productList[0].price * $scope.productList[0].quantity;
                }, function (response) {
                    // alert('단일 상품정보 수신 실패 : ' + response.data.errCode);
                    alert('단일 상품정보 수신 실패');
                    $rootScope.goto('home');
                });
            }
            //아무런 GET param이 없는 경우 카트에서 정보를 불러온다.
            else {
                //API서버를 통해 카트정보를 불러온다.
                $http.get('http://52.231.28.248/apis/baskets').
                then(function (response) {
                    $scope.productList = response.data;
                    if ($scope.productList.length == 0 || $scope.productList === undefined) {
                        alert('장바구니에 상품이 없습니다.');
                        $rootScope.goto('home');
                    }
                    angular.forEach(response.data, function (value, key) {
                        $scope.productPriceSum += value.price * value.quantity;
                    });
                }, function (response) {
                    var exception = response.data;
                    if (exception.errCode != undefined) {
                        // alert('장바구니 상품정보 수신 실패 : ' + exception.errCode);
                        alert('장바구니 상품정보 수신 실패');
                        $rootScope.goto('home');
                    } else {
                        // alert('알수 없는 에러 : ' + exception.data);
                        alert('알수 없는 에러');
                        $rootScope.goto('home');
                        console.log(exception);
                    }
                });
            }

            //타임슬롯 정보 수신. 현재 날짜 이후 3일을 가져온다. 공휴일 및 주말 제외
            $http({
                url: 'http://52.231.28.248/apis/timeslots/validDays',
                method: 'GET',
                params: {
                    searchInitDate: $rootScope.formatDate(new Date())
                }
            }).
            then(function (response) {
                var timeslots = response.data;
                $scope.days = [];
                angular.forEach(timeslots, function (value, key) {
                    $scope.days.push($rootScope.formatDate(value.deliveryDate));
                    $scope.timeslots.push(value.timeList);
                });
            }, function (response) {
                var exception = response.data;
                if (exception.errCode != undefined) {
                    // alert('타임슬롯 정보 수신 실패 : ' + exception.errCode);
                    alert('타임슬롯 정보 수신 실패');
                } else {
                    // alert('타임슬롯 정보 수신 실패 : ' + exception);
                    alert('타임슬롯 정보 수신 실패');
                    console.log(exception);
                }
            });
        }
    }
]);