app.controller('productListCtrl', ['$scope', '$http', '$rootScope', '$route',function($scope, $http, $rootScope, $route) {


    $scope.productList = '';
    $scope.categoryList = '';
    $rootScope.title = '상품리스트';
    $rootScope.sidebar = 'productList';



    //카테고리목록 받아옴
    $http.get('http://52.231.28.248/apis/categories').
    then(function(response) {
        $scope.categoryList = response.data;
    },function(response){
        alert('카테고리목록을 불러오는데 실패했습니다. 잠시 후 다시 한번 시도해주십시오.');
    }
    );

    //전체 위치에서 받아옴
    $scope.searchAll = function() {
        $http.get('http://52.231.28.248/apis/products').
        then(function(response) {
            $scope.productList = response.data;
        },function(response){
            alert('상품을 불러오는데 실패했습니다. 잠시 후 다시 한번 시도해주십시오.');
        });
    }
    $scope.searchAll();

    //특정 위치에서 받아옴
    //!! angularjs 자체에서 필터링해볼까 생각했으나 이미 API가 있으므로 이를 활용
    $scope.searchInCategory = function(categoryID) {
        $http.get('http://52.231.28.248/apis/products/category/' + categoryID).
        then(function(response) {
            $scope.productList = response.data;
        },function(response){
            alert('카테고리별 목록을 불러오는데 실패했습니다. 잠시 후 다시 한번 시도해주십시오.');
        }
        );
    }
    
    

}]);