app.controller('getdeal', ['$scope','$location','$http', function($scope,$location,$http) {
	
	$scope.$location = {};


	/* 나중에 지우기  */
	/* URL 에서 parameter 로그 찍어주는 로직  */
	$location.search();
	console.log('productid는');
	console.log($location.search());
	console.log('입니다.');
	var temp=$location.search().product_id;
	/* 나중에 지우기  */

	/*
	* 상품에 대한 상세 정보를 GET 방식으로 받아서 List 에 저장 하기!
	*/
	$http({
		url: 'http://127.0.0.1:8080/productDetail', 
		method: "GET",
		params: {product_id: temp}
	}).then(function mySuccess(response) {
		$scope.List = response.data;
	}, function myError(response) {
		$scope.List = response.statusText;
		console.log($scope.List);
	});


 	/*
	* 상품의 개수를 입력 하는 로직
	*/
	$scope.amount=0;
	
	$scope.amountsub=function(){
		debugger;
		$scope.amount--;
	};
	$scope.amountadd=function(){
		$scope.amount++;
	};

	$scope.submit=function(){

		$http({
			url: 'http://127.0.0.1:8080/addBasket', 
			method: "post",
			params: {
				//user_id : 'admin0001',
				product_id : temp,
				quantity : $scope.amount

			}
		}).then(function mySuccess(response) {

			$scope.List = response.data;
			console.log($scope.List);
			alert('장바구니가 성공적으로 삽입했습니다.');
			/*
			* 이제 여기서 끝인거지. 한번 물어보기
			*/
		}, function myError(response) {
			$scope.List = response.statusText;
			console.log($scope.List);
			alert('장바구니 넣기 실패!');
			/*
			*그러면 뭐 다시 눌러달라고 하든가 해야지
			*
			*/
		});


	}





}]);
