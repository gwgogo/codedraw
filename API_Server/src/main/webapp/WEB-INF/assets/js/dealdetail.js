app.controller('getdeal', ['$scope','$location','$http', function($scope,$location,$http) {
	
	$scope.$location = {};

	$location.search();
	console.log('productid는');
	console.log($location.search());
	console.log('입니다.');
	var temp=$location.search().product_id;


	$http({
    url: 'http://52.231.28.248:8080/productDetail', 
    method: "GET",
    params: {product_id: temp}
 	}).then(function mySuccess(response) {
        $scope.List = response.data;
    }, function myError(response) {
        $scope.List = response.statusText;
    });

}]);
