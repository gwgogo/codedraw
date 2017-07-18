app.controller('cart', function($scope, $http) {

	$http.get('http://127.0.0.1:8080/basket').
	then(function(response) {
		$scope.products = response.data;
	},function(response){
		alert('fail');
	});



	
	
	$scope.amountsub=function(index){
		debugger;
		index.quantity--;

	};
	$scope.amountadd=function(index){
		debugger;
		index.quantity++;
	};

	// $scope.submit=function(){

	// 	$http({
	// 		url: 'http://52.231.28.248/apis/addBasket', 
	// 		method: "post",
	// 		params: {
	// 			user_id : 'admin0001',
	// 			product_id : temp,
	// 			quantity : $scope.amount

	// 		}
	// 	}).then(function mySuccess(response) {

	// 		$scope.List = response.data;
	// 		console.log($scope.List);
	// 		alert('장바구니가 성공적으로 삽입했습니다.');
	// 		/*
	// 		* 이제 여기서 끝인거지. 한번 물어보기
	// 		*/
	// 	}, function myError(response) {
	// 		$scope.List = response.statusText;
	// 		console.log($scope.List);
	// 		alert('장바구니 넣기 실패!');
	// 		/*
	// 		*그러면 뭐 다시 눌러달라고 하든가 해야지
	// 		*
	// 		*/
	// 	});


	// }
});